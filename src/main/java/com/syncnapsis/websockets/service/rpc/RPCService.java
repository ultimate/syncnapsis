package com.syncnapsis.websockets.service.rpc;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.syncnapsis.exceptions.DeserializationException;
import com.syncnapsis.exceptions.SerializationException;
import com.syncnapsis.providers.AuthorityProvider;
import com.syncnapsis.security.SecurityManager;
import com.syncnapsis.utils.serialization.Serializer;
import com.syncnapsis.websockets.Connection;
import com.syncnapsis.websockets.service.BaseService;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Service-Implementation allowing bi-directional RPCs.
 * RPCs are forwarded to a RPCHandler the is used to obtain the target Object and the required
 * Method and to invoke the Method with the given Arguments.
 * 
 * @author ultimate
 */
public class RPCService extends BaseService implements InitializingBean
{
	/**
	 * The Serializer used to (de)-serialize messages.
	 */
	protected Serializer<String>	serializer;
	/**
	 * The RPCHandler that finally executes the RPC.
	 */
	protected RPCHandler								rpcHandler;
	/**
	 * The Number of calls the Server has made. Used to generate the CID
	 */
	protected long										currentCall	= 0;
	/**
	 * A map for caching the results
	 */
	protected final Map<Long, Object>					results		= new TreeMap<Long, Object>();

	/**
	 * Default Constructor
	 */
	public RPCService()
	{
	}

	/**
	 * The Serializer used to (de)-serialize messages.
	 * 
	 * @return serializer
	 */
	public Serializer<String> getSerializer()
	{
		return serializer;
	}

	/**
	 * The Serializer used to (de)-serialize messages.
	 * 
	 * @param serializer - the Serializer
	 */
	public void setSerializer(Serializer<String> serializer)
	{
		this.serializer = serializer;
	}

	/**
	 * The RPCHandler that finally executes the RPC.
	 * 
	 * @return rpcHandler
	 */
	public RPCHandler getRpcHandler()
	{
		return rpcHandler;
	}

	/**
	 * The RPCHandler that finally executes the RPC.
	 * 
	 * @param rpcHandler - the RPCHandler
	 */
	public void setRpcHandler(RPCHandler rpcHandler)
	{
		this.rpcHandler = rpcHandler;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		Assert.notNull(serializer, "serializer must not be null");
		Assert.notNull(rpcHandler, "rpcHandler must not be null");
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.websockets.service.BaseService#onMessage(com.syncnapsis.websockets.Connection,
	 * java.lang.String)
	 */
	@Override
	public void onMessage(Connection connection, String data)
	{
		logger.debug(connection.getId() + ": " + data);
		try
		{
			RPCMessage msg = (RPCMessage) serializer.deserialize(data, new RPCMessage(), getAuthorities());
			msg.setConnection(connection);
			if(msg.getScid() != null)
				this.onResult(msg);
			else
				this.onRPC(msg);
		}
		catch(DeserializationException e)
		{
			logger.error("Error deserializing message!", e);
		}
	}

	/**
	 * Handler an remote call when onMessage(..) detects the incoming message is an RPC.
	 * Simple forwarding to {@link RPCHandler#doRPC(RPCMessage)}.
	 * Forwarding is done here to enable pre- or post-processing or overwriting if necessary
	 * 
	 * @see RPCHandler#doRPC(RPCMessage)
	 * @param message - the RPCMessage
	 */
	protected void onRPC(RPCMessage message)
	{
		convertMessageData(message, RPCCall.class);
		if(!(message.getData() instanceof RPCCall))
		{
			logger.error("invalid RPCCall for scid '" + message.getScid() + "' received: "
					+ (message.getData() != null ? message.getData().getClass() + " " : "") + message.getData());
			return;
		}
		Object result = this.rpcHandler.doRPC((RPCCall) message.getData(), getAuthorities());
		if(result != null)
		{
			try
			{
				this.respond(message, result);
			}
			catch(IOException e)
			{
				logger.error("Could not send response.", e);
			}
		}
	}

	/**
	 * Handler an result when onMessage(..) detects the incoming message is a result for a
	 * server-client call.
	 * This Method notifies the corresponding method call so it can return the required return
	 * value.
	 * 
	 * @param msg - the result message
	 */
	protected void onResult(RPCMessage msg)
	{
		synchronized(this.results)
		{
			this.results.put(msg.getScid(), msg.getData());
			this.results.notifyAll();
		}
	}

