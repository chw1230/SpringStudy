package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // REST API 컨트롤러!
public class CoffeeApiController {
    @Autowired
    private CoffeeRepository coffeeRepository;

    // R
    @GetMapping("/api/coffees") // 목록 데이터 조회
    public Iterable<Coffee> index() {
        return coffeeRepository.findAll();
    }

    @GetMapping("/api/coffees/{id}") // 단일 데이터 조회
    public Coffee show(@PathVariable Long id) {
        return coffeeRepository.findById(id).orElse(null);
    }

    // C
    @PostMapping("/api/coffees")
    public Coffee create(@RequestBody CoffeeDto coffeeDto) {
        Coffee coffee = coffeeDto.toEntity();
        return coffeeRepository.save(coffee);
    }

    // U
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> upgrade(@PathVariable Long id, @RequestBody CoffeeDto coffeeDto) {
        Coffee coffee = coffeeDto.toEntity();
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if (target == null || id != coffee.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        target.patch(coffee); // 어떤 요소가 null(입력 되지 않은 상태)이라면 그냥 기존에 있던 값을 그대로 나오게 하겠다는 patch() 사용!
        Coffee updated = coffeeRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    // D
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id) {
        Coffee target = coffeeRepository.findById(id).orElse(null);
        if (target != null) {
            coffeeRepository.delete(target);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
