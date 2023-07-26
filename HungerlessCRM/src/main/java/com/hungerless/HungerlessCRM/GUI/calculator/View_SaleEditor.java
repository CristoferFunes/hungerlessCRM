package com.hungerless.HungerlessCRM.GUI.calculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_listItem;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Tex_TextArea;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.calculator.PricesContainer;
import com.hungerless.HungerlessCRM.calculator.ProductItem;
import com.hungerless.HungerlessCRM.calculator.ProductsContainer;
import com.hungerless.HungerlessCRM.calculator.QuotationsAPI;
import com.hungerless.HungerlessCRM.clients.Client;
import com.hungerless.HungerlessCRM.clients.ClientsAPI;
import com.hungerless.HungerlessCRM.clients.ClientsContainer;
import com.hungerless.HungerlessCRM.clients.SalesFromClientAPI;
import com.hungerless.HungerlessCRM.sales.Sale;
import com.hungerless.HungerlessCRM.sales.SalesContainer;

public class View_SaleEditor
{
	private boolean specsEditor = false;
	private int editorSize = 181;
	private int productsCount;
	private double sumForAverage;
	private int sumOfSales;
	SpinnerModel modelAmount, modelInner, modelWeeks, modelCalories, modelMultiplierPP, modelMultiplierG1, modelMultiplierG2, modelDiscount, modelShipment;
	
	View_SaleEditor()
	{
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		new View_Loading("L","Cargando cotizacion");
		
		if(StateControl.getCurrentSale().equals("new"))
		{
			SalesContainer.add(new Sale(
					"new",
					true,
					false,
					"",
					new Date(),
					0,
					0,
					0,
					1,
					ClientsContainer.get(StateControl.getCurrentClient()).getClientID(),
					ClientsContainer.get(StateControl.getCurrentClient()).getFirst_name()+" "+ClientsContainer.get(StateControl.getCurrentClient()).getLast_name(),
					new ArrayList<String>(),
					false,
					false,
					null));
		}
		else
		{
			SalesContainer.get(StateControl.getCurrentSale()).getProducts().stream().forEach(p -> 
			{
				ProductsContainer.add(new ProductItem(p, false));
			});
		}
		
		new Thread(()->
		{
			if(!new ClientsAPI().getDocument(SalesContainer.get(StateControl.getCurrentSale()).getClient_id(), d -> d.exists()) &&
					!StateControl.getCurrentSale().equals("new") &&
					!SalesContainer.get(StateControl.getCurrentSale()).isLegacy())
			{
				SalesContainer.get(StateControl.getCurrentSale()).setClient_name("Legacy: " + SalesContainer.get(StateControl.getCurrentSale()).getClient_name());
				SalesContainer.get(StateControl.getCurrentSale()).setLegacy(true);
				new QuotationsAPI().update(SalesContainer.get(StateControl.getCurrentSale()));
			}
			if(SalesContainer.get(StateControl.getCurrentSale()).isLegacy())
			{
				JOptionPane.showMessageDialog(null, "Estas editando una cotizacion de un cliente que esta eliminado\nRecomiendo que elimines la cotizacion y hagas otra asociando un cliente\nLas funciones estan limitadas");
			}
			GraphicObjects.softDropL();
			productEditor();
			saleView();
		}).start();
	}
	
