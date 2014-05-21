package com.syncnapsis.utils.math;

import java.util.Random;

/**
 * Class for creating distributions based on probabilities
 * 
 * @author ultimate
 */
public abstract class Distribution
{
	/**
	 * The default random number generator
	 */
	protected static final Random	random		= new Random();

	/**
	 * The precision for inverse calculations
	 */
	private static final int		PRECISION	= 1000000;
	/**
	 * The sum array used for inverse calculations
	 */
	private double[]				sum			= null;

	/**
	 * Return the relative probability of a number x.<br>
	 * This means probabilities do not have to be absolute and may be close to 1 or even bigger, but
	 * it must be guaranteed that if <code>prob(x) = 2 * prob(y)</code> thatn x occurs twice as
	 * often as y.
	 * 
	 * @param x - the number whichs probability to get
	 * @return the probability
	 */
	public abstract double probability(double x);

	/**
	 * Return a new random number for this distribution
	 * 
	 * @param random - the random number generator to use
	 * @return a random number
	 */
	public abstract double next(Random random);

	/**
	 * Return a new random number for this distribution using a default random number generator
	 * 
	 * @return a random number
	 */
	public double next()
	{
		return next(random);
	}

	/**
	 * Inverse find the corresponding value for the given probability sum being obtained from a
	 * uniformly distributed random function. This way we can reproduce the distribution defined
	 * by {@link Distribution#probability(double)} from a uniform distribution.
	 * 
	 * @param sum - the sum to invert to a random value according to this distribution
	 * @return the random value according to this distribution
	 */
	protected double invert(double sum)
	{
		double eps = 1 / (double) PRECISION;

		if(this.sum == null)
		{
			this.sum = new double[PRECISION + 1];
			for(int i = 1; i <= PRECISION; i++)
				this.sum[i] = this.sum[i - 1] + probability(i * eps - eps / 2) * eps;
		}

		int i = PRECISION / 2;
		int step = i;
		do
		{
			step = (step + 1) / 2;

			if(this.sum[i] < sum)
				i += step;
			else if(this.sum[i - 1] > sum)
				i -= step;
			else
				break;
			if(step == 1)
				break;
		} while(true);

		return i * eps - eps / 2;
	}

	/**
	 * A uniform distribution
	 */
	public static final Distribution	UNIFORM		= new Distribution() {
														/*
														 * (non-Javadoc)
														 * @see com.syncnapsis.utils.math.
														 * Distribution#probability(double)
														 */
														@Override
														public double probability(double x)
														{
															if(x >= -1 && x < 1)
																return 1;
															else
																return 0;
														}

														/*
														 * (non-Javadoc)
														 * @see com.syncnapsis.utils.math.
														 * Distribution
														 * #next(java.util.Random)
														 */
														@Override
														public double next(Random random)
														{
															return random.nextDouble() * 2 - 1;
														}
													};

	/**
	 * A gaussian distribution
	 */
	public static final Distribution	GAUSSIAN	= new Distribution() {
														/*
														 * (non-Javadoc)
														 * @see com.syncnapsis.utils.math.
														 * Distribution#probability(double)
														 */
														@Override
														public double probability(double x)
														{
															return Statistics.gaussian(x, 0, 1);
														}

														/*
														 * (non-Javadoc)
														 * @see com.syncnapsis.utils.math.
														 * Distribution
														 * #next(java.util.Random)
														 */
														@Override
														public double next(Random random)
														{
															return random.nextGaussian();
														}
													};

	/**
	 * Base for polynomial distributions
	 * 
	 * @author ultimaet
	 */
	private static final class Polynomial extends Distribution
	{
		/**
		 * The exponent
		 */
		private int				exponent;
		/**
		 * Increasing or decreasing mode?
		 */
		private PolynomialMode	mode;
		/**
		 * Invert the probability curve?
		 */
		private boolean			inverted;

		/**
		 * Constructor
		 * 
		 * @param exponent - The exponent
		 * @param mode - Increasing or decreasing mode?
		 */
		public Polynomial(int exponent, PolynomialMode mode, boolean inverted)
		{
			super();
			this.exponent = exponent;
			this.mode = mode;
			this.inverted = inverted;
		}

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.utils.math.Distribution#probability(double)
		 */
		@Override
		public double probability(double x)
		{
			x = Math.abs(x);
			double ret = 0;
			if(x >= 0 && x < 1)
			{
				if(mode == PolynomialMode.INCREASING)
					ret = Math.pow(x, exponent);
				else if(mode == PolynomialMode.DECREASING)
					ret = Math.pow(1 - x, exponent);

				if(inverted)
					ret = 1 - ret;
			}
			return ret;
		}

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.utils.math.Distribution#next(java.util.Random)
		 */
		@Override
		public double next(Random random)
		{
			double d = UNIFORM.next(random);
			double sign = Math.signum(d);
			d = Math.abs(d);

			double ret = 0;

			if(!inverted || exponent == 1)
			{
				if(mode == PolynomialMode.INCREASING)
				{
					ret = sign * Math.pow(d, 1 / (double) (exponent + 1));
				}
				else if(mode == PolynomialMode.DECREASING)
				{
					ret = sign * (1 - Math.pow(d, 1 / (double) (exponent + 1)));
				}
			}
			else
			{
				d *= exponent / (double) (exponent + 1);
				ret = sign * invert(d);
			}
			return ret;
		}
	}

	private enum PolynomialMode
	{
		INCREASING, DECREASING
	}

	/**
	 * A linear distribution (increasing)
	 */
	public static final Distribution	LINEAR_INCREASING			= new Polynomial(1, PolynomialMode.INCREASING, false);
	/**
	 * A linear distribution (decreasing)
	 */
	public static final Distribution	LINEAR_DECREASING			= new Polynomial(1, PolynomialMode.DECREASING, false);
	/**
	 * A square distribution (increasing)
	 */
	public static final Distribution	SQUARE_INCREASING			= new Polynomial(2, PolynomialMode.INCREASING, false);
	/**
	 * A square distribution (decreasing)
	 */
	public static final Distribution	SQUARE_DECREASING			= new Polynomial(2, PolynomialMode.DECREASING, false);
	/**
	 * A cubic distribution (increasing)
	 */
	public static final Distribution	CUBIC_INCREASING			= new Polynomial(3, PolynomialMode.INCREASING, false);
	/**
	 * A cubic distribution (decreasing)
	 */
	public static final Distribution	CUBIC_DECREASING			= new Polynomial(3, PolynomialMode.DECREASING, false);

	// inverted modes

	/**
	 * A square distribution (increasing, inverted)
	 */
	public static final Distribution	SQUARE_INCREASING_INVERTED	= new Polynomial(2, PolynomialMode.INCREASING, true);
	/**
	 * A square distribution (decreasing, inverted)
	 */
	public static final Distribution	SQUARE_DECREASING_INVERTED	= new Polynomial(2, PolynomialMode.DECREASING, true);
	/**
	 * A cubic distribution (increasing, inverted)
	 */
	public static final Distribution	CUBIC_INCREASING_INVERTED	= new Polynomial(3, PolynomialMode.INCREASING, true);
	/**
	 * A cubic distribution (decreasing, inverted)
	 */
	public static final Distribution	CUBIC_DECREASING_INVERTED	= new Polynomial(3, PolynomialMode.DECREASING, true);
}
