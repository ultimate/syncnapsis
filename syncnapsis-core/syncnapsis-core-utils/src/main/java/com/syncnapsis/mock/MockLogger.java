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
package com.syncnapsis.mock;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.Marker;

public class MockLogger implements Logger
{
	private static final String	newLine			= System.getProperty("line.separator");
	
	private StringBuilder		sb;
	private String				name;

	private boolean				traceEnabled	= true;
	private boolean				debugEnabled	= true;
	private boolean				infoEnabled		= true;
	private boolean				warnEnabled		= true;
	private boolean				errorEnabled	= true;

	private static final String	TRACE			= "[TRACE] ";
	private static final String	DEBUG			= "[DEBUG] ";
	private static final String	INFO			= "[INFO] ";
	private static final String	WARN			= "[WARN] ";
	private static final String	ERROR			= "[ERROR] ";

	public MockLogger(String name)
	{
		this.name = name;
		this.sb = new StringBuilder();
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	public StringBuilder getLog()
	{
		return sb;
	}

	@Override
	public boolean isTraceEnabled()
	{
		return traceEnabled;
	}

	public void setTraceEnabled(boolean traceEnabled)
	{
		this.traceEnabled = traceEnabled;
	}

	@Override
	public void trace(String msg)
	{
		sb.append(TRACE + msg);
		sb.append(newLine);
	}

	@Override
	public void trace(String format, Object arg)
	{
		trace(MessageFormat.format(format, arg));
	}

	@Override
	public void trace(String format, Object arg1, Object arg2)
	{
		trace(MessageFormat.format(format, arg1, arg2));
	}

	@Override
	public void trace(String format, Object[] argArray)
	{
		trace(MessageFormat.format(format, argArray));
	}

	@Override
	public void trace(String msg, Throwable t)
	{
		trace(msg + " " + t.getMessage());
	}

	@Override
	public boolean isTraceEnabled(Marker marker)
	{
		return traceEnabled;
	}

	@Override
	public void trace(Marker marker, String msg)
	{
		trace(msg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg)
	{
		trace(format, arg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2)
	{
		trace(format, arg1, arg2);
	}

	@Override
	public void trace(Marker marker, String format, Object[] argArray)
	{
		trace(format, argArray);
	}

	@Override
	public void trace(Marker marker, String msg, Throwable t)
	{
		trace(msg, t);
	}

	@Override
	public boolean isDebugEnabled()
	{
		return debugEnabled;
	}

	public void setDebugEnabled(boolean debugEnabled)
	{
		this.debugEnabled = debugEnabled;
	}

	@Override
	public void debug(String msg)
	{
		sb.append(DEBUG + msg);
		sb.append(newLine);
	}

	@Override
	public void debug(String format, Object arg)
	{
		debug(MessageFormat.format(format, arg));
	}

	@Override
	public void debug(String format, Object arg1, Object arg2)
	{
		debug(MessageFormat.format(format, arg1, arg2));
	}

	@Override
	public void debug(String format, Object[] argArray)
	{
		debug(MessageFormat.format(format, argArray));
	}

	@Override
	public void debug(String msg, Throwable t)
	{
		debug(msg + " " + t.getMessage());
	}

	@Override
	public boolean isDebugEnabled(Marker marker)
	{
		return debugEnabled;
	}

	@Override
	public void debug(Marker marker, String msg)
	{
		debug(msg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg)
	{
		debug(format, arg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2)
	{
		debug(format, arg1, arg2);
	}

	@Override
	public void debug(Marker marker, String format, Object[] argArray)
	{
		debug(format, argArray);
	}

	@Override
	public void debug(Marker marker, String msg, Throwable t)
	{
		debug(msg, t);
	}

	@Override
	public boolean isInfoEnabled()
	{
		return infoEnabled;
	}

	public void setInfoEnabled(boolean infoEnabled)
	{
		this.infoEnabled = infoEnabled;
	}

	@Override
	public void info(String msg)
	{
		sb.append(INFO + msg);
		sb.append(newLine);
	}

	@Override
	public void info(String format, Object arg)
	{
		info(MessageFormat.format(format, arg));
	}

	@Override
	public void info(String format, Object arg1, Object arg2)
	{
		info(MessageFormat.format(format, arg1, arg2));
	}

	@Override
	public void info(String format, Object[] argArray)
	{
		info(MessageFormat.format(format, argArray));
	}

	@Override
	public void info(String msg, Throwable t)
	{
		info(msg + " " + t.getMessage());
	}

	@Override
	public boolean isInfoEnabled(Marker marker)
	{
		return infoEnabled;
	}

	@Override
	public void info(Marker marker, String msg)
	{
		info(msg);
	}

	@Override
	public void info(Marker marker, String format, Object arg)
	{
		info(format, arg);
	}

	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2)
	{
		info(format, arg1, arg2);
	}

	@Override
	public void info(Marker marker, String format, Object[] argArray)
	{
		info(format, argArray);
	}

	@Override
	public void info(Marker marker, String msg, Throwable t)
	{
		info(msg, t);
	}

	@Override
	public boolean isWarnEnabled()
	{
		return warnEnabled;
	}

	public void setWarnEnabled(boolean warnEnabled)
	{
		this.warnEnabled = warnEnabled;
	}

	@Override
	public void warn(String msg)
	{
		sb.append(WARN + msg);
		sb.append(newLine);
	}

	@Override
	public void warn(String format, Object arg)
	{
		warn(MessageFormat.format(format, arg));
	}

	@Override
	public void warn(String format, Object arg1, Object arg2)
	{
		warn(MessageFormat.format(format, arg1, arg2));
	}

	@Override
	public void warn(String format, Object[] argArray)
	{
		warn(MessageFormat.format(format, argArray));
	}

	@Override
	public void warn(String msg, Throwable t)
	{
		warn(msg + " " + t.getMessage());
	}

	@Override
	public boolean isWarnEnabled(Marker marker)
	{
		return warnEnabled;
	}

	@Override
	public void warn(Marker marker, String msg)
	{
		warn(msg);
	}

	@Override
	public void warn(Marker marker, String format, Object arg)
	{
		warn(format, arg);
	}

	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2)
	{
		warn(format, arg1, arg2);
	}

	@Override
	public void warn(Marker marker, String format, Object[] argArray)
	{
		warn(format, argArray);
	}

	@Override
	public void warn(Marker marker, String msg, Throwable t)
	{
		warn(msg, t);
	}

	@Override
	public boolean isErrorEnabled()
	{
		return errorEnabled;
	}

	public void setErrorEnabled(boolean errorEnabled)
	{
		this.errorEnabled = errorEnabled;
	}

	@Override
	public void error(String msg)
	{
		sb.append(ERROR + msg);
		sb.append(newLine);
	}

	@Override
	public void error(String format, Object arg)
	{
		error(MessageFormat.format(format, arg));
	}

	@Override
	public void error(String format, Object arg1, Object arg2)
	{
		error(MessageFormat.format(format, arg1, arg2));
	}

	@Override
	public void error(String format, Object[] argArray)
	{
		error(MessageFormat.format(format, argArray));
	}

	@Override
	public void error(String msg, Throwable t)
	{
		error(msg + " " + t.getMessage());
	}

	@Override
	public boolean isErrorEnabled(Marker marker)
	{
		return errorEnabled;
	}

	@Override
	public void error(Marker marker, String msg)
	{
		error(msg);
	}

	@Override
	public void error(Marker marker, String format, Object arg)
	{
		error(format, arg);
	}

	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2)
	{
		error(format, arg1, arg2);
	}

	@Override
	public void error(Marker marker, String format, Object[] argArray)
	{
		error(format, argArray);
	}

	@Override
	public void error(Marker marker, String msg, Throwable t)
	{
		error(msg, t);
	}
}
