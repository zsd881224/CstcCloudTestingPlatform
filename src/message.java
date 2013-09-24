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

import persistentClasses.Message;
import persistentClasses.User;

import Constant.Constant;

public class message implements SessionAware, ServletRequestAware {
	private HttpServletRequest servletRequest;
	private Map<String, Object> sessionMap;
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

	public String check_user() {
		if (sessionMap.get("username") == null) {
			sessionMap.put("info", "请登录！");
			sessionMap.put("info_kind", "warning");
			return "needlogin";
		} else
			return "success";
	}

	public String leaveMessage() {
		if (check_user().equals("needlogin"))
			return "needlogin";
		
		if(servletRequest.getParameter("content").length()>150) {
			sessionMap.put("info", "请勿超过150字符！");
			sessionMap.put("info_kind", "warning");
			return "error";
		}

		Date time = new Date();

		User user = null;

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User where id=:userid");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		List<?> users = query.list();
		tx.commit();
		Iterator<?> it = users.iterator();
		if (!it.hasNext()) {
			sessionMap.put("info", "未找到用户！");
			sessionMap.put("info_kind", "warning");
			return "error";
		} else {
			user = (User) it.next();
		}

		Message message = new Message();
		message.setContent(servletRequest.getParameter("content"));
		message.setTime(time.getTime() / 1000);
		message.setUser(user);

		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		session.save(message);
		tx.commit();

		sessionMap.put("info", "留言成功！");
		sessionMap.put("info_kind", "info");
		return "success";
	}

	public String listMessage() {
		if (check_user().equals("needlogin"))
			return "needlogin";
		
		if(servletRequest.getParameter("page")==null||servletRequest.getParameter("page")==""||!isNumeric(servletRequest.getParameter("page"))) {
			curPage = 1;
		}
		else {
			curPage = Integer.parseInt(servletRequest.getParameter("page"));
		}
		
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("select count(*) from Message where userid=:userid order by time desc");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		float count_all = ((Long)query.uniqueResult()).floatValue();
		tx.commit();
		
		if(curPage<=0) curPage=1;
		maxPage = (int) Math.ceil(count_all/Constant.message_per_page);
		if(curPage>maxPage) curPage=maxPage;
		
		messageMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		
		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session.createQuery("from Message where userid=:userid order by time desc");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		query.setFirstResult((curPage-1)*Constant.message_per_page);
		query.setMaxResults(Constant.message_per_page);
		List<?> messages = query.list();
		tx.commit();

		Iterator<?> it = messages.iterator();
		while (it.hasNext()) {
			Message message = (Message) it.next();
			messageMap.put(String.valueOf(message.getId()),
					new LinkedHashMap<String, String>());
			messageMap.get(String.valueOf(message.getId())).put("content",
					message.getContent());
			messageMap.get(String.valueOf(message.getId())).put(
					"time",
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date(message.getTime() * 1000)));
			messageMap.get(String.valueOf(message.getId())).put("reply",
					message.getReply());
			if (message.getReply() != null) {
				messageMap
						.get(String.valueOf(message.getId()))
						.put("replyTime",
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(new Date(
												message.getReplyTime().longValue() * 1000)));
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
	
	public boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches() )
		{
			return false;
		}
		else return true;
	}
	
	public String deleteMessage() {
		if (check_user().equals("needlogin"))
			return "needlogin";
		
		if(servletRequest.getParameter("id")==null||servletRequest.getParameter("id")==""||!isNumeric(servletRequest.getParameter("id"))) {
			sessionMap.put("info", "留言ID必须存在且为数字！");
			sessionMap.put("info_kind", "warning");
			return "error";
		}
		
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("select count(*) from Message where userid=:userid and id=:id");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		query.setInteger("id", Integer.valueOf(servletRequest.getParameter("id")));
		int isExist = ((Long)query.uniqueResult()).intValue();
		tx.commit();
		
		if(isExist==0) {
			sessionMap.put("info", "非法操作！");
			sessionMap.put("info_kind", "warning");
			return "error";
		}
		
		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		Message message = (Message) session.load(Message.class, Integer.valueOf(servletRequest.getParameter("id")));
		session.delete(message);
		tx.commit();
		
		//计算当前页为第几页
		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session.createQuery("select count(*) from Message where userid=:userid and id>:id");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		query.setInteger("id", Integer.valueOf(servletRequest.getParameter("id")));
		float count = ((Long)query.uniqueResult()).floatValue();
		tx.commit();
		
		curPage = (int) Math.ceil(count/Constant.message_per_page);
		
		sessionMap.put("info", "删除成功！");
		sessionMap.put("info_kind", "info");
		return "success";
	}
}
