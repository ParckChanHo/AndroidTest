package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class boardDAO { //데이터베이스에 접근하여서 데이터를 가져오는 역할을 한다.!!!
	private Connection conn;
	private ResultSet rs;
	
	public boardDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/sampleBoard";
			String dbID = "root";
			String dbPassword = "1004";
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() {
		//NOW 함수는 현재 MySQL 서버의 시간 값을 가져오는 함수
		String sql="SELECT NOW()";  // 2018-01-11 11:21:36  즉 현재의 날짜와 시간을 반환해준다!!
		//The date and time is returned as "YYYY-MM-DD HH-MM-SS" (string) or as YYYYMMDDHHMMSS.uuuuuu (numeric).
		//YYYY(년도)-MM(월 01~12)-DD(일) HH(시, 0시~23시 일반적인 24시간 표시임 ex)오후 2시 >>>> 14시) -MM-SS
		
		//list.get(i).getBbsDate().substring(0,11) ===> 2021-07-25 이렇게 나옴!!! 그냥 이렇게 하자!!!
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ""; //데이터베이스 오류
	}
	
	public int getNext() {
		//NOW 함수는 현재 MySQL 서버의 시간 값을 가져오는 함수
		String sql="SELECT sampleBoard from board ORDER BY boardId DESC";  //내림차순으로  즉 큰 것 >>> 작은 것
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1; 
				//bbsID int >> 계시글의 번호를 부여함 
				// 즉 다음 계시물의 번호를 반환하기 위해서 이렇게 함!! 예를 들어서 1번 다음에 2번
			}
			return 1; //현재 하나도 게시물이 없을 때
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int write(String boardTitle,String boardNickname,String boardDate,String boardContent) { 
		String sql="insert into board VALUES(?,?,?,?,?,?)";  //게시물 작성하기
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext()); //boardId
			pstmt.setString(2, boardTitle); // boardTitle
			pstmt.setString(3, boardNickname); //boardNickname
			pstmt.setString(4, getDate()); // boardDate
			pstmt.setString(5, boardContent); // boardContent
			pstmt.setInt(6, 1); //삭제가 되지 않아서 ===> boardAvailable
			
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public ArrayList<board> getAllList(){ //모든 게시물을 반환한다.
		String sql="select * from board where boardAvailable = 1 order by boardId desc";  
		ArrayList<board> list = new ArrayList<board>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				board bbs = new board(); //1개의 게시물들을 모두 새로운 board객체에다가 저장을 
				bbs.setBoardId(rs.getInt(1));
				bbs.setBoardTitle(rs.getString(2));
				bbs.setBoardNickname(rs.getString(3));
				bbs.setBoardDate(rs.getString(4));
				bbs.setBoardContent(rs.getString(5));
				bbs.setBoardAvailable(rs.getInt(6));
				list.add(bbs);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	// boardId가 게시물의 번호를 의미한다.
	/*public ArrayList<board> getList(int pageNumber){
		String sql="select * from board where bbsID < ? and bbsAvailable = 1 order by bbsID desc limit 10";  //게시물 작성하기
		ArrayList<board> list = new ArrayList<board>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext() - (pageNumber - 1)*10);
			// getNext()=1인 경우 즉 게시물이 하나도 없는 경우를 가정한다. 그러면 pageNumber =1 이므로 결론적으로 setInt(1,1)이 된다.
			// getNext()=11 즉 게시물이 10개 인 경우를 가정 그러면 pageNumber = 1 결론적으로 setInt(1,11)이 된다. 즉 게시물 번호가 10이하인 것만 출력돤다.
			rs = pstmt.executeQuery();
			while(rs.next()) {
				board bbs = new board(); //1개의 게시물들을 모두 새로운 board객체에다가 저장을 
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) {
		String sql="select * from board where bbsID < ? and bbsAvailable = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext() - (pageNumber - 1)*10);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}*/
	
	public board getBoard(int boardId) { // bbsID에 해당하는 게시물을 반환한다.
		String sql="select * from board where boardId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);//인자로 전달받은 boardId의 값에 해당하는 게시물을 선택한다!!!
			rs = pstmt.executeQuery();
			if(rs.next()) {// 해당되는 게시물이 있으면
				board bbs = new board(); //1개의 게시물들을 모두 새로운 board객체에다가 저장을 
				bbs.setBoardId(rs.getInt(1));
				bbs.setBoardTitle(rs.getString(2));
				bbs.setBoardNickname(rs.getString(3));
				bbs.setBoardDate(rs.getString(4));
				bbs.setBoardContent(rs.getString(5));
				bbs.setBoardAvailable(rs.getInt(6));
				
				return bbs;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int boardId, String boardTitle, String boardContent) {
		String SQL = "UPDATE board SET boardTitle=?, boardContent=?, boardDate=? WHERE boardId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setString(3, getDate());
			pstmt.setInt(4, boardId);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; 
	}
	
	public int delete(int boardId) {
		String SQL = "UPDATE board SET boardAvailable = 0 WHERE boardId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, boardId);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; 
	}
	
}
