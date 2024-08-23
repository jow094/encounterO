package com.encounterO.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.board.db.BoardDAO;
import com.encounterO.board.db.BoardDTO;
import com.encounterO.member.db.MemberDAO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;

public class BoardReWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardReWriteAction_execute() 호출");
		
		//한글처리 인코딩
		//request.setCharacterEncoding("UTF-8"); web.xml에서 했음
		//전달받은 정보를 파라메터로 저장
		//bno, re_ref,re_lev,re_seq,pageNum,name,subject,pass,content

		BoardDTO dto = new BoardDTO();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String name = (String) session.getAttribute("name");
		
		
		int targetBno = Integer.parseInt(request.getParameter("bno"));
		String directory = request.getParameter("directory");
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String countentNum = request.getParameter("contentNum");
		String count = request.getParameter("count");
		String criteria = request.getParameter("criteria");
		String keyWord = request.getParameter("keyWord");
		
		dto.setRe_ref(Integer.parseInt(request.getParameter("re_ref")));
		dto.setRe_lev(Integer.parseInt(request.getParameter("re_lev")));
		dto.setRe_seq(Integer.parseInt(request.getParameter("re_seq")));
		dto.setId(id);
		dto.setName(name);
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		dto.setDirectory(request.getParameter("directory"));
		dto.setIp(request.getRemoteAddr());
		
		System.out.println(" M : BoardDAO : reInsertBoard 실행 / "+dto);
		
		BoardDAO dao = new BoardDAO();
		dao.reInsertBoard(dto,targetBno);
		//페이지 이동준비
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo?directory="+directory+"&pageNum="+pageNum+"&pageSize="+pageSize);
		forward.setIsRedirect(true);
		
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
		return forward;
	}

	
		
		

}
