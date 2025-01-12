package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 1. 컨트롤러임을 선언하는 어노테이션
public class FirstController {

    @GetMapping("/hi") // 2. URL 주소인 /hi 넣고 URL 요청 접수하기
    public String niceTOMeetYou(Model model) { // 3. Model model -> model 객체 받아 오며 메서드 수행
        // model 객체가 "최현우" 값을 "username"에 연결해 웹 드라우저로 보냄
        model.addAttribute("username", "choi hyun woo"); // 4. 모델 변수 등록 -> 우측 값을 좌측값이라는 이름으로 추가
        return "greetings"; // 5. 뷰 템플릿 페이지 변환
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("nickname", "현우");
        return "goodbye";
    }
}