	/**
	 * Actually do the RPC from server to client.
	 * Therefore the RPC parameters are wrapped into a RPCMessage an send to the client.
	 * If a return value is expected this method will pause until the return value from the client
	 * is received via onResult
	 * 
	 * @see RPCService#onResult(RPCMessage)
	 * @see RPCService#send(RPCMessage)
	 * @param connection - the Connection to perform the RPC on
	 * @param objectName - the client-side Object to perform the PRC on
	 * @param methodName - the client-side Method to invoke
	 * @param args - the arguments to pass to the method
	 * @param returnType - the expected return type
	 * @return the return value or null if no return value is expected
	 * @throws IOException - if message sending fails
	 */
	@SuppressWarnings("unchecked")
	public Object call(Connection connection, String objectName, String methodName, Object[] args, Class<?> returnType) throws IOException
	{
		RPCCall data = new RPCCall(objectName, methodName, args);
		RPCMessage message = new RPCMessage(connection, null, cid(), data);
		this.send(message);

		// wait for result if necessary
		if(returnType.equals(void.class))
			return null;
		else
		{
			synchronized(this.results)
			{
				while(!this.results.containsKey(message.getScid()))
				{
					try
					{
						this.results.wait();
					}
					catch(InterruptedException e)
					{
						logger.error("Could not wait for result.", e);
					}
				}
				Object result = this.results.remove(message.getScid());
				if(result instanceof Map)
				{
					try
					{
						return serializer.getMapper().fromMap(returnType.newInstance(), (Map<String, Object>) result, getAuthorities());
					}
					catch(InstantiationException e)
					{
						logger.error("Could not instantiate Type " + returnType.getName());
					}
					catch(IllegalAccessException e)
					{
						logger.error("Could not access Type " + returnType.getName());
					}
				}
				return result;
			}
		}
	}

	/**
	 * Send a response to an client-server call.
	 * Therefore the result is wrapped into a RPCMessage and send to the client.
	 * 
	 * @see RPCService#send(RPCMessage)
	 * @param message - the client-server RPCMessage
	 * @param result - the result to return
	 * @throws IOException - if sending fails
	 */
	protected void respond(RPCMessage message, Object result) throws IOException
	{
		RPCMessage response = new RPCMessage(message.getCcid(), null, result);
		this.send(response);
	}

	/**
	 * Send an RPCMessage to the client. The message can either be a call or a result.
	 * 
	 * @param message - the RPCMessage to send.
	 * @throws IOException - if sending fails
	 */
	protected void send(RPCMessage message) throws IOException
	{
		try
		{
			message.getConnection().sendMessage(serializer.serialize(message, getAuthorities()));
		}
		catch(SerializationException e)
		{
			throw new IOException("SerializationException", e);
		}
	}
	
	/**
	 * Convert the data included in the RPCMessage to a required type.
	 * 
	 * @param message - the RPCMessage to convert
	 * @param targetClass - The Type to convert the data to
	 */
	@SuppressWarnings("unchecked")
	protected void convertMessageData(RPCMessage message, Class<?> targetClass)
	{
		if(!(message.getData() instanceof Map))
			throw new IllegalStateException("Non-Map-Data cannot be converted to " + targetClass.getName());
		try
		{
			message.setData(serializer.getMapper().fromMap(targetClass.newInstance(), (Map<String, Object>) message.getData(), getAuthorities()));
		}
		catch(InstantiationException e)
		{
			logger.error("This should not happen!", e);
		}
		catch(IllegalAccessException e)
		{
			logger.error("This should not happen!", e);
		}
	}

	/**
	 * Create a new unique Call-ID required for each RPC that is done.
	 * 
	 * @return the Call-ID
	 */
	protected synchronized long cid()
	{
		return ++this.currentCall;
	}

	/**
	 * Get the authorities provided by the AuthorityProvider
	 * 
	 * @see RPCService#getAuthorityProvider()
	 * @return the authorities
	 */
	protected Object[] getAuthorities()
	{
		AuthorityProvider authorityProvider = getAuthorityProvider();
		return authorityProvider == null ? new Object[0] : authorityProvider.get();
	}

	/**
	 * The Provider used to determine the authorities for (de)-serialization.
	 * 
	 * @see SecurityManager#getAuthorityProvider()
	 * @return authorityProvider
	 */
	protected AuthorityProvider getAuthorityProvider()
	{
		if(serializer.getMapper().getSecurityManager() != null)
			return serializer.getMapper().getSecurityManager().getAuthorityProvider();
		return null;
	}

	/**
	 * Get a virtual Client-instance for the given target name and the Client represented by the
	 * Connection.
	 * For example a Proxy may be created that handles the method calls an creates the RPCMessage to
	 * be handled by this Service.
	 * 
	 * @see RPCHandler#getClientInstance(String, Connection)
	 * @param objectName - the name of the Object to get the Client-Instance for
	 * @param connection - the Connection representing the Client
	 * @return the Client-Instance
	 */
	public Object getClientInstance(String objectName, Connection connection)
	{
		return this.rpcHandler.getClientInstance(objectName, connection);
	}
}