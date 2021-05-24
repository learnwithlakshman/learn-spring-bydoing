package com.careerit.scart.payment;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("paytm")
@Getter
@Setter
public class PaytmDetails {

	private String merchantId;
	private String merchantKey;
	private String channelId;
	private String website;
	private String industryTypeId;
	private String paytmUrl;
	private Map<String, String> details;

	
}
