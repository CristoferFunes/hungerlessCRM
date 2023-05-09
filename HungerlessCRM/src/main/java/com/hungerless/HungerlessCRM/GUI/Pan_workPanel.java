package com.hungerless.HungerlessCRM.GUI;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



public class Pan_workPanel extends JPanel
{
	public Pan_workPanel(int type, String LorR)
	{
		GraphicObjects.add("Pan_"+LorR+"workPanel", this);
		this.setPreferredSize(GraphicConstants.getWorkSpacePanelSize());
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		drawPanel(type, LorR);
	}
	
	private void drawPanel(int type, String LorR)
	{
		switch(type)
		{
			case 1 -> 
			{
				GraphicObjects.add("Pan_"+LorR+"_ContentPanel", new JPanel()); 
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setPreferredSize(GraphicConstants.getWorkSpacePanelSize());
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setBackground(GraphicConstants.getWorkPanelColor());
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setLayout(new FlowLayout(FlowLayout.LEADING,0,0)); //sin margenes
				
				this.add(GraphicObjects.get("Pan_"+LorR+"_ContentPanel"));
			}
			case 2 ->
			{				
				GraphicObjects.add("Pan_"+LorR+"_ContentPanel", new JPanel());
				GraphicObjects.add("Scr_"+LorR+"_ScrollArea", new JScrollPane(GraphicObjects.get("Pan_"+LorR+"_ContentPanel"))); 
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setPreferredSize(GraphicConstants.getObjectsSize()); //reemplazar por multiplicador del height por objeto 
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setBackground(GraphicConstants.getWorkPanelColor());
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setLayout(new FlowLayout(FlowLayout.LEADING, GraphicConstants.getMargins(),GraphicConstants.getMargins())); //con margenes
				GraphicObjects.get("Scr_"+LorR+"_ScrollArea").setPreferredSize(GraphicConstants.getLargeScrollAreaSize());
				GraphicObjects.get("Scr_"+LorR+"_ScrollArea").setBorder(BorderFactory.createEmptyBorder());
				
				GraphicObjects.add("Pan_"+LorR+"_OptionsPanel", new JPanel());
				GraphicObjects.get("Pan_"+LorR+"_OptionsPanel").setPreferredSize(GraphicConstants.getOptionPanelSize());
				GraphicObjects.get("Pan_"+LorR+"_OptionsPanel").setBackground(GraphicConstants.getOptionPanelColor());
				GraphicObjects.get("Pan_"+LorR+"_OptionsPanel").setLayout(new FlowLayout(FlowLayout.LEADING,GraphicConstants.getMargins(), GraphicConstants.getMargins()));
				
				this.add(GraphicObjects.get("Pan_"+LorR+"_OptionsPanel"));
				this.add(GraphicObjects.get("Scr_"+LorR+"_ScrollArea"));
			}
			case 3 ->
			{
				GraphicObjects.add("Pan_"+LorR+"_ContentPanel", new JPanel());
				GraphicObjects.add("Scr_"+LorR+"_ScrollArea", new JScrollPane(GraphicObjects.get("Pan_"+LorR+"_ContentPanel"))); 
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setPreferredSize(GraphicConstants.getObjectsSize());
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setBackground(GraphicConstants.getWorkPanelColor());
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setLayout(new FlowLayout(FlowLayout.LEADING,GraphicConstants.getMargins(),GraphicConstants.getMargins()));//con margenes
				GraphicObjects.get("Scr_"+LorR+"_ScrollArea").setPreferredSize(GraphicConstants.getWorkSpacePanelSize());
				GraphicObjects.get("Scr_"+LorR+"_ScrollArea").setBorder(BorderFactory.createEmptyBorder());
				
				this.add(GraphicObjects.get("Scr_"+LorR+"_ScrollArea"));
			}
			case 4 ->
			{
				GraphicObjects.add("Pan_"+LorR+"_ContentPanel", new JPanel());
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setPreferredSize(GraphicConstants.getLargeScrollAreaSize());
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setBackground(GraphicConstants.getWorkPanelColor());
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setLayout(new FlowLayout(FlowLayout.LEADING, GraphicConstants.getMargins(), GraphicConstants.getMargins()));
				
				GraphicObjects.add("Pan_"+LorR+"_OptionsPanel", new JPanel());
				GraphicObjects.get("Pan_"+LorR+"_OptionsPanel").setPreferredSize(GraphicConstants.getOptionPanelSize());
				GraphicObjects.get("Pan_"+LorR+"_OptionsPanel").setBackground(GraphicConstants.getOptionPanelColor());
				
				this.add(GraphicObjects.get("Pan_"+LorR+"_OptionsPanel"));
				this.add(GraphicObjects.get("Pan_"+LorR+"_ContentPanel"));
			}
			case 5 ->
			{
				GraphicObjects.add("Pan_"+LorR+"_ContentPanel", new JPanel());
				GraphicObjects.add("Scr_"+LorR+"_ScrollArea", new JScrollPane(GraphicObjects.get("Pan_"+LorR+"_ContentPanel"))); 
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setPreferredSize(GraphicConstants.getObjectsSize()); //reemplazar por multiplicador del height por objeto 
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setBackground(GraphicConstants.getWorkPanelColor());
				GraphicObjects.get("Pan_"+LorR+"_ContentPanel").setLayout(new FlowLayout(FlowLayout.LEADING,GraphicConstants.getMargins(),GraphicConstants.getMargins())); //con margenes
				GraphicObjects.get("Scr_"+LorR+"_ScrollArea").setPreferredSize(GraphicConstants.getSmallScrollAreaSize());
				GraphicObjects.get("Scr_"+LorR+"_ScrollArea").setBorder(BorderFactory.createEmptyBorder());
				
				GraphicObjects.add("Pan_"+LorR+"_OptionsPanel", new JPanel());
				GraphicObjects.get("Pan_"+LorR+"_OptionsPanel").setPreferredSize(GraphicConstants.getOptionPanelSize());
				GraphicObjects.get("Pan_"+LorR+"_OptionsPanel").setBackground(GraphicConstants.getOptionPanelColor());
				GraphicObjects.get("Pan_"+LorR+"_OptionsPanel").setLayout(new FlowLayout(FlowLayout.LEADING,GraphicConstants.getMargins(), GraphicConstants.getMargins()));
				
				GraphicObjects.add("Pan_"+LorR+"_Footer", new JPanel());
				GraphicObjects.get("Pan_"+LorR+"_Footer").setPreferredSize(GraphicConstants.getOptionPanelSize());
				GraphicObjects.get("Pan_"+LorR+"_Footer").setLayout(new FlowLayout(FlowLayout.LEADING,GraphicConstants.getMargins(), GraphicConstants.getMargins()));
				GraphicObjects.get("Pan_"+LorR+"_Footer").setBackground(GraphicConstants.getOptionPanelColor());
				
				this.add(GraphicObjects.get("Pan_"+LorR+"_OptionsPanel"));
				this.add(GraphicObjects.get("Scr_"+LorR+"_ScrollArea"));
				this.add(GraphicObjects.get("Pan_"+LorR+"_Footer"));
			}
		}
	}
	private static final long serialVersionUID = -8329130260110815962L;
}