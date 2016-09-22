package com.velli.models;

public class RouteModel {
	public StationModel destination;
	public Integer length;
	public RouteModel(Integer leng, StationModel destStation) {
		this.length = leng;
		this.destination = destStation;
	}
}
