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
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{trip_id}/comment")
    public ResponseEntity<Void> createComment(@RequestBody CreateCommentRequest request, UserId userId) {
        commentService.createComment(request, userId.getId());

        return ResponseEntity.created(URI.create("/comment/" + request.getTripId() + "/comment")).build();
    }

    @PatchMapping("/{comment_id}")
    public ResponseEntity<Void> updateComment(@PathVariable Long comment_id,
                                                    @RequestBody UpdateCommentRequest request, UserId userId) {
        commentService.updateComment(comment_id, request, userId.getId());

        return ResponseEntity.created(URI.create("/comment/" + comment_id)).build();
    }

}
