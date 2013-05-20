package org.willclark.finance.models;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="FM_TRANSACTION")
public class Transaction implements Comparable<Transaction> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction")
	@SequenceGenerator(name="transaction", sequenceName="stransaction")
	private long id;
	
	private boolean active;
	
	@ManyToOne(optional = false)
	private User user;	
	
	@ManyToOne(optional = false)
	private Account account;
	
	@Column(nullable = false)
	private Date date;
	
	@Column(nullable = false)
	private BigDecimal amount;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@ManyToOne(optional = true)
	private Category category;
	
	@ManyToMany
	private Collection<Tag> tags;
	
	private String notes;
	
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
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public void setType(String type) {
		this.type = null;
		try {
			this.type = Type.valueOf(type);
		}
		catch (Exception e) {
			// do nothing
		}
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Collection<Tag> getTags() {
		return tags;
	}
	
	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public int compareTo(Transaction transaction) {
		return this.date.compareTo(transaction.date);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Transaction=[");
		sb.append("id=").append(id).append(",");
		sb.append("active=").append(active).append(",");
		sb.append(user).append(",");
		sb.append(account).append(",");
		sb.append("amount=").append(amount).append(",");
		sb.append("type=").append(type).append(",");
		sb.append("category=").append(category).append(",");
		if (tags != null) {
			sb.append("tags=");
			for(Tag each : tags) {
				sb.append(each).append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
}