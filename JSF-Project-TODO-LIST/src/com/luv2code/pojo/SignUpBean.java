package com.luv2code.pojo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.luv2code.dao.CustomerDAOImpl;
import com.luv2code.entity.Customer;

@ManagedBean(name = "signup")
@RequestScoped
public class SignUpBean {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phoneNumber;

	public SignUpBean() {
		// TODO Auto-generated constructor stub
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String signUp() {
		Customer myCustomer = new Customer();
		myCustomer.setEmail(this.email);
		myCustomer.setFirstName(this.firstName);
		myCustomer.setLastName(this.lastName);
		myCustomer.setPassword(this.password);
		myCustomer.setPhoneNumber(this.phoneNumber);
		CustomerDAOImpl customerDAO = new CustomerDAOImpl();

		customerDAO.register(myCustomer);

		return "signin";
	}

	public String loginPage() {
		return "signin";
	}

}
