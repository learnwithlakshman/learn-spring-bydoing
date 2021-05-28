package com.careerit.ipl.domain;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDetails {
	
	@Id
	private String id;
	private String name;
	private int count;
	private double amount;
}
