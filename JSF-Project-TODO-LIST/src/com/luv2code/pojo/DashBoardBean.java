package com.luv2code.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.luv2code.dao.CustomerDAOImpl;
import com.luv2code.dao.TaskDAOImpl;
import com.luv2code.entity.Customer;
import com.luv2code.entity.Task;

@ManagedBean(name = "dashboard")
@ViewScoped
public class DashBoardBean {

	private List<Task> customerTasks;
	private Task selectedTask;
	private TaskDAOImpl taskDAO = new TaskDAOImpl();
	private CustomerDAOImpl customerDAO = new CustomerDAOImpl();
	Customer customer = (Customer) getHttpSession().getAttribute("myCustomer");
	private List<String> statuses;

	public DashBoardBean() {
		statuses = new ArrayList<String>();
		statuses.add("Done");
		statuses.add("Postponed");
		statuses.add("in Progress");
		statuses.add("Started");
		statuses.add("Not Started");

	}

	public List<Task> getCustomerTasks() {
		return customerTasks;
	}

	public void setCustomerTasks(List<Task> customerTasks) {
		this.customerTasks = customerTasks;
	}

	public Task getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(Task selectedTask) {
		this.selectedTask = selectedTask;
	}

	public List<String> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<String> statuses) {
		this.statuses = statuses;
	}

	public void openNew() {
		this.selectedTask = new Task();
	}

	@PostConstruct
	public void init() {
		customerTasks = taskDAO.getAllTasks(customer.getId());
		System.out.println("customer name : " + customer.getFirstName());
	}

	public String signOut() {
		customerDAO.logOut();
		getHttpSession().invalidate();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Log Out Successfully"));
		return "signin";
	}

	public void saveTask() {

		if (this.selectedTask.getId() != 0) {
			System.out.println("********************* : " + this.selectedTask.getId());

			try {
				taskDAO.saveTask(this.selectedTask);
				customerTasks = taskDAO.getAllTasks(customer.getId());
				PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Task Edited"));
				PrimeFaces.current().ajax().update("form:messages", "form:dt-products");

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("save", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Wrong Dates", "Enter Start and End Dates in a Correct Way "));

				e.printStackTrace();
				;
			}

		} else {

			Task myTask = new Task();
			myTask.setId(0);
			myTask.setTaskName(this.selectedTask.getTaskName());
			myTask.setStartDate(this.selectedTask.getStartDate());
			myTask.setEndDate(this.selectedTask.getEndDate());
			myTask.setTaskDescription(this.selectedTask.getTaskDescription());
			myTask.setTaskStatus(this.selectedTask.getTaskStatus());
			try {
				customer.getTasks().add(myTask);
				myTask.setCustomer(customer);
				taskDAO.saveTask(myTask);
				customerTasks = taskDAO.getAllTasks(customer.getId());
				PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Task Added"));
				PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("save", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Wrong Dates", "Enter Start and End Dates in a Correct Way "));
				e.printStackTrace();
			}

		}

	}

	public void deleteTask() {

		taskDAO.deleteTask(this.selectedTask);
		this.selectedTask = null;
		customerTasks = taskDAO.getAllTasks(customer.getId());

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Task Removed"));
		PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
	}

	public HttpSession getHttpSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}
}
