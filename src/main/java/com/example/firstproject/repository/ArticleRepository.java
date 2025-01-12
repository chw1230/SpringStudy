package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository; // CrudRepository 패키지 자동 임포트
import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<Article,Long> { // JPA에서 제공하는 인터페이스 이를 상속해 엔티티를 관리할 수 있다.
    // Article : 관리 대상 엔티티의 클래스 타입
    // Long : 관리 대상 엔티티의 대푯값 타입 Article.java 에 가면 id의 타입이 Long 임!


    // Iterable<Article> findAll(); Iterable을 ArrayList로 바꾸기!!!

    @Override
    ArrayList<Article> findAll();
}
