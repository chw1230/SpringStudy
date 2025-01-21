package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스를 스프링 부트와 연동해 테스트 -> 테스트 코드에서 스프링 부트가 관리하는 다양한 객체를 주입받을 수 있다.
class ArticleServiceTest {
    @Autowired
    ArticleService articleService; // articleService 객체 주입

    @Test // 해당 메서드가 테스트를 위한 코드라고 선언
    void index() {
        // 1. 예상 데이터 - data.sql의 데이터들을 예상 데이터로 저장!
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        // Arrays.asList() 메서드를 통해 ArrayList로 합치고 이를 List<Article> 타입의 expected에 저장한다.
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));

        // 2. 실제 데이터 - 모든 게시글을 조회 요청하고 그 결과로 반환되는 게시글의 묶음을 받아오는 것
        List<Article> articles = articleService.index();

        // 3. 비교 및 검증
        // assertEquals()를 통해서 실제 데이터(문자열 형태)와 예상 데이터(문자열 형태)가 일치하는지 비교
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공_존재하는_id_입력() { // 1-메서드 이름 수정
        // 1. 예상 데이터 -> id가 1인 게시물의 조회를 요청했다고 가정해서 작성!
        Long id = 1L; // 3-예상 데이터 저장
        Article expected = new Article(id, "가가가가", "1111"); // 3-예상 데이터 저장

        // 2. 실제 데이터
        Article article = articleService.show(id); // 2-실제 데이터 저장

        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString()); // 4-비교
    }

    @Test
    void show_실패_존재하지_않는_id_입력() {
        // 1. 예상 데이터 -> 일부러 잘못된 id로 조회하는 상황을 가정!!!
        Long id = -1L;
        Article expected = null;

        // 2. 실제 데이터
        Article article = articleService.show(id);

        // 3. 비교 및 검증
        assertEquals(expected ,article);
        // 예상 데이터와 실제 데이터에서 모두 null을 반환한다고 했기 때문에 테스트에 통과되는 것!
    }

    @Transactional // 트랜잭션을 추가!!
    @Test
    void create_성공_title과_content만_있는_dto_입력() {
        // 1. 예상 데이터 -> 새 게시물을 생성한 상황을 가정해서 작성!
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content); // 위의 예상 데이터를 통해서 dto 생성하기!, id는 자동생성!!!
        Article expected = new Article(4L, title, content); // 자동 생성될 값이 4L 넣어주기!

        // 2. 실제 데이터
        Article article = articleService.create(dto);

        // 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Transactional // 트랜잭션을 추가!!
    @Test
    void create_실패_id가_포함된_dto_입력() {
        // 1. 예상 데이터 -> 새 게시글을 만드는데 id가 필요없음에도 넣은 상황을 가정한 것!
        Long id = 4L;
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content); // 위의 예상 데이터를 통해서 dto 생성하기!, id값도 넣기!
        Article expected = null;

        // 2. 실제 데이터
        Article article = articleService.create(dto);

        // 비교 및 검증
        assertEquals(expected, article);
    }
    @Test
    @Transactional
    void update_성공_존재하는_id와_title_content가_있는_dto_입력() {
        // 1. 예상
        Long id = 1L;
        String title = "가가가가";
        String content = "1234";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(id, title, content);

        // 2. 실제
        Article article = articleService.update(id,dto);

        // 3. 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title만_있는_dto_입력() {
        // 1. 예상
        Long id = 1L;
        String title = "가가가가";
        String content = null;
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(id, "가가가가", "1111");

        // 2. 실제
        Article article = articleService.update(id,dto);

        // 3. 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_실패_존재하지않는_id의_dto_입력() {
        // 1. 예상
        Long id = -1L;
        String title = "가가가가";
        String content = "1234";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        // 2. 실제
        Article article = articleService.update(id,dto);

        // 3. 비교
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void delete_성공_존재하는_id_입력() {
        // 1. 예상
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        // 2. 실제
        Article article = articleService.delete(id);

        // 3. 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_실패_존재하는_id_입력() {
        // 1. 예상
        Long id = -1L;
        Article expected = null;

        // 2. 실제
        Article article = articleService.delete(id);

        // 3. 비교
        assertEquals(expected, article);
    }


}