package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // 엔티티임을 선언
@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Member {
    @Id // 엔티티의 대푯값 지정
    @GeneratedValue // id에 숫자 자동 생성 1번째 입력, 2번째 입력 구분 가능!
    Long id;

    @Column // 테이블에 열로 연결
    String email;
    @Column // 테이블에 열로 연결
    String password;

    /*
    public Member(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getId() {
    }
    롬복 처리!
     */
}
