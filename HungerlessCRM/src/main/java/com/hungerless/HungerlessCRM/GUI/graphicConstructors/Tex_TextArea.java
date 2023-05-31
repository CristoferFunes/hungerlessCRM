package com.hungerless.HungerlessCRM.GUI.graphicConstructors;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.hungerless.HungerlessCRM.GUI.GraphicConstants;

public class Tex_TextArea extends JScrollPane
{
	JTextArea textArea;
	
	public Tex_TextArea(Dimension d, String text)
	{
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(GraphicConstants.getTextAreaColor());
		textArea.setText(text);
		//textArea.setFont(new Font(null, Font.PLAIN,20));
		
		this.setViewportView(textArea);
		this.setPreferredSize(d);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	}
	
	public void setEditable(boolean b)
	{
		this.textArea.setEditable(b);
	}
	
	public String getText()
	{
		return textArea.getText();
	}
	private static final long serialVersionUID = -766907960327539792L;
}
