package com.hungerless.HungerlessCRM.GUI.clients;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_listItem;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.calculator.PricesContainer;
import com.hungerless.HungerlessCRM.calculator.ProductItem;
import com.hungerless.HungerlessCRM.calculator.QuotationsAPI;
import com.hungerless.HungerlessCRM.clients.Client;
import com.hungerless.HungerlessCRM.clients.ClientsAPI;
import com.hungerless.HungerlessCRM.clients.ClientsContainer;
import com.hungerless.HungerlessCRM.clients.HistorySalesFromClientAPI;
import com.hungerless.HungerlessCRM.clients.SalesFromClientAPI;
import com.hungerless.HungerlessCRM.sales.Sale;
import com.hungerless.HungerlessCRM.sales.SalesContainer;

public class View_Resale
{
	private double sumForAverage;
	private int sumOfSales;
	private int products = 0;
	@SuppressWarnings("unchecked")
	View_Resale()
	{
		GraphicObjects.add("Pan_R_Main", new Pan_workPanel(1, "R"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_R_Main"));
		
		GraphicObjects.add("But_R_Return", new OptionButton(false, "Regresar", () -> returnFunction()));
		GraphicObjects.add("But_R_Resale", new OptionButton(false, "Re venta", () -> handleSale()));
		
		GraphicObjects.add("Lab_R_TotalCost", new JLabel());
		((JLabel)GraphicObjects.get("Lab_R_TotalCost")).setFont(new Font(null,Font.BOLD,16));
		GraphicObjects.get("Lab_R_TotalCost").setPreferredSize(GraphicConstants.getLargeButtonSize());
		GraphicObjects.get("Lab_R_TotalCost").setForeground(Color.WHITE);

		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_Return"));
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_Resale"));
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("Lab_R_TotalCost"));
		new View_Loading("R", "Cargando ultima venta");
		
		StateControl.setCurrentSale(ClientsContainer.get(StateControl.getCurrentClient()).getLastSaleID());
		
		new Thread(() ->
		{
			List<Sale> history = new ArrayList<>();
			new HistorySalesFromClientAPI(StateControl.getCurrentClient()).get().forEach(s -> history.add((Sale) s));
			if(!new QuotationsAPI().getDocument(StateControl.getCurrentSale(), t -> t.exists()))
			{
				if(history.isEmpty())
				{
					resetClient();
					return;
				}
				StateControl.setCurrentSale(history.get(history.size()-1).getSaleID());			
			}
			
			PricesContainer.updatePrices();
			SalesContainer.add(new QuotationsAPI().getDocument(StateControl.getCurrentSale(), t -> new Sale(
					t.getId(),
					t.getBoolean("quotation") == null ? false : t.getBoolean("quotation"),
					t.getBoolean("sale") == null ? false : t.getBoolean("sale"),
					t.getString("specs"),
					new Date(),
					t.getDouble("total_cost"),
					t.getDouble("tokens"),
					t.getDouble("for_sprint") == null ? 0 : t.getDouble("for_sprint"),
					t.getDouble("weeks"),
					t.getString("client_id"),
					t.getString("client_name"),
					((ArrayList<String>)t.get("products")),
					t.getBoolean("legacy") == null ? false : t.getBoolean("legacy"),
					t.getBoolean("consecutive_order") == null ? false : t.getBoolean("consecutive_order"),
					t.getString("parent_id") == null ? null : t.getString("parent_id")
					)));
			((JLabel) GraphicObjects.get("Lab_R_TotalCost")).setText("Total: $" + SalesContainer.get(StateControl.getCurrentSale()).getTotal_cost());
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
			if(SalesContainer.get(StateControl.getCurrentSale()).getWeeks() > 1) 
			{
				GraphicObjects.add("Pan_R_Specs", new Pan_listItem(true, StateControl.getCurrentSale(), () -> {}));
				GraphicObjects.get("Pan_R_Specs").setLayout(new FlowLayout(FlowLayout.LEADING,GraphicConstants.getMargins(),GraphicConstants.getMargins()));
				
				GraphicObjects.add("Pan_R_Specs_ContentLabelID", new JLabel("Semanas: "+SalesContainer.get(StateControl.getCurrentSale()).getWeeks()));
				GraphicObjects.get("Pan_R_Specs_ContentLabelID").setFont(new Font(null,Font.PLAIN,12));
				GraphicObjects.get("Pan_R_Specs_ContentLabelID").setPreferredSize(new Dimension(GraphicConstants.getObjectsWithScrollSizeXY()[0], 32));
				((JLabel)GraphicObjects.get("Pan_R_Specs_ContentLabelID")).setHorizontalAlignment(JLabel.LEFT);
				GraphicObjects.get("Pan_R_Specs").add(GraphicObjects.get("Pan_R_Specs_ContentLabelID"));

				GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Pan_R_Specs"));
				this.products++;		
			}
			GraphicObjects.get("Pan_R_ContentPanel").remove(GraphicObjects.get("Pan_R_Loading"));
			((OptionButton)GraphicObjects.get("But_R_Return")).setEnabled(true);
			((OptionButton)GraphicObjects.get("But_R_Resale")).setEnabled(true);
			GraphicObjects.get("Pan_R_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],(this.products * 64) + ((this.products + 1) * GraphicConstants.getMargins())));
			GraphicObjects.get("Pan_workSpace").revalidate();
			GraphicObjects.get("Pan_workSpace").repaint();
		}).start();
	}
	
	private void handleSale()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("ww"); 
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");		
		int week = (int)Integer.valueOf(dateFormat.format(new Date()));
		int year = (int)Integer.valueOf(yearFormat.format(new Date()));
		int suggestion1 = week+1 > 51 ? 2 : week+1;
		int suggestion2 = suggestion1+1 > 51 ? 2 : suggestion1+1;
		int suggestion3 = suggestion2+1 > 51 ? 2 : suggestion2+1;
		int suggestion4 = suggestion3+1 > 51 ? 2 : suggestion3+1;
		
		SpinnerNumberModel modelWeeks = new SpinnerNumberModel(suggestion1, 2, 51, 1);
		SpinnerNumberModel modelWeeks2 = new SpinnerNumberModel(suggestion2, 2, 51, 1);
		SpinnerNumberModel modelWeeks3 = new SpinnerNumberModel(suggestion3, 2, 51, 1);
		SpinnerNumberModel modelWeeks4 = new SpinnerNumberModel(suggestion4, 2, 51, 1);
		
		GraphicObjects.add("Spi_P_for_sprint0", new JSpinner(modelWeeks));
		GraphicObjects.add("Spi_P_for_sprint1", new JSpinner(modelWeeks2));
		GraphicObjects.add("Spi_P_for_sprint2", new JSpinner(modelWeeks3));
		GraphicObjects.add("Spi_P_for_sprint3", new JSpinner(modelWeeks4));
		
		((JSpinner)GraphicObjects.get("Spi_P_for_sprint0")).setPreferredSize(GraphicConstants.getSmallButtonSize());
		((JSpinner)GraphicObjects.get("Spi_P_for_sprint1")).setPreferredSize(GraphicConstants.getSmallButtonSize());
		((JSpinner)GraphicObjects.get("Spi_P_for_sprint2")).setPreferredSize(GraphicConstants.getSmallButtonSize());
		((JSpinner)GraphicObjects.get("Spi_P_for_sprint3")).setPreferredSize(GraphicConstants.getSmallButtonSize());
		
		JPanel optionPanel = new JPanel();
		
		switch(SalesContainer.get(StateControl.getCurrentSale()).getWeeks())
		{
			case 1 : 
			{
				optionPanel.add(GraphicObjects.get("Spi_P_for_sprint0"));
				break;
			}
			
			case 2 : 
			{
				optionPanel.add(GraphicObjects.get("Spi_P_for_sprint0"));
				optionPanel.add(GraphicObjects.get("Spi_P_for_sprint1"));
				break;
			}
			case 3 :
			{
				optionPanel.add(GraphicObjects.get("Spi_P_for_sprint0"));
				optionPanel.add(GraphicObjects.get("Spi_P_for_sprint1"));
				optionPanel.add(GraphicObjects.get("Spi_P_for_sprint2"));
				break;
			}
			case 4 :
			{
				optionPanel.add(GraphicObjects.get("Spi_P_for_sprint0"));
				optionPanel.add(GraphicObjects.get("Spi_P_for_sprint1"));
				optionPanel.add(GraphicObjects.get("Spi_P_for_sprint2"));
				optionPanel.add(GraphicObjects.get("Spi_P_for_sprint3"));
				break;
			}
		}
		
		if(JOptionPane.showOptionDialog(null, optionPanel, "Para que sprints? Hoy es Sprint ("+ week +")", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) != JOptionPane.OK_OPTION)
			return;
		
		if((int)((JSpinner)GraphicObjects.get("Spi_P_for_sprint0")).getValue() <= week ||
				(int)((JSpinner)GraphicObjects.get("Spi_P_for_sprint1")).getValue() <= week ||
				(int)((JSpinner)GraphicObjects.get("Spi_P_for_sprint2")).getValue() <= week ||
				(int)((JSpinner)GraphicObjects.get("Spi_P_for_sprint3")).getValue() <= week)
		{
			if(JOptionPane.showOptionDialog(null, "Uno o mas sprints son menores o iguales al sprint actual\nTodos los sprints que sean menores o iguales al actual se asignaran para el año:\n" + (year+1), "Advertencia", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null) != JOptionPane.OK_OPTION)
				return;
		}
		
		GraphicObjects.softDropL();
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		new View_Loading("L", "Generando venta");
		
		new Thread(() ->
		{
			ClientsContainer.add(new ClientsAPI().getDocument(StateControl.getCurrentClient(), t -> new Client(
					t.getId(),
					t.getString("first_name"),
					t.getString("last_name"),
					t.getString("address"),
					t.getString("phone_number"),
					t.getString("comments"),
					SalesContainer.get(StateControl.getCurrentSale()).getSpecs(),
					t.getDate("date_of_creation"),
					SalesContainer.get(StateControl.getCurrentSale()).getDateOfCreation(),
					false,
					true,
					t.getDouble("seniority") == null ? (int)((JSpinner)GraphicObjects.get("Spi_P_for_sprint0")).getValue() <= week ? ((year+1)*100) + (int)((JSpinner)GraphicObjects.get("Spi_P_for_sprint0")).getValue() : (year*100) + (int)((JSpinner)GraphicObjects.get("Spi_P_for_sprint0")).getValue() :t.getDouble("seniority") ,
					t.getDouble("average_ticket") == null ? 0 : t.getDouble("average_ticket"),
					t.getString("last_sale_id") == null ? null : t.getString("last_sale_id")
					)));
			int sprints = SalesContainer.get(StateControl.getCurrentSale()).getWeeks();
			for (int i = 0; i<sprints; i++)
			{
				int currentValue = (int)((JSpinner)GraphicObjects.get("Spi_P_for_sprint"+i)).getValue();
				SalesContainer.get(StateControl.getCurrentSale()).setForSprint(currentValue <= week ? ((year+1)*100) + currentValue : (year*100) + currentValue);
				if(i>0) 
				{
					SalesContainer.get(StateControl.getCurrentSale()).setConsecutiveOrder(true);
					SalesContainer.get(StateControl.getCurrentSale()).setTotal_cost(0);
					new QuotationsAPI().post(SalesContainer.get(StateControl.getCurrentSale()));
					continue;
				}
				String newid = new QuotationsAPI().post(SalesContainer.get(StateControl.getCurrentSale()));
				SalesContainer.get(StateControl.getCurrentSale()).setParent_id(newid);
				ClientsContainer.get(StateControl.getCurrentClient()).setLastSaleID(newid);
			}
			ClientsContainer.get(StateControl.getCurrentClient()).setAverage_ticket(averageTicketCalculator());
			new ClientsAPI().update(ClientsContainer.get(StateControl.getCurrentClient()));
			GraphicObjects.dropL();
			new View_Clients();
		}).start();
	}
	
	private void returnFunction()
	{
		GraphicObjects.dropR();
		new View_ClientDetails();
	}
	
	private void resetClient()
	{
		JOptionPane.showMessageDialog(null, "Todas las ventas de "+ClientsContainer.get(StateControl.getCurrentClient()).getFirst_name()+" "+ClientsContainer.get(StateControl.getCurrentClient()).getLast_name()+" fueron eliminadas\nEl cliente se guardará como prospecto");
		GraphicObjects.softDropL();
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		new View_Loading("L", "Actualizando cliente");
		ClientsContainer.get(StateControl.getCurrentClient()).setCustomer(false);
		ClientsContainer.get(StateControl.getCurrentClient()).setProspect(true);
		ClientsContainer.get(StateControl.getCurrentClient()).setSeniority(0);
		ClientsContainer.get(StateControl.getCurrentClient()).setLastSaleID("");
		ClientsContainer.get(StateControl.getCurrentClient()).setLastSaleDate(null);
		ClientsContainer.get(StateControl.getCurrentClient()).setAverage_ticket(0);
		new ClientsAPI().update(ClientsContainer.get(StateControl.getCurrentClient()));
		GraphicObjects.dropL();
		new View_Clients();
	}
	
	private double averageTicketCalculator()
	{
		List<Sale> sales = new ArrayList<>();
		this.sumForAverage = 0;
		this.sumOfSales = 0;
		new SalesFromClientAPI(StateControl.getCurrentClient()).get().stream().map(o -> (Sale)o).forEach(s -> sales.add(s));	
		sales.forEach(s -> 
		{
			if(!s.isConsecutiveOrder())
			{
				this.sumForAverage += s.getTotal_cost();
				this.sumOfSales++;
			}
		});
		return truncateNumber(this.sumForAverage/this.sumOfSales);
	}
	
	private static double truncateNumber(double n)   
	{   
		//moves the decimal to the right   
		n /= .01;  
		//determines the floor value  
		n = Math.floor(n);   
		//dividing the floor value by 10 to the power decimalplace  
		n /= 100;
		//prints the number after truncation  
		return n; 
	} 
	
}
