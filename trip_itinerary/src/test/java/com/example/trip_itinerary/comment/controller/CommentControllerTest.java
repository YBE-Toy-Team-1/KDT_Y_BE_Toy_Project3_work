package com.example.trip_itinerary.comment.controller;

import com.example.trip_itinerary.WithMember;
import com.example.trip_itinerary.comment.dto.request.CommentSaveRequest;
import com.example.trip_itinerary.comment.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("댓글 생성 요청 및 응답 성공")
    @WithMember(username = "tester", password = "1234asd!@")
    void CreateCommentSuccess() throws Exception {
        CommentSaveRequest commentSaveRequest = new CommentSaveRequest("댓글!");

        mvc.perform(post("/trips/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsBytes(commentSaveRequest))
                        .with(csrf())
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/trips/1"))
                .andDo(print());
    }
}