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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

import com.syncnapsis.enums.EnumGalaxyViewMode;
import com.syncnapsis.universe.galaxy.GalaxySpecification;

public class GalaxyViewImages
{
	public static BufferedImage createView(EnumGalaxyViewMode view, GalaxySpecification gs, List<int[]> sectors)
	{
		int a = 0;
		int b = 1;
		int da = 0;
		int db = 0;
		if(view.equals(EnumGalaxyViewMode.xy))
		{
			a = 0;
			b = 1;
			da = gs.getRealXSize() / 2;
			db = gs.getRealYSize() / 2;
		}
		else if(view.equals(EnumGalaxyViewMode.yz))
		{
			a = 2;
			b = 1;
			da = gs.getRealZSize() / 2;
			db = gs.getRealYSize() / 2;
		}
		else
		{
			a = 0;
			b = 2;
			da = gs.getRealXSize() / 2;
			db = gs.getRealZSize() / 2;
		}
		
		BufferedImage image = new BufferedImage(da*2, db*2, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2D = image.createGraphics();
		g2D.setColor(Color.black);
		g2D.fillRect(0, 0, da*2, db*2);
		g2D.setColor(Color.white);
		g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);

		for(int[] sector : sectors)
			g2D.drawRect(sector[a] + da, sector[b] + db, 0, 0);
		
		return image;
	}
}
