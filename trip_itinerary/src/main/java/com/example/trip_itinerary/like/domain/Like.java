package com.example.trip_itinerary.like.domain;

import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.trip.domain.Trip;
import jakarta.persistence.*;

@Entity
@Table(name = "like")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    protected Like() {
    }

    private Like(Member member, Trip trip) {
        this.member = member;
        this.trip = trip;
    }

    public static Like of(Member member, Trip trip) {
        return new Like(member, trip);
    }

    public Long getLikeId() {
        return this.likeId;
    }

    public Member getMember() {
        return this.member;
    }

    public Trip getTrip() {
        return this.trip;
    }
}
