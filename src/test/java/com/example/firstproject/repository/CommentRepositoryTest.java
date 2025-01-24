package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // 클래스를 JPA와 연동 -> 리퍼지터리를 테스트하기 때문에!
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository; // 객체 주입!

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회") // 실제 메서드의 이름 변경없이 테스트 이름을 바꾸고 싶을 때 사용!
    void findByArticleId() {
        /* 경우 1 : 4번 게시글의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            Long articleId = 4L; // 4번 게시글 조회 하는 것 이므로 id - 4로 정해두기!

            // 2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId); // 실제 데이터 가져오기!

            // 3. 예상 데이터 - DB h2-console의 Comment 테이블에서 id가 4인 애들 보고 예상 테이터 결정하기!!
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 적어보기~"); // 3-2. 부모 게시글 객체 생성
            Comment a = new Comment(1L, article, "park", "비긴 어게인"); // 3-1. 예상 데이터 댓글 객체 생성
            Comment b = new Comment(2L, article, "choi", "인턴");
            Comment c = new Comment(3L, article, "kim", "어바웃 타임");
            List<Comment> expected = Arrays.asList(a, b, c); // 3-3. 댓글 객체를 합치기!

            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(),"4번 글의 모든 댓글을 출력!");
        }

        /* 경우 2 : 1번 게시글의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            Long articleId = 1L; // 1번 게시글 조회 하는 것 이므로 id - 1로 정해두기!

            // 2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId); // 실제 데이터 가져오기!

            // 3. 예상 데이터 - DB h2-console의 Comment 테이블에서 id가 1인 애들 보고 예상 테이터 결정하기!! -> 1은 댓글이 없는걸 알게됨!
            Article article = new Article(1L, "가가가가", "1111"); // 3-2. 부모 게시글 객체 생성
            List<Comment> expected = Arrays.asList(); // 3-3. 댓글 객체를 합치기!

            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(),"1번 글은 댓글이 없음!");
        }

        /* 경우 3 : 9번 게시글의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            Long articleId = 9L;

            // 2. 실제 데이터 준비
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 3. 예상 데이터 준비
            Article article = null;
            List<Comment> expected = Arrays.asList();

            // 4. 비교 및 검증
            assertEquals(expected.toString(),comments.toString(),"9번 글은 존재하지 않으므로 댓글이 없음!");
        }

        /* 경우 4 : 999번 게시글의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            Long articleId = 999L;

            // 2. 실제 데이터 준비
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 3. 예상 데이터 준비
            Article article = null;
            List<Comment> expected = Arrays.asList();

            // 4. 비교 및 검증
            assertEquals(expected.toString(),comments.toString(),"999번 글은 존재하지 않으므로 댓글이 없음!");
        }

        /* 경우 5 : -1번 게시글의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            Long articleId = -1L;

            // 2. 실제 데이터 준비
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 3. 예상 데이터 준비
            Article article = null;
            List<Comment> expected = Arrays.asList();

            // 4. 비교 및 검증
            assertEquals(expected.toString(),comments.toString(),"-1번 글은 존재하지 않으므로 댓글이 없음!");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회") // 실제 메서드의 이름 변경없이 테스트 이름을 바꾸고 싶을 때 사용!
    void findByNickname() {
        /* 경우 1 : "park"의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            String nickname = "park"; //  "park"의 모든 댓글 조회 하는 것 이므로 nickname - "park"으로 정해두기!

            // 2. 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname); // 실제 데이터 가져오기!

            // 3. 예상 데이터 -  DB h2-console의 Comment 테이블에서 nickname = "park" 인 애들 보고 예상 테이터 결정하기!!
            // id 에 따른 부모 게시글이 모두 다르기 떄문에 a, b, c 생성 시 article 필드에 각각 객체를 생성
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 적어보기~"), nickname, "비긴 어게인"); // 3-1. 예상 데이터 댓글 객체 생성
            Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 고고링~"), nickname, "비빔밥");
            Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 고고고"), nickname, "헬스");
            List<Comment> expected = Arrays.asList(a, b, c); // 3-3. 댓글 객체를 합치기!

            // 4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(),"park의 모든 댓글을 출력!");
        }

        /* 경우 2 : "kim"의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            String nickname = "kim";

            // 2. 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 3. 예상 데이터
            Comment a = new Comment(3L,new Article(4L, "당신의 인생 영화는?", "댓글 적어보기~"), nickname, "어바웃 타임");
            Comment b = new Comment(6L,new Article(5L, "당신의 소울 푸드는?", "댓글 고고링~"), nickname, "치킨");
            Comment c = new Comment(9L, new Article(6L, "당신의 취미는?", "댓글 고고고"), nickname, "산책");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 4. 비교 및 검증
            assertEquals(expected.toString(),comments.toString(),"kim의 모든 댓글을 출력!");
        }

        /* 경우 3 : null의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            String nickname = null;

            // 2. 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 3. 예상 데이터
            List<Comment> expected = Arrays.asList();

            // 4. 비교 및 검증
            assertEquals(expected.toString(),comments.toString(),"null의 모든 댓글을 출력!");
        }

        /* 경우 4 : ""의 모든 댓글 조회 */
        {
            // 1. 입력 데이터 준비
            String nickname = "";

            // 2. 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 3. 예상 데이터
            List<Comment> expected = Arrays.asList();

            // 4. 비교 및 검증
            assertEquals(expected.toString(),comments.toString(),"\"\"의 모든 댓글을 출력!");
        }
    }
}