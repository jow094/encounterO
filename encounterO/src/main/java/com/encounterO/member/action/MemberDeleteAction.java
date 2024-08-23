package com.encounterO.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.member.db.MemberDAO;
import com.encounterO.member.db.MemberDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;
import com.encounterO.util.JSFunction;

public class MemberDeleteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
System.out.println(" M : MemberDeleteAction_execute() 실행");
		
		// 로그인여부 체크

		// 한글처리 인코딩 필요
		request.setCharacterEncoding("UTF-8");
		
		//세션정보 확인 (로그인 체크)
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null) {
			forward.setPath("./MemberLogin.me");
			forward.setIsRedirect(true);
			return forward;
		}
		// 전달받은 파라메터로 사용자가 입력한 비밀번호 저장
		String pw = request.getParameter("pw");
		
		// 얻은 정보 id,pw를 dto에 저장
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPw(pw);
		
		// MemberDAO 객체생성
		MemberDAO dao = new MemberDAO();
		
		// 회원정보를 삭제하는 메서드(동작)실행
		int result = dao.deleteMember(dto);
		System.out.println(" M : result : " + result);

		// 회원정보 삭제 결과에 따른 페이지 이동(JS)
		
		if(result == -1) { // 아이디 정보 없음
			JSFunction.alertAndBack(response, "아이디 정보가 없어 삭제에 실패하였습니다. 이전 페이지로 돌아갑니다.");
			return null;
		}
		else if(result == 0) { // 비밀번호 틀림
			JSFunction.alertAndBack(response, "비밀번호 오류로 삭제에 실패하였습니다. 이전 페이지로 돌아갑니다.");
			return null;
		}
		else {
			session.invalidate();
			JSFunction.alertAndLocation(response, "회원탈퇴에 성공하였습니다. 메인 페이지로 이동합니다.", "./Main.me");
		}
		
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
	return null;	
	}
	
	
}
