package com.careerit.ipl.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.careerit.ipl.domain.Player;
import com.careerit.ipl.domain.TeamDetails;
import com.careerit.ipl.dto.RoleDetailsDTO;
import com.careerit.ipl.repo.PlayerRepo;
import com.careerit.ipl.repo.TeamDetailsRepo;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class IplStatDaoImpl implements IplStatDao {

	@Autowired
	private MongoOperations mongoOpes;

	@Autowired
	private TeamDetailsRepo teamDetailsRepo;

	@Autowired
	private PlayerRepo playerRepo;

	@Override
	public List<TeamDetails> findTeamStat() {
		List<TeamDetails> teamDetails = teamDetailsRepo.findAll();
		log.info("Total {} team are found", teamDetails.size());
		return teamDetails;
	}

	@Override
	public List<Player> findPlayers() {
		List<Player> players = playerRepo.findAll();
		log.info("Total {} players found ", players.size());
		return players;
	}

	@Override
	public List<Player> findPlayersByTeam(String team) {
		List<Player> players = playerRepo.findByTeam(team);
		log.info("Total {} players found for given team:{}", players.size(), team);
		return players;
	}

	@Override
	public List<Player> findPlayersByTeamAndRole(String team, String role) {
		List<Player> players = playerRepo.findByTeamAndRole(team, role);
		log.info("Total {} players found for given team:{} with role :{}", players.size(), team, role);
		return players;

	}

	@Override
	public List<RoleDetailsDTO> findTeamRoleDetails(String team) {
		MatchOperation match = match(Criteria.where("team").is(team));
		GroupOperation group = group("role").count().as("count").sum("price").as("total");
		ProjectionOperation project = project().and("total").as("amount").and("count").as("count").and("_id")
				.as("rolename").andExclude("_id");
		Aggregation agger = newAggregation(match, group, project);
		AggregationResults<RoleDetailsDTO> aggreateResult = mongoOpes.aggregate(agger, "player", RoleDetailsDTO.class);
		List<RoleDetailsDTO> list = aggreateResult.getMappedResults();
		log.info("{} has {} role", team, list.size());
		return list;
	}

}
