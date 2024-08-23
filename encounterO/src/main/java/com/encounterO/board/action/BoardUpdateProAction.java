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

public class BoardUpdateProAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			System.out.println(" M : BoardUpdateProAction_execute() 실행");
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String pw = request.getParameter("pw");
		String directory = request.getParameter("directory");
		int bno = Integer.parseInt(request.getParameter("bno"));
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String contentNum = request.getParameter("contentNum");
		String count = request.getParameter("count");
		String criteria = request.getParameter("criteria");
		String keyWord = request.getParameter("keyWord");
		
		BoardDTO dto = new BoardDTO();
		
		
		dto.setBno(bno);
		dto.setId(id);
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		
		System.out.println(" M : boardDAO : updateBoard 실행 / 수정 요청된 내용 : "+dto);
		
		// MemberDAO 객체생성
		BoardDAO dao = new BoardDAO();
		
		int result = dao.updateBoard(id,pw,dto);
		System.out.println(" M : updateBoard 완료 / result : " + result);

		// 수정 결과에 따른 페이지 이동(JS) 실행
		
		if(result == 1) {
			JSFunction.alertAndLocation(response, "게시글 수정에 성공하였습니다.", "./BoardContent.bo?directory="+directory+"&bno="+bno+"&pageNum="+pageNum+"&pageSize="+pageSize+"&contentNum="+contentNum+"&count="+count+"&criteria="+criteria+"&keyWord="+keyWord);
		}else{ // 아이디 정보 없음
			JSFunction.alertAndBack(response, "작성자 본인이 아니거나 비밀번호 오류입니다. 로그인 시 입력한 비밀번호를 입력하세요. 이전 페이지로 돌아갑니다.");
			return null;
		}
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
		return null;
	}

}

