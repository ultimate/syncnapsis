package com.syncnapsis.utils.math;

import javax.vecmath.Matrix3d;
import javax.vecmath.Tuple2d;
import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;

public class Statistics
{
	/**
	 * Calculate the binomial coefficient for<br>
	 * <code>
	 * /n\        n!<br>
	 * | | = -----------<br>
	 * \k/   k! * (n-k)!<br>
	 * </code>
	 * 
	 * @param n
	 * @param k
	 * @return the binomial coefficient
	 */
	public static int binomial(int n, int k)
	{
		if(k > n || k < 0)
			throw new IllegalArgumentException("k out of range of n");
		long result = 1;
		int nk = n - k;
		while(n > k)
			result *= n--;
		while(nk > 0)
			result /= nk--;
		return (int) result;
	}

	/**
	 * Calculate the gaussian value for the given parameters
	 * 
	 * @param x - the value
	 * @param mu - the mean (center of the curve)
	 * @param sigma - the deviation (width of the curve)
	 * @return the gaussian value
	 */
	public static double gaussian(double x, double mu, double sigma)
	{
		return 1.0 / Math.sqrt(2 * Math.PI) / sigma * Math.exp(-(x - mu) * (x - mu) / 2.0 / sigma / sigma);
	}

	/**
	 * Calculate the gaussian value for the given parameters
	 * 
	 * @param x - the value
	 * @param mu - the mean (center of the curve)
	 * @param sigma - the deviation (width of the curve)
	 * @return the gaussian value
	 */
	public static double gaussian(Tuple2d x, Tuple2d mu, Tuple2d sigma, double rho)
	{
		double x1 = x.x - mu.x;
		double x2 = x.y - mu.y;
		double s1 = sigma.x;
		double s2 = sigma.y;
		double quot = 1 - rho * rho;
		return 1.0 / (2 * Math.PI * s1 * s2 * Math.sqrt(quot))
				* Math.exp(-((x1 * x1) / (s1 * s1) + (x2 * x2) / (s2 * s2) - (2 * rho * x1 * x2) / (s1 * s2)) / (2 * quot));
	}

	/**
	 * Calculate the gaussian value for the given parameters
	 * 
	 * @param x - the value
	 * @param mu - the mean (center of the curve)
	 * @param sigma - the deviation (width of the curve)
	 * @return the gaussian value
	 */
	public static double gaussian(Tuple3d x, Tuple3d mu, Matrix3d sigma)
	{
		Vector3d d = new Vector3d(x);
		d.sub(mu);
		Vector3d d2 = (Vector3d) d.clone();
		
		Matrix3d sigma_1 = (Matrix3d) sigma.clone();
		sigma_1.invert();
		sigma_1.transform(d);
		
		return 1.0 / (Math.pow(2 * Math.PI, 3 / 2) * Math.sqrt(sigma.determinant())) * Math.exp(-0.5 * d.dot(d2));
	}
}
