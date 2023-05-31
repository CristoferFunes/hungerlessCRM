package com.hungerless.HungerlessCRM.calculator;

import com.hungerless.HungerlessCRM.StateControl;
import com.hungerless.HungerlessCRM.sales.SalesContainer;

public class ProductItem
{
	private int productID;
	private String productCode;
	private int originalAmount;
	private int amount;
	private int innerAmount;
	private int tokens;
	private int calories = 2000;
	private double multiplierPP = 1;
	private double multiplierG1 = 1;
	private double multiplierG2 = 1;
	private double discount;
	private double unitCost;
	private double unitPack;
	private double productUnit;
	private double productTax;
	private double productUnitPlusTax;
	private double publicCost;
	private double publicTax;
	private double publicCostPlusTax;
	private boolean specialDiet = false;
	private boolean productDiscount;
	private int type;
	private String typeWriten;
	
	public static final int MEAL = 1;
	public static final int BREAKFAST = 2;
	public static final int SHIPMENT = 3;
	public static final String BUILDERMEAL = "100010010010020000501NNNNN";
	public static final String BUILDERBREAK = "200010010010020000501NNNNN";
	public static final String BUILDERSHIP = "300010010010020000101NNNNN";
	
	public ProductItem(String builder, boolean isNew)
	{
		productID = idConstructor(isNew);
		this.type = Integer.valueOf(builder.substring(0, 1));
		this.typeWriten = switch(this.type)
		{
			case 1 -> "Comidas";
			case 2 -> "Desayunos";
			case 3 -> "Envio";
			default -> throw new IllegalArgumentException("Unexpected value: " + this.type);
		};
		this.discount = Double.valueOf(builder.substring(1, 4))*0.1;
		this.multiplierPP = Double.valueOf(builder.substring(4, 7))*0.01;
		this.multiplierG1 = Double.valueOf(builder.substring(7, 10))*0.01;
		this.multiplierG2 = Double.valueOf(builder.substring(10, 13))*0.01;
		this.calories = Integer.valueOf(builder.substring(13, 17));
		this.innerAmount = Integer.valueOf(builder.substring(17, 19));
		this.originalAmount = Integer.valueOf(builder.substring(19, 21));
		if (builder.substring(21, 26).equals("NNNNN"))
		{
			calculateNewUnit();
			calculateCosts();
			return;
		}
		this.unitCost = Double.valueOf(builder.substring(21, 26))*0.01;
		calculateCosts();
	}
	
	private int idConstructor(boolean isNew)
	{
		int newProductID;
		if(isNew)
		{
			newProductID = -1;
			while(ProductsContainer.getKeySet().contains(newProductID))
			{
				newProductID--;
			}
			return newProductID;
		}
		newProductID = 1;
		while(ProductsContainer.getKeySet().contains(newProductID))
		{
			newProductID++;
		}
		return newProductID;
	}

	public String codeGenerator()
	{
		StringBuilder codeBuilder = new StringBuilder();
		codeBuilder.append(String.valueOf(type));
		codeBuilder.append(String.valueOf(1000 + (int)(this.discount*10)).substring(1, 4));
		codeBuilder.append(String.valueOf((int)(this.multiplierPP*100)));
		codeBuilder.append(String.valueOf((int)(this.multiplierG1*100)));
		codeBuilder.append(String.valueOf((int)(this.multiplierG2*100)));
		codeBuilder.append(String.valueOf(this.calories));
		codeBuilder.append(String.valueOf(100 + this.innerAmount).substring(1, 3));
		codeBuilder.append(String.valueOf(100 + this.originalAmount).substring(1, 3));
		codeBuilder.append(String.valueOf(100000 + (this.unitCost*100)).substring(1, 6));
		this.productCode = codeBuilder.toString();
		return this.productCode;
	}
	
