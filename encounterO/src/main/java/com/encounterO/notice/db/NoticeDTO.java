package com.encounterO.notice.db;

import java.sql.Date;

/**
 *  itwill_board 테이블에 저장할 정보를 담는 객체
 * 
 */

public class NoticeDTO {

	private int bno;											//글 번호, PK
	private String id;											//작성자 아이디, NN
	private String pass;										//글 비밀번호, NN
	private String name;										//작성자 이름, NN
	private String subject;										//글 제목, NN
	private String content;										//글 내용
	private int readcount;										//조회수
	
	private int re_ref;											//글의 그룹번호, 답글에 관련된 정보
	private int re_lev;											//글 레벨값, 답글에 관련된 정보
	private int re_seq;											//글 시퀀스(순서), 답글에 관련된 정보
	
	private Date date;											//작성일
	private String ip;											//작성자 ip
	private String file;										//첨부파일 이름
	
	
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
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
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
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	@Override
	public String toString() {
		return "NoticeDTO [bno=" + bno + ", id=" + id + ", pass=" + pass + ", name=" + name + ", subject=" + subject
				+ ", content=" + content + ", readcount=" + readcount + ", re_ref=" + re_ref + ", re_lev=" + re_lev
				+ ", re_seq=" + re_seq + ", date=" + date + ", ip=" + ip + ", file=" + file + "]";
	}
	
	
	
	
}
