package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Slf4j // 로깅을 위한 어노테이션 추가!!
@Controller // 컨트롤러 선언
public class ArticleController {
    @Autowired // 스프링 부트가 미리 생성해 놓은 리파지터리 객체 주입(DI) -> new 로 생성할 필요없음!!
    private ArticleRepository articleRepository; // articleRepository 객체 선언

    @GetMapping("/articles/new") // URL 요청 접수
    public String newArticleForm() { // 메서드 생성 및 반환값 작성
        return "articles/new"; // 반환값으로 뷰 페이지 이름 리턴
    }

    // post 방식으로 선언했기 때문에 @PostMapping 사용!!
    // 괄호 안에는 받는 URL 주소(action="~"여기에 사용한 주소!)를 넣는다!!
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) { // 폼 데이터를 DTO로 받기
        log.info(form.toString()); // 로깅 코드 추가
        // System.out.println(form.toString()); // DTO에 폼 데이터가 잘 담겼는지 확인 출력 -> println() 실제 서버에서 사용 X!

        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString()); // 로깅 코드 추가
        // System.out.println((article.toString())); // DTO 가 엔티티로 잘 변환되는지 확인 출력 -> println() 실제 서버에서 사용 X!

        // 2. 라파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article); // article  엔티티를 저장해 saved 객체에 반환
        log.info(saved.toString()); // 로깅 코드 추가
        // System.out.println(saved.toString()); // article DB에 잘 저장되는지 확인 출력 -> println() 실제 서버에서 사용 X!
        return "redirect:/articles/" + saved.getId(); // 리다이렉트 작성 위치 -> "redirect:URL" + 아이디별로 URL 반환시키기 위해서!! saved 객체를 이용하여 아이디값 가져오기
    }

    @GetMapping("/articles/{id}") // 데이터 조회 요청 접수 id 변수로서 사용
    public String show(@PathVariable Long id, Model model) { // 요청을 받아서 수행할 show() 메서드 생성 + @PathVariable Long id -> URL의 id 전달값을  매개변수로 가져오는 어노테이션 + 모델을 위한 model 객체 매개변수로 받기
        log.info("id = " + id); // id 확인 로그 찍기
        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); // findById는 특정 엔티티의 id값을 기준으로 데이터를 찾아 Optional 타입으로 반환!! , .orElse(null) 메서드 id 값으로 데이터 찾을 때 해당 id 값이 없으면 null을 반환하라는 뜻
        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        // 3. 뷰 페이지 반환하기
        return "articles/show"; // 목록으로 돌아가기 링크를 넣을 뷰 파일!! 가서 Back 코드 추가하기!
    }

    @GetMapping("/articles") // URL 요청 받기!!
    public String index(Model model) {
        // 1. 모든 데이터 가져오기
        ArrayList<Article> articleEntityList = articleRepository.findAll(); // 데이터 타입 불일치 일어남!!
        // findAll() 메서드가 Iterable 이 아닌 ArrayList를 반환하도록 수정 -> ArticleRepository에서!! 오버라이딩하기!!
        // 2. 모델에 데이터 등록하기
        model.addAttribute("articleList", articleEntityList); // articelEntityList 등록
        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) { // id를 매개변수로 가져오기!
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); // DB에서 수정할 데이터 가져오기 (DB에서 가져올때 리파지터리이용+findById()메서드로 찾기+못찾으며면Null)
        model.addAttribute("article", articleEntity); // articleEntity를 article로 등록
        // 뷰 페이지 설정하기
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) { // 매개변수로 DTO 받기
        log.info(form.toString());
        // 1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        // 2. 엔티티를 DB에 저장하기
        // 2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        // 2-2. 기존 데이터 값 갱신하기
        if (target != null) { // target 이 null 아니라면 -> 즉 기존 데이터가 있다면!
            articleRepository.save(articleEntity); // 엔티티를 DB에 저장 (갱신)
        }
        // 3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다."); //  delete메서드가 잘 작동하는 확인하는 로그
        // 1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString()); // 데이터의 존재여부를 확인하는 로그
        // 2. 대상 엔티티 삭제하기
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        // 3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }
}