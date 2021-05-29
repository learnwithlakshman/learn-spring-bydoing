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
import com.careerit.ipl.dto.TeamDTO;
import com.careerit.ipl.service.IplStatService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/stat")
public class IplStatController {
	@Autowired
	private IplStatService iplStatService;

	@GetMapping("/teams")
	@ApiResponse(description = "Returns team details with summary")
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
	
	@GetMapping("/teams/labels")
	public TeamDTO getTeamNames() {
		return iplStatService.getTeamNames();
	}
}
