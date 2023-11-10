package com.example.trip_itinerary.comment.controller;

import com.example.trip_itinerary.comment.dto.request.CreateCommentRequest;
import com.example.trip_itinerary.comment.service.CommentService;
import com.example.trip_itinerary.user.dto.data.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<HttpStatus> createComment(@RequestBody CreateCommentRequest request, UserId userId) {
        commentService.createComment(request, userId.getId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
