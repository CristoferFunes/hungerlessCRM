package com.hungerless.HungerlessCRM.GUI.clients;

import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;

import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_listItem;
import com.hungerless.HungerlessCRM.interfaces.mouseClickAction;
import com.hungerless.HungerlessCRM.sales.Sale;

public class Pan_SaleListItemClient extends Pan_listItem
{
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	private static final long serialVersionUID = -96498921410760540L;

	public Pan_SaleListItemClient(boolean complete, Sale sale, mouseClickAction a)
	{
		super(complete, sale.getSaleID(), a);
		GraphicObjects.add("Lab_R_Sale_CreationDateID" + sale.getSaleID(), new JLabel(String.valueOf(dateFormat.format(sale.getDateOfCreation()))));
		GraphicObjects.get("Lab_R_Sale_CreationDateID" + sale.getSaleID()).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6), GraphicConstants.getObjectsSizeXY()[1]));
		((JLabel)GraphicObjects.get("Lab_R_Sale_CreationDateID" + sale.getSaleID())).setHorizontalAlignment(JLabel.CENTER);
		
		GraphicObjects.add("Lab_R_Sale_NameLabelID" + sale.getSaleID(), new JLabel("Sprint: "+String.valueOf(sale.getForSprint()).substring(4, 6) + " Year: "+String.valueOf(sale.getForSprint()).substring(0, 4)));
		GraphicObjects.get("Lab_R_Sale_NameLabelID" + sale.getSaleID()).setFont(new Font(null,Font.PLAIN,16));
		GraphicObjects.get("Lab_R_Sale_NameLabelID" + sale.getSaleID()).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6)*4, GraphicConstants.getObjectsSizeXY()[1]));
		((JLabel)GraphicObjects.get("Lab_R_Sale_NameLabelID" + sale.getSaleID())).setHorizontalAlignment(JLabel.CENTER);
		
		GraphicObjects.add("Lab_R_Sale_TotalCostID" + sale.getSaleID(), new JLabel("Total: $"+sale.getTotal_cost()));
		GraphicObjects.get("Lab_R_Sale_TotalCostID" + sale.getSaleID()).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6), GraphicConstants.getObjectsSizeXY()[1]));
		((JLabel)GraphicObjects.get("Lab_R_Sale_TotalCostID" + sale.getSaleID())).setHorizontalAlignment(JLabel.CENTER);
		
		this.add(GraphicObjects.get("Lab_R_Sale_CreationDateID" + sale.getSaleID()));
		this.add(GraphicObjects.get("Lab_R_Sale_NameLabelID" + sale.getSaleID()));
		this.add(GraphicObjects.get("Lab_R_Sale_TotalCostID" + sale.getSaleID()));
	}

}