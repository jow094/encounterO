package com.encounterO.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.notice.db.NoticeContentDTO;
import com.encounterO.notice.db.NoticeDAO;
import com.encounterO.notice.db.NoticeDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;

public class NoticeUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : NoticeUpdateAction_execute() 호출");
		
		//수정 페이지에 기존의 사용자 정보를 출력
		
		// 전달 파라메터 저장 (bno, pageNum)
		int bno = Integer.parseInt(request.getParameter("bno"));
		String pageNum = request.getParameter("pageNum");
		System.out.println(" M : bno ="+bno+", pageNum="+pageNum);
			
		// NoticeDAO 객체 생성
				
		// 작성자 확인 생략
				
				ActionForward forward = new ActionForward();
				
				NoticeDAO dao = new NoticeDAO();
				
		// 특정 bno에 해당하는 글정보를 가져오는 메서드 호출
				NoticeContentDTO dto = dao.getNoticeContent(bno);
				System.out.println(" M : "+ dto);
				request.setAttribute("oldDto", dto);
				request.setAttribute("pageNum", pageNum);
				
				request.getParameter("bno");
				request.getParameter("pageNum");
				String contentNum = request.getParameter("contentNum");
				
		
		// 페이지 이동 준비 ./notice/noticeUpdate.jsp
				forward.setPath("./notice/noticeUpdate.jsp?bno="+bno+"&pageNum="+pageNum+"&contentNum="+contentNum);
				forward.setIsRedirect(false);
				
				return forward;
	}
}
	


