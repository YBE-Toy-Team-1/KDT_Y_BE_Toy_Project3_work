package com.example.trip_itinerary.member.repository;

import com.example.trip_itinerary.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
