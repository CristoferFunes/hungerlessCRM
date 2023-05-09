package com.hungerless.HungerlessCRM.GUI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;


public class Fra_main extends JFrame
{
	public Fra_main()
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
		this.setTitle("Hungerless CRM");
		this.setIconImage(new ImageIcon("./src/resources/logoHL.png").getImage());
		this.add(new Pan_main());
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	private static final long serialVersionUID = 6020078343071118074L;
}
