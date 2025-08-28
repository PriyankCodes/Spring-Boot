package com.tss.ioc.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class Computer {

	@Value("Apple")
	private String name;
	
	@Autowired
	private Harddisk harddisk;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Harddisk getHarddisk() {
		return harddisk;
	}
	public void setHarddisk(Harddisk harddisk) {
		this.harddisk = harddisk;
	}
	public Computer(String name, Harddisk harddisk) {
		super();
		this.name = name;
		this.harddisk = harddisk;
	}
	public Computer() {
		super();
	}
	
	
}
