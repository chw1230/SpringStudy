package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional // create 메서드는 DB 내용을 바꾸기 때문에 실패할 경우 롤백해야하기 때문에 트랜젝션 처리 해주기!!
    public CommentDto create(Long articleId, CommentDto dto) {
        // 1. DB에서 부모 게시글을 조회해 가져오고 없을 경우 예외 발생시키기
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! " + "대상 게시글이 없습니다."));
        // .orElseThrow() 메서드 => Optional 객체에 값이 존재하면 그 값을 반환하고, 값이 존재하지 않으면 전달값으로 보낸 예외를 발생시키는 메서드이다.
        // 전달 값으로 IllegalArgumentException 클래스를 사용하면 메서드가 잘못됐거나 부적합한 전달값을 보냈음을 나타낸다.

        // 2 부모 게시글의 새 댓글 엔티티 생성하기
        Comment comment = Comment.createComment(dto,article);

        // 3. 생성된 엔티티를 DB에 저장하기
        Comment created = commentRepository.save(comment);

        // 4. DB에 저장한 엔티티를 DTO로 변환해 반환하기
        return CommentDto.createCommentDto(created);
    }

    @Transactional // update 메서드는 DB 내용을 바꾸기 때문에 실패할 경우 롤백해야하기 때문에 트랜젝션 처리 해주기!!
    public  CommentDto update(Long id, CommentDto dto) {
        // 1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패 " + "대상 댓글이 없습니다,"));

        // 2. 댓글 수정
        target.patch(dto);

        // 3. DB로 갱신
        Comment updated = commentRepository.save(target);

        // 4. 댓글 앤티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional // delete 메서드는 DB 내용을 바꾸기 때문에 실패할 경우 롤백해야하기 때문에 트랜젝션 처리 해주기!!
    public CommentDto delete(Long id) {
        // 1. DB에서 해당 댓글을 조회해 가져오고 없을 경우 예외 발생시키기
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패!" + "대상이 없습니다."));

        // 2. 가져온 댓글을 DB에서 delete하기
        commentRepository.delete(target);

        // 3. DTO로 변환해서 반환하기
        return CommentDto.createCommentDto(target);
    }
}
