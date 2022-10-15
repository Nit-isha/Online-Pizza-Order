package com.cg.onlinepizza.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class MyController {

	public static String test() {
		return "Working"; 
	}
}
