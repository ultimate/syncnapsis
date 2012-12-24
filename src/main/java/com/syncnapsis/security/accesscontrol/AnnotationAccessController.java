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
package com.syncnapsis.security.accesscontrol;

import com.syncnapsis.data.model.base.Identifiable;
import com.syncnapsis.security.AccessController;
import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.security.annotations.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base for AccessControllers using the {@link Accessible} annotation.
 * 
 * @author ultimate
 * @param <T> - the Type of Object to access
 */
public abstract class AnnotationAccessController<T> implements AccessController<T>
{
	/**
	 * Logger-Instance
	 */
	protected final Logger	logger	= LoggerFactory.getLogger(getClass());

	/**
	 * Check an annotation for accessibility considering the give authorities
	 * 
	 * @param a - the annotation to consider
	 * @param globalDefault - the global default value for accessile
	 * @param authorities - the authorities to check for accessibility
	 * @return true or false
	 */
	public boolean isAccessible(Accessible a, boolean globalDefault, Object... authorities)
	{
		if(a != null)
		{
			boolean accessible = containsAuthority(a.accessible(), authorities);
			boolean notAccessible = containsAuthority(a.notAccessible(), authorities);
			boolean defaultAccessible = a.defaultAccessible();

			if(accessible || defaultAccessible && !notAccessible)
				return true;
			else
				return false;
		}
		return globalDefault;
	}

	/**
	 * Check wether a authority given for (de)-serialization is included in a list of authority
	 * defined by an
	 * Annotation.
	 * 
	 * @param annotationAuthorities - the Annotation authorities to scan for the (de)-serialization
	 *            authorities
	 * @param serializationAuthorities - the (de)-serialization authorities to check for
	 * @return true or false
	 */
	public boolean containsAuthority(Authority[] annotationAuthorities, Object... serializationAuthorities)
	{
		Object id;
		if(serializationAuthorities != null && serializationAuthorities.length > 0)
		{
			for(Object authority : serializationAuthorities)
			{
				if(authority == null)
					continue;
				else if(authority instanceof Identifiable)
				{
					if(((Identifiable<?>) authority).getId() instanceof Number)
						id = ((Identifiable<?>) authority).getId();
					else if(((Identifiable<?>) authority).getId() instanceof String)
						id = ((Identifiable<?>) authority).getId();
					else if(((Identifiable<?>) authority).getId() != null)
						id = ((Identifiable<?>) authority).getId().toString();
					else
						continue;
				}
				else if(authority instanceof Number)
					id = authority;
				else if(authority instanceof String)
					id = authority;
				else
					id = authority.toString();
				
				if(id == null)
					continue;
				else if(id instanceof Number)
				{
					for(Authority aAuthority : annotationAuthorities)
					{
						if(aAuthority.id() == ((Number) id).longValue())
							return true;
						else if(aAuthority.name().equals(id.toString()))
							return true;
					}
				}
				else if(id instanceof String)
				{
					for(Authority aAuthority : annotationAuthorities)
					{
						if(aAuthority.name().equals(id))
							return true;
						else if(Long.toString(aAuthority.id()).equals(id))
							return true;
					}
				}
			}
		}
		return false;
	}
}
