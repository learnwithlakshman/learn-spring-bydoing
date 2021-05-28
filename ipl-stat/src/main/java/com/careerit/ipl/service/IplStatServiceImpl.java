package com.careerit.ipl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.careerit.ipl.dao.IplStatDao;
import com.careerit.ipl.domain.Player;
import com.careerit.ipl.domain.TeamDetails;
import com.careerit.ipl.dto.RoleDetailsDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IplStatServiceImpl implements IplStatService {

	@Autowired
	private IplStatDao iplStatDao;

	@Override
	public List<TeamDetails> getTeamStat() {
		return iplStatDao.findTeamStat();
	}

	@Override
	public List<Player> getPlayers() {
		return iplStatDao.findPlayers();
	}

	@Override
	public List<Player> getPlayersByTeam(String team) {
		// Validation
		return iplStatDao.findPlayersByTeam(team);
	}

	@Override
	public List<Player> getPlayersByTeamAndRole(String team, String role) {
		// Validations
		return iplStatDao.findPlayersByTeamAndRole(team, role);
	}

	@Override
	public List<RoleDetailsDTO> getTeamRoleDetails(String team) {
		// Validations
		return iplStatDao.findTeamRoleDetails(team);
	}

}
