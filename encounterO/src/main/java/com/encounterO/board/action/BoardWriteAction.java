package com.encounterO.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.board.db.BoardDAO;
import com.encounterO.board.db.BoardDTO;
import com.encounterO.member.db.MemberDAO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;

public class BoardWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardWriteAction_execute() 호출");
		
		//한글처리 인코딩
		//request.setCharacterEncoding("UTF-8"); web.xml에서 했음
		//전달받은 정보를 파라메터로 저장
		//작성자 비밀번호 제목 내용

		BoardDTO dto = new BoardDTO();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String name = (String) session.getAttribute("name");
		String directory = request.getParameter("directory");
		String pageSize = request.getParameter("pageSize");
		
		dto.setId(id);
		dto.setName(name);
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		dto.setIp(request.getRemoteAddr());
		
		System.out.println(" M : BoardDAO : insertBoard 실행 / "+dto);
		
		BoardDAO dao = new BoardDAO();
		dao.insertBoard(directory,dto);
		
		//페이지 이동준비
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo?directory="+directory+"&pageNum=1&pageSize="+pageSize);
		forward.setIsRedirect(true);
		
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
		return forward;
	}

	
		
		

}