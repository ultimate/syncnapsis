package com.syncnapsis.utils.data;

import java.util.Properties;

public interface DataGenerator
{
	public void generateDummyData(int amount);
	
	public void generateTestData(Properties testDataProperties);
}
