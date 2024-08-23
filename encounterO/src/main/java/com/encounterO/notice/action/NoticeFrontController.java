package com.encounterO.notice.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.notice.db.NoticeContentDTO;
import com.encounterO.notice.db.NoticeDAO;
import com.encounterO.member.action.MemberJoinAction;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;

@WebServlet("*.no")
public class NoticeFrontController extends HttpServlet{
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" C : NoticeFrontController - doProcess() 호출 \n\n");
		
		System.out.println(" C : ----------------1. 가상주소 계산 - 시작 -----------------");
		String requestURI = request.getRequestURI();
		System.out.println(" C : requestURI : "+ requestURI);
		String CTXPath = request.getContextPath();
		System.out.println(" C : CTXPath : "+CTXPath);
		String command = requestURI.substring(CTXPath.length());
		System.out.println(" C : command : "+command);
		System.out.println(" C : ----------------1. 가상주소 계산 - 끝 -------------------\n");
		
		System.out.println(" C : ----------------2. 가상주소 매핑 - 시작 -----------------");
		
		ActionForward forward = null;
		Action action = null;
		
		if(command.equals("/NoticeWrite.no")) {
			System.out.println(" C : /NoticeWrite.no 매핑");
			System.out.println(" C : 패턴 1 - DB 사용 없이 단순 페이지 이동");
			
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
			}else {
			
			forward = new ActionForward();
			forward.setPath("./notice/noticeWrite.jsp");
			forward.setIsRedirect(false);
			
			System.out.println(" C : "+ forward);
			}
		}else if(command.equals("/NoticeWriteAction.no")) {
			System.out.println(" C : /NoticeWriteAction.no 매핑");
			System.out.println(" C : 패턴 2 - (페이지 이동, 액션존재)");
			
			//MemberJoinAction 객체 생성
			action = new NoticeWriteAction();
			//객체의 execute()메서드 호출
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}else if(command.equals("/NoticeList.no")) {
			System.out.println(" C : /NoticeList.no 매핑");
			System.out.println(" C : 패턴 3 - (페이지 이동, 액션존재, 결과표현)");
			
			//MemberJoinAction 객체 생성
			action = new NoticeListAction();
			//객체의 execute()메서드 호출
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}else if(command.equals("/NoticeContent.no")) {
			System.out.println(" C : /NoticeContent.no 매핑");
			System.out.println(" C : 패턴 3 - (페이지 이동, 액션존재, 결과표현)");
			
			//MemberJoinAction 객체 생성
			action = new NoticeContentAction();
			//객체의 execute()메서드 호출
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}else if(command.equals("/NoticeUpdate.no")) {
			System.out.println(" C : /NoticeUpdate.no 매핑");
			System.out.println(" C : 패턴 3 - (페이지 이동, 액션존재, 결과표현)");
			
			action = new NoticeUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}else if(command.equals("/NoticeUpdateProAction.no")) {
			System.out.println(" C : /NoticeUpdateProAction.no 매핑");
			System.out.println(" C : 패턴 2 - (페이지 이동, 액션존재)");
			
			action = new NoticeUpdateProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}else if(command.equals("/NoticeDelete.no")) {
			System.out.println(" C : /NoticeDelete.no 매핑");
			System.out.println(" C : 패턴 1 - DB 사용 없이 단순 페이지 이동");
			
			forward = new ActionForward();
			forward.setPath("./notice/noticeDelete.jsp");
			forward.setIsRedirect(false);
			
			System.out.println(" C : "+ forward);
			}	
		else if(command.equals("/NoticeDeleteAction.no")) {
			System.out.println(" C : /NoticeDeleteAction.no 매핑");
			System.out.println(" C : 패턴 2 - (페이지 이동, 액션존재)");
			
			action = new NoticeDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}
		
		System.out.println(" C : ----------------2. 가상주소 매핑 - 끝 -------------------\n");
		
		System.out.println(" C : ----------------3. 가상주소 이동 - 시작 -----------------");
		
		if(forward != null) {
			System.out.println(" C : : "+forward.isRedirect()+"방식으로"+forward.getPath()+"주소로 이동합니다.");
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			}else {
				RequestDispatcher dis = 
						request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
				
			}
		}
		
		System.out.println(" C : ----------------3. 가상주소 이동 - 끝 -------------------\n");
		
		System.out.println(" C : ------------------- doProcess() - 끝 --------------------");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" C : NoticeFrontController - doGet() 호출");
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" C : NoticeFrontController - doPost() 호출");
		doProcess(request, response);
	}
	
	

	
	
}
