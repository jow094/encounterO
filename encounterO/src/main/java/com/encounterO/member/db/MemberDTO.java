package com.encounterO.member.db;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * MemberDTO : 회원정보를 저장하는 객체
 * DTO : Data Transfer Object
 * 	xxxxAction 객체와 DB 사이에서 정보를 전달해주는 객체
 * 		→ 회원정보 테이블 정보를 모두 저장 가능한 객체
 * 		→ 테이블의 컬럼명과 객체의 변수명을 맞추어 놓았음.
 */


public class MemberDTO {

	private String id;												
	private String pw;
	private String name ;
	private String gender;
	private int age;
	private String phone;
	private String email1;
	private String email2;
	private Timestamp regdate;
	private Date lastVisit;
	private String grade;
	private int visit;
	private int token;
	private int post;
	private int replePost;
	private int boardAlarm;
	private int repleAlarm;
	private int chatAlarm;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public Timestamp getRegdate() {
		return regdate;
	}
	public void setRegdate(Timestamp regdate) {
		this.regdate = regdate;
	}
	public Date getLastVisit() {
		return lastVisit;
	}
	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getVisit() {
		return visit;
	}
	public void setVisit(int visit) {
		this.visit = visit;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}
	public int getPost() {
		return post;
	}
	public void setPost(int post) {
		this.post = post;
	}
	public int getReplePost() {
		return replePost;
	}
	public void setReplePost(int replePost) {
		this.replePost = replePost;
	}
	public int getBoardAlarm() {
		return boardAlarm;
	}
	public void setBoardAlarm(int boardAlarm) {
		this.boardAlarm = boardAlarm;
	}
	public int getRepleAlarm() {
		return repleAlarm;
	}
	public void setRepleAlarm(int repleAlarm) {
		this.repleAlarm = repleAlarm;
	}
	public int getChatAlarm() {
		return chatAlarm;
	}
	public void setChatAlarm(int chatAlarm) {
		this.chatAlarm = chatAlarm;
	}
	@Override
	public String toString() {
		return "MemberDTO [id=" + id + ", pw=" + pw + ", name=" + name + ", gender=" + gender + ", age=" + age
				+ ", phone=" + phone + ", email1=" + email1 + ", email2=" + email2 + ", regdate=" + regdate
				+ ", lastVisit=" + lastVisit + ", grade=" + grade + ", visit=" + visit + ", token=" + token + ", post="
				+ post + ", replePost=" + replePost + ", boardAlarm=" + boardAlarm + ", repleAlarm=" + repleAlarm
				+ ", chatAlarm=" + chatAlarm + "]";
	}

	
}
