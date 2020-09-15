package com.VB2.BYTEZ_Backend.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

// Restaurant Entity
@Entity
public class Restaurant {

    private final Long id;
    private final String name;

    public Restaurant(@JsonProperty("id") Long id,
                  @JsonProperty("name") String name)
    {
        this.id = id;
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
