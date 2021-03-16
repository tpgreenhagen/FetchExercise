package com.capstone.demo;

import java.util.Date;
/**
 * This class represents the Transaction Object. 
 * 
 * @author Tanner Greenhagen
 *
 */
public class Transaction 
{

	private String payer;
	private Integer points;
	private Date timestamp;
	
	
	public Transaction(String payer, Integer points, Date timestamp) {
		super();
		this.payer = payer;
		this.points = points;
		this.timestamp = timestamp;
	}
	public String getName() {
		return payer;
	}
	public void setName(String payer) {
		this.payer = payer;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Date getDate() {
		return timestamp;
	}
	public void setDate(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
