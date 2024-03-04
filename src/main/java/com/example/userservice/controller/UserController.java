package com.example.userservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.model.User;

@RestController
public class UserController {
	
	List<User> ul = new ArrayList<>();
	public UserController() {
		ul.add(new User(1,"Bhanu","Hyd"));
		ul.add(new User(2,"Vijay","knl"));
		ul.add(new User(3,"Giri","nel"));
		ul.add(new User(4,"Raghu","hay"));
		ul.add(new User(5,"Bhishma","dallas"));
		ul.add(new User(6,"Sai","irving"));
		ul.add(new User(7,"prawin","Oak"));
		ul.add(new User(8,"Sai","Fre"));
	}
	
	@RequestMapping("/greet")
	public String greet() {
		return "Hello there!!";
	}
	
	@RequestMapping("/users")
	public List<User> getUsers() {
		return ul;
	}
	
	
	//form.html
	@RequestMapping("/user")
	public User getUserById(@RequestParam int uid) {
		return ul.stream().filter(u->u.getUid()==uid)
		.findFirst().orElseThrow(()->new RuntimeException("User not found: " +uid));
	}
	
	@RequestMapping("/username/{uname}")
	public User getUserByName(@PathVariable String uname) {
		return ul.stream().filter(u->u.getUname().equalsIgnoreCase(uname))
		.findFirst().orElseThrow(()->new RuntimeException("User not found:" +uname));	
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public User inserUser(@RequestBody User usr) {
		ul.add(usr);
		return ul.stream().filter(u->u.getUid()==usr.getUid()).
				findFirst().orElseThrow(()->new RuntimeException("Unable to save user"));
	}
	
	@RequestMapping(value = "/update/{uid}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable int uid,@RequestBody User usr) {
		User existing = ul.stream().filter(u->u.getUid()==uid).
				findFirst().orElseThrow(()->new RuntimeException("User not found:" +uid));
		
		if(usr.getUname()!=null) {
			existing.setUname(usr.getUname());
		}
		if(usr.getAddr()!=null) {
			existing.setAddr(usr.getAddr());
		}
		return existing;
	}
	
	@RequestMapping(value = "/delete/{uid}", method = RequestMethod.DELETE)
	public String deleteUSer(@PathVariable int uid) {
		User existing = ul.stream().filter(u->u.getUid()==uid).
				findFirst().orElseThrow(()->new RuntimeException("User not found:" +uid));
		ul.remove(existing);
		return "User Deleted with id::" +uid;
	}
}
