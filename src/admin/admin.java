package admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.SessionFactory;

public class admin implements SessionAware, ServletRequestAware {
	private HttpServletRequest servletRequest;
	private Map<String, Object> sessionMap;
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public void setSession(Map<String, Object> map) {
		this.sessionMap = map;
	}
	
	public String check_admin() {
		if (sessionMap.get("username") == null||!(Boolean)(sessionMap.get("isadmin"))) {
			sessionMap.put("info", "请登录！");
			sessionMap.put("info_kind", "warning");
			return "needlogin";
		} else
			return "success";
	}
	
	public String switchToUserView() {
		if (check_admin().equals("needlogin"))
			return "needlogin";
		
		return "success";
	}
}
