package com.example.trip_itinerary.comment.controller;

import com.example.trip_itinerary.comment.dto.request.CreateCommentRequest;
import com.example.trip_itinerary.comment.dto.request.UpdateCommentRequest;
import com.example.trip_itinerary.comment.service.CommentService;
import com.example.trip_itinerary.user.dto.data.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<HttpStatus> createComment(@RequestBody CreateCommentRequest request, UserId userId) {
        commentService.createComment(request, userId.getId());

        return ResponseEntity.created(URI.create("/comments/" + request.getTripId())).build();
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<HttpStatus> updateComment(@PathVariable(name = "comment_id") Long commentId,
                                                    @RequestBody UpdateCommentRequest request, UserId userId) {
        commentService.updateComment(commentId, request, userId.getId());

        return ResponseEntity.created(URI.create("/comments/" + commentId)).build();
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable(name = "comment_id") Long commentId, UserId userId) {
        commentService.deleteComment(commentId, userId.getId());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
