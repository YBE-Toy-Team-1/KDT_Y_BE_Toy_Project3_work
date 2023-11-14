package com.example.trip_itinerary.trip.domain;

import com.example.trip_itinerary.comment.domain.Comment;
import com.example.trip_itinerary.itinerary.domain.Itinerary;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_domestic", nullable = false)
    private boolean isDomestic;

    @Column(name = "like_num", nullable = true)
    private Long likeNum;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.REMOVE)
    private List<Itinerary> itineraryList = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    protected Trip() {
    }

    private Trip(String name, LocalDate startDate, LocalDate endDate, boolean isDomestic) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isDomestic = isDomestic;
    }

    public static Trip of(String name, LocalDate startDate, LocalDate endDate, boolean isDomestic) {
        return new Trip(name, startDate, endDate, isDomestic);
    }

    public void updateTrip(String name, LocalDate startDate, LocalDate endDate, Boolean isDomestic) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isDomestic = isDomestic;
    }

    public void upLike(){
        this.likeNum++;
    }

    public void downLike(){
        this.likeNum--;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isDomestic() {
        return isDomestic;
    }

    public Long getLikeNum() {
        return likeNum;
    }

    public List<Itinerary> getItineraryList() {
        return itineraryList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }
}
