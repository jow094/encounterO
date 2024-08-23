package com.encounterO.board.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.board.db.BoardDAO;
import com.encounterO.board.db.BoardDTO;
import com.encounterO.member.db.MemberDAO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;
import com.encounterO.util.Transaction;

public class BoardMyMenuAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardMyMenuAction_execute() 실행");
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		String directory = request.getParameter("directory");
		String criteria = request.getParameter("criteria");
		String keyWord = request.getParameter("keyWord");
		String myMenu = request.getParameter("myMenu");
		BoardDAO dao = new BoardDAO();
		
		int count=0;
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		List<Transaction> boardAndRepleList = new ArrayList<Transaction>();
		
		switch(myMenu) {
		case "myPost" : count = dao.getMyBoardCount(id);
			break;
		case "myReple" : count = dao.getRepleCount(id);
			break;
		case "repleForMe" : 
			break;
		case "reWriteForMe" : 
			break;
		default :
			forward.setPath("./boardList.bo?directory=comm");
			forward.setIsRedirect(true);
		}
		
		System.out.println(" M : ---------------페이징처리 1 시작 ----------------");
		int pageSize = 20;
			if(request.getParameter("pageSize")!=null&&!request.getParameter("pageSize").equals("")) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
			}
		String pageNum = "1";
			if(request.getParameter("pageNum")!=null&&!request.getParameter("pageNum").equals("")) {
			pageNum = request.getParameter("pageNum");
			}
		int currentPage=(int)Double.parseDouble(pageNum);
		//페이지의 첫번째(1,21,41..) 계산
		int startRow= (currentPage-1)*pageSize + 1;
		//페이지의 마지막(20,40,60..) 계산
		int endRow= currentPage*pageSize;
		//총 페이지 개수 계산
		int lastPage= (int)(count/pageSize) + (count%pageSize==0?0:1);
		
		if(count>0) {
			
			switch(myMenu) {
			case "myPost" : boardList = dao.getMyBoardList(id,startRow,pageSize);
				break;
			case "myReple" : boardAndRepleList = dao.getRepleAndBoard(id,startRow,pageSize);
				break;
			case "repleForMe" : 
				break;
			case "reWriteForMe" : 
				break;
			default :
				forward.setPath("./boardList.bo?directory=comm");
				forward.setIsRedirect(true);
			}
			
			System.out.println(" M : BoardDAO : getBoardList 실행 : currentPage ="+currentPage+" startRow="+startRow+" endRow="+endRow+" lastPage="+lastPage+" pagesize="+pageSize);
			System.out.println(" M : BoardDAO : getBoardList 실행 : "+count+"개의 게시물을 "+pageSize+"개씩 "+lastPage+"개의 페이지로 나누어 표시");
		}
		System.out.println(" M : ---------------페이징처리 2 시작 ----------------");
		// 페이징 블럭 처리
		// 게시판 글 개수에 맞는 페이지 개수
		
		request.setAttribute("lastPage",lastPage);
		request.setAttribute("startRow",startRow);
		request.setAttribute("endRow",endRow);
		request.setAttribute("count",count);
		request.setAttribute("pageSize",pageSize);
		request.setAttribute("currentPage",currentPage);
		
		switch(myMenu) {
		case "myPost" : request.setAttribute("boardList",boardList);
			break;
		case "myReple" : request.setAttribute("brList",boardAndRepleList);
						 request.setAttribute("boardSize",boardAndRepleList.size());
			System.out.println("myMenu : "+myMenu+" boardAndRepleList :"+boardAndRepleList);
			break;
		case "repleForMe" : 
			break;
		case "reWriteForMe" : 
			break;
		default :
		}

		System.out.println(boardAndRepleList);
		//세션영역에 조회수 증가 상태를 저장
		session.setAttribute("isUpdate",true);
		forward.setPath("./board/boardListAndReple.jsp");
		forward.setIsRedirect(false);
		
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
		
		return forward;
	}
	
}
	

