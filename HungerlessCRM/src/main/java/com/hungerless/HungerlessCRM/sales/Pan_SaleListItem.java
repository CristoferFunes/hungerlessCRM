package com.hungerless.HungerlessCRM.sales;

import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;

import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_listItem;
import com.hungerless.HungerlessCRM.interfaces.mouseClickAction;

public class Pan_SaleListItem extends Pan_listItem
{
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	private static final long serialVersionUID = -96498921410760540L;
	private int mealCount = 0;
	private int breakCount = 0;
	
	
	public Pan_SaleListItem(boolean complete, Sale sale, mouseClickAction a)
	{
		super(complete, sale.getSaleID(), a);
		sale.getProducts().forEach(p -> 
		{
			if(Integer.valueOf(p.substring(0, 1)) == 1)
			{
				mealCount += Integer.valueOf(p.substring(19, 21));
			}
			if(Integer.valueOf(p.substring(0, 1)) == 2)
			{
				breakCount += Integer.valueOf(p.substring(19, 21));
			}
		});
		GraphicObjects.add("Lab_L_Sale_NameLabelID" + sale.getSaleID(), new JLabel(sale.getClient_name()));
		GraphicObjects.get("Lab_L_Sale_NameLabelID" + sale.getSaleID()).setFont(new Font(null,Font.PLAIN,16));
		GraphicObjects.get("Lab_L_Sale_NameLabelID" + sale.getSaleID()).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6)*3, GraphicConstants.getObjectsSizeXY()[1]));
		((JLabel)GraphicObjects.get("Lab_L_Sale_NameLabelID" + sale.getSaleID())).setHorizontalAlignment(JLabel.CENTER);
		
		GraphicObjects.add("Lab_L_Sale_CreationDateID" + sale.getSaleID(), new JLabel("Sprint: "+String.valueOf(sale.getForSprint()).substring(4, 6) + " " +(mealCount > 0 ? String.valueOf(mealCount)+"C" : "")+(breakCount > 0 ? String.valueOf(breakCount)+"D" : "")));
		GraphicObjects.get("Lab_L_Sale_CreationDateID" + sale.getSaleID()).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6)*2, GraphicConstants.getObjectsSizeXY()[1]));
		((JLabel)GraphicObjects.get("Lab_L_Sale_CreationDateID" + sale.getSaleID())).setHorizontalAlignment(JLabel.CENTER);
		
		GraphicObjects.add("Lab_L_Sale_TotalCostID" + sale.getSaleID(), new JLabel("Total: $"+sale.getTotal_cost()));
		GraphicObjects.get("Lab_L_Sale_TotalCostID" + sale.getSaleID()).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6), GraphicConstants.getObjectsSizeXY()[1]));
		((JLabel)GraphicObjects.get("Lab_L_Sale_TotalCostID" + sale.getSaleID())).setHorizontalAlignment(JLabel.CENTER);
		
		this.add(GraphicObjects.get("Lab_L_Sale_NameLabelID" + sale.getSaleID()));
		this.add(GraphicObjects.get("Lab_L_Sale_CreationDateID" + sale.getSaleID()));
		this.add(GraphicObjects.get("Lab_L_Sale_TotalCostID" + sale.getSaleID()));
	}

}
