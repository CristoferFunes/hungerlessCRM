package com.hungerless.HungerlessCRM;

public class Pair<K, V>
{
	private final K k;
	private final V v;

	public Pair(K k, V v)
	{
		this.k = k;
		this.v = v;
	}
	
	public K getK()
	{
		return this.k;
	}
	
	public V getV()
	{
		return this.v;
	}
}