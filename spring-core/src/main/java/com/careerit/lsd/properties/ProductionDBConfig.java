package com.careerit.lsd.properties;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class ProductionDBConfig {
	
		public ProductionDBConfig() {
			System.out.println("Product DB Configuration has been initialized");
		}
}
@Component
@Profile("qa")
class QaDBConfig {
	public QaDBConfig() {
		System.out.println("QA DB Configuration has been initialized");
	}
}
