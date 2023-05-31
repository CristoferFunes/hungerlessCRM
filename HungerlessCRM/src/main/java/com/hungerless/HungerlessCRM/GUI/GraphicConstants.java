package com.hungerless.HungerlessCRM.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public abstract class GraphicConstants
{
	private static int menuElementsAmount = 5;
	
	//Dimension
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	//private static Dimension screenSize = new Dimension(1366, 768);
	private static int objectMargin = 15;
	private static int width = screenSize.getWidth() < 1920 ? (int)Math.floor((screenSize.getWidth())*.9) : (int)Math.floor((screenSize.getWidth())*.75);
	private static int height = (int)Math.floor((screenSize.getHeight())/2);
	private static int loginW = (int)Math.floor((screenSize.getWidth())*.2);;
	private static int loginH = (int)Math.floor((screenSize.getHeight())*.5);;
	private static int menuBarWidth = (int)Math.floor(width/4);
	private static int workSpaceWidth = (int)Math.floor((width/4)*3);
	private static int menuElementWidth = (menuBarWidth)-(objectMargin*2); //for x
	private static int menuElementHeight = (height - ((menuElementsAmount+1)*objectMargin))/menuElementsAmount; //for y
	private static int workPanelWidth = (workSpaceWidth - (3*objectMargin))/2;
	private static int workPanelHeight = height - (objectMargin*2);
	private static int objectsHeight = 64;
	private static int objectsWidth = workPanelWidth-(objectMargin*2);
	private static int scrollAreaHeight = workPanelHeight - objectsHeight;
	private static int buttonHeight = 34;
	private static int buttonWidth = (workPanelWidth-(5*objectMargin))/4;
	private static int smallButtonWidth = buttonWidth/2;
	private static int largeButtonWidth = buttonWidth*2;;
	private static int textAreaWidth = ((objectsWidth-22)/5)*4;
	private static int smallTextAreaHeight = 25;
	private static int mediumSmallTextAreaHeight = 40;
	private static int mediumTextAreaHeight = 90;
	private static int largeTextAreaHeight = 200;
	private static int textAreaLabelWidth = ((objectsWidth-22)/5);
	private static int textAreaLabelHeight = 10;
	
	//Color
	private static Color menuBarColor = new Color(0x111b21);
	private static Color workSpaceColor = new Color(0x5E6B73);
	private static Color menuBarElementColor = new Color(0x495359);
	private static Color menuBarElementHighightedColor = new Color(0x5E6B73);
	private static Color workPanelColor = new Color(0x111b21);
	private static Color optionsColor = new Color(0x070A0D);
	private static Color completedListItemColor = new Color(0x5E6B73);
	private static Color notCompletedListItemColor = new Color(0x4F5166);
	private static Color highlightedListItemColor = new Color(0x363E42);
	private static Color checklistColor = new Color(0x192D33);
	private static Color textAreaBackgroundColor = new Color(0x8698A3);
	private static Color unfinishedColor = new Color(0x69111B);
	
	public static Dimension getMainSize()
	{
		return new Dimension(width, height);
	}
	
	public static Dimension getLoginSize()
	{
		return new Dimension(loginW, loginH);
	}
	
	public static int[] getButtonSizeXY()
	{
		return new int[] {buttonWidth,buttonHeight};
	}
	
	public static int[] getLoginSizeXY()
	{
		return new int[] {loginW, loginH};
	}
	
	public static Dimension getMenuBarSize()
	{
		return new Dimension(menuBarWidth, height);
	}
	
	public static int getMargins()
	{
		return objectMargin;
	}
	
	public static Dimension getWorkSpacePanelSize()
	{
		return new Dimension(workPanelWidth, workPanelHeight);
	}
	
	public static Dimension getObjectsSize()
	{
		return new Dimension(objectsWidth, objectsHeight);
	}
	
	public static Dimension getLargeScrollAreaSize()
	{
		return new Dimension(workPanelWidth, scrollAreaHeight);
	}
	
	public static Dimension getOptionPanelSize()
	{
		return new Dimension(workPanelWidth, objectsHeight);
	}
	
	public static Dimension getSmallScrollAreaSize()
	{
		return new Dimension(workPanelWidth, scrollAreaHeight - objectsHeight);
	}
	
	public static Dimension getWorkSpaceSize()
	{
		return new Dimension(workSpaceWidth, height);
	}
	
	public static Dimension getLargeWorkPanelSize()
	{
		return new Dimension((workPanelWidth*2) + objectMargin, workPanelHeight);
	}
	
	public static Dimension getMenuElementSize()
	{
		return new Dimension(menuElementWidth, menuElementHeight);
	}
	
	public static Dimension getButtonSize()
	{
		return new Dimension(buttonWidth, buttonHeight);
	}
	
	public static Dimension getSmallButtonSize()
	{
		return new Dimension(smallButtonWidth, buttonHeight);
	}
	
	public static Dimension getLargeButtonSize()
	{
		return new Dimension(largeButtonWidth, buttonHeight);
	}
	
	public static Dimension getObjectsWithScrollSize()
	{
		return new Dimension(objectsWidth - 22, objectsHeight);
	}
	
	public static int[] getObjectsSizeXY()
	{
		return new int[] {objectsWidth, objectsHeight};
	}
	
	public static int[] getObjectsWithScrollSizeXY()
	{
		return new int[] {objectsWidth - 22, objectsHeight};
	}
	
	public static Dimension getTextAreaLabelSize()
	{
		return new Dimension(textAreaLabelWidth, textAreaLabelHeight);
	}
	
	public static Dimension getLargeTextSize()
	{
		return new Dimension(textAreaWidth, largeTextAreaHeight);
	}
	
	public static Dimension getMediumTextSize()
	{
		return new Dimension(textAreaWidth, mediumTextAreaHeight);
	}
	
	public static Dimension getMediumSmallTextSize()
	{
		return new Dimension(textAreaWidth, mediumSmallTextAreaHeight);
	}
	
	public static Dimension getSmallTextSize()
	{
		return new Dimension(textAreaWidth, smallTextAreaHeight);
	}
	
	//Color
	
	public static Color getMenuBarColor()
	{
		return menuBarColor;
	}
	
	public static Color getWorkPanelColor()
	{
		return workPanelColor;
	}
	
	public static Color getOptionPanelColor()
	{
		return optionsColor;
	}
	
	public static Color getWorkSpaceColor()
	{
		return workSpaceColor;
	}
	
	public static Color getMenuElementColor()
	{
		return menuBarElementColor;
	}
	
	public static Color getMenuElementHighlightedColor()
	{
		return menuBarElementHighightedColor;
	}
	
	public static Color getCompletedListItemColor()
	{
		return completedListItemColor;
	}
	
	public static Color getNotCompletedListItemColor()
	{
		return notCompletedListItemColor;
	}
	
	public static Color getHighlightedListItemColor()
	{
		return highlightedListItemColor;
	}
	
	public static Color getChecklistColor()
	{
		return checklistColor;
	}
	
	public static Color getTextAreaColor()
	{
		return textAreaBackgroundColor;
	}
	
	public static Color getUnfinishedColor()
	{
		return unfinishedColor;
	}

}
