package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity // 해당 클래스는 엔티티임을 선언, 필드 클래스를 바탕으로 DB에 테이블 생성
@Getter // getter 메서드 자동 작성
@ToString // toString 메서드 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 가지는 생성자를 자동으로 생성
@NoArgsConstructor // 기본 생성자 자동 생성
public class Comment {
    // 1. Long 타입 id 대표키
    @Id // 대표키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id가 자동으로 1씩 상승
    private Long id;

    // 2. 해당 댓글의 부모 게시물
    @ManyToOne // Comment 엔티티와 Article 엔티티의 다대일 관계 설정
    @JoinColumn(name = "article_id") // 외래키 생성해서 매핑하기!!!!, Article 엔티티의 기본키(id)와 매핑
    private Article article;

    // 3. 댓글 단 사람
    @Column // 데이블의 속성으로 매핑
    private String nickname;

    // 4. 댓글 본문
    @Column // 테이블의 속성으로 매핑
    private String body;

    public void patch(CommentDto dto) {
        // 예외 발생
        // url의 id와 입력 JSON 데이터 속 id가 다른 경우
        if (this.id != dto.getId()) {
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");
        }
        // 객체 갱신 (업데이트)
        if (dto.getNickname() != null) { // 수정할 닉네임 대이터가 있다면
            this.nickname = dto.getNickname(); // 수정
        }
        if (dto.getBody() != null) { // 수정할 본문 내용이 있다면
            this.body = dto.getBody(); // 수정
        }
    }

    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 발생하는 경우
        // 1번 예외 - dto에 id가 입력되어 있는 경우( id는 자동으로 매겨지기때문에 클라이언트쪽에서 id를 입력하여 dto에 id가 들어 있을 수 없음! )
        if (dto.getId() != null) {
            // 의도적으로 IllegalArgumentException 예외 발생시키기
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        }
        // 2번 예외 - dto에서 가져온 부모 게시글과 엔티티에서 가져온 부모 게시글의 아이디가 다른경우
        if (dto.getArticleId() != article.getId()) {
            log.info(String.valueOf(dto.getArticleId()));
            log.info(String.valueOf(article.getId()));
            // 의도적으로 IllegalArgumentException 예외 발생시키기
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다..");
        }
        // 엔티티 생성 및 반환
        return new Comment(
                dto.getId(), // 댓글 아이디
                article, // 부모 게시글
                dto.getNickname(), // 댓글 닉네임
                dto.getBody() // 댓글 본문
        );
    }
}
