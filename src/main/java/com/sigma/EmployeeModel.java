package com.sigma;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeModel {

    @PersistenceContext
    transient EntityManager em;

    @Autowired
    private CertificationsModel cm;


    public int checkPasswords(String password, String matchingPassword) {
        if (password.equals(matchingPassword))
            return 1;
        return 0;
    }

    public int checkUsernameExists(String username) {
        TypedQuery < Employee > query = em.createQuery("FROM Employee e", Employee.class);
        List < Employee > elementList = query.getResultList();
        for (Employee emp: elementList) {
            if (emp.getUsername().equals(username))
                return 0;
        }
        return 1;
    }
    public String generateByCrypt(String password) {
        int strength = 10; // work factor of bcrypt
        BCryptPasswordEncoder bCryptPasswordEncoder =
            new BCryptPasswordEncoder(strength, new SecureRandom());
        return bCryptPasswordEncoder.encode(password);

    }
    @SuppressWarnings("unchecked")
    public List < Employee > getUserDetails(String username) {
        TypedQuery < Employee > query = em.createQuery("FROM Employee e WHERE  e.username = :username", Employee.class).setParameter("username", username);
        List < Employee > elementList = query.getResultList();
        return elementList;
    }
    
    public List < Employee > getAllEmployees() {
    	TypedQuery < Employee > query = em.createQuery("FROM Employee e", Employee.class);
        List < Employee > elementList = query.getResultList();
        return elementList;
    }

    @Transactional
    public void addEmployee(NewEmployee newEmp) throws UsernameExists, PasswordsnotMatch {
        int check = checkUsernameExists(newEmp.getUsername());
        if (check == 0)
            throw new UsernameExists();
        check = checkPasswords(newEmp.getPassword(), newEmp.getMatchingPassword());
        if (check == 0)
            throw new PasswordsnotMatch();
        String bycrypt = generateByCrypt(newEmp.getPassword());
        String role = "ROLE_USER";
        int enabled = 1;

        Query query = em.createNativeQuery("INSERT INTO Employees " +
            "(username, firstname, lastname, enabled, role, password, text_password, gender, dateofjoin, experienced, location) " +
            "VALUES (:username, :firstname, :lastname, :enabled, :role, :password, :text_password, :gender, :dateofjoin, :experienced, :location)");
       // em.getTransaction().begin();
        query.setParameter("username", newEmp.getUsername());
        query.setParameter("firstname", newEmp.getFirstName());
        query.setParameter("lastname", newEmp.getLastName());
        query.setParameter("enabled", enabled);
        query.setParameter("role", role);
        query.setParameter("password", bycrypt);
        query.setParameter("text_password", newEmp.getPassword());
        query.setParameter("gender", newEmp.getGender());
        query.setParameter("dateofjoin", newEmp.getDateofjoin());
        query.setParameter("experienced", newEmp.getExperienced());
        query.setParameter("location", newEmp.getLocation());
        query.executeUpdate();
        //em.getTransaction().commit();
    }

    @Transactional
    public int enrollCertificate(String certificate, String username) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        //System.out.println(enrolledDate);

        Calendar c = Calendar.getInstance();

        c.setTime(new Date()); // Now use today date.

        c.add(Calendar.MONTH, 2);
        Date dateAsObjAfterMonths = c.getTime();
        //Date deadline = new Date()
        //System.out.println(deadline);

        String certificationstatus = "IN PROGRESS";

        Query query = em.createQuery("UPDATE Employee e SET e.certificationname = :certificationname ,  e.certificationstatus = :certificationstatus, e.enrolleddate = :enrolleddate, e.deadline = :deadline " +
            "WHERE e.username = :username");
        query.setParameter("certificationname", certificate);
        query.setParameter("certificationstatus", certificationstatus);
        query.setParameter("enrolleddate", today);
        query.setParameter("deadline", dateAsObjAfterMonths);
        query.setParameter("username", username);
        return query.executeUpdate();
    }
    
    @Transactional
    public int updateEmployee(Employee emp) {
    	int status = 0;
    	int datecheck = 1;
        Date today = new Date();
    	List<Employee> employee = getUserDetails(emp.getUsername());
    	if (employee.get(0).getEnrolleddate() == null && emp.getCertificationname() != null) {
    		datecheck = 0;
    	}
    	if (employee.get(0).getEnrolleddate() != null && emp.getCertificationname() == null) {
    		emp.setEnrolleddate(null);
    	}
    	if (employee.get(0).getEnrolleddate() != null && emp.getCertificationname() != null) {
    		if (!(employee.get(0).getCertificationname()).equals(emp.getCertificationname())) {
        		datecheck = 0;
    		}
    	}
    	if (emp.getCertificationname() != null) {
    		if ((emp.getCertificationstatus()).equals("COMPLETED")) {
    			if(emp.getCertifieddate() == null) {
    				status = 0;
    			}
    			else {
    				status = 1;
    				emp.setDeadline(null);
    			}
    		}
    		if ((emp.getCertificationstatus()).equals("IN PROGRESS") || (emp.getCertificationstatus()).equals("FAILED")) {
    			if(emp.getDeadline() == null) {
    				status = 0;
    			}
    			else {
    				status = 1;
    				emp.setCertifieddate(null);
    			}
    		}
    		if ((emp.getCertificationstatus()).equals("NOT ENROLLED")) {
    			status = 0;
    		}
    		
    	} else if (emp.getCertificationname() == null) {
    		if (!(emp.getCertificationstatus()).equals("NOT ENROLLED")) {
    			status = 0;
    		}
    		else {
    			status = 1;
    			emp.setCertifieddate(null);
    			emp.setDeadline(null);
    		}
    	}
    	
    	if (status == 1) {
    		Query query = em.createQuery("UPDATE Employee e SET e.certificationname = :certificationname ,  e.certificationstatus = :certificationstatus, e.enrolleddate = :enrolleddate, e.deadline = :deadline , e.certifieddate = :certifieddate " +
    	            "WHERE e.username = :username");
    	        query.setParameter("certificationname", emp.getCertificationname());
    	        query.setParameter("certificationstatus", emp.getCertificationstatus());
    	        if(datecheck == 0)
    	        	query.setParameter("enrolleddate", today);
    	        else
    	        	query.setParameter("enrolleddate", emp.getEnrolleddate());
    	        query.setParameter("deadline", emp.getDeadline());
    	        query.setParameter("certifieddate", emp.getCertifieddate());
    	        query.setParameter("username", emp.getUsername());
    	    status =  query.executeUpdate();
    	}
    	else if (status == 0 ) {
    		return status;
    	}
    	return status;
    }
    
    @Transactional
    public int deleteEmployee(String username) {
      
        Query query = em.createQuery("DELETE Employee e WHERE e.username = :username");
        query.setParameter("username", username);
        return query.executeUpdate();
    }

}