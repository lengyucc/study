package com.antbean.springbootdemodatamongodb;

public class Location {
	private String place;
	private String year;
	public Location() {
		super();
	}
	public Location(String place, String year) {
		super();
		this.place = place;
		this.year = year;
	}
	@Override
	public String toString() {
		return "Location [place=" + place + ", year=" + year + "]";
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
}
