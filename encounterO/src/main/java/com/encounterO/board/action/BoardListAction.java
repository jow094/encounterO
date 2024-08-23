package com.encounterO.board.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.board.db.BoardDAO;
import com.encounterO.board.db.BoardDTO;
import com.encounterO.member.db.MemberDAO;
import com.encounterO.member.db.MemberDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;
import com.encounterO.util.Transaction;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardListAction_execute() 실행");
		ActionForward forward = new ActionForward();
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		String directory = request.getParameter("directory");
		String criteria = request.getParameter("criteria");
		String keyWord = request.getParameter("keyWord");
		
		int pageSize = 20;
			if(request.getParameter("pageSize")!=null&&!request.getParameter("pageSize").equals("")) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
			}
		String pageNum = "1";
			if(request.getParameter("pageNum")!=null&&!request.getParameter("pageNum").equals("")) {
			pageNum = request.getParameter("pageNum");
			}
		BoardDAO dao = new BoardDAO();
		
		int count=0;
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		List<Transaction> brList = new ArrayList<Transaction>();
		
		/////////////////////////////////////////////이상 필요한 변수 로드 및 선언///////////////////////////////////////////////////
		
		if(directory==null||directory.equals("")) {
			forward.setPath("./BoardList.bo?directory=comm");
			forward.setIsRedirect(true);
			return forward;
		}
		
		switch(directory) {
		case "comm" :
			if(criteria==null||keyWord==null||criteria.equals("")||keyWord.equals("")) {
				count = dao.getBoardCount(directory);
			}else {
				count = dao.getBoardCount(directory,criteria,keyWord);
			}
			break;
		case "myPost" :
			if(criteria==null||keyWord==null||criteria.equals("")||keyWord.equals("")) {
				count = dao.getMyBoardCount(id);
			}else {
				count = dao.getMyBoardCount(id,criteria,keyWord);
			}
			break;
		case "myReple" : 
			if(criteria==null||keyWord==null||criteria.equals("")||keyWord.equals("")) {
				count = dao.getRepledBoardCount(id);
			}else {
				count = dao.getRepledBoardCount(id,criteria,keyWord);
			}
			break;
		case "repleForMe" :
			break;
		case "reWriteForMe" :
			break;
		}
		System.out.println(" M : BoardListAction_execute() : Pagination_1 완료----------------------");
		int currentPage=(int)Double.parseDouble(pageNum);
		//페이지의 첫번째(1,21,41..) 계산
		int startRow= (currentPage-1)*pageSize + 1;
		//페이지의 마지막(20,40,60..) 계산
		int endRow= currentPage*pageSize;
		//총 페이지 개수 계산
		int lastPage= (int)(count/pageSize) + (count%pageSize==0?0:1);
		if(count>0) {
			
			switch(directory) {
			case "comm" :
				if(criteria==null||keyWord==null||criteria.equals("")||keyWord.equals("")) {
					boardList = dao.getBoardList(directory,startRow,pageSize);
					forward.setPath("./board/boardList.jsp");
					forward.setIsRedirect(false);
				}else {
					boardList = dao.getBoardList(directory,criteria,keyWord,startRow,pageSize);
					forward.setPath("./board/boardList.jsp");
					forward.setIsRedirect(false);
				}
				break;
			case "myPost" : 
				if(criteria==null||keyWord==null||criteria.equals("")||keyWord.equals("")) {
					boardList = dao.getMyBoardList(id,startRow,pageSize);
					forward.setPath("./board/boardList.jsp");
					forward.setIsRedirect(false);
				}else {
					boardList = dao.getMyBoardList(id,criteria,keyWord,startRow,pageSize);
					forward.setPath("./board/boardList.jsp");
					forward.setIsRedirect(false);
				}
				break;
			case "myReple" : 
				if(criteria==null||keyWord==null||criteria.equals("")||keyWord.equals("")) {
					brList = dao.getRepleAndBoard(id,startRow,pageSize);
					request.setAttribute("brList",brList);
					forward.setPath("./board/boardListAndReple.jsp");
					forward.setIsRedirect(false);
				}else {
					brList = dao.getRepleAndBoard(id,criteria,keyWord,startRow,pageSize);
					request.setAttribute("brList",brList);
					forward.setPath("./board/boardListAndReple.jsp");
					forward.setIsRedirect(false);
				}
			case "repleForMe" :
				break;
			case "reWriteForMe" :
				break;
			}
		}
		
		request.setAttribute("lastPage",lastPage);
		request.setAttribute("startRow",startRow);
		request.setAttribute("endRow",endRow);
		request.setAttribute("boardList",boardList);
		request.setAttribute("count",count);
		request.setAttribute("pageSize",pageSize);
		request.setAttribute("currentPage",currentPage);
		
		//세션영역에 조회수 증가 상태를 저장
		session.setAttribute("isUpdate",true);
		
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
		return forward;
	}


}
