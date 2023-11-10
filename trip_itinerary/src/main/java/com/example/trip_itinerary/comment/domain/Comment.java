package com.example.trip_itinerary.comment.domain;

import com.example.trip_itinerary.comment.dto.request.UpdateCommentRequest;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    private String content;

    @Builder
    public Comment(User user, Trip trip, String content) {
        this.user = user;
        this.trip = trip;
        this.content = content;
    }

    public void update(UpdateCommentRequest request) {
        this.content = request.getContent();
    }
}
