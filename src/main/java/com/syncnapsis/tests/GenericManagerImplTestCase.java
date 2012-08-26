package com.syncnapsis.tests;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jmock.Expectations;
import com.syncnapsis.data.dao.GenericDao;
import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.data.service.GenericManager;
import com.syncnapsis.providers.TimeProvider;

public abstract class GenericManagerImplTestCase<T extends BaseObject<PK>, PK extends Serializable, M extends GenericManager<T, PK>, D extends GenericDao<T, PK>>
		extends BaseDaoTestCase
{
	protected T						entity;
	protected M						mockManager;
	protected D						mockDao;
	protected Class<? extends D>	daoClass;

	protected TimeProvider timeProvider;

	public void setEntity(T entity)
	{
		this.entity = entity;
	}

	public void setMockManager(M mockManager)
	{
		this.mockManager = mockManager;
	}

	public void setMockDao(D mockDao)
	{
		this.mockDao = mockDao;
	}

	public void setDaoClass(Class<? extends D> daoClass)
	{
		this.daoClass = daoClass;
	}

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
		mockManager = null;
		mockDao = null;
	}

	protected void simpleGenericTest(final MethodCall managerCall, final MethodCall daoCall) throws Exception
	{
		StringBuilder out = new StringBuilder();
		out.append("testing ");
		out.append(managerCall.getMethod());
		out.append("(");
		if(managerCall.getIn() != null && managerCall.getIn().length > 0)
		{
			for(int i = 0; i < managerCall.getIn().length; i++)
			{
				if(i != 0)
					out.append(", ");
				out.append(managerCall.getIn()[i].getClass().getSimpleName());
			}
		}
		out.append(")");
		out.append(" call on -> ");
		out.append(daoCall.getMethod());
		out.append("(");
		if(daoCall.getIn() != null && daoCall.getIn().length > 0)
		{
			for(int i = 0; i < daoCall.getIn().length; i++)
			{
				if(i != 0)
					out.append(", ");
				out.append(daoCall.getIn()[i].getClass().getSimpleName());
			}
		}
		out.append(")");
		logger.debug(out.toString());

		final Method managerMethod = getMethod(mockManager.getClass(), managerCall);
		final Method daoMethod = getMethod(daoClass, daoCall);

		mockContext.checking(new Expectations() {
			{
				daoMethod.invoke(oneOf(mockDao), daoCall.getIn());
				will(returnValue(daoCall.getOut()));
			}
		});
		assertEquals(managerCall.getOut(), managerMethod.invoke(mockManager, managerCall.getIn()));
		mockContext.assertIsSatisfied();
	}

	public void testGet() throws Exception
	{
		MethodCall managerCall = new MethodCall("get", entity, 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testRemove() throws Exception
	{
		MethodCall managerCall = new MethodCall("remove", "deleted", entity);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testSave() throws Exception
	{
		MethodCall managerCall = new MethodCall("save", entity, entity);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testExists() throws Exception
	{
		MethodCall managerCall = new MethodCall("exists", true, 1L);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetByIdList() throws Exception
	{
		MethodCall managerCall = new MethodCall("getByIdList", new ArrayList<T>(), new ArrayList<Long>());
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);
	}

	public void testGetAll() throws Exception
	{
		MethodCall managerCall = new MethodCall("getAll", new ArrayList<T>(), true);
		MethodCall daoCall = managerCall;
		simpleGenericTest(managerCall, daoCall);

		MethodCall managerCall2 = new MethodCall("getAll", new ArrayList<T>());
		MethodCall daoCall2 = managerCall2;
		simpleGenericTest(managerCall2, daoCall2);
	}
}