package user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpException;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import persistentClasses.SystemTemplate;
import persistentClasses.User;
import persistentClasses.SystemVapp;

import CstcVCloudAPI.CstcVCloudAPI;

import com.vmware.vcloud.sdk.VCloudException;
import com.vmware.vcloud.sdk.VM;

public class templateService implements SessionAware, ServletRequestAware {
	private int maxDevice;
	private int haveDevice;
	private HttpServletRequest servletRequest;
	private Map<String, Object> sessionMap;
	private ArrayList<String> templateNameSet;
	private LinkedHashMap<String, LinkedHashMap<String, String>> vappListMap;
	private LinkedHashMap<String, LinkedHashMap<String, String>> vmListMap;
	private LinkedHashMap<String, String> vmDetail;
	private LinkedHashMap<String, String> consoleKey;

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

	public String index() throws KeyManagementException,
			UnrecoverableKeyException, NoSuchAlgorithmException,
			KeyStoreException, VCloudException, HttpException, IOException {
		return check_user();
	}

	public String listSystemTemplate() throws KeyManagementException,
			UnrecoverableKeyException, NoSuchAlgorithmException,
			KeyStoreException {
		if (check_user().equals("needlogin"))
			return "needlogin";

		templateNameSet = new ArrayList<String>();

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from SystemTemplate");
		List<?> templates = query.list();
		tx.commit();
		Iterator<?> it = templates.iterator();
		while (it.hasNext()) {
			SystemTemplate systemTemplate = (SystemTemplate) it.next();
			templateNameSet.add(systemTemplate.getTempname());
		}

		return "success";
	}

	public ArrayList<String> getTemplateNameSet() {
		return templateNameSet;
	}
	
	public String getBasicInfo() {
		if (check_user().equals("needlogin"))
			return "needlogin";
		
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User where id=:userid");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		User user = (User)query.uniqueResult();
		tx.commit();
		maxDevice = user.getVmlimit();
		
		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session.createQuery("from TestVapp where user.id=:userid");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		List<?> vapps = query.list();
		tx.commit();
		haveDevice = vapps.size();
		
		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session.createQuery("from SystemVapp where user.id=:userid");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		vapps = query.list();
		tx.commit();
		haveDevice += vapps.size();
		
		return "success";
	}
	
	public int getMaxDevice() {
		return maxDevice;
	}
	
	public int getHaveDevice() {
		return haveDevice;
	}

	public String listApp() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		if (check_user().equals("needlogin"))
			return "needlogin";

		vappListMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from SystemVapp where user.id=:userid");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		List<?> vapps = query.list();
		tx.commit();
		Iterator<?> it = vapps.iterator();
		while (it.hasNext()) {
			SystemVapp vapp = (SystemVapp) it.next();
			vappListMap.put(vapp.getVappname(), new LinkedHashMap<String, String>());
			vappListMap.get(vapp.getVappname()).put("vappname",
					vapp.getVappname());
			vappListMap.get(vapp.getVappname()).put(
					"time",
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date(vapp.getTime() * 1000)));
		}

		return "success";
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> getVappListMap() {
		return vappListMap;
	}

	public String releaseResource() throws KeyManagementException,
			UnrecoverableKeyException, NoSuchAlgorithmException,
			KeyStoreException, VCloudException, HttpException, IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		if (check_user().equals("needlogin")) {
			return "needlogin";
		}

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		SystemVapp vapp = null;
		Query query = session
				.createQuery("from SystemVapp where vappname=:vappname and user.id=:userid");
		query.setString("vappname", servletRequest.getParameter("vapp"));
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		List<?> vapps = query.list();
		tx.commit();
		Iterator<?> it = vapps.iterator();
		if (it.hasNext()) {
			vapp = (SystemVapp) it.next();
		} else {
			sessionMap.put("info", "无法获取相应VM！");
			sessionMap.put("info_kind", "warning");
			return "fail";
		}

		if (!CstcVCloudAPI.deleteVapp(vapp.getOrgname(), vapp.getVdcname(),
				vapp.getNameinvcloud())) {
			sessionMap.put("info", "删除失败！（请确认虚拟设备中所有虚拟机为关闭状态）");
			sessionMap.put("info_kind", "warning");
			return "fail";
		}

		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		vapp = (SystemVapp) session.load(SystemVapp.class, vapp.getVappid());
		session.delete(vapp);
		tx.commit();

		sessionMap.put("info", "释放资源成功！");
		sessionMap.put("info_kind", "info");
		return "success";
	}

	public String listVM() throws KeyManagementException,
			UnrecoverableKeyException, NoSuchAlgorithmException,
			KeyStoreException, VCloudException, HttpException, IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		if (check_user().equals("needlogin"))
			return "needlogin";

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createQuery("from SystemVapp where vappname=:vappname and user.id=:userid");
		query.setString("vappname", servletRequest.getParameter("vapp"));
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		List<?> vapps = query.list();
		tx.commit();
		Iterator<?> it = vapps.iterator();
		SystemVapp vapp;
		if (it.hasNext()) {
			vapp = (SystemVapp) it.next();
		} else {
			sessionMap.put("info", "无法获取相应vApp！");
			sessionMap.put("info_kind", "warning");
			return "fail";
		}

		Collection<VM> vmsCollection = CstcVCloudAPI.listVM(vapp.getOrgname(),
				vapp.getVdcname(), vapp.getNameinvcloud());
		vmListMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		for (VM vm : vmsCollection) {
			vmListMap.put(vm.getReference().getName(),
					new LinkedHashMap<String, String>());
			vmListMap.get(vm.getReference().getName()).put("vmname",
					vm.getReference().getName());
			vmListMap.get(vm.getReference().getName()).put("vmid",
					vm.getReference().getId());
			vmListMap.get(vm.getReference().getName()).put("vmstatus",
					vm.getVMStatus().toString());
		}

		return "success";
	}

	public LinkedHashMap<String, LinkedHashMap<String, String>> getVmListMap() {
		return vmListMap;
	}

	public String changeState() throws KeyManagementException,
			UnrecoverableKeyException, NoSuchAlgorithmException,
			KeyStoreException, VCloudException, HttpException, IOException,
			SQLException, InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		if (check_user().equals("needlogin")) {
			return "needlogin";
		}

		String vappname = CstcVCloudAPI.getVmParent(servletRequest
				.getParameter("vmid"));

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createQuery("from SystemVapp where nameinvcloud=:nameinvcloud and user.id=:userid");
		query.setString("nameinvcloud", vappname);
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		List<?> vapps = query.list();
		tx.commit();
		Iterator<?> it = vapps.iterator();
		if (!it.hasNext()) {
			sessionMap.put("info", "未找到相应虚拟设备！");
			sessionMap.put("info_kind", "warning");
			return "fail";
		}

		if (!CstcVCloudAPI.sendOrder(servletRequest.getParameter("vmid"),
				servletRequest.getParameter("newstate"))) {
			sessionMap.put("info", "发送命令失败！");
			sessionMap.put("info_kind", "warning");
			return "fail";
		}

		SystemVapp vapp = (SystemVapp) it.next();

		Collection<VM> vmsCollection = CstcVCloudAPI.listVM(vapp.getOrgname(),
				vapp.getVdcname(), vapp.getNameinvcloud());
		vmListMap = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		for (VM vm : vmsCollection) {
			vmListMap.put(vm.getReference().getName(),
					new LinkedHashMap<String, String>());
			vmListMap.get(vm.getReference().getName()).put("vmname",
					vm.getReference().getName());
			vmListMap.get(vm.getReference().getName()).put("vmid",
					vm.getReference().getId());
			vmListMap.get(vm.getReference().getName()).put("vmstatus",
					vm.getVMStatus().toString());
		}

		return "success";
	}

	public String showVMDetail() throws KeyManagementException,
			UnrecoverableKeyException, NoSuchAlgorithmException,
			KeyStoreException, VCloudException, HttpException, IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		if (check_user().equals("needlogin"))
			return "needlogin";

		String vappname = CstcVCloudAPI.getVmParent(servletRequest
				.getParameter("vmid"));

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session
				.createQuery("from SystemVapp where nameinvcloud=:nameinvcloud and user.id=:userid");
		query.setString("nameinvcloud", vappname);
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		List<?> vapps = query.list();
		tx.commit();
		Iterator<?> it = vapps.iterator();
		if (!it.hasNext()) {
			sessionMap.put("info", "无法获取相应VM！");
			sessionMap.put("info_kind", "warning");
			return "fail";
		}

		vmDetail = new LinkedHashMap<String, String>();

		if (!CstcVCloudAPI.getVMDetail(servletRequest.getParameter("vmid"),
				vmDetail)) {
			sessionMap.put("info", "无法获取相应VM详细信息！");
			sessionMap.put("info_kind", "warning");
			return "fail";
		}

		return "success";
	}

	public HashMap<String, String> getVmDetail() {
		return vmDetail;
	}

	public String createVapp() throws KeyManagementException,
			UnrecoverableKeyException, NoSuchAlgorithmException,
			KeyStoreException, VCloudException, HttpException, IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {
		if (check_user().equals("needlogin")) {
			return "needlogin";
		}

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("select count(*) from TestVapp where user.id=:userid");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		int vapp_count = ((Long)query.uniqueResult()).intValue();
		tx.commit();
		
		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session.createQuery("select count(*) from SystemVapp where user.id=:userid");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		vapp_count += ((Long)query.uniqueResult()).intValue();
		tx.commit();

		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session.createQuery("from User where id=:id");
		query.setInteger("id", (Integer) sessionMap.get("userid"));
		User user = (User)query.uniqueResult();
		tx.commit();
		
		if (user.getVmlimit() <= vapp_count) {
			sessionMap.put("info", "已达到最大可拥有vm数量！");
			sessionMap.put("info_kind", "warning");
			return "fail";
		}
		
		if (servletRequest.getParameter("name") == null||servletRequest.getParameter("name") == "") {
			sessionMap.put("info", "非法设备名！");
			sessionMap.put("info_kind", "warning");
			return "fail";
		}

		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		query = session
				.createQuery("from SystemVapp where user.id=:userid and vappname=:vappname");
		query.setInteger("userid", (Integer) sessionMap.get("userid"));
		query.setString("vappname", servletRequest.getParameter("name"));
		List<?> vapps = query.list();
		tx.commit();
		Iterator<?> it = vapps.iterator();
		if (it.hasNext()) {
			sessionMap.put("info", "已有同名VM存在！");
			sessionMap.put("info_kind", "warning");
			return "reset";
		}

		Date time = new Date();
		String name_time = new SimpleDateFormat("yyyyMMddHHmmss").format(time);
		String vappName = sessionMap.get("username") + "_" + name_time + "_"
				+ (int) (Math.random() * 100000);

		if (!CstcVCloudAPI.createVapp(Constant.Constant.default_org,Constant.Constant.default_vdc,servletRequest.getParameter("templatename"),vappName)) {
			sessionMap.put("info", "无法新建VM！");
			sessionMap.put("info_kind", "warning");
			return "fail";
		}

		SystemVapp vapp = new SystemVapp();
		vapp.setOrgname("cstc");
		vapp.setVdcname("cstc-vdc");
		vapp.setVappname(servletRequest.getParameter("name"));
		vapp.setUser(user);
		vapp.setNameinvcloud(vappName);
		vapp.setTime(time.getTime() / 1000);

		session = sessionFactory.getCurrentSession();
		tx = session.beginTransaction();
		session.save(vapp);
		tx.commit();

		sessionMap.put("info", "新建VM " + servletRequest.getParameter("name")
				+ " 成功！");
		sessionMap.put("info_kind", "info");
		return "success";
	}

	public String prepareConsole() throws KeyManagementException,
			UnrecoverableKeyException, NoSuchAlgorithmException,
			KeyStoreException, UnsupportedEncodingException {
		if (check_user().equals("needlogin")) {
			return "needlogin";
		}

		String all = null;
		try {
			all = CstcVCloudAPI.acquireTicket(servletRequest.getParameter("vmid"));
		} catch (Exception e) {
			sessionMap.put("info", "请确认虚拟机为开机状态！");
			sessionMap.put("info_kind", "warning");
			return "fail";
		}

		int IP_start = 6;
		int IP_end = all.indexOf('/', IP_start);
		String IP = all.substring(IP_start, IP_end);

		int VmID_start = IP_end + 1;
		int VmID_end = all.indexOf('?', VmID_start);
		String VmID = all.substring(VmID_start, VmID_end);

		int ticket_start = VmID_end + 8;
		String ticket = all.substring(ticket_start);
		ticket = URLDecoder.decode(ticket, "UTF-8");

		consoleKey = new LinkedHashMap<String, String>();

		consoleKey.put("host", IP);
		consoleKey.put("ticket", ticket);
		consoleKey.put("vmid", VmID);

		return "success";
	}

	public HashMap<String, String> getConsoleKey() {
		return consoleKey;
	}
}