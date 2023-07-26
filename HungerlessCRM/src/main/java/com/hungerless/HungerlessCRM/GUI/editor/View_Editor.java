package com.hungerless.HungerlessCRM.GUI.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.OptionButton;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Pan_workPanel;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Tex_TextArea;
import com.hungerless.HungerlessCRM.GUI.graphicConstructors.View_Loading;
import com.hungerless.HungerlessCRM.calculator.PricesAPI;
import com.hungerless.HungerlessCRM.calculator.PricesContainer;

public class View_Editor
{
	Pattern pattern = Pattern.compile("^(?=.*\\d)\\d*(?:\\.\\d{1,})?$");
	Matcher matcher;
	
	public View_Editor()
	{
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		
		GraphicObjects.add("But_L_Save", new OptionButton(false, "Guardar", () -> handleSave()));
		GraphicObjects.add("But_L_Multipliers", new OptionButton(false, "Multiplicadores", () -> setMultipliers()));
		
		if(StateControl.getCurrentUser().isAdmin())
		{
			GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Save"));
		}
		GraphicObjects.get("Pan_L_OptionsPanel").add(GraphicObjects.get("But_L_Multipliers"));
		
		new View_Loading("L", "Cargando costos");
		
		new Thread(() ->
		{
			PricesContainer.updatePrices();
			GraphicObjects.get("Pan_L_ContentPanel").remove(GraphicObjects.get("Pan_L_Loading"));
			((OptionButton)GraphicObjects.get("But_L_Save")).setEnabled(true);
			//((OptionButton)GraphicObjects.get("But_L_Multipliers")).setEnabled(true);
			((OptionButton)GraphicObjects.get("But_L_Multipliers")).setToolTipText("Aun no disponible");
			showCosts();
		}).start();
	}
	
	private void handleSave()
	{
		GraphicObjects.getKeySet().stream().filter(k -> k.substring(0, 6).equals("Tex_L_")).forEach(text -> 
		{
			matcher = pattern.matcher(((Tex_TextArea)GraphicObjects.get(text)).getText());
			if(matcher.matches())
			{
				PricesContainer.update(text.substring(6), Double.valueOf(((Tex_TextArea)GraphicObjects.get(text)).getText()));			
			}
			else
			{
				JOptionPane.showMessageDialog(null, "El valor de "+ ((JLabel)GraphicObjects.get("Lab_L_"+text.substring(6))).getText().toUpperCase() + " no fue actualizado\nNo tenia formato de numero");
			}
		});
		GraphicObjects.softDropL();
		GraphicObjects.add("Pan_L_Main", new Pan_workPanel(1, "L"));
		GraphicObjects.get("Pan_workSpace").add(GraphicObjects.get("Pan_L_Main"));
		new View_Loading("L", "Actualizando costos");
		
		new Thread(() -> 
		{
			new PricesAPI().update(PricesContainer.getAllPrices());
			GraphicObjects.dropL();
			new View_Editor();
		}).start();
	}
	
	private void setMultipliers()
	{
		
	}
	
