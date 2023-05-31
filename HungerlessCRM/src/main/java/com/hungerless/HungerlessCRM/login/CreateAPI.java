package com.hungerless.HungerlessCRM.login;

import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.hungerless.HungerlessCRM.API;
import com.hungerless.HungerlessCRM.Pair;

public class CreateAPI extends API
{

	String user;
	String password;
	
	public CreateAPI(String user, String password)
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
		return q -> new User(q.getId(), false, false, "noName");
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> putMapper(Object o)
	{
		HashMap<String, Object> post = new HashMap<>();
		post.put("user", ((Pair<String, String>) o).getK());
		post.put("password", ((Pair<String, String>) o).getV());
		if(!((User)o).getName().equals("Nuevo usuario")) post.put("name", ((User)o).getName());
		return post;
	}

	@Override
	public <T> String idRef(T t)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <V> Pair<String, V> getSecondCondition()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
