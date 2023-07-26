package com.hungerless.HungerlessCRM.sales;

import java.util.ArrayList;
import java.util.Date;

import com.hungerless.HungerlessCRM.calculator.ProductsContainer;

public class Sale
{
	private boolean quotation;
	private boolean sale;
	private String saleID;
	private String specs;
	private Date dateOfCreation;
	private double total_cost;
	private int tokens;
	private double forSprint;
	private int weeks;
	private String client_id;
	private String client_name;
	private ArrayList<String> products;
	private boolean legacy;
	private boolean consecutive_order; //for including in sales report, cost 0, for last sale, look into sale that's not consecutive order
	private String parent_id;
	
	public Sale(
			String saleID, 
			boolean quotation,
			boolean sale, 
			String specs, 
			Date dateOfCreation, 
			double total_cost,
			double tokens,
			double forSprint, 
			double weeks,
			String client_id,
			String client_name,
			ArrayList<String> products,
			boolean legacy,
			boolean consecutive_order,
			String parent_id
			)
	{
		this.quotation = quotation;
		this.sale = sale;
		this.saleID = saleID;
		this.specs = specs;
		this.dateOfCreation = dateOfCreation;
		this.total_cost = total_cost;
		this.tokens = (int)tokens;
		this.forSprint = forSprint;
		this.weeks = (int)weeks;
		this.client_id = client_id;
		this.client_name = client_name;
		this.products = products;
		this.legacy = legacy;
		this.consecutive_order = consecutive_order;
		this.parent_id = parent_id;
	}
	
	public boolean isConsecutive_order()
	{
		return consecutive_order;
	}

	public void setConsecutive_order(boolean consecutive_order)
	{
		this.consecutive_order = consecutive_order;
	}

	public String getParent_id()
	{
		return parent_id;
	}

	public void setParent_id(String parent_id)
	{
		this.parent_id = parent_id;
	}

	public boolean isConsecutiveOrder()
	{
		return consecutive_order;
	}

	public void setConsecutiveOrder(boolean consecutive_order)
	{
		this.consecutive_order = consecutive_order;
	}

	public boolean isLegacy()
	{
		return legacy;
	}

	public void setLegacy(boolean legacy)
	{
		this.legacy = legacy;
	}

	public ArrayList<String> getProducts()
	{
		return products;
	}

	public void setProducts(ArrayList<String> products)
	{
		this.products = products;
	}

	public String getClient_id()
	{
		return client_id;
	}

	public void setClient_id(String client_id)
	{
		this.client_id = client_id;
	}

	public String getClient_name()
	{
		return client_name;
	}

	public void setClient_name(String client_name)
	{
		this.client_name = client_name;
	}

	public void setTokens(int tokens)
	{
		this.tokens = tokens;
	}

	public void setWeeks(int d)
	{
		this.weeks = d;
	}

	public boolean isQuotation()
	{
		return quotation;
	}
	
	public void setQuotation(boolean quotation)
	{
		this.quotation = quotation;
	}
	
	public boolean isSale()
	{
		return sale;
	}
	
	public void setSale(boolean sale)
	{
		this.sale = sale;
	}
	
	public String getSaleID()
	{
		return saleID;
	}
	
	public void setSaleID(String saleID)
	{
		this.saleID = saleID;
	}
	
	public String getSpecs()
	{
		return specs;
	}
	
	public void setSpecs(String specs)
	{
		this.specs = specs;
	}
	
	public Date getDateOfCreation()
	{
		return dateOfCreation;
	}
	
	public void setDateOfCreation(Date dateOfCreation)
	{
		this.dateOfCreation = dateOfCreation;
	}
	
	public double getTotal_cost()
	{
		return total_cost;
	}
	
	public void setTotal_cost(double total_cost)
	{
		this.total_cost = total_cost;
	}
	
	public int getTokens()
	{
		return tokens;
	}
	
	public double getForSprint()
	{
		return forSprint;
	}

	public void setForSprint(int forSprint)
	{
		this.forSprint = forSprint;
	}
	
	public int getWeeks()
	{
		return weeks;
	}
	
	public void updateTotalCost()
	{
		this.total_cost = 0;
		this.tokens = 0;
		ProductsContainer.getKeySet().forEach(k -> 
		{
			this.total_cost += ProductsContainer.get(k).getTotal();
			this.tokens += ProductsContainer.get(k).getTokens();
		});
		this.total_cost = truncateNumber(this.total_cost);
	}
	
	private static double truncateNumber(double n)   
	{   
		//moves the decimal to the right   
		n /= .01;  
		//determines the floor value  
		n = Math.floor(n);   
		//dividing the floor value by 10 to the power decimalplace  
		n /= 100;
		//prints the number after truncation  
		return n; 
	} 
}
