package com.careerit.ipl.service;

import java.util.List;

import com.careerit.ipl.domain.Player;
import com.careerit.ipl.domain.TeamDetails;
import com.careerit.ipl.dto.RoleDetailsDTO;
import com.careerit.ipl.dto.TeamDTO;

public interface IplStatService {

	public List<TeamDetails> getTeamStat();

	public List<Player> getPlayers();

	public List<Player> getPlayersByTeam(String team);

	public List<Player> getPlayersByTeamAndRole(String team, String name);

	public List<RoleDetailsDTO> getTeamRoleDetails(String team);
	public TeamDTO getTeamNames();
}
