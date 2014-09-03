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
package com.syncnapsis.utils.data;

import org.springframework.util.Assert;

import com.syncnapsis.data.model.help.Vector;
import com.syncnapsis.utils.math.Array3D;

/**
 * Generator for Integer Vectors. The Generator will create random Vectors with distribution
 * according to a 3-dimensional array of probabilities. If no matrix is given an even distribution
 * will be used.<br>
 * <br>
 * Since the space of available Vectors is limited due to the nature of the limited Arra3D all
 * Vectors been generated once will be stored to avoid collisions if desired. During Vector
 * generation collisions are detected and if necessary a new Vector that has not yet been generated
 * before will be generated. Since chance of creating a new Vector decreases for each Vector being
 * generated execution time for {@link VectorGenerator#generate(Object...)} may increase as well.<br>
 * <br>
 * The random generation is done as described below:
 * <ol>
 * <li>During initialization probability sums will be calculated for every possible Vector within
 * the space defined by the Array3D of probabilities. Vectors therefore are associated with their
 * index as specified by {@link Array3D#getIndex(int, int, int)}. For every index the sum of
 * probabilities is defined as <code>sum[i] = prob[i] + sum[i-1]</code> where
 * <code>sum[0] = prob[0]</code></li>
 * <li>Using this sums of probability not only even distributions can be realized but Vectors are
 * selected according to the probability that is associated to them by creating a random sum within
 * 0.0 and the total sum of probabilities and then finding the index that contains the generated
 * probability.</li>
 * </ol>
 * 
 * @author ultimate
 */
public class VectorGenerator extends Generator<Vector.Integer>
{
	/**
	 * The array of probabilties
	 */
	protected Array3D	probabilities;

	/**
	 * The probability sums for each index
	 */
	protected double[]	vectorProbabilitySums;

	/**
	 * The sum of all probabilites within the Arra3D
	 */
	protected double	vectorProbabilitySum;

	/**
	 * The counter holding the number of times the vectors have been generated.
	 */
	protected int[]		vectorGenerated;

	/**
	 * The counter for the number of collions
	 */
	protected int		vectorCollisions;

	/**
	 * Are collisions allowed or is regeneration required on collision.
	 */
	protected boolean	collisionsAllowed;

	/**
	 * Internal helper variable needed during random generation
	 */
	protected int		stepMax;

	/**
	 * Construct a new VectorGenerator from the given Array3D of probabilities
	 * 
	 * @param probabilities - the array of probabilities
	 */
	public VectorGenerator(Array3D probabilities)
	{
		super();
		init(probabilities);
	}

	/**
	 * Construct a new VectorGenerator from the given Array3D of probabilities
	 * 
	 * @see Generator#Generator(ExtendedRandom)
	 * @param probabilities - the array of probabilities
	 * @param random - the random number generator
	 */
	public VectorGenerator(Array3D probabilities, ExtendedRandom random)
	{
		super(random);
		init(probabilities);
	}

	/**
	 * Construct a new VectorGenerator from the given Array3D of probabilities
	 * 
	 * @see Generator#Generator(long)
	 * @param probabilities - the array of probabilities
	 * @param seed - the seed for the random number generator
	 */
	public VectorGenerator(Array3D probabilities, long seed)
	{
		super(seed);
		init(probabilities);
	}

	/**
	 * Construct a new VectorGenerator with the given bounds for creating a new underlying Array3D
	 * with even probability distribution
	 * 
	 * @param xMin - The min value for x (inclusive)
	 * @param xMax - The max value for x (inclusive)
	 * @param yMin - The min value for y (inclusive)
	 * @param yMax - The max value for y (inclusive)
	 * @param zMin - The min value for z (inclusive)
	 * @param zMax - The max value for z (inclusive)
	 */
	public VectorGenerator(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax)
	{
		super();
		init(new Array3D(xMin, xMax, yMin, yMax, zMin, zMax, 1.0));
	}

