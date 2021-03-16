package com.capstone.demo;


/**
 * This class represents each payer and their total points.
 * 
 * @author Tanner Greenhagen
 *
 */
public class Payer {

	private String payer;
	private int points;
	
	public Payer(String payer, int points) {
		super();
		this.payer = payer;
		this.points = points;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	
}
