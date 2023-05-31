package com.hungerless.HungerlessCRM.GUIlogin;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.hungerless.HungerlessCRM.GUI.Pan_main;

public class Fra_login extends JFrame
{
	public Fra_login()
	{
		try
		{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
		catch (Exception e) 
		{
            e.printStackTrace();
        }
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Login");
		this.setIconImage(new ImageIcon("./resources/logoHL.png").getImage());
		this.add(new Pan_login());
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private static final long serialVersionUID = -5913905469256410923L;
}
