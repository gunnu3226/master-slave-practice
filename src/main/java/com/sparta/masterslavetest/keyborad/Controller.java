package com.sparta.masterslavetest.keyborad;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final KeyboardRepository keyboardRepository;

    @PostMapping
    public void save(@RequestBody SaveRequest request) {
        keyboardRepository.save(new Keyboard(request));
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<Keyboard>> get() {
        return ResponseEntity.status(HttpStatus.OK).body(
            keyboardRepository.findAll()
        );
    }
}
