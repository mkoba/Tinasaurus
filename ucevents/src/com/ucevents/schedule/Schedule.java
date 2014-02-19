package com.ucevents.schedule;

public class Schedule {
	private int eventid;
	private String name;
	private int time;
	private String location;
	private int month;
	private int date;
	private int year;
	private String description;
	private int hostid;
	private int iconid;
	
	public Schedule(int eventid, String name, int time, String location,
			int month, int date, int year, String description, int hostid, int iconid) {
		super();
		this.eventid = eventid;
		this.name = name;
		this.time = time;
		this.location = location;
		this.month = month;
		this.date = date;
		this.year = year;
		this.description = description;
		this.hostid = hostid;
		this.iconid = iconid;
	}
	public int getEventid() {
		return eventid;
	}
	public String getName() {
		return name;
	}
	public int getTime() {
		return time;
	}
	public String getLocation() {
		return location;
	}
	public int getMonth() {
		return month;
	}
	public int getDate() {
		return date;
	}
	public int getYear() {
		return year;
	}
	public String getDescription() {
		return description;
	}
	public int getHostid() {
		return hostid;
	}
	
	public int getIconid() {
		return iconid;
	}
}
