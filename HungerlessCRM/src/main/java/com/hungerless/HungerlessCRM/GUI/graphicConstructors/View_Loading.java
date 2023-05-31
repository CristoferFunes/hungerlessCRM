package com.hungerless.HungerlessCRM.GUI.graphicConstructors;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;


public class View_Loading
{
	public View_Loading(String LorR, String LoadingMessage)
	{
		GraphicObjects.add("Pan_"+LorR+"_Loading", new JLabel(LoadingMessage + "..."));
		GraphicObjects.get("Pan_"+LorR+"_Loading").setFont(new Font(null,Font.PLAIN,24));
		GraphicObjects.get("Pan_"+LorR+"_Loading").setPreferredSize(GraphicConstants.getObjectsSize());
		((JLabel)GraphicObjects.get("Pan_"+LorR+"_Loading")).setHorizontalAlignment(JLabel.CENTER);
		((JLabel)GraphicObjects.get("Pan_"+LorR+"_Loading")).setForeground(Color.LIGHT_GRAY);
		GraphicObjects.get("Pan_"+LorR+"_ContentPanel").add(GraphicObjects.get("Pan_"+LorR+"_Loading"));
		
		GraphicObjects.get("Pan_workSpace").revalidate();
		GraphicObjects.get("Pan_workSpace").repaint();
	}
}
