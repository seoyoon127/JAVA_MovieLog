package DB;

public class MovieComment {
	private String id;
	private int serialNum;
	private String comment;
	
	public MovieComment(String id, int serialNum, String comment) {
		this.id = id;
		this.serialNum = serialNum;
		this.comment = comment;
	}
	public String getId() {
		return id;
	}
	public int getSerialNum() {
		return serialNum;
	}
	public String getComment() {
		return comment;
	}
//	public void setId(String newId) {
//		id = newId;
//	}
//	public void setSerialNum(String newSerialNum) {
//		serialNum = newId;
//	}
//	public void setId(String newId) {
//		id = newId;
//	}
}
