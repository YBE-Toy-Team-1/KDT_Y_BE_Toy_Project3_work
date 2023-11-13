package com.example.trip_itinerary.comment.domain;

import com.example.trip_itinerary.comment.dto.request.UpdateCommentRequest;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    private String content;

    public Comment(Member member, Trip trip, String content) {
        this.member = member;
        this.trip = trip;
        this.content = content;
//        trip.mappingComment(this);    연관관계 편의 메서드
    }

    public void update(UpdateCommentRequest request) {
        this.content = request.getContent();
    }
}
