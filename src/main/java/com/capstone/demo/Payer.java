package com.capstone.demo;


/**
 * This class represents each payer and their total balance.
 * 
 * @author Tanner Greenhagen
 *
 */
public class Payer {

	private String payer;
	private int points;
	
	public Payer(String name, int balance) {
		super();
		this.payer = name;
		this.points = balance;
	}
	public String getName() {
		return payer;
	}
	public void setName(String name) {
		this.payer = name;
	}
	public int getBalance() {
		return points;
	}
	public void setBalance(int balance) {
		this.points = balance;
	}
	
	
}
