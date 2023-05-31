package com.hungerless.HungerlessCRM.GUI.calculator;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;

import javax.swing.JLabel;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.clients.View_Clients;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Tex_TextArea;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.clients.Client;
import com.hungerless.HungerlessCRM.clients.ClientsAPI;
import com.hungerless.HungerlessCRM.clients.ClientsContainer;

public class View_QuickClient
{
	int componentsHeight = 485;
	View_QuickClient()
	{
		if(StateControl.getCurrentClient().substring(0, 3).equals("new")) ClientsContainer.add(
				new Client("new", "", "", "", "", "", null, Calendar.getInstance().getTime(), null, true, false, 0, 0, null));
		GraphicObjects.add("Pan_R_Main", new Pan_workPanel(1, "R"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_R_Main"));
		
		GraphicObjects.add("But_L_Add", new OptionButton(true, "Confirmar", () -> handleSave()));
		GraphicObjects.add("But_L_Return", new OptionButton(true, "Regresar", () -> returnFunction()));
		
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_L_Return"));
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_L_Add"));
		
		//content panel
		
		GraphicObjects.add("Lab_R_FirstName", new JLabel("Nombre")); //name label _3
		GraphicObjects.get("Lab_R_FirstName").setPreferredSize(GraphicConstants.getTextAreaLabelSize());
		GraphicObjects.get("Lab_R_FirstName").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_R_FirstName", new Tex_TextArea(GraphicConstants.getMediumSmallTextSize(),ClientsContainer.get(StateControl.getCurrentClient()).getFirst_name()));
		
		GraphicObjects.add("Lab_R_LastName", new JLabel("Apellido")); //name label _3
		GraphicObjects.get("Lab_R_LastName").setPreferredSize(GraphicConstants.getTextAreaLabelSize());
		GraphicObjects.get("Lab_R_LastName").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_R_LastName", new Tex_TextArea(GraphicConstants.getMediumSmallTextSize(),ClientsContainer.get(StateControl.getCurrentClient()).getLast_name()));
		
		GraphicObjects.add("Lab_R_Address", new JLabel("Direccion")); //name label _3
		GraphicObjects.get("Lab_R_Address").setPreferredSize(GraphicConstants.getTextAreaLabelSize());
		GraphicObjects.get("Lab_R_Address").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_R_Address", new Tex_TextArea(GraphicConstants.getMediumTextSize(),ClientsContainer.get(StateControl.getCurrentClient()).getAddress()));
		
		GraphicObjects.add("Lab_R_PhoneNumber", new JLabel("Telefono")); //name label _3
		GraphicObjects.get("Lab_R_PhoneNumber").setPreferredSize(GraphicConstants.getTextAreaLabelSize());
		GraphicObjects.get("Lab_R_PhoneNumber").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_R_PhoneNumber", new Tex_TextArea(GraphicConstants.getSmallTextSize(),ClientsContainer.get(StateControl.getCurrentClient()).getPhoneNumber()));
		
		GraphicObjects.add("Lab_R_Comments", new JLabel("Comentarios")); //name label _3
		GraphicObjects.get("Lab_R_Comments").setPreferredSize(GraphicConstants.getTextAreaLabelSize());
		GraphicObjects.get("Lab_R_Comments").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_R_Comments", new Tex_TextArea(GraphicConstants.getLargeTextSize(),ClientsContainer.get(StateControl.getCurrentClient()).getComments()));
		
		GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Lab_R_FirstName"));
		GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Tex_R_FirstName"));
		GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Lab_R_LastName"));
		GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Tex_R_LastName"));
		GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Lab_R_Address"));
		GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Tex_R_Address"));
		GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Lab_R_PhoneNumber"));
		GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Tex_R_PhoneNumber"));
		GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Lab_R_Comments"));
		GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Tex_R_Comments"));
		
		GraphicObjects.get("Pan_R_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0], componentsHeight));
		GraphicObjects.get("Pan_workSpace").revalidate();
		GraphicObjects.get("Pan_workSpace").repaint();
	}
	
	private void handleSave()
	{
		ClientsContainer.get(StateControl.getCurrentClient()).setFirst_name(((Tex_TextArea)GraphicObjects.get("Tex_R_FirstName")).getText());
		ClientsContainer.get(StateControl.getCurrentClient()).setLast_name(((Tex_TextArea)GraphicObjects.get("Tex_R_LastName")).getText());
		ClientsContainer.get(StateControl.getCurrentClient()).setAddress(((Tex_TextArea)GraphicObjects.get("Tex_R_Address")).getText());
		ClientsContainer.get(StateControl.getCurrentClient()).setPhoneNumber(((Tex_TextArea)GraphicObjects.get("Tex_R_PhoneNumber")).getText());
		ClientsContainer.get(StateControl.getCurrentClient()).setComments(((Tex_TextArea)GraphicObjects.get("Tex_R_Comments")).getText());
		GraphicObjects.dropR();
		GraphicObjects.add("Pan_R_Main", new Pan_workPanel(1, "R"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_R_Main"));
		new View_Loading("R", "Guardando");
		new Thread(() ->
		{
			ClientsContainer.get(StateControl.getCurrentClient()).setClientID(new ClientsAPI().post(ClientsContainer.get(StateControl.getCurrentClient())));
			ClientsContainer.add(ClientsContainer.get(StateControl.getCurrentClient()));
			StateControl.setCurrentClient(ClientsContainer.get(StateControl.getCurrentClient()).getClientID());
			GraphicObjects.softDropL();
			new View_SaleEditor();
		}).start();
	}
	
	private void returnFunction()
	{
		StateControl.setCurrentSale(null);
		StateControl.setCurrentClient(null);
		GraphicObjects.softDropL();
		new View_Quotations();
	}
}
