package com.encounterO.board.db;

import java.sql.Timestamp;

public class BoardDTO{

	private int bno;											//글 번호, PK
	private String id;											//작성자 이름, NN
	private String name;										//작성자 이름, NN
	private String subject;										//글 제목, NN
	private String content;										//글 내용
	private int readCount;										//조회수
	
	private int re_ref;											//글의 그룹번호, 답글에 관련된 정보
	private int re_lev;											//글 레벨값, 답글에 관련된 정보
	private int re_seq;											//글 시퀀스(순서), 답글에 관련된 정보
	private int repleNum;
	
	private Timestamp time;										//작성일
	private String ip;											//작성자 ip
	private String file;										//첨부파일 이름
	
	private int prevBno;
	private int nextBno;
	
	private String prevSub;
	private String nextSub;
	
	private int editCount;
	private int likeCount;
	private int deleteCount;
	
	private String target;
	private boolean isread;
	private Timestamp readtime;
	
	private String directory;

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getRe_ref() {
		return re_ref;
	}

	public void setRe_ref(int re_ref) {
		this.re_ref = re_ref;
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

	public int getRepleNum() {
		return repleNum;
	}

	public void setRepleNum(int repleNum) {
		this.repleNum = repleNum;
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

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getPrevBno() {
		return prevBno;
	}

	public void setPrevBno(int prevBno) {
		this.prevBno = prevBno;
	}

	public int getNextBno() {
		return nextBno;
	}

	public void setNextBno(int nextBno) {
		this.nextBno = nextBno;
	}

	public String getPrevSub() {
		return prevSub;
	}

	public void setPrevSub(String prevSub) {
		this.prevSub = prevSub;
	}

	public String getNextSub() {
		return nextSub;
	}

	public void setNextSub(String nextSub) {
		this.nextSub = nextSub;
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

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	@Override
	public String toString() {
		return "BoardDTO [bno=" + bno + ", id=" + id + ", name=" + name + ", subject=" + subject + ", content="
				+ content + ", readCount=" + readCount + ", re_ref=" + re_ref + ", re_lev=" + re_lev + ", re_seq="
				+ re_seq + ", repleNum=" + repleNum + ", time=" + time + ", ip=" + ip + ", file=" + file + ", prevBno="
				+ prevBno + ", nextBno=" + nextBno + ", prevSub=" + prevSub + ", nextSub=" + nextSub + ", editCount="
				+ editCount + ", likeCount=" + likeCount + ", deleteCount=" + deleteCount + ", target=" + target
				+ ", isread=" + isread + ", readtime=" + readtime + ", directory=" + directory + ", getBno()="
				+ getBno() + ", getId()=" + getId() + ", getName()=" + getName() + ", getSubject()=" + getSubject()
				+ ", getContent()=" + getContent() + ", getReadCount()=" + getReadCount() + ", getRe_ref()="
				+ getRe_ref() + ", getRe_lev()=" + getRe_lev() + ", getRe_seq()=" + getRe_seq() + ", getRepleNum()="
				+ getRepleNum() + ", getTime()=" + getTime() + ", getIp()=" + getIp() + ", getFile()=" + getFile()
				+ ", getPrevBno()=" + getPrevBno() + ", getNextBno()=" + getNextBno() + ", getPrevSub()=" + getPrevSub()
				+ ", getNextSub()=" + getNextSub() + ", getEditCount()=" + getEditCount() + ", getLikeCount()="
				+ getLikeCount() + ", getDeleteCount()=" + getDeleteCount() + ", getTarget()=" + getTarget()
				+ ", isIsread()=" + isIsread() + ", getReadtime()=" + getReadtime() + ", getDirectory()="
				+ getDirectory() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	
	
	
}
