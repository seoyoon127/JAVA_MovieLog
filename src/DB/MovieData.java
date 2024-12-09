package DB;

import java.util.Date;

public class MovieData {
	private String id;
	private String imgUrl;
	private String title;
	private Date releaseDate;
	private double rate;
	private String cast;
	private String review; 
	private int serialNum;
	
	//사용자가 등록할 때
	public MovieData(String id, String imgUrl, String title, Date releaseDate, double rate, String cast, String review) {
		this.id=id;
		this.imgUrl=imgUrl;
		this.title=title;
		this.releaseDate=releaseDate; 
		this.rate=rate;
		this.cast=cast;
		this.review=review;
	}
	//DB에서 불러올 때 (서버에서 serialNum 자동 생성)
	public MovieData(String id, String imgUrl, String title, Date releaseDate, double rate, String cast, String review, int serialNum) {
		this.id=id;
		this.imgUrl=imgUrl;
		this.title=title;
		this.releaseDate=releaseDate;
		this.rate=rate;
		this.cast=cast;
		this.review=review;
		this.serialNum=serialNum;
	}
	public String getId() {
		return id;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public String getTitle() {
		return title;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public double getRate() {
		return rate;
	}
	public String getCast() {
		return cast;
	}
	public String getReview() {
		return review;
	}
	public int getSerialNum() {
        return serialNum;
    }
	
	public void setId(String id) {
		this.id = id;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public void setRating(double rate) {
		this.rate = rate;
	}
	public void setCast(String cast) {
		this.cast = cast;
	}
	public void setReview(String review) {
		this.review = review;
	}
}
