package com.encounterO.notice.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.notice.db.NoticeDAO;
import com.encounterO.notice.db.NoticeDTO;
import com.encounterO.member.db.MemberDAO;
import com.encounterO.member.db.MemberDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;

public class NoticeListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : NoticeListAction_execute() 실행");
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		if(id == null) {
			response.setContentType("text/html; charset=UTF-8");
			//response.getWriter().append("Hello!");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('먼저 로그인해주세요!.');");
			out.println("location.href='./Login.me';"); // 경고창 후 페이지 이동
			out.println("</script>");
		}
		
		ActionForward forward = new ActionForward();

		NoticeDAO dao = new NoticeDAO();
		//저장된 글의 개수 조회
		int count = dao.getNoticeCount();
		System.out.println(" M : count : "+count);
		System.out.println(" M : ---------------페이징처리 1 시작 ----------------");
		
		int pageSize = 10; // 한 페이지에서 출력할 글의 개수
		String pageNum = request.getParameter("pageNum"); // 현 페이지가 몇페이지인지 체크
		if(pageNum==null) {
			pageNum="1";
		}
		int currentPage=(int)Double.parseDouble(pageNum);
		//시작행 번호 계산
		int startRow= (currentPage-1)*pageSize + 1;
		//끝 행 번호 계산
		int endRow= currentPage*pageSize;
		//총 페이지 개수 계산
		int lastPage= (int)(count+9)/10;
		
		request.setAttribute("currentPage",currentPage);
		request.setAttribute("lastPage",lastPage);
		
		request.setAttribute("count",count);
		request.setAttribute("pageSize",pageSize);
		request.setAttribute("startRow",startRow);
		request.setAttribute("endRow",endRow);
		request.setAttribute("pageNum",pageNum);
		
		System.out.println(" M : ---------------페이징처리 1 시작 ----------------");
		List<NoticeDTO> noticeList = null;
		
		if(count>0) {
			noticeList = dao.getNoticeList(startRow,10);
			System.out.println(" M : 조회된 목록 개수 : "+noticeList.size());
		}
		System.out.println(" M : ---------------페이징처리 2 시작 ----------------");
		// 페이징 블럭 처리
		// 게시판 글 개수에 맞는 페이지 개수
		//int pageCount = (count/pageSize)+(count%pageSize==0?0:1) ;
		int pageCount = (int)((count/pageSize)+0.9) ;
		
		// 한 페이지에 출력될 페이징 블럭의 크기
		int pageBlock=10;
		// 페이지 블럭 시작번호 계산
		// 1 11 21 31 ...
		int startPage = ( (currentPage-1) / pageBlock) * pageBlock + 1;
		
		// 페이지 블럭의 끝 번호 계산
		// 10 20 30 40 ...
		int endPage = startPage + pageBlock - 1; 	// 10페이지까지도 필요없을때 에러발생함. 그래서 다음 제어문으로 endPage>pageCount 일때를 해결함
		if(endPage>pageCount) {
			endPage = pageCount;
		}
		
		request.setAttribute("pageCount",pageCount);
		request.setAttribute("pageBlock",endRow);
		request.setAttribute("startPage",startPage);
		request.setAttribute("endPage",endPage);
		request.setAttribute("noticeList",noticeList);
		
		System.out.println(" M : ---------------페이징처리 2 시작 ----------------");
		
		//세션영역에 조회수 증가 상태를 저장
		session = request.getSession();
		session.setAttribute("isUpdate",true);
		forward.setPath("./notice/noticeList.jsp");
		forward.setIsRedirect(false);
		
		return forward;
	}

	
}
