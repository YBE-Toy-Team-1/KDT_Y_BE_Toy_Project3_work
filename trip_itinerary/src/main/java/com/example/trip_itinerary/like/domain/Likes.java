package com.example.trip_itinerary.like.domain;

import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.trip.domain.Trip;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    public Likes(Member member, Trip trip) {
        this.member = member;
        this.trip = trip;
//        trip.mappingLikes(this);  연관관계 편의 메서드
    }
}
