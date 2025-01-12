package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@Controller // 컨트롤러 선언
public class MemberController {
    @Autowired // 레포지터리 객체 주입(DI) -> new를 이용해서 생성할 필요없음!
    MemberRepository memberRepository; // 레포지터리 객체 선언

    @GetMapping("/members/new")
    public String newMemberForm() {
        return "members/new";
    }

    @GetMapping("/signup") // URL 요청 접수
    public String signUpPage() {
        return "members/new"; // 반환값으로 뷰 페이지 이름 리턴
    }

    @PostMapping("/join") // '어디에'에 해당
    public String join(MemberForm form) { // 폼 데이터를 DTO로 받기
        log.info(form.toString()); // 로깅
        //System.out.println(form.toString()); // DTO에 폼 데이터 잘 담겼나 확인 -> 로깅으로 변환!

        Member member = form.toEntity(); // DTO를 엔티티로 변환
        log.info(member.toString()); // 로깅
        //System.out.println(form.toString()); // 엔티티로 잘 변환되는지 확인 -> 로깅으로 변환!

        Member saved = memberRepository.save(member); // 엔티티를 저장해 saved에 객체에 반환
        log.info(saved.toString()); // 로깅
        //System.out.println(saved.toString()); // 잘 저장되었는지 확인 -> 로깅으로 변환!
        return "redirect:/members/" + saved.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {
        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", member);
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model) {
        Iterable<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Member memberEntity = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", memberEntity);
        return "members/edit";
    }

    @PostMapping("/members/update")
    public String update(MemberForm form) {
        // DTO -> 엔티티  바꾸기
        Member memberEntity = form.toEntity();
        // 엔티티를 저장하기
        // 그 전에 데이터의 기존 값 가져오기
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);// 2-2. 기존 데이터 값 갱신하기
        if (target != null) { // target 이 null 아니라면 -> 즉 기존 데이터가 있다면!
            memberRepository.save(memberEntity); // 엔티티를 DB에 저장 (갱신)
        }
        // 리다이렉트 사용
        return "redirect:/members/" + memberEntity.getId();
    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제하기 잘 실행됨");
        // 삭제 대상 가져오기
        Member target = memberRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 대상 삭제
        if (target != null) {
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        // 리다이렉트
        return "redirect:/members";
    }
}
