package com.velli.models;

import java.util.HashMap;

public class ScheduleModel {
	private HashMap<String, StationModel> stationModels = new HashMap<String, StationModel>();
	
	public ScheduleModel(){
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
