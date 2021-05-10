package com.sigma;

import java.security.SecureRandom;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

    @Autowired
    private EmployeeModel empModel;

    @Autowired
    private CertificationsModel cModel;

    @RequestMapping(value = {
    		"",
        "/",
        "/home"
    }, method = RequestMethod.GET)
    public ModelAndView welcome(Authentication authentication) {
        List < Employee > emp = empModel.getUserDetails(authentication.getName());
        //System.out.println(emp.get(0).getCertificationname());
        ModelAndView mv = new ModelAndView();
        mv.addObject("employee", emp.get(0));
        mv.setViewName("home");
        return mv;
    }
    
    @RequestMapping(value = "/enroll", method = RequestMethod.GET)
    public ModelAndView enrollNew(Authentication authentication) {
        List < Employee > emp = empModel.getUserDetails(authentication.getName());
        ModelAndView mv = new ModelAndView();
        //System.out.println(emp.get(0).getCertificationstatus());
        if ((emp.get(0).getCertificationstatus()).equals("NOT ENROLLED")) {
            Enroll enroll = new Enroll();
            List < String > certifications = cModel.getAllCertifications();
            mv.addObject("allCertifications", certifications);
            mv.addObject("enroll", enroll);
            mv.setViewName("enroll");
            return mv;
        } else {
            //System.out.println(emp.get(0).getCertificationstatus());
            mv.setViewName("access-denied");
            return mv;
        }
    }

    @RequestMapping(value = "/enroll", method = RequestMethod.POST)
    public String enrolled(Authentication authentication, @Valid Enroll enroll, BindingResult result) {
        System.out.println(enroll.getCertificationname());
        if (result.hasErrors()) {
            return "enroll";
        }

        int enrolled = empModel.enrollCertificate(enroll.getCertificationname(), authentication.getName());

        if (enrolled > 0) {
            return "redirect:home";
        } else {
            return "enroll";
        }
    }
    
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ModelAndView checkReport(Authentication authentication) {
        List < Employee > emp = empModel.getUserDetails(authentication.getName());
        ModelAndView mv = new ModelAndView();
        if ((emp.get(0).getRole()).equals("ROLE_ADMIN")) {
            List <Employee> employeeReports = empModel.getAllEmployees();
            mv.addObject("report", employeeReports);
            mv.setViewName("reports");
            return mv;
        } else {
            mv.setViewName("access-denied");
            return mv;
        }
    }

    @RequestMapping(value = "/aboutus", method = RequestMethod.GET)
    public String aboutUs(Authentication authentication) {
    	return "aboutus";    
    }

    @RequestMapping(value = "/contactus", method = RequestMethod.GET)
    public String contactUs(Authentication authentication) {
        return "contactus";
    }
    
    @RequestMapping(value = "/edit/{username}", method = RequestMethod.GET)
    public ModelAndView getEditEmployee(@PathVariable(name = "username") String username) {
    	//System.out.println("edit: "+ username);
    	List < Employee > emp = empModel.getUserDetails(username);
    	 ModelAndView mv = new ModelAndView();
    	 List < String > certifications = cModel.getAllCertifications();
         mv.addObject("allCertifications", certifications);
         List < String > status = cModel.getAllStatus();
         mv.addObject("allStatus", status);
         mv.addObject("employee", emp.get(0));
         mv.setViewName("edit_emp");
         return mv;
    }
    
    @RequestMapping(value = "/edit-profile/{username}", method = RequestMethod.GET)
    public ModelAndView getEditProfile(@PathVariable(name = "username") String username) {
    	//System.out.println("edit: "+ username);
    	List < Employee > emp = empModel.getUserDetails(username);
    	 ModelAndView mv = new ModelAndView();
    	 emp.get(0).setText_password(null);
    	 mv.addObject("employee", emp.get(0));
         mv.setViewName("edit_profile");
         return mv;
    }
    
  @RequestMapping(value = "/home", method = RequestMethod.POST)
   public ModelAndView editProfile(@Valid Employee employee, Authentication authentication ) {
  	//System.out.println("edit: "+ username);
   	List < Employee > emp = empModel.getUserDetails(authentication.getName());
	 ModelAndView mv = new ModelAndView();
   	 emp.get(0).setText_password(null);
    	 mv.addObject("employee", emp.get(0));
       mv.setViewName("edit_profile");
        return mv;
   }
  
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView postEditEmployee(@Valid Employee employee, BindingResult result, Authentication authentication) {
    	
    	//System.out.println(authentication.getName());
    	List < Employee > emp = empModel.getUserDetails(authentication.getName());
        ModelAndView mv = new ModelAndView();
 
        int updated = empModel.updateEmployee(employee);

        if (updated > 0) {
            mv.addObject("employee", emp.get(0));
        	mv.addObject("success", "Updated Employee details!!");
        	mv.setViewName("home");
            return mv;
        } else {
            mv.addObject("employee", emp.get(0));
        	mv.addObject("error", "Error updating the employee details!!");
        	mv.setViewName("home");
            return mv;
        }
    }
    
    @RequestMapping(value = "/delete/{username}")
    public ModelAndView getDeleteEmployee(@PathVariable(name = "username") String username, Authentication authentication) {
    	System.out.println("delete: "+ username);
    	List < Employee > emp = empModel.getUserDetails(authentication.getName());
        ModelAndView mv = new ModelAndView();
    	int deleted = empModel.deleteEmployee(username);
    	if (deleted > 0) {
            mv.addObject("employee", emp.get(0));
        	mv.addObject("success", "Deleted Employee from Server completely!!");
        	mv.setViewName("home");
            return mv;
        } else {
            mv.addObject("employee", emp.get(0));
        	mv.addObject("error", "Error Deleting the Employee!!");
        	mv.setViewName("home");
            return mv;
        }
    }
}