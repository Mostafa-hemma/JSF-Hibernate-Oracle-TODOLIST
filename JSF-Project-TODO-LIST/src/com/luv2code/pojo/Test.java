package com.luv2code.pojo;

import java.time.LocalDate;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "calendarView")
public class Test {
	private LocalDate date;

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

public void loadDate() {
	System.out.println("Date Picker : "+this.getDate());
}
}
