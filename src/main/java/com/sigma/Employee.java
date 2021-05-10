package com.sigma;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employees")
public class Employee {
	
	@Id
	@Column(name="username")
	private String username;
	
	@Column(name="firstname")
	private String firstName;
	
	@Column(name="lastname")
	private String lastName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="text_password")
	private String text_password;
	
	@Column(name="role")
	private String role;
	
	@Column(name="enabled")
	private int enabled;
	
	@Column(name="gender")
	private char gender;
	
	@Column(name="dateofjoin")
	private Date dateofjoin;
	
	@Column(name="experienced")
	private char experienced;
	
	@Column(name="location")
	private String location;
	
	@Column(name="certificationname")
	private String certificationname;
	
	@Column(name="certificationstatus")
	private String certificationstatus;
	
	@Column(name="certifieddate")
	private Date certifieddate;

	@Column(name="enrolleddate")
	private Date enrolleddate;

	@Column(name="deadline")
	private Date deadline;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getText_password() {
		return text_password;
	}

	public void setText_password(String text_password) {
		this.text_password = text_password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public Date getDateofjoin() {
		return dateofjoin;
	}

	public void setDateofjoin(Date dateofjoin) {
		this.dateofjoin = dateofjoin;
	}

	public char getExperienced() {
		return experienced;
	}

	public void setExperienced(char experienced) {
		this.experienced = experienced;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCertificationname() {
		return certificationname;
	}

	public void setCertificationname(String certificationname) {
		this.certificationname = certificationname;
	}

	public String getCertificationstatus() {
		return certificationstatus;
	}

	public void setCertificationstatus(String certificationstatus) {
		this.certificationstatus = certificationstatus;
	}

	public Date getCertifieddate() {
		return certifieddate;
	}

	public void setCertifieddate(Date certifieddate) {
		this.certifieddate = certifieddate;
	}

	public Date getEnrolleddate() {
		return enrolleddate;
	}

	public void setEnrolleddate(Date enrolleddate) {
		this.enrolleddate = enrolleddate;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
}
