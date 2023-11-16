package com.example.trip_itinerary.comment.controller;

import com.example.trip_itinerary.comment.dto.request.CommentSaveRequest;
import com.example.trip_itinerary.comment.dto.request.CommentUpdateRequest;
import com.example.trip_itinerary.comment.service.CommentService;
import com.example.trip_itinerary.member.domain.Member;
import com.example.trip_itinerary.member.domain.MemberAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "댓글 생성", description = "댓글이 생성됩니다.")
    @PostMapping
    public ResponseEntity<HttpStatus> saveComment(
            @Parameter(description = "여행 ID", required = true, example = "1")
            @PathVariable(name = "trip_id") Long tripId,
            @RequestBody CommentSaveRequest request,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        commentService.createComment(tripId, request, memberAdapter);

        return ResponseEntity.created(URI.create("/trips/" + tripId)).build();
    }

    @Operation(summary = "댓글 수정", description = "댓글이 수정됩니다.")
    @PutMapping("/{comment_id}")
    public ResponseEntity<HttpStatus> updateComment(
            @Parameter(description = "댓글 ID", required = true, example = "1")
            @PathVariable(name = "comment_id") Long commentId,
            @RequestBody CommentUpdateRequest request,
            @AuthenticationPrincipal MemberAdapter memberAdapter
    ) {
        commentService.updateComment(commentId, request, memberAdapter);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "댓글 삭제", description = "댓글이 삭제됩니다.")
    @DeleteMapping("/{comment_id}")
    public ResponseEntity<HttpStatus> deleteComment(
            @Parameter(description = "댓글 ID", required = true, example = "1")
            @PathVariable(name = "comment_id") Long commentId,
            @AuthenticationPrincipal MemberAdapter memberAdapter) {
        commentService.deleteComment(commentId, memberAdapter);

        return ResponseEntity.noContent().build();
    }

}
