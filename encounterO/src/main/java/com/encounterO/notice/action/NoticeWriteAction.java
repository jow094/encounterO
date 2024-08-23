package com.encounterO.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.notice.db.NoticeDAO;
import com.encounterO.notice.db.NoticeDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;

public class NoticeWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : NoticeWriteAction_execute() 호출");
		
		//한글처리 인코딩
		//request.setCharacterEncoding("UTF-8"); web.xml에서 했음
		//전달받은 정보를 파라메터로 저장
		//작성자 비밀번호 제목 내용

		NoticeDTO dto = new NoticeDTO();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String name = (String) session.getAttribute("name");
		
		dto.setId(id);
		dto.setName(name);
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		dto.setIp(request.getRemoteAddr());
		
		System.out.println(" M : "+dto);
		
		NoticeDAO dao = new NoticeDAO();
		dao.insertNotice(dto);
		
		//페이지 이동준비
		ActionForward forward = new ActionForward();
		forward.setPath("./NoticeList.no");
		forward.setIsRedirect(true);
		
		return forward;
	}

	
		
		

}
