package com.example.db;

public class Sms {
	int id;
	String sms;
	String time;

	public Sms() {
	}

	public Sms(int id, String s, String t) {
		this.id = id;
		this.sms = s;
		this.time = t;
	}

	public Sms(String s, String t) {
		this.sms = s;
		this.time = t;
	}

	public int getID() {
		return this.id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getSms() {
		return this.sms;
	}

	public void setSms(String s) {
		this.sms = s;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String t) {
		this.time = t;
	}
}