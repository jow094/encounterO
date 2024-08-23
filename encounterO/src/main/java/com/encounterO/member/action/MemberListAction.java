package com.encounterO.member.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.member.db.MemberDAO;
import com.encounterO.member.db.MemberDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;

public class MemberListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberListAction_execute() 실행");
		
		//세션정보 확인 (로그인 체크, id가 admin 인지 체크)
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(!id.equals("admin")||id == null) {
			forward.setPath("./MemberLogin.me");
			forward.setIsRedirect(true);
			return forward;
		}
		
		// 사용자가 admin 이면 MemberDAO 객체 생성
		MemberDAO dao = new MemberDAO();
		
		// 회원정보 모두를 조회하는 getMemberList 메서드를 만들어 실행한다
		dao.getMemberList();
		ArrayList<MemberDTO> memberList = dao.getMemberList();
		
		// 결과 데이터를 request 영역객체에 저장한 뒤 페이지에 출력시키자
		request.setAttribute("memberList", memberList);
		forward.setPath("./member/memberList.jsp");
		forward.setIsRedirect(false);
		
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
		return forward;
	}

	
}
