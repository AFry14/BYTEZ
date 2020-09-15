package com.VB2.BYTEZ_Backend.Repositories;

import com.VB2.BYTEZ_Backend.Domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
