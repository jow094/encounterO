package com.encounterO.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.encounterO.board.db.BoardDAO;
import com.encounterO.board.db.BoardDTO;
import com.encounterO.member.db.MemberDAO;
import com.encounterO.member.db.MemberDTO;
import com.encounterO.util.Action;
import com.encounterO.util.ActionForward;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardFileWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : BoardFileWriteAction_execute 호출");
		
		
		// 파일 업로드 :
		// 1) 파일이 저장될 장소 설정 (webapp/upload)
		// 실제 작업파일 위치 :
		// D:\workspace_encounterO\encounterO\src\main\webapp
		// realPath : 서버에 배포된 작업파일 위치
		
		String realPath = request.getRealPath("/upload");
		System.out.println(" M : realPath : "+realPath);
		
		// 2) 업로드 할 파일의 크기제한 (5MB)
		// bit -> byte -> KB -> MB
		int maxSize = 5 * 1024 * 1024; // byte 기준이며, 5MB
		// int maxSize = 5242880;
		
		// 3) 파일 업로드
		MultipartRequest multi = new MultipartRequest(
														request,
														realPath,
														maxSize,
														"UTF-8",
														new DefaultFileRenamePolicy()
														);
		// request : request 영역객체 정보를 전달 (+Parameter)
		// realPath : 서버에 배포된 업로드 폴더의 위치
		// maxSize : 업로드시 파일 크기 제한
		
		System.out.println(" M : 파일 업로드 성공");
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		MemberDAO mdao = new MemberDAO();
		MemberDTO mdto = mdao.getMember(id);
		mdao.rememberMember(id,request);
		
		BoardDTO bdto = new BoardDTO();
		bdto.setId(id);
		bdto.setName(mdto.getName());
		bdto.setSubject(multi.getParameter("subject"));
		bdto.setContent(multi.getParameter("content"));
		bdto.setIp(request.getRemoteAddr());
		
		bdto.setFile(multi.getFilesystemName("file"));
		System.out.println(" M : dto : " + bdto);
		System.out.println(" M : oFile : " + multi.getOriginalFileName("file"));
		
		BoardDAO bdao = new BoardDAO();
		
		bdao.insertBoard("comm", bdto);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./BoardList.bo");
		forward.setIsRedirect(true);
		return forward;
	}
}
