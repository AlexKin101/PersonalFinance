package dao;

public class User {
	private String userId="";
	private String userPwd="";
	private String userEmail="";
	private int userIcon=0;
	
	//getter&setter
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public int getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(int userIcon) {
		this.userIcon = userIcon;
	}
}
