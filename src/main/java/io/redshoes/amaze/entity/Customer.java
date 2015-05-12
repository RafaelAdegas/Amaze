package io.redshoes.amaze.entity;

import io.redshoes.amaze.JsonViews;

import javax.persistence.*;

import org.codehaus.jackson.map.annotate.JsonView;


/**
 * The persistent class for the customers database table.
 * 
 */
@javax.persistence.Entity
public class Customer implements Entity  {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long idCustomer;

	@Column
	private String login;

	@Column
	private String name;

	@Column
	private String password;
	
	public Customer() {}

	@JsonView(JsonViews.Admin.class)
	public Long getIdCustomer() {
		return this.idCustomer;
	}

	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
	}

	@JsonView(JsonViews.User.class)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@JsonView(JsonViews.User.class)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonView(JsonViews.User.class)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString()
	{
		return String.format("Customer[%d, %s, %s, %s]", this.idCustomer, this.login, this.name, this.password);
	}

}