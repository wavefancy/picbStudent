package wavefancy;

import java.util.Date;

public class Articles {
	int id;
	String aTitle;
	String aAbstract;
	int aAuthorId;
	int aReadTimes;
	String aContent;
	int pGroupId;
	String aImg;
	Date aDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getaTitle() {
		return aTitle;
	}
	public void setaTitle(String aTitle) {
		this.aTitle = aTitle;
	}
	public String getaAbstract() {
		return aAbstract;
	}
	public void setaAbstract(String aAbstract) {
		this.aAbstract = aAbstract;
	}
	public int getaAuthorId() {
		return aAuthorId;
	}
	public void setaAuthorId(int aAuthorId) {
		this.aAuthorId = aAuthorId;
	}
	public int getaReadTimes() {
		return aReadTimes;
	}
	public void setaReadTimes(int aReadTimes) {
		this.aReadTimes = aReadTimes;
	}
	public String getaContent() {
		return aContent;
	}
	public void setaContent(String aContent) {
		this.aContent = aContent;
	}
	public int getpGroupId() {
		return pGroupId;
	}
	public void setpGroupId(int pGroupId) {
		this.pGroupId = pGroupId;
	}
	public String getaImg() {
		return aImg;
	}
	public void setaImg(String aImg) {
		this.aImg = aImg;
	}
	public Date getaDate() {
		return aDate;
	}
	public void setaDate(Date aDate) {
		this.aDate = aDate;
	}
}
