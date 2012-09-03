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
