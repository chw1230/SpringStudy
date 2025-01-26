package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    // 1. 조회
    @GetMapping
    private ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {
        // 서비스에 넘기기
        // 결과 응답하기
        return null;
    }
    // 2. 생성
    // 3. 수정
    // 4. 삭제
}
