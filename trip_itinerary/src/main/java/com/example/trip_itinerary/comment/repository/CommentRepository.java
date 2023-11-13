package com.example.trip_itinerary.comment.repository;

import com.example.trip_itinerary.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
