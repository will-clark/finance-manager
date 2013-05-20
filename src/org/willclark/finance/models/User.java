package org.willclark.finance.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.codec.digest.DigestUtils;
import org.willclark.finance.utils.DateUtil;
import org.willclark.finance.utils.MathUtil;
import org.willclark.finance.utils.StringUtil;

@Entity
@Table(name="FM_USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user")
	@SequenceGenerator(name="user", sequenceName="suser")
	private long id;
	
	private boolean active;
	
	private Date created;
	
	private Date modified;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String salt;
	
	private Date resetExpiration;
	private String resetToken;

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
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
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
	    if (password.equals(this.password)) return;
	    this.salt = StringUtil.generatePseudoRandomToken();
	    this.password = DigestUtils.sha512Hex(password + salt);
	}
	
	public Date getResetExpiration() {
		return resetExpiration;
	}
	
	public void setResetExpiration(Date resetExpiration) {
		this.resetExpiration = resetExpiration;
	}
	
	public String getResetToken() {
		return resetToken;
	}
	
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	
	public void initReset() {
		this.resetExpiration = DateUtil.addDays(new Date(), 1);
		this.resetToken = StringUtil.generatePseudoRandomToken();
	}
	
	public boolean authenticate(String password) {
	    return (this.password.equals(DigestUtils.sha512Hex(password + salt)));
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("User=[");
		sb.append("id=").append(id).append(",");
		sb.append("active=").append(active).append(",");
		sb.append("created=").append(created).append(",");
		sb.append("modified=").append(modified).append(",");
		sb.append("username=").append(username).append(",");
		sb.append("email=").append(email).append(",");
		sb.append("password=").append("XXX");
		sb.append("]");
		return sb.toString();
	}
	
}
