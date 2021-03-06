package zenmobile.mvc.controllers;

import zenmobile.beans.vo.LoginVO;
import zenmobile.beans.vo.UserVO;
import zenmobile.dao.UserDao;
import zenmobile.utils.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/")
public class DefaultController {

	@Autowired
	HttpServletRequest request;
	
	public UserDao userDao;
	
	@RequestMapping(method=RequestMethod.GET) 
	public String getUserLoginPage(Model model) { 
		//model.addAttribute("uservo", new UserVO()); 
	  HttpSession sess = request.getSession(false);
	  if(null == sess) {
		  sess = request.getSession(true);
	  }
	  Object logVO = sess.getAttribute(Constants.LOGGED_IN_USER);
	  if(logVO == null) {
		  model.addAttribute("loginvo", new LoginVO());
		  return "user/login";
	  }
	  else return "user/userMain"; 
	} 
	
	@RequestMapping(value="login",method=RequestMethod.POST) 
	public String submitLogin(LoginVO loginVO,BindingResult result) throws Exception { 
		if(userDao.loginUser(loginVO))  {
			HttpSession sess = request.getSession(true);
			sess.setAttribute(Constants.LOGGED_IN_USER, loginVO);
			return "user/userMain"; 
		}
		else {
			  return "user/login"; 
		}
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	} 	
	
	
}
