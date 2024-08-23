package com.encounterO.util;

public class ActionForward {

/**
 * ActionForward (티켓) : 페이지 이동 정보를 저장하는 객체. 
 * 		이동할 페이지 주소(path)를 가져야 한다.
 *		이동할 방식(isRedirect)을 가져야 한다.
 */

	private String path;
	private boolean isRedirect;
		//true → sendRedirect() 방식으로 이동
		//false → forward() 방식으로 이동
	
	
	public ActionForward() {
		System.out.println(" C : 페이지 이동정보(티켓정보)를 저장하는 객체를 생성하였습니다.");
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setIsRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	@Override
	public String toString() {
		return "ActionForward(티켓) [path(이동주소)="+ path +", isRedirect(이동방식이 Redirect인가)="+isRedirect;
	}

	
	
	
}
