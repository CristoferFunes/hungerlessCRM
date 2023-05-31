package com.hungerless.HungerlessCRM.clients;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.swing.JFileChooser;

import com.hungerless.HungerlessCRM.StateControl;

public abstract class ClientsExporter 
{
	private static StringBuilder data;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy"); 
	
	public static void exportClientsCSV()
	{
		data = new StringBuilder();
		JFileChooser fileChooser = new JFileChooser();
		String fileDirectory = new String();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle("Selecciona donde deseas guardar los contactos");
		if(fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
		fileDirectory = fileChooser.getSelectedFile().getAbsolutePath();
		File destination = new File(fileDirectory + "\\Clients.csv");
		PrintWriter fileOut = null;
		
		data.append("First name, Last name, Phone number, Address, Seniority, Membership notes\n");
		
		StateControl.getSelectedClients().forEach(c ->
		{
			data.append(convertSpecialCharacters(ClientsContainer.get(c).getFirst_name())+",");
			data.append(convertSpecialCharacters(ClientsContainer.get(c).getLast_name())+",");
			data.append(convertSpecialCharacters(ClientsContainer.get(c).getPhoneNumber())+",");
			data.append(convertSpecialCharacters(ClientsContainer.get(c).getAddress())+",");
			data.append(convertSpecialCharacters(String.valueOf(ClientsContainer.get(c).getSeniority()))+",");
			data.append(convertSpecialCharacters(ClientsContainer.get(c).getComments())+"\n");
		});
		
		
		try
		{
			fileOut = new PrintWriter(destination);
			fileOut.println(data);
		}
		catch(FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		finally
		{
			fileOut.close();
		}
	}
	
	private static String convertSpecialCharacters(String toConvert)
	{
		String converted = toConvert.replaceAll("\\R", "");
		if(toConvert.contains(",") || toConvert.contains("\"") || toConvert.contains("'"))
		{
			toConvert = toConvert.replace("\"", "\"\"");
			converted = "\""+toConvert+"\"";
		}
		return converted;
	}
}
