package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // JpaRepository<Comment,Long> 상속 받아서 만들기
    // 왜 CrudRepository 가 아닌가?  CrudRepository -> 단순 Crud 작업
    // CRUD 작업 + 페이지 처리와 정렬 작업 -> JpaRepository

    // 네이티브 쿼리 메서드 사용하기!!!!! 쿼리를 메서드로 작성하는 것!!!
    // 방식 1 -> @Query 어노테이션 사용
    @Query(value = "SELECT * FROM comment WHERE article_id = :articleId", nativeQuery = true) // @Query -> 메서드로 쿼리 수행을 위한 어노테이션
    // 원래는 JPQL 언어로 쿼리 처리 하지만 nativeQuery = true 를 통해서 JPQL 대신 SQL 사용가능 !!! (대신 ':' 사용)
    // 특정 게시글의 모든 댓글 조회
    List<Comment> findByArticleId(Long articleId);

    // 특정 닉네임의 모든 댓글 조회
    // 방식 2 -> 네이티브 쿼리 XML 사용
    List<Comment> findByNickname(String nickname);

}
