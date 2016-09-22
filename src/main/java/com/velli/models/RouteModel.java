package com.velli.models;

public class RouteModel {
	private StationModel destination;
	private Integer length;
	
	public RouteModel(Integer leng, StationModel destStation) {
		this.length = leng;
		this.destination = destStation;
	}

	public StationModel getDestination() {
		return destination;
	}

	public void setDestination(StationModel destination) {
		this.destination = destination;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
}
