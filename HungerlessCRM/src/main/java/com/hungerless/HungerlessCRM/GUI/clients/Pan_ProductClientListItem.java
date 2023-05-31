package com.hungerless.HungerlessCRM.GUI.clients;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_listItem;
import com.hungerless.HungerlessCRM.calculator.ProductItem;
import com.hungerless.HungerlessCRM.calculator.ProductsContainer;
import com.hungerless.HungerlessCRM.interfaces.mouseClickAction;
import com.hungerless.HungerlessCRM.sales.SalesContainer;

public class Pan_ProductClientListItem extends Pan_listItem
{
	private static final long serialVersionUID = 9121874474333223507L;

	public Pan_ProductClientListItem(boolean complete, ProductItem product)
	{
		super(complete, String.valueOf(product.getProductID()), () -> {});
		GraphicObjects.add("Lab_R_Product_ContentLabelID" +product.getProductID(), new JLabel
				(
						product.getType() != 3 ? 
						product.getOriginalAmount()+" x "+product.getTypeWriten()+"("+product.getInner()+")":
						product.getOriginalAmount()+" x "+product.getTypeWriten()	
				));
		GraphicObjects.get("Lab_R_Product_ContentLabelID" +product.getProductID()).setFont(new Font(null,Font.BOLD,16));
		GraphicObjects.get("Lab_R_Product_ContentLabelID" +product.getProductID()).setPreferredSize(new Dimension(GraphicConstants.getObjectsWithScrollSizeXY()[0], GraphicConstants.getObjectsSizeXY()[1]));
		((JLabel)GraphicObjects.get("Lab_R_Product_ContentLabelID" +product.getProductID())).setHorizontalAlignment(JLabel.CENTER);
		this.add(GraphicObjects.get("Lab_R_Product_ContentLabelID" +product.getProductID()));
	}

}
