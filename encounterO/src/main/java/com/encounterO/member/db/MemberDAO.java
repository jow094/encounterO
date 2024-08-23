package com.encounterO.member.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.xdevapi.Result;

/**
 * MemberDAO : 회원정보(itwill_member테이블)에 관한 모든 DB동작을 처리하는 개체
 * DAO : Data Access Object
 * 
 * 		각 동작별 메서드를 생성 (DB처리 유형 1개당 하나의 메서드)
 * 
 */

public class MemberDAO {
	//공통변수 선언
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	

	public MemberDAO() {
		System.out.println(" DAO : MemberDAO 객체 생성 - 디비처리 수행 가능");
	}
	
	//디비 연결 메서드 - getConnect()
	private Connection getConnect() throws Exception {
		// 0. 디비 연결에 필요한 변수 생성
		final String DRIVERNAME = "com.mysql.cj.jdbc.Driver";
		final String DBURL = "jdbc:mysql://localhost:3306/encounterO";
		final String DBID = "root";
		final String DBPW = "1234";
		// 1. 드라이버 로드
		Class.forName(DRIVERNAME);	
		System.out.println(" DAO : DB 드라이버 로드 성공!");
		// 2. 디비 연결
		this.con = DriverManager.getConnection(DBURL, DBID, DBPW);
		System.out.println(" DAO : DB 연결 성공!");
		
		return con; // 필드의 con을 이미 바꿔놓았으니 사실 보이드 타입이 맞지만 수행동작의 의미상 그냥 이렇게 적었다고 함
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
	
	
	// 회원가입 메서드
	public void insertMember(MemberDTO dto) throws Exception{
				
				// 3. sql 구분 작성 및 pstmt 객체 생성
				con = getConnect();
				String sql = "insert into member(id,pw,name,gender,age,phone,email,regdate) values (?,?,?,?,?,?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1,dto.getId()); 
				pstmt.setString(2,dto.getPw()); 
				pstmt.setString(3,dto.getName()); 
				pstmt.setString(4,dto.getGender());
				pstmt.setInt(5,dto.getAge());
				pstmt.setString(6,dto.getPhone());
				pstmt.setString(7,dto.getEmail1()+"@"+dto.getEmail2());
				pstmt.setTimestamp(8,dto.getRegdate());
				
				// 4. sql 구문 실행
				pstmt.executeUpdate();
				
				System.out.println(" DAO : 회원가입 완료!");
	}
	
	
	public String loginCheck(MemberDTO dto){
		String result = "-1"; // 아이디가 없는 경우 -1 리턴, 비밀번호만 틀린 경우 0 리턴, 로그인 성공한 경우 1 리턴
		
		try {
			con = getConnect();
			// 3. sql 구분 작성 및 pstmt 객체 생성
			sql = "select name,pw from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			// 4. SQL 실행 $ rs 저장
			rs = pstmt.executeQuery();
			// 5. 데이터 처리 - 로그인 여부 체크
			if(rs.next()) {
				//아이디에 매칭되는 이름과 비밀번호가 있다 → 아이디는 있다
				if(dto.getPw().equals(rs.getString("pw"))) {
				result = rs.getString("name");	//매칭된 비밀번호와 사용자가 입력한 비밀번호가 같다 → 로그인 성공
				}else {
				result = "0";
				}
			}else {
				result = "-1";
			}
			System.out.println(" DAO : 로그인 체크 코드 : " + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		}
		
		return result;
	}
	
	//회원정보 조회 - getMember(id)
	public MemberDTO getMember(String id) {
		
		// 회원정보 저장 객체 생성
		MemberDTO dto = null;
		try {
			con = getConnect();
			// 3. sql 구분 작성 및 pstmt 객체 생성
			String sql = "select* from member where id=?";
			PreparedStatement pstmt = this.con.prepareStatement(sql);
			
			pstmt.setString(1,id); 
			
			// 4. sql 구문 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리 - 조회한 정보를 MemberDTO에 저장
			if(rs.next()) { 	// 데이터가 존재한다. rs를 DTO에 저장하자
				dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setName(rs.getString("name"));
				dto.setGender(rs.getString("gender"));
				dto.setAge(rs.getInt("age"));
				dto.setPhone(rs.getString("phone"));
				String[] email = rs.getString("email").split("@");
				dto.setEmail1(email[0]);
				dto.setEmail2(email[1]);
				dto.setRegdate(rs.getTimestamp("regdate"));
				dto.setLastVisit(rs.getDate("lastvisit"));
				dto.setGrade(rs.getString("grade"));
				dto.setVisit(rs.getInt("visit"));
				dto.setToken(rs.getInt("token"));
				dto.setPost(rs.getInt("post"));
				dto.setReplePost(rs.getInt("replepost"));
				dto.setBoardAlarm(rs.getInt("boardalarm"));
				dto.setRepleAlarm(rs.getInt("replealarm"));
				dto.setChatAlarm(rs.getInt("chatalarm"));
			}
			
			System.out.println(" DAO : getMember 실행 / "+ dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return dto;
	}//회원정보 조회 - getMember(id)
	

	// 회원정보 수정 - updateMember(dto)
	public int updateMember(MemberDTO dto) {
		int result = -1;  // -1: 아이디 X ,0:아이디O,비밀번호X ,1: 정상처리
		
		try {
			// 1. 드라이버 로드
			// 2. 디비 연결
			con = getConnect();
			// 3. SQL 구문(select-본인확인) & pstmt객체
			sql = "select pw from member where id=?";
			pstmt = con.prepareStatement(sql);
			// ???
			pstmt.setString(1, dto.getId());
			// 4. SQL 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리
			if(rs.next()) {
				// 회원정보 있음(아이디에 해당하는 비밀번호 있음)
				if(dto.getPw().equals(rs.getString("pw"))) {
					// 아이디 비밀번호 O => 본인
					// 3. sql구문(update) & pstmt 객체
					sql = "update member set name=?,gender=?,age=?,phone=?,email=? where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, dto.getName());
					pstmt.setString(2, dto.getGender());
					pstmt.setInt(3, dto.getAge());
					pstmt.setString(4, dto.getPhone());
					pstmt.setString(5, dto.getEmail1()+"@"+dto.getEmail2());
					pstmt.setString(6, dto.getId());
					
					// 4. sql 실행
					//result = 1;
					result = pstmt.executeUpdate(); // 해당 메서드는 수정한 row의 개수를 return함
				}else {
					// 아이디(o) 비밀번호(x) => 본인X
					result = 0;
				}
				
			}else {
				// 회원정보 없음(아이디에 해당하는 비밀번호 없음)
				result = -1;
			}
			
			System.out.println(" DAO : 회원정보 수정 완료 ("+result+")");
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return result;
	}
	// 회원정보 수정 - updateMember(dto)
	
	
	
	
	// 회원정보 삭제 - deleteMember(dto)
		public int deleteMember(MemberDTO dto) {
			int result = -1;  // -1: 아이디 X ,0:아이디O,비밀번호X ,1: 정상처리
			
			try {
				// 1. 드라이버 로드
				// 2. 디비 연결
				con = getConnect();
				// 3. SQL 구문(select-본인확인) & pstmt객체
				sql = "select pw from member where id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, dto.getId());
				// 4. SQL 실행
				rs = pstmt.executeQuery();
				// 5. 데이터 처리
				if(rs.next()) {
					// 회원정보 있음(아이디에 해당하는 비밀번호 있음)
					if(dto.getPw().equals(rs.getString("pw"))) {
						// 아이디 비밀번호 O => 본인확인, 삭제 실행
						// 3. sql구문(update) & pstmt 객체
						sql="delete from member where id=?";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1,dto.getId());
						// 4. sql 실행
						//result = 1;
						result = pstmt.executeUpdate(); // 해당 메서드는 수정한 row의 개수를 return함
					}else {
						// 아이디(o) 비밀번호(x) => 본인X
						result = 0;
					}
					
				}else {
					// 회원정보 없음(아이디에 해당하는 비밀번호 없음)
					result = -1;
				}
				
				System.out.println(" DAO : 회원정보 삭제 완료 ("+result+")");
					
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}
			
			return result;
		}
		// 회원정보 삭제 - updateMember(dto)
		
		// 회원목록 리스트 - getMemberList()
				public ArrayList<MemberDTO> getMemberList() {

					ArrayList<MemberDTO> memberList = new ArrayList<MemberDTO>();
					
					try {
						// 1. 드라이버 로드
						// 2. 디비 연결
						con = getConnect();
						// 3. SQL 구문(select-본인확인) & pstmt객체
						sql = "select * from member where id!= ?";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1,"admin");
						// 4. SQL 실행
						rs = pstmt.executeQuery();
						// 5. 데이터 처리
						while(rs.next()) {	// rs의 데이터를 dto로 옮겨담자             [rs → dto] → List 
							MemberDTO mdto = new MemberDTO();
							mdto.setId(rs.getString("id"));
							mdto.setPw(rs.getString("pw"));
							mdto.setName(rs.getString("name"));
							mdto.setGender(rs.getString("gender"));
							mdto.setAge(rs.getInt("age"));
							mdto.setPhone(rs.getString("phone"));
							String[] email = rs.getString("email").split("@");
							mdto.setEmail1(email[0]);
							mdto.setEmail2(email[1]);
							mdto.setRegdate(rs.getTimestamp("regdate"));
							
							// DTO 하나를 List 한칸에 넣자
							memberList.add(mdto);
							
						} // while
						
						System.out.println(" DAO : 회원 목록 조회 성공");
						System.out.println(" DAO : 회원 목록 개수 : " + memberList.size());
						
//						for(MemberDTO dto : memberList) {
//						}
							
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						closeDB();
					}
					
					return memberList;
				}
				// 회원목록 리스트 - getMemberList()

		public boolean isDuplicateId(MemberDTO dto) {// 중복아이디 확인
		        // DB 연결 및 중복 체크 로직 구현
	        try {
	        	con = getConnect();
	            String sql = "select id from member where id=?";
	            pstmt = con.prepareStatement(sql);
				pstmt.setString(1,dto.getId());
				rs = pstmt.executeQuery();

	            	if (rs.next()) {
	            	System.out.println("중복되는 아이디가 있어 회원가입이 실패했습니다.");
	            	return true;
	            	}
	            
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		        	closeDB();
		        }
		        return false;
		        
		    }// 중복아이디 확인
		
		public boolean isDuplicatePhone(MemberDTO dto) {// 중복전화번호 확인
	        // DB 연결 및 중복 체크 로직 구현
        try {
        	con = getConnect();
            String sql = "select phone from member where phone=?";
            pstmt = con.prepareStatement(sql);
			pstmt.setString(1,dto.getPhone());
			rs = pstmt.executeQuery();

            	if (rs.next()) {
            	System.out.println("중복되는 전화번호가 있어 회원가입이 실패했습니다.");
            	return true;
            	}
            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        	closeDB();
	        }
	        return false;
	        
	    }// 중복전화번호 확인
		
		public boolean isDuplicateEmail(MemberDTO dto) {// 중복이메일 확인
	        // DB 연결 및 중복 체크 로직 구현
        try {
        	con = getConnect();
            String sql = "select email from member where email=?";
            pstmt = con.prepareStatement(sql);
			pstmt.setString(1,dto.getEmail1()+"@"+dto.getEmail2());
			rs = pstmt.executeQuery();

            	if (rs.next()) {
            	System.out.println("중복되는 이메일이 있어 회원가입이 실패했습니다.");
            	return true;
            	}
            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        	closeDB();
	        }
	        return false;
	        
	    }// 중복이메일 확인
		
		public boolean isDuplicateName(MemberDTO dto) {// 중복아이디 확인
	        // DB 연결 및 중복 체크 로직 구현
        try {
        	con = getConnect();
            String sql = "select name from member where name=?";
            pstmt = con.prepareStatement(sql);
			pstmt.setString(1,dto.getName());
			rs = pstmt.executeQuery();

            	if (rs.next()) {
            	System.out.println("중복되는 회원명이 있어 회원가입이 실패했습니다.");
            	return true;
            	}
            
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        	closeDB();
	        }
	        return false;
	        
	    }// 중복아이디 확인
		
		public void updateVisit(MemberDTO dto) { // 방문횟수 증가
			System.out.println(" MemberDAO : updateVisit 실행");
			
				try {
					con = getConnect();
					sql = "update member set visit= visit+1 where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1,dto.getId());
					pstmt.executeUpdate();
						System.out.println(" MemberDAO : updateVisit 완료 / id: "+dto.getId()+" visit 1 증가");
					}
					catch (Exception e) {
					e.printStackTrace();
					} finally {
					closeDB();
					}
				
		}//	방문횟수 증가
		
		public void updatePostCount(String id) {
			System.out.println(" MemberDAO : updatePostCount 실행");
			
				try {
					con = getConnect();
					sql = "update member set post= post+1 where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1,id);
					pstmt.executeUpdate();
						System.out.println(" MemberDAO : updatePostCount 완료 / id: "+id+" post 1 증가");
					}
					catch (Exception e) {
					e.printStackTrace();
					} finally {
					closeDB();
					}
		}
		
		public void updateReplePostCount(String id) {
			System.out.println(" MemberDAO : updateReplePostCount 실행");
			
				try {
					con = getConnect();
					sql = "update member set replepost= replepost+1 where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1,id);
					pstmt.executeUpdate();
						System.out.println(" MemberDAO : updateReplePostCount 완료 / id: "+id+" replePost 1 증가");
					}
					catch (Exception e) {
					e.printStackTrace();
					} finally {
					closeDB();
					}
		}
		
		public void updateToken(String id) {
			System.out.println(" MemberDAO : updateToken 실행");
			
				try {
					con = getConnect();
					sql = "update member set token = 10 where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1,id);
					pstmt.executeUpdate();
						System.out.println(" MemberDAO : updateToken 완료 / id: "+id+" token 10으로 초기화");
					}
					catch (Exception e) {
					e.printStackTrace();
					} finally {
					closeDB();
					}
		}
		
		public void reduceToken(String id) {
			System.out.println(" MemberDAO : reduceToken 실행");
			
				try {
					con = getConnect();
					sql = "update member set token= token-1 where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1,id);
					pstmt.executeUpdate();
						System.out.println(" MemberDAO : reduceToken 완료 / id: "+id+" token 1 감소");
					}
					catch (Exception e) {
					e.printStackTrace();
					} finally {
					closeDB();
					}
		}
		
		public boolean isFirstToday(String id) {
			System.out.println(" MemberDAO : isFirstToday 실행");
			boolean result = false;
				try {
					LocalDate today = LocalDate.now();
					con = getConnect();
					sql = "select lastVisit,visit from member where id =?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1,id);
					rs = pstmt.executeQuery();
						if(rs.next()) {
							if(!today.isEqual(rs.getDate("lastvisit").toLocalDate())){
								sql = "update member set lastVisit= curdate() where id =?";
								pstmt = con.prepareStatement(sql);
								pstmt.setString(1,id);
								pstmt.executeUpdate();
								System.out.println(" MemberDAO : isFirstToday 완료 : true / id: "+id+" lastVisit 업데이트");
								result = true;
							}else {
								System.out.println(" MemberDAO : isFirstToday 완료 : false");
							}
						}
					}
					catch (Exception e) {
					e.printStackTrace();
					} finally {
					closeDB();
					}
			return result;
		}
		
		public void updateGrade(String id) {
			System.out.println(" MemberDAO : updateGrade 실행");
			
				try {
					con = getConnect();
					sql = "update member set grade= ? where id=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1,id);
					int result = pstmt.executeUpdate();
						System.out.println(" MemberDAO : updateGrade 완료 / id: "+id+" grade 변경사항 :"+result);
					}
					catch (Exception e) {
					e.printStackTrace();
					} finally {
					closeDB();
					}
		}
		
		//회원정보 기억
		public void rememberMember(String id,HttpServletRequest request) {
			MemberDTO dto = getMember(id);
			request.setAttribute("id",dto.getId());
			request.setAttribute("name",dto.getName());
			request.setAttribute("lastVisit",dto.getLastVisit());
			request.setAttribute("grade",dto.getGrade());
			request.setAttribute("visit",dto.getVisit());
			request.setAttribute("token",dto.getToken());
			request.setAttribute("post",dto.getPost());
			request.setAttribute("replePost",dto.getReplePost());
			request.setAttribute("boardAlarm",dto.getBoardAlarm());
			request.setAttribute("repleAlarm",dto.getRepleAlarm());
			request.setAttribute("chatAlarm",dto.getChatAlarm());
			System.out.println(" MemberDAO : rememberMember 실행 / request 영역에 저장 :"+dto);
		}
		
}// Class