	/**
	 * Construct a new VectorGenerator with the given bounds for creating a new underlying Array3D
	 * with even probability distribution
	 * 
	 * @see Generator#Generator(ExtendedRandom)
	 * @param xMin - The min value for x (inclusive)
	 * @param xMax - The max value for x (inclusive)
	 * @param yMin - The min value for y (inclusive)
	 * @param yMax - The max value for y (inclusive)
	 * @param zMin - The min value for z (inclusive)
	 * @param zMax - The max value for z (inclusive)
	 * @param random - the random number generator
	 */
	public VectorGenerator(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax, ExtendedRandom random)
	{
		super(random);
		init(new Array3D(xMin, xMax, yMin, yMax, zMin, zMax, 1.0));
	}

	/**
	 * Construct a new VectorGenerator with the given bounds for creating a new underlying Array3D
	 * with even probability distribution
	 * 
	 * @see Generator#Generator(long)
	 * @param xMin - The min value for x (inclusive)
	 * @param xMax - The max value for x (inclusive)
	 * @param yMin - The min value for y (inclusive)
	 * @param yMax - The max value for y (inclusive)
	 * @param zMin - The min value for z (inclusive)
	 * @param zMax - The max value for z (inclusive)
	 * @param seed - the seed for the random number generator
	 */
	public VectorGenerator(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax, long seed)
	{
		super(seed);
		init(new Array3D(xMin, xMax, yMin, yMax, zMin, zMax, 1.0));
	}

	/**
	 * Construct a new VectorGenerator with the given bounds for creating a new underlying Array3D
	 * with even probability distribution
	 * 
	 * @param xSize - The size in x-direction
	 * @param ySize - The size in y-direction
	 * @param zSize - The size in z-direction
	 */
	public VectorGenerator(int xSize, int ySize, int zSize)
	{
		super();
		init(new Array3D(xSize, ySize, zSize, 1.0));
	}

	/**
	 * Construct a new VectorGenerator with the given bounds for creating a new underlying Array3D
	 * with even probability distribution
	 * 
	 * @see Generator#Generator(ExtendedRandom)
	 * @param xSize - The size in x-direction
	 * @param ySize - The size in y-direction
	 * @param zSize - The size in z-direction
	 * @param random - the random number generator
	 */
	public VectorGenerator(int xSize, int ySize, int zSize, ExtendedRandom random)
	{
		super(random);
		init(new Array3D(xSize, ySize, zSize, 1.0));
	}

	/**
	 * Construct a new VectorGenerator with the given bounds for creating a new underlying Array3D
	 * with even probability distribution
	 * 
	 * @see Generator#Generator(long)
	 * @param xSize - The size in x-direction
	 * @param ySize - The size in y-direction
	 * @param zSize - The size in z-direction
	 * @param seed - the seed for the random number generator
	 */
	public VectorGenerator(int xSize, int ySize, int zSize, long seed)
	{
		super(seed);
		init(new Array3D(xSize, ySize, zSize, 1.0));
	}

	/**
	 * Construct a new VectorGenerator with the given bounds for creating a new underlying Array3D
	 * with even probability distribution
	 * 
	 * @param size - The size as an Vector
	 */
	public VectorGenerator(Vector.Integer size)
	{
		this(size.getX(), size.getY(), size.getZ());
	}

	/**
	 * Construct a new VectorGenerator with the given bounds for creating a new underlying Array3D
	 * with even probability distribution
	 * 
	 * @see Generator#Generator(ExtendedRandom)
	 * @param size - The size as an Vector
	 * @param random - the random number generator
	 */
	public VectorGenerator(Vector.Integer size, ExtendedRandom random)
	{
		this(size.getX(), size.getY(), size.getZ(), random);
	}

	/**
	 * Construct a new VectorGenerator with the given bounds for creating a new underlying Array3D
	 * with even probability distribution
	 * 
	 * @see Generator#Generator(long)
	 * @param size - The size as an Vector
	 * @param seed - the seed for the random number generator
	 */
	public VectorGenerator(Vector.Integer size, long seed)
	{
		this(size.getX(), size.getY(), size.getZ(), seed);
	}

