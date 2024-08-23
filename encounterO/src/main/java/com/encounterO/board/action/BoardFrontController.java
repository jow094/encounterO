package com.encounterO.board.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.board.db.BoardDTO;
import com.encounterO.board.db.BoardDAO;
import com.encounterO.member.action.MemberJoinAction;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;

@WebServlet("*.bo")
public class BoardFrontController extends HttpServlet{
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" C : BoardFrontController - doProcess() 호출 \n\n");
		
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
		
		if(command.equals("/BoardWrite.bo")) {
			System.out.println(" C : /BoardWrite.bo 매핑");
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
			forward.setPath("./board/boardWrite.jsp");
			forward.setIsRedirect(false);
			
			System.out.println(" C : "+ forward);
			}
		}else if(command.equals("/BoardWriteAction.bo")) {
			System.out.println(" C : /BoardWriteAction.bo 매핑");
			System.out.println(" C : 패턴 2 - (페이지 이동, 액션존재)");
			
			//MemberJoinAction 객체 생성
			action = new BoardWriteAction();
			//객체의 execute()메서드 호출
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}else if(command.equals("/BoardList.bo")) {
			System.out.println(" C : /BoardList.bo 매핑");
			System.out.println(" C : 패턴 3 - (페이지 이동, 액션존재, 결과표현)");
			
			//MemberJoinAction 객체 생성
			action = new BoardListAction();
			//객체의 execute()메서드 호출
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}else if(command.equals("/BoardContent.bo")) {
			System.out.println(" C : /BoardContent.bo 매핑");
			System.out.println(" C : 패턴 3 - (페이지 이동, 액션존재, 결과표현)");
			
			//MemberJoinAction 객체 생성
			action = new BoardContentAction();
			//객체의 execute()메서드 호출
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}else if(command.equals("/BoardUpdate.bo")) {
			System.out.println(" C : /BoardUpdate.bo 매핑");
			System.out.println(" C : 패턴 3 - (페이지 이동, 액션존재, 결과표현)");
			
			action = new BoardUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}else if(command.equals("/BoardUpdateProAction.bo")) {
			System.out.println(" C : /BoardUpdateProAction.bo 매핑");
			System.out.println(" C : 패턴 2 - (페이지 이동, 액션존재)");
			
			action = new BoardUpdateProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}else if(command.equals("/BoardDelete.bo")) {
			System.out.println(" C : /BoardDelete.bo 매핑");
			System.out.println(" C : 패턴 1 - DB 사용 없이 단순 페이지 이동");
			
			forward = new ActionForward();
			forward.setPath("./board/boardDelete.jsp");
			forward.setIsRedirect(false);
			
			System.out.println(" C : "+ forward);
			}	
		else if(command.equals("/BoardDeleteAction.bo")) {
			System.out.println(" C : /BoardDeleteAction.bo 매핑");
			System.out.println(" C : 패턴 2 - (페이지 이동, 액션존재)");
			
			action = new BoardDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}
		else if(command.equals("/RepleWriteAction.bo")) {
			System.out.println(" C : /RepleWriteAction.bo 매핑");
			
			action = new RepleWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}
		else if(command.equals("/BoardReWrite.bo")) {
			System.out.println(" C : /BoardReWrite.bo 매핑");
			System.out.println(" C : 패턴 1 - (페이지 이동, 액션존재)");

			forward = new ActionForward();
			forward.setPath("./board/boardReWrite.jsp");
			forward.setIsRedirect(false);
		}
		else if(command.equals("/BoardReWriteAction.bo")) {
			System.out.println(" C : /BoardReWriteAction.bo 매핑");
			System.out.println(" C : 패턴 2 - (DB 사용, 페이지 이동, 액션존재)");

			action = new BoardReWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}
		else if(command.equals("/BoardLikeAction.bo")) {
			System.out.println(" C : /BoardLikeAction.bo 매핑");
			System.out.println(" C : 패턴 2 - (DB 사용, 페이지 이동, 액션존재)");
			
			action = new BoardLikeAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}
		else if(command.equals("/BoardFileWrite.bo")) {
			System.out.println(" C : /BoardFileWrite.bo 매핑");
			System.out.println(" C : 패턴 1 - (페이지 이동)");
			
			forward = new ActionForward();
			forward.setPath("./board/boardFileWrite.jsp");
			forward.setIsRedirect(false);
		}
		else if(command.equals("/BoardFileWriteAction.bo")) {
			System.out.println(" C : /BoardFileWriteAction.bo 매핑");
			System.out.println(" C : 패턴 2 - (페이지 이동,DB 사용)");
			
			action = new BoardFileWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" C : "+forward);
		}else if(command.equals("/BoardFileDownLoadAction.bo")) {
			System.out.println(" C : /BoardFileDownLoadAction.bo 매핑");
			System.out.println(" C : 패턴 3 - (페이지 이동,작업 존재)");
			
			action = new BoardFileDownLoadAction();
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
		System.out.println(" C : BoardFrontController - doGet() 호출");
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(" C : BoardFrontController - doPost() 호출");
		doProcess(request, response);
	}
	
	

	
	
}
