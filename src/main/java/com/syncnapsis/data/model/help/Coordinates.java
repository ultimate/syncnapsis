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
package com.syncnapsis.data.model.help;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

import com.syncnapsis.data.model.base.Model;

/**
 * Embeddable coordinate entity.
 * 
 * @author ultimate
 */
@MappedSuperclass
public abstract class Coordinates<N extends Number> implements Model
{
	/**
	 * The x-coordinate
	 */
	protected N	x;
	/**
	 * The y-coordinate
	 */
	protected N	y;
	/**
	 * The z-coordinate
	 */
	protected N	z;

	/**
	 * Constructor initializing this entity with the given coordinatses
	 * 
	 * @param x - the x-coordinate
	 * @param y - the y-coordinate
	 * @param z - the z-coordinate
	 */
	public Coordinates(N x, N y, N z)
	{
		super();
		setX(x);
		setY(y);
		setZ(z);
	}

	/**
	 * The x-coordinate
	 * 
	 * @return x
	 */
	@Column(name = "coordinateX", nullable = false)
	public N getX()
	{
		return x;
	}

	/**
	 * The y-coordinate
	 * 
	 * @return y
	 */
	@Column(name = "coordinateY", nullable = false)
	public N getY()
	{
		return y;
	}

	/**
	 * The z-coordinate
	 * 
	 * @return z
	 */
	@Column(name = "coordinateZ", nullable = false)
	public N getZ()
	{
		return z;
	}

	/**
	 * The x-coordinate
	 * 
	 * @param x - the coordinate value (null is permitted)
	 */
	public void setX(N x)
	{
		if(x == null)
			throw new IllegalArgumentException("x must not be null!");
		this.x = x;
	}

	/**
	 * The y-coordinate
	 * 
	 * @param y - the coordinate value (null is permitted)
	 */
	public void setY(N y)
	{
		if(y == null)
			throw new IllegalArgumentException("y must not be null!");
		this.y = y;
	}

	/**
	 * The z-coordinate
	 * 
	 * @param z - the coordinate value (null is permitted)
	 */
	public void setZ(N z)
	{
		if(z == null)
			throw new IllegalArgumentException("z must not be null!");
		this.z = z;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		result = prime * result + ((z == null) ? 0 : z.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if(x == null)
		{
			if(other.x != null)
				return false;
		}
		else if(!x.equals(other.x))
			return false;
		if(y == null)
		{
			if(other.y != null)
				return false;
		}
		else if(!y.equals(other.y))
			return false;
		if(z == null)
		{
			if(other.z != null)
				return false;
		}
		else if(!z.equals(other.z))
			return false;
		return true;
	}

	/**
	 * Integer implementation of Coordinates
	 * 
	 * @author ultimate
	 */
	@Embeddable
	public static final class Integer extends Coordinates<java.lang.Integer>
	{
		/**
		 * Default Constructor initializing all coords with <code>0</code>
		 */
		public Integer()
		{
			this(0, 0, 0);
		}

		/**
		 * Constructor initializing this entity with the given coordinatses
		 * 
		 * @param x - the x-coordinate
		 * @param y - the y-coordinate
		 * @param z - the z-coordinate
		 */
		public Integer(java.lang.Integer x, java.lang.Integer y, java.lang.Integer z)
		{
			super(x, y, z);
		}
	}

	/**
	 * Long implementation of Coordinates
	 * 
	 * @author ultimate
	 */
	@Embeddable
	public static final class Long extends Coordinates<java.lang.Long>
	{
		/**
		 * Default Constructor initializing all coords with <code>0L</code>
		 */
		public Long()
		{
			this(0L, 0L, 0L);
		}

		/**
		 * Constructor initializing this entity with the given coordinatses
		 * 
		 * @param x - the x-coordinate
		 * @param y - the y-coordinate
		 * @param z - the z-coordinate
		 */
		public Long(java.lang.Long x, java.lang.Long y, java.lang.Long z)
		{
			super(x, y, z);
		}
	}

	/**
	 * Float implementation of Coordinates
	 * 
	 * @author ultimate
	 */
	@Embeddable
	public static final class Float extends Coordinates<java.lang.Float>
	{
		/**
		 * Default Constructor initializing all coords with <code>0.0F</code>
		 */
		public Float()
		{
			this(0.0F, 0.0F, 0.0F);
		}

		/**
		 * Constructor initializing this entity with the given coordinatses
		 * 
		 * @param x - the x-coordinate
		 * @param y - the y-coordinate
		 * @param z - the z-coordinate
		 */
		public Float(java.lang.Float x, java.lang.Float y, java.lang.Float z)
		{
			super(x, y, z);
		}
	}

	/**
	 * Double implementation of Coordinates
	 * 
	 * @author ultimate
	 */
	@Embeddable
	public static final class Double extends Coordinates<java.lang.Double>
	{
		/**
		 * Default Constructor initializing all coords with <code>0.0</code>
		 */
		public Double()
		{
			this(0.0, 0.0, 0.0);
		}

		/**
		 * Constructor initializing this entity with the given coordinatses
		 * 
		 * @param x - the x-coordinate
		 * @param y - the y-coordinate
		 * @param z - the z-coordinate
		 */
		public Double(java.lang.Double x, java.lang.Double y, java.lang.Double z)
		{
			super(x, y, z);
		}
	}
}
