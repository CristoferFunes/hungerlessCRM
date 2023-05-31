package com.hungerless.HungerlessCRM.GUI.clients;

import java.awt.Dimension;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_listItem;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.clients.ClientsAPI;
import com.hungerless.HungerlessCRM.clients.ClientsContainer;
import com.hungerless.HungerlessCRM.clients.ClientsExporter;
import com.hungerless.HungerlessCRM.clients.ProspectsAPI;
import com.hungerless.HungerlessCRM.clients.Client;

public class View_Clients
{
	private int clientCount = 0;
	public View_Clients()
	{
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		
		GraphicObjects.add("But_L_Add", new OptionButton(false, "Agregar", () -> addFunction()));
		GraphicObjects.add("But_L_Export", new OptionButton(false, "Exportar", () -> exportCSV()));
		GraphicObjects.add("But_L_SelectAll", new OptionButton(false, "Selec. todos", () -> selectAll()));
		GraphicObjects.add("But_L_DeselectAll", new OptionButton(false, "Deselec. todos", () -> deselectAll()));
		
		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Add"));
		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Export"));
		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_SelectAll"));
		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_DeselectAll"));
		new View_Loading("L", "Cargando");
		new Thread(() ->
		{
			new ProspectsAPI().get().stream().map(o -> (Client)o).forEach(p ->
			{
				ClientsContainer.add(p);
				GraphicObjects.add("Lab_L_ClientID"+p.getClientID(), new Pan_clientListItem(false, p, () -> clickOnClient(p)));
				GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_ClientID"+p.getClientID()));
				clientCount ++;
			});
			new ClientsAPI().get().stream().map(o -> (Client)o).forEach(c ->
			{
				ClientsContainer.add(c);
				GraphicObjects.add("Lab_L_ClientID"+c.getClientID(), new Pan_clientListItem(true, c, () -> clickOnClient(c)));
				GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_ClientID"+c.getClientID()));
				clientCount ++;
			});

			GraphicObjects.get("Pan_L_ContentPanel").remove(GraphicObjects.get("Pan_L_Loading"));
			GraphicObjects.get("Pan_L_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],(clientCount * 64) + ((clientCount + 1) * GraphicConstants.getMargins())));
			((OptionButton)GraphicObjects.get("But_L_Add")).setEnabled(true);
			((OptionButton)GraphicObjects.get("But_L_Export")).setEnabled(true);
			((OptionButton)GraphicObjects.get("But_L_SelectAll")).setEnabled(true);
			((OptionButton)GraphicObjects.get("But_L_DeselectAll")).setEnabled(true);			
			GraphicObjects.get("Pan_workSpace").revalidate();
			GraphicObjects.get("Pan_workSpace").repaint();
		}).start();
		
		GraphicObjects.get("Pan_workSpace").revalidate();
		GraphicObjects.get("Pan_workSpace").repaint();
	}
	
	private void addFunction()
	{
		if (StateControl.getCurrentClient() != null && !StateControl.getCurrentClient().substring(0, 3).equals("new"))
		{
			((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).currentColor = ((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).mainColor;	
			((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).setBackground(((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).currentColor);
		}
		StateControl.setCurrentClient("new");
		GraphicObjects.dropR();
		new View_ClientDetails();
	}
	
	private void clickOnClient(Client o)
	{
		if (StateControl.getCurrentClient() != null && !StateControl.getCurrentClient().substring(0, 3).equals("new"))
		{
			((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).currentColor = ((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).mainColor;	
			((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).setBackground(((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).currentColor);
		}
		((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+ o.getClientID())).setBackground(GraphicConstants.getHighlightedListItemColor());
		((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+ o.getClientID())).currentColor = GraphicConstants.getHighlightedListItemColor();
		StateControl.setCurrentClient(o.getClientID());
		GraphicObjects.dropR();
		new View_ClientDetails();
	}
	
	private void exportCSV()
	{
		if(StateControl.selectedClientsEmpty())
		{
			JOptionPane.showMessageDialog(null, "Selecciona al menos un cliente para exportar", "Advertencia", JOptionPane.WARNING_MESSAGE);
			return;
		}
		GraphicObjects.dropR();
		GraphicObjects.get("Pan_workSpace").remove(GraphicObjects.get("Pan_L_Main"));
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		new View_Loading("L", "Exportando");
		new Thread(()->
		{
			ClientsExporter.exportClientsCSV();
			GraphicObjects.dropL();
			new View_Clients();
		}).start();
	}
	
	private void selectAll()
	{
		ClientsContainer.getKeySet().forEach(c ->
		{
			((JCheckBox)GraphicObjects.get("Che_L_Client_CheckBoxID"+c)).setSelected(true);
		});
	}
	
	private void deselectAll()
	{
		ClientsContainer.getKeySet().forEach(c ->
		{
			((JCheckBox)GraphicObjects.get("Che_L_Client_CheckBoxID"+c)).setSelected(false);
		});
	}
}


