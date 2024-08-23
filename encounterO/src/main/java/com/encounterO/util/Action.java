package com.encounterO.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	// 추상메서드 execute() 정의. 인터페이스의 경우 메서드 접근지정자 생략시 퍼블릭으로 설정.
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
	// execute() : 실행시 request,response 정보가 필요하며, 실행 후 ActionForward 객체를 리턴한다.
}
