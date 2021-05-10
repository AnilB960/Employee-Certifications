package com.sigma;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

@Service
public class CertificationsModel {
	
	@PersistenceContext
	transient EntityManager em;
	
	public List<String> getAllCertifications() {
		List<String> returnList = new ArrayList<String>();
		TypedQuery<Certifications> query = em.createQuery("FROM Certifications c", Certifications.class);
		List<Certifications> elementList = query.getResultList();
		for(Certifications c: elementList) {
			returnList.add(c.getCertificationname());
		}
		return returnList;
	}
	
	public List<String> getAllStatus() {
		List<String> status = new ArrayList<String>();
		status.add("NOT ENROLLED");
		status.add("IN PROGRESS");
		status.add("COMPLETED");
		status.add("FAILED");
		return status;
	}
}
