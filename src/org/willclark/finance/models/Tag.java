package org.willclark.finance.models;

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
@Table(name="FM_TAG")
public class Tag implements Comparable<Tag> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tag")
	@SequenceGenerator(name="tag", sequenceName="stag")
	private long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="user_id")
	private User user;
	
	private boolean active;
	private Date created;
	private Date modified;
	
	@Column(nullable = false)
	private String name;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Tag=[");
		sb.append("id=").append(id).append(",");
		sb.append(user).append(",");
		sb.append("active=").append(active).append(",");
		sb.append("created=").append(created).append(",");
		sb.append("modified=").append(modified).append(",");
		sb.append("name=").append(name);
		sb.append("]");
		return sb.toString();
	}

	public int compareTo(Tag category) {
		return (this.name.compareTo(category.name));
	}
	
}
