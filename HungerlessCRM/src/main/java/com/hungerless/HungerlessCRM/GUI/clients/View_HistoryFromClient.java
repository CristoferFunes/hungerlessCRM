package com.hungerless.HungerlessCRM.GUI.clients;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.calculator.PricesContainer;
import com.hungerless.HungerlessCRM.clients.HistorySalesFromClientAPI;
import com.hungerless.HungerlessCRM.sales.Sale;
import com.hungerless.HungerlessCRM.sales.SalesContainer;

public class View_HistoryFromClient
{
	private int salesCount = 0;
	private double totalSalesCount = 0;
	
	View_HistoryFromClient()
	{
		GraphicObjects.add("Pan_R_Main", new Pan_workPanel(1, "R"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_R_Main"));
		
		GraphicObjects.add("But_R_Return", new OptionButton(false, "Regresar", () -> returnFunction()));

		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_Return"));
		new View_Loading("R", "Cargando historial");
		
		new Thread(()->
		{
			PricesContainer.updatePrices();
			GraphicObjects.add("Lab_R_totalSale", new JLabel()); //date _1
			GraphicObjects.get("Lab_R_totalSale").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],10));
			GraphicObjects.get("Lab_R_totalSale").setForeground(Color.WHITE);
			GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Lab_R_totalSale"));
			new HistorySalesFromClientAPI(StateControl.getCurrentClient()).get().stream().map(o -> (Sale)o).forEach(s -> 
			{
				SalesContainer.add(s);
				GraphicObjects.add("Lab_R_SaleID"+s.getSaleID(), new Pan_SaleListItemClient(true, s, () -> clickOnSale(s)));
				GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Lab_R_SaleID"+s.getSaleID()));
				totalSalesCount += s.getTotal_cost();
				salesCount ++;
			});
			GraphicObjects.get("Pan_R_ContentPanel").remove(GraphicObjects.get("Pan_R_Loading"));
			((JLabel) GraphicObjects.get("Lab_R_totalSale")).setText("Venta total: $"+ this.totalSalesCount);
			GraphicObjects.get("Pan_R_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],25 + (salesCount * 64) + ((salesCount + 1) * GraphicConstants.getMargins())));
			((OptionButton)GraphicObjects.get("But_R_Return")).setEnabled(true);
			GraphicObjects.get("Pan_workSpace").revalidate();
			GraphicObjects.get("Pan_workSpace").repaint();
		}).start();
		GraphicObjects.get("Pan_workSpace").revalidate();
		GraphicObjects.get("Pan_workSpace").repaint();
	}
	
	private void returnFunction()
	{
		GraphicObjects.dropR();
		new View_ClientDetails();
	}
	
	private void clickOnSale(Sale q)
	{
		StateControl.setCurrentSale(q.getSaleID());
		GraphicObjects.dropR();
		new View_SaleDetailClient();
	}
}
