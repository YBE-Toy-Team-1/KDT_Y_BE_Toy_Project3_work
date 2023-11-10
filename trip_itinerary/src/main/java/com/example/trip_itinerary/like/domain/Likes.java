package com.example.trip_itinerary.like.domain;

import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.user.domain.User;
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
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Trip_id")
    private Trip trip;

    public Likes(User user, Trip trip) {
        this.user = user;
        this.trip = trip;
    }
}
