package com.careerit.scart.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.careerit.scart.domain.CartDetails;
import com.careerit.scart.domain.Product;
import com.careerit.scart.payment.PaymentStatusDetails;
import com.careerit.scart.payment.PaytmDetails;
import com.careerit.scart.service.ProductService;
import com.paytm.pg.merchant.PaytmChecksum;


@Controller
public class ProductWebController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private PaytmDetails paytmDetails;
	
	@Value("${paytm.email}")
	private String email;
	@Value("${paytm.mobile}")
	private String mobile;


	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String index(Model model) {
		List<Product> productList = productService.getAllProducts();
		model.addAttribute("productList", productList);
//		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
//			
//			User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			model.addAttribute("username", user.getUsername());
//		}
		return "index";
	}
	
	

	@GetMapping("/addtocart")
	public String addToCart(@RequestParam("pid") long pid, HttpSession httpSession) {
		Product product = productService.getProductById(pid);

		if (httpSession.getAttribute("cartItems") == null) {
			List<Product> products = new ArrayList<>();
			httpSession.setAttribute("cartItems", products);
		}

		Object object = httpSession.getAttribute("cartItems");
		if (object instanceof List) {
			@SuppressWarnings("unchecked")
			List<Product> productList = (List<Product>) object;
			productList.add(product);
			httpSession.setAttribute("cartItems", productList);

		}
		return "redirect:home";

	}

	@SuppressWarnings("unchecked")
	@GetMapping("/viewcartitems")
	public ModelAndView viewCartItems(HttpSession httpSession) {

		List<Product> productList = null;
		Object object = httpSession.getAttribute("cartItems");
		if (object instanceof List) {
			productList = (List<Product>) object;

		}
	
		double totalPrice = 0;
		if (productList != null) {
			totalPrice = productList.stream().mapToDouble(e -> e.getPrice()).sum();
		}
		CartDetails cartDetails = new CartDetails();
		cartDetails.setProducts(productList);
		cartDetails.setTotalPrice(totalPrice);
		ModelAndView obj = new ModelAndView("viewcartitems");
		httpSession.setAttribute("cartdetails", cartDetails);
		return obj;
	}
	@GetMapping("/checkoutitems")
	public String checkoutItems() {
		return "checkoutitems";
	}
	
	@RequestMapping("/paymentreponse")
	public String getResponseRedirect(HttpServletRequest request, Model model,HttpSession httpSession) {
		Map<String, String[]> mapData = request.getParameterMap();
		mapData.entrySet().forEach(e->{
				System.out.println(e.getKey());
				System.out.println(Arrays.toString(e.getValue()));
		});
		PaymentStatusDetails paymentStatus = new PaymentStatusDetails();
		paymentStatus.setAmount(mapData.get("TXNAMOUNT")[0]);
		paymentStatus.setStatus(mapData.get("STATUS")[0]);
		paymentStatus.setOrderNumber(mapData.get("ORDERID")[0]);
		model.addAttribute("paymentstatus", paymentStatus);
		httpSession.invalidate();
		return "paymentreponse";
	}
	
	@PostMapping(value = "/pgredirect")
	public ModelAndView getRedirect(@RequestParam(name = "CUST_ID") String customerId,
			@RequestParam(name = "TXN_AMOUNT") String transactionAmount) throws Exception {

		ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetails.getPaytmUrl());
		TreeMap<String, String> parameters = new TreeMap<>();
		paytmDetails.getDetails().forEach((k, v) -> parameters.put(k, v));
		parameters.put("MOBILE_NO", mobile);
		parameters.put("EMAIL", email);
		parameters.put("ORDER_ID", Integer.toString(ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE)));
		parameters.put("TXN_AMOUNT", transactionAmount);
		parameters.put("CUST_ID", customerId);
		String checkSum = getCheckSum(parameters);
		parameters.put("CHECKSUMHASH", checkSum);
		modelAndView.addAllObjects(parameters);
		return modelAndView;
	}

	private String getCheckSum(TreeMap<String, String> parameters) throws Exception {
		return PaytmChecksum.generateSignature(parameters, paytmDetails.getMerchantKey());
	}
	
}
