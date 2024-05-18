package kr.co.iei.note.vo;

public class Member {
	private String id;
	private String pw;
	private String[][] memo;
	private int memoIndex;
	
	public Member() {
		super();
		memo = new String[10][2];
		memoIndex = 0;
		// TODO Auto-generated constructor stub
	}
	public Member(String id, String pw) {
		super();
		this.id = id;
		this.pw = pw;
		memo = new String[10][2];
		memoIndex = 0;
	}
	
	public String[][] getMemo() {
		return memo;
	}
	
	
	public int getMemoIndex() {
		return memoIndex;
	}
	public void setMemoIndex(int memoIndex) {
		this.memoIndex = memoIndex;
	}
	public String getMemo(int titleNum) {
		return memo[titleNum][0]+"\t"+memo[titleNum][1];
	}
	public void setMemo(String title, String content) {
		this.memo[memoIndex][0] = title;
		this.memo[memoIndex][1] = content;
		memoIndex++;
	}
	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	
	
	
	
	
}
