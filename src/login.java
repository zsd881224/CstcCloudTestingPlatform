import java.util.Date;
import java.util.Iterator;
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
import persistentClasses.Admin;
import persistentClasses.User;

public class login implements SessionAware, ServletRequestAware {
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

	public String Login() {

		String referrer = servletRequest.getHeader("referer");
		if (referrer == null) {
			sessionMap.put("info", "请从网站表单入口进入！");
			sessionMap.put("info_kind", "warning");
			return "error";
		}

		erase_info();
		String username = servletRequest.getParameter("user");
		String password = servletRequest.getParameter("pswd");
		if (username.length() < 6 || password.length() < 6) {
			sessionMap.put("info", "请确认账号密码格式正确！");
			sessionMap.put("info_kind", "warning");
			return "error";
		}

		if (servletRequest.getParameter("admin") != null) {
			Session session = sessionFactory.getCurrentSession();
			Transaction tx = session.beginTransaction();
			Query query = session
					.createQuery("from Admin where username=:username and password=password(:password)");
			query.setString("username", username);
			query.setString("password", password);
			List<?> admins = query.list();
			tx.commit();
			Iterator<?> it = admins.iterator();
			if (it.hasNext()) {
				Admin admin = (Admin) it.next();
				sessionMap.put("userid", admin.getId());
				sessionMap.put("username", admin.getUsername());
				sessionMap.put("isadmin", true);
				
				sessionMap.put("info", "登录成功！");
				sessionMap.put("info_kind", "info");
				return "login";
			} else {
				sessionMap.put("info", "用户名/密码错误或用户无管理权限！");
				sessionMap.put("info_kind", "warning");
				return "error";
			}
		} else if (servletRequest.getParameter("login") != null) {
			Session session = sessionFactory.getCurrentSession();
			Transaction tx = session.beginTransaction();
			Query query = session
					.createQuery("from User where username=:username and password=password(:password)");
			query.setString("username", username);
			query.setString("password", password);
			List<?> users = query.list();
			tx.commit();

			Iterator<?> it = users.iterator();
			if (it.hasNext()) {
				User user = (User) it.next();
				sessionMap.put("userid", user.getId());
				sessionMap.put("username", user.getUsername());
				sessionMap.put("nickname", user.getNickname());

				Date time = new Date();

				user.setLasttimeLoginTime(user.getThistimeLoginTime());
				user.setThistimeLoginTime(time.getTime() / 1000);

				session = sessionFactory.getCurrentSession();
				tx = session.beginTransaction();
				session.update(user);
				tx.commit();
				
				sessionMap.put("info", "登录成功！");
				sessionMap.put("info_kind", "info");
				return "login";
			} else {
				sessionMap.put("info", "用户名或密码错误！");
				sessionMap.put("info_kind", "warning");
				return "error";
			}
		} else {
			sessionMap.put("info", "指令错误！");
			sessionMap.put("info_kind", "warning");
			return "error";
		}
	}

	public String Logout() {
		erase_info();
		sessionMap.put("info", "登出成功！");
		sessionMap.put("info_kind", "info");
		return "logout";
	}

	public String Regist() {
		String referrer = servletRequest.getHeader("referer");
		if (referrer == null) {
			sessionMap.put("info", "请从网站表单入口进入！");
			sessionMap.put("info_kind", "warning");
			return "form_error";
		}

		erase_info();
		if (servletRequest.getParameter("user").length() < 6) {
			sessionMap.put("info", "用户名过短，请设置至少6位的用户名！");
			sessionMap.put("info_kind", "warning");
			return "form_error";
		}

		if (servletRequest.getParameter("user").length() > 20) {
			sessionMap.put("info", "用户名过长，请设置至多20位的用户名！");
			sessionMap.put("info_kind", "warning");
			return "form_error";
		}

		if (servletRequest.getParameter("pswd").length() < 6) {
			sessionMap.put("info", "密码过短，请设置至少6位的密码！");
			sessionMap.put("info_kind", "warning");
			return "form_error";
		}

		if (servletRequest.getParameter("pswd").length() > 20) {
			sessionMap.put("info", "密码过长，请设置至多20位的密码！");
			sessionMap.put("info_kind", "warning");
			return "form_error";
		}

		Pattern pattern = Pattern.compile("[0-9a-zA-Z_]{6,20}");
		Matcher matcher = pattern.matcher(servletRequest.getParameter("user"));

		if (!matcher.matches()) {
			sessionMap.put("info", "存在非法字符！（用户名只能由字母、数字和下划线组成）");
			sessionMap.put("info_kind", "warning");
			return "form_error";
		}

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User where username=:username");
		query.setString("username", servletRequest.getParameter("user"));
		List<?> users = query.list();
		tx.commit();

		Iterator<?> it = users.iterator();
		if (it.hasNext()) {
			sessionMap.put("info", "该用户名已存在！");
			sessionMap.put("info_kind", "warning");
			return "form_error";
		}

		Date time = new Date();

		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session
				.createSQLQuery("insert into user(username,password,vmlimit,"
						+ "regist_time,thistime_login_time,lasttime_login_time)"
						+ " values(:user,password(:pswd),3," + time.getTime() / 1000 + ","
						+ time.getTime() / 1000 + "," + time.getTime() / 1000
						+ ");");
		query.setString("user",servletRequest.getParameter("user"));
		query.setString("pswd",servletRequest.getParameter("pswd"));
		query.executeUpdate();
		tx.commit();

		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session.createQuery("from User where username=:username");
		query.setString("username", servletRequest.getParameter("user"));
		User user = (User) query.uniqueResult();
		tx.commit();

		sessionMap.put("userid", user.getId());
		sessionMap.put("username", user.getUsername());
		sessionMap.put("info", "注册成功！");
		sessionMap.put("info_kind", "info");
		return "success";
	}

	private void erase_info() {
		sessionMap.remove("userid");
		sessionMap.remove("username");
		sessionMap.remove("nickname");
		sessionMap.remove("isadmin");
	}

	public String checkLogin() {
		if (sessionMap.get("isadmin") != null) {
			return "admin";
		} else if (sessionMap.get("userid") != null) {
			return "user";
		} else {
			return "success";
		}
	}
}
