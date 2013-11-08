package persistentClasses;

public class TestTemplate {
	private int id;
	private String tempname;
	private String orgname;
	private String vdcname;
	private String nameinvcloud;

	
	private void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setTempname(String tempname) {
		this.tempname = tempname;
	}
	
	public String getTempname() {
		return this.tempname;
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
	
	public void setNameinvcloud(String nameinvcloud) {
		this.nameinvcloud = nameinvcloud;
	}
	
	public String getNameinvcloud() {
		return this.nameinvcloud;
	}
}