package kr.co.me.practice01.Board.controller;

import java.util.Scanner;

import kr.co.me.practice01.Board.vo.Board;
import kr.co.me.practice01.Board.vo.User;

public class BoardProgram {
	private User[] users;
	private int uIndex;
	private Board[] writings;
	private int bIndex;
	private Scanner sc;
	private boolean checkSignIn;
	private int signIndex;

	public BoardProgram() {
		sc = new Scanner(System.in);
		users = new User[100];
		uIndex = 0;
		writings = new Board[100];
		bIndex = 0;
		checkSignIn = false;
		signIndex = -1;
	}

	public void main() {
		// 프로그램 실행

		while (true) {
			System.out.println("\n===== 우리들이 만드는 게시판 =====\n");
			if (checkSignIn) {
				System.out.println("1. 로그아웃");
			} // if 로그인 한 상태
			else {
				System.out.println("1. 회원가입");
			} // else 로그인 안한 상태
			System.out.println("2. 로그인");
			System.out.println("3. 회원정보 조회");
			System.out.println("4. 게시판 들어가기");
			System.out.println("5. 회원 탈퇴");
			System.out.println("0. 프로그램 종료");
			System.out.print("선택 >> ");
			int selectMainMenu = sc.nextInt();
			System.out.println();
			switch (selectMainMenu) {
			case 1:// 회원가입
				if (checkSignIn) {
					checkSignIn = false;
					signIndex = -1;
					System.out.println("성공적으로 로그아웃되었습니다.");
				} // if 로그인 한 상태
				else {
					signUp();
				} // else 로그인 안한 상태
				break;
			case 2: // 로그인
				int signIn = signIn();
				if (signIn == -1) {
					System.out.println("일치하는 ID가 존재하지 않습니다.");
				} // if 아이디 없음
				else if (signIn == -2) {
					System.out.println("비밀번호가 일치하지 않습니다.");
				} // else if 비밀번호 불일치
				else {
					checkSignIn = true;
					signIndex = signIn;
					System.out.println("성공적으로 로그인 되었습니다.");
				} // else
				break;
			case 3:// 회원정보 조회
				lookMemInfor();
				break;
			case 4:// 게시판 들어가기
				goToBoard();
				break;
			case 5:// 회원 탈퇴
				deleteMembership();
				break;
			case 0:// 프로그램 종료
				System.out.println("프로그램을 종료합니다....");
				return;
			default:
				break;
			}// switch(selectMainMenu)
		} // while(true)
	}// main()

	public void signUp() {
		System.out.println("\n------- 회원가입 -------\n");
		while (true) {
			System.out.print("ID를 입력하세요 : ");
			String id = sc.next();
			int searchUIndex = searchUser(id);
			if (searchUIndex == -1) {
				System.out.print("이름을 입력하세요 : ");
				String name = sc.next();
				System.out.print("PW를 입력하세요 : ");
				String pw = sc.next();
				System.out.print("나이를 입력하세요 : ");
				int age = sc.nextInt();
				users[uIndex++] = new User(name, id, pw, age);
				System.out.println("회원가입이 완료되었습니다.");
				return;
			} // if 존재하지 않는 경우
			else {
				System.out.println("이미 존재하는 ID 입니다 다시 입력하세요");
			} // else 존재할 경우
		} // while(signUp)
	}// signUp()

	public void lookMemInfor() {
		// 1.로그인
		while (true) {
			if (checkSignIn) {
				int signIn = signIndex;
				System.out.println("\n---- 회원 정보 ----\n");
				System.out.println("이름\t: " + users[signIn].getName());
				System.out.println("ID\t: " + users[signIn].getId());
				System.out.println("PW\t: " + users[signIn].getPw());
				System.out.println("나이\t: " + users[signIn].getAge());
				System.out.print("수정하시겠습니까?[y/n] : ");
				char Answer = sc.next().charAt(0);
				switch (Answer) {
				case 'y':
					updateUser(signIn);
					return;
				default:
					break;
				}// switch(Answer)
			} // if로그인한 상태
			else {
				int signIn = signIn();
				if (signIn == -1) {
					System.out.println("일치하는 ID가 존재하지 않습니다.");
				} // if 아이디 없음
				else if (signIn == -2) {
					System.out.println("비밀번호가 일치하지 않습니다.");
				} // else if 비밀번호 불일치
				else {
					signIndex = signIn;
					checkSignIn = true;
				} // else
			}
		} // while(true)
	}// lookMemInfor()

