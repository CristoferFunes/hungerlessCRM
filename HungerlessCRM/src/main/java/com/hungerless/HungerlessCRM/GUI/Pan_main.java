package com.hungerless.HungerlessCRM.GUI;

import java.awt.FlowLayout;

import javax.swing.JPanel;

public class Pan_main extends JPanel
{
	public Pan_main()
	{
		GraphicObjects.add("Pan_Main", this);
		this.setPreferredSize(GraphicConstants.getMainSize());
		this.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
		this.add(new Pan_menuBar());
		this.add(new Pan_workSpace());
	}
	private static final long serialVersionUID = 7075398094773142275L;
}