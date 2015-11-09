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

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.providers.TimeProvider;
import com.syncnapsis.providers.impl.SystemTimeProvider;
import com.syncnapsis.utils.MBeanUtil;
import com.syncnapsis.utils.collections.LimitedQueue;

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
	 * A history of errors that have occurred (with limited length).
	 */
	private LimitedQueue<HistoryEntry>	errorHistory;
	/**
	 * A history of warnings that have occurred (with limited length).
	 */
	private LimitedQueue<HistoryEntry>	warningHistory;

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
		
		this.errorHistory = new LimitedQueue<Worker.HistoryEntry>(10);
		this.warningHistory = new LimitedQueue<Worker.HistoryEntry>(10);

		MBeanUtil.registerMBean(this);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#getInterval()
	 */
	@Override
	public long getInterval()
	{
		return interval;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#setInterval(long)
	 */
	@Override
	public void setInterval(long interval)
	{
		this.interval = interval;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#isRunning()
	 */
	@Override
	public boolean isRunning()
	{
		return running;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#isSuspended()
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#hasError()
	 */
	@Override
	public boolean hasError()
	{
		return !errorHistory.isEmpty();
	}

	/**
	 * A history of errors that have occurred (with limited length).
	 * 
	 * @return errorHistory as a {@link Queue}
	 */
	protected Queue<HistoryEntry> getErrorHistoryQueue()
	{
		return errorHistory;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#getErrorHistory()
	 */
	@Override
	public Map<Long, String> getErrorHistory()
	{
		Map<Long, String> errors = new HashMap<Long, String>();
		for(HistoryEntry e : this.errorHistory)
			errors.put(e.getExecutionId(), e.getCause().getClass().getName() + ": " + e.getCause().getMessage());
		return errors;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#clearErrors()
	 */
	@Override
	public void clearErrors()
	{
		synchronized(this)
		{
			this.errorHistory.clear();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#hasWarning()
	 */
	@Override
	public boolean hasWarning()
	{
		return !warningHistory.isEmpty();
	}

	/**
	 * A history of warnings that have occurred (with limited length).
	 * 
	 * @return warningHistory as a {@link Queue}
	 */
	protected Queue<HistoryEntry> getWarningHistoryQueue()
	{
		return warningHistory;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#getWarningHistory()
	 */
	@Override
	public Map<Long, String> getWarningHistory()
	{
		Map<Long, String> warnings = new HashMap<Long, String>();
		for(HistoryEntry e : this.warningHistory)
			warnings.put(e.getExecutionId(), e.getCause().getClass().getName() + ": " + e.getCause().getMessage());
		return warnings;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#clearWarnings()
	 */
	@Override
	public void clearWarnings()
	{
		synchronized(this)
		{
			this.warningHistory.clear();
		}
	}

	/**
	 * Internal class for storing errors or warnings in the history queues.
	 * 
	 * @author ultimate
	 */
	protected class HistoryEntry
	{
		/**
		 * The ID of the execution the error/warning occurred in.
		 */
		private long		executionId;
		/**
		 * The error/warning that occurred.
		 */
		private Throwable	cause;

		/**
		 * Standard Constructor
		 * 
		 * @param executionId - The ID of the execution the error/warning occurred in
		 * @param cause - The error/warning that occurred
		 */
		public HistoryEntry(long executionId, Throwable cause)
		{
			super();
			this.executionId = executionId;
			this.cause = cause;
		}

		/**
		 * The ID of the execution the error/warning occurred in.
		 * 
		 * @return executionId
		 */
		public long getExecutionId()
		{
			return executionId;
		}

		/**
		 * The error/warning that occurred.
		 * 
		 * @return cause
		 */
		public Throwable getCause()
		{
			return cause;
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
						this.errorHistory.add(new HistoryEntry(executionCount, e));
						logger.error("execution #" + executionCount + ": suspended state interrupted!", e);
					}
				}

				if(!isRunning())
					break;
			}

			begin = timeProvider.get();
			try
			{
				this.work(executionCount);
			}
			catch(Throwable t)
			{
				synchronized(this)
				{
					this.errorHistory.add(new HistoryEntry(executionCount, t));
					logger.error("execution #" + executionCount + ": error while executing work!", t);
				}
			}
			duration = timeProvider.get() - begin;

			if(logger.isDebugEnabled())
				logger.debug("execution #" + executionCount + ": load=" + (duration * 100.0 / (double) interval) + "% (" + duration + "ms of " + interval + "ms)");

			if(duration <= interval)
			{
				try
				{
					Thread.sleep(interval - duration);
				}
				catch(InterruptedException e)
				{
					synchronized(this)
					{
						this.errorHistory.add(new HistoryEntry(executionCount, e));
						logger.error("execution #" + executionCount + ": could not sleep for interval!", e);
					}
				}
			}
			else if(interval != 0)
			{
				synchronized(this)
				{
					Throwable t = new Throwable("can't keep up interval of" + interval + "ms with execution time: " + duration + "ms");
					this.warningHistory.add(new HistoryEntry(executionCount, t));
					logger.warn("execution #" + executionCount + ": " + t.getMessage());
				}
			}

			this.executionCount++;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#start()
	 */
	@Override
	public void start()
	{
		this.start(false);
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#start(boolean)
	 */
	@Override
	public void start(boolean suspended)
	{
		synchronized(this)
		{
			if(this.running)
				throw new IllegalStateException("Worker already started!");
			this.running = true;
			this.suspended = suspended;
			this.clearErrors();
			this.clearWarnings();
			this.thread = new Thread(this);
			this.thread.start();
			logger.info("Worker started!");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#stop()
	 */
	@Override
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
			logger.info("Worker stopped!");
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
			logger.info("Worker suspended!");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.concurrent.WorkerMXBean#resume()
	 */
	@Override
	public void resume()
	{
		synchronized(this)
		{
			if(!this.running)
				throw new IllegalStateException("Worker not running!");
			this.suspended = false;
			logger.info("Worker resumed!");
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
