package com.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebService implements CommandLineRunner {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	private FormLoginFront formLogFF;
	
	public FormLoginFront getFormLogFF() {
		return formLogFF;
	}

	public void setFormLogFF(FormLoginFront formLogFF) {
		this.formLogFF = formLogFF;
	}

	@PostMapping("/traitementLoginFront")
	public int traitementLoginFront(Model model,@RequestBody FormLoginFront formLoginFront) {
		setFormLogFF(formLoginFront);
		System.out.println(getFormLogFF().getNom());
		int rep = 0;
		String sql = "SELECT Count(*) FROM LoginFront Where nom = '"+getFormLogFF().getNom()+"' and mdp = '"+getFormLogFF().getMdp()+"'";
		System.out.println(sql);
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		if(count == 0) {
			rep = 0;
		} else {
			String sql1 = "SELECT * FROM LoginFront Where nom = ? and mdp = ?";
			LoginFront logF = jdbcTemplate.queryForObject(sql1, new Object[]{getFormLogFF().getNom(),getFormLogFF().getMdp()}, new LoginFrontMapper());
			model.addAttribute("loginFront", logF);
			rep = 1;
		}
		return rep;
	}
	
	@Override
    public void run(String... args) throws Exception {
    	
    }
}
