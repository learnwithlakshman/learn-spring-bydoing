package com.careerit.lsd.di;

import org.springframework.stereotype.Service;

@Service
public class BirthdayGreetingService implements GreetingService {

	@Override
	public String message() {
	
		return "Many many happy returns of the day";
	}

}
