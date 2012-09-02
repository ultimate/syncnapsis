package com.syncnapsis;

public interface TestClient
{
	public void ping(String message);
	
	public void post(TestEntity entity);
}
