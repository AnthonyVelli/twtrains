package com.velli.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class StationModel {
	private String stationName;	
	private HashMap<String, RouteModel> destinations = new HashMap<String, RouteModel>();
	private ArrayList<FullRouteModel> fullRoutes;
	private ArrayList<String> stationsChecked;
	
	public StationModel (String location){
		this.setStationName(location);
		fullRoutes = new ArrayList<FullRouteModel> ();
		stationsChecked = new ArrayList<String>();
	}
	
	public void populateFullRoutes(){
		Collection <RouteModel> routes = this.getRoutes().values();
		for (RouteModel route : routes){
			FullRouteModel fullRouteModel = new FullRouteModel(this);
			fullRouteModel.addRoute(route);
			recurseFullRoute(fullRouteModel);
		}

	}
	
	private void recurseFullRoute(FullRouteModel fullRoute){
		StationModel station = fullRoute.getTermination();
		Collection <RouteModel> routes = station.getRoutes().values();
		for (RouteModel route : routes){
			if (route.getDestination() == this){
				FullRouteModel newRoute = fullRoute.clone();
				newRoute.addRoute(route);
				newRoute.setLoop(true);
				
				this.fullRoutes.add(newRoute);
			} else if (!stationsChecked.contains(route.getDestination().getStationName())){
				FullRouteModel newRoute = fullRoute.clone();
				newRoute.addRoute(route);
				stationsChecked.add(newRoute.getTermination().getStationName());
				this.fullRoutes.add(newRoute);
				recurseFullRoute(newRoute);
			}
		}
		
	}
	
	public void getRoutesTo(String destination){
		ArrayList <FullRouteModel> foundRoutes = new ArrayList<FullRouteModel>();
		for (FullRouteModel route : this.fullRoutes){
			if (route.getTermination().getStationName().equals(destination)){
				foundRoutes.add(route);
			}
		}
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

	public HashMap<String, RouteModel> getDestinations() {
		return destinations;
	}

	public void setDestinations(HashMap<String, RouteModel> destinations) {
		this.destinations = destinations;
	}

	public HashMap<String, FullRouteModel> getFullRoutes() {
		return fullRoutes;
	}

	public void setFullRoutes(HashMap<String, FullRouteModel> fullRoutes) {
		this.fullRoutes = fullRoutes;
	}

}
