package com.example.trip_itinerary.comment.controller;

import com.example.trip_itinerary.comment.dto.request.CommentSaveRequest;
import com.example.trip_itinerary.comment.dto.request.CommentUpdateRequest;
import com.example.trip_itinerary.comment.service.CommentService;
import com.example.trip_itinerary.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/trips/{trip_id}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<HttpStatus> saveComment(@PathVariable(name = "trip_id") Long tripId, @RequestBody CommentSaveRequest request, @AuthenticationPrincipal Member member){
        Member tester = Member.of("tester", "abcde@1234", "test");
        commentService.createComment(tripId, request, tester);

        return ResponseEntity.created(URI.create("/trips/" + tripId)).build();
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<HttpStatus> updateComment(@PathVariable(name = "comment_id") Long commentId,
                                                    @RequestBody CommentUpdateRequest request) {
        commentService.updateComment(commentId, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable(name = "comment_id") Long commentId) {
        commentService.deleteComment(commentId);

        return ResponseEntity.noContent().build();
    }

}
