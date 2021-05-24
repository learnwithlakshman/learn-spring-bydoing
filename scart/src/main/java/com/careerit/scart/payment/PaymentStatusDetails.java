package com.careerit.scart.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentStatusDetails {

		private String orderNumber;
		private String amount;
		private String status;
}
