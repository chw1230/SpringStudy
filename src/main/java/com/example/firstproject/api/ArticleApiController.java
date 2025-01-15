package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j // 로그찍기 위해 추가함!
@RestController
public class ArticleApiController {
    @Autowired // 의존성 주입
    private ArticleRepository articleRepository;

    // GET - 조회(Read)
    @GetMapping("/api/articles") // URL 요청 접수 <목록 데이터 조회>
    public List<Article> index() { // index 메서드를 정의( Article의 묶음을 반환해야하기 때문에 반환형으로 List<Article>를 사용
        return articleRepository.findAll(); // findAll 로 모든 Article가져와 반환
    }

    @GetMapping("/api/articles/{id}") // URL 요청 접수 <단일 데이터 조회>
    public Article show(@PathVariable Long id) { // show 메서드를 정의( Article의 단일 반환해야하기 때문에 반환형으로 Article를 사용
        return articleRepository.findById(id).orElse(null); // findById(id) 로 특정 데이터 가져와 반환
    }

    // POST - 생성(Create)
    @PostMapping("/api/articles") // URL 요청 접수 <데이터 생성>
    public Article create(@RequestBody ArticleForm dto) { // create 메서드를 정의 ( 데이터를 dto 매개변수로 받아오기)
        Article article = dto.toEntity(); // dto 객체를 엔티티로 바꾸기
        return articleRepository.save(article); // 엔티티를 레포지토리에 저장해서 반환
    }

    // PATCH - 수정(Update)
    @PatchMapping("api/articles/{id}") // URL 요청 접수 <데이터 수정>
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) { // create 메서드를 정의 ( id와 데이터를 dto 매개변수로 받아오기)
        // 1. DTO -> 엔티티 <변환>
        Article article = dto.toEntity();
        log.info("id: {},article {}", id, article.toString()); // 로그 찍기

        // 2. 타깃 조회(DB에 있는지 없는지 여부 확인)
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리 (잘못된 요청 ->대상 엔티티가 없는 경우 or 수정 요청 id와 본문 id가 다른 경우({id}와 article.getId() 가 다른 경우)
        if (target == null || id != article.getId()) {
            log.info("잘못된 요청! id: {},article: {}", id, article.toString()); // 잘못된 거라고 로그 찍기!
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            /* ResponseEntity -> REST 컨트롤러의 반환형(응답을 위해 사용!)
            .status( ) 에 담기, ( ) 속에는 HttpStatus(HTTP 상태 코드를 관리).BAD_REQUEST를 넣기!
            .body 본문에는 null을 넣어서 반환!!
             */
        }

        // 4. 업데이트 및 정상응답 반환
        target.patch(article); // Article에 가서 patch() 메서드 생성하기!
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // DELETE
    @DeleteMapping("api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        // 1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        // 2. 잘못된 요청 처리하기
        if (target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 3. 대상 삭제하기
        articleRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).body(null); // .body(null) 대신 .build 작성해도됨!
    }
}
