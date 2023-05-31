package com.hungerless.HungerlessCRM.GUI.sales;

import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.calculator.Pan_quotationListItem;
import com.hungerless.HungerlessCRM.GUI.clients.View_Clients;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_listItem;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.calculator.PricesContainer;
import com.hungerless.HungerlessCRM.calculator.QuotationsAPI;
import com.hungerless.HungerlessCRM.clients.ClientsExporter;
import com.hungerless.HungerlessCRM.sales.DueSalesAPI;
import com.hungerless.HungerlessCRM.sales.Pan_SaleListItem;
import com.hungerless.HungerlessCRM.sales.Sale;
import com.hungerless.HungerlessCRM.sales.SalesContainer;
import com.hungerless.HungerlessCRM.sales.SalesExporter;

public class View_Sales
{
	private int salesCount = 0;
	private double totalSalesCount = 0;
	SimpleDateFormat dateFormat = new SimpleDateFormat("ww"); 
	private int sprint =  (int)Integer.valueOf(dateFormat.format(Calendar.getInstance().getTime()));

	
	public View_Sales()
	{
		sprint = sprint+1 > 51 ? 2 : sprint+1;
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		
		GraphicObjects.add("But_L_Export", new OptionButton(false, "Exportar", () -> exportFunction()));
		GraphicObjects.add("But_L_History", new OptionButton(false, "Historial", () -> historyFunction()));
		

		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Export"));
		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_History"));
		new View_Loading("L", "Cargando ordenes");
		
		new Thread(() -> 
		{
			try
			{
				PricesContainer.get("group_Constant_M_Animal"); //getter arbitrario
			}
			catch(NullPointerException e)
			{
				PricesContainer.updatePrices();
			}
			
			GraphicObjects.add("Lab_L_salesGoal", new JLabel()); //date _1
			GraphicObjects.get("Lab_L_salesGoal").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],10));
			GraphicObjects.get("Lab_L_salesGoal").setForeground(Color.WHITE);
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_salesGoal"));
			
			GraphicObjects.add("Lab_L_totalSale", new JLabel()); //date _1
			GraphicObjects.get("Lab_L_totalSale").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],10));
			GraphicObjects.get("Lab_L_totalSale").setForeground(Color.WHITE);
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_totalSale"));
			
			new DueSalesAPI().get().stream().map(o -> (Sale)o).forEach(s -> 
			{
				SalesContainer.add(s);
				GraphicObjects.add("Lab_L_SaleID"+s.getSaleID(), new Pan_SaleListItem(true, s, () -> clickOnSale(s)));
				GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_SaleID"+s.getSaleID()));
				this.salesCount ++;
				if(s.getForSprint() % 100 == this.sprint)
				{
					this.totalSalesCount += s.getTotal_cost();
				}
			});
			GraphicObjects.get("Pan_L_ContentPanel").remove(GraphicObjects.get("Pan_L_Loading"));
			((JLabel) GraphicObjects.get("Lab_L_salesGoal")).setText("Meta de ventas semanal: $"+String.valueOf(PricesContainer.get("sales_goal"))+" Restante: $"+String.valueOf(truncateNumber(PricesContainer.get("sales_goal") - this.totalSalesCount)));
			((JLabel) GraphicObjects.get("Lab_L_totalSale")).setText("Venta total del sprint "+this.sprint+": $"+ truncateNumber(this.totalSalesCount));
			GraphicObjects.get("Pan_L_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],50 + (salesCount * 64) + ((salesCount + 1) * GraphicConstants.getMargins())));
			((OptionButton)GraphicObjects.get("But_L_Export")).setEnabled(true);
			((OptionButton)GraphicObjects.get("But_L_History")).setEnabled(true);
			GraphicObjects.get("Pan_workSpace").revalidate();
			GraphicObjects.get("Pan_workSpace").repaint();
		}).start();
	}
	
	private void exportFunction()
	{
		if(JOptionPane.showOptionDialog(null, "Cerrar ventas?", "Advertencia", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null) != JOptionPane.OK_OPTION)
			return;
		GraphicObjects.dropR();
		GraphicObjects.get("Pan_workSpace").remove(GraphicObjects.get("Pan_L_Main"));
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		new View_Loading("L", "Exportando");
		new Thread(()->
		{
			new SalesExporter().salesReportCSV();
			GraphicObjects.dropL();
			new View_Sales();
		}).start();
	}
	private void historyFunction()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("ww"); 
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");		
		int week = (int)Integer.valueOf(dateFormat.format(Calendar.getInstance().getTime()));
		int year = (int)Integer.valueOf(yearFormat.format(Calendar.getInstance().getTime()));
		SpinnerNumberModel modelWeeks = new SpinnerNumberModel(week, 2, week, 1);
		SpinnerNumberModel modelYear = new SpinnerNumberModel(year, 2023, year, 1);
		JSpinner sprintSpinner = new JSpinner(modelWeeks);
		JSpinner yearSpinner = new JSpinner(modelYear);
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(yearSpinner, "#");
		yearSpinner.setEditor(editor);
		sprintSpinner.setPreferredSize(GraphicConstants.getSmallButtonSize());
		yearSpinner.setPreferredSize(GraphicConstants.getSmallButtonSize());
		JPanel optionPanel = new JPanel();
		optionPanel.add(sprintSpinner);
		optionPanel.add(yearSpinner);
		if(JOptionPane.showOptionDialog(null, optionPanel, "Que sprint?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) != JOptionPane.OK_OPTION)
			return;
		week = (int)sprintSpinner.getValue();
		year = (int)yearSpinner.getValue();
		GraphicObjects.softDropL();
		new View_History(year*100 + week);
	}
	
	private void clickOnSale(Sale s)
	{
		if (StateControl.getCurrentSale() != null)
		{
			((Pan_listItem)GraphicObjects.get("Lab_L_SaleID"+StateControl.getCurrentSale())).currentColor = ((Pan_listItem)GraphicObjects.get("Lab_L_SaleID"+StateControl.getCurrentSale())).mainColor;	
			((Pan_listItem)GraphicObjects.get("Lab_L_SaleID"+StateControl.getCurrentSale())).setBackground(((Pan_listItem)GraphicObjects.get("Lab_L_SaleID"+StateControl.getCurrentSale())).currentColor);
		}
		((Pan_listItem)GraphicObjects.get("Lab_L_SaleID"+ s.getSaleID())).setBackground(GraphicConstants.getHighlightedListItemColor());
		((Pan_listItem)GraphicObjects.get("Lab_L_SaleID"+ s.getSaleID())).currentColor = GraphicConstants.getHighlightedListItemColor();
		StateControl.setCurrentSale(s.getSaleID());
		GraphicObjects.dropR();
		new View_SaleDetails();
	}
	
	private static double truncateNumber(double n)   
	{   
		//moves the decimal to the right   
		n /= .01;  
		//determines the floor value  
		n = Math.floor(n);   
		//dividing the floor value by 10 to the power decimalplace  
		n /= 100;
		//prints the number after truncation  
		return n; 
	} 
}
