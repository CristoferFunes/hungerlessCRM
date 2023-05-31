package com.hungerless.HungerlessCRM.GUI.graphicConstructors;

import java.awt.Dimension;

import javax.swing.JButton;

import com.hungerless.HungerlessCRM.GUI.GraphicConstants;
import com.hungerless.HungerlessCRM.interfaces.mouseClickAction;

public class OptionButton extends JButton
{
	public OptionButton(Boolean enabled, String text, mouseClickAction a)
	{
		this.setText(text);
		this.setPreferredSize(GraphicConstants.getButtonSize());
		this.addActionListener(onClick -> a.clickedAction());
		this.setEnabled(enabled);
	}
	
	public void setNewSize(Dimension d)
	{
		this.setPreferredSize(d);
	}
	private static final long serialVersionUID = 5096455090120482179L;
}
