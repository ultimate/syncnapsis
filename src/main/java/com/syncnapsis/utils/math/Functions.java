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
package com.syncnapsis.utils.math;

import com.syncnapsis.utils.MathUtil;
import org.springframework.util.Assert;

/**
 * Hilfs-Klasse für die Berechnung mathematischer Funktionen
 * 
 * @author ultimate
 */
public abstract class Functions
{
	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Die Funktion erfüllt die Bedingungen f(0) = 1; f(1) = 0
	 * Geradengleichung: f(param > 0) = 1 - param
	 * 
	 * @param param - der Parameter für die Funktion
	 * @return der Funktionswert
	 */
	public static double linear(double param)
	{
		if(param > 0)
			return (1 - param);
		else
			return (1 + param);
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Die Funktion erfüllt die Bedingungen f(0) = 1; f(1) = 0
	 * Geradengleichung mit "Kreissegment an der Spitze"
	 * 
	 * @param param - der Parameter für die Funktion
	 * @return der Funktionswert
	 */
	public static double linearSoft(double param)
	{
		double softness = 0.1;

		return linearSoft(param, softness);
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Die Funktion erfüllt die Bedingungen f(0) = 1; f(1) = 0
	 * Geradengleichung mit "Kreissegment an der Spitze"
	 * 
	 * @param param - der Parameter für die Funktion
	 * @param softness - wie groß soll das Kreissegment sein?
	 * @return der Funktionswert
	 */
	public static double linearSoft(double param, double softness)
	{
		Assert.isTrue(softness >= 0 && softness <= 1, "softness must be between 0 and 1");

		return linearSoft2(param, softness) / linearSoft2(0, softness);
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Die Funktion erfüllt die Bedingungen f(0) = 1; f(1) = 0 NICHT
	 * Geradengleichung mit "Kreissegment an der Spitze"
	 * 
	 * @param param - der Parameter für die Funktion
	 * @param softness - wie groß soll das Kreissegment sein?
	 * @return der Funktionswert
	 */
	public static double linearSoft2(double param, double softness)
	{
		/*
		 * Gerade: l(x) = (x+1) l'(x) = 1 Nach oben verschobener Kreis: c(x) = sqrt(r*r - x*x) + dh
		 * c'(x) = -x/sqrt(r*r - x*x) Bedingungen: l(-s) = c(-s) --> (-s+1) = sqrt(r*r - s*s) + dh
		 * l'(-s) = c'(-s) --> 1 = -s/sqrt(r*r - s*s)
		 */
		double r = Math.sqrt(2 * softness * softness);
		double dh = (-softness + 1) - Math.sqrt(softness * softness);
		if(Math.abs(param) <= softness)
			return circular(param, r) + dh;
		else
			return linear(param);
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Die Funktion erfüllt die Bedingungen: f(0) = 1; f(1) = 0
	 * Parabelgleichung f(param) = 1 - param^2
	 * 
	 * @param param - der Parameter für die Funktion
	 * @return der Funktionswert
	 */
	public static double quad(double param)
	{
		return (1 - param * param);
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Die Funktion erfüllt die Bedingungen f(0) = 1; f(1) = 0
	 * Kubische Gleichung f(param > 0) = 1 - param^3
	 * 
	 * @param param - der Parameter für die Funktion
	 * @return der Funktionswert
	 */
	public static double cubic(double param)
	{
		return (1 - Math.abs(param * param * param));
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Die Funktion erfüllt die Bedingungen: f(0) = 1; f(1) = 0
	 * Einheitskreis f(param) = sqrt(1 - param^2)
	 * 
	 * @param param - der Parameter für die Funktion
	 * @return der Funktionswert
	 */
	public static double circularUnit(double param)
	{
		return Math.sqrt(1 - param * param);
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Die Funktion erfüllt die Bedingungen f(0) = r; f(r) = 0
	 * Kreisgleichung f(param) = sqrt(r^2 - param^2)
	 * 
	 * @param param - der Parameter für die Funktion
	 * @param r - der Radius des Kreises
	 * @return der Funktionswert
	 */
	public static double circular(double param, double r)
	{
		Assert.isTrue(r >= 0, "r must be >= 0");
		if(Math.abs(param) > r)
			return 0;
		return Math.sqrt(r * r - param * param);
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Die Funktion erfüllt die Bedingungen: f(0) = ry; f(rx) = 0
	 * Ellipsengleichung f(param) = ry * sqrt(1 - param^2/rx^2)
	 * 
	 * @param param - der Parameter für die Funktion
	 * @param rx - der halbe Durchmesser der Ellipse in x-Richtung
	 * @param ry - der halbe Durchmesser der Ellipse in y-Richtung
	 * @return der Funktionswert
	 */
	public static double ellipsis(double param, double rx, double ry)
	{
		Assert.isTrue(rx >= 0, "rx must be >= 0");
		Assert.isTrue(ry >= 0, "ry must be >= 0");
		if(Math.abs(param) > rx)
			return 0;
		// x^2/rx^2 + y^2/ry^2 = 1
		return ry * Math.sqrt(1 - (param * param) / (rx * rx));
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Gaussglocke mit sigma = 0.3 & normalize = true
	 * 
	 * @see MathUtil#gauss(double, double, boolean)
	 * @param param - der Parameter für die Funktion
	 * @return der Funktionswert
	 */
	public static double gauss(double param)
	{
		double sigma = 0.3;
		return gauss(param, sigma, true);
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Die Funktion erfüllt die Bedingungen f(0) = 1; f(1) = 0, wenn normalize = true
	 * Bei Normalisierung gilt f_norm = (f(x) - f(1)) / (f(0) - f(1))
	 * Gaussglocke mit sigma beliebig & mu = 0
	 * 
	 * @param param - der Parameter für die Funktion
	 * @param sigma - der Sigma-Wert für die Gaussglocke
	 * @param normalize - soll die Funktion Normalisiert werden (f(0) = 1; f(rx) = 0)
	 * @return der Funktionswert
	 */
	public static double gauss(double param, double sigma, boolean normalize)
	{
		double ret = 0;

		double mu = 0;

		ret = 1.0 / Math.sqrt(2 * Math.PI) / sigma * Math.exp(-(param - mu) * (param - mu) / 2.0 / sigma / sigma);
		if(normalize)
		{
			ret = ret - gauss(1, sigma, false);
			ret = ret / (gauss(0, sigma, false) - gauss(1, sigma, false));
		}
		return ret;
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Modifizierte Gaussglocke mit bound = 0.4, gradient = 0.125 & sigma = 0.4
	 * 
	 * @see MathUtil#gaussModified(double, double, double, double)
	 * @param param - der Parameter für die Funktion
	 * @return der Funktionswert
	 */
	public static double gaussModified(double param)
	{
		double bound = 0.4;
		double gradient = 0.125;
		double sigma = 0.4;

		return gaussModified(param, bound, gradient, sigma);
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Die Funktion erfüllt die Bedingungen f(1) = 0; f(0) = 1
	 * Modifizierte Gaussglocke mit beliebigen Werten für bound, gradient & sigma.
	 * param < bound
	 * f(param) = "Geringfügig angepasste Gaussglocke, so dass f in bound stetig ist und f(0) = 1"
	 * param > bound
	 * f(param) = gradient - gradient*param
	 * 
	 * @see MathUtil#gauss(double, double, boolean)
	 * @param param - der Parameter für die Funktion
	 * @return der Funktionswert
	 */
	public static double gaussModified(double param, double bound, double gradient, double sigma)
	{
		double ret = gauss(param, sigma, false);

		if(param < bound)
		{
			double retBound = gauss(bound, sigma, false);
			double retZero = gauss(0, sigma, false);

			ret = ret - retBound;
			ret = ret / (retZero - retBound) * (1 - gradient + gradient * bound);
			ret = ret + (gradient - gradient * bound);
		}
		else
		{
			ret = gradient - gradient * param;
		}

		return ret;
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Die Funktion erfüllt die Bedingungen f(1) = 0; f(0) = 1
	 * "Funktion, die rotiert wie eine Scheibe aussieht" mit discHeight = 1
	 * 
	 * @see MathUtil#disc(double, double)
	 * @param param - der Parameter für die Funktion
	 * @return der Funktionswert
	 */
	public static double disc(double param)
	{
		double discHeight = 1;
		return disc(param, discHeight);
	}

	/**
	 * Mathematischen Funktion z.B. für die Verwendung als Gradient.
	 * Die Funktion ist symmetrisch: f(-param) = f(param)
	 * Die Funktion erfüllt die Bedingungen f(1) = 0; f(0) = discHeight
	 * "Funktion, die rotiert wie eine Scheibe aussieht"
	 * f(param) = discHeight * (1 - param)^0.25
	 * 
	 * @see MathUtil#gauss(double, double, boolean)
	 * @param param - der Parameter für die Funktion
	 * @return der Funktionswert
	 */
	public static double disc(double param, double discHeight)
	{
		return Math.pow(1 - Math.abs(param), 0.25) * discHeight;
	}

}
