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
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
