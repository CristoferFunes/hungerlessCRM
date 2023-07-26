package com.hungerless.HungerlessCRM.GUI.account;

import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;

public class View_Account
{
	 public View_Account()
	 {
		 GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		 GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		 
		 new View_Loading("L", "Aun no disponible");
	 }
}
