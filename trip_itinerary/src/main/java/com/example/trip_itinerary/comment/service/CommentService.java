package com.example.trip_itinerary.comment.service;

import com.example.trip_itinerary.comment.domain.Comment;
import com.example.trip_itinerary.comment.dto.request.CommentSaveRequest;
import com.example.trip_itinerary.comment.dto.request.CommentUpdateRequest;
import com.example.trip_itinerary.comment.exception.CommentErrorCode;
import com.example.trip_itinerary.comment.exception.CommentNotFoundException;
import com.example.trip_itinerary.comment.repository.CommentRepository;
import com.example.trip_itinerary.member.domain.MemberAdapter;
import com.example.trip_itinerary.member.exception.MemberErrorCode;
import com.example.trip_itinerary.member.exception.MemberNotMatchedException;
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

    public void createComment(Long tripId, CommentSaveRequest request, MemberAdapter memberAdapter) {
        Trip findTrip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));
        commentRepository.save(Comment.of(memberAdapter.getMember(), findTrip, request.getContent()));
    }

    public void updateComment(Long commentId, CommentUpdateRequest request, MemberAdapter memberAdapter) {
        Comment foundComment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(CommentErrorCode.COMMENT_NOT_FOUND));

        if (foundComment.getMember().getUsername().equals(memberAdapter.getUsername())) {
            foundComment.update(request);
            return;
        }

        throw new MemberNotMatchedException(MemberErrorCode.MEMBER_NOT_MATCHED);
    }

    public void deleteComment(Long commentId, MemberAdapter memberAdapter) {
        Comment foundComment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(CommentErrorCode.COMMENT_NOT_FOUND));

        if (foundComment.getMember().getUsername().equals(memberAdapter.getUsername())) {
            commentRepository.delete(foundComment);
            return;
        }

        throw new MemberNotMatchedException(MemberErrorCode.MEMBER_NOT_MATCHED);
    }

}
