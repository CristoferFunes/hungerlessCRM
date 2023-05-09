package com.hungerless.HungerlessCRM.GUI.clients;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.mouseClickAction;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_listItem;
import com.hungerless.HungerlessCRM.clients.ClientsContainer;

public class Pan_clientListItem extends Pan_listItem
{
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	public Pan_clientListItem(boolean complete, String id, mouseClickAction a)
	{
		super(complete, id, a);
		GraphicObjects.add("Che_L_Client_CheckBoxID" + id, new JCheckBox());
		GraphicObjects.get("Che_L_Client_CheckBoxID" + id).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6), GraphicConstants.getObjectsSizeXY()[1]));
		((JCheckBox)GraphicObjects.get("Che_L_Client_CheckBoxID" + id)).setHorizontalAlignment(JLabel.CENTER);
		((JCheckBox)GraphicObjects.get("Che_L_Client_CheckBoxID" + id)).setBackground(GraphicConstants.getChecklistColor());
		((JCheckBox)GraphicObjects.get("Che_L_Client_CheckBoxID" + id)).addItemListener(e ->
		{
			if (e.getStateChange() == ItemEvent.SELECTED) StateControl.addSelectedClient(id);
			else StateControl.removeSelectedClient(id);
		});
		
		GraphicObjects.add("Lab_L_Client_NameLabelID" + id, new JLabel(ClientsContainer.get(id).getFirst_name()+" "+ClientsContainer.get(id).getLast_name()));
		GraphicObjects.get("Lab_L_Client_NameLabelID" + id).setFont(new Font(null,Font.PLAIN,16));
		GraphicObjects.get("Lab_L_Client_NameLabelID" + id).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6)*4, GraphicConstants.getObjectsSizeXY()[1]));
		((JLabel)GraphicObjects.get("Lab_L_Client_NameLabelID" + id)).setHorizontalAlignment(JLabel.CENTER);
		
		GraphicObjects.add("Lab_L_Client_DateID" + id, new JLabel(complete ? String.valueOf(dateFormat.format(ClientsContainer.get(id).getLastSaleDate())) : "Prospecto")); 
		GraphicObjects.get("Lab_L_Client_DateID" + id).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6), GraphicConstants.getObjectsSizeXY()[1]));
		((JLabel)GraphicObjects.get("Lab_L_Client_DateID" + id)).setHorizontalAlignment(JLabel.CENTER);
		
		this.add(GraphicObjects.get("Che_L_Client_CheckBoxID" + id));
		this.add(GraphicObjects.get("Lab_L_Client_NameLabelID" + id));
		this.add(GraphicObjects.get("Lab_L_Client_DateID" + id));
	}
	private static final long serialVersionUID = -1050117028191845382L;
}
