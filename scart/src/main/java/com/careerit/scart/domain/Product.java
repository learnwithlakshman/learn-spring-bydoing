package com.careerit.scart.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

		private long pid;
		private String name;
		private String description;
		private double price;
}
