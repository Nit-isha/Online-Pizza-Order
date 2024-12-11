package com.cg.onlinepizza.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pizza {
	@Id
	@GeneratedValue
	private int pizzaId;
	private String pizzaType;
	private String pizzaName;
	private String pizzaSize;
	private String pizzaDescription;
	private double pizzaCost;

	public Pizza() {
		
	}
	public Pizza(String pizzaType, String pizzaName, String pizzaSize, String pizzaDescription, double pizzaCost) {
		super();
		//this.pizzaId = pizzaId;
		this.pizzaType = pizzaType;
		this.pizzaName = pizzaName;
		this.pizzaSize = pizzaSize;
		this.pizzaDescription = pizzaDescription;
		this.pizzaCost = pizzaCost;
	}
	
    public String getPizzaDescription() {
        return pizzaDescription;
    }
    public void setPizzaDescription(String pizzaDescription) {
        this.pizzaDescription = pizzaDescription;
    }
    public int getPizzaId() {
        return pizzaId;
    }
    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }
    public String getPizzaType() {
        return pizzaType;
    }
    public void setPizzaType(String pizzaType) {
        this.pizzaType = pizzaType;
    }
    public String getPizzaName() {
        return pizzaName;
    }
    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }
    public String getPizzaSize() {
        return pizzaSize;
    }
    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }
    public double getPizzaCost() {
        return pizzaCost;
    }
    public void setPizzaCost(double pizzaCost) {
        this.pizzaCost = pizzaCost;
    }
}
