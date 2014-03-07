package com.ucevents.schedule;

import java.util.Arrays;
import java.util.List;

import com.ucevents.events.Events;

import android.os.Parcelable;
import android.os.Parcel;

public class Schedule implements Parcelable {
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
	public String getHostid() {
		return host;
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

}