	@SuppressWarnings("unchecked")
	private void productEditor()
	{	
		editorSize = 196;
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		
		GraphicObjects.add("But_L_Add", new OptionButton(true, "Nuevo prod.", () -> newProduct()));
		GraphicObjects.add("But_L_Shipment", new OptionButton(true, "Nuevo envio", () -> newShipment()));
		GraphicObjects.add("But_L_Specs", new OptionButton(true, "Especif.", () -> editSpecs()));
		GraphicObjects.add("But_L_Return", new OptionButton(true, "Regresar", () -> functionReturn()));
		
		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Add"));
		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Shipment"));
		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Specs"));
		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Return"));
		
		GraphicObjects.add("Lab_L_clientName", new JLabel(StateControl.getCurrentSale().equals("new") ? ClientsContainer.get(StateControl.getCurrentClient()).getFirst_name() + " " + ClientsContainer.get(StateControl.getCurrentClient()).getLast_name(): SalesContainer.get(StateControl.getCurrentSale()).getClient_name() )); //name of client
		GraphicObjects.get("Lab_L_clientName").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],10));
		GraphicObjects.get("Lab_L_clientName").setForeground(Color.WHITE);
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_clientName"));
		
		GraphicObjects.get("Pan_workSpace").revalidate();
		GraphicObjects.get("Pan_workSpace").repaint();
		
		if(this.specsEditor)
		{
			GraphicObjects.add("Lab_L_1", new JLabel("Especificaciones:"));
			GraphicObjects.get("Lab_L_1").setPreferredSize(GraphicConstants.getTextAreaLabelSize());
			GraphicObjects.get("Lab_L_1").setForeground(Color.WHITE);
				
			GraphicObjects.add("Tex_L_Specs", new Tex_TextArea(GraphicConstants.getLargeTextSize(),SalesContainer.get(StateControl.getCurrentSale()).getSpecs()));
			
			GraphicObjects.add("But_L_SaveSpecs", new OptionButton(true, "Guardar", () -> 
			{
				SalesContainer.get(StateControl.getCurrentSale()).setSpecs(((Tex_TextArea)GraphicObjects.get("Tex_L_Specs")).getText());
				this.specsEditor = false;
				GraphicObjects.softDropL();
				productEditor();
				saleView();
			}));
	
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_1"));
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_Specs"));
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("But_L_SaveSpecs"));
			
			return;
		}
		
		if(StateControl.getCurrentProduct() == 0) return;
		
		if(ProductsContainer.get(StateControl.getCurrentProduct()).getType() == 3)
		{
			GraphicObjects.add("Lab_L_1", new JLabel("Envio: $"));
			((JLabel)GraphicObjects.get("Lab_L_1")).setFont(new Font(null,Font.BOLD,16));
			GraphicObjects.get("Lab_L_1").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(2*GraphicConstants.getMargins()))/8)*2, 34));
			GraphicObjects.get("Lab_L_1").setForeground(Color.WHITE);
			
			modelShipment = new SpinnerNumberModel(50, 1, 150, 1.0);
			GraphicObjects.add("Spi_L_ShipAmount", new JSpinner(modelShipment));
			GraphicObjects.get("Spi_L_ShipAmount").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(2*GraphicConstants.getMargins()))/8)*2, 34));
			((JSpinner)GraphicObjects.get("Spi_L_ShipAmount")).setValue(ProductsContainer.get(StateControl.getCurrentProduct()).getUnit());
			((JSpinner)GraphicObjects.get("Spi_L_ShipAmount")).setFont(new Font(null,Font.BOLD,20));
			((JSpinner)GraphicObjects.get("Spi_L_ShipAmount")).addChangeListener(e ->
			{
				ProductsContainer.get(StateControl.getCurrentProduct()).setUnit((double) ((JSpinner)GraphicObjects.get("Spi_L_ShipAmount")).getValue());
				SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
				GraphicObjects.dropR();
				saleView();
			});
			
			GraphicObjects.add("But_L_SaveShipment", new OptionButton(true,"Guardar", () ->
			{
				StateControl.setCurrentProduct(0);
				GraphicObjects.softDropL();
				productEditor();
				saleView();
			}));
			((OptionButton)GraphicObjects.get("But_L_SaveShipment")).setNewSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(2*GraphicConstants.getMargins()))/8)*4, 34));
			
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_1"));
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Spi_L_ShipAmount"));
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("But_L_SaveShipment"));
			return;
		}
		
		modelAmount = new SpinnerNumberModel(1, 1, 99, 1);		
		GraphicObjects.add("Spi_L_Amount", new JSpinner(modelAmount));
		GraphicObjects.get("Spi_L_Amount").setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(4*GraphicConstants.getMargins()))/9, 34));
		((JSpinner)GraphicObjects.get("Spi_L_Amount")).setValue(ProductsContainer.get(StateControl.getCurrentProduct()).getOriginalAmount());
		((JSpinner)GraphicObjects.get("Spi_L_Amount")).setFont(new Font(null,Font.BOLD,20));
		((JSpinner)GraphicObjects.get("Spi_L_Amount")).addChangeListener(e -> 
		{
			ProductsContainer.get(StateControl.getCurrentProduct()).setAmount((int) ((JSpinner)GraphicObjects.get("Spi_L_Amount")).getValue());
			SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
			GraphicObjects.dropR();
			saleView();
		});
		
		GraphicObjects.add("Lab_L_PackageText", new JLabel("X paquetes de"));
		((JLabel)GraphicObjects.get("Lab_L_PackageText")).setFont(new Font(null,Font.BOLD,16));
		GraphicObjects.get("Lab_L_PackageText").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(4*GraphicConstants.getMargins()))/9)*3, 34));
		GraphicObjects.get("Lab_L_PackageText").setForeground(Color.WHITE);
		
		modelInner = new SpinnerNumberModel(5, 1, 99, 1);
		GraphicObjects.add("Spi_L_Inner", new JSpinner(modelInner));
		GraphicObjects.get("Spi_L_Inner").setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(4*GraphicConstants.getMargins()))/9, 34));
		((JSpinner)GraphicObjects.get("Spi_L_Inner")).setValue(ProductsContainer.get(StateControl.getCurrentProduct()).getInner());
		((JSpinner)GraphicObjects.get("Spi_L_Inner")).setFont(new Font(null,Font.BOLD,20));
		((JSpinner)GraphicObjects.get("Spi_L_Inner")).addChangeListener(e ->
		{
			ProductsContainer.get(StateControl.getCurrentProduct()).setInner((int) ((JSpinner)GraphicObjects.get("Spi_L_Inner")).getValue());
			SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
			GraphicObjects.dropR();
			saleView();
		});
		
		String[] typeOptions = {"Comidas", "Desayunos"};
		GraphicObjects.add("Box_L_MorB", new JComboBox<String>(typeOptions));
		GraphicObjects.get("Box_L_MorB").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(4*GraphicConstants.getMargins()))/9)*2, 34));
		((JComboBox<String>)GraphicObjects.get("Box_L_MorB")).setFont(new Font(null,Font.PLAIN,15));
		((JComboBox<String>)GraphicObjects.get("Box_L_MorB")).setSelectedItem(ProductsContainer.get(StateControl.getCurrentProduct()).getTypeWriten());
		((JComboBox<String>)GraphicObjects.get("Box_L_MorB")).addActionListener(e ->
		{
			int type;
			switch((String) ((JComboBox<String>)GraphicObjects.get("Box_L_MorB")).getSelectedItem())
			{
				case "Comidas" : type = 1;
				break;
				case "Desayunos" : type = 2;
				break;
				default : throw new IllegalArgumentException("Unexpected value: " + (String) ((JComboBox<String>)GraphicObjects.get("Box_L_MorB")).getSelectedItem());
			}
			ProductsContainer.get(StateControl.getCurrentProduct()).setType(type);
			SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
			GraphicObjects.dropR();
			saleView();
		});
		
		GraphicObjects.add("But_L_SaveProd", new OptionButton(true ,"Guardar", () -> 
		{
			StateControl.setCurrentProduct(0);
			GraphicObjects.softDropL();
			productEditor();
			saleView();
		}));
		((OptionButton)GraphicObjects.get("But_L_SaveProd")).setNewSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(4*GraphicConstants.getMargins()))/9)*2, 34));
		
		GraphicObjects.add("Lab_L_Margin1", new JLabel()); 
		GraphicObjects.get("Lab_L_Margin1").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],1));
		
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Spi_L_Amount"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_PackageText"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Spi_L_Inner"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Box_L_MorB"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("But_L_SaveProd"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_Margin1"));
		
		GraphicObjects.add("Che_L_DiscountCheck", new JCheckBox());
		((JCheckBox)GraphicObjects.get("Che_L_DiscountCheck")).setText("Descuento?");
		((JCheckBox)GraphicObjects.get("Che_L_DiscountCheck")).setSelected(ProductsContainer.get(StateControl.getCurrentProduct()).isProductDiscount());
		((JCheckBox)GraphicObjects.get("Che_L_DiscountCheck")).addItemListener(e ->
		{
			ProductsContainer.get(StateControl.getCurrentProduct()).setIsProductDiscount(e.getStateChange() == ItemEvent.SELECTED);
			if(e.getStateChange() == ItemEvent.DESELECTED) 
			{
				ProductsContainer.get(StateControl.getCurrentProduct()).setDiscount(0);
				SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
			}
			GraphicObjects.softDropL();
			productEditor();
			saleView();
		});
		
		GraphicObjects.add("Lab_L_Margin2", new JLabel()); 
		GraphicObjects.get("Lab_L_Margin2").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],1));
		
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Che_L_DiscountCheck"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_Margin2"));
		
		if(ProductsContainer.get(StateControl.getCurrentProduct()).isProductDiscount())
		{
			editorSize += 54;
			modelDiscount = new SpinnerNumberModel(0.0, 0.0, 99.9, 0.1);
			GraphicObjects.add("Lab_L_Discount", new JLabel("Descuento:"));
			((JLabel)GraphicObjects.get("Lab_L_Discount")).setFont(new Font(null,Font.BOLD,16));
			GraphicObjects.get("Lab_L_Discount").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(2*GraphicConstants.getMargins()))/8)*2, 34));
			GraphicObjects.get("Lab_L_Discount").setForeground(ProductsContainer.get(StateControl.getCurrentProduct()).getDiscount() > 10.0 ? Color.RED : Color.WHITE);
			
			GraphicObjects.add("Spi_L_Discount", new JSpinner(modelDiscount));
			GraphicObjects.get("Spi_L_Discount").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(2*GraphicConstants.getMargins()))/8)*5, 34));
			((JSpinner)GraphicObjects.get("Spi_L_Discount")).setValue(ProductsContainer.get(StateControl.getCurrentProduct()).getDiscount());
			//ProductsContainer.get(StateControl.getCurrentProduct()).getDiscount() > 10.0 ? Color.RED : Color.WHITE
			((JSpinner)GraphicObjects.get("Spi_L_Discount")).setFont(new Font(null,Font.BOLD,15));
			((JSpinner)GraphicObjects.get("Spi_L_Discount")).addChangeListener(e ->
			{
				ProductsContainer.get(StateControl.getCurrentProduct()).setDiscount((double) ((JSpinner)GraphicObjects.get("Spi_L_Discount")).getValue());
				GraphicObjects.get("Lab_L_Discount").setForeground(ProductsContainer.get(StateControl.getCurrentProduct()).getDiscount() > PricesContainer.get("maximum_discount") ? Color.RED : Color.WHITE);
				GraphicObjects.get("Lab_L_Percent").setForeground(ProductsContainer.get(StateControl.getCurrentProduct()).getDiscount() > PricesContainer.get("maximum_discount") ? Color.RED : Color.WHITE);
				SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
				GraphicObjects.dropR();
				saleView();
			});
			GraphicObjects.add("Lab_L_Percent", new JLabel("%"));
			((JLabel)GraphicObjects.get("Lab_L_Percent")).setFont(new Font(null,Font.BOLD,16));
			GraphicObjects.get("Lab_L_Percent").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(2*GraphicConstants.getMargins()))/8)*1, 34));
			GraphicObjects.get("Lab_L_Percent").setForeground(ProductsContainer.get(StateControl.getCurrentProduct()).getDiscount() > 10.0 ? Color.RED : Color.WHITE);
			
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_Discount"));
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Spi_L_Discount"));
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_Percent"));
		}
		
		GraphicObjects.add("Che_L_DietCheck", new JCheckBox());
		((JCheckBox)GraphicObjects.get("Che_L_DietCheck")).setText("Dieta especial?");
		((JCheckBox)GraphicObjects.get("Che_L_DietCheck")).setSelected(ProductsContainer.get(StateControl.getCurrentProduct()).isDiet());
		((JCheckBox)GraphicObjects.get("Che_L_DietCheck")).addItemListener(e ->
		{
			ProductsContainer.get(StateControl.getCurrentProduct()).setIsDiet(e.getStateChange() == ItemEvent.SELECTED);
			if(e.getStateChange() == ItemEvent.DESELECTED) 
			{
				ProductsContainer.get(StateControl.getCurrentProduct()).setCalories(2000);
				ProductsContainer.get(StateControl.getCurrentProduct()).setMultiplierPP(1);
				ProductsContainer.get(StateControl.getCurrentProduct()).setMultiplierG1(1);
				ProductsContainer.get(StateControl.getCurrentProduct()).setMultiplierG2(1);
				SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
			}
			GraphicObjects.softDropL();
			productEditor();
			saleView();
		});
		
		GraphicObjects.add("Lab_L_Margin3", new JLabel()); 
		GraphicObjects.get("Lab_L_Margin3").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],1));
		
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Che_L_DietCheck"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_Margin3"));
		
		if(ProductsContainer.get(StateControl.getCurrentProduct()).isDiet())
		{
			editorSize += 160;
			modelCalories = new SpinnerNumberModel(2000, 1200, 3000, 200);
			modelMultiplierPP = new SpinnerNumberModel(1, 0.1, 3, 0.05);
			modelMultiplierG1 = new SpinnerNumberModel(1, 0.1, 3, 0.05);
			modelMultiplierG2 = new SpinnerNumberModel(1, 0.1, 3, 0.05);
			GraphicObjects.add("Lab_L_Calories", new JLabel("Calorias:")); //name label _3
			GraphicObjects.get("Lab_L_Calories").setPreferredSize(GraphicConstants.getTextAreaLabelSize());
			GraphicObjects.get("Lab_L_Calories").setForeground(Color.WHITE);
			
			GraphicObjects.add("Spi_L_Calories", new JSpinner(modelCalories));
			GraphicObjects.get("Spi_L_Calories").setPreferredSize(GraphicConstants.getSmallTextSize());
			((JSpinner)GraphicObjects.get("Spi_L_Calories")).setValue(ProductsContainer.get(StateControl.getCurrentProduct()).getCalories());
			((JSpinner)GraphicObjects.get("Spi_L_Calories")).setFont(new Font(null,Font.BOLD,15));
			((JSpinner)GraphicObjects.get("Spi_L_Calories")).addChangeListener(e ->
			{
				ProductsContainer.get(StateControl.getCurrentProduct()).setCalories((int) ((JSpinner)GraphicObjects.get("Spi_L_Calories")).getValue());
				SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
				GraphicObjects.dropR();
				saleView();
			});
			
			GraphicObjects.add("Lab_L_PP", new JLabel("Principal:")); //name label _3
			GraphicObjects.get("Lab_L_PP").setPreferredSize(GraphicConstants.getTextAreaLabelSize());
			GraphicObjects.get("Lab_L_PP").setForeground(Color.WHITE);
			GraphicObjects.get("Lab_L_PP").setToolTipText("Es multiplicador");
			
			GraphicObjects.add("Spi_L_PP", new JSpinner(modelMultiplierPP));
			GraphicObjects.get("Spi_L_PP").setPreferredSize(GraphicConstants.getSmallTextSize());
			((JSpinner)GraphicObjects.get("Spi_L_PP")).setValue(ProductsContainer.get(StateControl.getCurrentProduct()).getMultiplierPP());
			((JSpinner)GraphicObjects.get("Spi_L_PP")).setFont(new Font(null,Font.BOLD,15));
			((JSpinner)GraphicObjects.get("Spi_L_PP")).addChangeListener(e ->
			{
				ProductsContainer.get(StateControl.getCurrentProduct()).setMultiplierPP((double) ((JSpinner)GraphicObjects.get("Spi_L_PP")).getValue());
				SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
				GraphicObjects.dropR();
				saleView();
			});
			
			GraphicObjects.add("Lab_L_G1", new JLabel("Guarn. Carbs:")); //name label _3
			GraphicObjects.get("Lab_L_G1").setPreferredSize(GraphicConstants.getTextAreaLabelSize());
			GraphicObjects.get("Lab_L_G1").setForeground(Color.WHITE);
			GraphicObjects.get("Lab_L_G1").setToolTipText("Es multiplicador");
			
			GraphicObjects.add("Spi_L_G1", new JSpinner(modelMultiplierG1));
			GraphicObjects.get("Spi_L_G1").setPreferredSize(GraphicConstants.getSmallTextSize());
			((JSpinner)GraphicObjects.get("Spi_L_G1")).setValue(ProductsContainer.get(StateControl.getCurrentProduct()).getMultiplierG1());
			((JSpinner)GraphicObjects.get("Spi_L_G1")).setFont(new Font(null,Font.BOLD,15));
			((JSpinner)GraphicObjects.get("Spi_L_G1")).addChangeListener(e ->
			{
				ProductsContainer.get(StateControl.getCurrentProduct()).setMultiplierG1((double) ((JSpinner)GraphicObjects.get("Spi_L_G1")).getValue());
				SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
				GraphicObjects.dropR();
				saleView();
			});
			
			GraphicObjects.add("Lab_L_G2", new JLabel("Guarn. Vegetal:")); //name label _3
			GraphicObjects.get("Lab_L_G2").setPreferredSize(GraphicConstants.getTextAreaLabelSize());
			GraphicObjects.get("Lab_L_G2").setForeground(Color.WHITE);
			GraphicObjects.get("Lab_L_G2").setToolTipText("Es multiplicador");
			
			GraphicObjects.add("Spi_L_G2", new JSpinner(modelMultiplierG2));
			GraphicObjects.get("Spi_L_G2").setPreferredSize(GraphicConstants.getSmallTextSize());
			((JSpinner)GraphicObjects.get("Spi_L_G2")).setValue(ProductsContainer.get(StateControl.getCurrentProduct()).getMultiplierG2());
			((JSpinner)GraphicObjects.get("Spi_L_G2")).setFont(new Font(null,Font.BOLD,15));
			((JSpinner)GraphicObjects.get("Spi_L_G2")).addChangeListener(e ->
			{
				ProductsContainer.get(StateControl.getCurrentProduct()).setMultiplierG2((double) ((JSpinner)GraphicObjects.get("Spi_L_G2")).getValue());
				SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
				GraphicObjects.dropR();
				saleView();
			});
			
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_Calories"));
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Spi_L_Calories"));
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_PP"));
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Spi_L_PP"));
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_G1"));
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Spi_L_G1"));
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_G2"));
			GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Spi_L_G2"));
		}
		
		GraphicObjects.get("Pan_L_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],this.editorSize));
		
	}

	private void saleView()
	{
		this.productsCount = 0;
		GraphicObjects.add("Pan_R_Main", new Pan_workPanel(2, "R"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_R_Main"));
		
		GraphicObjects.add("But_R_Save", new OptionButton(true, "Guardar", () -> handleSave()));
		GraphicObjects.add("But_R_Delete", new OptionButton(true, "Eliminar cotiz.", () -> handleDelete()));
		GraphicObjects.add("But_R_Print", new OptionButton(false, "Imprimir", () -> functionPrint()));
		((OptionButton)GraphicObjects.get("But_R_Print")).setToolTipText("Aun no disponible");
		
		GraphicObjects.add("Lab_R_Tokens", new JLabel(SalesContainer.get(StateControl.getCurrentSale()).getTokens() + " Tokens"));
		((JLabel)GraphicObjects.get("Lab_R_Tokens")).setFont(new Font(null,Font.BOLD,16));
		GraphicObjects.get("Lab_R_Tokens").setPreferredSize(GraphicConstants.getButtonSize());
		GraphicObjects.get("Lab_R_Tokens").setForeground(Color.WHITE);
		
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_Save"));
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_Delete"));
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("But_R_Print"));
		GraphicObjects.get("Pan_R_OptionsPanel").add(GraphicObjects.get("Lab_R_Tokens"));
		
		modelWeeks = new SpinnerNumberModel(1, 1, 4, 1);	
		GraphicObjects.add("Spi_R_Weeks", new JSpinner(modelWeeks));
		GraphicObjects.get("Spi_R_Weeks").setPreferredSize(new Dimension((GraphicConstants.getButtonSizeXY()[0]/3), GraphicConstants.getButtonSizeXY()[1]));
		((JSpinner)GraphicObjects.get("Spi_R_Weeks")).setFocusable(false);
		((JSpinner)GraphicObjects.get("Spi_R_Weeks")).setValue(SalesContainer.get(StateControl.getCurrentSale()).getWeeks());
		((JSpinner)GraphicObjects.get("Spi_R_Weeks")).setFont(new Font(null,Font.BOLD,15));
		((JSpinner)GraphicObjects.get("Spi_R_Weeks")).addChangeListener(e ->
		{
			SalesContainer.get(StateControl.getCurrentSale()).setWeeks((Integer) ((JSpinner) GraphicObjects.get("Spi_R_Weeks")).getValue());
			ProductsContainer.getKeySet().forEach(p -> ProductsContainer.get(p).revalidateCost());
			SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
			GraphicObjects.dropR();
			saleView();
		});
		
		GraphicObjects.add("Lab_R_Weeks", new JLabel("Semanas"));
		((JLabel)GraphicObjects.get("Lab_R_Weeks")).setFont(new Font(null,Font.BOLD,16));
		GraphicObjects.get("Lab_R_Weeks").setPreferredSize(new Dimension((GraphicConstants.getButtonSizeXY()[0]/3)*2, GraphicConstants.getButtonSizeXY()[1]));
		GraphicObjects.get("Lab_R_Weeks").setForeground(Color.WHITE);
		
		GraphicObjects.add("Lab_R_TotalCost", new JLabel("Total: $" + SalesContainer.get(StateControl.getCurrentSale()).getTotal_cost()));
		((JLabel)GraphicObjects.get("Lab_R_TotalCost")).setFont(new Font(null,Font.BOLD,16));
		GraphicObjects.get("Lab_R_TotalCost").setPreferredSize(GraphicConstants.getLargeButtonSize());
		GraphicObjects.get("Lab_R_TotalCost").setForeground(Color.WHITE);
		
		GraphicObjects.add("But_R_Sale", new OptionButton(false, "VENTA", () -> handleSale()));
		((OptionButton)GraphicObjects.get("But_R_Sale")).setEnabled
		(
			SalesContainer.get(StateControl.getCurrentSale()).getTokens()>=3 && 
			ProductsContainer.getKeySet().stream().filter(p -> ProductsContainer.get(p).getType() == 3).count()>0 && 
			!SalesContainer.get(StateControl.getCurrentSale()).isLegacy()
		);
		((OptionButton)GraphicObjects.get("But_R_Sale")).setToolTipText
		(
			((OptionButton)GraphicObjects.get("But_R_Sale")).isEnabled() ?
			null :
			"Necesitas minimo 3 tokens, un envio y no puede faltarle Cliente"
		);
		
		GraphicObjects.get("Pan_R_Footer").add(GraphicObjects.get("Spi_R_Weeks"));
		GraphicObjects.get("Pan_R_Footer").add(GraphicObjects.get("Lab_R_Weeks"));
		GraphicObjects.get("Pan_R_Footer").add(GraphicObjects.get("Lab_R_TotalCost"));
		GraphicObjects.get("Pan_R_Footer").add(GraphicObjects.get("But_R_Sale"));
		
		ProductsContainer.getKeySet().forEach(p -> 
		{
			GraphicObjects.add("Lab_R_ProductID"+p, new Pan_listItem(true, String.valueOf(p), () -> 
			{
				this.specsEditor = false;
				StateControl.setCurrentProduct(p);
				GraphicObjects.softDropL();
				productEditor();
				saleView();
			}));
			
			if(p == StateControl.getCurrentProduct())
			{
				((Pan_listItem)GraphicObjects.get("Lab_R_ProductID"+p)).setBackground(GraphicConstants.getHighlightedListItemColor());
				((Pan_listItem)GraphicObjects.get("Lab_R_ProductID"+p)).currentColor = GraphicConstants.getHighlightedListItemColor();
			}
			GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Lab_R_ProductID"+p));
			
			GraphicObjects.add("Lab_R_Product_ContentLabelID" +p, new JLabel
			(
					ProductsContainer.get(p).getType() != 3 ? 
					ProductsContainer.get(p).getAmount()+" x "+ProductsContainer.get(p).getTypeWriten()+"("+ProductsContainer.get(p).getInner()+")"+" Total: $"+ProductsContainer.get(p).getTotal() :
					ProductsContainer.get(p).getAmount()+" x "+ProductsContainer.get(p).getTypeWriten()+" Total: $"+ProductsContainer.get(p).getTotal()	
			));
			GraphicObjects.get("Lab_R_Product_ContentLabelID" +p).setFont(new Font(null,Font.BOLD,16));
			GraphicObjects.get("Lab_R_Product_ContentLabelID" +p).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6)*4, GraphicConstants.getObjectsSizeXY()[1]));
			((JLabel)GraphicObjects.get("Lab_R_Product_ContentLabelID" +p)).setHorizontalAlignment(JLabel.CENTER);
		
			GraphicObjects.add("Lab_R_Product_StatusID" +p, new JLabel(ProductsContainer.get(p).isDiet() ? "Especial" : "Normal")); 
			GraphicObjects.get("Lab_R_Product_StatusID" +p).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6), GraphicConstants.getObjectsSizeXY()[1]));
			((JLabel)GraphicObjects.get("Lab_R_Product_StatusID" + +p)).setHorizontalAlignment(JLabel.CENTER);
			
			GraphicObjects.add("But_R_Product_DeleteID" +p, new OptionButton(true,"x", () ->
			{
				if ((JOptionPane.showInternalConfirmDialog(null, "¿Eliminar producto?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)) != 0) return;
				ProductsContainer.remove(p);
				GraphicObjects.softDropL();
				StateControl.setCurrentProduct(0);
				SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
				productEditor();
				saleView();
			}));
			GraphicObjects.get("But_R_Product_DeleteID" +p).setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6), GraphicConstants.getObjectsSizeXY()[1]));
			((OptionButton)GraphicObjects.get("But_R_Product_DeleteID" +p)).setFont(new Font(null,Font.BOLD,16));
			((OptionButton)GraphicObjects.get("But_R_Product_DeleteID" +p)).setFocusable(false);
			((OptionButton)GraphicObjects.get("But_R_Product_DeleteID" +p)).setBackground(GraphicConstants.getChecklistColor());
			((OptionButton)GraphicObjects.get("But_R_Product_DeleteID" +p)).setBorder(null);
			
			GraphicObjects.get("Lab_R_ProductID"+p).add(GraphicObjects.get("But_R_Product_DeleteID" +p));
			GraphicObjects.get("Lab_R_ProductID"+p).add(GraphicObjects.get("Lab_R_Product_ContentLabelID" + p));	
			if(ProductsContainer.get(p).getType() != 3)
			{
				GraphicObjects.get("Lab_R_ProductID"+p).add(GraphicObjects.get("Lab_R_Product_StatusID" + p));
			}
			this.productsCount++;
		});
		
		
		if(!SalesContainer.get(StateControl.getCurrentSale()).getSpecs().equals("")) 
		{
			GraphicObjects.add("Pan_R_Specs", new Pan_listItem(true, StateControl.getCurrentSale(), () -> 
			{
				this.specsEditor = true;
				StateControl.setCurrentProduct(0);
				GraphicObjects.softDropL();
				productEditor();
				saleView();
			}));
			if(this.specsEditor)
			{
				((Pan_listItem)GraphicObjects.get("Pan_R_Specs")).setBackground(GraphicConstants.getHighlightedListItemColor());
				((Pan_listItem)GraphicObjects.get("Pan_R_Specs")).currentColor = GraphicConstants.getHighlightedListItemColor();
			}
			GraphicObjects.get("Pan_R_Specs").setLayout(new FlowLayout(FlowLayout.LEADING,GraphicConstants.getMargins(),GraphicConstants.getMargins()));
			
			GraphicObjects.add("Pan_R_Specs_ContentLabelID", new JLabel("Especificaciones: "+SalesContainer.get(StateControl.getCurrentSale()).getSpecs()));
			GraphicObjects.get("Pan_R_Specs_ContentLabelID").setFont(new Font(null,Font.PLAIN,15));
			GraphicObjects.get("Pan_R_Specs_ContentLabelID").setPreferredSize(new Dimension((GraphicConstants.getObjectsWithScrollSizeXY()[0]/6)*5, 32));
			((JLabel)GraphicObjects.get("Pan_R_Specs_ContentLabelID")).setHorizontalAlignment(JLabel.LEFT);
			GraphicObjects.get("Pan_R_Specs").add(GraphicObjects.get("Pan_R_Specs_ContentLabelID"));

			GraphicObjects.get("Pan_R_ContentPanel").add(GraphicObjects.get("Pan_R_Specs"));
			this.productsCount++;		
		}

		GraphicObjects.get("Pan_R_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0],(this.productsCount * 64) + ((this.productsCount + 1) * GraphicConstants.getMargins())));
		GraphicObjects.get("Pan_workSpace").revalidate();
		GraphicObjects.get("Pan_workSpace").repaint();
	}
	
	private void newProduct()
	{
		this.specsEditor = false;
		ProductItem temp = new ProductItem(ProductItem.BUILDERMEAL, true);
		StateControl.setCurrentProduct(temp.getProductID());
		ProductsContainer.add(temp);
		SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
		GraphicObjects.softDropL();
		productEditor();
		saleView();
	}
	
	private void newShipment()
	{
		this.specsEditor = false;
		ProductItem temp = new ProductItem(ProductItem.BUILDERSHIP, true);
		StateControl.setCurrentProduct(temp.getProductID());
		ProductsContainer.add(temp);
		SalesContainer.get(StateControl.getCurrentSale()).updateTotalCost();
		GraphicObjects.softDropL();
		productEditor();
		saleView();
	}
	
	private void editSpecs()
	{
		this.specsEditor = true;
		GraphicObjects.softDropL();
		productEditor();
		saleView();
	}
	
	private void functionReturn()
	{
		StateControl.setCurrentClient(null);
		StateControl.setCurrentSale(null);
		StateControl.setCurrentProduct(0);
		ClientsContainer.clearClients();
		SalesContainer.clearSales();
		ProductsContainer.clearProducts();
		GraphicObjects.softDropL();
		new View_Quotations();
	}
	
	private void handleSave()
	{
		GraphicObjects.softDropL();
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));	
		new View_Loading("L", "Guardando");
		
		ArrayList<String> products = new ArrayList<>();	
		ProductsContainer.getKeySet().stream().map(k -> ProductsContainer.get(k)).forEach(p -> products.add(p.codeGenerator()));
		SalesContainer.get(StateControl.getCurrentSale()).setProducts(products);
		
		new Thread(() ->
		{
			if(StateControl.getCurrentSale().equals("new"))
			{
				new QuotationsAPI().post(SalesContainer.get(StateControl.getCurrentSale()));
			}
			else
			{
				new QuotationsAPI().update(SalesContainer.get(StateControl.getCurrentSale()));
			}
			functionReturn();
		}).start();
	}
	
	private void handleDelete()
	{
		if ((JOptionPane.showInternalConfirmDialog(null, "¿Seguro que deseas eliminar la cotizacion?\nEsta accion es irreversible", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)) != 0) return;
		GraphicObjects.softDropL();
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));	
		new View_Loading("L", "Eliminando");
		
		new Thread(() ->
		{
			if(!StateControl.getCurrentSale().equals("new"))
			{
				new QuotationsAPI().delete(SalesContainer.get(StateControl.getCurrentSale()));
			}
			functionReturn();
		}).start();
	}
	
	private void functionPrint()
	{
		
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
		GraphicObjects.get("Pan_workSpace").revalidate();
		GraphicObjects.get("Pan_workSpace").repaint();
		new View_Loading("L","Generando venta");
		
		SalesContainer.get(StateControl.getCurrentSale()).setSale(true);
		SalesContainer.get(StateControl.getCurrentSale()).setQuotation(false);
		SalesContainer.get(StateControl.getCurrentSale()).setDateOfCreation(new Date());
		
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
			ArrayList<String> products = new ArrayList<>();	
			ProductsContainer.getKeySet().stream().map(k -> ProductsContainer.get(k)).forEach(p -> products.add(p.codeGenerator()));
			SalesContainer.get(StateControl.getCurrentSale()).setProducts(products);
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
				
				if(StateControl.getCurrentSale().equals("new"))
				{
					String newid = new QuotationsAPI().post(SalesContainer.get(StateControl.getCurrentSale()));
					SalesContainer.get(StateControl.getCurrentSale()).setParent_id(newid);
					ClientsContainer.get(StateControl.getCurrentClient()).setLastSaleID(newid);
					continue;
				}
				
				new QuotationsAPI().update(SalesContainer.get(StateControl.getCurrentSale()));
			}
			ClientsContainer.get(StateControl.getCurrentClient()).setAverage_ticket(averageTicketCalculator());
			new ClientsAPI().update(ClientsContainer.get(StateControl.getCurrentClient()));
			functionReturn();
		}).start();
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
