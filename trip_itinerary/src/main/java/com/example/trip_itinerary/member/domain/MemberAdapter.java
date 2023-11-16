package com.example.trip_itinerary.member.domain;

import org.springframework.security.core.userdetails.User;

public class MemberAdapter extends User {
    private final Member member;

    public MemberAdapter(Member member) {
        super(member.getUsername(), member.getPassword(), member.getAuthorities());
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
