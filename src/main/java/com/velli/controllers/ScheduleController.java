package com.velli.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.velli.models.RouteModel;
import com.velli.models.ScheduleModel;
import com.velli.models.StationModel;

public class ScheduleController {
	/*
	 * routesChecked is a cache used by several of the recursive functions.
	 */
	private ScheduleModel scheduleModel;
	private HashMap<String,Integer> routesChecked;
	/*
	 * Constructor initiates scheduleModel.
	 * scheduleModel holds a hashmap of all Station objects.
	 * Station objects hold a hashmap of their station's routes.  Each route is an object.
	 * Route objects contain the length of the route, and the destination Station object.  
	 */
	public ScheduleController(String schedString){
		String[] schedArray = schedString.split(", ");
		scheduleModel = new ScheduleModel();
		for (String route : schedArray) {
			StationModel origin = scheduleModel.getOrCreateStation(route.substring(0, 1));
			StationModel dest = scheduleModel.getOrCreateStation(route.substring(1, 2));
			Integer length = Integer.parseInt(route.substring(2));
			origin.putRoute(length, dest);
		}
	}
	/*
	 * Start exposed functions for calculating train routes
	 */
	public Integer getRouteDistance(String [] fullRoute){		
		StationModel departureStation = scheduleModel.getStation(fullRoute[0]);
		int tripLength = 0;
		for(int x=1; x < fullRoute.length; x++){
			RouteModel route = departureStation.getRoute(fullRoute[x]);
			if (route == null){
				return null;
			} else {
				tripLength += route.length;
				departureStation = route.destination;
			}
		}
		return tripLength;
	}
	
	public Integer getShortestRoute(String [] fullRoute){
		routesChecked = new HashMap<String, Integer>();
		StationModel departureStation = scheduleModel.getStation(fullRoute[0]);
		StationModel arrivalStation = scheduleModel.getStation(fullRoute[1]);
		ArrayList<String> routeTravelled = new ArrayList<String>();
		routeTravelled.add(departureStation.getStationName());
		return findShortestRoute(departureStation, arrivalStation.getStationName(), routeTravelled);
	}
	
	public Integer getNumRoutes(String [] fullRoute, Integer lengthLimit){
		routesChecked = new HashMap<String, Integer>();
		StationModel departureStation = scheduleModel.getStation(fullRoute[0]);
		StationModel arrivalStation = scheduleModel.getStation(fullRoute[1]);
		return findAllRoutes(departureStation, arrivalStation, 0, 30);
	}
	
	public Integer getRoutesWithMaxStops(String [] fullRoute, Integer limit){
		routesChecked = new HashMap<String, Integer>();
		StationModel departureStation = scheduleModel.getStation(fullRoute[0]);
		StationModel arrivalStation = scheduleModel.getStation(fullRoute[1]);
		return findRoutesWithMaxStops(departureStation, arrivalStation.getStationName(), limit);
	}
	
	public Integer getRoutesWithExactStops(String [] fullRoute, Integer limit){
		routesChecked = new HashMap<String, Integer>();
		StationModel departureStation = scheduleModel.getStation(fullRoute[0]);
		StationModel arrivalStation = scheduleModel.getStation(fullRoute[1]);
		return findRoutesWithExactStops(departureStation, arrivalStation.getStationName(), limit);
	}
	
	/*
	 * End exposed functions
	*/
	
	/*
	 * Start private recursive functions used by exposed functions
	 */
		
	private Integer findShortestRoute(StationModel currentStation, String arrivalStationName, ArrayList<String> routeLength){
		RouteModel foundArrival = currentStation.getRoute(arrivalStationName);
		if (foundArrival != null){			
			this.routesChecked.put(currentStation.getStationName(), foundArrival.length);
			return foundArrival.length;
		} else {
			HashMap<String,RouteModel> allRoutes = currentStation.getRoutes();
			Integer shortestRoute = null;
			for(RouteModel x : allRoutes.values() ) {
				String xName = x.destination.getStationName();
				if (!routeLength.contains(xName)) {
					if (this.routesChecked.get(xName) != null){
						Integer xTotalLength = x.length + this.routesChecked.get(xName);
						if (shortestRoute == null || xTotalLength < shortestRoute){
							shortestRoute = xTotalLength;
						}
					} else {
						ArrayList<String> routeLengthCopy = (ArrayList<String>) routeLength.clone();
						routeLengthCopy.add(x.destination.getStationName());
						Integer completedRoute = findShortestRoute(x.destination, arrivalStationName, routeLengthCopy);
						if (completedRoute != null) {
							completedRoute += x.length;
							if (shortestRoute == null || completedRoute < shortestRoute){
								shortestRoute = completedRoute;
							}
						}
					}
				}
			}
			this.routesChecked.put(currentStation.getStationName(), shortestRoute);
			return shortestRoute;
		}
	}
	
	private Integer findRoutesWithMaxStops(StationModel currentStation, String arrivalStationName, Integer maxStops){
		if (maxStops == 0){
			return 0;
		}
		Integer routeCompiler = 0;
		HashMap<String,RouteModel> allRoutes = currentStation.getRoutes();
		for(RouteModel x : allRoutes.values()){
			if (x.destination.getStationName().equals(arrivalStationName)) {
				routeCompiler++;
			}
			routeCompiler += findRoutesWithMaxStops(x.destination, arrivalStationName, maxStops-1);
		}
		return routeCompiler;
	}
	
	private Integer findRoutesWithExactStops(StationModel currentStation, String arrivalStationName, Integer limitStops){
		Integer routeCompiler = 0;
		if (limitStops < 0) {
			return 0;
		} else if (currentStation.getStationName().equals(arrivalStationName) && limitStops == 0){ 
			return 1;
		} else {
			HashMap<String,RouteModel> allRoutes = currentStation.getRoutes();
			for(RouteModel x : allRoutes.values()){
				routeCompiler += findRoutesWithExactStops(x.destination, arrivalStationName, limitStops-1);
			}
		}
		return routeCompiler;
	}
	
	
	private Integer findAllRoutes(StationModel currentStation, StationModel arrivalStation, Integer routeLength, Integer maxLength){
		Integer routeCompiler = 0;
		if (routeLength >=30) {
			return 0;
		} else {
			HashMap<String,RouteModel> allRoutes = currentStation.getRoutes();
			for(RouteModel x : allRoutes.values()){
				if (x.destination == arrivalStation && ((routeLength + x.length) < maxLength)) {
					routeCompiler++;
				}
				routeCompiler += findAllRoutes(x.destination, arrivalStation, routeLength+x.length, maxLength);
			}
		}
		return routeCompiler;
	}
	
}
