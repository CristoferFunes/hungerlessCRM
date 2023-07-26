package com.hungerless.HungerlessCRM.GUI.clients;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Tex_TextArea;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.clients.Client;
import com.hungerless.HungerlessCRM.clients.ClientsAPI;
import com.hungerless.HungerlessCRM.clients.ClientsContainer;

public class View_ClientDetails
{
	int componentsHeight = 535;
	
	View_ClientDetails()
	{
		if(StateControl.getCurrentClient().substring(0, 3).equals("new")) ClientsContainer.add(
				new Client("new", "", "", "", "", "", null, new Date(), null, true, false, 0, 0, null));
		GraphicObjects.add("Pan_R_Main", new Pan_workPanel(1, "R"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_R_Main"));
		
		GraphicObjects.add("But_R_save", new OptionButton(true, "Guardar", () -> handleSave()));
		GraphicObjects.add("But_R_delete", new OptionButton(true, "Eliminar", () -> handleDelete()));
		GraphicObjects.add("But_R_history", new OptionButton(true, "Historial", () -> salesHistory()));
		GraphicObjects.add("But_R_newSale", new OptionButton(true, "Re venta", () -> reSale()));
		
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_save"));
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_delete"));
		
		//content panel
		
		GraphicObjects.add("Lab_R_CreationDate", new JLabel("Fecha de cracion: "+ ClientsContainer.get(StateControl.getCurrentClient()).getDateOfCreation())); //date _1
		GraphicObjects.get("Lab_R_CreationDate").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],10));
		GraphicObjects.get("Lab_R_CreationDate").setForeground(Color.WHITE);
		
		GraphicObjects.add("Lab_R_AverageTicket", new JLabel("Ticket promedio: "+ (ClientsContainer.get(StateControl.getCurrentClient()).getAverage_ticket() == 0 ? "Aun no tiene compras" :"$"+String.valueOf(ClientsContainer.get(StateControl.getCurrentClient()).getAverage_ticket())) )); //average ticket _2
		GraphicObjects.get("Lab_R_AverageTicket").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],10));
		GraphicObjects.get("Lab_R_AverageTicket").setForeground(Color.WHITE);
		
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
		
		GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Lab_R_CreationDate"));
		GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Lab_R_AverageTicket"));
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
		
		if(ClientsContainer.get(StateControl.getCurrentClient()).isCustomer())
		{
			GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_history"));
			GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_newSale"));
			
			GraphicObjects.add("Lab_R_LastSaleSpecs", new JLabel("Ultima especificacion")); //name label _3
			GraphicObjects.get("Lab_R_LastSaleSpecs").setPreferredSize(GraphicConstants.getTextAreaLabelSize());
			GraphicObjects.get("Lab_R_LastSaleSpecs").setForeground(Color.WHITE);
			GraphicObjects.add("Tex_R_LastSaleSpecs", new Tex_TextArea(GraphicConstants.getLargeTextSize(),ClientsContainer.get(StateControl.getCurrentClient()).getLastSaleSpecs()));
			((Tex_TextArea)GraphicObjects.get("Tex_R_LastSaleSpecs")).setEditable(false);
			
			componentsHeight = 750;
			
			GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Lab_R_LastSaleSpecs"));
			GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Tex_R_LastSaleSpecs"));
		}
		
		GraphicObjects.get("Pan_R_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0], componentsHeight));
		GraphicObjects.get("Pan_workSpace").revalidate();
		GraphicObjects.get("Pan_workSpace").repaint();
	}
	
	public void handleSave()
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
			if(!StateControl.getCurrentClient().substring(0, 3).equals("new"))
			{
				new ClientsAPI().update(ClientsContainer.get(StateControl.getCurrentClient()));
			}
			else
			{
				new ClientsAPI().post(ClientsContainer.get(StateControl.getCurrentClient()));
			}
			GraphicObjects.dropL();
			new View_Clients();
		}).start();
	}

	public void handleDelete()
	{
		if ((JOptionPane.showInternalConfirmDialog(null, "¿Seguro que deseas eliminar al cliente?\nEsta accion es irreversible", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)) != JOptionPane.OK_OPTION) return;
		GraphicObjects.dropR();
		GraphicObjects.add("Pan_R_Main", new Pan_workPanel(1, "R"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_R_Main"));
		new View_Loading("R", "Guardando");
		
		new Thread(() -> 
		{
			if(!StateControl.getCurrentClient().substring(0, 3).equals("new")) new ClientsAPI().delete(ClientsContainer.get(StateControl.getCurrentClient()));
			GraphicObjects.dropL();
			new View_Clients();
		}).start();
	}
	
	public void salesHistory()
	{
		GraphicObjects.dropR();
		new View_HistoryFromClient();
	}
	
	public void reSale()
	{
		GraphicObjects.dropR();
		new View_Resale();
	}
}
