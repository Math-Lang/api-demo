package com.example.apidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example.apidemo.repository", "com.example.apidemo.Controller", "com.example.apidemo.service"})
public class ApiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDemoApplication.class, args);
	}

	/*@GetMapping("/greeting")
	public List<Listing> hello() {
		return List.of(
				new Listing(
						"288be150c45142c88f6461be45237203",
						"Lavaltrie",
						"G7A4X2",
						177,
						1,
						1,
						"Aeriell Pippin",
						5,
						"1 bed/1 bath renovated condo in a vibrant community of Lavaltrie with a balcony and hardwood floor."
				)
		);
	}*/
}
