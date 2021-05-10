package com.sigma;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="certifications")
public class Certifications {
	
	@Id
	@Column(name="certificationname")
	private String certificationname;

	public String getCertificationname() {
		return certificationname;
	}

	public void setCertificationname(String certificationname) {
		this.certificationname = certificationname;
	}

}
