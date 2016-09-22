package com.velli.models;

import java.util.HashMap;

public class StationModel {
	private String stationName;	
	private HashMap<String, RouteModel> destinations = new HashMap<String, RouteModel>();
	
	public StationModel (String location){
		this.setStationName(location);
	}
	
	public void putRoute(Integer length, StationModel destStation){
		RouteModel newRoute = new RouteModel(length, destStation);
		destinations.put(destStation.getStationName(), newRoute);
	}
	public RouteModel getRoute(String dest){
		return destinations.get(dest);
	}
	public HashMap<String, RouteModel> getRoutes(){
		return destinations;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

}
