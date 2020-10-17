package com.VB2.BYTEZ_Backend.Repositories;

import com.VB2.BYTEZ_Backend.Domain.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
}
