package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    // 1. 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        // 서비스에 넘기기
        List<CommentDto> dtos = commentService.comments(articleId);
        // 결과 응답하기 - 성공하는 경우만 한정지어서 응답하기!
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 2. 생성
    @PostMapping("api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {
        // 서비스에 넘기기
        CommentDto dtos = commentService.create(articleId, dto);
        // 결과 응답하기
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 3. 수정
    @PatchMapping("api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) {
        // 서비스에 넘가기
        CommentDto updated = commentService.update(id, dto);
        // 결과 응답하기
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // 4. 삭제
    @DeleteMapping("api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){
        // 서비스에 넘기기
        CommentDto deleted = commentService.delete(id);
        // 결과 응답하기
        return ResponseEntity.status(HttpStatus.OK).body(deleted);
    }

}
