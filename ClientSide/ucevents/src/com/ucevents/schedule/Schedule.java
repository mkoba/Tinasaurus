package com.ucevents.schedule;

import java.util.Arrays;
import java.util.List;

import com.ucevents.events.Events;

import android.os.Parcelable;
import android.os.Parcel;

public class Schedule implements Comparable<Schedule>, Parcelable{
	private String eventid;
	private String name;
	private int time;
	private String location;
	private int month;
	private int date;
	private int year;
	private String description;
	private String host;
	private int iconid;
	private List<String> attendees;
	private boolean attending;
	private String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Oct", "Nov", "Dec"};

	public Schedule(String eventid, String name, int time, String location,
			int month, int date, int year, String description, String host,
			int iconid, String[] attendees, boolean attending) {
		super();
		this.eventid = eventid;
		this.name = name;
		this.time = time;
		this.location = location;
		this.month = month;
		this.date = date;
		this.year = year;
		this.description = description;
		this.host = host;
		this.iconid = iconid;
		this.attendees = Arrays.asList(attendees);
		this.attending = attending;
	}
	public String getEventid() {
		return eventid;
	}
	public String getName() {
		return name;
	}
	public int getTime() {
		return time;
	}
	public String getTimeDisplay(){
		int hour = time/100;
		int min = time%100;
		boolean pm = false;
		if (hour == 12){
			hour = 12;
			pm = true;
		}
		else if (hour == 24 || hour == 0){
			hour = 12;
		}
		else if (hour > 12){
			hour -= 12;
			pm = true;
		}
		String timeDisp = null;
		if (hour == 0){
			timeDisp = "00:";
		}
		else{
			if (hour % 10 > 0){
				timeDisp = hour + ":";
			}
			else{
				timeDisp = " " + hour + ":";
			}
		}
		if (min == 0){
			timeDisp += "00 ";
		}
		else if (min/10 == 0){
			timeDisp += "0" + min;
		}
		else{
			timeDisp += min + " ";
		}
		if (pm){
			timeDisp += "pm";
		}
		else{
			timeDisp += "am";
		}
		return timeDisp;
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
	public String getEventDate(){
		return months[getMonth()] + " " + getDate() + ", " + getYear();
	}
	public String getDescription() {
		return description;
	}
	public String getHost() {
		return host;
	}
	
	public String getHostId(){
		return eventid.substring(0, eventid.indexOf("_"));
	}

	public int getIconid() {
		return iconid;
	}
	public List<String> getAttendees(){
		return attendees;
	}
	public boolean getAttending(){
		return attending;
	}

	@Override
	public int describeContents() {
		return 0;
	}
	

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(eventid);
		dest.writeString(name);
		dest.writeInt(time);
		dest.writeString(location);
		dest.writeInt(month);
		dest.writeInt(date);
		dest.writeInt(year);
		dest.writeString(description);
		dest.writeString(host);
		dest.writeInt(iconid);
		dest.writeStringArray(attendees.toArray(new String[attendees.size()]));
		dest.writeByte((byte) (attending ? 1 : 0));
	}

	public static final Parcelable.Creator<Schedule> CREATOR =
			new Parcelable.Creator<Schedule>() {
		@Override
		public Schedule createFromParcel(Parcel source) {
			Schedule event = new Schedule(source.readString(), source.readString(), source.readInt(), source.readString(), source.readInt(), 
					source.readInt(), source.readInt(), source.readString(), source.readString(), source.readInt(), source.createStringArray(), 
					source.readByte() != 0);
			return event;

		}

		@Override
		public Schedule[] newArray(int size) {
			return null;

		}
	};

	@Override
	public int compareTo(Schedule another) {
		// TODO Auto-generated method stub
		if (((Schedule) another).getYear() > this.getYear()){
			return -1;
		}
		if (((Schedule) another).getYear() < this.getYear()){
			return 1;
		}
		if (((Schedule) another).getMonth() > this.getMonth()){
			return -1;
		}
		if (((Schedule) another).getMonth() < this.getMonth()){
			return 1;
		}
		if (((Schedule) another).getDate() > this.getDate()){
			return -1;
		}
		if (((Schedule) another).getDate() < this.getDate()){
			return 1;
		}
		if (((Schedule) another).getTime() > this.getTime()){
			return -1;
		}
		if (((Schedule) another).getTime() < this.getTime()){
			return 1;
		}
		return 0;
	}

}
