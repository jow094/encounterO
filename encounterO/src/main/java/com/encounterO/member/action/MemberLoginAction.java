package com.encounterO.member.action;

import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.member.db.MemberDAO;
import com.encounterO.member.db.MemberDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;
import com.encounterO.util.JSFunction;

public class MemberLoginAction implements Action {

	ActionForward forward;
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberLoginAction_execute() 실행");

		// 한글처리 인코딩  /  전달정보(파라메터)저장  /  DB연결-select 처리  /  로그인 완료 후 메인페이지로 이동
		
		// 한글처리 인코딩
		request.setCharacterEncoding("UTF-8");
		// 전달받은 정보(파라메터)저장 → MemberDTO 객체를 이용해보자.
		MemberDTO dto = new MemberDTO();
		
		dto.setId(request.getParameter("id"));
		dto.setPw(request.getParameter("pw"));

		System.out.println(" M : 전달받은 정보 : "+dto);
		
		// DB연결-insert 처리 → DAO 객체 생성
		MemberDAO dao = new MemberDAO();
		String result = dao.loginCheck(dto);
		System.out.println(" M : result : "+result);
		ActionForward forward = new ActionForward();
		
		if(result.equals("-1")) {
			JSFunction.alertAndBack(response, "존재하지 않는 아이디입니다.");
		}else if (result.equals("0")) {
			JSFunction.alertAndBack(response, "비밀번호가 틀렸습니다.");
		}else {
			if(dao.isFirstToday(dto.getId())) {
				dao.updateToken(dto.getId());
				
				dao.updateVisit(dto);
				dto = dao.getMember(dto.getId());
				HttpSession session = request.getSession();
				session.setAttribute("id", dto.getId());
				session.setAttribute("name", result);
				session.setAttribute("grade", dto.getGrade());
				session.setAttribute("visit", dto.getVisit());
				session.setAttribute("post", dto.getPost());
				session.setAttribute("replePost", dto.getReplePost());
				session.setAttribute("token", dto.getToken());
				
				JSFunction.alertAndLocation(response, result+" 님 안녕하세요! 금일 첫번째 로그인 입니다. 추천 횟수 10회가 제공되었습니다.", "./Main.me");

			}else {
				dao.updateVisit(dto);
				dto = dao.getMember(dto.getId());
				HttpSession session = request.getSession();
				session.setAttribute("id", dto.getId());
				session.setAttribute("name", result);
				session.setAttribute("grade", dto.getGrade());
				session.setAttribute("visit", dto.getVisit());
				session.setAttribute("post", dto.getPost());
				session.setAttribute("replePost", dto.getReplePost());
				session.setAttribute("token", dto.getToken());
				
				JSFunction.alertAndLocation(response, result+" 님 안녕하세요!", "./Main.me");
			}
		}
		HttpSession session = request.getSession();
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
	return forward;
	}

}
