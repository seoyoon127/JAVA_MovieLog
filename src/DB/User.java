package DB;

public class User {
	private String id;
	private String pw;
	
	public User(String id, String pw) {
		this.id=id;
		this.pw=pw;
	}
	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public void setId(String newId) {
		id=newId;
	}
	public void setPw(String newPw) {
		pw=newPw;
	}
}
