package com.encounterO.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.member.db.MemberDAO;
import com.encounterO.member.db.MemberDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;
import com.encounterO.util.JSFunction;

public class MemberUpdateProAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
System.out.println(" M : MemberUpdateProAction_execute() 실행");
		
		// 한글처리 인코딩  /  전달정보(파라메터)저장  /  DB연결-insert 처리  /  로그인 페이지로 이동
		
		// 로그인 여부 체크
				HttpSession session = request.getSession();
				String id = (String) session.getAttribute("id");
				
				ActionForward forward = new ActionForward();
				if(id == null) {
					forward.setPath("./MemberLogin.me");
					forward.setIsRedirect(true);
					return forward;
				}
		
		// 한글처리 인코딩
		request.setCharacterEncoding("UTF-8");
		
		if (request.getParameter("name").length() < 2 || request.getParameter("name").length()>11) {
    	    JSFunction.alertAndBack(response, "회원명은 2자리 이상, 10자리 이하여야 합니다.");
    	    return null;
    	}else if(request.getParameter("name").toLowerCase().contains("admin")) {
	        JSFunction.alertAndBack(response, "회원명에 사용할 수 없는 문구가 포함되어 있습니다.");
	        return null;
	    }
	     
	     if(request.getParameter("age").equals("")) {
	    	 JSFunction.alertAndBack(response, "나이를 입력해주세요.");
	    	 return null;
	     }else {
		     int age = Integer.parseInt(request.getParameter("age"));
		     if (age<14||age>=65){
			        JSFunction.alertAndBack(response, "14세 이상, 65세 미만만 가입 가능합니다.");
			        return null;
			 }
	     }
	     
	     if (!request.getParameter("phone").matches("^010[2-9][0-9]{7}$")) {
		        JSFunction.alertAndBack(response, "유효한 전화번호가 아닙니다.");
		        return null;
		    }
	     
	     if(request.getParameter("email1").equals("")&&request.getParameter("email2").equals("")) {
	    	 JSFunction.alertAndBack(response, "이메일을 입력해주세요.");
	    	 return null;
	     }else {
	 		if (!(request.getParameter("email1")+"@"+request.getParameter("email2")).matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
	 			JSFunction.alertAndBack(response, "유효한 이메일이 아닙니다.");
	 			return null;
		    }
	     }
		
		
		
		// 전달정보(파라메터)저장 → MemberDTO 객체를 이용해보자.
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPw(request.getParameter("pw"));
		dto.setName(request.getParameter("name"));
		dto.setGender(request.getParameter("gender"));
		dto.setAge(Integer.parseInt(request.getParameter("age")));
		dto.setPhone(request.getParameter("phone"));
		dto.setEmail1(request.getParameter("email1"));
		dto.setEmail2(request.getParameter("email2"));
		
		System.out.println(" M : 수정할 내용 "+dto);
		
		// MemberDAO 객체생성
		MemberDAO dao = new MemberDAO();
		
		if(!request.getParameter("name").equals(session.getAttribute("name"))) {
			if(dao.isDuplicateName(dto)) {
				JSFunction.alertAndBack(response, "이미 사용중인 회원명입니다.");
				return null;
			}
		}
			
		if(!(request.getParameter("prevEmail1")+request.getParameter("prevEmail2")).equals(request.getParameter("email1")+request.getParameter("email2"))) {
			if(dao.isDuplicateEmail(dto)) {
				JSFunction.alertAndBack(response, "이미 사용중인 이메일입니다.");
			 	return null;
			}
		}
		
		// 회원정보를 수정하는 메서드(동작)실행
		int result = dao.updateMember(dto);
		System.out.println(" M : result : " + result);

		// 수정 결과에 따른 페이지 이동(JS) 실행
		if(result == -1) { // 아이디 정보 없음
			JSFunction.alertAndBack(response, "아이디 정보가 없어 수정에 실패하였습니다. 이전 페이지로 돌아갑니다.");
			return null;
		}
		else if(result == 0) { // 비밀번호 틀림
			JSFunction.alertAndBack(response, "비밀번호 오류로 수정에 실패하였습니다. 이전 페이지로 돌아갑니다.");
			return null;
		}
		else {
			session.setAttribute("name", dto.getName());
			JSFunction.alertAndLocation(response, "회원정보 수정에 성공하였습니다. 메인 페이지로 이동합니다.", "./Main.me");
		}
		
		
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
		return null;
	}

}

