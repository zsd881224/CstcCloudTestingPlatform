package Constant;

public class Constant {
	// -----MySQL-----
	// 驱动程序名
	public static final String driverName = "com.mysql.jdbc.Driver";
	// 数据库用户名
	public static final String userName = "root";
	// 密码
	public static final String userPasswd = "qwe123";
	// 数据库名
	public static final String dbName = "cstc";
	// 链接数据库路径
	public static final String mysqlurl = "jdbc:mysql://localhost/" + dbName + "?user=" + userName
			+ "&password=" + userPasswd + "&useUnicode=true&characterEncoding=utf8";

	// -----Vcloud-----
	public static final String vServer = "http://172.16.21.248";
	public static final String vUser = "administrator@System";
	public static final String vPswd = "cstcqwe123";
	
	// -----默认新建位置-----
	public static final String default_org = "cstc";
	public static final String default_vdc = "cstc-vdc";
	
	// -----留言单页显示条数-----
	public static final int message_per_page = 10;
}