package com.careerit.ipl.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.careerit.ipl.domain.Player;

public interface PlayerRepo extends MongoRepository<Player, String> {

	List<Player> findByTeam(String team);

	List<Player> findByTeamAndRole(String team, String role);

}
