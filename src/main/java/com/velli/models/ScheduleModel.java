package com.velli.models;

import java.util.HashMap;

public class ScheduleModel {
	private HashMap<String, StationModel> stationModels = new HashMap<String, StationModel>();
	
	public ScheduleModel(String schedString){
		String[] schedArray = schedString.split(", ");
		for (String route : schedArray) {
			StationModel origin = getOrCreateStation(route.substring(0, 1));
			StationModel dest = getOrCreateStation(route.substring(1, 2));
			Integer length = Integer.parseInt(route.substring(2));
			origin.putRoute(length, dest);
		}
	}
	
	public StationModel getOrCreateStation(String stationName){
		if (stationModels.containsKey(stationName)){
			return stationModels.get(stationName);
		} else {
			StationModel newStation = new StationModel(stationName);
			stationModels.put(stationName, newStation);
			return newStation;
		}		
	}
	
	public StationModel getStation(String stationName){
		return stationModels.get(stationName);
	}
	
	public HashMap<String, StationModel> getStations(){
		return stationModels;
	}
}
