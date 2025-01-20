package com.example.firstproject.service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j // 로깅 위해서!
@Service
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;

    // R
    public Iterable<Coffee> index() {
        return coffeeRepository.findAll();
    }

    public Coffee show(Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    // C
    public Coffee create(CoffeeDto coffeeDto) {
        Coffee coffee = coffeeDto.toEntity();
        if (coffee.getId() != null) {
            return null; // 이미 존재 하는 경우 null 반환
        }
        // 아닌 경우
        return coffeeRepository.save(coffee);
    }

    // U
    public Coffee update( Long id,  CoffeeDto coffeeDto) {
        // 1. dto -> entity
        Coffee coffee = coffeeDto.toEntity();
        log.info("id: {},coffee {}", id, coffee.toString()); // 로그 찍기

        // 2. entity에 들어있는 지 + 업데이트 하려는 id와 DB 속 id랑 같은지 확인하고 반환 (있으면 + 맞으면 수정값, 없으면 + 안 맞으면 null)
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if (target == null || id != coffee.getId()) {
            log.info("잘못된 요청! id: {},coffee: {}", id, coffee.toString()); // 잘못된 거라고 로그 찍기!
            return null;
        }
        target.patch(coffee); // 어떤 요소가 null(입력 되지 않은 상태)이라면 그냥 기존에 있던 값을 그대로 나오게 하겠다는 patch() 사용!
        return coffeeRepository.save(target);
    }

    // D
    public Coffee delete(Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if (target != null) {
            coffeeRepository.delete(target);
            return target;
        }
        return null;
    }
}
