package com.encounterO.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.notice.db.NoticeDAO;
import com.encounterO.notice.db.NoticeDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;
import com.encounterO.util.JSFunction;

public class NoticeUpdateProAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			System.out.println(" M : NoticeUpdateProAction_execute() 실행");
		
		NoticeDTO dto = new NoticeDTO();
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		dto.setBno(Integer.parseInt(request.getParameter("bno")));
		dto.setId(id);
		dto.setPass(request.getParameter("pass"));
		dto.setSubject(request.getParameter("subject"));
		dto.setContent(request.getParameter("content"));
		
		System.out.println(" M : 수정 요청된 내용 : "+dto);
		
		// MemberDAO 객체생성
		NoticeDAO dao = new NoticeDAO();
		
		int result = dao.updateNotice(dto);
		System.out.println(" M : result : " + result);

		// 수정 결과에 따른 페이지 이동(JS) 실행
		
		int bno = dto.getBno();
		
		if(result == 1) {
			JSFunction.alertAndLocation(response, "게시글 수정에 성공하였습니다.", "./NoticeContent.no?bno="+request.getParameter("bno")+"&pageNum="+request.getParameter("pageNum")+"&contentNum="+request.getParameter("contentNum")+"&count="+request.getParameter("count"));
		}else{ // 아이디 정보 없음
			JSFunction.alertAndBack(response, "작성자 본인이 아니거나 비밀번호 오류입니다. 로그인 시 입력한 비밀번호를 입력하세요. 이전 페이지로 돌아갑니다.");
			return null;
		}
		
		return null;
	}

}

