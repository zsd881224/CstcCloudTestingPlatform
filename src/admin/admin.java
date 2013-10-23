package admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

import Constant.Constant;

import persistentClasses.Config;
import persistentClasses.Message;
import persistentClasses.SystemVapp;
import persistentClasses.TestVapp;

public class admin implements SessionAware, ServletRequestAware {
	private HttpServletRequest servletRequest;
	private Map<String, Object> sessionMap;
	private String organization;
	private String vdc;
	private LinkedHashMap<String, LinkedHashMap<String, String>> testVappMap;
	private LinkedHashMap<String, LinkedHashMap<String, String>> systemVappMap;
	private LinkedHashMap<String, LinkedHashMap<String, String>> messageMap;
	private int maxPage;
	private int curPage;

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
		if (sessionMap.get("username") == null
				|| !(Boolean) (sessionMap.get("isadmin"))) {
			sessionMap.put("info", "请登录！");
			sessionMap.put("info_kind", "warning");
			return "needlogin";
		} else
			return "success";
	}

	public String get_basic_config() {
		if (check_admin().equals("needlogin")) {
			return "needlogin";
		}

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from Config");
		List<?> configs = query.list();
		tx.commit();
		Iterator<?> it = configs.iterator();

		while (it.hasNext()) {
			Config config = (Config) it.next();
			if (config.getName().equals("organization"))
				organization = config.getValue();
			else if (config.getName().equals("vdc"))
				vdc = config.getValue();
		}

		return "success";
	}

	public String getOrganization() {
		return organization;
	}

	public String getVdc() {
		return vdc;
	}

	public String vmManagement() {
		if (check_admin().equals("needlogin")) {
			return "needlogin";
		}

		testVappMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		systemVappMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from TestVapp");
		List<?> testVapps = query.list();
		tx.commit();
		Iterator<?> it = testVapps.iterator();
		while (it.hasNext()) {
			TestVapp testVapp = (TestVapp) it.next();
			testVappMap.put(testVapp.getVappname(),
					new LinkedHashMap<String, String>());
			testVappMap.get(testVapp.getVappname()).put("vappname",
					testVapp.getVappname());
			testVappMap.get(testVapp.getVappname()).put("userid",
					String.valueOf(testVapp.getUser().getId()));
			testVappMap.get(testVapp.getVappname()).put("username",
					testVapp.getUser().getUsername());
		}

		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session.createQuery("from SystemVapp");
		List<?> systemVapps = query.list();
		tx.commit();
		it = systemVapps.iterator();
		while (it.hasNext()) {
			SystemVapp systemVapp = (SystemVapp) it.next();
			systemVappMap.put(systemVapp.getVappname(),
					new LinkedHashMap<String, String>());
			systemVappMap.get(systemVapp.getVappname()).put("vappname",
					systemVapp.getVappname());
			systemVappMap.get(systemVapp.getVappname()).put("userid",
					String.valueOf(systemVapp.getUser().getId()));
			systemVappMap.get(systemVapp.getVappname()).put("username",
					systemVapp.getUser().getUsername());
		}

		return "success";
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> getTestVappMap() {
		return testVappMap;
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> getSystemVappMap() {
		return systemVappMap;
	}
	
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		} else
			return true;
	}

	public String showMessage() {
		if (check_admin().equals("needlogin")) {
			return "needlogin";
		}

		if (servletRequest.getParameter("page") == null
				|| servletRequest.getParameter("page") == ""
				|| !isNumeric(servletRequest.getParameter("page"))) {
			curPage = 1;
		} else {
			curPage = Integer.parseInt(servletRequest.getParameter("page"));
		}

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createQuery("select count(*) from Message order by time desc");
		float count_all = ((Long) query.uniqueResult()).floatValue();
		tx.commit();

		if (curPage <= 0)
			curPage = 1;
		maxPage = (int) Math.ceil(count_all / Constant.message_per_page);
		if (curPage > maxPage)
			curPage = maxPage;

		messageMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();

		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session
				.createQuery("from Message order by time desc");
		query.setFirstResult((curPage - 1) * Constant.message_per_page);
		query.setMaxResults(Constant.message_per_page);
		List<?> messages = query.list();
		tx.commit();

		Iterator<?> it = messages.iterator();
		while (it.hasNext()) {
			Message message = (Message) it.next();
			messageMap.put(String.valueOf(message.getId()),
					new LinkedHashMap<String, String>());
			messageMap.get(String.valueOf(message.getId())).put("username",
					message.getUser().getUsername());
			messageMap.get(String.valueOf(message.getId())).put("content",
					message.getContent());
			messageMap.get(String.valueOf(message.getId())).put(
					"time",
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date(message.getTime() * 1000)));
			messageMap.get(String.valueOf(message.getId())).put("reply",
					message.getReply());
			if (message.getReply() != null) {
				messageMap.get(String.valueOf(message.getId())).put(
						"replyTime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date(message.getReplyTime()
										.longValue() * 1000)));
			} else
				messageMap.get(String.valueOf(message.getId())).put(
						"replyTime", "null");
		}

		return "success";
	}
	
	public LinkedHashMap<String, LinkedHashMap<String, String>> getMessageMap() {
		return this.messageMap;
	}

	public int getMaxPage() {
		return this.maxPage;
	}

	public int getCurPage() {
		return this.curPage;
	}

	public String userRights() {
		if (check_admin().equals("needlogin")) {
			return "needlogin";
		}

		return "success";
	}
}
