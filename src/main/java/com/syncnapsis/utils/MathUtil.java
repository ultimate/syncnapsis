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
package com.syncnapsis.utils;

import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.syncnapsis.utils.math.BezierCurve;

/**
 * Util-Klasse für die Berechnung mathematischer Formeln und Funktionen
 * 
 * @author ultimate
 */
public abstract class MathUtil
{
	/**
	 * Normale Schrittgröße für die Approximation einer Bezierkurve
	 */
	private static double	stepSize		= 0.05;
	/**
	 * Feinere Schrittgröße für die Approximation einer Bezierkurve
	 */
	private static double	stepSizeSmall	= 0.005;

	/**
	 * Berechnung des Abstands zweier Punkte (2D)
	 * 
	 * @param x1 - die x-Koordinate des 1. Punkts
	 * @param y1 - die y-Koordinate des 1. Punkts
	 * @param x2 - die x-Koordinate des 2. Punkts
	 * @param y2 - die y-Koordinate des 2. Punkts
	 * @return der Abstand
	 */
	public static double distance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	/**
	 * Berechnung des Abstands zweier Punkte (3D)
	 * 
	 * @param x1 - die x-Koordinate des 1. Punkts
	 * @param y1 - die y-Koordinate des 1. Punkts
	 * @param z2 - die z-Koordinate des 1. Punkts
	 * @param x2 - die x-Koordinate des 2. Punkts
	 * @param y2 - die y-Koordinate des 2. Punkts
	 * @param z2 - die z-Koordinate des 2. Punkts
	 * @return der Abstand
	 */
	public static double distance(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1));
	}

	/**
	 * Berechnung des Abstands eines Punktes p zu einer Linie. Die Linie wird durch die Punkte p1
	 * und p2 definiert. Über das Array nearest kann der nächste Punkt n zum Punkt p auf der Geraden
	 * zurückgegeben werden (Schnittpunkt aus Lot und Linie). Der zurückgegeben Abstand entspricht
	 * dem Abstand des Punkte p zum Punkt n. Bei der Berechnung des Abstands wird dabei die Linie
	 * als endlich betrachtet. Liegt der Schnittpunkt des Lotes mit der Linie außerhalb der Linie,
	 * so ist einer der Endpunkt der nächste Punkt n zu p.
	 * 
	 * @param x - die x-Koordinate des zu untersuchenden Punkts
	 * @param y - die y-Koordinate des zu untersuchenden Punkts
	 * @param x1 - die x-Koordinate des Startpunkts der Linie
	 * @param y1 - die y-Koordinate des Startpunkts der Linie
	 * @param x2 - die x-Koordinate des Endpunkts der Linie
	 * @param y2 - die y-Koordinate des Endpunkts der Linie
	 * @param nearest - ein Array für die Rückgabe des nächsten Punktes auf der Linie
	 * @return der Abstand des Punktes p zur Linie bzw. zum Punkt n
	 */
	public static double distanceToLine(double x, double y, double x1, double y1, double x2, double y2, double[] nearest)
	{
		double dist;
		double[] ptMin = new double[2];

		double rVecX = x2 - x1;
		double rVecY = y2 - y1;

		double s = (x1 * rVecY - y1 * rVecX - x * rVecY + y * rVecX) / (-rVecY * rVecY - rVecX * rVecX);
		double t = (x * rVecY + s * -rVecY * rVecY - x1 * rVecY) / (rVecX * rVecY);

		if(rVecX == 0)
		{
			ptMin[0] = x1;
			ptMin[1] = y;
			if((y > y2) && (y > y1))
				ptMin[1] = Math.max(y1, y2);
			if((y < y2) && (y < y1))
				ptMin[1] = Math.min(y1, y2);

		}
		else if(rVecY == 0)
		{
			ptMin[0] = x;
			ptMin[1] = y1;
			if((x > x2) && (x > x1))
				ptMin[0] = Math.max(x1, x2);
			if((x < x2) && (x < x1))
				ptMin[0] = Math.min(x1, x2);
		}
		else if(t < 0)
		{
			// nearest point is p1
			ptMin[0] = x1;
			ptMin[1] = y1;
		}
		else if(t > 1)
		{
			// nearest point is p2
			ptMin[0] = x2;
			ptMin[1] = y2;
		}
		else
		{
			ptMin[0] = x1 + t * rVecX;
			ptMin[1] = y1 + t * rVecY;
		}

		if(nearest != null)
		{
			nearest[0] = ptMin[0];
			nearest[1] = ptMin[1];
		}

		dist = distance(ptMin[0], ptMin[1], x, y);

		return dist;
	}

	/**
	 * Berechnet den Abstand eines Punktes p zu einem Shape und den nächsten Punkt n auf dem Shape.
	 * Es werden dazu alle Teilstücke des Shapes geprüft und der geringste Abstand ermittelt. Bei
	 * den Teilstücken kann es sich um Linien oder Bezierkurven handeln. Es kann der nächste Punkt
	 * n, sowie der Winkel und Abstand zu diesem Punkt in Arrays zurückgegeben werden.
	 * 
	 * @see MathUtil#distanceToLine(double, double, double, double, double, double, double[])
	 * @see MathUtil#distanceToBezier(BezierCurve, double, double, double[], double[], boolean)
	 * @param shape - der Shape
	 * @param x - die x-Koordinate des zu untersuchenden Punkts
	 * @param y - die y-Koordinate des zu untersuchenden Punkts
	 * @param nearest - der nächste Punkt n zum Punkt p auf dem Shape
	 * @param distanceAndAngle - der Abstand zwischen p und der Winkel zwischen dem Lot und dem
	 *            Shape im Punkt n
	 * @return der Abstand des Punktes p zum Shape bzw. zum Punkt n
	 */
	public static double distanceToShape(Shape shape, double x, double y, double[] nearest, double[] distanceAndAngle)
	{
		PathIterator pi = shape.getPathIterator(null);
		double minDist = Double.POSITIVE_INFINITY;
		double dist;
		double[] ptMin = new double[2];
		double[] distAng = new double[2];

		int type;
		double[] coords = new double[6];
		double[] coords2 = null;
		double[] firstStart = null;
		double[] start = null;
		BezierCurve b = null;
		while(!pi.isDone())
		{
			type = pi.currentSegment(coords);
			pi.next();
			switch(type)
			{
				case PathIterator.SEG_LINETO:
					coords2 = new double[] { start[0], start[1], coords[0], coords[1] };
					b = new BezierCurve(type, coords2);
					start = new double[] { coords[0], coords[1] };
					break;
				case PathIterator.SEG_QUADTO:
					coords2 = new double[] { start[0], start[1], coords[0], coords[1], coords[2], coords[3] };
					b = new BezierCurve(type, coords2);
					start = new double[] { coords[2], coords[3] };
					break;
				case PathIterator.SEG_CUBICTO:
					coords2 = new double[] { start[0], start[1], coords[0], coords[1], coords[2], coords[3], coords[4], coords[5] };
					b = new BezierCurve(type, coords2);
					start = new double[] { coords[4], coords[5] };
					break;
				case PathIterator.SEG_MOVETO:
					b = null;
					start = new double[] { coords[0], coords[1] };
					firstStart = new double[] { coords[0], coords[1] };
					break;
				case PathIterator.SEG_CLOSE:
					type = PathIterator.SEG_LINETO;
					coords2 = new double[] { start[0], start[1], firstStart[0], firstStart[1] };
					b = new BezierCurve(type, coords2);
					start = new double[] { coords[0], coords[1] };
					break;
				default:
					break;
			}
			if(b != null)
			{
				dist = distanceToBezier(b, x, y, ptMin, distAng, true);
				if(dist < minDist)
				{
					minDist = dist;
					if(nearest != null)
					{
						nearest[0] = ptMin[0];
						nearest[1] = ptMin[1];
					}
					if(distanceAndAngle != null)
					{
						distanceAndAngle[0] = distAng[0];
						distanceAndAngle[1] = distAng[1];
					}
				}
			}
		}

		return minDist;
	}

	/**
	 * Berechnet den Abstand eines Punktes p zu einer Bezierkurve und den nächsten Punkt n auf der
	 * Kurve. Dazu wird die Bezierkurve numerisch approximiert und in definierten Schrittabständen
	 * berechnet. Es kann der nächste Punkt n, sowie der Winkel und Abstand zu diesem Punkt in
	 * Arrays zurückgegeben werden.
	 * 
	 * @see MathUtil#stepSize
	 * @see MathUtil#stepSizeSmall
	 * @param b - die Bezierkurve
	 * @param x - die x-Koordinate des zu untersuchenden Punkts
	 * @param y - die y-Koordinate des zu untersuchenden Punkts
	 * @param nearest - der nächste Punkt n zum Punkt p auf dem Shape
	 * @param distanceAndAngle - der Abstand zwischen p und der Winkel zwischen dem Lot und der
	 *            Kurve im Punkt n
	 * @param highPrecision - soll die Bezierkurve mit feinerer Schrittgröße approximiert werden?
	 * @return der Abstand des Punktes p zum Shape bzw. zum Punkt n
	 */
	public static double distanceToBezier(BezierCurve b, double x, double y, double[] nearest, double[] distanceAndAngle, boolean highPrecision)
	{
		double minDist = Double.POSITIVE_INFINITY;
		double[] ptMin = new double[2];
		if(b.getType() == PathIterator.SEG_LINETO)
		{
			double[] coords = b.getCoords();
			double x1 = coords[0];
			double y1 = coords[1];
			double x2 = coords[2];
			double y2 = coords[3];
			minDist = MathUtil.distanceToLine(x, y, x1, y1, x2, y2, ptMin);
			if(distanceAndAngle != null)
			{
				double angle = Math.atan2(-(y2 - y1), (x2 - x1)) + Math.PI / 2.0;
				if((ptMin[0] == x1) && (ptMin[1] == y1) && ((x != x1) || (y != y1)))
					angle = Math.atan2(-(y1 - y), (x1 - x));
				if((ptMin[0] == x2) && (ptMin[1] == y2) && ((x != x2) || (y != y2)))
					angle = Math.atan2(-(y2 - y), (x2 - x));
				distanceAndAngle[0] = minDist;
				distanceAndAngle[1] = angle;
			}

		}
		else
		{
			double[] pt;
			double dist = 0;
			double tMin = 0;
			for(double t = 0; t <= 1; t += stepSize)
			{
				pt = b.getCoordsAt(t);
				dist = MathUtil.distance(x, y, pt[0], pt[1]);
				if(dist < minDist)
				{
					minDist = dist;
					ptMin[0] = pt[0];
					ptMin[1] = pt[1];
					tMin = t;
				}
			}
			if(highPrecision)
			{
				double tMinStart = tMin - stepSize;
				double tMinEnd = tMin + stepSize;
				for(double t = tMinStart; t <= tMinEnd; t += stepSizeSmall)
				{
					pt = b.getCoordsAt(t);
					dist = MathUtil.distance(x, y, pt[0], pt[1]);
					if(dist < minDist)
					{
						minDist = dist;
						ptMin[0] = pt[0];
						ptMin[1] = pt[1];
						tMin = t;
					}
				}
			}
			if(distanceAndAngle != null)
			{
				double[] p1 = b.getCoordsAt(tMin);
				double[] p2 = b.getCoordsAt(tMin + stepSizeSmall);
				double angle = Math.atan2(-(p2[1] - p1[1]), (p2[0] - p1[0])) + Math.PI / 2.0;

				if(((tMin == 0) || (tMin == 1)) && (dist != 0))
					angle = Math.atan2(-(ptMin[1] - y), (ptMin[0] - x));

				distanceAndAngle[0] = minDist;
				distanceAndAngle[1] = angle;
			}
		}
		if(nearest != null)
		{
			nearest[0] = ptMin[0];
			nearest[1] = ptMin[1];
		}
		return minDist;
	}

	/**
	 * Modification of {@link Math#ceil(double)} taking an additional argument representing the
	 * requested accuracy in the following way:<br>
	 * <code>double fac = Math.pow(10, digits);<br>
	 * return fac * Math.ceil(a / fac);</code>
	 * 
	 * @param a - a value
	 * @param cutOfDigits - the number of digits to shift the value before and after ceiling
	 * @return the new value
	 */
	public static double ceil(double a, int cutOfDigits)
	{
		double fac = Math.pow(10, cutOfDigits);
		return fac * Math.ceil(a / fac);
	}

	/**
	 * Modification of {@link Math#floor(double)} taking an additional argument representing the
	 * requested accuracy in the following way:<br>
	 * <code>double fac = Math.pow(10, digits);<br>
	 * return fac * Math.floor(a / fac);</code>
	 * 
	 * @param a - a value
	 * @param cutOfDigits - the number of digits to shift the value before and after flooring
	 * @return the new value
	 */
	public static double floor(double a, int cutOfDigits)
	{
		double fac = Math.pow(10, cutOfDigits);
		return fac * Math.floor(a / fac);
	}

	/**
	 * Modification of {@link Math#round(double)} taking an additional argument representing the
	 * requested accuracy in the following way:<br>
	 * <code>double fac = Math.pow(10, digits);<br>
	 * return fac * Math.round(a / fac);</code>
	 * 
	 * @param a - a value
	 * @param cutOfDigits - the number of digits to shift the value before and after rounding
	 * @return the new value
	 */
	public static double round(double a, int cutOfDigits)
	{
		double fac = Math.pow(10, cutOfDigits);
		return fac * Math.round(a / fac);
	}

	/**
	 * {@link Math#ceil(double)}-like operation for integers, e.g.:
	 * <ul>
	 * <li>1234, 3 --> 2000</li>
	 * <li>1234, 2 --> 1300</li>
	 * <li>1234, 1 --> 1240</li>
	 * </ul>
	 * Some "unusual" cases:
	 * <ul>
	 * <li>1234, 5 --> 100000</li>
	 * <li>1234, 4 --> 10000</li>
	 * <li>1234, 0 --> 1234</li>
	 * <li>1234, -1 --> 1234</li>
	 * </ul>
	 * 
	 * @param a - a value
	 * @param cutOfDigits - the number of digits to "cut of"
	 * @return the new value
	 */
	public static int ceil(int a, int cutOfDigits)
	{
		if(cutOfDigits <= 0)
			return a;
		int fac = 1;
		while(--cutOfDigits >= 0)
			fac *= 10;
		int ret = (a / fac) * fac;
		if(a > ret)
			ret += fac;
		return ret;
	}

	/**
	 * {@link Math#floor(double)}-like operation for integers, e.g.:
	 * <ul>
	 * <li>1234, 3 --> 1000</li>
	 * <li>1234, 2 --> 1200</li>
	 * <li>1234, 1 --> 1230</li>
	 * </ul>
	 * Some "unusual" cases:
	 * <ul>
	 * <li>1234, 5 --> 0</li>
	 * <li>1234, 4 --> 0</li>
	 * <li>1234, 0 --> 1234</li>
	 * <li>1234, -1 --> 1234</li>
	 * </ul>
	 * 
	 * @param a - a value
	 * @param cutOfDigits - the number of digits to "cut of"
	 * @return the new value
	 */
	public static int floor(int a, int cutOfDigits)
	{
		if(cutOfDigits <= 0)
			return a;
		int fac = 1;
		while(--cutOfDigits >= 0)
			fac *= 10;
		int ret = (a / fac) * fac;
		return ret;
	}

	/**
	 * {@link Math#round(double)}-like operation for integers, e.g.:
	 * <ul>
	 * <li>1234, 3 --> 1000 (like floor, since digits are < 5)</li>
	 * <li>1234, 2 --> 1200 (like floor, since digits are < 5)</li>
	 * <li>1234, 1 --> 1230 (like floor, since digits are < 5)</li>
	 * <li>5678, 3 --> 6000 (like ceil, since digits are >= 5)</li>
	 * <li>5678, 2 --> 5700 (like ceil, since digits are >= 5)</li>
	 * <li>5678, 1 --> 5680 (like ceil, since digits are >= 5)</li>
	 * </ul>
	 * Some "unusual" cases:
	 * <ul>
	 * <li>1234, 5 --> 0 (like floor, since digits are < 5)</li>
	 * <li>1234, 4 --> 0 (like floor, since digits are < 5)</li>
	 * <li>1234, 0 --> 1234 (like floor, since digits are < 5)</li>
	 * <li>1234, -1 --> 1234 (like floor, since digits are < 5)</li>
	 * <li>5678, 5 --> 100000 (like ceil, since digits are >= 5)</li>
	 * <li>5678, 4 --> 10000 (like ceil, since digits are >= 5)</li>
	 * <li>5678, 0 --> 5678 (like ceil, since digits are >= 5)</li>
	 * <li>5678, -1 --> 5678 (like ceil, since digits are >= 5)</li>
	 * </ul>
	 * 
	 * @param a - a value
	 * @param cutOfDigits - the number of digits to "cut of"
	 * @return the new value
	 */
	public static int round(int a, int cutOfDigits)
	{
		if(cutOfDigits <= 0)
			return a;
		int fac = 1;
		while(--cutOfDigits >= 0)
			fac *= 10;
		int ret = (a / fac) * fac;
		if(a - ret > fac / 2)
			ret += fac;
		return ret;
	}

	/**
	 * {@link Math#ceil(double)}-like operation for integers preserving the given number of digits
	 * in contrary to {@link MathUtil#ceil(int, int)} where cut-of-digits are specified:<br>
	 * <code>return ceil(a, getDigits(a) - preserveDigits)</code>
	 * 
	 * @param a - a value
	 * @param preserveDigits - the number of digits to preserve
	 * @return the new value
	 */
	public static int ceil2(int a, int preserveDigits)
	{
		return ceil(a, getDigits(a) - preserveDigits);
	}

	/**
	 * {@link Math#floor(double)}-like operation for integers preserving the given number of digits
	 * in contrary to {@link MathUtil#floor(int, int)} where cut-of-digits are specified:<br>
	 * <code>return floor(a, getDigits(a) - preserveDigits)</code>
	 * 
	 * @param a - a value
	 * @param preserveDigits - the number of digits to preserve
	 * @return the new value
	 */
	public static int floor2(int a, int preserveDigits)
	{
		return floor(a, getDigits(a) - preserveDigits);
	}

	/**
	 * {@link Math#round(double)}-like operation for integers preserving the given number of digits
	 * in contrary to {@link MathUtil#round(int, int)} where cut-of-digits are specified:<br>
	 * <code>return round(a, getDigits(a) - preserveDigits)</code>
	 * 
	 * @param a - a value
	 * @param preserveDigits - the number of digits to preserve
	 * @return the new value
	 */
	public static int round2(int a, int preserveDigits)
	{
		return round(a, getDigits(a) - preserveDigits);
	}

	/**
	 * Get the number of digits for the given value
	 * 
	 * @param a - a value
	 * @return the number of digits
	 */
	public static int getDigits(int a)
	{
		int digits = 0;
		while(a != 0)
		{
			digits++;
			a /= 10;
		}
		return digits;
	}

	/**
	 * Apply the given permutation to the array. The permutation must contain each index within the
	 * range from 0 to n-1 exactly once (and no other indexes) and therefore represents the new
	 * order of the elements within the array.
	 * 
	 * @param array - the array to permute
	 * @param perm - the permutation
	 * @return the modified array for convenience
	 */
	public static <T> T[] perm(T[] array, int[] perm)
	{
		permCheck(array.length, perm);
		T[] tmp = Arrays.copyOf(array, array.length);
		for(int i = 0; i < array.length; i++)
		{
			array[i] = tmp[perm[i]];
		}
		return array;
	}

	/**
	 * Apply the given permutation to the array. The permutation must contain each index within the
	 * range from 0 to n-1 exactly once (and no other indexes) and therefore represents the new
	 * order of the elements within the array.
	 * 
	 * @param array - the array to permute
	 * @param perm - the permutation
	 * @return the modified array for convenience
	 */
	public static int[] perm(int[] array, int[] perm)
	{
		permCheck(array.length, perm);
		int[] tmp = Arrays.copyOf(array, array.length);
		for(int i = 0; i < array.length; i++)
		{
			array[i] = tmp[perm[i]];
		}
		return array;
	}

	/**
	 * Apply the given permutation to the array. The permutation must contain each index within the
	 * range from 0 to n-1 exactly once (and no other indexes) and therefore represents the new
	 * order of the elements within the array.
	 * 
	 * @param array - the array to permute
	 * @param perm - the permutation
	 * @return the modified array for convenience
	 */
	public static long[] perm(long[] array, int[] perm)
	{
		permCheck(array.length, perm);
		long[] tmp = Arrays.copyOf(array, array.length);
		for(int i = 0; i < array.length; i++)
		{
			array[i] = tmp[perm[i]];
		}
		return array;
	}

	/**
	 * Apply the given permutation to the array. The permutation must contain each index within the
	 * range from 0 to n-1 exactly once (and no other indexes) and therefore represents the new
	 * order of the elements within the array.
	 * 
	 * @param array - the array to permute
	 * @param perm - the permutation
	 * @return the modified array for convenience
	 */
	public static double[] perm(double[] array, int[] perm)
	{
		permCheck(array.length, perm);
		double[] tmp = Arrays.copyOf(array, array.length);
		for(int i = 0; i < array.length; i++)
		{
			array[i] = tmp[perm[i]];
		}
		return array;
	}

	/**
	 * Apply the given permutation to the array. The permutation must contain each index within the
	 * range from 0 to n-1 exactly once (and no other indexes) and therefore represents the new
	 * order of the elements within the array.
	 * 
	 * @param array - the array to permute
	 * @param perm - the permutation
	 * @return the modified array for convenience
	 */
	public static float[] perm(float[] array, int[] perm)
	{
		permCheck(array.length, perm);
		float[] tmp = Arrays.copyOf(array, array.length);
		for(int i = 0; i < array.length; i++)
		{
			array[i] = tmp[perm[i]];
		}
		return array;
	}

	/**
	 * Apply the given permutation to the array. The permutation must contain each index within the
	 * range from 0 to n-1 exactly once (and no other indexes) and therefore represents the new
	 * order of the elements within the array.
	 * 
	 * @param array - the array to permute
	 * @param perm - the permutation
	 * @return the modified array for convenience
	 */
	public static boolean[] perm(boolean[] array, int[] perm)
	{
		permCheck(array.length, perm);
		boolean[] tmp = Arrays.copyOf(array, array.length);
		for(int i = 0; i < array.length; i++)
		{
			array[i] = tmp[perm[i]];
		}
		return array;
	}

	/**
	 * Apply the given permutation to the array. The permutation must contain each index within the
	 * range from 0 to n-1 exactly once (and no other indexes) and therefore represents the new
	 * order of the elements within the array.
	 * 
	 * @param array - the array to permute
	 * @param perm - the permutation
	 * @return the modified array for convenience
	 */
	public static short[] perm(short[] array, int[] perm)
	{
		permCheck(array.length, perm);
		short[] tmp = Arrays.copyOf(array, array.length);
		for(int i = 0; i < array.length; i++)
		{
			array[i] = tmp[perm[i]];
		}
		return array;
	}

	/**
	 * Apply the given permutation to the array. The permutation must contain each index within the
	 * range from 0 to n-1 exactly once (and no other indexes) and therefore represents the new
	 * order of the elements within the array.
	 * 
	 * @param array - the array to permute
	 * @param perm - the permutation
	 * @return the modified array for convenience
	 */
	public static byte[] perm(byte[] array, int[] perm)
	{
		permCheck(array.length, perm);
		byte[] tmp = Arrays.copyOf(array, array.length);
		for(int i = 0; i < array.length; i++)
		{
			array[i] = tmp[perm[i]];
		}
		return array;
	}

	/**
	 * Apply the given permutation to the array. The permutation must contain each index within the
	 * range from 0 to n-1 exactly once (and no other indexes) and therefore represents the new
	 * order of the elements within the array.
	 * 
	 * @param array - the array to permute
	 * @param perm - the permutation
	 * @return the modified array for convenience
	 */
	public static char[] perm(char[] array, int[] perm)
	{
		permCheck(array.length, perm);
		char[] tmp = Arrays.copyOf(array, array.length);
		for(int i = 0; i < array.length; i++)
		{
			array[i] = tmp[perm[i]];
		}
		return array;
	}

	/**
	 * Apply the given permutation to the array. The permutation must contain each index within the
	 * range from 0 to n-1 exactly once (and no other indexes) and therefore represents the new
	 * order of the elements within the array.
	 * 
	 * @param array - the array to permute
	 * @param perm - the permutation
	 * @return the modified array for convenience
	 */
	public static <T> List<T> perm(List<T> c, int[] perm)
	{
		permCheck(c.size(), perm);
		List<T> tmp = new ArrayList<T>(c);
		c.clear();
		for(int i = 0; i < tmp.size(); i++)
		{
			c.add(tmp.get(perm[i]));
		}
		return c;
	}

	/**
	 * Check wether the given permutation is valid. The permutation must contain each index within 0
	 * and length-1 exactly once (and no other indexes are allowed!)
	 * 
	 * @param length - the length required
	 * @param perm - the permutation to check
	 */
	protected static void permCheck(int length, int[] perm)
	{
		if(length != perm.length)
			throw new IllegalArgumentException("perm must have same length as the array given");
		int[] indexOccurence = new int[length];
		for(int i = 0; i < length; i++)
		{
			if(perm[i] >= length || indexOccurence[perm[i]] > 0)
				throw new IllegalArgumentException("not a valid permutation: each index from 0 to n-1 must occur exactly once!");
			indexOccurence[perm[i]]++;
		}
	}

	/**
	 * Get the cycle length of a permutation. The cycle length is defined as the number of times the
	 * permutation has to be applied to an array until the original array reoccurrs in every entry.<br>
	 * <br>
	 * Note: This is a stronger check than {@link MathUtil#permPartialCycleLength(int[])}
	 * 
	 * @param perm - the permutation to check
	 * @return the cycle length
	 */
	public static int permCycleLength(int[] perm)
	{
		return permCycleLength(perm, true);
	}

	/**
	 * Get the partial cycle length of a permutation. The partial cycle length is defined as the
	 * number of times the permutation has to be applied to an array until any entry of the array
	 * occurrs at it's original position.<br>
	 * <br>
	 * Note: This is a weaker check than {@link MathUtil#permCycleLength(int[])}).
	 * 
	 * @param perm - the permutation to check
	 * @return the partial cycle length
	 */
	public static int permPartialCycleLength(int[] perm)
	{
		return permCycleLength(perm, false);
	}

	/**
	 * Unified method for {@link MathUtil#permCycleLength(int[])} and
	 * {@link MathUtil#permPartialCycleLength(int[])}.
	 * 
	 * @param perm
	 * @param fullCycle
	 * @return
	 */
	protected static int permCycleLength(int[] perm, boolean fullCycle)
	{
		permCheck(perm.length, perm);

		int[] arr = new int[perm.length];
		for(int i = 0; i < perm.length; i++)
			arr[i] = i;

		int reoccurred;
		int count = 0;
		while(true)
		{
			count++;
			reoccurred = 0;
			perm(arr, perm);
			for(int j = 0; j < arr.length; j++)
			{
				if(j == arr[j])
					reoccurred++;
			}
			if(reoccurred == perm.length || (!fullCycle && reoccurred > 0))
				return count;
		}
	}
}