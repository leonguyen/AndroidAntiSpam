package com.example.antispam;

import java.util.List;

public interface IDA {

	void add(Object obj);

	Object get(int id);

	public List<?> getAll();

	public int update(Object obj);

	public void delete(Object obj);
	
	public int getCount();
}
