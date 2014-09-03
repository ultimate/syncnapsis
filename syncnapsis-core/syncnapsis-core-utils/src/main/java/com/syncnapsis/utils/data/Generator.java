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

/**
 * Abstract base for random data generators.
 * 
 * @author ultimate
 */
public abstract class Generator<T>
{
	/**
	 * The underlying extended random number generator.
	 */
	private ExtendedRandom	random;

	/**
	 * Create a new Generator with a new {@link ExtendedRandom}
	 */
	public Generator()
	{
		this(new ExtendedRandom());
	}

	/**
	 * Create a new Generator with a new {@link ExtendedRandom} from the given seed.
	 * 
	 * @see ExtendedRandom#ExtendedRandom(long)
	 * @param seed - the seed for the Random
	 */
	public Generator(long seed)
	{
		this(new ExtendedRandom(seed));
	}

	/**
	 * Create a new Generator with the given {@link ExtendedRandom}
	 * 
	 * @param random - the extended random number generator
	 */
	public Generator(ExtendedRandom random)
	{
		Assert.notNull(random);
		this.random = random;
	}

	/**
	 * Generate a new instance of type T from the given arguments and the given random number
	 * generator (underlying random number generator will be ignored).<br>
	 * The requirement of the optional arguments will be defined by the implementing/overwriting
	 * class.
	 * 
	 * @param random - the random number generator to use
	 * @param args - optional arguments
	 * @return the generated instance
	 */
	public abstract T generate(ExtendedRandom random, Object... args);

	/**
	 * Generate a new instance of type T from the given arguments and the underlying random number
	 * generator.<br>
	 * The requirement of the optional arguments will be defined by the implementing/overwriting
	 * class.
	 * 
	 * @param args - optional arguments
	 * @return the generated instance
	 */
	public final T generate(Object... args)
	{
		return generate(this.random, args);
	}
}