	private void showCosts()
	{
		GraphicObjects.add("Lab_L_iva_percentage", new JLabel("multiplicador de iva")); //name label _3
		GraphicObjects.get("Lab_L_iva_percentage").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_iva_percentage").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_iva_percentage", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("iva_percentage"))));

		GraphicObjects.add("Lab_L_stripe_percentage", new JLabel("multiplicador de stripe")); //name label _3
		GraphicObjects.get("Lab_L_stripe_percentage").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_stripe_percentage").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_stripe_percentage", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("stripe_percentage"))));
		
		GraphicObjects.add("Lab_L_amortization_Constant_For_Meals", new JLabel("Amortizacion comidas")); //name label _3
		GraphicObjects.get("Lab_L_amortization_Constant_For_Meals").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_amortization_Constant_For_Meals").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_amortization_Constant_For_Meals", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("amortization_Constant_For_Meals"))));
		
		GraphicObjects.add("Lab_L_amortization_Constant_For_Break", new JLabel("Amortizacion desayunos")); //name label _3
		GraphicObjects.get("Lab_L_amortization_Constant_For_Break").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_amortization_Constant_For_Break").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_amortization_Constant_For_Break", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("amortization_Constant_For_Break"))));
		
		GraphicObjects.add("Lab_L_unit_Cost_Meal", new JLabel("Unitario comidas")); //name label _3
		GraphicObjects.get("Lab_L_unit_Cost_Meal").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_unit_Cost_Meal").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_unit_Cost_Meal", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("unit_Cost_Meal"))));

		GraphicObjects.add("Lab_L_unit_Cost_Break", new JLabel("Unitario desayunos")); //name label _3
		GraphicObjects.get("Lab_L_unit_Cost_Break").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_unit_Cost_Break").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_unit_Cost_Break", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("unit_Cost_Break"))));
		
		GraphicObjects.add("Lab_L_unit_Cost_Meal_PP", new JLabel("Unitario comidas Principal")); //name label _3
		GraphicObjects.get("Lab_L_unit_Cost_Meal_PP").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_unit_Cost_Meal_PP").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_unit_Cost_Meal_PP", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("unit_Cost_Meal_PP"))));
		
		GraphicObjects.add("Lab_L_unit_Cost_Meal_G1", new JLabel("Unitario comidas Guarnicion1")); //name label _3
		GraphicObjects.get("Lab_L_unit_Cost_Meal_G1").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_unit_Cost_Meal_G1").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_unit_Cost_Meal_G1", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("unit_Cost_Meal_G1"))));
		
		GraphicObjects.add("Lab_L_unit_Cost_Meal_G2", new JLabel("Unitario comidas Guarnicion2")); //name label _3
		GraphicObjects.get("Lab_L_unit_Cost_Meal_G2").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_unit_Cost_Meal_G2").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_unit_Cost_Meal_G2", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("unit_Cost_Meal_G2"))));
		
		GraphicObjects.add("Lab_L_unit_Cost_Break_PP", new JLabel("Unitario desayunos Principal")); //name label _3
		GraphicObjects.get("Lab_L_unit_Cost_Break_PP").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_unit_Cost_Break_PP").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_unit_Cost_Break_PP", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("unit_Cost_Break_PP"))));
		
		GraphicObjects.add("Lab_L_unit_Cost_Break_G1", new JLabel("Unitario desayunos Guarnicion1")); //name label _3
		GraphicObjects.get("Lab_L_unit_Cost_Break_G1").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_unit_Cost_Break_G1").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_unit_Cost_Break_G1", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("unit_Cost_Break_G1"))));
		
		GraphicObjects.add("Lab_L_unit_Cost_Break_G2", new JLabel("Unitario desayunos Guarnicion2")); //name label _3
		GraphicObjects.get("Lab_L_unit_Cost_Break_G2").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_unit_Cost_Break_G2").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_unit_Cost_Break_G2", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("unit_Cost_Break_G2"))));
		
		GraphicObjects.add("Lab_L_group_Constant_M_Vegetables", new JLabel("Constante de comidas - Vegetales")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_M_Vegetables").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_M_Vegetables").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_M_Vegetables", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_M_Vegetables"))));
		
		GraphicObjects.add("Lab_L_group_Constant_M_Fruit", new JLabel("Constante de comidas - Fruta")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_M_Fruit").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_M_Fruit").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_M_Fruit", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_M_Fruit"))));
		
		GraphicObjects.add("Lab_L_group_Constant_M_Cereal", new JLabel("Constante de comidas - Cereal")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_M_Cereal").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_M_Cereal").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_M_Cereal", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_M_Cereal"))));
		
		GraphicObjects.add("Lab_L_group_Constant_M_Legumes", new JLabel("Constante de comidas - Legumbres")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_M_Legumes").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_M_Legumes").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_M_Legumes", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_M_Legumes"))));
		
		GraphicObjects.add("Lab_L_group_Constant_M_Animal", new JLabel("Constante de comidas - Origen animal")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_M_Animal").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_M_Animal").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_M_Animal", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_M_Animal"))));
		
		GraphicObjects.add("Lab_L_group_Constant_M_Fat", new JLabel("Constante de comidas - Grasas")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_M_Fat").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_M_Fat").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_M_Fat", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_M_Fat"))));
		
		GraphicObjects.add("Lab_L_group_Constant_M_Other", new JLabel("Constante de comidas - Otro")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_M_Other").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_M_Other").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_M_Other", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_M_Other"))));
		
		GraphicObjects.add("Lab_L_group_Constant_B_Vegetables", new JLabel("Constante de desayunos - Vegetales")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_B_Vegetables").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_B_Vegetables").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_B_Vegetables", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_B_Vegetables"))));
		
		GraphicObjects.add("Lab_L_group_Constant_B_Fruit", new JLabel("Constante de desayunos - Fruta")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_B_Fruit").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_B_Fruit").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_B_Fruit", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_B_Fruit"))));
		
		GraphicObjects.add("Lab_L_group_Constant_B_Cereal", new JLabel("mConstante de desayunos - Cereal")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_B_Cereal").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_B_Cereal").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_B_Cereal", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_B_Cereal"))));
		
		GraphicObjects.add("Lab_L_group_Constant_B_Legumes", new JLabel("Constante de desayunos - Legumbres")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_B_Legumes").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_B_Legumes").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_B_Legumes", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_B_Legumes"))));
		
		GraphicObjects.add("Lab_L_group_Constant_B_Animal", new JLabel("Constante de desayunos - Origen animal")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_B_Animal").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_B_Animal").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_B_Animal", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_B_Animal"))));
		
		GraphicObjects.add("Lab_L_group_Constant_B_Fat", new JLabel("Constante de desayunos - Grasas")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_B_Fat").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_B_Fat").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_B_Fat", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_B_Fat"))));
		
		GraphicObjects.add("Lab_L_group_Constant_B_Other", new JLabel("Constante de desayunos - Otro")); //name label _3
		GraphicObjects.get("Lab_L_group_Constant_B_Other").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_group_Constant_B_Other").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_group_Constant_B_Other", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("group_Constant_B_Other"))));
		
		GraphicObjects.add("Lab_L_discount_two_weeks", new JLabel("Descuento dos semanas")); //name label _3
		GraphicObjects.get("Lab_L_discount_two_weeks").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_discount_two_weeks").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_discount_two_weeks", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("discount_two_weeks"))));
		
		GraphicObjects.add("Lab_L_discount_three_weeks", new JLabel("Descuento tres semanas")); //name label _3
		GraphicObjects.get("Lab_L_discount_three_weeks").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_discount_three_weeks").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_discount_three_weeks", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("discount_three_weeks"))));
		
		GraphicObjects.add("Lab_L_discount_four_weeks", new JLabel("Descuento cuatro semanas")); //name label _3
		GraphicObjects.get("Lab_L_discount_four_weeks").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_discount_four_weeks").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_discount_four_weeks", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("discount_four_weeks"))));
		
		GraphicObjects.add("Lab_L_sales_goal", new JLabel("Meta de ventas")); //name label _3
		GraphicObjects.get("Lab_L_sales_goal").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_sales_goal").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_sales_goal", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("sales_goal"))));
		
		GraphicObjects.add("Lab_L_maximum_discount", new JLabel("descuento maximo")); //name label _3
		GraphicObjects.get("Lab_L_maximum_discount").setPreferredSize(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4)*3,25));
		GraphicObjects.get("Lab_L_maximum_discount").setForeground(Color.WHITE);
		GraphicObjects.add("Tex_L_maximum_discount", new Tex_TextArea(new Dimension(((GraphicConstants.getObjectsWithScrollSizeXY()[0]-(GraphicConstants.getMargins()*2))/4),25), String.valueOf(PricesContainer.get("maximum_discount"))));
		
		((Tex_TextArea)GraphicObjects.get("Tex_L_iva_percentage")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_stripe_percentage")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_amortization_Constant_For_Meals")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_amortization_Constant_For_Break")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_unit_Cost_Meal")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_unit_Cost_Break")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_unit_Cost_Meal_PP")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_unit_Cost_Meal_G1")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_unit_Cost_Meal_G2")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_unit_Cost_Break_PP")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_unit_Cost_Break_G1")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_unit_Cost_Break_G2")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_M_Vegetables")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_M_Fruit")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_M_Cereal")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_M_Legumes")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_M_Animal")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_M_Fat")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_M_Other")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_B_Vegetables")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_B_Fruit")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_B_Cereal")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_B_Legumes")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_B_Animal")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_B_Fat")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_group_Constant_B_Other")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_discount_two_weeks")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_discount_three_weeks")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_discount_four_weeks")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_sales_goal")).setEditable(StateControl.getCurrentUser().isAdmin());
		((Tex_TextArea)GraphicObjects.get("Tex_L_maximum_discount")).setEditable(StateControl.getCurrentUser().isAdmin());
			
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_iva_percentage"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_iva_percentage"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_stripe_percentage"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_stripe_percentage"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_amortization_Constant_For_Meals"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_amortization_Constant_For_Meals"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_amortization_Constant_For_Break"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_amortization_Constant_For_Break"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_unit_Cost_Meal"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_unit_Cost_Meal"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_unit_Cost_Break"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_unit_Cost_Break"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_unit_Cost_Meal_PP"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_unit_Cost_Meal_PP"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_unit_Cost_Meal_G1"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_unit_Cost_Meal_G1"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_unit_Cost_Meal_G2"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_unit_Cost_Meal_G2"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_unit_Cost_Break_PP"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_unit_Cost_Break_PP"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_unit_Cost_Break_G1"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_unit_Cost_Break_G1"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_unit_Cost_Break_G2"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_unit_Cost_Break_G2"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_M_Vegetables"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_M_Vegetables"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_M_Fruit"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_M_Fruit"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_M_Cereal"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_M_Cereal"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_M_Legumes"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_M_Legumes"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_M_Animal"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_M_Animal"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_M_Fat"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_M_Fat"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_M_Other"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_M_Other"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_B_Vegetables"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_B_Vegetables"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_B_Fruit"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_B_Fruit"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_B_Cereal"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_B_Cereal"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_B_Legumes"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_B_Legumes"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_B_Animal"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_B_Animal"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_B_Fat"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_B_Fat"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_group_Constant_B_Other"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_group_Constant_B_Other"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_discount_two_weeks"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_discount_two_weeks"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_discount_three_weeks"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_discount_three_weeks"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_discount_four_weeks"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_discount_four_weeks"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_sales_goal"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_sales_goal"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Lab_L_maximum_discount"));
		GraphicObjects.get("Pan_L_ContentPanel").add(GraphicObjects.get("Tex_L_maximum_discount"));
		
		int componentsHeight = (31*(25+15))+15 ;
		
		GraphicObjects.get("Pan_L_ContentPanel").setPreferredSize(new Dimension(GraphicConstants.getObjectsSizeXY()[0], componentsHeight));
		GraphicObjects.get("Pan_workSpace").revalidate();
		GraphicObjects.get("Pan_workSpace").repaint();
	}
}
