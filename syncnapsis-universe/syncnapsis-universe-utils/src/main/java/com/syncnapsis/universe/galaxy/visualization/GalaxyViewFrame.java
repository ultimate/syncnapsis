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

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.syncnapsis.enums.EnumGalaxyViewMode;
import com.syncnapsis.universe.galaxy.GalaxySpecification;

/**
 * Frame zur Anzeige einer generierten Galaxie.
 * 
 * @author ultimate
 */
public class GalaxyViewFrame extends JFrame
{
	private static final long	serialVersionUID	= 1L;

	private GalaxySpecification	gs;
	private List<int[]>			sectors;

	private GalaxyViewPanel		xyPanel, yzPanel, xzPanel;
	private JScrollPane			sp;
	private JPanel				p;

	public GalaxyViewFrame(GalaxySpecification gs, List<int[]> sectors)
	{
		super("GalaxyView");
		this.gs = gs;
		this.sectors = sectors;

		this.setVisible(true);
		this.setLocation(0, 0);

		this.p = new JPanel();
		this.p.setLayout(null);

		this.xyPanel = new GalaxyViewPanel(EnumGalaxyViewMode.xy, this.gs, this.sectors);
		this.xyPanel.setBounds(10, 10, this.gs.getRealXSize(), this.gs.getRealYSize());

		this.yzPanel = new GalaxyViewPanel(EnumGalaxyViewMode.yz, this.gs, this.sectors);
		this.yzPanel.setBounds(20 + this.gs.getRealXSize(), 10, this.gs.getRealZSize(), this.gs.getRealYSize());

		this.xzPanel = new GalaxyViewPanel(EnumGalaxyViewMode.xz, this.gs, this.sectors);
		this.xzPanel.setBounds(10, 20 + this.gs.getRealYSize(), this.gs.getRealXSize(), this.gs.getRealZSize());

		this.p.add(xyPanel, null);
		this.p.add(yzPanel, null);
		this.p.add(xzPanel, null);

		this.sp = new JScrollPane(p);
		
		this.p.setSize(this.gs.getRealXSize() + this.gs.getRealZSize() + 30,
						this.gs.getRealYSize() + this.gs.getRealZSize() + 30);
		this.p.setPreferredSize(this.p.getSize());
		this.sp.setSize(this.p.getSize());
		this.sp.setPreferredSize(this.sp.getSize());
		this.setSize(1024, 768);
		this.setPreferredSize(this.getSize());

		this.setContentPane(sp);
//		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
