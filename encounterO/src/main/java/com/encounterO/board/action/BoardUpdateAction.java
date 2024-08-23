package com.encounterO.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.board.db.BoardDTO;
import com.encounterO.member.db.MemberDAO;
import com.encounterO.board.db.BoardDAO;
import com.encounterO.board.db.BoardDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;

public class BoardUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardUpdateAction_execute() 호출");
		
			
		ActionForward forward = new ActionForward();
		
		String directory = request.getParameter("directory");
		int bno = Integer.parseInt(request.getParameter("bno"));
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String contentNum = request.getParameter("contentNum");
		String count = request.getParameter("count");
		String criteria = request.getParameter("criteria");
		String keyWord = request.getParameter("keyWord");

		BoardDAO dao = new BoardDAO();
				
		BoardDTO dto = dao.getBoardContent(directory,bno);
		System.out.println(" M : boardDAO : getBoardContent 실행 / 수정 대상 게시글 :"+ dto);
		
		request.setAttribute("oldDto", dto);
		
		request.getParameter("bno");
		request.getParameter("pageNum");
				
		forward.setPath("./board/boardUpdate.jsp?directory="+directory+"&bno="+bno+"&pageNum="+pageNum+"&pageSize="+pageSize+"&contentNum="+contentNum+"&count="+count+"&criteria="+criteria+"&keyWord="+keyWord);
		forward.setIsRedirect(false);
		
		HttpSession session = request.getSession();
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
		return forward;
	}
}
	


