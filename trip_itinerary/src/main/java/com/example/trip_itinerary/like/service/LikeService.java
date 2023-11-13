package com.example.trip_itinerary.like.service;

import com.example.trip_itinerary.like.domain.Likes;
import com.example.trip_itinerary.like.repository.LikeRepository;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.repository.TripRepository;
import com.example.trip_itinerary.user.domain.User;
import com.example.trip_itinerary.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    public void likeTrip(Long tripId, Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Trip trip = tripRepository.findById(tripId).orElseThrow(RuntimeException::new);
        likeRepository.findByUserAndTrip(findUser, trip).ifPresentOrElse(
                like -> {
                    likeRepository.delete(like);
                    trip.deleteLike();
                },
                () -> {
                    Likes like = new Likes(findUser, trip);
                    likeRepository.save(like);
                    trip.addLike();
                }
        );
    }

}
