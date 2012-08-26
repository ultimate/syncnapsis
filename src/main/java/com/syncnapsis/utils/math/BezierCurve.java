package com.syncnapsis.utils.math;

import java.awt.geom.PathIterator;

import org.springframework.util.Assert;

/**
 * Klasse für die Repräsentation und Berechnung einer Bezierkurve.<br/>
 * Die Bezierkurve lässt sich je nach Grad (linear, quadratisch oder kubisch) in
 * jeder Koordinate durch ein Polynom des entsprechenden Grades berechnen. Dazu
 * kann getCoordsAt(...) mit einem Parameter zwischen 0 und 1 aufgerufen werden.
 * Die Koeffizienten der Polynome werden aus den übergebenen Punkten berechnet.
 * Dabei stehen in coords jeweils gerade Indizes (0,2,4,6) für die x-Koordinaten
 * der bis zu 6 Punkte und die ungeraden Indizes (1,3,5,7) für die
 * y-Koordinaten.
 * 
 * @author ultimate
 */
public class BezierCurve
{
	/**
	 * Konstante für eine Gerade. Es sind 2 Punkte für die Definition notwendig.
	 * 
	 * @see PathIterator#SEG_LINETO
	 */
	public static final int	LINEAR	= PathIterator.SEG_LINETO;
	/**
	 * Konstante für einer quadratischen Funktion. Es sind 3 Punkte für die
	 * Definition notwendig.
	 * 
	 * @see PathIterator#SEG_QUADTO
	 */
	public static final int	QUADRIC	= PathIterator.SEG_QUADTO;
	/**
	 * Konstante für eine kubischen Funktion. Es sind 4 Punkte für die
	 * Definition notwendig.
	 * 
	 * @see PathIterator#SEG_CUBICTO
	 */
	public static final int	CUBIC	= PathIterator.SEG_CUBICTO;

	/**
	 * Grad der Bezierkurve (LINEAR, QUADRIC oder CUBIC)
	 */
	private int				type;
	/**
	 * Die Punkte entlang der die Kurve verlaufen soll
	 */
	private double[]		coords;
	/**
	 * Koeffizienten des Polynoms für die x-Koordinate
	 */
	private double[]		factorsX;
	/**
	 * Koeffizienten des Polynoms für die y-Koordinate
	 */
	private double[]		factorsY;

	/**
	 * Erzeugt eine neue Bezierkurve aus den gegebenen Punkten und berechnet die
	 * Koeffizienten des Polynoms.
	 * 
	 * @param type - Grad der Bezierkurve (LINEAR, QUADRIC oder CUBIC)
	 * @param coords - die Punkte entlang der die Kurve verlaufen soll
	 */
	public BezierCurve(int type, double[] coords)
	{
		Assert.notNull(coords, "coords must not be null");
		this.type = type;
		this.coords = coords.clone();
		if(type == LINEAR)
		{
			Assert.isTrue(coords.length == 4, "coords must have length 4 with LINEAR");
			this.factorsX = new double[] { coords[0], (coords[2] - coords[0]), 0, 0 };
			this.factorsY = new double[] { coords[1], (coords[3] - coords[1]), 0, 0 };
		}
		else if(type == QUADRIC)
		{
			Assert.isTrue(coords.length == 6, "coords must have length 6 with QUADRIC");
			this.factorsX = new double[] { coords[0], 2 * (coords[2] - coords[0]), (coords[4] - 2 * coords[2] + coords[0]), 0 };
			this.factorsY = new double[] { coords[1], 2 * (coords[3] - coords[1]), (coords[5] - 2 * coords[3] + coords[1]), 0 };
		}
		else if(type == CUBIC)
		{
			Assert.isTrue(coords.length == 8, "coords must have length 8 with CUBIC");
			this.factorsX = new double[] { coords[0], 3 * (coords[2] - coords[0]), 3 * (coords[4] - 2 * coords[2] + coords[0]),
					(coords[6] - 3 * coords[4] + 3 * coords[2] - coords[0]) };
			this.factorsY = new double[] { coords[1], 3 * (coords[3] - coords[1]), 3 * (coords[5] - 2 * coords[3] + coords[1]),
					(coords[7] - 3 * coords[5] + 3 * coords[3] - coords[1]) };
		}
		else
		{
			throw new IllegalArgumentException("type must be LINEAR, QUADRIC or CUBIC");
		}
	}

	/**
	 * Grad der Bezierkurve (LINEAR, QUADRIC oder CUBIC)
	 * 
	 * @return type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * Die Punkte entlang der die Kurve verlaufen soll
	 * 
	 * @return coords
	 */
	public double[] getCoords()
	{
		return coords.clone();
	}

	/**
	 * Koeffizienten des Polynoms für die x-Koordinate
	 * 
	 * @return factorsX
	 */
	public double[] getFactorsX()
	{
		return factorsX.clone();
	}

	/**
	 * Koeffizienten des Polynoms für die y-Koordinate
	 * 
	 * @return factorsY
	 */
	public double[] getFactorsY()
	{
		return factorsY.clone();
	}

	/**
	 * Berechnet die Koordinaten der Bezierkurve an einer bestimmten Stelle t.
	 * Für t=0 ergibt sich dabei der erste Punkt von coords, für t=1 der letzte
	 * Punkt von coords.
	 * 
	 * @param t - die Stelle für die Berechnung der Koordinaten
	 * @return die Koordinaten x und y
	 */
	public double[] getCoordsAt(double t)
	{
		double x = 0;
		double y = 0;
		for(int i = 0; i < 4; i++)
		{
			if(factorsX[i] != 0)
				x += factorsX[i] * Math.pow(t, i);
			if(factorsY[i] != 0)
				y += factorsY[i] * Math.pow(t, i);
		}
		return new double[] { x, y };
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Bezier[");
		sb.append(type);
		sb.append(" - ");
		for(int i = 0; i < coords.length; i = i + 2)
		{
			sb.append("(");
			sb.append(coords[i]);
			sb.append("|");
			sb.append(coords[i + 1]);
			sb.append(")");
		}
		sb.append(" ]");
		return sb.toString();
	}
}
