package com.encounterO.notice.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.encounterO.member.db.MemberDTO;
import com.mysql.cj.Session;

public class NoticeDAO {
	//공통사용 변수 선언
	//공통변수 선언
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	
	//디비 연결 메서드 - getConnect()
	private Connection getConnect() throws Exception {
		// 디비 연결정보 : META - INF/context.xml 에 저장해두었음.
		Context initCTX = new InitialContext();
		DataSource ds = (DataSource) initCTX.lookup("java:comp/env/jdbc/MVC");
		// 드라이버 로드 및 디비연결을 한 동작으로 합쳐보자
		con = ds.getConnection();
		System.out.println(" DAO : 디비 연결 성공 (CP)");
		System.out.println(" DAO : "+con);
		
		return con;
	}
	
	// 디비 자원해제 메서드 - closeDB();
	public void closeDB() {
		System.out.println(" DAO : 자원해제 코드 - 시작");
		
		try {
			// 변수에 값이 대입된 역순으로 해제하자.
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(" DAO : 자원해제 코드 - 끝");
		
	}// 디비 자원해제 메서드 - closeDB();
	
	
	//게시판 글쓰기
	public void insertNotice(NoticeDTO dto) {
		
		// 3. sql 구분 작성 및 pstmt 객체 생성
		try {
			con = getConnect();
//			int bno = 0;                                                    auto increment 미작성시
//			String sql = "select max(bno) from notice";
//			pstmt = con.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				//bno = rs.getInt(1) + 1;  인덱스로 불러와도 됨
//				bno = rs.getInt("max(bno)") + 1;
//			}
////			else {					해당 구문은 무조건 true라서 else 필요없음0. rs.next는 커서를 하나 내렸을때 데이터가 있느냐를 묻는건데, 컬럼을 그냥 찾으면 null이면 값이 없다고 리턴하지만
////				bno = 1; 			함수의 결과를 도출할때는 "없다" 라는 값이 리턴되어 값이 있는것으로 해석한다.
////			}
//			
//			sql = "insert into notice(bno, name, pass, subject, content) values (?,?,?,?,?)";
//			pstmt = con.prepareStatement(sql);
//			
//			pstmt.setInt(1,bno); 
//			pstmt.setString(2,dto.getName()); 
//			pstmt.setString(3,dto.getPass()); 
//			pstmt.setString(4,dto.getSubject()); 
//			pstmt.setString(5,dto.getContent());
			
			sql = "select pw from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,dto.getId());
			rs=pstmt.executeQuery();
			String pw = null;
			if(rs.next()) {
				pw = rs.getString("pw");
			}
			
			sql = "insert into notice(id, name, pass, subject, content,readcount,re_ref,re_lev,re_seq,date,ip,file) values (?,?,?,?,?,?,?,?,?,now(),?,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1,dto.getId()); 
			pstmt.setString(2,dto.getName()); 
			pstmt.setString(3,pw); 
			pstmt.setString(4,dto.getSubject()); 
			pstmt.setString(5,dto.getContent());
			pstmt.setInt(6,0);
			pstmt.setInt(7,0); // 그룹번호
			pstmt.setInt(8,0); // 레벨
			pstmt.setInt(9,0); // 시퀀스
			pstmt.setString(10,dto.getIp());
			pstmt.setString(11,dto.getFile());
			
			// 4. sql 구문 실행
			pstmt.executeUpdate();
			
			System.out.println(" DAO : 게시글 작성 완료!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}	//게시판 글쓰기
	}
	
	// 게시판에 저장된 전체 글 개수 조회 - getNoticeCount()
	public int getNoticeCount() {
		int count = 0;
		
		try {
			con = getConnect();
			sql = "select count(bno) from notice";
			//sql = "select count(*) from notice"; 전체요소 몇행인지 리턴
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {										// 함수를 받아오면 무조건 true. 없어도 없다는 값이 있기 때문
				count = rs.getInt(1);
				//count = rs.getInt("count(bno)");
			}
			
			System.out.println(" DAO : 전체 글 개수 : "+ count + "개");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}	//게시판 글쓰기
		
		return count;
	}// 게시판에 저장된 전체 글 개수 조회 - getNoticeCount()
	
	//게시판의 게시글 목록 조회 - getNoticeList()
	public List<NoticeDTO> getNoticeList() {
		List<NoticeDTO> noticeList = new ArrayList<NoticeDTO>();
		
		try {
			con = getConnect();
			sql = "select * from Notice order by bno desc limit 0,10";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {	// rs의 데이터를 dto로 옮겨담자             [rs → dto] → List 
				NoticeDTO dto = new NoticeDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setDate(rs.getDate("date"));
				dto.setIp(rs.getString("ip"));
				dto.setFile(rs.getString("file"));
				
				// DTO 하나를 List 한칸에 넣자
				noticeList.add(dto);
				
			} // while
			
			System.out.println(" DAO : 게시글 목록 조회 성공");
			System.out.println(" DAO : 게시글 목록 개수 : " + noticeList.size());
			
//			for(MemberDTO dto : memberList) {
//			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return noticeList;
	}//게시판의 게시글 목록 조회 - getNoticeList()
	
	
	public List<NoticeDTO> getNoticeList(int startRow,int pageSize) {
		List<NoticeDTO> noticeList = new ArrayList<NoticeDTO>();
		
		try {
			con = getConnect();
			sql = "select * from Notice order by bno desc limit "+(startRow-1)+", "+pageSize+"";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {	// rs의 데이터를 dto로 옮겨담자             [rs → dto] → List 
				NoticeDTO dto = new NoticeDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setDate(rs.getDate("date"));
				dto.setIp(rs.getString("ip"));
				dto.setFile(rs.getString("file"));
				
				// DTO 하나를 List 한칸에 넣자
				noticeList.add(dto);
				
			} // while
			
			System.out.println(" DAO : 게시글 목록 조회 성공");
			System.out.println(" DAO : 게시글 목록 개수 : " + noticeList.size());
			
//			for(MemberDTO dto : memberList) {
//			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return noticeList;
	}
	
	public void updateReadcount(int bno) { // 조회수 1 증가
		
			try {
				con = getConnect();
				sql = "update notice set readcount= readcount+1 where bno=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,bno);
				pstmt.executeUpdate();
					System.out.println(" DAO : 조회수 증가 완료!");
				}
				catch (Exception e) {
				e.printStackTrace();
				} finally {
				closeDB();
				}
			
			}//	조회수 1 증가
	
	public NoticeContentDTO getNoticeContent(int bno) { // getContent()
		try {
			con = getConnect();
			
			sql = "select *,"
				+"(select bno from notice where bno>? order by bno limit 1) as nextBno,"	
				+"(select bno from notice where bno<? order by bno desc limit 1) as prevBno,"
			    +"(select subject from notice where bno>? order by bno limit 1) as nextSub,"   
			    +"(select subject from notice where bno<? order by bno desc limit 1) as prevSub "			    
			    +"from notice where bno = ?";
				
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,bno);
			pstmt.setInt(2,bno);
			pstmt.setInt(3,bno);
			pstmt.setInt(4,bno);
			pstmt.setInt(5,bno);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				NoticeContentDTO dto = new NoticeContentDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setDate(rs.getDate("date"));
				dto.setIp(rs.getString("ip"));
				dto.setFile(rs.getString("file"));
				dto.setPrevBno(rs.getInt("prevBno"));
				dto.setNextBno(rs.getInt("nextBno"));
				dto.setPrevSub(rs.getString("prevSub"));
				dto.setNextSub(rs.getString("nextSub"));
			System.out.println(" DAO : 게시글 조회 성공 : "+dto.toString());
			return dto;			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}return null;
	}// getContent()
	
	public NoticeDTO getContent(int bno) { // getContent()
		try {
			con = getConnect();
			
			sql = "select * from notice where bno = ?";
				
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,bno);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setReadcount(rs.getInt("readcount"));
				dto.setDate(rs.getDate("date"));
				dto.setIp(rs.getString("ip"));
				dto.setFile(rs.getString("file"));
			System.out.println(" DAO : 게시글 조회 성공 : "+dto.toString());
			return dto;			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}return null;
	}// getContent()
	
	public int getPage(List<NoticeDTO> noticeList,int bno) { // getPage()
		
		int index = 0;
		 for (int i = 1; i <= noticeList.size(); i++) {
	            if (noticeList.get(i).getBno() == bno) {
	                index = i;
	            }
	        }
		return index;
		}// getPage()
	
	// 게시글 수정 - updateNotice(dto)
	public int updateNotice(NoticeDTO dto) {
		int result = -1;
		
			try {
				con = getConnect();
				
			sql = "select id,pass from notice where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBno());
			rs = pstmt.executeQuery();
				if(rs.next()) {
					if(dto.getId().equals(rs.getString("id")) && dto.getPass().equals(rs.getString("pass"))) {
						sql = "update notice set subject=?,content=? where bno=?";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, dto.getSubject());
						pstmt.setString(2, dto.getContent());
						pstmt.setInt(3, dto.getBno());
						result = pstmt.executeUpdate(); // 해당 메서드는 수정한 row의 개수를 return함
						System.out.println(" DAO : 게시글 수정 완료 ("+result+")");
					}else {
					result = -1;
					}
				}
				return result;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return -1;
	}
	// 회원정보 수정 - updateMember(dto)
	
	
	// 게시글 삭제 - deleteNotice(dto)
			public int deleteNotice(NoticeDTO dto) {
				int result = -1;  // -1: 아이디 X ,0:아이디O,비밀번호X ,1: 정상처리
				
				try {
					con = getConnect();
					sql = "select id,pass from notice where bno=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, dto.getBno());
					rs = pstmt.executeQuery();
					if(rs.next()) {
						if(dto.getId().equals(rs.getString("id")) && dto.getPass().equals(rs.getString("pass"))) {
							sql="delete from notice where bno=?";
							pstmt = con.prepareStatement(sql);
							pstmt.setInt(1,dto.getBno());
							result = pstmt.executeUpdate(); 
							System.out.println(" DAO : 게시글 삭제 완료 ("+result+")");
						}else {
						result = -1;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					closeDB();
				}
				
				return result;
			}
			// 게시글 삭제 - deleteNotice(dto)	
			
			
			
			
}
