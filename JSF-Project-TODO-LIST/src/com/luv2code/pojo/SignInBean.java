package com.luv2code.pojo;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.luv2code.dao.CustomerDAOImpl;
import com.luv2code.entity.Customer;

@ManagedBean(name = "signin")
@SessionScoped
public class SignInBean {

	private String email;
	private String password;

	public SignInBean() {

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

	public String signIn() {
		CustomerDAOImpl customerDAO = new CustomerDAOImpl();

		Customer customer;
		try {
			customer = customerDAO.getCustomerByEmail(this.getEmail());
			if (customer != null && customer.getPassword().equals(this.getPassword())) {
				System.out.println("email : " + this.getEmail());
				System.out.println("password : " + this.getPassword());
				System.out.println("success");
				HttpSession myHttpSession = getHttpSession();
				myHttpSession.setAttribute("myCustomer", customer);
				return "dashboard";

			}else {
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR
								,"Wrong Password"
								,"Please Enter the Correct Password"
								));
				return "signin";

			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, 	new FacesMessage(
					FacesMessage.SEVERITY_ERROR
					,"Wrong Email"
					,"Please Enter  Correct Email"
					));
			System.out.println("failed");

			e.printStackTrace();
			return "signin";
		}

	}

	public String register() {
		return "signup";
	}

	public HttpSession getHttpSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

}
