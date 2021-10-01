package board; // 하나의 게시물을 담을 수 있는 객체 생성

public class board {
	private int boardId;  
	private String boardTitle; 
	private String boardNickname;  
	private String boardDate;  
	private String boardContent;  
	private int boardAvailable;
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardNickname() {
		return boardNickname;
	}
	public void setBoardNickname(String boardNickname) {
		this.boardNickname = boardNickname;
	}
	public String getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public int getBoardAvailable() {
		return boardAvailable;
	}
	public void setBoardAvailable(int boardAvailable) {
		this.boardAvailable = boardAvailable;
	}
	
	
	
}
