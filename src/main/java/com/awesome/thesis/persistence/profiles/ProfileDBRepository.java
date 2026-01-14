package com.awesome.thesis.persistence.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileDBRepository extends CrudRepository<Profile, Integer> {
}