	private void calculateNewUnit()
	{
		switch(this.type)
		{
			case 1 -> //comidas
			{
				this.unitCost = 0;
				this.unitCost += PricesContainer.get("group_Constant_M_Animal")*PricesContainer.getMultipliers(this.calories).get("m_Animal");
				this.unitCost += PricesContainer.get("group_Constant_M_Cereal")*PricesContainer.getMultipliers(this.calories).get("m_Cereal");
				this.unitCost += PricesContainer.get("group_Constant_M_Fat")*PricesContainer.getMultipliers(this.calories).get("m_Fat");
				this.unitCost += PricesContainer.get("group_Constant_M_Fruit")*PricesContainer.getMultipliers(this.calories).get("m_Fruit");
				this.unitCost += PricesContainer.get("group_Constant_M_Legumes")*PricesContainer.getMultipliers(this.calories).get("m_Legumes");
				this.unitCost += PricesContainer.get("group_Constant_M_Other")*PricesContainer.getMultipliers(this.calories).get("m_Other");
				this.unitCost += PricesContainer.get("group_Constant_M_Vegetables")*PricesContainer.getMultipliers(this.calories).get("m_Vegetables");
				
				double PP = this.unitCost * (PricesContainer.get("unit_Cost_Meal_PP")/100) * this.multiplierPP;
				double G1 = this.unitCost * (PricesContainer.get("unit_Cost_Meal_G1")/100) * this.multiplierG1;
				double G2 = this.unitCost * (PricesContainer.get("unit_Cost_Meal_G2")/100) * this.multiplierG2;
				
				this.unitCost = PP + G1 + G2;
			}
			case 2 -> //desayunos
			{
				this.unitCost = 0;
				this.unitCost += PricesContainer.get("group_Constant_B_Animal")*PricesContainer.getMultipliers(this.calories).get("b_Animal");
				this.unitCost += PricesContainer.get("group_Constant_B_Cereal")*PricesContainer.getMultipliers(this.calories).get("b_Cereal");
				this.unitCost += PricesContainer.get("group_Constant_B_Fat")*PricesContainer.getMultipliers(this.calories).get("b_Fat");
				this.unitCost += PricesContainer.get("group_Constant_B_Fruit")*PricesContainer.getMultipliers(this.calories).get("b_Fruit");
				this.unitCost += PricesContainer.get("group_Constant_B_Legumes")*PricesContainer.getMultipliers(this.calories).get("b_Legumes");
				this.unitCost += PricesContainer.get("group_Constant_B_Other")*PricesContainer.getMultipliers(this.calories).get("b_Other");
				this.unitCost += PricesContainer.get("group_Constant_B_Vegetables")*PricesContainer.getMultipliers(this.calories).get("b_Vegetables");
				
				double PP = this.unitCost * (PricesContainer.get("unit_Cost_Break_PP")/100) * this.multiplierPP;
				double G1 = this.unitCost * (PricesContainer.get("unit_Cost_Break_G1")/100) * this.multiplierG1;
				double G2 = this.unitCost * (PricesContainer.get("unit_Cost_Break_G2")/100) * this.multiplierG2;
				
				this.unitCost = PP + G1 + G2;	
			}
			case 3 -> //envio
			{
				this.unitCost = 50;
			}
		}
		
		this.unitCost = truncateNumber(this.unitCost);
	}
	
