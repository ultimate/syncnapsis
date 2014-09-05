/**
 * Syncnapsis Framework - Copyright (c) 2012-2014 ultimate
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

import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import com.syncnapsis.enums.EnumGalaxyViewMode;
import com.syncnapsis.universe.galaxy.GalaxySpecification;

/**
 * Panel zur Anzeige einer generierten Galaxie in einer Richtung. Wird in
 * GalaxyViewFrame verwendet.
 * 
 * @author ultimate
 */
public class GalaxyViewPanel extends JPanel
{

	private static final long	serialVersionUID	= 1L;

	private EnumGalaxyViewMode	view;
	private GalaxySpecification	gs;
	private List<int[]>			sectors;

	public GalaxyViewPanel(EnumGalaxyViewMode view, GalaxySpecification gs, List<int[]> sectors)
	{
		this.view = view;
		this.gs = gs;
		this.sectors = sectors;
	}

	public void paint(Graphics g)
	{
		g.drawImage(GalaxyViewImages.createView(view, gs, sectors), 0, 0, null);
//		if(g instanceof Graphics2D)
//		{
//			Graphics2D g2D = (Graphics2D) g;
//			g2D.setColor(Color.black);
//			g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
//			g2D.setColor(Color.white);
//			g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
//			int a = 0;
//			int b = 1;
//			int da = 0;
//			int db = 0;
//			if(this.view.equals(EnumGalaxyViewMode.xy))
//			{
//				a = 0;
//				b = 1;
//				da = gs.getRealXSize() / 2;
//				db = gs.getRealYSize() / 2;
//			}
//			else if(this.view.equals(EnumGalaxyViewMode.yz))
//			{
//				a = 2;
//				b = 1;
//				da = gs.getRealZSize() / 2;
//				db = gs.getRealYSize() / 2;
//			}
//			else
//			{
//				a = 0;
//				b = 2;
//				da = gs.getRealXSize() / 2;
//				db = gs.getRealZSize() / 2;
//			}
//
//			for(int[] sector : sectors)
//				g2D.drawRect(sector[a] + da, sector[b] + db, 0, 0);
//		}
	}
}
