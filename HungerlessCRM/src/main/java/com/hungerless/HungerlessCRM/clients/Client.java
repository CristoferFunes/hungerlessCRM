package com.hungerless.HungerlessCRM.clients;

import java.util.Date;

public class Client
{
	

	private String clientID;
	private String first_name;
	private String last_name;
	private String address;
	private String phoneNumber;
	private String comments;
	private String lastSaleSpecs;
	private Date dateOfCreation;
	private Date lastSaleDate;
	private boolean prospect;
	private boolean customer;
	private double seniority;
	private double average_ticket;
	private String lastSaleID;
	
	public Client(String clientID,
				  String first_name, 
				  String last_name, 
				  String address, 
				  String phoneNumber, 
				  String comments,
				  String lastSaleSpecs,
				  Date dateOfCreation, 
				  Date lastSaleDate, 
				  Boolean prospect, 
				  Boolean customer,
				  double seniority,
				  double average_ticket,
				  String last_sale_id)
	{
		this.clientID = clientID;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.comments = comments;
		this.lastSaleSpecs = lastSaleSpecs;
		this.dateOfCreation = dateOfCreation;
		this.lastSaleDate = lastSaleDate;
		this.prospect = prospect;
		this.customer = customer;
		this.seniority = seniority;
		this.average_ticket = average_ticket;
		this.lastSaleID = last_sale_id;
	}
	
	public String getLastSaleID()
	{
		return lastSaleID;
	}

	public void setLastSaleID(String lastSaleID)
	{
		this.lastSaleID = lastSaleID;
	}

	public double getAverage_ticket()
	{
		return average_ticket;
	}

	public void setAverage_ticket(double average_ticket)
	{
		this.average_ticket = average_ticket;
	}

	public void setClientID(String clientID)
	{
		this.clientID = clientID;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public void setLastSaleDate(Date lastSaleDate)
	{
		this.lastSaleDate = lastSaleDate;
	}

	public String getLastSaleSpecs()
	{
		return lastSaleSpecs;
	}

	public void setLastSaleSpecs(String lastSaleSpecs)
	{
		this.lastSaleSpecs = lastSaleSpecs;
	}

	public double getSeniority()
	{
		return seniority;
	}

	public void setSeniority(double seniority)
	{
		this.seniority = seniority;
	}

	public String getFirst_name()
	{
		return first_name;
	}

	public void setFirst_name(String first_name)
	{
		this.first_name = first_name;
	}

	public String getLast_name()
	{
		return last_name;
	}

	public void setLast_name(String last_name)
	{
		this.last_name = last_name;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public Date getDateOfCreation()
	{
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation)
	{
		this.dateOfCreation = dateOfCreation;
	}

	public boolean isProspect()
	{
		return prospect;
	}

	public void setProspect(boolean prospect)
	{
		this.prospect = prospect;
	}

	public boolean isCustomer()
	{
		return customer;
	}

	public void setCustomer(boolean customer)
	{
		this.customer = customer;
	}

	public String getClientID()
	{
		return clientID;
	}

	public String getAddress()
	{
		return address;
	}

	public Date getLastSaleDate()
	{
		return lastSaleDate;
	}
}
