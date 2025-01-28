package com.example.firstproject.dto;


import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class CommentDto {
    private Long id; // 댓글의 id
    @JsonProperty("article_id") // JSON 데이터의 키 이름과 이를 저장하는 DTO에 선언된 필드 명이 다를 경우에 이렇게 작성해주면 매핑이 됨!!
    private Long articleId; // 댓글의 부모 id
    private String nickname; // 댓글 작성자
    private String body; // 댓글 본문

    // 정적 메서드로 선언! 객체 생성 없이 호출 가능한 메서드라는 뜻 (=생성 메서드)
    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(), // 댓글 엔티티의 id
                comment.getArticle().getId(), // 댓글 엔티티가 속한 부모 게시글의 id
                comment.getNickname(), // 댓글 엔티티의 nickname
                comment.getBody() // 댓글 엔티티의 body
        );
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
