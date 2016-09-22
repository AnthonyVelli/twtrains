package com.velli.main;

import com.velli.controllers.ScheduleController;

public class ProcessSchedule {
	private static ScheduleController schedController;
	
	public static void main(String[] args){
		/*
		 * Reads settings files
		 */
		CustomReader reader = new CustomReader();
		reader.setString("/schedule.txt");		
		schedController = new ScheduleController(reader.getString());
		reader.setString("/testData.txt");
		String testString = reader.getString();
		String [] tests = testString.split("\n");
		/*
		 * Runs Tests
		 */
		for (String testCase : tests) {
			test(testCase);
		}
		
	}
	/*
	 * Custom testing. 
	 * Parses the test string, calls appropriate function & compares given solution with calculated solution.
	 */
	private static void test(String testCase){
		System.out.println("----------------------------------");
		System.out.println("Test: "+testCase+"\n");
		String[] conditions = testCase.trim().split(",");
		String testType = conditions[0];
		String[] route = conditions[1].split("-");;
		
		Integer solution = null;
		if (conditions[2].equals("NO SUCH ROUTE")) {
			solution = null;
		} else {
			solution = Integer.parseInt(conditions[2]);
		}
		
		Integer limit = null;
		if (conditions.length > 3) {
			limit = Integer.parseInt(conditions[3]);
		}
		
		Integer answer = null;
		if (testType.equals("Distance")) {
			answer = schedController.getRouteDistance(route);
		} else if (testType.equals("MaxStops")) {
			answer = schedController.getRoutesWithMaxStops(route, limit);
		} else if (testType.equals("Shortest")) {
			answer = schedController.getShortestRoute(route);
		} else if (testType.equals("NumRoutes")) {
			answer = schedController.getNumRoutes(route, limit);
		} else if (testType.equals("ExactStops")) {
			answer = schedController.getRoutesWithExactStops(route, limit);
		}
		
		if (answer == null) {
			System.out.println("Your Answer: No Such Route");
		} else {
			System.out.println("Your Answer: "+answer);
		}
		
		if (solution == null) {
			System.out.println("Correct Answer: No Such Route\n");
		} else {
			System.out.println("Correct Answer: "+solution+"\n");
		}
		
		if (solution == answer) {
			System.out.println("CORRECT!!!!\n");
		} else {
			System.out.println("INCORRECT!!!!\n");
		}
		
		System.out.println("----------------------------------");
	}
}