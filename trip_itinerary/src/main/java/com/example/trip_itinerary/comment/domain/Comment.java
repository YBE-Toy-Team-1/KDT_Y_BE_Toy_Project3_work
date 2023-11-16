package com.example.trip_itinerary.comment.domain;

import com.example.trip_itinerary.comment.dto.request.CommentUpdateRequest;
import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.trip.domain.Trip;
import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    private String content;

    protected Comment() {
    }

    private Comment(Member member, Trip trip, String content) {
        this.member = member;
        this.trip = trip;
        this.content = content;
    }

    public static Comment of(Member member, Trip trip, String content) {
        return new Comment(member, trip, content);
    }

    public void update(CommentUpdateRequest request) {
        this.content = request.getContent();
    }

    public Long getId() {
        return this.id;
    }

    public Trip getTrip() {
        return this.trip;
    }

    public String getContent() {
        return this.content;
    }

    public Member getMember() {
        return this.member;
    }
}