	public void updateUser(int index) {
		System.out.print("비밀번호 수정 : ");
		String modifyPw = sc.next();
		System.out.println("나이 수정 : ");
		int modifyAge = sc.nextInt();
		users[index].setAge(modifyAge);
		users[index].setPw(modifyPw);
		System.out.println("회원정보 수정 완료");
	}// updateUser()

	public void goToBoard() {
		while (true) {
			System.out.println("\n------ 게시판 ------\n");
			if (bIndex == 0) {
				System.out.println("게시글이 존재하지 않습니다");
				System.out.print("게시글을 작성하시겠습니까?[y/n] : ");
				char answer = sc.next().charAt(0);
				if (answer == 'y') {
					makeNewBoard();
				} // if
				else {
					return;
				} // else
			} // if
			else {
				showAllTitle();
				System.out.println("--- 게시판 메뉴 ---");
				System.out.println("1. 게시글 조회");
				System.out.println("2. 게시글 작성");
				System.out.println("3. 게시글 검색");
				System.out.println("4. 내 게시글 보기");
				System.out.println("0. 게시판 나가기");
				System.out.println("선택 >> ");
				int select = sc.nextInt();
				switch (select) {
				case 1:
					showOneWriting();
					break;
				case 2:
					makeNewBoard();
					break;
				case 3:
					searchWriting();// 아직 안함
					break;
				case 4:
					showMine();
					break;
				case 0:
					return;
				}// switch(select)
			} // else
		} // while(true)
	}// goToBoard()

	public void searchWriting() {
		while (true) {
			System.out.println("[검색 유형 선택]");
			System.out.println("1. 제목으로 검색");
			System.out.println("2. 작성자 ID로 검색");
			System.out.println("0. 검색 종료");
			System.out.print("선택 >> ");
			int select = sc.nextInt();
			switch (select) {
			case 1:
				break;
			case 2:
				break;
			case 0:
				return;
			}// switch(select)
		} // while(true)
	}// searchWriting()

	public void showOneWriting() {
		while (true) {
			System.out.print("조회할 게시글 선택 : ");
			int showbIndex = sc.nextInt() - 1;
			if (showbIndex < bIndex) {
				showWritings(showbIndex);
				System.out.println();
				System.out.print("추가적으로 다른 게시물을 보시겠습니까?[y/n] : ");
				char keep = sc.next().charAt(0);
				if (keep == 'n') {
					return;
				} // if
			} // if 옳은 게시글 선택
			else {
				System.out.println("선택하신 게시글이 존재하지 않습니다.");
			} // else
		} // while(true)
	}// showOneWriting

	public void showAllTitle() {
		for (int i = 0; i < bIndex; i++) {
			System.out.println((i + 1) + ". " + writings[i].getTitle());
		} // for
	}// showAllTitle()

	public void showWritings(int index) {
		System.out.println("<" + writings[index].getTitle() + ">");
		System.out.println("작성자 : " + writings[index].getAuthor() + "\t 작성시간 : " + writings[index].getTime());
		System.out.println("-------------------------------");
		System.out.println(writings[index].getContents());
	}// showWritings()

	public void makeNewBoard() {
		while (true) {
			if (checkSignIn) {
				int signIn = signIndex;
				System.out.print("제목 : ");
				sc.nextLine();
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String contents = sc.nextLine();
				writings[bIndex++] = new Board(title, contents, users[signIn].getId());
				return;
			} // if 로그인 한 상태
			else {
				int signIn = signIn();
				if (signIn == -1) {
					System.out.println("일치하는 ID가 존재하지 않습니다.");
				} // if 아이디 없음
				else if (signIn == -2) {
					System.out.println("비밀번호가 일치하지 않습니다.");
				} // else if 비밀번호 불일치
				else {
					checkSignIn = true;
					signIndex = signIn;
				} // else
			} // else
		} // while(true)
	}// makeNewBoard()

