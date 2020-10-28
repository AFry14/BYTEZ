package com.VB2.BYTEZ_Backend.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;

@Entity
public class Report
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonIgnore
    private User author;

    private String report;

    // Getter and Setters

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public User getAuthor()
    {
        return this.author;
    }

    public void setAuthor(User author)
    {
        this.author = author;
    }

    public String getReport()
    {
        return this.report;
    }

    public void setReport(String report)
    {
        this.report = report;
    }
}
