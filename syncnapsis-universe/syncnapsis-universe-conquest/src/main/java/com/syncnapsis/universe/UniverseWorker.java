package com.syncnapsis.universe;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.utils.concurrent.Worker;

/**
 * {@link Worker} implementation for the universe-conquest game.<br>
 * The {@link Worker} will run in a separate Thread and will continuously perform the the necessary
 * step-wise population calculations for the game using the {@link Calculator}.
 * 
 * @author ultimate
 */
public class UniverseWorker extends Worker implements InitializingBean
{
	/**
	 * The calculator used
	 */
	protected Calculator	calculator;

	/**
	 * Standard Constructor
	 * 
	 * @param interval - the interval for executing the calculations
	 * @param timeProvider - the {@link TimeProvider}
	 */
	public UniverseWorker(long interval, TimeProvider timeProvider)
	{
		super(interval, timeProvider);
	}

	/**
	 * The {@link Calculator}
	 * 
	 * @return calculator
	 */
	public Calculator getCalculator()
	{
		return calculator;
	}

	/**
	 * The Calculator
	 * 
	 * @param calculator
	 */
	public void setCalculator(Calculator calculator)
	{
		this.calculator = calculator;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(calculator, "calculator must not be null!");
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
