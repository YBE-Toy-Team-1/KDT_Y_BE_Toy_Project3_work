package com.example.trip_itinerary.like.domain;

import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.trip.domain.Trip;
import jakarta.persistence.*;

@Entity
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    protected Likes() {
    }

    private Likes(Member member, Trip trip) {
        this.member = member;
        this.trip = trip;
    }

    public static Likes of(Member member, Trip trip) {
        return new Likes(member, trip);
    }

    public Member getMember() {
        return this.member;
    }

    public Trip getTrip() {
        return this.trip;
    }
}
