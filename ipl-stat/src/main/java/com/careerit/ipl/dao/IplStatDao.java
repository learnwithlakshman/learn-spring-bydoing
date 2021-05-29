package com.careerit.ipl.dao;

import java.util.List;

import com.careerit.ipl.domain.Player;
import com.careerit.ipl.domain.TeamDetails;
import com.careerit.ipl.dto.RoleDetailsDTO;
import com.careerit.ipl.dto.TeamDTO;

public interface IplStatDao {
	public List<TeamDetails> findTeamStat();

	public List<Player> findPlayers();

	public List<Player> findPlayersByTeam(String team);

	public List<Player> findPlayersByTeamAndRole(String team, String name);

	public List<RoleDetailsDTO> findTeamRoleDetails(String team);
	
	public TeamDTO findTeamNames();
}
