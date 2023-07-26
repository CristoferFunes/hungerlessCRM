package com.hungerless.HungerlessCRM.GUI.calculator;

import java.awt.Dimension;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.calculator.QuotationsAPI;
import com.hungerless.HungerlessCRM.sales.Sale;
import com.hungerless.HungerlessCRM.sales.SalesContainer;

public class View_Quotations
{
	private int quotationCount = 0;
	
	View_Quotations()
	{
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		
		GraphicObjects.add("But_L_Add", new OptionButton(false, "Nueva Cotiz.", () -> addFunction()));

		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Add"));
		new View_Loading("L", "Cargando cotizaciones");
		new Thread(()->
		{
			new QuotationsAPI().get().stream().map(o -> (Sale)o).forEach(q -> 
			{
				SalesContainer.add(q);
				GraphicObjects.add("Lab_L_SaleID"+q.getSaleID(), new Pan_quotationListItem(false, q, () -> clickOnQuotation(q)));
				GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_SaleID"+q.getSaleID()));
				quotationCount ++;
			});
			GraphicObjects.get("Pan_L_ContentPanel").remove(GraphicObjects.get("Pan_L_Loading"));
			GraphicObjects.get("Pan_L_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],(quotationCount * 64) + ((quotationCount + 1) * GraphicConstants.getMargins())));
			((OptionButton)GraphicObjects.get("But_L_Add")).setEnabled(true);
			GraphicObjects.get("Pan_workSpace").revalidate();
			GraphicObjects.get("Pan_workSpace").repaint();
		}).start();
		GraphicObjects.get("Pan_workSpace").revalidate();
		GraphicObjects.get("Pan_workSpace").repaint();
	}
	
	private void addFunction()
	{
		StateControl.setCurrentSale("new");
		GraphicObjects.softDropL();
		new View_ClientSelect();
	}
	
	private void clickOnQuotation(Sale q)
	{
		StateControl.setCurrentSale(q.getSaleID());
		StateControl.setCurrentClient(q.getClient_id());
		GraphicObjects.softDropL();
		new View_SaleEditor();
	}
}
