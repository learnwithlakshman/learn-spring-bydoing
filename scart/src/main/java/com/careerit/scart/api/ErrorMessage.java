package com.careerit.scart.api;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorMessage {

		private String message;
		private HttpStatus status;
		private String path;
		private LocalDateTime dateTime;
}
