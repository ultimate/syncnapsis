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
	protected static final Random	random	= new Random();

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
		private double			exponent;
		/**
		 * Increasing or decreasing mode?
		 */
		private PolynomialMode	mode;

		/**
		 * Constructor
		 * 
		 * @param exponent - The exponent
		 * @param mode - Increasing or decreasing mode?
		 */
		public Polynomial(double exponent, PolynomialMode mode)
		{
			super();
			this.exponent = exponent;
			this.mode = mode;
		}

		/*
		 * (non-Javadoc)
		 * @see com.syncnapsis.utils.math.Distribution#probability(double)
		 */
		@Override
		public double probability(double x)
		{
			x = Math.abs(x);
			if(mode == PolynomialMode.INCREASING)
			{
				if(x >= 0 && x < 1)
					return Math.pow(x, exponent);
				else
					return 0;
			}
			else if(mode == PolynomialMode.DECREASING)
			{
				if(x >= 0 && x < 1)
					return Math.pow(1 - x, exponent);
				else
					return 0;
			}
			else if(mode == PolynomialMode.INCREASING_INVERTED)
			{
				if(x >= 0 && x < 1)
					return 1 - Math.pow(1 - x, exponent);
				else
					return 0;
			}
			else if(mode == PolynomialMode.DECREASING_INVERTED)
			{
				if(x >= 0 && x < 1)
					return 1 - Math.pow(x, exponent);
				else
					return 0;
			}
			else
			{
				return 0;
			}
		}

		@Override
		public double next(Random random)
		{
			double d = UNIFORM.next(random);
			double ret;
			if(mode == PolynomialMode.INCREASING)
			{
				ret = Math.signum(d) * Math.pow(Math.abs(d), 1 / (exponent + 1));
			}
			else if(mode == PolynomialMode.DECREASING)
			{
				ret = Math.signum(d) * (1 - Math.pow(Math.abs(d), 1 / (exponent + 1)));
			}
			else if(mode == PolynomialMode.INCREASING_INVERTED)
			{
				ret = Math.signum(d) * (Math.abs(d) + Math.pow(Math.abs(d), 1 / (exponent + 1))) / 2;
			}
			else if(mode == PolynomialMode.DECREASING_INVERTED)
			{
				ret = Math.signum(d) * (1 - (Math.abs(d) + Math.pow(Math.abs(d), 1 / (exponent + 1))) / 2);
			}
			else
			{
				ret = 0;
			}
			return ret;
		}
	}

	private enum PolynomialMode
	{
		INCREASING, DECREASING, INCREASING_INVERTED, DECREASING_INVERTED
	}

	/**
	 * A linear distribution (increasing)
	 */
	public static final Distribution	LINEAR_INCREASING			= new Polynomial(1, PolynomialMode.INCREASING);
	/**
	 * A linear distribution (decreasing)
	 */
	public static final Distribution	LINEAR_DECREASING			= new Polynomial(1, PolynomialMode.DECREASING);
	/**
	 * A square distribution (increasing)
	 */
	public static final Distribution	SQUARE_INCREASING			= new Polynomial(2, PolynomialMode.INCREASING);
	/**
	 * A square distribution (decreasing)
	 */
	public static final Distribution	SQUARE_DECREASING			= new Polynomial(2, PolynomialMode.DECREASING);
	/**
	 * A cubic distribution (increasing)
	 */
	public static final Distribution	CUBIC_INCREASING			= new Polynomial(3, PolynomialMode.INCREASING);
	/**
	 * A cubic distribution (decreasing)
	 */
	public static final Distribution	CUBIC_DECREASING			= new Polynomial(3, PolynomialMode.DECREASING);

	// inverted modes

	/**
	 * A square distribution (increasing, inverted)<br>
	 * <b>*** buggy ***</b>
	 */
	public static final Distribution	SQUARE_INCREASING_INVERTED	= new Polynomial(2, PolynomialMode.INCREASING_INVERTED);
	/**
	 * A square distribution (decreasing, inverted)<br>
	 * <b>*** buggy ***</b>
	 */
	public static final Distribution	SQUARE_DECREASING_INVERTED	= new Polynomial(2, PolynomialMode.DECREASING_INVERTED);
	/**
	 * A cubic distribution (increasing, inverted)<br>
	 * <b>*** buggy ***</b>
	 */
	public static final Distribution	CUBIC_INCREASING_INVERTED	= new Polynomial(3, PolynomialMode.INCREASING_INVERTED);
	/**
	 * A cubic distribution (decreasing, inverted)<br>
	 * <b>*** buggy ***</b>
	 */
	public static final Distribution	CUBIC_DECREASING_INVERTED	= new Polynomial(3, PolynomialMode.DECREASING_INVERTED);
}
