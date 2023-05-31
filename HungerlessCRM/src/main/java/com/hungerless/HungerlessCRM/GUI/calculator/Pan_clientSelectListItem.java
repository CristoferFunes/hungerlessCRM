package com.hungerless.HungerlessCRM.GUI.calculator;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_listItem;
import com.hungerless.HungerlessCRM.clients.ClientsContainer;
import com.hungerless.HungerlessCRM.interfaces.mouseClickAction;

public class Pan_clientSelectListItem extends Pan_listItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Pan_clientSelectListItem(boolean complete, String id, mouseClickAction a)
	{
		super(complete, id, a);
		GraphicObjects.add("Lab_L_Client_NameLabelID" + id, new JLabel(ClientsContainer.get(id).getFirst_name()+" "+ClientsContainer.get(id).getLast_name()));
		GraphicObjects.get("Lab_L_Client_NameLabelID" + id).setFont(new Font(null,Font.PLAIN,16));
		GraphicObjects.get("Lab_L_Client_NameLabelID" + id).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6)*5, GraphicConstants.getObjectsSizeXY()[1]));
		((JLabel)GraphicObjects.get("Lab_L_Client_NameLabelID" + id)).setHorizontalAlignment(JLabel.CENTER);
		
		GraphicObjects.add("Lab_L_Client_DateID" + id, new JLabel(complete ? "Cliente" : "Prospecto")); 
		GraphicObjects.get("Lab_L_Client_DateID" + id).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6), GraphicConstants.getObjectsSizeXY()[1]));
		((JLabel)GraphicObjects.get("Lab_L_Client_DateID" + id)).setHorizontalAlignment(JLabel.CENTER);

		this.add(GraphicObjects.get("Lab_L_Client_NameLabelID" + id));
		this.add(GraphicObjects.get("Lab_L_Client_DateID" + id));
	}

}
