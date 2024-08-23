package com.encounterO.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.notice.db.NoticeDAO;
import com.encounterO.notice.db.NoticeDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;
import com.encounterO.util.JSFunction;

public class NoticeDeleteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
System.out.println(" M : MemberDeleteAction_execute() 실행");
		
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
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		NoticeDTO dto = new NoticeDTO();
		dto.setId(id);
		dto.setPass(pw);
		dto.setBno(bno);
		
		NoticeDAO dao = new NoticeDAO();
		
		int result = dao.deleteNotice(dto);
		System.out.println(" M : result : " + result);

		if(result == -1) { // 아이디 정보 없음
			JSFunction.alertAndBack(response, "작성자 본인이 아니거나 비밀번호 오류입니다. 이전 페이지로 돌아갑니다.");
			return null;
		}
		else {
			JSFunction.alertAndLocation(response, "게시글이 삭제되었습니다.", "./NoticeList.bo");
		}
		
	return null;	
	}
	
	
}
