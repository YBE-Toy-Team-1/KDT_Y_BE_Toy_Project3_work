package com.example.trip_itinerary.member.domain;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    protected Member() {
    }

    public Long getId() {
        return id;
    }

    public Member(Long id) {
        this.id = id;
    }
}
