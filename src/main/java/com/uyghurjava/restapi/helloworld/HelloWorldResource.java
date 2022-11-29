package com.uyghurjava.restapi.helloworld;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldResource {
	
	@RequestMapping("/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	@RequestMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World"); // Jackson convert bean to JSON
	}
	
	// Path Variable - Path Params
	// /user/uyghurjava/todos/1

	@RequestMapping("/hello-world-path-param/{name}/message/{message}")
	public HelloWorldBean helloWorldMultiplePathParams(
			@PathVariable String name,
			@PathVariable String message) {
		return new HelloWorldBean("Hello World," + name + "," + message); 
	}

}
