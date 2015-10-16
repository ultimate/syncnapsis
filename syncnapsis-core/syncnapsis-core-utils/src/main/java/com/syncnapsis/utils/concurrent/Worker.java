/**
 * Syncnapsis Framework - Copyright (c) 2012 ultimate
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation; either version
 * 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MECHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Plublic License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 */
package com.syncnapsis.utils.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.providers.impl.SystemTimeProvider;
import com.syncnapsis.utils.MBeanUtil;

/**
 * Implementation of a Worker that periodically performs operations.<br>
 * When subclassing Worker instead of {@link Thread} a start/stop/suspend/resume logic is provided
 * that will call the operation implemented in {@link Worker#work(long)} in an endless loop with an
 * optionally given interval.<br>
 * Other than {@link Thread} workers may be restarted after they have been stopped.<br>
 * <b>Note:</b> the ID of the current execution will be passed to {@link Worker#work(long)}. It is
 * incremented by 1 for each execution and resetted when the worker is stopped/restarted.
 * 
 * @author ultimate
 */
public abstract class Worker implements Runnable, WorkerMXBean
{
	/**
	 * Logger-Instance
	 */
	protected transient final Logger	logger	= LoggerFactory.getLogger(getClass());

	/**
	 * The (optional) interval for executing {@link Worker#work(long)}
	 */
	protected long						interval;

	/**
	 * Internal counter for the number of executions.
	 */
	private long						executionCount;

	/**
	 * Internal {@link Thread} for running this {@link Runnable}
	 */
	private Thread						thread;

	/**
	 * Internal state variable
	 */
	private boolean						running;
	/**
	 * Internal state variable
	 */
	private boolean						suspended;

	/**
	 * Flag signaling that an error has occurred.
	 */
	private boolean						errorFlag;
	/**
	 * The error that has occurred.
	 */
	private Throwable					errorCause;
	/**
	 * Flag signaling that a warning has occurred.
	 */
	private boolean						warningFlag;
	/**
	 * The warning that has occurred.
	 */
	private Throwable					warningCause;

	/**
	 * The {@link TimeProvider} for accessing the current time and calculating the interval.
	 */
	private TimeProvider				timeProvider;

	/**
	 * Create a new {@link Worker} with 0 interval (continous execution without delay).
	 * 
	 * @see Worker#getInterval()
	 * @see Worker#setInterval(long)
	 */
	public Worker()
	{
		this(0, new SystemTimeProvider());
	}

	/**
	 * Create a new {@link Worker} with the given interval.
	 * 
	 * @see Worker#getInterval()
	 * @see Worker#setInterval(long)
	 * @param interval - the interval for executing {@link Worker#work(long)}
	 */
	public Worker(long interval)
	{
		this(interval, new SystemTimeProvider());
	}

	/**
	 * Create a new {@link Worker} with the given interval.
	 * 
	 * @see Worker#getInterval()
	 * @see Worker#setInterval(long)
	 * @param interval - the interval for executing {@link Worker#work(long)}
	 * @param timeProvider - the {@link TimeProvider} for accessing the current time and calculating
	 *            the interval.
	 */
	public Worker(long interval, TimeProvider timeProvider)
	{
		if(timeProvider == null)
			throw new IllegalArgumentException("timeProvider must not be null!");
		this.interval = interval;
		this.timeProvider = timeProvider;
		
		MBeanUtil.registerMBean(this);
	}

	/**
	 * Get the (optional) interval for executing {@link Worker#work(long)}.<br>
	 * If interval is set to 0 the worker will immediately execute {@link Worker#work(long)}
	 * continuously without delay.
	 * 
	 * @see Worker#setInterval(long)
	 * @return interval
	 */
	public long getInterval()
	{
		return interval;
	}

	/**
	 * Update the (optional) interval for executing {@link Worker#work(long)}.<br>
	 * If interval is set to 0 the worker will immediately execute {@link Worker#work(long)}
	 * continuously without delay.<br>
	 * <b>Note:</b> the interval change will be applied for the execution immediately
	 * 
	 * @see Worker#getInterval(long)
	 * @param interval - the new interval to set
	 */
	public void setInterval(long interval)
	{
		this.interval = interval;
	}

	/**
	 * Is this worker currently running?<br>
	 * <b>Note:</b> will even return true when worker is suspended.
	 * 
	 * @see Worker#isSuspended()
	 * @return running
	 */
	public boolean isRunning()
	{
		return running;
	}

	/**
	 * Is this worker currently suspended?<br>
	 * <b>Note:</b> will even return true when worker is suspended.
	 * 
	 * @see Worker#isSuspended()
	 * @return suspended
	 */
	public boolean isSuspended()
	{
		return suspended;
	}

	/**
	 * The {@link TimeProvider} for accessing the current time and calculating the interval.
	 * 
	 * @return timeProvider
	 */
	public TimeProvider getTimeProvider()
	{
		return timeProvider;
	}

	/**
	 * Flag signaling that an error has occurred.
	 * 
	 * @return error-flag
	 */
	public boolean hasError()
	{
		return errorFlag;
	}

	/**
	 * The error that has occurred.
	 * 
	 * @return errorCause
	 */
	public Throwable getErrorCause()
	{
		return errorCause;
	}

	/**
	 * Clear the error-flag and errorCause
	 * 
	 * @see Worker#hasError()
	 * @see Worker#getErrorCause()
	 */
	public void clearError()
	{
		synchronized(this)
		{
			this.errorFlag = false;
			this.errorCause = null;
		}
	}

