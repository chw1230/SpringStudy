package com.example.firstproject.entity;

// 474쪽 문제 피자 엔티티와 REST API 주소 만들기
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String name;
    @Column
    double price;
}

/*
1. 생성(POST)
/pizzas
ex>
{
    "name" : "페퍼로니",
    "price" : "14000"
}

2-1. 조회(단건,GET)
/pizzas/id

2-2. 조회(목록,GET)
/pizzas

3. 수정(PATCH)
/pizzas/id
ex>
{
    "id" : 1,
    "name" : "페퍼로니",
    "price" : "29000"
}

4. 삭제
/pizzas/id
 */
