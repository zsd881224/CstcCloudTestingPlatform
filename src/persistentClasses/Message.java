package persistentClasses;

public class Message {
	private int id;
	private User user;
	private String content;
	private long time;
	private String reply;
	private Integer replyTime;
	
	private void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public long getTime() {
		return this.time;
	}
	
	public void setReply(String reply) {
		this.reply = reply;
	}
	
	public String getReply() {
		return this.reply;
	}
	
	public void setReplyTime(Integer replyTime) {
		this.replyTime = replyTime;
	}
	
	public Integer getReplyTime() {
		return this.replyTime;
	}
}