package com.encounterO.notice.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.notice.db.NoticeContentDTO;
import com.encounterO.notice.db.NoticeDAO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;

public class NoticeContentAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : NoticeContentAction_execute() 실행");
		// 작성된 글의 내용을 화면에 출력
		// DAO 객체가 할 일 : 조회수 1 증가(update), 작성된 글의 정보를 가져오기(select)

		ActionForward forward = new ActionForward();
				
				int bno = Integer.parseInt(request.getParameter("bno"));
				int contentNum = Integer.parseInt(request.getParameter("contentNum"));
				System.out.println(" M : bno : "+bno);
				
				// bno에 해당하는 조회수를 증가시키는 메서드를 만들자
				NoticeDAO dao = new NoticeDAO();
				// 조회수가 증가해도 되는 상태인지 체크
				HttpSession session = request.getSession();
				boolean isUpdate = (Boolean)session.getAttribute("isUpdate");
				if(isUpdate) {
					dao.updateReadcount(bno);
					session.setAttribute("isUpdate", false);
				}
				
				// bno에 해당하는 게시물의 내용을 조회하는 메서드를 만들자
				NoticeContentDTO dto = dao.getNoticeContent(bno);
				
		        // 줄바꿈 문자를 <br> 태그로 변환
		        String contentWithBreaks = dto.getContent().replaceAll("\n", "<br>");
		        dto.setContent(contentWithBreaks);

				request.setAttribute("dto", dto);
				request.setAttribute("bno", bno);
				request.setAttribute("contentNum", contentNum);
				
				
				
				forward.setPath("./notice/noticeContent.jsp");
				forward.setIsRedirect(false);
				
				return forward;
		
		
		
	}
}
