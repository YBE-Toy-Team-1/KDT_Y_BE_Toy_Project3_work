package com.example.trip_itinerary.comment.repository;

import com.example.trip_itinerary.comment.domain.Comment;
import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.trip.domain.Trip;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource("classpath:application-test.properties")
@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 저장 성공")
    void createCommentSuccess() {
        Member member = Member.of("김패캠", "gmnkmc@naver.com", "123@#!fdf");

        Trip trip = Trip.of("서울 여행", LocalDate.of(2023, 12, 10),
                LocalDate.of(2023, 12, 11), true, member);

        Comment comment = Comment.of(member, trip, "댓글 테스트");

        Comment savedComment = commentRepository.save(comment);

        Assertions.assertThat(savedComment).isEqualTo(comment);
    }

}