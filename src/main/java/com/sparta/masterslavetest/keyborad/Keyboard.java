package com.sparta.masterslavetest.keyborad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor
public class Keyboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Keyboard(SaveRequest request) {
        this.name = request.getName();
    }
}
