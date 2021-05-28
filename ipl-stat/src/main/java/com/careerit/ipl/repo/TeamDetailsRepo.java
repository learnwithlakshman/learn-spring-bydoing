package com.careerit.ipl.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.careerit.ipl.domain.TeamDetails;

public interface TeamDetailsRepo extends MongoRepository<TeamDetails, String> {

}
