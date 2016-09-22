package com.velli.models;

import java.util.ArrayList;

public class FullRouteModel {
	private Integer length;
	private Integer stops;
	private ArrayList<RouteModel> fullRoute;
	private StationModel origin;
	private StationModel termination;
	private Boolean loop;
	
	public FullRouteModel(StationModel origin){
		this.origin = origin;
		this.fullRoute = new ArrayList<RouteModel>();
		this.termination = origin;
		this.loop = false;
	}
	
	public void addRoute(RouteModel route){
		fullRoute.add(route);
		length += route.getLength();
		stops += 1;
		this.termination = route.getDestination();
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getStops() {
		return stops;
	}

	public void setStops(Integer stops) {
		this.stops = stops;
	}

	public ArrayList<RouteModel> getFullRoute() {
		return fullRoute;
	}

	public void setFullRoute(ArrayList<RouteModel> fullRoute) {
		this.fullRoute = fullRoute;
	}

	public StationModel getOrigin() {
		return origin;
	}

	public void setOrigin(StationModel origin) {
		this.origin = origin;
	}

	public StationModel getTermination() {
		return termination;
	}

	public void setTermination(StationModel termination) {
		this.termination = termination;
	}
	
	public Boolean getLoop() {
		return loop;
	}

	public void setLoop(Boolean loop) {
		this.loop = loop;
	}

	@Override
	public FullRouteModel clone(){
		FullRouteModel newRoute = new FullRouteModel(this.origin);
		newRoute.setFullRoute(this.getFullRoute());
		newRoute.setTermination(this.getTermination());
		newRoute.setLength(this.getLength());
		newRoute.setStops(this.getStops());
		return newRoute;
	}
}
