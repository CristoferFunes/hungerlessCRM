package com.hungerless.HungerlessCRM.GUI.editor;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;

public class View_Editor
{
	public View_Editor()
	{
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		
		GraphicObjects.add("But_L_Save", new OptionButton(false, "Guardar", () -> handleSave()));
		GraphicObjects.add("But_L_Multipliers", new OptionButton(false, "Multiplicadores", () -> setMultipliers()));
		
		if(StateControl.getCurrentUser().isAdmin())
		{
			GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Save"));
		}
		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Multipliers"));
		
		new View_Loading("L", "Cargando costos");
	}
	
	private void handleSave()
	{
		
	}
	
	private void setMultipliers()
	{
		
	}
}
