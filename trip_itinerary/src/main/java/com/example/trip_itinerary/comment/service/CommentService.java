package com.example.trip_itinerary.comment.service;

import com.example.trip_itinerary.comment.domain.Comment;
import com.example.trip_itinerary.comment.dto.request.CreateCommentRequest;
import com.example.trip_itinerary.comment.dto.request.UpdateCommentRequest;
import com.example.trip_itinerary.comment.repository.CommentRepository;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.repository.TripRepository;
import com.example.trip_itinerary.user.domain.User;
import com.example.trip_itinerary.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    public void createComment(CreateCommentRequest request, Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Trip findTrip = tripRepository.findById(request.getTripId()).orElseThrow(RuntimeException::new);
        Comment comment = new Comment(findUser, findTrip, request.getContent());
        commentRepository.save(comment);
    }

    public void updateComment(Long commentId, UpdateCommentRequest request, Long userId) {
//        User findUser = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(RuntimeException::new);
//        isCorrectUser(findUser, comment);
        comment.update(request);
    }

    public void deleteComment(Long commentId, Long userId) {
//        User findUser = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(RuntimeException::new);
//        isCorrectUser(findUser, comment);
        commentRepository.delete(comment);
    }


//    private void isCorrectUser(User findUser, Comment comment) {
//        if (!comment.getUser().getUserId().equals(findUser.getUserId())) {
//            throw new RuntimeException();
//        }
//    }
}
