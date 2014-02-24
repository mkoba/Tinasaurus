package com.ucevents.events;

import android.os.Parcel;
import android.os.Parcelable;

public class Events implements Parcelable{
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
	
	public Events() {
		
	}
	
	public Events(int eventid, String name, int time, String location,
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
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(eventid);
		dest.writeString(name);
		dest.writeInt(time);
		dest.writeString(location);
		dest.writeInt(month);
		dest.writeInt(date);
		dest.writeInt(year);
		dest.writeString(description);
		dest.writeInt(hostid);
		dest.writeInt(iconid);
	}
	
	public static final Parcelable.Creator<Events> CREATOR =
			new Parcelable.Creator<Events>() {

        @Override

        public Events createFromParcel(Parcel source) {
        	//Events event = new Events();
        	Events event = new Events(source.readInt(), source.readString(), source.readInt(), source.readString(), source.readInt(), 
        			source.readInt(), source.readInt(), source.readString(), source.readInt(), source.readInt());
            /*event.eventid = source.readInt();
            event.name = source.readString();
            event.time = source.readInt();
            event.location = source.readString();
            event.month = source.readInt();
            event.year = source.readInt();
            event.description = source.readString();
            event.hostid = source.readInt();
            event.iconid = source.readInt();*/
            				
            return event;

        }


        @Override

        public Events[] newArray(int size) {

            return null;

        }

    };
}
