package com.example.antispam;

public class Call implements IPhoneNumber {
	int id;
	String number;
 
	public Call() {
	}
 
	public Call(int id, String num) {
		this.id = id;
		this.number = num;
	}
 
	public Call(String num) {
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