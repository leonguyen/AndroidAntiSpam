package com.example.db;

public class Call implements IPhoneNumber {
	int id;
	String number;
	String time;
 
	public Call() {
	}
 
	public Call(int id, String num, String t) {
		this.id = id;
		this.number = num;
		this.time = t;
	}
 
	public Call(String num, String t) {
		this.number = num;
		this.time = t;
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
	
	public String getTime() {
		return this.time;
	}
 
	public void setTime(String t) {
		this.time = t;
	}
}