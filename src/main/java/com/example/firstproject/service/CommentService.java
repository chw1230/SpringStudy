package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository; // 댓글 생성시 게시글의 존재 여부를 파악해야하기 때문


    public List<CommentDto> comments(Long articleId) {
        /*
        // 1. 댓글 조회 - articleId번의 게시글의 댓글을 모두 조회
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        // 댓글이 여러개 일 수도 있어서 Comments를 List로 받기

        // 2. 엔티티 -> DTO 변환
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        // 비어있는 dtos에 DTO 하나씩 담기 앞에서 가져온 comments(댓글 엔티티 목록)에서 엔티티를 하나씩 꺼내 DTO로 변환후 추가하기
        for (int i = 0; i < comments.size(); i++) {
            Comment c = comments.get(i); // 리스트에 있는 것 엔티티들을 하나씩 꺼내서 Comment c변수에 저장 시키기
            CommentDto dto = CommentDto.createCommentDto(c); // 엔티티를 DTO로 바꿔서 dto 변수에 저장하기
            // createCommentDto( ) 메서드 정적 메서드라 클래스 통해서 접근~
            dtos.add(dto); // dto를 리스트인 dtos에 추가해주기!
        }*/
        // 스트림 문법을 통해서 코드 리펙토링 하기!
        // 3. 결과 반환
        return commentRepository.findByArticleId(articleId) // articleId번의 게시글의 댓글을 모두 가져오기 (엔티티 목록 가져오기)
                .stream() // 스트림 문법 사용하기
                .map(comment -> CommentDto.createCommentDto(comment)) // 스트림의 각 요소(좌측-comment)을 꺼내 우측(CommentDto.createCommentDto(comment))을 수행
                // 댓글 엔티티를 하나씩 꺼내서 DTO로 매핑 하는 것!
                .collect(Collectors.toList()); //  반환형을 맞추기위해서 collect() 메서드 추가하기 -> 리스트 반환형으로 바꾸기
    }
}
