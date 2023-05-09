package com.hungerless.HungerlessCRM.GUI.graphicConstructors;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import com.hungerless.HungerlessCRM.mouseClickAction;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;

public class Pan_listItem extends JPanel
{
	private mouseClickAction action;
	public String associatedObjectID = null;
	public Color mainColor;
	public Color currentColor;
	
	public Pan_listItem(boolean complete, String id, mouseClickAction a)
	{
		this.action = a;
		this.associatedObjectID = id;
		this.mainColor = complete ? GraphicConstants.getCompletedListItemColor() : GraphicConstants.getNotCompletedListItemColor();
		this.setPreferredSize(GraphicConstants.getObjectsWithScrollSize());
		this.setBackground(mainColor);
		this.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
		this.currentColor = mainColor;
		this.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				Pan_listItem.this.action.clickedAction();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) 
			{
				Pan_listItem.this.setBackground(GraphicConstants.getHighlightedListItemColor());
			}

			@Override
			public void mouseExited(MouseEvent e) 
			{
				Pan_listItem.this.setBackground(currentColor);
			}
	
		});
	}
	
	private static final long serialVersionUID = -2070462477408946043L;
}
