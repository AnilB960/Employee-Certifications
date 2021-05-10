package com.sigma;

import java.sql.Date;

public class Enroll {
	
	private String certificationname;
	private String certificationstatus;
	private Date certifieddate;
	private Date enrolleddate;
	private Date deadline;
	
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
