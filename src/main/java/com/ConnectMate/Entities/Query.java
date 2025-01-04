package com.ConnectMate.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Query {
    @Id
    private String id;
    private String name;
    private String title;
    @Column(length = 1000)
    private String content;
    private String image;
    private boolean isResolved;
    private Date date;

    @ManyToOne
    @JsonIgnore
    private User user;
}
