package kr.co.iei.note.controller;

import java.util.Scanner;

import kr.co.iei.note.vo.Member;

public class IdPwAb {
	private Member[] members;
	private int index;
	private Scanner sc;
	public IdPwAb() {
		super();
		sc=new Scanner(System.in);
		index=0;
		members = new Member[10];
	}
	
	public void main() {
		while(true) {
			System.out.println("\n===== 게시판 =====\n");
			System.out.println("1. 회원가입");
			System.out.println("2. 글쓰기");
			System.out.println("3. 전체 글 조회");
			System.out.println("4. 아이디별 글 조회");
			System.out.println("5. 글 수정");
			System.out.println("6. 글 삭제");
			System.out.println("0. 프로그램 종료");
			System.out.print("선택 >> ");
			int select = sc.nextInt();
			switch(select) {
			case 1://가입
				joinMember();
				break;
			case 2://글쓰기
				write();
				break;
			case 3://전체 출력
				printAllNote();
				break;
			case 4://특정 아이디 글 출력
				printOneNote();
				break;
			case 5://수정
				updateNote();
				break;
			case 6://삭제
				deleteNote();
				break;
			case 0:
				System.out.println("프로그램을 종료합니다...");
				return;
			default:
				System.out.println("잘못 입력하셨습니다.");
				break;
			}//switch(select)
		}//while
	}//main
	
	public void joinMember() {
		System.out.println("\n------ 회원가입 ------\n");
		System.out.print("아이디를 입력하세요 : ");
		String id = sc.next();
		int searchIndex = searchMember(id);
		if(searchIndex==-1) {
			System.out.print("비밀번호를 입력하세요 : ");
			String pw = sc.next();
			members[index++]= new Member(id,pw);
			System.out.println("회원가입 완료");
		}//if 회원가입 가능
		else {
			System.out.println("이미 존재하는 ID 입니다.");
		}//else 이미 id 존재
	}//joinMember
	
	public void write() {
		System.out.println("\n------ 글쓰기 ------\n");
		System.out.println("===로그인===");
		System.out.print("ID : ");
		String id = sc.next();
		System.out.print("PW : ");
		String pw = sc.next();
		int searchIndex = searchMember(id,pw);
		if(searchIndex==-1) {
			System.out.println("존재하지 않는 ID 입니다.");
		}//if 아이디 존재 안함
		else if(searchIndex==-2) {
			System.out.println("비밀번호를 잘못입력하셨습니다.");
		}//else if 비밀번호 틀림
		else {
			System.out.println("===== 글 입력 ======");
			System.out.print("제목 : ");
			sc.nextLine();
			String title = sc.nextLine();
			System.out.print("내용 : ");
			String content = sc.nextLine();
			members[searchIndex].setMemo(title, content);
			System.out.println("게시판 작성 완료");
			return;
		}//else 다 같음
	}//write
	
	public void printAllNote() {
		System.out.println("\n------ 전체 글 출력 ------\n");
		System.out.println("ID\tTitle\tMemo");
		for(int i=0; i<index; i++) {
			if(members[i].getMemo()[0][0]==null) {
				continue;
			}//아무것도 안쓰여져 있는 경우 출력 안되게
			for(int j=0; j<members[i].getMemoIndex(); j++) {
				System.out.println(members[i].getId()+"\t"+members[i].getMemo(j));
			}//for
			
		}//for
	}//printAllNote
	
	public void printOneNote() {
		System.out.println("\n------ 아이디별 글 조회 ------\n");
		System.out.print("조회할 아이디를 입력하세요 : ");
		String id = sc.next();
		int searchIndex = searchMember(id);
		if(searchIndex == -1) {
			System.out.println("해당하는 글이 존재하지 않습니다.");
		}//if 조회 안됨
		else {
			printSpecNote(searchIndex);
		}//else 조회됨
	}//printOneNote
	
	public void printSpecNote(int searchIndex) {
		System.out.println("ID\t: "+members[searchIndex].getId());
		for(int j=0; j<members[searchIndex].getMemoIndex(); j++) {
			System.out.println("<제목 : "+members[searchIndex].getMemo()[j][0]+">");
			System.out.println("------내용------");
			System.out.println(members[searchIndex].getMemo()[j][1]);
		}//for
	}//printSpecNote()
	
