package com.example.antispam;

public class Sms {
	int id;
	String sms;

	public Sms() {
	}

	public Sms(int id, String s) {
		this.id = id;
		this.sms = s;
	}

	public Sms(String s) {
		this.sms = s;
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
}