	private void calculateCosts()
	{
		this.amount = this.originalAmount * SalesContainer.get(StateControl.getCurrentSale()).getWeeks();
		this.unitPack = this.unitCost * this.innerAmount;
		this.productUnit = (this.unitPack + getAmortization()) * this.amount;
		this.productUnit = setWeeksDiscount(this.productUnit);
		this.productUnit = setRegularDiscount(this.productUnit);
		this.productTax = this.productUnit*PricesContainer.get("iva_percentage");
		this.productUnitPlusTax = this.productUnit + this.productTax;
		this.publicCostPlusTax = this.productUnitPlusTax/ (1-PricesContainer.get("stripe_percentage"));
		this.publicCostPlusTax = truncateNumber(this.publicCostPlusTax);
		this.publicCost = this.publicCostPlusTax/1.16;
		this.publicTax = this.publicCost*0.16;		
		
		this.specialDiet =
				this.multiplierG1 != 1 ||
				this.multiplierG2 != 1 ||
				this.multiplierPP != 1 ||
				this.calories != 2000;
		
		this.productDiscount = discount != 0;
		
		this.tokens = this.type == 3 ? 0 : this.innerAmount*this.originalAmount;
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
	
	private double setWeeksDiscount(double value)
	{
		//				this.amount *= 2;
		if(this.type == 3 || SalesContainer.get(StateControl.getCurrentSale()).getWeeks() <= 1) return value;
		return switch((int)SalesContainer.get(StateControl.getCurrentSale()).getWeeks())
		{
		case 2 -> value*= 1-(PricesContainer.get("discount_two_weeks")*0.01);
		case 3 -> value*= 1-(PricesContainer.get("discount_three_weeks")*0.01);
		case 4 -> value*= 1-(PricesContainer.get("discount_four_weeks")*0.01);
		default -> throw new IllegalArgumentException("Unexpected value: " + (int)SalesContainer.get(StateControl.getCurrentSale()).getWeeks());
		};
	}
	
	private double getAmortization()
	{
		return switch(type)
		{
		case 1 -> PricesContainer.get("amortization_Constant_For_Meals");
		case 2 -> PricesContainer.get("amortization_Constant_For_Break");
		default -> 0;
		};
	}
	
	private double setRegularDiscount(double value)
	{
		if (this.type == 3) return value;
		return value*(1-(this.discount*0.01));
	}
	
	public void revalidateCost()
	{	
		calculateCosts();
	}
	
	public double getUnit()
	{
		return this.unitCost;
	}
	
	public int getProductID()
	{
		return this.productID;
	}
	
	public double getDiscount()
	{
		return this.discount;
	}
	
	public int getType()
	{
		return this.type;
	}
	
	public int getCalories()
	{
		return this.calories;
	}
	
	public double getTotal()
	{
		return this.publicCostPlusTax;
	}
	
	public int getTokens()
	{
		return this.tokens;
	}
	
	public int getAmount()
	{
		return this.amount;
	}
	
	public int getOriginalAmount()
	{
		return this.originalAmount;
	}
	
	public String getTypeWriten()
	{
		return this.typeWriten;
	}
	
	public int getInner()
	{
		return this.innerAmount;
	}
	
	public double getMultiplierPP()
	{
		return this.multiplierPP;
	}
	
	public double getMultiplierG1()
	{
		return this.multiplierG1;
	}
	
	public double getMultiplierG2()
	{
		return this.multiplierG2;
	}
	
	public boolean isDiet()
	{
		return this.specialDiet;
	}
	
	public boolean isProductDiscount()
	{
		return this.productDiscount;
	}
	
	public void setCalories(int c)
	{
		this.calories = c;
		calculateNewUnit();
		calculateCosts();
	}
	
	public void setAmount(int a)
	{
		this.originalAmount = a;
		calculateNewUnit();
		calculateCosts();
	}
	
	public void setInner(int i)
	{
		this.innerAmount = i;
		calculateNewUnit();
		calculateCosts();
	}
	
	public void setType(int t)
	{
		this.type = t;
		this.typeWriten = switch(this.type)
				{
					case 1 -> "Comidas";
					case 2 -> "Desayunos";
					case 3 -> "Envio";
					default -> throw new IllegalArgumentException("Unexpected value: " + this.type);
				};
		calculateNewUnit();
		calculateCosts();
		
	}
	
	public void setMultiplierPP(double m)
	{
		this.multiplierPP = m;
		calculateNewUnit();
		calculateCosts();
	}
	
	public void setMultiplierG1(double m)
	{
		this.multiplierG1 = m;
		calculateNewUnit();
		calculateCosts();
	}
	
	public void setMultiplierG2(double m)
	{
		this.multiplierG2 = m;
		calculateNewUnit();
		calculateCosts();
	}
	
	public void setDiscount(double d)
	{
		this.discount = d;
		calculateNewUnit();
		calculateCosts();
	}
	
	public void setUnit(double u)
	{
		this.unitCost = u;
		calculateCosts();
	}
	
	public void setIsDiet(boolean b)
	{
		this.specialDiet = b;
	}
	
	public void setIsProductDiscount(boolean b)
	{
		this.productDiscount = b;
	}
}
