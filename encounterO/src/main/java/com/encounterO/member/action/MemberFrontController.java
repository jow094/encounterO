package com.encounterO.member.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;

public class MemberFrontController extends HttpServlet{

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" C : Member Controller가 실행되었습니다.");
		System.out.println(" C : -----------1. 가상주소 계산 - 시작-----------");
		String requestURI = request.getRequestURI();
		System.out.println(" C : requestURI :"+requestURI);
		String CTXPath = request.getContextPath();
		System.out.println(" C : CTXPath :"+CTXPath);
		String command = requestURI.substring(CTXPath.length());
		System.out.println(" C : command :"+command);
		System.out.println(" C : -----------1. 가상주소 계산 - 완료-----------");
		System.out.println(" C : -----------2. 가상주소 매핑 - 시작-----------");
		Action action = null;
		ActionForward forward = null; 
		if(command.equals("/Main.me")) {
			System.out.println(" C : /Main.me 매핑");
			System.out.println(" C : 패턴 1 - (단순 페이지 이동)");
			
			forward = new ActionForward();
			forward.setPath("./member/main.jsp");
			forward.setIsRedirect(false);
			System.out.println(" C : "+forward);
		}else if(command.equals("/Login.me")){
			System.out.println(" C : /Login.me 매핑");
			System.out.println(" C : 패턴 1 - (단순 페이지 이동)");
			
			forward = new ActionForward();
			forward.setPath("./member/loginForm.jsp");
			forward.setIsRedirect(false);
			System.out.println(" C : "+forward);	
		}else if(command.equals("/MemberJoin.me")){
			System.out.println(" C : /MemberJoin.me 매핑");
			System.out.println(" C : 패턴 1 - (단순 페이지 이동)");
			
			forward = new ActionForward();
			forward.setPath("./member/insertForm.jsp");
			forward.setIsRedirect(false);
			System.out.println(" C : "+forward);
		}else if(command.equals("/MemberJoinAction.me")) {
			System.out.println(" C : /MemberJoinAction.me 매핑");
			System.out.println(" C : 패턴 2 - (페이지 이동, 액션존재)");
			
			//MemberJoinAction 객체 생성
			action = new MemberJoinAction();
			//객체의 execute()메서드 호출
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}else if(command.equals("/MemberLoginAction.me")) {
			System.out.println(" C : /MemberLoginAction.me 매핑");
			System.out.println(" C : 패턴 2 - (페이지 이동, 액션존재)");
			
			action = new MemberLoginAction();
			//객체의 execute()메서드 호출
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}else if(command.equals("/Logout.me")) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그아웃 되었습니다.');");
			out.println("location.href='./Main.me';");
			out.println("</script>");
			out.close();
		}else if(command.equals("/MemberList.me")) {
				System.out.println(" C : /MemberList.me 매핑");
				System.out.println(" C : 패턴 3 - (페이지 이동, 액션존재, 결과 표시)");
				
				//MemberJoinAction 객체 생성
				action = new MemberListAction();
				//객체의 execute()메서드 호출
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(" C : "+forward);
		}else if(command.equals("/MemberUpdate.me")) {
			System.out.println(" C : /MemberUpdate.me 매핑");
			System.out.println(" C : 패턴 3 - DB를 사용하여 페이지에 데이터 출력");
			
			//MemberInfoAction() 객체 생성
			action = new MemberUpdateAction();
			//객체의 execute()메서드 호출
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/MemberUpdateProAction.me")) {
			System.out.println(" C : /MemberUpdateProAction.me 매핑");
			System.out.println(" C : 패턴 2 - DB를 사용함.");
			
			//MemberInfoAction() 객체 생성
			action = new MemberUpdateProAction();
			//객체의 execute()메서드 호출
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MemberDelete.me")) {
			System.out.println(" C : /MemberDelete.me 매핑");
			System.out.println(" C : 패턴 1 - DB를 사용하지 않음. 실제 삭제는 pro페이지에서 수행, 거기로 이동만 하자");
			
			forward = new ActionForward();
			forward.setPath("./member/deleteForm.jsp");
			forward.setIsRedirect(false);
			System.out.println(" C : "+forward);
		}else if(command.equals("/MemberDeleteAction.me")) {
			System.out.println(" C : /MemberDeleteAction.me 매핑");
			System.out.println(" C : 패턴 2 - DB를 사용한 뒤 페이지를 이동한다");
			
			// MemberDeleteAction() 객체 생성
			action = new MemberDeleteAction();
			//객체의 execute()메서드 호출
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(" C : -----------2. 가상주소 매핑 - 완료-----------");
		System.out.println(" C : -----------3. 가상주소 이동 - 시작-----------");
		if(forward != null) { // 이동정보 객체인 티켓이 있다는 의미.
			System.out.println(" C : "+forward.isRedirect()+" 방식으로 "+forward.getPath()+"로 이동");
			if(forward.isRedirect()) { // 주소창에 표시되는 주소를 바꿀거면 sendRedirect, 아니면 RequestDispatcher 사용
				response.sendRedirect(forward.getPath());
			}else {
				RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
			}
		}
		System.out.println(" C : -----------3. 가상주소 이동 - 완료-----------");
		
		System.out.println(" C : Member Controller 실행 종료 \n\n");
	}//doProcess
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
}
