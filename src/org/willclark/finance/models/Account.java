package org.willclark.finance.models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="FM_ACCOUNT")
public class Account implements Comparable<Account> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account")
	@SequenceGenerator(name="account", sequenceName="saccount")
	private long id;
	
	private boolean active;
	
	private Date created;
	
	private Date modified;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(nullable = false)
	private String name;
	
	private String number;
	
	private String notes;
	
	private BigDecimal balance;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getModified() {
		return modified;
	}
	
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Account=[");
		sb.append("id=").append(id).append(",");
		sb.append(user).append(",");
		sb.append("created=").append(created).append(",");
		sb.append("modified=").append(modified).append(",");
		sb.append("name=").append(name).append(",");
		sb.append("balance=").append(balance).append(",");
		sb.append("number=").append(number).append(",");
		sb.append("notes=").append(notes);
		sb.append("]");
		return sb.toString();
	}

	public int compareTo(Account account) {
		return this.name.compareTo(account.name);
	}
	
}
