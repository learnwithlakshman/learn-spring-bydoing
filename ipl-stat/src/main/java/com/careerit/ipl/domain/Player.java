package com.careerit.ipl.domain;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {

	@Id
	private String id;
	private String name;
	private String role;
	private String country;
	private String team;
	private double price;
}
