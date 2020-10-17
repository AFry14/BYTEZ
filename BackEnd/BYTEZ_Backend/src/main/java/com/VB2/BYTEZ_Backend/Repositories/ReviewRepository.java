package com.VB2.BYTEZ_Backend.Repositories;

import com.VB2.BYTEZ_Backend.Domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
}
