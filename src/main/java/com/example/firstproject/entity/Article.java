package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 밑에서 삭제한 생성자 대신 작성
@NoArgsConstructor // 기본 생성자 추가 어노테이션 --> 롬복 사용!!!!!!!
@ToString // 밑에서 삭제한 메서드 대신 작성
@Entity // 엔티티임을 선언함
@Getter // GetId() 을 간단히 표현하기 위해사용!
public class Article {
    @Id // 엔티티의 대푯값 지정
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // 앞으로 데이터를 생성할 할때마다 DB가 알아서 id에 값을 넣음!!!!
    private Long id;

    @Column // title 필드 선언, DB 테이블의 title 열과 연결됨
    private String title;
    @Column // content 필드 선언, DB 테이블의 content 열과 연결됨
    private String content;

    // 일부 수정하는 경우 기존에 있는 데이터에 새로 입력된 데이터만 수정하는 메서드 만들기!
    public void patch(Article article) {
        // ex> 새로 입력할 때 content만 입력했다면 title은 null이니까 content만 수정되는 것!!
        if (article.title != null) {
            this.title = article.title;
        }
        if (article.content != null) {
            this.content = article.content;
        }
    }

    /*롬복으로 코드 생략!!!! @Getter 코드 추가 해주기!
    public Long GetId() { //id Long 타입이니까 바꿔주기
        return id;
    }
     */

//    기존 생성자 코드 삭제
//    public Article(Long id, String title, String content) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//    }
//    기존 메서드 코드 삭제
//    @Override
//    public String toString() {
//        return "Article{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                '}';
//    }
}
