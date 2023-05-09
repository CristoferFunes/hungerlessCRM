package com.hungerless.HungerlessCRM.GUI.clients;

import java.awt.Dimension;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_listItem;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.clients.ClientsAPI;
import com.hungerless.HungerlessCRM.clients.ClientsContainer;
import com.hungerless.HungerlessCRM.clients.ProspectsAPI;
import com.hungerless.HungerlessCRM.clients.Client;

public class View_Clients
{
	private int clientCount = 0;
	public View_Clients()
	{
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(2, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		new View_Loading("L");
		new Thread(() ->
		{
			new ProspectsAPI().get().stream().map(o -> (Client)o).forEach(p ->
			{
				ClientsContainer.add(p);
				GraphicObjects.add("Lab_L_ClientID"+p.getClientID(), new Pan_clientListItem(false, p.getClientID(), () -> 
				{
					if (StateControl.getCurrentClient() != null && !StateControl.getCurrentClient().substring(0, 3).equals("new"))
					{
						((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).currentColor 
						= ((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).mainColor;
						
						((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).setBackground
						(
							((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).currentColor
						);
					}
					((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+ p.getClientID())).setBackground(GraphicConstants.getHighlightedListItemColor());
					((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+ p.getClientID())).currentColor = GraphicConstants.getHighlightedListItemColor();
					StateControl.setCurrentClient(p.getClientID());
					GraphicObjects.dropR();
				}));
				GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_ClientID"+p.getClientID()));
				clientCount ++;
			});
			new ClientsAPI().get().stream().map(o -> (Client)o).forEach(c ->
			{
				ClientsContainer.add(c);
				GraphicObjects.add("Lab_L_ClientID"+c.getClientID(), new Pan_clientListItem(true, c.getClientID(), () -> 
				{
					if (StateControl.getCurrentClient() != null && !StateControl.getCurrentClient().substring(0, 3).equals("new"))
					{
						((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).currentColor 
						= ((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).mainColor;
						
						((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).setBackground
						(
							((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+StateControl.getCurrentClient())).currentColor
						);
					}
					((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+ c.getClientID())).setBackground(GraphicConstants.getHighlightedListItemColor());
					((Pan_listItem)GraphicObjects.get("Lab_L_ClientID"+ c.getClientID())).currentColor = GraphicConstants.getHighlightedListItemColor();
					StateControl.setCurrentClient(c.getClientID());
					GraphicObjects.dropR();
				}));
				GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_ClientID"+c.getClientID()));
				clientCount ++;
			});

			GraphicObjects.get("Pan_L_ContentPanel").remove(GraphicObjects.get("Pan_L_Loading"));
			GraphicObjects.get("Pan_L_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],(clientCount * 64) + ((clientCount + 1) * GraphicConstants.getMargins())));
			GraphicObjects.get("Pan_workSpace").revalidate();
			GraphicObjects.get("Pan_workSpace").repaint();
		}).start();
		
		GraphicObjects.get("Pan_workSpace").revalidate();
		GraphicObjects.get("Pan_workSpace").repaint();
	}
}


