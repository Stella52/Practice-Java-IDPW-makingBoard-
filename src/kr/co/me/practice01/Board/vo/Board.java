package kr.co.me.practice01.Board.vo;

import java.time.LocalDateTime;

public class Board {
	private String title;
	private LocalDateTime time;
	private String contents;
	private String author;
	public Board() {
		// TODO Auto-generated constructor stub
	}
	public Board(String title, String contents, String author) {
		this.title = title;
		this.contents = contents;
		this.author = author;
		this.time = LocalDateTime.now();
	}
	public String getTitle() {
		return title;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public String getContents() {
		return contents;
	}
	public String getAuthor() {
		return author;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setTime() {
		this.time = LocalDateTime.now();
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	
	
}
