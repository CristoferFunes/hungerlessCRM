package com.hungerless.HungerlessCRM.GUI.sales;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.calculator.Pan_quotationListItem;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.sales.HistorySalesAPI;
import com.hungerless.HungerlessCRM.sales.Sale;
import com.hungerless.HungerlessCRM.sales.SalesContainer;

public class View_History
{
	private int salesCount = 0;
	private double totalSalesCount = 0;
	
	View_History(int sprint)
	{
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		
		GraphicObjects.add("But_L_Return", new OptionButton(false, "Regresar", () -> returnFunction()));		

		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Return"));
		new View_Loading("L", "Cargando historial");
		
		new Thread(() -> 
		{
			GraphicObjects.add("Lab_L_totalSale", new JLabel()); //date _1
			GraphicObjects.get("Lab_L_totalSale").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],10));
			GraphicObjects.get("Lab_L_totalSale").setForeground(Color.WHITE);
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_totalSale"));
			
			new HistorySalesAPI(sprint).get().stream().map(o -> (Sale)o).forEach(s -> 
			{
				SalesContainer.add(s);
				GraphicObjects.add("Lab_L_SaleID"+s.getSaleID(), new Pan_quotationListItem(true, s, () -> clickOnSale(s)));
				GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_SaleID"+s.getSaleID()));
				this.totalSalesCount += s.getTotal_cost();
				salesCount ++;
			});
			GraphicObjects.get("Pan_L_ContentPanel").remove(GraphicObjects.get("Pan_L_Loading"));
			((JLabel) GraphicObjects.get("Lab_L_totalSale")).setText("Venta total del Sprint "+String.valueOf(sprint).substring(4, 6) + " "+String.valueOf(sprint).substring(0, 4)+": $"+ this.totalSalesCount);
			GraphicObjects.get("Pan_L_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],(salesCount * 64) + ((salesCount + 1) * GraphicConstants.getMargins())));
			((OptionButton)GraphicObjects.get("But_L_Return")).setEnabled(true);
			GraphicObjects.get("Pan_workSpace").revalidate();
			GraphicObjects.get("Pan_workSpace").repaint();
		}).start();
	}
	
	private void returnFunction()
	{
		GraphicObjects.dropL();
		new View_Sales();
	}
	
	private void clickOnSale(Sale s)
	{
		StateControl.setCurrentSale(s.getSaleID());
		GraphicObjects.dropR();
		new View_SaleDetailsHistory();
	}
}
