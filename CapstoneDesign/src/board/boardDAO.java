package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class boardDAO { //�����ͺ��̽��� �����Ͽ��� �����͸� �������� ������ �Ѵ�.!!!
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
		//NOW �Լ��� ���� MySQL ������ �ð� ���� �������� �Լ�
		String sql="SELECT NOW()";  // 2018-01-11 11:21:36  �� ������ ��¥�� �ð��� ��ȯ���ش�!!
		//The date and time is returned as "YYYY-MM-DD HH-MM-SS" (string) or as YYYYMMDDHHMMSS.uuuuuu (numeric).
		//YYYY(�⵵)-MM(�� 01~12)-DD(��) HH(��, 0��~23�� �Ϲ����� 24�ð� ǥ���� ex)���� 2�� >>>> 14��) -MM-SS
		
		//list.get(i).getBbsDate().substring(0,11) ===> 2021-07-25 �̷��� ����!!! �׳� �̷��� ����!!!
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
		return ""; //�����ͺ��̽� ����
	}
	
	public int getNext() {
		//NOW �Լ��� ���� MySQL ������ �ð� ���� �������� �Լ�
		String sql="SELECT sampleBoard from board ORDER BY boardId DESC";  //������������  �� ū �� >>> ���� ��
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1)+1; 
				//bbsID int >> ��ñ��� ��ȣ�� �ο��� 
				// �� ���� ��ù��� ��ȣ�� ��ȯ�ϱ� ���ؼ� �̷��� ��!! ���� �� 1�� ������ 2��
			}
			return 1; //���� �ϳ��� �Խù��� ���� ��
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
	
	public int write(String boardTitle,String boardNickname,String boardDate,String boardContent) { 
		String sql="insert into board VALUES(?,?,?,?,?,?)";  //�Խù� �ۼ��ϱ�
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext()); //boardId
			pstmt.setString(2, boardTitle); // boardTitle
			pstmt.setString(3, boardNickname); //boardNickname
			pstmt.setString(4, getDate()); // boardDate
			pstmt.setString(5, boardContent); // boardContent
			pstmt.setInt(6, 1); //������ ���� �ʾƼ� ===> boardAvailable
			
			return pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
	
	public ArrayList<board> getAllList(){ //��� �Խù��� ��ȯ�Ѵ�.
		String sql="select * from board where boardAvailable = 1 order by boardId desc";  
		ArrayList<board> list = new ArrayList<board>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				board bbs = new board(); //1���� �Խù����� ��� ���ο� board��ü���ٰ� ������ 
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
	
	
	
	// boardId�� �Խù��� ��ȣ�� �ǹ��Ѵ�.
	/*public ArrayList<board> getList(int pageNumber){
		String sql="select * from board where bbsID < ? and bbsAvailable = 1 order by bbsID desc limit 10";  //�Խù� �ۼ��ϱ�
		ArrayList<board> list = new ArrayList<board>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext() - (pageNumber - 1)*10);
			// getNext()=1�� ��� �� �Խù��� �ϳ��� ���� ��츦 �����Ѵ�. �׷��� pageNumber =1 �̹Ƿ� ��������� setInt(1,1)�� �ȴ�.
			// getNext()=11 �� �Խù��� 10�� �� ��츦 ���� �׷��� pageNumber = 1 ��������� setInt(1,11)�� �ȴ�. �� �Խù� ��ȣ�� 10������ �͸� ��µô�.
			rs = pstmt.executeQuery();
			while(rs.next()) {
				board bbs = new board(); //1���� �Խù����� ��� ���ο� board��ü���ٰ� ������ 
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
	
	public board getBoard(int boardId) { // bbsID�� �ش��ϴ� �Խù��� ��ȯ�Ѵ�.
		String sql="select * from board where boardId = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);//���ڷ� ���޹��� boardId�� ���� �ش��ϴ� �Խù��� �����Ѵ�!!!
			rs = pstmt.executeQuery();
			if(rs.next()) {// �ش�Ǵ� �Խù��� ������
				board bbs = new board(); //1���� �Խù����� ��� ���ο� board��ü���ٰ� ������ 
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
