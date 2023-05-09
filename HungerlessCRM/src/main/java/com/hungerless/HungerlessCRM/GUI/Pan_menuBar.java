package com.hungerless.HungerlessCRM.GUI;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.hungerless.HungerlessCRM.GUI.graphicConstructors.Lab_menuBarElement;


public class Pan_menuBar extends JPanel
{
	Pan_menuBar()
	{
		this.setPreferredSize(GraphicConstants.getMenuBarSize());
		this.setBackground(GraphicConstants.getMenuBarColor());
		this.setLayout(new FlowLayout(FlowLayout.LEADING, GraphicConstants.getMargins(), GraphicConstants.getMargins()));	
		this.add(new Lab_menuBarElement("Cuenta", 1));
		this.add(new Lab_menuBarElement("Cotizador", 2));
		this.add(new Lab_menuBarElement("Clientes/Prospectos", 3));
		this.add(new Lab_menuBarElement("Ventas", 4));
		this.add(new Lab_menuBarElement("Editor de costos", 5));
	}
	private static final long serialVersionUID = -5986259541201213394L;
}