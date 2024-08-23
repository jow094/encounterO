package com.encounterO.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.member.db.MemberDAO;
import com.encounterO.member.db.MemberDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;

public class MemberUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberUpdateAction_execute() 실행");
		
		// 로그인여부 체크
		// MemberDAO 객체를 이용하여 로그인되어있는 아이디에 해당하는 기존 회원정보를 가져오는 메서드 실행
		// DAO 에서 가져온 객체를 request 영역에 저장한 뒤 출력할 페이지("./member/updateForm.jsp"로 이동하자.
		
		//세션정보 확인 (로그인 체크)
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null) {
			//response.sendRedirect(id);
			forward.setPath("./MemberLogin.me");
			forward.setIsRedirect(true);
			return forward;
		}
		
		// 사용자가 로그인상태이면 MemberDAO 객체 생성
		// 사용자 아이디에 해당하는 회원정보 모두를 조회하는 메서드를 만들자
		
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getMember(id);
		
		System.out.println(" M : "+ dto);
		
		
		request.setAttribute("dto", dto);
		
		forward.setPath("./member/updateForm.jsp");
		forward.setIsRedirect(false);
		
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
		
		
		return forward;
	

		
		
	}
}
