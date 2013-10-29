package user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import persistentClasses.User;

public class userInfo implements SessionAware, ServletRequestAware {
	private HttpServletRequest servletRequest;
	private Map<String, Object> sessionMap;
	private String username;
	private int vmlimit;
	private String nickname;
	private String registTime;
	private String lasttimeLoginTime;
	private String email;
	private Integer qq;
	private String address;
	private Integer zip;

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

	public String check_user() {
		if (sessionMap.get("username") == null) {
			sessionMap.put("info", "请登录！");
			sessionMap.put("info_kind", "warning");
			return "needlogin";
		} else
			return "success";
	}
	
	private int vappCount;

	public String getBasicInfo() {
		if (check_user().equals("needlogin"))
			return "needlogin";

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User where id=:id");
		query.setInteger("id", (Integer) sessionMap.get("userid"));
		User user = (User) query.uniqueResult();
		tx.commit();

		username = user.getUsername();
		vmlimit = user.getVmlimit();
		nickname = user.getNickname();

		registTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date(user.getRegistTime() * 1000));
		lasttimeLoginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date(user.getLasttimeLoginTime() * 1000));
		
		email = user.getEmail();
		qq = user.getQq();
		address = user.getAddress();
		zip = user.getZip();
		
		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session.createQuery("select count(*) from TestVapp where user.id=:userid");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		vappCount = ((Long)query.uniqueResult()).intValue();
		tx.commit();
		
		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session.createQuery("select count(*) from SystemVapp where user.id=:userid");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		vappCount += ((Long)query.uniqueResult()).intValue();
		tx.commit();

		return "success";
	}
	
	public int getVappCount() {
		return this.vappCount;
	}

	public String getUsername() {
		return username;
	}

	public int getVmlimit() {
		return vmlimit;
	}

	public String getNickname() {
		return nickname;
	}

	public String getRegistTime() {
		return registTime;
	}

	public String getLasttimeLoginTime() {
		return lasttimeLoginTime;
	}
	
	public String getEmail() {
		return email;
	}
	
	public Integer getQq() {
		return qq;
	}
	
	public String getAddress() {
		return address;
	}
	
	public Integer getZip() {
		return zip;
	}

	public String changePassword() {
		if (check_user().equals("needlogin"))
			return "needlogin";

		String oldPassword = servletRequest.getParameter("old");
		String newPassword = servletRequest.getParameter("new");
		String newPassword2 = servletRequest.getParameter("new2");

		if (newPassword.length() < 6) {
			sessionMap.put("info", "密码过短，请设置至少6位的密码！");
			sessionMap.put("info_kind", "warning");
			return "form_error";
		}

		if (newPassword.length() > 20) {
			sessionMap.put("info", "密码过长，请设置至多20位的密码！");
			sessionMap.put("info_kind", "warning");
			return "form_error";
		}

		if (!newPassword.equals(newPassword2)) {
			sessionMap.put("info", "两次新密码输入不一致！");
			sessionMap.put("info_kind", "warning");
			return "form_error";
		}

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createQuery("from User where id=:id and password=password(:password)");
		query.setInteger("id", (Integer) sessionMap.get("userid"));
		query.setString("password", oldPassword);
		List<?> users = query.list();
		tx.commit();

		if (users.isEmpty()) {
			sessionMap.put("info", "旧密码不正确！");
			sessionMap.put("info_kind", "warning");
			return "form_error";
		}
		
		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session
				.createSQLQuery("update user set password=password('"
						+ newPassword
						+ "') where id="
						+ (Integer) sessionMap.get("userid")
						+ ";");
		query.executeUpdate();
		tx.commit();

		sessionMap.put("info", "密码修改成功！");
		sessionMap.put("info_kind", "info");
		return "success";
	}
	
	public String editInfo() {
		if (check_user().equals("needlogin"))
			return "needlogin";

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User where id=:id");
		query.setInteger("id", (Integer) sessionMap.get("userid"));
		User user = (User) query.uniqueResult();
		tx.commit();

		username = user.getUsername();
		nickname = user.getNickname();
		email = user.getEmail();
		qq = user.getQq();
		address = user.getAddress();
		zip = user.getZip();

		return "success";
	}
	
	public boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches() )
		{
			return false;
		}
		else return true;
	}
	
	public boolean isNickname(String str) {
		Pattern pattern = Pattern.compile("^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[a-zA-Z0-9])*$");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches() )
		{
			return false;
		}
		else return true;
	}
	
	public String saveInfo() {
		if (check_user().equals("needlogin"))
			return "needlogin";
		
		if(servletRequest.getParameter("qq")!=null&&!isNumeric(servletRequest.getParameter("qq"))) {
			sessionMap.put("info", "QQ号必须为数字！");
			sessionMap.put("info_kind", "warning");
			return "error";
		}
		
		if(servletRequest.getParameter("zip")!=null&&!isNumeric(servletRequest.getParameter("zip"))) {
			sessionMap.put("info", "邮编必须为数字！");
			sessionMap.put("info_kind", "warning");
			return "error";
		}
		
		if(servletRequest.getParameter("nickname")!=null&&!isNickname(servletRequest.getParameter("nickname"))) {
			sessionMap.put("info", "非法昵称！请勿包含空格等非法字符");
			sessionMap.put("info_kind", "warning");
			return "error";
		}
		
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User where id=:id");
		query.setInteger("id", (Integer) sessionMap.get("userid"));
		User user = (User) query.uniqueResult();
		tx.commit();
		
		user.setNickname(servletRequest.getParameter("nickname"));
		user.setEmail(servletRequest.getParameter("email"));
		user.setQq(Integer.parseInt(servletRequest.getParameter("qq")));
		user.setAddress(servletRequest.getParameter("address"));
		user.setZip(Integer.parseInt(servletRequest.getParameter("zip")));
		
		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		session.update(user);
		tx.commit();
		
		sessionMap.put("nickname", servletRequest.getParameter("nickname"));
		
		return "success";
	}
}