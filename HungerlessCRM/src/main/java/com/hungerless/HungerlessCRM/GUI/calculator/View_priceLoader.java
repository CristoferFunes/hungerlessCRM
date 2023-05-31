package com.hungerless.HungerlessCRM.GUI.calculator;

import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.calculator.PricesContainer;

public class View_priceLoader
{
	public View_priceLoader()
	{
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		new View_Loading("L", "Cargando costos");
		new Thread(()->
		{
			PricesContainer.updatePrices();
			GraphicObjects.softDropL();
			new View_Quotations();
		}).start();
	}
}
