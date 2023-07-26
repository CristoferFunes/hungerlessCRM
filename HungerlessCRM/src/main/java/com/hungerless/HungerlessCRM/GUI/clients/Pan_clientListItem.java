package com.hungerless.HungerlessCRM.GUI.clients;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_listItem;
import com.hungerless.HungerlessCRM.clients.Client;
import com.hungerless.HungerlessCRM.interfaces.mouseClickAction;

public class Pan_clientListItem extends Pan_listItem
{
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	public Pan_clientListItem(boolean complete, Client c, mouseClickAction a)
	{
		super(complete, c.getClientID(), a);
		if(c.isCustomer() && (c.getAddress().equals("") || c.getPhoneNumber().equals("")))
		{
			super.mainColor = GraphicConstants.getUnfinishedColor();
			super.currentColor = GraphicConstants.getUnfinishedColor();
			this.setBackground(GraphicConstants.getUnfinishedColor());
		}
		GraphicObjects.add("Che_L_Client_CheckBoxID" + c.getClientID(), new JCheckBox());
		GraphicObjects.get("Che_L_Client_CheckBoxID" + c.getClientID()).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6), GraphicConstants.getObjectsSizeXY()[1]));
		((JCheckBox)GraphicObjects.get("Che_L_Client_CheckBoxID" + c.getClientID())).setHorizontalAlignment(JLabel.CENTER);
		((JCheckBox)GraphicObjects.get("Che_L_Client_CheckBoxID" + c.getClientID())).setBackground(GraphicConstants.getChecklistColor());
		((JCheckBox)GraphicObjects.get("Che_L_Client_CheckBoxID" + c.getClientID())).addItemListener(e ->
		{
			if (e.getStateChange() == ItemEvent.SELECTED) StateControl.addSelectedClient(c.getClientID());
			else StateControl.removeSelectedClient(c.getClientID());
		});
		GraphicObjects.add("Lab_L_Client_NameLabelID" + c.getClientID(), new JLabel(c.getFirst_name()+" "+c.getLast_name()));
		GraphicObjects.get("Lab_L_Client_NameLabelID" + c.getClientID()).setFont(new Font(null,Font.PLAIN,16));
		GraphicObjects.get("Lab_L_Client_NameLabelID" + c.getClientID()).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6)*4, GraphicConstants.getObjectsSizeXY()[1]));
		((JLabel)GraphicObjects.get("Lab_L_Client_NameLabelID" + c.getClientID())).setHorizontalAlignment(JLabel.CENTER);
		
		GraphicObjects.add("Lab_L_Client_DateID" + c.getClientID(), new JLabel(complete ? String.valueOf(dateFormat.format(c.getLastSaleDate())) : "Prospecto")); 
		GraphicObjects.get("Lab_L_Client_DateID" + c.getClientID()).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6), GraphicConstants.getObjectsSizeXY()[1]));
		((JLabel)GraphicObjects.get("Lab_L_Client_DateID" + c.getClientID())).setHorizontalAlignment(JLabel.CENTER);
		
		this.add(GraphicObjects.get("Che_L_Client_CheckBoxID" + c.getClientID()));
		this.add(GraphicObjects.get("Lab_L_Client_NameLabelID" + c.getClientID()));
		this.add(GraphicObjects.get("Lab_L_Client_DateID" + c.getClientID()));
	}
	private static final long serialVersionUID = -1050117028191845382L;
}
