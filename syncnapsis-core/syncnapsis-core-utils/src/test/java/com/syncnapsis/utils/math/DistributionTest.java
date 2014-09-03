/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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
package com.syncnapsis.utils.math;

import com.syncnapsis.tests.LoggerTestCase;
import com.syncnapsis.tests.annotations.TestCoversMethods;
import com.syncnapsis.tests.annotations.TestExcludesMethods;

@TestExcludesMethods("invert")
public class DistributionTest extends LoggerTestCase
{
	private void distributionTest(Distribution distribution)
	{
		int steps = 20;
		int drawScale = 200;
		int samples = 1000000;
		double prob, sample;
		int[] sampleCount = new int[steps * 4 + 1];
		double[] probs = new double[sampleCount.length];
		double[] ratios = new double[sampleCount.length];
		int index;
		int skipped = 0;

		StringBuffer buffer;

		logger.info("Probabilities:");
		for(int i = -steps; i <= steps; i++)
		{
			prob = distribution.probability(i / (double) steps);
			probs[i + 2 * steps] = prob;

			buffer = new StringBuffer();
			buffer.append(i / (double) steps + "\t" + prob + "\t");
			for(int j = 0; j < drawScale; j++)
			{
				if(j / (double) drawScale < prob)
					buffer.append('x');
			}
			logger.debug(buffer.toString());
		}

		logger.info("Values:");
		long start = System.currentTimeMillis();
		for(int i = 0; i < samples; i++)
		{
			sample = distribution.next();
			index = (int) Math.round(sample * steps) + 2 * steps;
			if(index < 0 || index >= sampleCount.length)
			{
				skipped++;
				continue;
			}
			sampleCount[index]++;
		}
		long end = System.currentTimeMillis();
		logger.info("time needed: " + (end - start) + " / skipped: " + skipped);

		double x;
		for(int i = 0; i < sampleCount.length; i++)
		{
			ratios[i] = sampleCount[i] / probs[i];
			x = (i - 2 * steps) / (double) steps;

			buffer = new StringBuffer();
			buffer.append(x + "\t" + ratios[i] + "\t");

			for(int j = 0; j < sampleCount[i]; j += 200)
			{
				buffer.append('x');
			}

			logger.debug(buffer.toString());
		}

		double avgRatio = 0;
		int ratiosCounted = 0;
		for(int i = steps + 1; i < sampleCount.length - steps; i++)
		{
			if(Double.isInfinite(ratios[i]) || Double.isNaN(ratios[i]))
				continue;

			avgRatio += ratios[i];
			ratiosCounted++;
		}
		avgRatio /= ratiosCounted;
		double ratioDelta = avgRatio * 0.10; // 10 % delta is allowed!

		for(int i = steps + 1; i < sampleCount.length - steps; i++)
		{
			x = (i - 2 * steps) / (double) steps;

			if(Double.isInfinite(ratios[i]) || Double.isNaN(ratios[i]) || sampleCount[i] < 1000)
				// sampleCount < 1000 has too large rounding error 
				continue;
			
			assertEquals("ratio delta too large: x=" + x + " avg=" + avgRatio + " ratio=" + ratios[i], avgRatio, ratios[i], ratioDelta);
		}
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_UNIFORM() throws Exception
	{
		logger.info("UNIFORM");
		distributionTest(Distribution.UNIFORM);
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_GAUSSIAN() throws Exception
	{
		logger.info("GAUSSIAN");
		distributionTest(Distribution.GAUSSIAN);
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_LINEAR_INCREASING() throws Exception
	{
		logger.info("LINEAR_INCREASING");
		distributionTest(Distribution.LINEAR_INCREASING);
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_LINEAR_DECREASING() throws Exception
	{
		logger.info("LINEAR_DECREASING");
		distributionTest(Distribution.LINEAR_DECREASING);
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_SQUARE_INCREASING() throws Exception
	{
		logger.info("SQUARE_INCREASING");
		distributionTest(Distribution.SQUARE_INCREASING);
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_SQUARE_DECREASING() throws Exception
	{
		logger.info("SQUARE_DECREASING");
		distributionTest(Distribution.SQUARE_DECREASING);
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_CUBIC_INCREASING() throws Exception
	{
		logger.info("CUBIC_INCREASING");
		distributionTest(Distribution.CUBIC_INCREASING);
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_CUBIC_DECREASING() throws Exception
	{
		logger.info("CUBIC_DECREASING");
		distributionTest(Distribution.CUBIC_DECREASING);
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_SQUARE_INCREASING_INVERTED() throws Exception
	{
		logger.info("SQUARE_INCREASING_INVERTED");
		distributionTest(Distribution.SQUARE_INCREASING_INVERTED);
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_SQUARE_DECREASING_INVERTED() throws Exception
	{
		logger.info("SQUARE_DECREASING_INVERTED");
		distributionTest(Distribution.SQUARE_DECREASING_INVERTED);
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_CUBIC_INCREASING_INVERTED() throws Exception
	{
		logger.info("CUBIC_INCREASING_INVERTED");
		distributionTest(Distribution.CUBIC_INCREASING_INVERTED);
	}

	@TestCoversMethods({ "probability", "next" })
	public void test_CUBIC_DECREASING_INVERTED() throws Exception
	{
		logger.info("CUBIC_DECREASING_INVERTED");
		distributionTest(Distribution.CUBIC_DECREASING_INVERTED);
	}
}