	/**
	 * Flag signaling that a warning has occurred.
	 * 
	 * @return
	 */
	public boolean hasWarning()
	{
		return warningFlag;
	}

	/**
	 * The warning that has occurred.
	 * 
	 * @param warningCause
	 */
	public Throwable getWarningCause()
	{
		return warningCause;
	}

	/**
	 * Clear the warning-flag and warningCause
	 * 
	 * @see Worker#hasWarning()
	 * @see Worker#getWarningCause()
	 */
	public void clearWarning()
	{
		synchronized(this)
		{
			this.warningFlag = false;
			this.warningCause = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run()
	{
		long begin, duration;
		while(true)
		{
			synchronized(this)
			{
				while(this.suspended)
				{
					try
					{
						this.wait();
					}
					catch(InterruptedException e)
					{
						if(!this.errorFlag)
						{
							this.errorFlag = true;
							this.errorCause = e;
							logger.error("suspended state interrupted!", e);
						}
					}
				}

				if(!isRunning())
					break;
			}

			begin = timeProvider.get();
			try
			{
				this.work(executionCount++);
			}
			catch(Throwable t)
			{
				if(!this.errorFlag)
				{
					this.errorFlag = true;
					this.errorCause = t;
					logger.error("error while executing work (execution #" + (executionCount - 1) + ")", t);
				}
			}
			duration = timeProvider.get() - begin;

			if(logger.isDebugEnabled())
				logger.debug("worker load: " + (duration / (double) interval) + "% (" + duration + "ms of " + interval + "ms)");

			if(duration < interval)
			{
				try
				{
					Thread.sleep(interval - duration);
				}
				catch(InterruptedException e)
				{
					if(!this.errorFlag)
					{
						this.errorFlag = true;
						this.errorCause = e;
						logger.error("could not sleep for interval!", e);
					}
				}
			}
			else if(interval != 0)
			{
				synchronized(this)
				{
					if(!this.warningFlag)
					{
						this.warningFlag = true;
						this.warningCause = new Throwable("can't keep up interval of " + interval + "ms with execution time: " + duration);
					}
					logger.warn(warningCause.getMessage());
				}
			}
		}
	}

	/**
	 * Start this worker.<br>
	 * Exactly like <code>worker.start(false);</code>
	 * 
	 * @see Worker#start(boolean)
	 * @see Worker#isRunning()
	 * @see Worker#isSuspended()
	 */
	public void start()
	{
		this.start(false);
	}

	/**
	 * Start this worker - if desired in suspended state<br>
	 * Other than calling {@link Worker#start()} and {@link Worker#suspend()} subsequently this call
	 * will guarantee suspended state from the beginning without any change of the worker to be
	 * executed before suspend(..) is called.
	 * 
	 * @see Worker#start()
	 * @see Worker#suspend()
	 */
	public void start(boolean suspended)
	{
		synchronized(this)
		{
			if(this.running)
				throw new IllegalStateException("Worker already started!");
			this.running = true;
			this.suspended = suspended;
			this.clearError();
			this.clearWarning();
			this.thread = new Thread(this);
			this.thread.start();
		}
	}

	/**
	 * Stop this worker
	 * 
	 * @see Worker#isRunning()
	 * @see Worker#isSuspended()
	 */
	public void stop()
	{
		this.stop(true);
	}

	/**
	 * Stop this worker with or without joining
	 * 
	 * @see Worker#isRunning()
	 * @see Worker#isSuspended()
	 */
	protected void stop(boolean join)
	{
		synchronized(this)
		{
			if(!this.running)
				throw new IllegalStateException("Worker not running!");
			logger.debug("stopping worker: join=" + join);
			this.running = false;
			this.suspended = false;
			this.notify(); // in case worker is suspended
		}

		// Join the inner Thread using a second Thread in order to be able to reset the Worker
		// afterwards. Otherwise we could not reset the executionCount and thread-variable after
		// the inner Thread has finished, if this method is called non-blocking (argument
		// join=false)

		Thread joinThread = new Thread() {
			public void run()
			{
				try
				{
					Worker.this.join();
				}
				catch(InterruptedException e)
				{
					logger.error("thread join interrupted!");
				}
				Worker.this.thread = null;
				Worker.this.executionCount = 0;
			}
		};
		joinThread.start();

		if(join)
		{
			try
			{
				joinThread.join();
			}
			catch(InterruptedException e)
			{
				logger.error("thread join interrupted!");
			}
		}
	}

	/**
	 * Wait for this Worker to stop
	 * 
	 * @throws InterruptedException
	 */
	public void join() throws InterruptedException
	{
		if(this.thread == null)
			return; // Worker not started;
		this.thread.join();
	}

	/**
	 * Suspend this worker
	 * 
	 * @see Worker#isRunning()
	 * @see Worker#isSuspended()
	 */
	public void suspend()
	{
		synchronized(this)
		{
			if(!this.running)
				throw new IllegalStateException("Worker not running!");
			this.suspended = true;
		}
	}

	/**
	 * Resume this worker
	 * 
	 * @see Worker#isRunning()
	 * @see Worker#isSuspended()
	 */
	public void resume()
	{
		synchronized(this)
		{
			if(!this.running)
				throw new IllegalStateException("Worker not running!");
			this.suspended = false;
			this.notify();
		}
	}

	/**
	 * Perform the current execution for this worker.<br>
	 * This function is called periodically with the given interval.
	 * 
	 * @param executionId - the ID of the current execution
	 * @throws Throwable
	 */
	public abstract void work(long executionId) throws Throwable;
}
