package com.example.db;

public class BlackList implements IPhoneNumber {
	int id;
	String number;
 
	public BlackList() {
	}
 
	public BlackList(int id, String num) {
		this.id = id;
		this.number = num;
	}
 
	public BlackList(String num) {
		this.number = num;
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
}