package com.encounterO.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.board.db.BoardDAO;
import com.encounterO.board.db.BoardDTO;
import com.encounterO.member.db.MemberDAO;
import com.encounterO.member.db.MemberDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;
import com.encounterO.util.JSFunction;

public class BoardLikeAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardLikeAction_execute() 실행");
		// 작성된 글의 내용을 화면에 출력
		// DAO 객체가 할 일 : 조회수 1 증가(update), 작성된 글의 정보를 가져오기(select)

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String directory = request.getParameter("directory");
		int bno = Integer.parseInt(request.getParameter("bno"));
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String contentNum = request.getParameter("contentNum");
		String count = request.getParameter("count");
		String criteria = request.getParameter("criteria");
		String keyWord = request.getParameter("keyWord");
		
		MemberDAO mDao = new MemberDAO();
		MemberDTO mDto = new MemberDTO();
		BoardDAO bDao = new BoardDAO();
		BoardDTO bDto = new BoardDTO();
		
		int leftToken = bDao.like(id, bno);
		if(leftToken!=(-1)) {
		JSFunction.alertAndLocation(response, "게시글을 추천하였습니다! 오늘 남은 추천 횟수는 "+leftToken+"회 입니다.", "./BoardContent.bo?directory="+directory+"&bno="+bno+"&pageNum="+pageNum+"&pageSize="+pageSize+"&contentNum="+contentNum+"&count="+count+"&criteria="+criteria+"&keyWord="+keyWord);
		}else {
			JSFunction.alertAndBack(response, "오늘 사용 가능한 추천수를 모두 소진했습니다. 이전 페이지로 돌아갑니다.");
			
		}
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
		return null;
	}
}