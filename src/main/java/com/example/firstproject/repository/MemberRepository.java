package com.example.firstproject.repository;

import com.example.firstproject.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member,Long> {
    //<member,Long>의 의미
//    member 는  관리 대상 엔티티의 클래스 타입을 의미한다.
//    Long 은 관리 대상 엔티티의 대푯값의 타입을 의미한다.
}
