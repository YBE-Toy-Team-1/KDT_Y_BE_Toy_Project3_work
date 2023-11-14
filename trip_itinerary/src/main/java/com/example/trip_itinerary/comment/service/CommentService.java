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
    private final MemberRepository memberRepository;
    private final TripRepository tripRepository;

    public void createComment(Long tripId, CommentSaveRequest request, Member member) { // , Long userId
//        Member findMember = memberRepository.findById(userId).orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
        Trip findTrip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(TripErrorCode.TRIP_NOT_FOUND));

        commentRepository.save(Comment.of(member, findTrip, request.getContent()));
    }

    public void updateComment(Long commentId, CommentUpdateRequest request) {
//        User findUser = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(CommentErrorCode.COMMENT_NOT_FOUND));
//        isCorrectUser(findUser, comment);
        comment.update(request);
    }

    public void deleteComment(Long commentId) {
//        User findUser = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Comment foundComment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(CommentErrorCode.COMMENT_NOT_FOUND));
//        isCorrectUser(findUser, comment);

        commentRepository.delete(foundComment);
    }


//    private void isCorrectUser(User findUser, Comment comment) {
//        if (!comment.getUser().getUserId().equals(findUser.getUserId())) {
//            throw new RuntimeException();
//        }
//    }
}
