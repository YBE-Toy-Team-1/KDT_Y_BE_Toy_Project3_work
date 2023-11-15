package com.example.trip_itinerary.comment.service;

import com.example.trip_itinerary.comment.domain.Comment;
import com.example.trip_itinerary.comment.dto.request.CommentSaveRequest;
import com.example.trip_itinerary.comment.dto.request.CommentUpdateRequest;
import com.example.trip_itinerary.comment.exception.CommentErrorCode;
import com.example.trip_itinerary.comment.exception.CommentNotFoundException;
import com.example.trip_itinerary.comment.repository.CommentRepository;
import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.member.repository.MemberRepository;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.exception.TripErrorCode;
import com.example.trip_itinerary.trip.exception.TripNotFoundException;
import com.example.trip_itinerary.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final TripRepository tripRepository;

    public void createComment(Long tripId, CommentSaveRequest request, Member member) {
        Trip findTrip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        commentRepository.save(Comment.of(member, findTrip, request.getContent()));
    }

    public void updateComment(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(CommentErrorCode.COMMENT_NOT_FOUND));
        comment.update(request);
    }

    public void deleteComment(Long commentId) {
        Comment foundComment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(CommentErrorCode.COMMENT_NOT_FOUND));
        commentRepository.delete(foundComment);
    }

}
