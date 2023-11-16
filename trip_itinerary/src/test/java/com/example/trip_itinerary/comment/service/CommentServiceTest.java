package com.example.trip_itinerary.comment.service;

import com.example.trip_itinerary.comment.domain.Comment;
import com.example.trip_itinerary.comment.dto.request.CommentSaveRequest;
import com.example.trip_itinerary.comment.repository.CommentRepository;
import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.member.domain.MemberAdapter;
import com.example.trip_itinerary.trip.domain.Trip;
import com.example.trip_itinerary.trip.repository.TripRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    @DisplayName("댓글 생성 성공")
    void createCommentSuccess() {
        Member member = Member.of("김패캠", "gmnkmc@naver.com", "123@#!fdf");

        Trip trip = Trip.of("서울 여행", LocalDate.of(2023, 12, 10),
                LocalDate.of(2023, 12, 11), true, member);

        Comment comment = Comment.of(member, trip, "댓글 테스트");

        CommentSaveRequest commentSaveRequest = new CommentSaveRequest("댓글 테스트");

        given(tripRepository.findById(any())).willReturn(Optional.of(trip));

        commentService.createComment(1L, commentSaveRequest, new MemberAdapter(member));

        Assertions.assertThat(comment.getContent()).isEqualTo(commentSaveRequest.getContent());
    }
}