package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class MemberForm {
    private Long id;
    private String email;
    private String password;

//    생성자 생략 가능!
//    public MemberForm(String email, String password) {
//        this.email = email;
//        this.password = password;
//    }
//    메서드 생략 가능!
//    @Override
//    public String toString() {
//        return "MemberForm{" +
//                "email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }

    public Member toEntity(){
        return new Member(id, email, password);
    }
}
