package com.bitacademy.mysite.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitacademy.mysite.service.UserService;

@Controller("UserApiController")
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping("/existemail")
	public Map existEmail(@RequestParam(value="email", required=false, defaultValue="") String email) {
		
		boolean result = userService.existsEmail(email);
		
		Map<String, Object> map = new HashMap<>();
		map.put("result", "success");
		map.put("data", result);				// exist: true, not exist: false
		map.put("message", "권용");
		
		return map;
	}
}