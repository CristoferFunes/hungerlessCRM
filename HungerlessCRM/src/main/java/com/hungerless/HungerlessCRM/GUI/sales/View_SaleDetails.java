package com.hungerless.HungerlessCRM.GUI.sales;

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
import com.hungerless.HungerlessCRM.GUI.clients.Pan_ProductClientListItem;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_listItem;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.calculator.ProductItem;
import com.hungerless.HungerlessCRM.calculator.QuotationsAPI;
import com.hungerless.HungerlessCRM.sales.ConsecutiveSalesAPI;
import com.hungerless.HungerlessCRM.sales.Sale;
import com.hungerless.HungerlessCRM.sales.SalesContainer;

public class View_SaleDetails
{
	private int products = 0;
	private boolean notFound = false;
	
	View_SaleDetails()
	{
		GraphicObjects.add("Pan_R_Main", new Pan_workPanel(1, "R"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_R_Main"));
		
		GraphicObjects.add("But_R_Return", new OptionButton(true, "Regresar", () -> returnFunction()));
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_Return"));
		
		GraphicObjects.add("But_R_Delete", new OptionButton(true, "Eliminar venta", () -> handleDelete()));
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_Delete"));
		
		GraphicObjects.add("But_R_setSprint", new OptionButton(true, "Cambiar sprint", () -> setNewSprint()));
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_setSprint"));
		
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
		StateControl.setCurrentSale(null);
		GraphicObjects.dropR();
	}
	
	@SuppressWarnings("unchecked")
	private void handleDelete()
	{
		if(SalesContainer.get(StateControl.getCurrentSale()).isConsecutive_order())
		{
			this.notFound = true;
			SalesContainer.getKeySet().stream().map(k -> SalesContainer.get(k))
											   .filter(s -> s.getSaleID().equals(SalesContainer.get(StateControl.getCurrentSale()).getParent_id()))
											   .forEach(s -> this.notFound = false);
			if(this.notFound)
			{
				JOptionPane.showMessageDialog(null, "Una vez completa la primera semana consecutiva de un pedido, no puedes eliminar pedidos pagados\nPuedes editar el sprint destino");
				return;
			}
		}
		if ((JOptionPane.showInternalConfirmDialog(null, "¿Seguro que deseas eliminar la Venta?\nEsta accion es irreversible\nLas ventas de multiples semanas se eliminaran juntas", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)) != 0) return;
		GraphicObjects.softDropL();
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));	
		new View_Loading("L", "Eliminando");
		
		new Thread(() ->
		{
			if(!StateControl.getCurrentSale().equals("new"))
			{
				List<Sale> toDelete = new ArrayList<>();
				String currentID = SalesContainer.get(StateControl.getCurrentSale()).getParent_id();
				toDelete.add(new QuotationsAPI().getDocument(null == currentID ? StateControl.getCurrentSale() : currentID , t -> new Sale(
					t.getId(),
					t.getBoolean("quotation") == null ? false : t.getBoolean("quotation"),
					t.getBoolean("sale") == null ? false : t.getBoolean("sale"),
					t.getString("specs"),
					t.getDate("date_of_Creation"),
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
				new ConsecutiveSalesAPI(null == currentID ? StateControl.getCurrentSale() : currentID).get().forEach(s -> toDelete.add((Sale) s));
				
				toDelete.forEach(s -> new QuotationsAPI().delete(s));
			}
			GraphicObjects.dropL();
			new View_Sales();
		}).start();
	}
	
	private void setNewSprint()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("ww"); 
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");		
		int week = (int)Integer.valueOf(dateFormat.format(new Date()));
		int year = (int)Integer.valueOf(yearFormat.format(new Date()));
		int suggestion1 = week+1 > 51 ? 2 : week+1;
		
		SpinnerNumberModel modelWeeks = new SpinnerNumberModel(suggestion1, 2, 51, 1);
		
		GraphicObjects.add("Spi_P_for_sprint0", new JSpinner(modelWeeks));
		((JSpinner)GraphicObjects.get("Spi_P_for_sprint0")).setPreferredSize(GraphicConstants.getSmallButtonSize());
		
		JPanel optionPanel = new JPanel();
		optionPanel.add(GraphicObjects.get("Spi_P_for_sprint0"));
		
		if(JOptionPane.showOptionDialog(null, optionPanel, "Para que sprint? Hoy es Sprint ("+ week +")", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null) != JOptionPane.OK_OPTION)
			return;
		
		if((int)((JSpinner)GraphicObjects.get("Spi_P_for_sprint0")).getValue() <= week)
		{
			if(JOptionPane.showOptionDialog(null, "El sprint es menor al actual, se asignara para el año:\n" + (year+1), "Advertencia", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null) != JOptionPane.OK_OPTION)
				return;
		}
		
		int currentValue = (int)((JSpinner)GraphicObjects.get("Spi_P_for_sprint0")).getValue();
		SalesContainer.get(StateControl.getCurrentSale()).setForSprint(currentValue <= week ? ((year+1)*100) + currentValue : (year*100) + currentValue);
		
		GraphicObjects.softDropL();
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));	
		new View_Loading("L", "Eliminando");
		
		new Thread(() ->
		{
			new QuotationsAPI().update(SalesContainer.get(StateControl.getCurrentSale()));
			GraphicObjects.dropL();
			new View_Sales();
		}).start();
		
	}
}
