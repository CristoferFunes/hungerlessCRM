package com.hungerless.HungerlessCRM.login;

public class User
{
	private String id;
	private String name;
	private boolean auth;
	private boolean admin;
	
	public User(String id, boolean auth, boolean admin, String name)
	{
		this.id = id;
		this.auth = auth;
		this.admin = admin;
		this.name = name;
	}
	
	public boolean isAdmin()
	{
		return admin;
	}
	
	public String getId()
	{
		return id;
	}
	
	public boolean isAuth()
	{
		return auth;
	}
	
	public String getName()
	{
		return this.name;
	}
}
