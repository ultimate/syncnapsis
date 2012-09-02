package com.syncnapsis;

import com.syncnapsis.data.model.base.BaseObject;
import com.syncnapsis.security.annotations.Accessible;
import com.syncnapsis.security.annotations.Authority;

public class TestEntity extends BaseObject<Integer>
{
	@Accessible(defaultAccessible = false, accessible = {@Authority(name="admin")})
	private int id;
	
	@Accessible(defaultAccessible = true, notAccessible = {@Authority(name="demo")})
	private String name;
	
	private String value;

	public TestEntity()
	{
	}
	
	public TestEntity(int id, String name)
	{
		this(id, name, null);
	}
	
	public TestEntity(int id, String name, String value)
	{
		super();
		this.id = id;
		this.name = name;
		this.value = value;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		TestEntity other = (TestEntity) obj;
		if(id != other.id)
			return false;
		if(name == null)
		{
			if(other.name != null)
				return false;
		}
		else if(!name.equals(other.name))
			return false;
		if(value == null)
		{
			if(other.value != null)
				return false;
		}
		else if(!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "TestEntity [id=" + id + ", name=" + name + ", value=" + value + "]";
	}	
}
