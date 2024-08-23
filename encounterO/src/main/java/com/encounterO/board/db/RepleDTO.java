package com.encounterO.board.db;

import java.sql.Timestamp;

public class RepleDTO {


	private int replebno;										
	private String id;											
	private String name;										
	private String content;										
	private Timestamp time;										
	private String ip;
	private int re_lev;											//글 레벨값, 답글에 관련된 정보
	private int re_seq;											//글 시퀀스(순서), 답글에 관련된 정보
	
	private int editCount;
	private int likeCount;
	private int deleteCount;
	private int bno;
	
	private String target;
	private boolean isread;
	private Timestamp readtime;
	public int getReplebno() {
		return replebno;
	}
	public void setReplebno(int replebno) {
		this.replebno = replebno;
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
	public int getRe_lev() {
		return re_lev;
	}
	public void setRe_lev(int re_lev) {
		this.re_lev = re_lev;
	}
	public int getRe_seq() {
		return re_seq;
	}
	public void setRe_seq(int re_seq) {
		this.re_seq = re_seq;
	}
	public int getEditCount() {
		return editCount;
	}
	public void setEditCount(int editCount) {
		this.editCount = editCount;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getDeleteCount() {
		return deleteCount;
	}
	public void setDeleteCount(int deleteCount) {
		this.deleteCount = deleteCount;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
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
		return "RepleDTO [replebno=" + replebno + ", id=" + id + ", name=" + name + ", content=" + content + ", time="
				+ time + ", ip=" + ip + ", re_lev=" + re_lev + ", re_seq=" + re_seq + ", editCount=" + editCount
				+ ", likeCount=" + likeCount + ", deleteCount=" + deleteCount + ", bno=" + bno + ", target=" + target
				+ ", isread=" + isread + ", readtime=" + readtime + ", getReplebno()=" + getReplebno() + ", getId()="
				+ getId() + ", getName()=" + getName() + ", getContent()=" + getContent() + ", getTime()=" + getTime()
				+ ", getIp()=" + getIp() + ", getRe_lev()=" + getRe_lev() + ", getRe_seq()=" + getRe_seq()
				+ ", getEditCount()=" + getEditCount() + ", getLikeCount()=" + getLikeCount() + ", getDeleteCount()="
				+ getDeleteCount() + ", getBno()=" + getBno() + ", getTarget()=" + getTarget() + ", isIsread()="
				+ isIsread() + ", getReadtime()=" + getReadtime() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
	
	
}
