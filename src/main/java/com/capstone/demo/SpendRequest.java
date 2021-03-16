package com.capstone.demo;


/**
 * This class represents the spend points request.
 * 
 * @author Tanner Greenhagen
 *
 */
public class SpendRequest {
	private Integer points;

	public SpendRequest() {
		super();
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public SpendRequest(Integer points) {
		super();
		this.points = points;
	}
	
	
}
