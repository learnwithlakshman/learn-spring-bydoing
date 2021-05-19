package com.careerit.lsd.scope;

import javax.inject.Named;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;





@Named
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Task {

		public Task() {
			System.out.println("Task");
		}
		
		
		
}
