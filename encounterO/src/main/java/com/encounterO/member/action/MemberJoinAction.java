package com.encounterO.member.action;

import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.member.db.MemberDAO;
import com.encounterO.member.db.MemberDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;
import com.encounterO.util.JSFunction;

public class MemberJoinAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberJoinAction_execute() 실행");
		
		// 한글처리 인코딩  /  전달정보(파라메터)저장  /  DB연결-insert 처리  /  로그인 페이지로 이동
		
		// 한글처리 인코딩
		request.setCharacterEncoding("UTF-8");
		MemberDTO dto = new MemberDTO();
		 
		 if (!request.getParameter("id").matches("^[a-zA-Z0-9]{4,15}$")) {
		        JSFunction.alertAndBack(response, "아이디는 4자리 이상, 15자리 이하의 영문 및 숫자 조합이어야 합니다.");
		        return null;
		    }
		 
		 if (request.getParameter("id").toLowerCase().contains("admin")) {
		        JSFunction.alertAndBack(response, "아이디에 사용할 수 없는 문구가 포함되어 있습니다.");
		        return null;
		    }
		 
	     if (!request.getParameter("pw").matches("^.{6,20}$")) {
	            JSFunction.alertAndBack(response, "비밀번호는 6자리 이상, 20자리 이하여야 합니다.");
	            return null;
	        }
	     
	     if (!request.getParameter("pw").equals(request.getParameter("pw2"))){
	            JSFunction.alertAndBack(response, "입력한 비밀번호가 서로 맞지 않습니다. 같은 비밀번호를 다시 입력해주세요.");
	            return null;
	        }
	     
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
	     
	     if(request.getParameter("email1").equals("")||request.getParameter("email2").equals("")) {
	    	 JSFunction.alertAndBack(response, "이메일을 입력해주세요.");
	    	 return null;
	     }else {
	 		if (!(request.getParameter("email1")+"@"+request.getParameter("email2")).matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
	 			JSFunction.alertAndBack(response, "유효한 이메일이 아닙니다.");
	 			return null;
		    }
	     }
	     
		dto.setId(request.getParameter("id"));
		dto.setPw(request.getParameter("pw"));
		dto.setName(request.getParameter("name"));
		dto.setAge(Integer.parseInt(request.getParameter("age")));
		dto.setGender(request.getParameter("gender"));
		dto.setPhone(request.getParameter("phone"));
		dto.setEmail1(request.getParameter("email1"));
		dto.setEmail2(request.getParameter("email2"));
		dto.setRegdate(new Timestamp(System.currentTimeMillis()));
		
		System.out.println(" M : 전달받은 정보 : "+dto);
		
		// DB연결-insert 처리 → DAO 객체 생성
		MemberDAO dao = new MemberDAO();
		if(dao.isDuplicateId(dto)) {
			 JSFunction.alertAndBack(response, "이미 사용중인 아이디입니다.");
			 return null;
		}else if(dao.isDuplicateName(dto)) {
		 	 JSFunction.alertAndBack(response, "이미 사용중인 회원명입니다.");
		 	 return null;
		}else if(dao.isDuplicatePhone(dto)) {
		 	 JSFunction.alertAndBack(response, "이미 사용중인 전화번호입니다.");
		 	 return null;
		}else if(dao.isDuplicateEmail(dto)) {
		 	 JSFunction.alertAndBack(response, "이미 사용중인 이메일입니다.");
		 	 return null;
		}
		
		dao.insertMember(dto);
		System.out.println(" M : dao.insertMember 작동 완료!");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out = response.getWriter();
		out.println("<script>");
		out.println("alert('회원가입에 성공했습니다!');");
		out.println("location.href='./Login.me';"); // 경고창 후 페이지 이동
		out.println("</script>");
		out.close();
		
		HttpSession session = request.getSession();
		if(session.getAttribute("id")!=null) {
			 MemberDAO mdao = new MemberDAO();
		     mdao.rememberMember((String)session.getAttribute("id"), request);
			}
		return null;
	}
	
}
