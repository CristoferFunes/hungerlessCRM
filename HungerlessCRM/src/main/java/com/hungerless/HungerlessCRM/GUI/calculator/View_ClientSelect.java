package com.hungerless.HungerlessCRM.GUI.calculator;

import java.awt.Dimension;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.clients.Pan_clientListItem;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.clients.Client;
import com.hungerless.HungerlessCRM.clients.ClientsAPI;
import com.hungerless.HungerlessCRM.clients.ClientsContainer;
import com.hungerless.HungerlessCRM.clients.ProspectsAPI;

public class View_ClientSelect
{
	private int clientCount = 0;
	
	public View_ClientSelect()
	{
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		
		GraphicObjects.add("But_L_Add", new OptionButton(false, "Agregar", () -> addFunction()));
		GraphicObjects.add("But_L_Return", new OptionButton(false, "Regresar", () -> returnFunction()));

		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Return"));
		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Add"));
		new View_Loading("L", "Cargando clientes");
		
		new Thread(() ->
		{
			new ProspectsAPI().get().stream().map(o -> (Client)o).forEach(p ->
			{
				ClientsContainer.add(p);
				GraphicObjects.add("Lab_L_ClientID"+p.getClientID(), new Pan_clientSelectListItem(false, p.getClientID(), () -> clickOnClient(p)));
				GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_ClientID"+p.getClientID()));
				clientCount ++;
			});
			new ClientsAPI().get().stream().map(o -> (Client)o).forEach(c ->
			{
				ClientsContainer.add(c);
				GraphicObjects.add("Lab_L_ClientID"+c.getClientID(), new Pan_clientSelectListItem(true, c.getClientID(), () -> clickOnClient(c)));
				GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_ClientID"+c.getClientID()));
				clientCount ++;
			});

			GraphicObjects.get("Pan_L_ContentPanel").remove(GraphicObjects.get("Pan_L_Loading"));
			GraphicObjects.get("Pan_L_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],(clientCount * 64) + ((clientCount + 1) * GraphicConstants.getMargins())));
			((OptionButton)GraphicObjects.get("But_L_Add")).setEnabled(true);
			((OptionButton)GraphicObjects.get("But_L_Return")).setEnabled(true);		
			GraphicObjects.get("Pan_workSpace").revalidate();
			GraphicObjects.get("Pan_workSpace").repaint();
		}).start();
		
		GraphicObjects.get("Pan_workSpace").revalidate();
		GraphicObjects.get("Pan_workSpace").repaint();
	}
	
	private void addFunction()
	{
		StateControl.setCurrentClient("new");
		GraphicObjects.softDropL();
		new View_QuickClient();
	}
	
	private void returnFunction()
	{
		StateControl.setCurrentSale(null);
		GraphicObjects.softDropL();
		new View_Quotations();
	}
	
	private void clickOnClient(Client o)
	{
		StateControl.setCurrentClient(o.getClientID());
		GraphicObjects.softDropL();
		new View_SaleEditor();
	}
}