	public void updateNote() {
		System.out.println("\n------ 글 수정 ------\n");
		System.out.println("===로그인===");
		System.out.print("ID : ");
		String id = sc.next();
		System.out.print("PW : ");
		String pw = sc.next();
		int searchIndex = searchMember(id,pw);
		if(searchIndex==-1) {
			System.out.println("존재하지 않는 ID 입니다.");
		}//if 아이디 존재 안함
		else if(searchIndex==-2) {
			System.out.println("비밀번호를 잘못입력하셨습니다.");
		}//else if 비밀번호 틀림
		else {
			//1.해당 id 글 모두 출력
			printSpecNote(searchIndex);
			//2.수정할 글 제목 고르기
			System.out.println("----------------------");
			System.out.print("수정할 글의 제목을 입력해주세요 : ");
			sc.nextLine();
			String title = sc.nextLine();
			//3.글 수정
			int searchMemoIndex = searchMemoIndex(searchIndex,title);
			if(searchMemoIndex==-1) {
				System.out.println("입력하신 글이 존재하지 않습니다.");
			}//if
			else {
				System.out.print("수정할 제목 입력 : ");
				String modifyTitle = sc.nextLine();
				System.out.print("수정할 내용 입력 : ");
				String modifyContent = sc.nextLine();
				String[] n = {modifyTitle, modifyContent};
				members[searchIndex].getMemo()[searchMemoIndex] = n;
				System.out.println("수정 완료");
			}//else
			return;
		}//else 다 같음
	}//updateNote()
	
	public void deleteNote() {
		System.out.println("\n------ 글 수정 ------\n");
		System.out.println("===로그인===");
		System.out.print("ID : ");
		String id = sc.next();
		System.out.print("PW : ");
		String pw = sc.next();
		int searchIndex = searchMember(id,pw);
		if(searchIndex==-1) {
			System.out.println("존재하지 않는 ID 입니다.");
		}//if 아이디 존재 안함
		else if(searchIndex==-2) {
			System.out.println("비밀번호를 잘못입력하셨습니다.");
		}//else if 비밀번호 틀림
		else {
			//1.해당 id 글 모두 출력
			printSpecNote(searchIndex);
			//2.수정할 글 제목 고르기
			System.out.println("----------------------");
			System.out.print("삭제할 글의 제목을 입력해주세요 : ");
			sc.nextLine();
			String title = sc.nextLine();
			//3.글 삭제
			int searchMemoIndex = searchMemoIndex(searchIndex,title);
			if(searchMemoIndex==-1) {
				System.out.println("입력하신 글이 존재하지 않습니다.");
			}//if
			else {
				for(int i=searchMemoIndex; i<members[searchIndex].getMemoIndex()-1;i++) {
					members[searchIndex].getMemo()[i]=members[searchIndex].getMemo()[i+1];
				}//for
				members[searchIndex].getMemo()[members[searchIndex].getMemoIndex()-1]=null;
				members[searchIndex].setMemoIndex(members[searchIndex].getMemoIndex()-1);
				System.out.println("삭제 완료");
			}//else
			return;
		}//else 다 같음
	}//deleteNote()
	
	public int searchMemoIndex(int searchIndex, String title) {
		for(int i=0; i<members[searchIndex].getMemoIndex();i++) {
			if(title.equals(members[searchIndex].getMemo()[i][0])) {
				return i;
			}//if
		}//for
		return -1;
	}//searchMemoIndex()
	
	public int searchMember(String id) {
		for(int i=0; i<index; i++) {
			if(id.equals(members[i].getId())) {
				return i;
			}//같은거 찾음
		}//for
		return -1;
	}//searchMember()

	public int searchMember(String id, String pw) {
		for(int i=0; i<index; i++) {
			if(id.equals(members[i].getId())) {
				if(pw.equals(members[i].getPw())) {
					return i;
				}//if 로그인 완료
				else{
					return -2;
				}//else 비밀번호 다름
			}//if아이디같은거 찾음
		}//for
		return -1;
	}//searchMember()
	
}
