package persistentClasses;

public class TestVapp {
	private int vappid;
	private String orgname;
	private String vdcname;
	private String vappname;
	private String templatename;
	private User user;
	private String nameinvcloud;
	private long time;
	
	private void setVappid(int vappid) {
		this.vappid = vappid;
	}
	
	public int getVappid() {
		return this.vappid;
	}
	
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	public String getOrgname() {
		return this.orgname;
	}
	
	public void setVdcname(String vdcname) {
		this.vdcname = vdcname;
	}
	
	public String getVdcname() {
		return this.vdcname;
	}
	
	public void setVappname(String vappname) {
		this.vappname = vappname;
	}
	
	public String getVappname() {
		return this.vappname;
	}
	
	public void setTemplatename(String templatename) {
		this.templatename = templatename;
	}
	
	public String getTemplatename() {
		return this.templatename;
	}
		
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setNameinvcloud(String nameinvcloud) {
		this.nameinvcloud = nameinvcloud;
	}
	
	public String getNameinvcloud() {
		return this.nameinvcloud;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public long getTime() {
		return this.time;
	}
}