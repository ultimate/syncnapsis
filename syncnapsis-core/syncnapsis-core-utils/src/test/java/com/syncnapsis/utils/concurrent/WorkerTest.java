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

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

/**
 * @author ultimate
 */
@TestExcludesMethods({ "get*", "set*" })
public class WorkerTest extends LoggerTestCase
{
	private static final long	INTERVAL	= 100;

	@TestCoversMethods({ "start", "stop", "suspend", "resume", "work", "run", "is*" })
	public void testLifecycle() throws Exception
	{
		long time;
		long id;
		TestWorker worker = new TestWorker();
		assertNotNull(worker.getTimeProvider());

		assertFalse(worker.isRunning());
		assertFalse(worker.isSuspended());
		assertFalse(worker.hasError());
		assertFalse(worker.hasWarning());
		assertEquals(-1, worker.lastExecutionId);
		assertEquals(-1, worker.lastExecutionTime);

		for(int i = 0; i < 2; i++)
		{
			time = worker.getTimeProvider().get();
			logger.debug("starting...");
			worker.start();

			Thread.sleep(INTERVAL / 10);

			assertTrue(worker.isRunning());
			assertFalse(worker.isSuspended());
			assertFalse(worker.hasError());
			assertFalse(worker.hasWarning());
			assertEquals(0, worker.lastExecutionId);
			assertTrue(worker.lastExecutionTime >= time);

			time = worker.getTimeProvider().get();
			Thread.sleep((long) (INTERVAL * 3.5));

			assertTrue(worker.isRunning());
			assertFalse(worker.isSuspended());
			assertFalse(worker.hasError());
			assertFalse(worker.hasWarning());
			assertEquals(3, worker.lastExecutionId);
			assertTrue(worker.lastExecutionTime >= time);

			id = worker.lastExecutionId;
			time = worker.lastExecutionTime;
			logger.debug("suspending...");
			worker.suspend();
			Thread.sleep((long) (INTERVAL * 3.5));

			assertTrue(worker.isRunning());
			assertTrue(worker.isSuspended());
			assertFalse(worker.hasError());
			assertFalse(worker.hasWarning());
			assertEquals(id, worker.lastExecutionId);
			assertEquals(time, worker.lastExecutionTime);

			time = worker.lastExecutionTime;
			logger.debug("resuming...");
			worker.resume();
			Thread.sleep((long) (INTERVAL * 2.5));

			assertTrue(worker.isRunning());
			assertFalse(worker.isSuspended());
			assertFalse(worker.hasError());
			assertFalse(worker.hasWarning());
			assertEquals(6, worker.lastExecutionId);
			assertTrue(worker.lastExecutionTime >= time);

			id = worker.lastExecutionId;
			time = worker.lastExecutionTime;
			logger.debug("stopping...");
			worker.stop();

			assertFalse(worker.isRunning());
			assertFalse(worker.isSuspended());
			assertFalse(worker.hasError());
			assertFalse(worker.hasWarning());
			assertEquals(id, worker.lastExecutionId);
			assertEquals(time, worker.lastExecutionTime);

			// restart by repeating the whole test with the same worker
		}
	}

	@TestCoversMethods({ "hasWarning", "clearWarnings", "getWarningHistoryQueue", "getWarningQueue" })
	public void testOverload() throws Exception
	{
		long time;
		TestWorker worker = new TestWorker();
		assertNotNull(worker.getTimeProvider());

		assertFalse(worker.isRunning());
		assertFalse(worker.isSuspended());
		assertFalse(worker.hasError());
		assertFalse(worker.hasWarning());
		assertEquals(-1, worker.lastExecutionId);
		assertEquals(-1, worker.lastExecutionTime);

		worker.wait = (long) (INTERVAL * 1.5);

		time = worker.getTimeProvider().get();
		worker.start();

		Thread.sleep(INTERVAL / 10);

		assertTrue(worker.isRunning());
		assertFalse(worker.isSuspended());
		assertFalse(worker.hasError());
		assertFalse(worker.hasWarning());
		assertEquals(0, worker.lastExecutionId);
		assertTrue(worker.lastExecutionTime >= time);

		time = worker.lastExecutionTime;
		Thread.sleep((long) (INTERVAL * 3.5));

		worker.stop();

		assertFalse(worker.isRunning());
		assertFalse(worker.isSuspended());
		assertFalse(worker.hasError());
		assertTrue(worker.hasWarning());
		assertNotNull(worker.getWarningHistoryQueue().peek());
		assertTrue(worker.getWarningHistoryQueue().peek().getExecutionId() < 3);
		assertTrue(worker.lastExecutionId < 3);
		assertTrue(worker.lastExecutionTime >= time);
	}

	@TestCoversMethods({ "hasError", "clearErrors", "getErrorHistoryQueue", "getErrorQueue" })
	public void testErrorHandling() throws Exception
	{
		long time;
		TestWorker worker = new TestWorker();
		assertNotNull(worker.getTimeProvider());

		assertFalse(worker.isRunning());
		assertFalse(worker.isSuspended());
		assertFalse(worker.hasError());
		assertFalse(worker.hasWarning());
		assertEquals(-1, worker.lastExecutionId);
		assertEquals(-1, worker.lastExecutionTime);

		worker.throwable = new Throwable("test");

		time = worker.getTimeProvider().get();
		worker.start();

		Thread.sleep(INTERVAL);

		worker.stop();

		assertFalse(worker.isRunning());
		assertFalse(worker.isSuspended());
		assertTrue(worker.hasError());
		assertFalse(worker.hasWarning());
		assertSame(worker.throwable, worker.getErrorHistoryQueue().peek().getCause());
		assertTrue(worker.getErrorHistoryQueue().peek().getExecutionId() < 3);
		assertTrue(worker.lastExecutionId < 3);
		assertTrue(worker.lastExecutionTime >= time);
	}

	private class TestWorker extends Worker
	{
		public long			lastExecutionTime	= -1;
		public long			lastExecutionId		= -1;
		public long			wait;
		public Throwable	throwable;

		public TestWorker()
		{
			super(INTERVAL);
		}

		@Override
		public void work(long executionId) throws Throwable
		{
			this.lastExecutionId = executionId;
			this.lastExecutionTime = System.currentTimeMillis();
			logger.info("execution #" + executionId + " @ time " + lastExecutionTime);
			if(wait > 0)
			{
				logger.info("waiting " + wait + "ms");
				try
				{
					Thread.sleep(wait);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			if(throwable != null)
				throw throwable;
		}
	}

	@TestCoversMethods("join")
	public void testEndingSupport() throws Exception
	{
		long time;
		EndingWorker worker = new EndingWorker();
		assertNotNull(worker.getTimeProvider());

		assertFalse(worker.isRunning());
		assertFalse(worker.isSuspended());
		assertFalse(worker.hasError());
		assertFalse(worker.hasWarning());
		assertEquals(-1, worker.lastExecutionId);
		assertEquals(-1, worker.lastExecutionTime);

		time = worker.getTimeProvider().get();
		worker.start();

		Thread.sleep(INTERVAL);

		worker.join();

		assertFalse(worker.isRunning());
		assertFalse(worker.isSuspended());
		assertFalse(worker.hasError());
		assertFalse(worker.hasWarning());
		assertTrue(worker.lastExecutionId == 10);
		assertTrue(worker.lastExecutionTime >= time);
	}

	private class EndingWorker extends TestWorker
	{
		@Override
		public void work(long executionId) throws Throwable
		{
			super.work(executionId);
			if(executionId == 10)
				this.stop(false);
		}
	}
}
