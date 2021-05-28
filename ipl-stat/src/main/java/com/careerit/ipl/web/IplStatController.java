package com.careerit.ipl.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.careerit.ipl.domain.Player;
import com.careerit.ipl.domain.TeamDetails;
import com.careerit.ipl.dto.RoleDetailsDTO;
import com.careerit.ipl.service.IplStatService;

@RestController
@RequestMapping("/stat")
public class IplStatController {
	@Autowired
	private IplStatService iplStatService;

	@GetMapping("/teams")
	public List<TeamDetails> getTeamStats() {
		return iplStatService.getTeamStat();
	}

	@GetMapping("/players")
	public List<Player> getPlayers() {
		return iplStatService.getPlayers();
	}

	@GetMapping("/players/{team}")
	public List<Player> getPlayers(@PathVariable("team") String team) {
		return iplStatService.getPlayersByTeam(team);
	}

	@GetMapping("/players/{team}/{role}")
	public List<Player> getPlayersByTeamAndRole(@PathVariable("team") String team, @PathVariable("role") String role) {
		return iplStatService.getPlayersByTeamAndRole(team, role);
	}

	@GetMapping("/teams/{team}")
	public List<RoleDetailsDTO> getTeamRoleDetails(@PathVariable("team") String team) {
		return iplStatService.getTeamRoleDetails(team);
	}
}
