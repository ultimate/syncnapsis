package com.syncnapsis.utils.logging.log4j;

/**
 * Interface for Appender using LoggingEventConverters
 * 
 * @author ultimate
 */
public interface ConvertingAppender
{
	/**
	 * The Class of the Converter to use
	 * 
	 * @param className - the Class
	 * @throws ClassNotFoundException - if Class.forName(..) fails
	 * @throws IllegalAccessException - if class.newInstance(..) fails
	 * @throws InstantiationException - if class.newInstance(..) fails
	 */
	public void setConverterClass(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException;

	/**
	 * The Class of the Converter to use
	 * 
	 * @return the className
	 */
	public String getConverterClass();
}
