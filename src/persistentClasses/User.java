package persistentClasses;

public class User {
	private int id;
	private String username;
	private String password;
	private int vmlimit;
	private String nickname;
	private long registTime;
	private long thistimeLoginTime;
	private long lasttimeLoginTime;
	private String email;
	private Integer qq;
	private String address;
	private Integer zip;
	
	private void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setVmlimit(int vmlimit) {
		this.vmlimit = vmlimit;
	}

	public int getVmlimit() {
		return this.vmlimit;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	
	public void setRegistTime(long registTime) {
		this.registTime = registTime;
	}
	
	public long getRegistTime() {
		return this.registTime;
	}
	
	public void setThistimeLoginTime(long thistimeLoginTime) {
		this.thistimeLoginTime = thistimeLoginTime;
	}
	
	public long getThistimeLoginTime() {
		return this.thistimeLoginTime;
	}
	
	public void setLasttimeLoginTime(long lasttimeLoginTime) {
		this.lasttimeLoginTime = lasttimeLoginTime;
	}
	
	public long getLasttimeLoginTime() {
		return this.lasttimeLoginTime;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setQq(Integer qq) {
		this.qq = qq;
	}
	
	public Integer getQq() {
		return this.qq;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setZip(Integer zip) {
		this.zip = zip;
	}
	
	public Integer getZip() {
		return this.zip;
	}
}