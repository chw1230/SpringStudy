package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j // 로그찍기 위해 추가함!
@RestController
public class ArticleApiController {
    @Autowired // ArticleService 클래스에서 객체를 만들면 REST 컨트롤러에서 객체 주입(생성 객체를 가져와 연결하는 방식=AutoWeried)하는 방식으로 서비스 객체 선언
    private ArticleService articleService;

    // GET - 조회(Read)
    @GetMapping("/api/articles") // URL 요청 접수 <목록 데이터 조회>
    public List<Article> index() { // index 메서드를 정의( Article의 묶음을 반환해야하기 때문에 반환형으로 List<Article>를 사용
        return articleService.index(); // 서비스를 통해서 가져오기! -> Service 객체에 index 메서드 만들기
    }

    @GetMapping("/api/articles/{id}") // URL 요청 접수 <단일 데이터 조회>
    public Article show(@PathVariable Long id) { // show 메서드를 정의( Article의 단일 반환해야하기 때문에 반환형으로 Article를 사용
        return articleService.show(id); // Service의 show() 메서드 사용
    }

    // POST - 생성(Create)
    @PostMapping("/api/articles") // URL 요청 접수 <데이터 생성>
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) { // create 메서드를 정의 ( 데이터 받을 dto 매개변수로 받아오기)
        Article created = articleService.create(dto); // 서비스가 처리하도록 바꾸기!
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH - 수정(Update)
    @PatchMapping("api/articles/{id}") // URL 요청 접수 <데이터 수정>
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) { // create 메서드를 정의 ( id와 데이터를 dto 매개변수로 받아오기)
        Article updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
    }

    // DELETE - 삭제(Delete)
    @DeleteMapping("api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleted) :
                ResponseEntity.status((HttpStatus.BAD_REQUEST)).build();
    }

    @PostMapping("/api/transaction-test") // 여러 게시글 생성하는 요청 접수
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article> createdList = articleService.createArticle(dtos); // 서비스 호출!!
        return (createdList != null) ? // 생성 결과에 따른 응답처리
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
