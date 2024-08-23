package com.encounterO.chat.db;

import java.sql.Timestamp;

public class ChatDTO {

	private int chatbno;										
	private String id;											
	private String name;										
	private String content;										
	private Timestamp time;										
	private String ip;
	
	private int deleteCount;
	
	private String target;
	private boolean isread;
	private Timestamp readtime;
	
	
	
	public int getChatbno() {
		return chatbno;
	}
	public void setChatbno(int chatbno) {
		this.chatbno = chatbno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getDeleteCount() {
		return deleteCount;
	}
	public void setDeleteCount(int deleteCount) {
		this.deleteCount = deleteCount;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public boolean isIsread() {
		return isread;
	}
	public void setIsread(boolean isread) {
		this.isread = isread;
	}
	public Timestamp getReadtime() {
		return readtime;
	}
	public void setReadtime(Timestamp readtime) {
		this.readtime = readtime;
	}
	@Override
	public String toString() {
		return "ChatDTO [chatbno=" + chatbno + ", id=" + id + ", name=" + name + ", content=" + content + ", time="
				+ time + ", ip=" + ip + ", deleteCount=" + deleteCount + ", target=" + target + ", isread=" + isread
				+ ", readtime=" + readtime + "]";
	}

	
	
}
