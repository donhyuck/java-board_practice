package board.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import board.dto.Article;
import board.util.Util;

public class ArticleController {

	private Scanner sc;
	private List<Article> articles;

	public ArticleController(Scanner sc, List<Article> articles) {
		this.sc = sc;
		this.articles = articles;
	}

	public void showList(String command) {

		if (articles.size() == 0) {
			System.out.println("등록된 게시글이 없습니다.");
			return;
		}

		String searchKeyword = command.substring("article list".length()).trim();

		List<Article> forListArticles = articles;

		if (searchKeyword.length() > 0) {
			forListArticles = new ArrayList<>();

			for (Article article : articles) {
				if (article.title.contains(searchKeyword)) {
					forListArticles.add(article);
				}
			}

			if (forListArticles.size() == 0) {
				System.out.println("검색결과가 존재하지 않습니다.");
				return;
			}

		}

		System.out.println("== 게시글 목록 ==");
		System.out.println("번호 /   작성일   / 제목 / 조회수");

		for (int i = forListArticles.size() - 1; i >= 0; i--) {

			Article currentArticle = forListArticles.get(i);
			System.out.printf(" %d / %s / %s / %d\n", currentArticle.id, currentArticle.regDate, currentArticle.title,
					currentArticle.hit);
		}

	}

	public void doWrite() {

		System.out.println("== 게시글 작성 ==");

		int id = articles.size() + 1;

		System.out.print("글 제목 : ");
		String title = sc.nextLine();

		System.out.print("글 내용 : ");
		String body = sc.nextLine();

		String regDate = Util.getCurrentDate();

		Article article = new Article(id, regDate, title, body);

		articles.add(article);

		System.out.printf("%d번 글이 등록되었습니다.\n", id);

	}

	public void doModify(String command) {

		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시글을 찾을 수 없습니다.\n", id);
			return;
		}

		System.out.println("== 게시글 수정 ==");

		System.out.print("새 제목 : ");
		String title = sc.nextLine();

		System.out.print("새 내용 : ");
		String body = sc.nextLine();

		foundArticle.title = title;
		foundArticle.body = body;

		System.out.printf("%d번 게시글이 수정되었습니다.\n", id);

	}

	public void doDelete(String command) {

		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		int foundIndex = getArticleIndexById(id);

		if (foundIndex == -1) {
			System.out.printf("%d번 게시글을 찾을 수 없습니다.\n", id);
			return;
		}

		articles.remove(foundIndex);

		System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);

	}

	private int getArticleIndexById(int id) {

		int i = 0;

		for (Article article : articles) {
			if (article.id == id) {
				return i;
			}

			i++;
		}

		return -1;
	}

	private Article getArticleById(int id) {

		int index = getArticleIndexById(id);

		if (index != -1) {
			return articles.get(index);
		}

		return null;
	}

	public void showDetail(String command) {

		String[] commandBits = command.split(" ");
		int id = Integer.parseInt(commandBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시글을 찾을 수 없습니다.\n", id);
			return;
		}

		foundArticle.increaseHit();

		System.out.println("== 게시글 상세보기 ==");

		System.out.printf("번 호 : %d\n", foundArticle.id);
		System.out.printf("작성일 : %s\n", foundArticle.regDate);
		System.out.printf("제 목 : %s\n", foundArticle.title);
		System.out.printf("내 용 : %s\n", foundArticle.body);
		System.out.printf("조회수 : %d\n", foundArticle.hit);

	}

}
