package com.hungerless.HungerlessCRM.login;

import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.hungerless.HungerlessCRM.API;
import com.hungerless.HungerlessCRM.Pair;

public class AuthAPI extends API
{
	String user;
	String password;
	
	public AuthAPI(String user, String password)
	{
		this.user = user;
		this.password = password;
	}
	
	@Override
	public String from()
	{
		return "Users";
	}

	@Override
	public int getTypeOfCondition()
	{
		return API.EQUALSTO;
	}

	@Override
	public Pair<String, Object> getCondition()
	{
		return new Pair<String, Object>("user", this.user);
	}

	@Override
	public Pair<String, Object> getOrder()
	{
		return null;
	}

	@Override
	public int getLimit()
	{
		return 1;
	}

	@Override
	public Function<QueryDocumentSnapshot, Object> mapper()
	{
		return q -> 
		{
			if (this.password.equals(q.getString("password")))
			{
				return new User(q.getId(), true, q.getBoolean("admin") == null ? false : q.getBoolean("admin"), q.getString("name") == null ? "Nuevo usuario" : q.getString("name"));
			}
			return new User("nonAuth", false, q.getBoolean("admin") == null ? false : q.getBoolean("admin"), "noName");
		};
	}

	@Override
	public <T> HashMap<String, Object> putMapper(T t)
	{
		return null;
	}

	@Override
	public <T> String idRef(T t)
	{
		return null;
	}

	@Override
	public <V> Pair<String, V> getSecondCondition()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
