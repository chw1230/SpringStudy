package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 새 어노테이션 추가 -> title과 content를 매개변수로 하는 생성자 자동으로 생성! 생산성 올라감
@ToString // 새 어노테이션 추가 -> toString()메서드 사용하는 것과 똑같음! 생산성 올라감
public class ArticleForm {
    private Long id;
    private String title;
    private String content;

//    기존 생성자 코드 삭제
//    public ArticleForm(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }
//    기존 메서드 코드 삭제
//    @Override
//    public String toString() {
//        return "ArticleForm{" +
//                "title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }

    public Article toEntity() {
        return new Article(id, title, content);
    }
}
