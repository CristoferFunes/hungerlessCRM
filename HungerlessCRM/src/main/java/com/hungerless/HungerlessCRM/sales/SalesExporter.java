package com.hungerless.HungerlessCRM.sales;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.hungerless.HungerlessCRM.GUI.GraphicObjects;
import com.hungerless.HungerlessCRM.GUI.sales.View_Sales;
import com.hungerless.HungerlessCRM.clients.Client;
import com.hungerless.HungerlessCRM.clients.ClientsAPI;
import com.hungerless.HungerlessCRM.clients.ClientsContainer;

public class SalesExporter
{
	private StringBuilder data;
	private StringBuilder specsFromProduct;
	private int mealCount = 0;
	private int breakCount = 0;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("ww"); 
	private int sprint =  (int)Integer.valueOf(dateFormat.format(new Date()));
	
	public void salesReportCSV()
	{
		sprint = sprint+1 > 51 ? 2 : sprint+1;
		data = new StringBuilder();
		JFileChooser fileChooser = new JFileChooser();
		String fileDirectory = new String();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle("Selecciona donde deseas guardar el reporte");
		if(fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
		fileDirectory = fileChooser.getSelectedFile().getAbsolutePath();
		File destination = new File(fileDirectory + "\\Ordenes.csv");
		PrintWriter fileOut = null;
		
		new ClientsAPI().get().stream().map(o -> (Client)o).forEach(c -> ClientsContainer.add(c));
		
		data.append("Nombre, Telefono, Direccion, Comidas, Desayunos, Especificaciones, Comentarios, Total\n");
		
		SalesContainer.getKeySet().stream()
								  .map(k -> SalesContainer.get(k))
								  .filter(s -> s.getForSprint() % 100 == sprint)
								  .forEach(s -> 
		{
			mealCount = 0;
			breakCount = 0;
			s.getProducts().forEach(p -> 
			{
				if(Integer.valueOf(p.substring(0, 1)) == 1)
				{
					mealCount += Integer.valueOf(p.substring(19, 21));
				}
				if(Integer.valueOf(p.substring(0, 1)) == 2)
				{
					breakCount += Integer.valueOf(p.substring(19, 21));
				}
			});
			data.append(convertSpecialCharacters(s.getClient_name()) + ",");
			data.append(convertSpecialCharacters(ClientsContainer.get(s.getClient_id()).getPhoneNumber()) + ",");
			data.append(convertSpecialCharacters(ClientsContainer.get(s.getClient_id()).getAddress()) + ",");
			data.append(convertSpecialCharacters(String.valueOf(mealCount)) + ",");
			data.append(convertSpecialCharacters(String.valueOf(breakCount)) + ",");
			data.append(convertSpecialCharacters(getSpecsFromProducts(s)+s.getSpecs()) + ",");
			data.append(convertSpecialCharacters(ClientsContainer.get(s.getClient_id()).getComments())+",");
			data.append(convertSpecialCharacters(String.valueOf(s.getTotal_cost()))+"\n");
		});
		
		try
		{
			fileOut = new PrintWriter(destination);
			fileOut.println(data);
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Verifica que el archivo este cerrado\nNo se pudo exportar el archivo, intente de nuevo");
			GraphicObjects.dropL();
			new View_Sales();
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
	
	private String getSpecsFromProducts(Sale s)
	{
		this.specsFromProduct = new StringBuilder();
		
		s.getProducts().forEach(p -> 
		{
			if(Integer.valueOf(p.substring(0, 1)) == 3)
			{
				return;
			}
			if(Integer.valueOf(p.substring(17, 19)) != 5)
			{
				this.specsFromProduct.append(Integer.valueOf(p.substring(0, 1)) == 1 ? "Un paquete de comidas es de " + p.substring(17, 19) + " comidas, ": "Un paquete de desayunos es de " + p.substring(17, 19) + " desayunos, ");
			}
			
		});
		return String.valueOf(this.specsFromProduct);
	}
}
