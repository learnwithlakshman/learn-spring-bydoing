package com.careerit.lsd.properties;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerService {

		private List<Player> players;
		
		@Autowired
		private PlayerConfiguration playerConfiguration;
		
		public List<Player> getPlayers(){
			return players;
		}
		public List<Player> getPlayers(String team){
			return this.players.stream().filter(e->e.getTeam().equalsIgnoreCase(team)).collect(Collectors.toList());
		}
		@PostConstruct
		public void init() {
			this.players = playerConfiguration.getPlayers();
		}
}
