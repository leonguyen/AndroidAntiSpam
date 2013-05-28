package com.example.db;

public class BlackList implements IPhoneNumber {
	int id;
	String number;
	int type = 0; // 1: whitelist; 0: backlist

	public BlackList() {
	}

	public BlackList(int id, String num, int t) {
		this.id = id;
		this.number = num;
		this.type = t;
	}

	public BlackList(String num) {
		this.number = num;
		this.type = 0;
	}

	public BlackList(String num, int t) {
		this.number = num;
		this.type = t;
	}
	
	public int getID() {
		return this.id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String num) {
		this.number = num;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int t) {
		this.type = t;
	}
}