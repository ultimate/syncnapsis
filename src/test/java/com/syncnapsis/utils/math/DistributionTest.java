package com.syncnapsis.utils.math;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;

public class DistributionTest extends LoggerTestCase
{
	private void distributionTest(Distribution distribution)
	{
		int steps = 20;
		int drawScale = 200;
		int samples = 1000000;
		double prop, sample;
		int[] sampleCount = new int[steps * 4 + 1];
		int index;
		int skipped = 0;

		logger.debug("Probabilities:");
		for(int i = -steps; i <= steps; i++)
		{
			prop = distribution.probability(i / (double) steps);
			
			System.out.print(i / (double) steps + "\t");
			for(int j = 0; j < drawScale; j++)
			{
				if(j / (double) drawScale < prop)
					System.out.print("x");
			}
			System.out.println("");
		}

		logger.debug("Values:");
		for(int i = 0; i < samples; i++)
		{
			sample = distribution.next();
			index = (int) Math.round(sample * steps) + 2*steps;
			if(index < 0 || index >= sampleCount.length)
			{
				skipped++;
//				logger.debug("skipping value: " + sample);
				continue;
			}
			sampleCount[index]++;
		}
		logger.debug("(skipped = " + skipped + ")");

		for(int i = 0; i < sampleCount.length; i++)
		{
			System.out.print((i - 2*steps) / (double) steps + "\t");
			for(int j = 0; j < sampleCount[i]; j += 200)
			{
				System.out.print("x");
			}
			System.out.println("");
		}
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_UNIFORM() throws Exception
	{
		logger.debug("UNIFORM");
		distributionTest(Distribution.UNIFORM);
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_GAUSSIAN() throws Exception
	{
		logger.debug("GAUSSIAN");
		distributionTest(Distribution.GAUSSIAN);
	}
	
	@TestCoversMethods({ "probability", "next" })
	public void test_LINEAR_INCREASING() throws Exception
	{
		logger.debug("LINEAR_INCREASING");
		distributionTest(Distribution.LINEAR_INCREASING);
	}
	
	@TestCoversMethods({ "probability", "next" })
	public void test_LINEAR_DECREASING() throws Exception
	{
		logger.debug("LINEAR_DECREASING");
		distributionTest(Distribution.LINEAR_DECREASING);
	}
	
	@TestCoversMethods({ "probability", "next" })
	public void test_SQUARE_INCREASING() throws Exception
	{
		logger.debug("SQUARE_INCREASING");
		distributionTest(Distribution.SQUARE_INCREASING);
	}
	
	@TestCoversMethods({ "probability", "next" })
	public void test_SQUARE_DECREASING() throws Exception
	{
		logger.debug("SQUARE_DECREASING");
		distributionTest(Distribution.SQUARE_DECREASING);
	}
	
	@TestCoversMethods({ "probability", "next" })
	public void test_CUBIC_INCREASING() throws Exception
	{
		logger.debug("CUBIC_INCREASING");
		distributionTest(Distribution.CUBIC_INCREASING);
	}
	
	@TestCoversMethods({ "probability", "next" })
	public void test_CUBIC_DECREASING() throws Exception
	{
		logger.debug("CUBIC_DECREASING");
		distributionTest(Distribution.CUBIC_DECREASING);
	}
	
	@TestCoversMethods({ "probability", "next" })
	public void test_SQUARE_INCREASING_INVERTED() throws Exception
	{
		logger.debug("SQUARE_INCREASING_INVERTED");
		distributionTest(Distribution.SQUARE_INCREASING_INVERTED);
	}
	
	@TestCoversMethods({ "probability", "next" })
	public void test_SQUARE_DECREASING_INVERTED() throws Exception
	{
		logger.debug("SQUARE_DECREASING_INVERTED");
		distributionTest(Distribution.SQUARE_DECREASING_INVERTED);
	}
	
	@TestCoversMethods({ "probability", "next" })
	public void test_CUBIC_INCREASING_INVERTED() throws Exception
	{
		logger.debug("CUBIC_INCREASING_INVERTED");
		distributionTest(Distribution.CUBIC_INCREASING_INVERTED);
	}
	
	@TestCoversMethods({ "probability", "next" })
	public void test_CUBIC_DECREASING_INVERTED() throws Exception
	{
		logger.debug("CUBIC_DECREASING_INVERTED");
		distributionTest(Distribution.CUBIC_DECREASING_INVERTED);
	}
}
