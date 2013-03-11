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
package com.syncnapsis.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Util for creating icons from given parameters.
 * 
 * @author ultimate
 */
public abstract class IconUtil
{
	// @formatter:off
	private static final String[]	STYLES				= { 
//		"fill_1 border_1", 
//		"fill_1 border_t", 
//		"fill_1 border_0", 
		"fill_t border_1",
		"fill_t border_t", 
		"fill_t border_0", 
		"fill_0 border_1", 
//		"fill_0 border_t", 
//		"fill_0 border_0", // will be invisible
	};
	// @formatter:on

	private static final int		HREF_MAX				= 8;
	private static final int		HREF_MIN_1				= 3;
	private static final int		SCALE_MAX				= 83;
	private static final int		SCALE_MIN				= 20;
	private static final int		ROTATION_MAX			= 360;
	private static final int		PROBABILITY_BOUND		= 100;
	private static final int		COUNT_MAX				= 8;
	private static final int		COUNT_MIN_1				= 2;
	private static final int		STYLE_MAX				= STYLES.length - 1;
	private static final int		BORDER_MAX				= 5;
	private static final int		ASCII_OFFSET			= 48;

	private static final String		ATTR_NON_SCALING_STROKE	= " vector-effect=\"non-scaling-stroke\"";
	private static final boolean	SCALE_STROKES			= false;
	private static final double		STROKE_FACTOR			= 0.5;

	/**
	 * Key for the "href"-parameter
	 */
	public static final String		KEY_HREF				= "href";
	/**
	 * Key for the "scale"-parameter
	 */
	public static final String		KEY_SCALE				= "scale";
	/**
	 * Key for the "rotation"-parameter
	 */
	public static final String		KEY_ROTATION			= "rotation";
	/**
	 * Key for the "count"-parameter
	 */
	public static final String		KEY_COUNT				= "count";
	/**
	 * Key for the "style"-parameter
	 */
	public static final String		KEY_STYLE				= "style";
	/**
	 * Key for the "offset"-parameter
	 */
	public static final String		KEY_OFFSET				= "offset";
	/**
	 * Key for the "border"-parameter
	 */
	public static final String		KEY_BORDER				= "border";