	/**
	 * Initialize this VectorGenerator and do all precalculations required for random Vector
	 * generation.
	 * 
	 * @param probabilities - the array of probabilites
	 */
	protected void init(Array3D probabilities)
	{
		Assert.notNull(probabilities, "probabilities must not be null!");
		this.probabilities = probabilities;

		this.vectorProbabilitySums = new double[this.probabilities.getVolume()];
		this.vectorGenerated = new int[this.vectorProbabilitySums.length];
		this.vectorCollisions = 0;
		this.collisionsAllowed = false;

		this.stepMax = (this.probabilities.getVolume() + 1) / 2;

		int index = 0;
		int xSize = this.probabilities.getXSize();
		int ySize = this.probabilities.getYSize();
		int zSize = this.probabilities.getZSize();
		this.vectorProbabilitySum = 0.0;

		for(int x = 0; x < xSize; x++)
		{
			for(int y = 0; y < ySize; y++)
			{
				for(int z = 0; z < zSize; z++)
				{
					this.vectorProbabilitySum += this.probabilities.get(index);
					this.vectorProbabilitySums[index] = this.vectorProbabilitySum;
					index++;
				}
			}
		}
	}

	/**
	 * Get the array of probabilities
	 * 
	 * @return probabilities
	 */
	public Array3D getProbabilities()
	{
		return probabilities;
	}

	/**
	 * Check wether a Vector has been generated with matching coordinates to vec.
	 * All Vectors been gener
	 * 
	 * @param vec - the Vector to check
	 * @return true if the Vector has been generated, false otherwise
	 */
	public boolean wasVectorGenerated(Vector.Integer vec)
	{
		return wasVectorGenerated(this.probabilities.getIndex(vec.getX(), vec.getY(), vec.getZ()));
	}

	/**
	 * Check wether a Vector has been generated with coordinates belonging to this index.
	 * 
	 * @param index - the index to check
	 * @return true if the Vector has been generated, false otherwise
	 */
	protected boolean wasVectorGenerated(int index)
	{
		return vectorGenerated[index] > 0;
	}

	/**
	 * The counter for the number of collions
	 * 
	 * @return vectorCollisions
	 */
	public int getVectorCollisions()
	{
		return vectorCollisions;
	}

	/**
	 * Are collisions allowed or is regeneration required on collision.
	 * 
	 * @return true if collisions are allowed, false otherwise
	 */
	public boolean isCollisionsAllowed()
	{
		return collisionsAllowed;
	}

	/**
	 * Are collisions allowed or is regeneration required on collision.
	 * 
	 * @param collisionsAllowed - true for collisions allowed, false otherwise
	 */
	public void setCollisionsAllowed(boolean collisionsAllowed)
	{
		this.collisionsAllowed = collisionsAllowed;
	}

	/*
	 * (non-Javadoc)
	 * @see com.syncnapsis.utils.data.Generator#generate(java.lang.Object[])
	 */
	@Override
	public Vector.Integer generate(ExtendedRandom random, Object... args)
	{
		double randSum;
		int step;
		int i;
		do
		{
			// generate a random sum
			randSum = random.nextDouble() * this.vectorProbabilitySum;
			// find the index for this sum with binary search
			step = this.stepMax;
			i = step - 1;
			do
			{
				step = (step + 1) / 2;

				if(this.vectorProbabilitySums[i] < randSum)
					i += step;
				else if(i > 0 && this.vectorProbabilitySums[i - 1] > randSum)
					i -= step;
				else
					break;
			} while(true);
			// check wether this index has been generated before
			if(this.vectorGenerated[i] > 0)
			{
				this.vectorCollisions++;
				// collisions are not allowed: continue = generate a new vector
				if(!this.collisionsAllowed)
					continue;
			}
			// no collision -> vector is valid
			break;
		} while(true);

		// mark the index being generated
		this.vectorGenerated[i]++;

		// get the coords for the index and return them as a Vector.Integer
		int[] vec = this.probabilities.getIndexes(i);
		return new Vector.Integer(vec[0], vec[1], vec[2]);
	}
}
