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
package com.syncnapsis.universe.galaxy.visualization;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syncnapsis.enums.EnumGalaxyViewMode;
import com.syncnapsis.universe.galaxy.GalaxySpecification;
import com.syncnapsis.universe.galaxy.GalaxySpecification2;
import com.syncnapsis.utils.data.ExtendedRandom;
import com.syncnapsis.utils.math.Array3D;
import com.syncnapsis.utils.math.Functions;

/**
 * Generiert ein Universum oder eine Galaxy.
 * TODO Im Moment sind es noch nur Sektoren.
 * 
 * @author ultimate
 */
public abstract class UniverseGenerator
{
	protected static transient final Logger	logger	= LoggerFactory.getLogger(UniverseGenerator.class);

	private static final boolean			paint	= true;
	private static final boolean			js		= true;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		logger.debug("----------------------");
		logger.debug("Generating Universe...");
		logger.debug("----------------------");

		// GalaxySpecification gs = new GalaxySpecification(200, 200, 100, 0.025, 10);
		// GalaxySpecification gs = new GalaxySpecification(100, 100, 50, 0.1, 10);
		GalaxySpecification gs = new GalaxySpecification(100, 100, 50, 20000, 6);
		// GalaxySpecification gs = new GalaxySpecification(100, 100, 50, 1000, 6);
		gs.addTypeEx();
		// gs.addTypeEx(1.0, 0.5);
		// gs.addTypeS0(1.0, 0.2, 1);
		// gs.addTypeSB0(1.0, 0.2, 0.5, 0);
		// gs.addTypeSx(5, 2, 1.5, 1, 0);
		// gs.addTypeSx(3, 1.5, -1, 0);
		// gs.addTypeSBx(1, 1, 0);
		// gs.addTypeRx(5);
		// gs.addTypeAx(0.5, 1, 4);
		// gs.addTypeAx(0.7, 3, 5);
		// gs.addTypeAx(0.3, 4, 8);
		// gs.addTypeAx(0.2, 7, 12);
		// gs.addTypeAx(0.6, 5, 9);
		// gs.addTypeAx(0.4, 3, 8);

		List<int[]> coords = gs.generateCoordinates();
		gs.generateCoordinates2(); // warm up

		long start1 = System.currentTimeMillis();
		List<int[]> coords1 = gs.generateCoordinates();
		long duration1 = System.currentTimeMillis() - start1;

		long start2 = System.currentTimeMillis();
		List<int[]> coords2 = gs.generateCoordinates2();
		long duration2 = System.currentTimeMillis() - start2;

		GalaxySpecification2 gs2 = new GalaxySpecification2(100, 100, 50, 20000, 6);
		gs2.addTypeEx(1, 0.5);
		// gs2.addTypeSx(3, 1.5, -1, 0);
		long start3 = System.currentTimeMillis();
		List<int[]> coords3 = gs2.generateCoordinates();
		long duration3 = System.currentTimeMillis() - start3;

		logger.debug("1: coords=" + coords1.size() + " time=" + duration1);
		logger.debug("2: coords=" + coords2.size() + " time=" + duration2);
		logger.debug("3: coords=" + coords3.size() + " time=" + duration3);

		long time = System.currentTimeMillis();
		if(paint)
		{
			logger.debug("----------------------");
			logger.debug("Generating Universe is done!");
			logger.debug("Painting...");
			paint(gs, coords, "" + time);
			paint(gs, coords1, "coords1");
			paint(gs, coords2, "coords2");
			paint(gs, coords3, "coords3");
			logger.debug("Painting is done!");
		}

		if(js)
		{
			logger.debug("----------------------");
			try
			{
				logger.debug("writing coords...");
				createJS(coords, time + ".js");
				createJS(coords1, "coords1.js");
				createJS(coords2, "coords2.js");
				logger.debug("  coords written");
			}
			catch(IOException e)
			{
				logger.error("Could not write coords to file: " + e.getMessage());
			}
		}
		logger.debug("----------------------");
	}

	private static void paint(GalaxySpecification gs, List<int[]> coords, String filename)
	{
		JFrame view = new GalaxyViewFrame(gs, coords);
		view.setTitle(view.getTitle() + " - " + filename);
		view.requestFocus();

		BufferedImage imageXY = GalaxyViewImages.createView(EnumGalaxyViewMode.xy, gs, coords);
		BufferedImage imageYZ = GalaxyViewImages.createView(EnumGalaxyViewMode.yz, gs, coords);
		BufferedImage imageXZ = GalaxyViewImages.createView(EnumGalaxyViewMode.xz, gs, coords);

		try
		{
			logger.debug("writing 3 images...");
			ImageIO.write(imageXY, "png", new File("image_" + filename + "_XY.png"));
			logger.debug("  image XY written");
			ImageIO.write(imageYZ, "png", new File("image_" + filename + "_YZ.png"));
			logger.debug("  image YZ written");
			ImageIO.write(imageXZ, "png", new File("image_" + filename + "_XZ.png"));
			logger.debug("  image XZ written");
		}
		catch(IOException e)
		{
			logger.error("Could not write image to file: " + e.getMessage());
		}
	}

	private static void createJS(List<int[]> coords, String filename) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		sb.append("var sectors = [\n");
		for(int[] sec : coords)
		{
			sb.append("\t[" + sec[0] + "," + sec[1] + "," + sec[2] + "]");
			sb.append(",\n");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.deleteCharAt(sb.length() - 1);
		sb.append("\n];");
		OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(filename)));
		os.write(sb.toString().getBytes());
		os.flush();
		os.close();
	}

}
