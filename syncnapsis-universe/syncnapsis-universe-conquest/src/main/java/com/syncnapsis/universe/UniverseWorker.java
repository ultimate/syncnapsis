package com.syncnapsis.universe;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.utils.concurrent.Worker;
import com.syncnapsis.utils.constants.ConstantLoader;

/**
 * {@link Worker} implementation for the universe-conquest game.<br>
 * The {@link Worker} will run in a separate Thread and will continuously perform the the necessary
 * step-wise population calculations for the game using the {@link Calculator}.<br>
 * <br>
 * This worker will start itself after properties are set and after the underlying
 * {@link ConstantLoader} has finished loading the constants. If no {@link ConstantLoader} is set it
 * will start immediately.
 * 
 * @author ultimate
 */
public class UniverseWorker extends Worker implements InitializingBean, DisposableBean
{
	/**
	 * The calculator used
	 */
	protected Calculator		calculator;

	/**
	 * The {@link ConstantLoader} to wait for before start working
	 */
	protected ConstantLoader<?>	constantLoader;

	/**
	 * Standard Constructor
	 * 
	 * @param interval - the interval for executing the calculations
	 * @param timeProvider - the {@link TimeProvider}
	 * @param calculator - the {@link Calculator}
	 */
	public UniverseWorker(long interval, TimeProvider timeProvider, Calculator calculator)
	{
		super(interval, timeProvider);
		this.calculator = calculator;
	}

	/**
	 * The {@link ConstantLoader} to wait for before start working
	 * 
	 * @return constantLoader
	 */
	public ConstantLoader<?> getConstantLoader()
	{
		return constantLoader;
	}

	/**
	 * The {@link ConstantLoader} to wait for before start working
	 * 
	 * @param constantLoader - the Constant Loader
	 */
	public void setConstantLoader(ConstantLoader<?> constantLoader)
	{
		this.constantLoader = constantLoader;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		if(this.constantLoader != null)
			this.constantLoader.await();
		this.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception
	{
		this.stop();
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.Worker#work(long)
	 */
	@Override
	public void work(long executionId) throws Throwable
	{
		// TODO Auto-generated method stub

		logger.info("UniverseWorker running: #" + executionId);
	}
}
