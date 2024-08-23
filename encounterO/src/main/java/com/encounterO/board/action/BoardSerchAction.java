package com.encounterO.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.board.db.BoardDAO;
import com.encounterO.board.db.BoardDTO;
import com.encounterO.member.db.MemberDAO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;

public class BoardSerchAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardSerchAction_execute() 실행");
		
		ActionForward forward = new ActionForward();
		String directory = request.getParameter("directory");
		String criteria = request.getParameter("criteria");
		String keyWord = request.getParameter("keyWord");
		BoardDAO dao = new BoardDAO();
		//저장된 글의 개수 조회
		int count=0;
		if(criteria==null||keyWord==null||criteria.equals("")||keyWord.equals("")) {
			count = dao.getBoardCount(directory);
		}else {
			count = dao.getBoardCount(directory,criteria,keyWord);
		}
		System.out.println(" M : ---------------페이징처리 1 시작 ----------------");
		
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		
		String pageNum = request.getParameter("pageNum"); // 현 페이지가 몇페이지인지 체크
		int currentPage=(int)Double.parseDouble(pageNum);
		//페이지의 첫번째(1,21,41..) 계산
		int startRow= (currentPage-1)*pageSize + 1;
		//페이지의 마지막(20,40,60..) 계산
		int endRow= currentPage*pageSize;
		//총 페이지 개수 계산
		int lastPage= (int)(count/pageSize) + (count%pageSize==0?0:1);
		List<BoardDTO> boardList = null;
		if(count>0) {
			
			if(criteria==null||keyWord==null||criteria.equals("")||keyWord.equals("")) {
				boardList = dao.getBoardList(directory,startRow,pageSize);
			}else {
				boardList = dao.getBoardList(directory,criteria,keyWord,startRow,pageSize);
			}
			
			System.out.println(" M : BoardDAO : getBoardList 실행 : "+count+"개의 게시물을 "+pageSize+"개씩 "+lastPage+"개의 페이지로 나누어 표시");
		}
		System.out.println(" M : ---------------페이징처리 2 시작 ----------------");
		// 페이징 블럭 처리
		// 게시판 글 개수에 맞는 페이지 개수
		
		request.setAttribute("lastPage",lastPage);
		request.setAttribute("startRow",startRow);
		request.setAttribute("endRow",endRow);
		request.setAttribute("boardList",boardList);
		request.setAttribute("count",count);
		request.setAttribute("pageSize",pageSize);
		request.setAttribute("currentPage",currentPage);
		
		//세션영역에 조회수 증가 상태를 저장
		HttpSession session = request.getSession();
		session.setAttribute("isUpdate",true);
		forward.setPath("./board/boardList.jsp");
		forward.setIsRedirect(false);
		
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
		return forward;

	
		
		
	}

	
	
}
