package com.hungerless.HungerlessCRM.GUI.graphicConstructors;

import java.awt.Dimension;

import javax.swing.JButton;

import com.hungerless.HungerlessCRM.mouseClickAction;
import com.hungerless.HungerlessCRM.GUI.GraphicConstants;

public class OptionButton extends JButton
{
	public OptionButton(String text, mouseClickAction a)
	{
		this.setText(text);
		this.setPreferredSize(GraphicConstants.getButtonSize());
		this.addActionListener(onClick -> a.clickedAction());
	}
	private static final long serialVersionUID = 5096455090120482179L;
}