	public void showMine() {
		while (true) {
			if (checkSignIn) {
				int signIn = signIndex;
				while (true) {
					int count = 0;
					System.out.println("\n------ 검색 결과 ------\n");
					for (int i = 0; i < bIndex; i++) {
						if (writings[i].getAuthor().equals(users[signIn].getId())) {
							System.out.println((i + 1) + ". " + writings[i].getTitle());
							count++;
						} // if
					} // for
					if (count == 0) {
						System.out.println("작성하신 게시글이 없습니다");
						return;
					} // if
					else {
						System.out.println("--------------------");
						System.out.println("1. 게시글 조회");
						System.out.println("2. 게시글 수정");
						System.out.println("3. 게시글 삭제");
						System.out.println("0. 돌아가기");
						System.out.print("선택 >> ");
						int select = sc.nextInt();
						if (select == 0) {
							return;
						} // if
						System.out.print("게시글을 선택하세요 : ");
						int writingNum = sc.nextInt() - 1;
						if (writingNum >= 0 && writingNum < bIndex) {
							if (writings[writingNum].getAuthor().equals(users[signIn].getId())) {
								switch (select) {
								case 1:
									showWritings(writingNum);
									break;
								case 2:
									updateWritings(writingNum);
									break;
								case 3:
									deleteWritings(writingNum);
									break;
								}// switch(select)
							} // if
							else {
								System.out.println("잘못된 게시글을 선택하셨습니다.");
							} // else
						} // if
						else {
							System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
						} //
					} // else
				} // while(true)
			} // if
			else {
				int signIn = signIn();
				if (signIn == -1) {
					System.out.println("일치하는 ID가 존재하지 않습니다.");
				} // if 아이디 없음
				else if (signIn == -2) {
					System.out.println("비밀번호가 일치하지 않습니다.");
				} // else if 비밀번호 불일치
				else {
					checkSignIn = true;
					signIndex = signIn;
				}//else
			} // else
		} // while(true)

	}// showMine()

	public void updateWritings(int writingNum) {
		System.out.print("제목 수정 : ");
		sc.nextLine();
		String modifyTitle = sc.nextLine();
		System.out.print("내용 수정 : ");
		String modifyContents = sc.nextLine();
		writings[writingNum] = new Board(modifyTitle, modifyContents, writings[writingNum].getAuthor());
		System.out.println("수정이 완료되었습니다.");
	}// updateWritings()

	public void deleteWritings(int writingNum) {
		for (int i = writingNum; i < bIndex - 1; i++) {
			writings[i] = writings[i + 1];
		} // for
		writings[--bIndex] = null;
		System.out.println("삭제가 완료되었습니다.");
	}// deleteWritings()

	public void deleteMembership() {
		while(true) {
			if(checkSignIn) {
				int signIn = signIndex;
				System.out.print("비밀번호를 다시 한번 입력하세요 : ");
				String pw = sc.next();
				if(pw.equals(users[signIn].getPw())) {
					for (int i = signIn; i < uIndex - 1; i++) {
						users[i] = users[i + 1];
					} // for
					users[--uIndex] = null;
					System.out.println("탈퇴가 완료되었습니다.");
					return;
				}//if
				System.out.println("비밀번호를 잘못 입력하셨습니다.");
			}//if
			else {
				int signIn = signIn();
				if (signIn == -1) {
					System.out.println("일치하는 ID가 존재하지 않습니다.");
				} // if 아이디 없음
				else if (signIn == -2) {
					System.out.println("비밀번호가 일치하지 않습니다.");
				} // else if 비밀번호 불일치
				else {
					checkSignIn = true;
					signIndex = signIn;
				}//else
			}//else
		}//while(true)
	}// deleteMembership()

	public int searchUser(String id) {
		for (int i = 0; i < uIndex; i++) {
			if (id.equals(users[i].getId())) {
				return i;
			} // if
		} // for
		return -1;
	}// searchUser

	public int signIn() {
		System.out.print("ID : ");
		String id = sc.next();
		System.out.print("PW : ");
		String pw = sc.next();
		int searchUIndex = searchUser(id);
		if (searchUIndex == -1) {
			return -1;
		} // if
		else if (pw.equals(users[searchUIndex].getPw())) {
			return searchUIndex;
		} // else if
		else {
			return -2;
		} // else
	}// signIn()
}
