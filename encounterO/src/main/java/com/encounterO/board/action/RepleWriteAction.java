package com.encounterO.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.board.db.BoardDAO;
import com.encounterO.board.db.BoardDTO;
import com.encounterO.board.db.RepleDTO;
import com.encounterO.member.db.MemberDAO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;
import com.encounterO.util.JSFunction;

public class RepleWriteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardWriteAction_execute() 호출");
		
		//한글처리 인코딩
		//request.setCharacterEncoding("UTF-8"); web.xml에서 했음
		//전달받은 정보를 파라메터로 저장
		//작성자 비밀번호 제목 내용

		RepleDTO dto = new RepleDTO();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String name = (String) session.getAttribute("name");
		
		
		dto.setId(id);
		dto.setName(name);
		dto.setContent(request.getParameter("content"));
		dto.setBno(Integer.parseInt(request.getParameter("bno")));
		dto.setIp(request.getRemoteAddr());
		
		System.out.println(" M : 작성된 댓글 정보 :"+dto);
		
		BoardDAO dao = new BoardDAO();
		dao.insertReple(dto);
		
		String directory = request.getParameter("directory");
		String bno = request.getParameter("bno");
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String contentNum = request.getParameter("contentNum");
		String count = request.getParameter("count");
		
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardContent.bo?directory="+directory+"&bno="+bno+"&pageNum="+pageNum+"&pageSize="+pageSize+"&contentNum="+contentNum+"&count="+count);
		forward.setIsRedirect(true);
		
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
		return forward;
	}

}
