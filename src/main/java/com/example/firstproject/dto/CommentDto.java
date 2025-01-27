package com.example.firstproject.dto;


import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
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
}
