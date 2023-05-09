package com.hungerless.HungerlessCRM.GUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hungerless.HungerlessCRM.GUI.clients.View_Clients;



public class Pan_workSpace extends JPanel
{
	public Pan_workSpace()
	{
		GraphicObjects.add("Pan_workSpace", this);
		this.setPreferredSize(GraphicConstants.getWorkSpaceSize());
		this.setBackground(GraphicConstants.getWorkSpaceColor());
		this.setLayout(new FlowLayout(FlowLayout.LEADING, GraphicConstants.getMargins(), GraphicConstants.getMargins()));

		GraphicObjects.add("Pan_L_ContentPanel", new JPanel());
		GraphicObjects.get("Pan_L_ContentPanel").setPreferredSize(GraphicConstants.getLargeWorkPanelSize());
		GraphicObjects.get("Pan_L_ContentPanel").setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
		GraphicObjects.get("Pan_L_ContentPanel").setBackground(GraphicConstants.getWorkPanelColor());
		
		GraphicObjects.add("Lab_L_Title", new JLabel("Haz click en una opcion"));
		((JLabel)GraphicObjects.get("Lab_L_Title")).setHorizontalAlignment(JLabel.CENTER);
		GraphicObjects.get("Lab_L_Title").setPreferredSize(GraphicConstants.getLargeWorkPanelSize());
		GraphicObjects.get("Lab_L_Title").setFont(new Font(null, Font.PLAIN, 42));
		GraphicObjects.get("Lab_L_Title").setBackground(GraphicConstants.getWorkPanelColor());
		GraphicObjects.get("Lab_L_Title").setOpaque(true);
		GraphicObjects.get("Lab_L_Title").setForeground(Color.white);
		
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_Title"));
		this.add(GraphicObjects.get("Pan_L_ContentPanel"));
	}
	
	/*public void selectAccount()
	{
		new View_Account();
	}
	
	public void selectCalculator()
	{
		new View_Calculator();
	}*/
	
	public void selectClients()
	{
		new View_Clients();
	}
	/*
	public void selectSales()
	{
		new View_Sales();
	}
	
	public void selectEditor()
	{
		new View_Editor();
	}*/
	private static final long serialVersionUID = -5280371732195713756L;
}
