package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
}
