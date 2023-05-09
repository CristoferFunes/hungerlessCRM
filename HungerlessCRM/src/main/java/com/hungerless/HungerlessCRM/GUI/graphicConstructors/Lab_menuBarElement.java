package com.hungerless.HungerlessCRM.GUI.graphicConstructors;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.Pan_workSpace;



public class Lab_menuBarElement extends JLabel implements MouseListener
{
	private static boolean activeSelection = false;
	private static List<Lab_menuBarElement> menuElements = new ArrayList<>();
	private static int currentSelection;
	private Color currentColor;
	private int selection;
	
	public Lab_menuBarElement(String title, int selection)
	{
		menuElements.add(this);
		this.selection = selection;
		this.currentColor = GraphicConstants.getMenuElementColor();
		this.setText(title);
		this.setFont(new Font(null,Font.PLAIN,16));
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setBackground(GraphicConstants.getMenuElementColor());
		this.setPreferredSize(GraphicConstants.getMenuElementSize());
		this.addMouseListener(this);
		this.setOpaque(true);
	}
	
	private static final long serialVersionUID = -5511912537124656699L;

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if(activeSelection)
		{
			menuElements.forEach(element -> 
			{
				element.currentColor = GraphicConstants.getMenuElementColor();
				element.setBackground(element.currentColor);
			});
		}
		this.setBackground(GraphicConstants.getMenuElementHighlightedColor());
		this.currentColor = GraphicConstants.getMenuElementHighlightedColor();
		activeSelection = true;
		menuSelection();
	}
	
	public void menuSelection()
	{
		if(currentSelection != selection)
		{
			currentSelection = selection;
			GraphicObjects.dropL();
			switch(selection)
			{
				//case 1 -> ((Pan_workSpace)GraphicObjects.get("Pan_workSpace")).selectAccount();
				//case 2 -> ((Pan_workSpace)GraphicObjects.get("Pan_workSpace")).selectCalculator();
				case 3 -> ((Pan_workSpace)GraphicObjects.get("Pan_workSpace")).selectClients();
				//case 4 -> ((Pan_workSpace)GraphicObjects.get("Pan_workSpace")).selectSales();
				//case 5 -> ((Pan_workSpace)GraphicObjects.get("Pan_workSpace")).selectEditor();
			}
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		this.setBackground(GraphicConstants.getMenuElementHighlightedColor());
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		this.setBackground(this.currentColor);
	}
}
