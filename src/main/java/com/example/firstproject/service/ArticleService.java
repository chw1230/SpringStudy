package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j // 로깅 위해서!
@Service // 서비스 객체 생성 -> 해당 클래스를 서비스로 인식하고 스프링 부트에 객체 생성
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository; // 리파지터리와 협업하기 위해 선언하고 객체 주입

    public List<Article> index() {
        return articleRepository.findAll(); // 데이터는 리퍼지토리에서 가져오므로 Service의 index() 메서드에서는 이와같이 DB 조회 결과를 가져오기
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null); //  데이터는 리퍼지토리에서 가져오므로 Service의 index() 메서드에서는 이와같이 DB 조회 결과를 가져오기
    }

    public Article create(ArticleForm dto) { // POST의 요청 데이터를 전달해야하기 때문에 dto을 매개변수로 사용
        Article article = dto.toEntity(); // 기존에 컨트롤러에서 한 것 처럼 dto를 엔티티로 변환
        if(article.getId() != null){
            return null;
        }
        return articleRepository.save(article); // 엔티티를 레포지토리에 저장해서 반환
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. DTO -> 엔티티 <변환>
        Article article = dto.toEntity();
        log.info("id: {},article {}", id, article.toString()); // 로그 찍기

        // 2. 타깃 조회(DB에 있는지 없는지 여부 확인)
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리 (잘못된 요청 ->대상 엔티티가 없는 경우 or 수정 요청 id와 본문 id가 다른 경우({id}와 article.getId() 가 다른 경우)
        if (target == null || id != article.getId()) {
            log.info("잘못된 요청! id: {},article: {}", id, article.toString()); // 잘못된 거라고 로그 찍기!
            return null;  // 그냥 잘못된 것을 알리는 null을 보내면 컨트롤러에서 에러 메시지 띄울 것!
        }

        // 4. 업데이트 및 정상응답 반환
        target.patch(article); // Article에 가서 patch() 메서드 생성하기!
        Article updated = articleRepository.save(target);
        return updated; // 여기서는 수정한 데이터를 저장시켜서 보내는 역할!! 컨트롤러와 서비스의 업무 분담!
    }

    public Article delete(Long id) {
        // 1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        // 2. 잘못된 요청 처리하기
        if (target == null) {
            return null;
        }
        // 3. 대상 삭제하기
        articleRepository.delete(target);
        return target; // .body(null) 대신 .build 작성해도됨!
    }

    @Transactional // 트랜젝션 어노테이션 붙이면 해당 메서드는 하나의 트랜잭션으로 묶인다!!! 그러면 중간에 실패를 하더라도 록백을 통해 이전 상태로 돌아갈 수 있음!!
    public List<Article> createArticle(List<ArticleForm> dtos) {
        // 1. dto 묶음음 엔티티 묶음으로 바꾸기
        List<Article> articleList = dtos.stream() // 스트림 문법 사용하기!!!, 1번-dtos 스트림화 하기 , 4번-articleList에 저장하기
                .map(dto -> dto.toEntity()) // 2번-map()으로 dto가 하나하나 올 때마다 dto.toEntity()를 수행해 매핑하기
                .collect(Collectors.toList()); // 3번-이렇게 매핑한 것을 리스트로 묶기

        // 2. 엔티티 묶음을 DB에 저장하기
        articleList.stream() // 1번-스트림화 하기
                .forEach(article -> articleRepository.save(article)); // 2번-엔티티 묶음을 DB에 저장하기

        // 3. 강제로 에러 발생시키기
        articleRepository.findById(-1L) // 1번-id를 -1인 데이터 찾기(강제 에러 빌드업)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패!")); // 2번- 찾는 데이터 없으면 예외 발생!

        // 4. 결과 반환하기
        return articleList;
    }
}
