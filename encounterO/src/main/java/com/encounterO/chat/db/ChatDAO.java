package com.encounterO.chat.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.encounterO.board.db.BoardDTO;

public class ChatDAO {
	
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
		System.out.println(" ChatDAO : 디비 연결 성공");
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
		System.out.println(" ChatDAO : 자원해제 완료");
		
	}// 디비 자원해제 메서드 - closeDB();

	//채팅 보내기
		public void sendChat(ChatDTO dto) {
			System.out.println(" ChatDAO : sendChat 실행");
			try {
				con = getConnect();
				
				int chatbno = 0;
				sql = "select max(chatbno) from chat";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					chatbno = rs.getInt(1) + 1;
				}
				System.out.println(" ChatDAO : sendChat 실행 / 채팅에 접근 성공했습니다. 작성 채팅 기대 bno : "+chatbno);
				sql = "insert into chat(chatbno, id, name, content, time, ip, target) values (?,?,?,?,now(),?,?)";
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1,chatbno); 
				pstmt.setString(2,dto.getId()); 
				pstmt.setString(3,dto.getName()); 
				pstmt.setString(4,dto.getContent());
				pstmt.setTimestamp(5,dto.getTime()); // readcount
				pstmt.setString(6,dto.getIp());
				pstmt.setString(7,dto.getTarget()); // 그룹번호
				
				// 4. sql 구문 실행
				pstmt.executeUpdate();
				
				System.out.println(" ChatDAO : sendChat 완료 / "+dto);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeDB();
			}	
		}//채팅 보내기
	


//채팅 불러오기
	public ChatDTO getChat() {
		System.out.println(" ChatDAO : getChat 실행");
		try {
			con = getConnect();
			
			sql = "select * from chat";
				
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				ChatDTO dto = new ChatDTO();
				dto.setChatbno(rs.getInt("chatbno"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setContent(rs.getString("content"));
				dto.setTime(rs.getTimestamp("time"));
				dto.setIp(rs.getString("ip"));
				dto.setTarget(rs.getString("target"));
			System.out.println(" ChatDAO : getChat 완료 / "+dto.toString());
			return dto;			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}return null;
	}// 채팅 불러오기
	
}