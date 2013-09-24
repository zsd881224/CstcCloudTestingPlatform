package persistentClasses;

public class TestTemplate {
	private int id;
	private String orgname;
	private String vdcname;
	private String tempname;
	
	private void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
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
	
	public void setTempname(String tempname) {
		this.tempname = tempname;
	}
	
	public String getTempname() {
		return this.tempname;
	}
}