package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Coffee {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // 자동 생성!!
    private Long id;

    @Column
    private String name;
    @Column
    private String price;

    public void patch(Coffee coffee) {
        if (coffee.name != null) {
            this.name = coffee.name;
        }
        if (coffee.price != null) {
            this.price = coffee.price;
        }

    }
}
