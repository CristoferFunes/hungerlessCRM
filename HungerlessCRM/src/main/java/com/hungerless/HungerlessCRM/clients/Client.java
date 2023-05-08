package com.hungerless.HungerlessCRM.clients;

public class Client
{
	

	private String clientID;
	private String first_name;
	private String last_name;
	private String address;
	private String phoneNumber;
	private String comments;
	private String dateOfCreation;
	private String lastSaleDate;
	private boolean prospect;
	private boolean customer;
	
	public Client(String clientID,
				  String first_name, 
				  String last_name, 
				  String address, 
				  String phoneNumber, 
				  String comments, 
				  String dateOfCreation, 
				  String lastSaleDate, 
				  Boolean prospect, 
				  Boolean customer)
	{
		this.clientID = clientID;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.comments = comments;
		this.dateOfCreation = dateOfCreation;
		this.lastSaleDate = lastSaleDate;
		this.prospect = prospect;
		this.customer = customer;
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

	public String getDateOfCreation()
	{
		return dateOfCreation;
	}

	public void setDateOfCreation(String dateOfCreation)
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

	public String getLastSaleDate()
	{
		return lastSaleDate;
	}
}
