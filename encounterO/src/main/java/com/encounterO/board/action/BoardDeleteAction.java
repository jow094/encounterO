package com.encounterO.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.board.db.BoardDAO;
import com.encounterO.board.db.BoardDTO;
import com.encounterO.member.db.MemberDAO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;
import com.encounterO.util.JSFunction;

public class BoardDeleteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
System.out.println(" M : BoardDeleteAction_execute() 실행");
		
		ActionForward forward = new ActionForward();
		// 전달받은 파라메터로 사용자가 입력한 비밀번호 저장
		String pw = request.getParameter("pw");
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		String directory = request.getParameter("directory");
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = dao.getInfo(bno);
		
		int result = dao.deleteBoard(id,pw,dto);

		if(result == -1) {
			JSFunction.alertAndBack(response, "작성자 본인이 아니거나 비밀번호 오류입니다. 이전 페이지로 돌아갑니다.");
			return null;
		}
		else {
			JSFunction.alertAndLocation(response, "게시글이 삭제되었습니다.", "./BoardList.bo?directory="+directory+"&pageNum="+pageNum+"&pageSize="+pageSize);
		}
		
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
	return null;	
	}
	
	
}
