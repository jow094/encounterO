package com.encounterO.board.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.encounterO.member.db.MemberDTO;
import com.encounterO.util.Transaction;
import com.mysql.cj.Session;

public class BoardDAO {
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
		System.out.println(" BoardDAO : 디비 연결 성공");
		return con;
	} //디비 연결 메서드 - getConnect()
	
	// 디비 자원해제 메서드 - closeDB();
	public void closeDB() { 
		try {
			// 변수에 값이 대입된 역순으로 해제하자.
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(" boardDAO : 자원해제 완료");
		
	}// 디비 자원해제 메서드 - closeDB();
	
	// bno로 게시글 정보 불러오기
		public BoardDTO getInfo(int bno) { 
			System.out.println("boardDAO : getInfo 실행");
			try {
				con = getConnect();
				sql = "select * from board where bno=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, bno);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					BoardDTO dto = new BoardDTO();
					dto.setBno(rs.getInt("bno"));
					dto.setId(rs.getString("id"));
					dto.setName(rs.getString("name"));
					dto.setSubject(rs.getString("subject"));
					dto.setReadCount(rs.getInt("readcount"));
					dto.setRe_ref(rs.getInt("re_ref"));
					dto.setRe_lev(rs.getInt("re_lev"));
					dto.setRe_seq(rs.getInt("re_seq"));
					dto.setTime(rs.getTimestamp("time"));
					dto.setIp(rs.getString("ip"));
					dto.setFile(rs.getString("file"));
					dto.setLikeCount(rs.getInt("likecount"));
					dto.setEditCount(rs.getInt("editcount"));
					dto.setDirectory(rs.getString("directory"));
				System.out.println(" boardDAO : getInfo 완료 / "+dto.toString());
				return dto;			
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}	//게시판 글쓰기
		return null;
	}// bno로 게시글 정보 불러오기
		
	// 삭제,수정권한 부여 메서드
	public boolean authorize(String UserId,String WriterId) {
		boolean result = false;
		result = UserId.equals(WriterId)||UserId.equals("admin")?true:false;
		if(result=true) {
		System.out.println("boardDAO : authorize 실행 / 삭제,수정 권한을 가진 사용자입니다. ID:"+UserId);
		}
		return result;
	}// 삭제,수정권한 부여 메서드
	
	// 비밀번호 확인 메서드
	public boolean validatePw(String id,String pw) {
		boolean result = false;

		try {
			String realPw = null;
			con = getConnect();
			sql = "select pw from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,id);
			rs=pstmt.executeQuery();
				if(rs.next()) {
					realPw = rs.getString("pw");
				}else {
					System.out.println("boardDAO : validatePw 실행 / 오류가 발생하였습니다. id에 해당하는 pw가 없습니다.");
					return false;
				}
				
			result = pw.equals(realPw)? true : false ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				closeDB();
			}
		System.out.println("boardDAO : isValidPw 실행 결과 : "+result);
		return result;
	}// 비밀번호 확인 메서드
	
	//게시판 글쓰기
	public void insertBoard(String directory,BoardDTO dto) {
		System.out.println(" boardDAO : insertBoard 실행");
		try {
			con = getConnect();
			
			int bno = 0;
			sql = "select max(bno) from board where directory=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,directory); 			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bno = rs.getInt(1) + 1;
			}
			System.out.println(" boardDAO : insertBoard 실행 / "+directory+" 게시판에 접근 성공했습니다. 작성글 기대 bno : "+bno);
			sql = "insert into board(bno, id, name, subject, content, readcount, re_ref, time, ip, file, directory) values (?,?,?,?,?,?,?,now(),?,?,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1,bno); 
			pstmt.setString(2,dto.getId()); 
			pstmt.setString(3,dto.getName()); 
			pstmt.setString(4,dto.getSubject()); 
			pstmt.setString(5,dto.getContent());
			pstmt.setInt(6,0); // readcount
			pstmt.setInt(7,bno); // 그룹번호
			pstmt.setString(8,dto.getIp());
			pstmt.setString(9,dto.getFile());
			pstmt.setString(10,directory); 
			
			// 4. sql 구문 실행
			pstmt.executeUpdate();
			
			System.out.println(" boardDAO : insertBoard 완료 / "+dto);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}	//게시판 글쓰기
	}
	
	// 검색 결과 개수 조회
	public int getBoardCount(String directory) {
		System.out.println(" boardDAO : getBoardCount (검색어 없음) 실행");
		int count = 0;
		
		try {
			con = getConnect();
			sql = "select count(bno) from board where directory=? and deletecount=0";
			//sql = "select count(*) from board"; 전체요소 몇행인지 리턴
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,directory); 
			rs = pstmt.executeQuery();
			if(rs.next()) {										// 함수를 받아오면 무조건 true. 없어도 없다는 값이 있기 때문
				count = rs.getInt(1);
				//count = rs.getInt("count(bno)");
			}
			System.out.println(" boardDAO : getBoardCount 완료 / "+directory+" 게시판의 전체 글 개수 : "+ count + "개");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}	//게시판 글쓰기
		
		return count;
	}// 검색 결과 개수 조회
	
	// 검색 결과 개수 조회
	public int getBoardCount(String directory,String criteria,String keyWord) {
		System.out.println(" boardDAO : getBoardCount (검색어 있음) 실행 / "+criteria+"/"+keyWord+"검색");
		int count = 0;
		
		try {
			con = getConnect();
			sql = "select count(bno) from board "
					+ "where directory=? and "+criteria+" LIKE ? and deletecount=0";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, directory);
			pstmt.setString(2, "%" + keyWord + "%");
			rs = pstmt.executeQuery();
			if(rs.next()) {	
				count = rs.getInt(1);
				//count = rs.getInt("count(bno)");
			}
			System.out.println(" boardDAO : getBoardCount 완료 / "+directory+" 게시판의 "+criteria+"이(가) "+keyWord+"을(를) 포함하는 글 개수 : "+ count + "개");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}	
		
		return count;
		}// 검색결과 개수 조회
	
	// 내가 쓴 글 개수 조회
		public int getMyBoardCount(String id) {
			System.out.println(" boardDAO : getMyBoardCount (검색어 없음) 실행 / id : "+id+" 검색");
			int count = 0;
			
			try {
				con = getConnect();
				sql = "select count(bno) from board "
						+ "where id=? and deletecount=0";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				if(rs.next()) {	
					count = rs.getInt(1);
				}
				System.out.println(" boardDAO : getMyBoardCount (검색어 없음) 완료 / 검색결과 :"+count + "개");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}	
			
			return count;
			}
	// 내가 쓴 글 개수 조회
		
	// 내가 쓴 글 개수 조회
			public int getMyBoardCount(String id,String criteria,String keyWord) {
				System.out.println(" boardDAO : getMyBoardCount (검색어 있음) 실행 / id : "+id+" , "+criteria+" : "+keyWord);
				int count = 0;
				
				try {
					con = getConnect();
					sql = "select count(bno) from board "
							+ "where id=? and "+criteria+" like ? and deletecount=0";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, id);
					pstmt.setString(2, "%"+keyWord+"%");
					rs = pstmt.executeQuery();
					if(rs.next()) {	
						count = rs.getInt(1);
					}
					System.out.println(" boardDAO : getMyBoardCount (검색어 있음) 완료 /"+id+"가 작성한 글 중 "+criteria+"이(가) "+keyWord+"을(를) 포함하는 글 개수: "+count);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					closeDB();
				}	
				
				return count;
				}
		// 내가 쓴 글 개수 조회
		
	//내가 쓴 게시글 목록 조회
	public List<BoardDTO> getMyBoardList(String id,int startRow,int pageSize) {
		System.out.println(" boardDAO : getMyBoardList (검색어 없음) 실행 / id:"+id);
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		try {
			con = getConnect();
			sql =
			"select b.*, count(r.bno) as repleNum "
			+ "from board b left join reple r on b.bno = r.bno "
			+ "where b.id = ? and b.deletecount=0 group by b.bno "
			+ "order by re_ref desc, re_seq asc limit "+(startRow-1)+", "+pageSize;
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,id); 
			
			rs = pstmt.executeQuery();
			while(rs.next()) {	// rs의 데이터를 dto로 옮겨담자             [rs → dto] → List 
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setRepleNum(rs.getInt("repleNum"));
				dto.setReadCount(rs.getInt("readCount"));
				dto.setLikeCount(rs.getInt("likeCount"));
				dto.setEditCount(rs.getInt("editCount"));
				dto.setTime(rs.getTimestamp("time"));
				dto.setFile(rs.getString("file"));
				dto.setDirectory(rs.getString("directory"));
				boardList.add(dto);
			} // while
			System.out.println(" boardDAO : getBoardList (검색어 있음) 완료 / 검색결과 개수 : " + boardList.size());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return boardList;
		}
	//내가 쓴 게시글 목록 조회
	
	//내가 쓴 게시글 목록 조회
		public List<BoardDTO> getMyBoardList(String id,String criteria,String keyWord,int startRow,int pageSize) {
			System.out.println(" boardDAO : getMyBoardList (검색어 있음) 실행 / id : "+id+","+criteria+" : "+keyWord);
			List<BoardDTO> boardList = new ArrayList<BoardDTO>();
			
			try {
				con = getConnect();
				sql =
				"select b.*, count(r.bno) as repleNum "
				+ "from board b left join reple r on b.bno = r.bno "
				+ "where b.id = ? and b."+criteria+" LIKE ? and b.deletecount=0 group by b.bno "
				+ "order by re_ref desc, re_seq asc limit "+(startRow-1)+", "+pageSize;
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,id); 
				pstmt.setString(2,"%"+keyWord+"%"); 
				
				rs = pstmt.executeQuery();
				while(rs.next()) {	// rs의 데이터를 dto로 옮겨담자             [rs → dto] → List 
					BoardDTO dto = new BoardDTO();
					dto.setBno(rs.getInt("bno"));
					dto.setId(rs.getString("id"));
					dto.setName(rs.getString("name"));
					dto.setSubject(rs.getString("subject"));
					dto.setRe_ref(rs.getInt("re_ref"));
					dto.setRe_lev(rs.getInt("re_lev"));
					dto.setRe_seq(rs.getInt("re_seq"));
					dto.setRepleNum(rs.getInt("repleNum"));
					dto.setReadCount(rs.getInt("readCount"));
					dto.setLikeCount(rs.getInt("likeCount"));
					dto.setEditCount(rs.getInt("editCount"));
					dto.setTime(rs.getTimestamp("time"));
					dto.setFile(rs.getString("file"));
					dto.setDirectory(rs.getString("directory"));
					boardList.add(dto);
				} // while
				System.out.println(" boardDAO : getBoardList (검색어 있음) 완료 / "+id+" 가 작성한 글 중 "+criteria+"이(가) "+keyWord+"을(를) 포함하는 글 " + boardList.size() + "개를 불러옴.");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					closeDB();
				}
				
				return boardList;
			}
		//내가 쓴 게시글 목록 조회
	
	//게시판의 게시글 목록 조회
	public List<BoardDTO> getBoardList(String directory,int startRow,int pageSize) {
		System.out.println(" boardDAO : getBoardList (검색어 없음) 실행");
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		try {
			con = getConnect();
			sql =
			"select b.*, count(r.bno) as repleNum "
			+ "from board b left join reple r on b.bno = r.bno "
			+ "where b.directory = ? and b.deletecount=0 group by b.bno "
			+ "order by re_ref desc, re_seq asc limit "+(startRow-1)+", "+pageSize;
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,directory); 
			
			rs = pstmt.executeQuery();
			while(rs.next()) {	// rs의 데이터를 dto로 옮겨담자             [rs → dto] → List 
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setRepleNum(rs.getInt("repleNum"));
				dto.setReadCount(rs.getInt("readCount"));
				dto.setLikeCount(rs.getInt("likeCount"));
				dto.setEditCount(rs.getInt("editCount"));
				dto.setTime(rs.getTimestamp("time"));
				dto.setFile(rs.getString("file"));
				dto.setDirectory(rs.getString("directory"));
				boardList.add(dto);
			} // while
			System.out.println(" boardDAO : getBoardList 완료 / 불러온 "+directory+" 게시판의 글 개수 : " + boardList.size());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return boardList;
		}//게시판의 게시글 목록 조회
	
	//게시글 검색 결과 확인
	public List<BoardDTO> getBoardList(String directory,String criteria,String keyWord,int startRow,int pageSize) {
		System.out.println(" boardDAO : getBoardList (검색어 있음) 실행 / "+criteria+"/"+keyWord+"검색");
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		try {
			con = getConnect();
			sql =
					"select b.*, count(r.bno) as repleNum "
					+ "from board b left join reple r on b.bno = r.bno "
					+ "where b.directory = ? and b."+criteria+" LIKE ? and b.deletecount=0 group by b.bno "
					+ "order by re_ref desc, re_seq asc limit "+(startRow-1)+", "+pageSize;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,directory); 
			pstmt.setString(2,"%"+keyWord+"%"); 
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setRepleNum(rs.getInt("repleNum"));
				dto.setReadCount(rs.getInt("readCount"));
				dto.setLikeCount(rs.getInt("likeCount"));
				dto.setEditCount(rs.getInt("editCount"));
				dto.setTime(rs.getTimestamp("time"));
				dto.setFile(rs.getString("file"));
				dto.setDirectory(rs.getString("directory"));
				boardList.add(dto);
			} // while
			System.out.println(" boardDAO : getBoardList 완료 / 불러온 "+directory+" 게시판의 "+criteria+"이(가) "+keyWord+"을(를) 포함하는 글 : " + boardList.size());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return boardList;
	}
	//게시글 결과 확인
	
	public void updateReadCount(int bno) { // 조회수 1 증가
		System.out.println(" boardDAO : updateReadCount 실행");
		
			try {
				con = getConnect();
				sql = "update board set readcount= readcount+1 where bno=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,bno);
				pstmt.executeUpdate();
					System.out.println(" boardDAO : updateReadCount 완료 / "+bno+"번 게시글 조회수 1 증가");
				}
				catch (Exception e) {
				e.printStackTrace();
				} finally {
				closeDB();
				}
			
			}//	조회수 1 증가
	
	//게시글 조회
	public BoardDTO getBoardContent(String directory,int bno) {
		System.out.println(" boardDAO : getBoardContent 실행");
		try {
			con = getConnect();
			
			sql = "select *,"
				+"(select bno from board where directory=? and bno>? and deletecount=0 order by bno limit 1) as nextBno,"	
				+"(select bno from board where directory=? and bno<? and deletecount=0 order by bno desc limit 1) as prevBno,"
			    +"(select subject from board where directory=? and bno>? and deletecount=0 order by bno limit 1) as nextSub,"   
			    +"(select subject from board where directory=? and bno<? and deletecount=0 order by bno desc limit 1) as prevSub "			    
			    +"from board where bno = ?";
				
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,directory);
			pstmt.setInt(2,bno);
			pstmt.setString(3,directory);
			pstmt.setInt(4,bno);
			pstmt.setString(5,directory);
			pstmt.setInt(6,bno);
			pstmt.setString(7,directory);
			pstmt.setInt(8,bno);
			pstmt.setInt(9,bno);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setReadCount(rs.getInt("readcount"));
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setTime(rs.getTimestamp("time"));
				dto.setIp(rs.getString("ip"));
				dto.setFile(rs.getString("file"));
				dto.setLikeCount(rs.getInt("likecount"));
				dto.setEditCount(rs.getInt("editcount"));
				dto.setPrevBno(rs.getInt("prevBno"));
				dto.setNextBno(rs.getInt("nextBno"));
				dto.setPrevSub(rs.getString("prevSub"));
				dto.setNextSub(rs.getString("nextSub"));
				dto.setDirectory(rs.getString("directory"));
			System.out.println(" boardDAO : getBoardContent 완료 / "+dto.toString());
			return dto;			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}return null;
	}// getBoardContent()
	
	// 게시글 수정
	public int updateBoard(String UserId, String Pw, BoardDTO dto) {
		System.out.println(" boardDAO : updateBoard 실행");
		int result = -1;
		if(authorize(UserId,dto.getId())&&validatePw(UserId, Pw)) {
				try {
					con = getConnect();
					sql = "update board set subject=?,content=?,editcount=editcount+1 where bno=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, dto.getSubject());
					pstmt.setString(2, dto.getContent());
					pstmt.setInt(3, dto.getBno());
					result = pstmt.executeUpdate(); // 해당 메서드는 수정한 row의 개수를 return함
					System.out.println(" boardDAO : updateBoard 완료 / 게시글 수정 결과 : "+result);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					closeDB();				}
		}
		return result;
	}
	// 게시글 수정
	
	
	// 게시글 삭제
	public int deleteBoard(String UserId, String Pw, BoardDTO dto) {
		System.out.println(" boardDAO : deleteBoard 실행");
		int result = -1;  // -1: 아이디 X ,0:아이디O,비밀번호X ,1: 정상처리
		if(authorize(UserId,dto.getId())&&validatePw(UserId, Pw)) {
			try {
				con = getConnect();
				sql="update board set deletecount=deletecount+1 where bno=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,dto.getBno());
				result = pstmt.executeUpdate(); 
				System.out.println(" boardDAO : deleteBoard 완료 / 게시글 삭제 결과 : "+result);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();				}
		}
		return result;
	}
	// 게시글 삭제
	
	// 댓글 작성
	public void insertReple(RepleDTO dto) {
		System.out.println(" boardDAO : insertReple 실행");
		try {
			String target = null;
			con = getConnect();
			sql = "select id from board where bno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,dto.getBno()); 
			rs = pstmt.executeQuery();
			if(rs.next()) {	 
				target = rs.getString("id");
			}
			sql = "insert into reple(id,name,content,time,bno,ip,target) values (?,?,?,now(),?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,dto.getId()); 
			pstmt.setString(2,dto.getName()); 
			pstmt.setString(3,dto.getContent());
			pstmt.setInt(4,dto.getBno());
			pstmt.setString(5,dto.getIp());
			pstmt.setString(6,target);
			pstmt.executeUpdate();
			
			System.out.println(" boardDAO : insertReple 완료 / "+dto+" target :"+target);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}	//게시판 글쓰기
	}
	// 댓글 작성
	
	// 댓글 조회
	public List<RepleDTO> getReple(int bno) {
		System.out.println(" boardDAO : getReple(bno) 실행 / 게시글 bno:"+bno);
		List<RepleDTO> repleList = new ArrayList<RepleDTO>();
		try {
			con = getConnect();
			sql = "select * from reple where bno=? and deletecount=0";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,bno); 
			rs = pstmt.executeQuery();
			while(rs.next()) {	 
				RepleDTO dto = new RepleDTO();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setContent(rs.getString("content"));
				dto.setTime(rs.getTimestamp("time"));
				dto.setBno(rs.getInt("bno"));
				dto.setIp(rs.getString("ip"));
				dto.setEditCount(rs.getInt("editcount"));
				dto.setLikeCount(rs.getInt("likecount"));
				repleList.add(dto);
			}
			System.out.println(" boardDAO : getReple(bno) 완료 / 불러온 댓글 목록 개수 : " + repleList.size());
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return repleList;
	}
	//댓글 조회
	
	// 댓글 개수 조회
		public int getRepleCount(String id) {
			System.out.println(" boardDAO : getRepleCount(id) (검색어 없음) 실행 / id:"+id);
			int count = 0;
			try {
				con = getConnect();
				sql = "select count(bno) from reple where id=? and deletecount=0";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,id); 
				rs = pstmt.executeQuery();
				while(rs.next()) {	 
					count = rs.getInt(1);
				}
				System.out.println(" boardDAO : getRepleCount(id) (검색어 없음) 완료 / 작성한 댓글 개수 : " + count);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			return count;
		}
	//댓글 개수 조회
		
	// 댓글 단 게시물 개수 조회
		public int getRepledBoardCount(String id) {
			System.out.println(" boardDAO : getRepledBoardCount(id) 실행 / id:"+id);
			int count = 0;
			try {
				con = getConnect();
				sql = "select count(distinct bno) from reple where id=? and deletecount=0";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,id); 
				rs = pstmt.executeQuery();
				while(rs.next()) {	 
					count = rs.getInt(1);
				}
				System.out.println(" boardDAO : getRepledBoardCount(id) 완료 / 댓글을 작성한 게시물 수 : " + count);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			return count;
		}
	//댓글 단 게시물 개수 조회
		
	// 댓글 단 게시물 개수 조회(검색어 있음)
		public int getRepledBoardCount(String id,String criteria,String keyWord) {
			System.out.println(" boardDAO : getRepledBoardCount(id) (검색어 있음) 실행 / id:"+id+" , "+criteria+" : "+keyWord);
			int count = 0;
			try {
				con = getConnect();
				sql ="select count(distinct bno) "
						+ "from( "
						+ "select b.bno as bno, b.id as 'id',b.name as 'name',b.content as 'content',b.subject as 'subject',r.content as repleContent "
						+ "from reple r  "
						+ "join board b on r.bno=b.bno "
						+ "where r.id = ? and r.deletecount=0 and b.deletecount=0 "
						+ ")as subquery "
						+ "where subquery."+criteria+" like ? ";

				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,id); 
				pstmt.setString(2,"%"+keyWord+"%"); 
				rs = pstmt.executeQuery();
				while(rs.next()) {	 
					count = rs.getInt(1);
				}
				System.out.println(" boardDAO : getRepledBoardCount(id) (검색어 있음) 완료 / 댓글을 작성한 게시물 수 : " + count);
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			return count;
		}
	//댓글 단 게시물 개수 조회(검색어 있음)
	
	// 내 댓글/대상 게시글 조회
		public List<Transaction> getRepleAndBoard(String id,int startRow,int pageSize) {
			System.out.println(" boardDAO : getRepleAndBoard 실행");
			
			List<Transaction> boardAndRepleList = new ArrayList<Transaction>();
			PreparedStatement pstmtBoard = null;
			PreparedStatement pstmtReple = null;
			
			try {
				con = getConnect();
				sql =
				"select distinct bno from reple where id = ? and deletecount=0";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,id); 
				ResultSet bnoRs = pstmt.executeQuery();
				int boardRsCount = 0;
				int repleRsCount = 0;
				
				List<Integer> bnoList = new ArrayList<Integer>();
					while(bnoRs.next()) {	 
						bnoList.add(bnoRs.getInt("bno"));
					}
					
					for(Integer bno : bnoList) {
						List<RepleDTO> rdtoList = new ArrayList<RepleDTO>();
						BoardDTO bdto = new BoardDTO();
						Transaction boardAndReple = new Transaction();
						rdtoList.clear();
						sql =	"select b.*, count(r.bno) as repleNum "
						+ "from board b left join reple r on b.bno = r.bno "
						+ "where b.bno = ? group by b.bno "
						+ "order by re_ref desc, re_seq asc  limit "+(startRow-1)+", "+pageSize;
						pstmtBoard = con.prepareStatement(sql);
						pstmtBoard.setInt(1,bno); 
						ResultSet boardRs = pstmtBoard.executeQuery();
						while(boardRs.next()) {	 
							bdto.setBno(boardRs.getInt("bno"));
							bdto.setName(boardRs.getString("name"));
							bdto.setSubject(boardRs.getString("subject"));
							bdto.setRepleNum(boardRs.getInt("repleNum"));
							bdto.setReadCount(boardRs.getInt("readCount"));
							bdto.setLikeCount(boardRs.getInt("likeCount"));
							bdto.setTime(boardRs.getTimestamp("time"));
							bdto.setDirectory(boardRs.getString("directory"));
							boardRsCount++;
						}
						boardAndReple.setBdto(bdto);

						sql = "select * from reple where bno=? and id=?";
						pstmtReple = con.prepareStatement(sql);
						pstmtReple.setInt(1,bno); 
						pstmtReple.setString(2,id); 
						ResultSet repleRs = pstmtReple.executeQuery();
						while(repleRs.next()) {
							RepleDTO rdto = new RepleDTO();
							rdto.setReplebno(repleRs.getInt("replebno"));
							rdto.setId(repleRs.getString("id"));
							rdto.setName(repleRs.getString("name"));
							rdto.setContent(repleRs.getString("content"));
							rdto.setTime(repleRs.getTimestamp("time"));
							rdto.setBno(repleRs.getInt("bno"));
							rdto.setIp(repleRs.getString("ip"));
							rdto.setEditCount(repleRs.getInt("editcount"));
							rdto.setLikeCount(repleRs.getInt("likecount"));
							rdtoList.add(rdto);
							repleRsCount++;
						}
						boardAndReple.setRdtoList(rdtoList);
						boardAndRepleList.add(boardAndReple);
						
					}
					
					System.out.println(" boardDAO : getRepleAndBoard 완료 / reple 에서 id: "+id+" 검색 완료. 해당 id가 댓글을 작성한 게시글 개수 : "+bnoList.size() );
					System.out.println(" boardDAO : getRepleAndBoard 완료 / 불러온 게시글 개수 : "+boardRsCount);
					System.out.println(" boardDAO : getRepleAndBoard 완료 / 불러온 댓글 개수 : "+repleRsCount);
					System.out.println(" boardDAO : getRepleAndBoard 완료 / 불러온 게시글과 댓글 모음 개수 : "+boardAndRepleList.size());
				}
			catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}	
		
			return boardAndRepleList;
		}
		// 내 댓글/대상게시글 조회
		
		// 내 댓글/대상 게시글 조회(검색어 있음)
				public List<Transaction> getRepleAndBoard(String id,String criteria,String keyWord,int startRow,int pageSize) {
					System.out.println(" boardDAO : getRepleAndBoard(검색어 있음) 실행");
					
					List<Transaction> boardAndRepleList = new ArrayList<Transaction>();
					PreparedStatement pstmtBoard = null;
					PreparedStatement pstmtReple = null;
					try {
						con = getConnect();
						sql = 
								"select distinct * "
								+ "from ( "
								+ "select b.bno "
								+ "from reple r "
								+ "join board b on r.bno=b.bno "
								+ "whrer r.id = ? and r.deletecount=0 and b.deletecount=0 "
								+ ") as subquery "
								+ "where subquery."+criteria+" like ? ";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1,id); 
						pstmt.setString(2,"%"+keyWord+"%"); 
						ResultSet bnoRs = pstmt.executeQuery();
						int boardRsCount = 0;
						int repleRsCount = 0;
						
						List<Integer> bnoList = new ArrayList<Integer>();
							while(bnoRs.next()) {	 
								bnoList.add(bnoRs.getInt("bno"));
							}
							
							for(Integer bno : bnoList) {
								List<RepleDTO> rdtoList = new ArrayList<RepleDTO>();
								BoardDTO bdto = new BoardDTO();
								Transaction boardAndReple = new Transaction();
								rdtoList.clear();
								sql =	"select b.*, count(r.bno) as repleNum "
								+ "from board b left join reple r on b.bno = r.bno "
								+ "where b.bno = ? group by b.bno "
								+ "order by re_ref desc, re_seq asc  limit "+(startRow-1)+", "+pageSize;
								pstmtBoard = con.prepareStatement(sql);
								pstmtBoard.setInt(1,bno); 
								ResultSet boardRs = pstmtBoard.executeQuery();
								while(boardRs.next()) {	 
									bdto.setBno(boardRs.getInt("bno"));
									bdto.setName(boardRs.getString("name"));
									bdto.setSubject(boardRs.getString("subject"));
									bdto.setRepleNum(boardRs.getInt("repleNum"));
									bdto.setReadCount(boardRs.getInt("readCount"));
									bdto.setLikeCount(boardRs.getInt("likeCount"));
									bdto.setTime(boardRs.getTimestamp("time"));
									bdto.setDirectory(boardRs.getString("directory"));
									boardRsCount++;
								}
								boardAndReple.setBdto(bdto);

								sql = "select * from reple where bno=? and id=?";
								pstmtReple = con.prepareStatement(sql);
								pstmtReple.setInt(1,bno); 
								pstmtReple.setString(2,id); 
								ResultSet repleRs = pstmtReple.executeQuery();
								while(repleRs.next()) {
									RepleDTO rdto = new RepleDTO();
									rdto.setReplebno(repleRs.getInt("replebno"));
									rdto.setId(repleRs.getString("id"));
									rdto.setName(repleRs.getString("name"));
									rdto.setContent(repleRs.getString("content"));
									rdto.setTime(repleRs.getTimestamp("time"));
									rdto.setBno(repleRs.getInt("bno"));
									rdto.setIp(repleRs.getString("ip"));
									rdto.setEditCount(repleRs.getInt("editcount"));
									rdto.setLikeCount(repleRs.getInt("likecount"));
									rdtoList.add(rdto);
									repleRsCount++;
								}
								boardAndReple.setRdtoList(rdtoList);
								boardAndRepleList.add(boardAndReple);
								System.out.println("검색한 bno : "+bno);
								
								System.out.println("boardAndRepleList["+(boardAndRepleList.size()-1)+"] 생성 완료");
								System.out.println("boardAndRepleList["+(boardAndRepleList.size()-1)+"] 의 board bno : "+bdto.getBno());
								System.out.println("boardAndRepleList["+(boardAndRepleList.size()-1)+"] 의 첫번째 reple bno : "+rdtoList.getFirst().getReplebno());
								System.out.println("boardAndRepleList["+(boardAndRepleList.size()-1)+"] 의 마지막 reple bno : "+rdtoList.getLast().getReplebno());
							}
							
							System.out.println(" boardDAO : getRepleAndBoard 완료 / reple 에서 id: "+id+" 검색 완료. 해당 id가 댓글을 작성한 게시글 개수 : "+bnoList.size() );
							
							System.out.println(" boardDAO : getRepleAndBoard 완료 / 불러온 게시글 개수 : "+boardRsCount);
							System.out.println(" boardDAO : getRepleAndBoard 완료 / 불러온 댓글 개수 : "+repleRsCount);
							System.out.println(" boardDAO : getRepleAndBoard 완료 / 불러온 게시글과 댓글 모음 개수 : "+boardAndRepleList.size());
						}
					catch (Exception e) {
						e.printStackTrace();
					}finally {
						closeDB();
					}	
				
					return boardAndRepleList;
				}
				// 내 댓글/대상게시글 조회(검색어 있음)
		
		
			
	//게시판 답글쓰기
	public void reInsertBoard(BoardDTO dto,int targetBno) {
			System.out.println(" boardDAO : reInsertBoard 실행");
		int bno = 0;
		String targetId = null;
		try {
			con = getConnect();
			//////////////답글번호,targetId계산//////////////
			sql = "select max(bno) from board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bno = rs.getInt(1) + 1;
			}
			sql = "select id from board where bno = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,targetBno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				targetId = rs.getString(1);
			}
			System.out.println(" boardDAO : reInsertBoard 실행 / 기대 bno : "+bno+" targetId :"+targetId);
			////////////////////////////////////////
			////////////답글re_seq 변경///////////// : 답글 순서 재배치
			// 쿼리 의미 : 부모글과 ref 값이 같으면서, seq 값이 더 큰 경우 seq 1씩 증가 
			sql = "update board set re_seq=re_seq+1 "
					+ "where re_ref=? and re_seq>?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,dto.getRe_ref()); // 부모글과 같은 그룹번호이면서
			pstmt.setInt(2,dto.getRe_seq()); // 본인의 seq 값이 부모글의 seq 보다 더 큰 경우에, seq를 1씩 증가하겠다
			int check = pstmt.executeUpdate();
			if(check>0) {
				System.out.println(" boardDAO : reInsertBoard 실행 / 답글 순서 배치 완료");
			}
			////////////////////////////////////////
			///////////////답글 작성////////////////
			
			sql = "insert into board(bno, id, name, subject, content, readcount, re_ref,re_lev,re_seq, time, ip, file, directory,target) values (?,?,?,?,?,?,?,?,?,now(),?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1,bno); 
			pstmt.setString(2,dto.getId()); 
			pstmt.setString(3,dto.getName()); 
			pstmt.setString(4,dto.getSubject()); 
			pstmt.setString(5,dto.getContent());
			pstmt.setInt(6,0); 
			pstmt.setInt(7,dto.getRe_ref()); // 그룹번호
			pstmt.setInt(8,dto.getRe_lev()+1); // 그룹번호
			pstmt.setInt(9,dto.getRe_seq()+1); // 그룹번호
			pstmt.setString(10,dto.getIp());
			pstmt.setString(11,dto.getFile());
			pstmt.setString(12,dto.getDirectory()); 
			pstmt.setString(13,targetId); 
			
			// 4. sql 구문 실행
			pstmt.executeUpdate();
			
			System.out.println(" boardDAO : reInsertBoard 완료 / 답글 작성완료");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}	//게시판 답글쓰기
	}
	
	
		// 좋아요
		public int like(String id,int bno) {
			int leftToken = 0;
			System.out.println(" boardDAO : like 실행");
			try {
				con = getConnect();
				sql = "select token from member where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,id); 
				rs = pstmt.executeQuery();
				if(rs.next()) {	 
					leftToken = rs.getInt("token");
					if(leftToken>0) {
						sql = "update member set token = token-1 where id=?;";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1,id);
						pstmt.executeUpdate();
						sql = "update board set likecount = likecount+1 where bno=?;";
						pstmt = con.prepareStatement(sql);
						pstmt.setInt(1,bno); 
						pstmt.executeUpdate();
						leftToken--;
						System.out.println(" boardDAO : like 완료 / id: "+id+" bno: "+bno+" 남은 토큰 : "+leftToken);
					}else { 
						leftToken=-1;
						System.out.println(" boardDAO : like 실패 - 토큰 없음 / id: "+id+" 남은 토큰 : "+leftToken);
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return leftToken;
		}
		//좋아요
		
}
