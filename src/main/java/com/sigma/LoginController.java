package com.sigma;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@Autowired
	private EmployeeModel empModel;
	
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public ModelAndView register() {
		NewEmployee newEmp = new NewEmployee();
		ModelAndView mv = new ModelAndView();
		List <Character> genderTypes = new ArrayList<Character>();
		genderTypes.add('M');
		genderTypes.add('F');
		List <Character> experienced = new ArrayList<Character>();
		experienced.add('Y');
		experienced.add('N');
		mv.addObject("newEmp", newEmp);
		mv.addObject("genderTypes", genderTypes);
		mv.addObject("experienced", experienced);
		mv.setViewName("register");
		return mv;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerPost(Authentication authentication, @Valid NewEmployee newEmp, BindingResult result)
				throws UsernameExists, PasswordsnotMatch{
		ModelAndView mv = new ModelAndView();
		NewEmployee newEmp2 = new NewEmployee();
		List <Character> genderTypes = new ArrayList<Character>();
		genderTypes.add('M');
		genderTypes.add('F');
		List <Character> experienced = new ArrayList<Character>();
		experienced.add('Y');
		experienced.add('N');
        //System.out.println(newEmp.getFirstName());
        //System.out.println(newEmp.getGender());
        if (result.hasErrors()) {
        	mv.addObject("newEmp", newEmp2);
        	mv.addObject("genderTypes", genderTypes);
    		mv.addObject("experienced", experienced);
        	mv.setViewName("register");
            return mv;
        }
        try {
        	empModel.addEmployee(newEmp);
        }
        catch(UsernameExists u) {
        	mv.addObject("newEmp", newEmp2);
        	mv.addObject("genderTypes", genderTypes);
    		mv.addObject("experienced", experienced);
        	mv.addObject("errorRegister", "Username Already Exists!!!");
        	mv.setViewName("register");
        	return mv;
        }
        catch(PasswordsnotMatch p) {
        	mv.addObject("newEmp", newEmp2);
        	mv.addObject("genderTypes", genderTypes);
    		mv.addObject("experienced", experienced);
        	mv.addObject("errorRegister", "Passwords Do Not Match!!!");
        	mv.setViewName("register");
        	return mv;
        }
        
        mv.setViewName("login");
		return mv;
    }
}
