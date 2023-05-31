package com.hungerless.HungerlessCRM.GUI.sales;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.clients.Pan_ProductClientListItem;
import com.hungerless.HungerlessCRM.GUI.clients.View_ClientDetails;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_listItem;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.calculator.ProductItem;
import com.hungerless.HungerlessCRM.sales.SalesContainer;

public class View_SaleDetailsHistory
{
	private int products = 0;
	
	View_SaleDetailsHistory()
	{
		GraphicObjects.add("Pan_R_Main", new Pan_workPanel(1, "R"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_R_Main"));
		
		GraphicObjects.add("But_R_Return", new OptionButton(true, "Regresar", () -> returnFunction()));
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_Return"));
		
		GraphicObjects.get("Pan_workSpace").revalidate();
		GraphicObjects.get("Pan_workSpace").repaint();
		
		SalesContainer.get(StateControl.getCurrentSale()).getProducts().forEach(code ->
		{
			GraphicObjects.add("Lab_R_ProductID"+code, new Pan_ProductClientListItem(true, new ProductItem(code, false)));
			GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Lab_R_ProductID"+code));
			this.products++;
		});
		if(!SalesContainer.get(StateControl.getCurrentSale()).getSpecs().equals("")) 
		{
			GraphicObjects.add("Pan_R_Specs", new Pan_listItem(true, StateControl.getCurrentSale(), () -> {}));
			GraphicObjects.get("Pan_R_Specs").setLayout(new FlowLayout(FlowLayout.LEADING,GraphicConstants.getMargins(),GraphicConstants.getMargins()));
			
			GraphicObjects.add("Pan_R_Specs_ContentLabelID", new JLabel("Especificaciones: "+SalesContainer.get(StateControl.getCurrentSale()).getSpecs()));
			GraphicObjects.get("Pan_R_Specs_ContentLabelID").setFont(new Font(null,Font.PLAIN,12));
			GraphicObjects.get("Pan_R_Specs_ContentLabelID").setPreferredSize(new Dimension(GraphicConstants.getObjectsWithScrollSizeXY()[0], 32));
			((JLabel)GraphicObjects.get("Pan_R_Specs_ContentLabelID")).setHorizontalAlignment(JLabel.LEFT);
			GraphicObjects.get("Pan_R_Specs").add(GraphicObjects.get("Pan_R_Specs_ContentLabelID"));

			GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Pan_R_Specs"));
			this.products++;		
		}

		GraphicObjects.get("Pan_R_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],(this.products * 64) + ((this.products + 1) * GraphicConstants.getMargins())));
		
	}
	
	private void returnFunction()
	{
		GraphicObjects.dropR();
	}
}
