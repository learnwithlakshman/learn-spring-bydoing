package com.careerit.ipl.batch;

import org.springframework.batch.item.ItemProcessor;

import com.careerit.ipl.domain.Player;

public class PlayerItemProcessor implements ItemProcessor<PlayerItem, Player> {

	@Override
	public Player process(PlayerItem item) throws Exception {
		Player player = new Player();
		player.setName(item.getName());
		player.setRole(item.getRole());
		player.setTeam(item.getTeam());
		player.setCountry(item.getCountry());
		player.setPrice(Double.parseDouble(item.getPrice()));
		return player;
	}

}
