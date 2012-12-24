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
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.syncnapsis.enums.EnumGalaxyViewMode;
import com.syncnapsis.universe.galaxy.GalaxySpecification;
import com.syncnapsis.universe.galaxy.visualization.GalaxyViewFrame;
import com.syncnapsis.universe.galaxy.visualization.GalaxyViewImages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generiert ein Universum oder eine Galaxy.
 * TODO Im Moment sind es noch nur Sektoren.
 * 
 * @author ultimate
 */
public abstract class UniverseGenerator
{
	protected static transient final Logger	logger	= LoggerFactory.getLogger(UniverseGenerator.class);

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		logger.debug("----------------------");
		logger.debug("Generating Universe...");
		logger.debug("----------------------");

		GalaxySpecification gs = new GalaxySpecification(200, 200, 100, 0.025, 10);
		// GalaxySpecification gs = new GalaxySpecification(100, 100, 50, 0.1, 10);
		// GalaxySpecification gs = new GalaxySpecification(100, 100, 50, 20000, 6);
		// gs.addTypeEx();
		// gs.addTypeEx(1.0, 0.5);
		// gs.addTypeS0(1.0, 0.2, 1);
		// gs.addTypeSB0(1.0, 0.2, 0.5);
		gs.addTypeSx(2, 1.5, 1);
		// gs.addTypeSx(3,1.5, -1);
		// gs.addTypeSBx(1, 1);
		// gs.addTypeRx(5);
		// gs.addTypeAx(0.5, 1, 4);
		// gs.addTypeAx(0.7, 3, 5);
		// gs.addTypeAx(0.3, 4, 8);
		// gs.addTypeAx(0.2, 7, 12);
		// gs.addTypeAx(0.6, 5, 9);
		// gs.addTypeAx(0.4, 3, 8);

		List<int[]> coords = gs.generateCoordinates();

		logger.debug("----------------------");
		logger.debug("Generating Universe is done!");
		logger.debug("Painting...");

		JFrame view = new GalaxyViewFrame(gs, coords);
		view.requestFocus();

		BufferedImage imageXY = GalaxyViewImages.createView(EnumGalaxyViewMode.xy, gs, coords);
		BufferedImage imageYZ = GalaxyViewImages.createView(EnumGalaxyViewMode.yz, gs, coords);
		BufferedImage imageXZ = GalaxyViewImages.createView(EnumGalaxyViewMode.xz, gs, coords);

		long time = System.currentTimeMillis();
		try
		{
			logger.debug("writing 3 images...");
			ImageIO.write(imageXY, "png", new File("image_" + time + "_XY.png"));
			logger.debug("  image XY written");
			ImageIO.write(imageYZ, "png", new File("image_" + time + "_YZ.png"));
			logger.debug("  image YZ written");
			ImageIO.write(imageXZ, "png", new File("image_" + time + "_XZ.png"));
			logger.debug("  image XZ written");
		}
		catch(IOException e)
		{
			logger.error("Could not write image to file: " + e.getMessage());
		}

		try
		{
			logger.debug("writing coords...");
			createJS(coords, time + ".js");
			logger.debug("  coords written");
		}
		catch(IOException e)
		{
			logger.error("Could not write coords to file: " + e.getMessage());
		}

		logger.debug("Painting is done!");
		logger.debug("----------------------");
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