	public static String createIcon(String s)
	{
		String elements = createIconElements(s);
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"no\"?>\n");
		sb.append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 20010904//EN\" \"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">\n");
		sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"1000\" height=\"800\" style=\"background: #000000;\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n");
		sb.append("  <title>USER_ID</title>\n");
		sb.append("  <defs>\n");
		sb.append("    <style type=\"text/css\">\n");
		sb.append("      <![CDATA[\n");
		sb.append("        .fill_1 {fill:#FFFFFF; fill-opacity:1;}\n");
		sb.append("        .fill_t {fill:#FFFFFF; fill-opacity:0.5;}\n");
		sb.append("        .fill_0 {fill:#FFFFFF; fill-opacity:0;}\n");
		sb.append("        .border_1 {stroke:#FFFFFF; stroke-opacity:1; }\n");
		sb.append("        .border_t {stroke:#FFFFFF; stroke-opacity:0.5; }\n");
		sb.append("        .border_0 {stroke:#FFFFFF; stroke-opacity:0; }\n");
		sb.append("        .stroke_1 {stroke-width: 1px;}\n");
		sb.append("        .stroke_5 {stroke-width: 5px;}\n");
		sb.append("        .stroke_10 {stroke-width: 10px;}\n");
		sb.append("      ]]>\n");
		sb.append("    </style>\n");
		sb.append("    <circle id=\"0\" cx=\"0\" cy=\"0\" r=\"0.5\"" + (SCALE_STROKES ? "" : ATTR_NON_SCALING_STROKE) + "/>\n");
		sb.append("    <polygon id=\"3\" points=\"0 0.5 0.4330 -0.25 -0.4330 -0.25\"" + (SCALE_STROKES ? "" : ATTR_NON_SCALING_STROKE) + "/>\n");
		sb.append("    <polygon id=\"4\" points=\"-0.3535 0.3535 0.3535 0.3535 0.3535 -0.3535 -0.3535 -0.3535\""
				+ (SCALE_STROKES ? "" : ATTR_NON_SCALING_STROKE) + "/>\n");
		sb.append("    <polygon id=\"5\" points=\"0 0.5 0.4755 0.1545 0.2939 -0.4045 -0.2939 -0.4045 -0.4755 0.1545\""
				+ (SCALE_STROKES ? "" : ATTR_NON_SCALING_STROKE) + "/>\n");
		sb.append("    <polygon id=\"6\" points=\"0 0.5 0.4330 0.25 0.4330 -0.25 0 -0.5 -0.4330 -0.25 -0.4330 0.25\""
				+ (SCALE_STROKES ? "" : ATTR_NON_SCALING_STROKE) + "/>\n");
		sb.append("    <polygon id=\"7\" points=\"0 0.5 0.3909 0.3117 0.4875 -0.1113 0.2169 -0.4505 -0.2169 -0.4505 -0.4875 -0.1113 -0.3909 0.3117\""
				+ (SCALE_STROKES ? "" : ATTR_NON_SCALING_STROKE) + "/>\n");
		sb.append("    <polygon id=\"8\" points=\"0 0.5 0.3535 0.3535 0.5 0 0.3535 -0.3535 0 -0.5 -0.3535 -0.3535 -0.5 0 -0.3535 0.3535\""
				+ (SCALE_STROKES ? "" : ATTR_NON_SCALING_STROKE) + "/>\n");
		sb.append("  </defs>\n");
		sb.append("  <desc>Generated from user settings</desc>\n");
		sb.append(elements);
		sb.append("</svg>");
		return sb.toString();
	}

	/**
	 * Create the icon elements for a list of icon-element-parameters in SVG-style using the
	 * prerequisites of defined geometries.<br>
	 * Prerequisites are:<br>
	 * <code> 
	 * &lt;style type="text/css"&gt;<br>
	 * 		&lt;![CDATA[<br>
	 * 		  .fill_1 {fill:#FFFFFF; fill-opacity:1;}<br>
	 * 		  .fill_t {fill:#FFFFFF; fill-opacity:0.5;}<br>
	 * 		  .fill_0 {fill:#FFFFFF; fill-opacity:0;}<br>
	 * 		  .border_1 {stroke:#FFFFFF; stroke-opacity:1; }<br>
	 * 		  .border_t {stroke:#FFFFFF; stroke-opacity:0.5; }<br>
	 * 		  .border_0 {stroke:#FFFFFF; stroke-opacity:0; }<br>
	 * 		  .stroke_1 {stroke-width: 1px;}<br>
	 * 		  .stroke_5 {stroke-width: 5px;}<br>
	 * 		  .stroke_10 {stroke-width: 10px;}<br>
	 * 		]]&gt;<br>
	 * &lt;/style&gt;<br>
	 * &lt;circle id="0" cx="0" cy="0" r="0.5" vector-effect="non-scaling-stroke"/&gt;<br>
	 * &lt;polygon id="3" points="0 0.5 0.4330 -0.25 -0.4330 -0.25" vector-effect="non-scaling-stroke"/&gt;<br>
	 * &lt;polygon id="4" points="-0.3535 0.3535 0.3535 0.3535 0.3535 -0.3535 -0.3535 -0.3535" vector-effect="non-scaling-stroke"/&gt;<br>
	 * &lt;polygon id="5" points="0 0.5 0.4755 0.1545 0.2939 -0.4045 -0.2939 -0.4045 -0.4755 0.1545" vector-effect="non-scaling-stroke"/&gt;<br>
	 * &lt;polygon id="6" points="0 0.5 0.4330 0.25 0.4330 -0.25 0 -0.5 -0.4330 -0.25 -0.4330 0.25" vector-effect="non-scaling-stroke"/&gt;<br>
	 * &lt;polygon id="7" points="0 0.5 0.3909 0.3117 0.4875 -0.1113 0.2169 -0.4505 -0.2169 -0.4505 -0.4875 -0.1113 -0.3909 0.3117" vector-effect="non-scaling-stroke"/&gt;<br>
	 * &lt;polygon id="8" points="0 0.5 0.3535 0.3535 0.5 0 0.3535 -0.3535 0 -0.5 -0.3535 -0.3535 -0.5 0 -0.3535 0.3535" vector-effect="non-scaling-stroke"/&gt;<br>
	 * </code>
	 * 
	 * @see IconUtil#parameterize(String)
	 * @param parameters - the list of parameters
	 * @return the SVG-Code for the elements
	 */
	public static String createIconElements(List<Map<String, Number>> parameters)
	{
		StringBuilder sb = new StringBuilder();
		StringBuilder transform;
		for(Map<String, Number> parameterMap : parameters)
		{
			for(int i = 0; i < (Integer) parameterMap.get(KEY_COUNT); i++)
			{
				sb.append("<use xlink:href=\"#");
				sb.append(parameterMap.get(KEY_HREF));
				sb.append("\" class=\"");
				sb.append(STYLES[parameterMap.get(KEY_STYLE).intValue()]);
				sb.append("\" transform=\"");
				transform = new StringBuilder();
				transform.insert(0, " scale(" + parameterMap.get(KEY_SCALE) + "," + parameterMap.get(KEY_SCALE) + ")");
				transform.insert(0, " rotate(" + parameterMap.get(KEY_ROTATION) + ")");
				if((Integer) parameterMap.get(KEY_COUNT) > 1)
				{
					transform.insert(0, " translate(" + parameterMap.get(KEY_OFFSET) + ",0)");
					transform.insert(0, " rotate(" + i * 360 / (Integer) parameterMap.get(KEY_COUNT) + ")");
				}
				transform.insert(0, " translate(50,50)");
				sb.append(transform);
				sb.append("\" style=\"stroke-width:");
				sb.append(parameterMap.get(KEY_BORDER));
				sb.append("px\" />");
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	/**
	 * Similar to <code>IconUtil.createIconElement(IconUtil.parameterize(s))</code>
	 * 
	 * @see IconUtil#createIconElements(List)
	 * @see IconUtil#parameterize(String)
	 * @param s - the String defining the icon parameters
	 * @return the SVG-Code for the elements
	 */
	public static String createIconElements(String s)
	{
		return createIconElements(parameterize(s));
	}

	/**
	 * Create a list of icon-element-parameters from a given String.<br>
	 * Each Map of element-parameters contains the following parameters:
	 * <ul>
	 * <li>{@link IconUtil#KEY_HREF}</li>
	 * <li>{@link IconUtil#KEY_SCALE}</li>
	 * <li>{@link IconUtil#KEY_ROTATION}</li>
	 * <li>{@link IconUtil#KEY_COUNT}</li>
	 * <li>{@link IconUtil#KEY_STYLE}</li>
	 * <li>{@link IconUtil#KEY_OFFSET}</li>
	 * <li>{@link IconUtil#KEY_BORDER}</li>
	 * </ul>
	 * The returned result is invariant for the given String. For two Strings s1 and s2 with
	 * <code>s1.equals(s2)==true</code> the returned results will also be equal.
	 * 
	 * @param s - the String defining the icon parameters
	 * @return the list of icon-element-parameters
	 */
	public static List<Map<String, Number>> parameterize(String s)
	{
		byte[] bytes = s.toUpperCase().getBytes();
		int l = bytes.length;
		int elements = (l * 5 + 3) % 7 + 2;

		List<Map<String, Number>> parameters = new ArrayList<Map<String, Number>>(elements);
		Map<String, Number> parameterMap;

		int href = 0;
		int count, style;
		double scale = 0;
		double rotation, offset, border;
		int parent_href;
		double parent_scale;
		boolean useParentHrefAsCount;

		int off = 0;
		for(int e = 0; e < elements; e++)
		{
			parameterMap = new HashMap<String, Number>();

			parent_href = href;
			parent_scale = scale;
			// geometry used
			href = getByte(bytes, e, off++) % (HREF_MAX - HREF_MIN_1 + 2);
			if(href > 0)
				href += (HREF_MIN_1 - 1);

			// scaling
			scale = getByte(bytes, e, off++) * (SCALE_MAX - SCALE_MIN + 1) / 256 + SCALE_MIN;

			// rotation
			if(href > 0)
				rotation = getByte(bytes, e, off++) * (ROTATION_MAX / href) / 256;
			else
				rotation = 0;

			// multiple symbols?
			useParentHrefAsCount = getByte(bytes, e, off++) < PROBABILITY_BOUND;
			if(useParentHrefAsCount && (parent_href > 0))
				count = parent_href;
			else
				count = getByte(bytes, e, off++) % (COUNT_MAX - COUNT_MIN_1 + 2);
			if(count > 1)
				count += COUNT_MIN_1;
			if(count > 1 && useParentHrefAsCount && parent_scale > 0)
				offset = parent_scale / 2;
			else
				offset = scale / 2;
			if(count > 1)
				scale = scale * 2 / count;

			// style
			style = getByte(bytes, e, off++) % (STYLE_MAX + 1);
			border = STROKE_FACTOR * ((getByte(bytes, e, off++) % (BORDER_MAX)) + 1);

			parameterMap.put(KEY_HREF, href);
			parameterMap.put(KEY_SCALE, round(scale));
			parameterMap.put(KEY_ROTATION, round(rotation));
			parameterMap.put(KEY_COUNT, count);
			parameterMap.put(KEY_STYLE, style);
			parameterMap.put(KEY_OFFSET, round(offset)); // not always used
			parameterMap.put(KEY_BORDER, round(border)); // not always used
			parameters.add(parameterMap);
		}

		return parameters;
	}

	/**
	 * Get a byte from the array with the given parameters by <code>((int) b+256)%256</code>
	 * 
	 * @param b - the byte to convert
	 * @return the resulting int
	 */
	private static int getByte(byte[] bytes, int e, int offset)
	{
		int b = bytes[(e + offset) % bytes.length] + offset * 13;
		int i = (((int) b - ASCII_OFFSET) + 256) % 256;
		// System.out.println(i);
		return i;
	}

	private static double round(double d)
	{
		return Math.round(d * 100) / 100.0;
	}

	private static void createIconFile(String s) throws IOException
	{
		FileUtil.writeFile(new File(s + ".svg"), createIcon(s));
	}

	public static void main(String[] args) throws IOException
	{
		createIconFile("ultimate");
		createIconFile("moronicjoker");
		createIconFile("X");
		createIconFile("no");
		createIconFile("foo");
		createIconFile("bazz");
		createIconFile("thing");
		createIconFile("abcdef");
		createIconFile("nothing");
	}
}
