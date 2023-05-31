package com.hungerless.HungerlessCRM.calculator;

import java.util.HashMap;
import java.util.function.Function;

import com.google.cloud.firestore.QueryDocumentSnapshot;

public class PricesMapper implements Function<QueryDocumentSnapshot, HashMap<String, Object>>
{
	private static HashMap<String, Object> prices = new HashMap<>();
	
	@Override
	public HashMap<String, Object> apply(QueryDocumentSnapshot t)
	{
		prices.put("id", t.getId());
		prices.put("iva_percentage", t.getDouble("iva_percentage"));
		prices.put("stripe_percentage", t.getDouble("stripe_percentage"));
		prices.put("stripe_Constant", t.getDouble("stripe_Constant"));
		prices.put("amortization_Constant_For_Meals", t.getDouble("amortization_Constant_For_Meals"));
		prices.put("amortization_Constant_For_Break", t.getDouble("amortization_Constant_For_Break"));	
		prices.put("unit_Cost_Meal", t.getDouble("unit_Cost_Meal"));
		prices.put("unit_Cost_Break", t.getDouble("unit_Cost_Break"));
		prices.put("unit_Cost_Meal_PP", t.getDouble("unit_Cost_Meal_PP"));
		prices.put("unit_Cost_Meal_G1", t.getDouble("unit_Cost_Meal_G1"));
		prices.put("unit_Cost_Meal_G2", t.getDouble("unit_Cost_Meal_G2"));
		prices.put("unit_Cost_Break_PP", t.getDouble("unit_Cost_Break_PP"));
		prices.put("unit_Cost_Break_G1", t.getDouble("unit_Cost_Break_G1"));
		prices.put("unit_Cost_Break_G2", t.getDouble("unit_Cost_Break_G2"));
		prices.put("group_Constant_M_Vegetables", t.getDouble("group_Constant_M_Vegetables"));
		prices.put("group_Constant_M_Fruit", t.getDouble("group_Constant_M_Fruit"));
		prices.put("group_Constant_M_Cereal", t.getDouble("group_Constant_M_Cereal"));
		prices.put("group_Constant_M_Legumes", t.getDouble("group_Constant_M_Legumes"));
		prices.put("group_Constant_M_Animal", t.getDouble("group_Constant_M_Animal"));
		prices.put("group_Constant_M_Fat", t.getDouble("group_Constant_M_Fat"));
		prices.put("group_Constant_M_Other", t.getDouble("group_Constant_M_Other"));
		prices.put("group_Constant_B_Vegetables", t.getDouble("group_Constant_B_Vegetables"));
		prices.put("group_Constant_B_Fruit", t.getDouble("group_Constant_B_Fruit"));
		prices.put("group_Constant_B_Cereal", t.getDouble("group_Constant_B_Cereal"));
		prices.put("group_Constant_B_Legumes", t.getDouble("group_Constant_B_Legumes"));
		prices.put("group_Constant_B_Animal", t.getDouble("group_Constant_B_Animal"));
		prices.put("group_Constant_B_Fat", t.getDouble("group_Constant_B_Fat"));
		prices.put("group_Constant_B_Other", t.getDouble("group_Constant_B_Other"));	
		prices.put("discount_four_weeks", t.getDouble("discount_four_weeks"));
		prices.put("discount_three_weeks", t.getDouble("discount_three_weeks"));
		prices.put("discount_two_weeks", t.getDouble("discount_two_weeks"));
		prices.put("sales_goal", t.getDouble("sales_goal"));
		prices.put("maximum_discount", t.getDouble("maximum_discount"));
		
		return prices;
	}
}
