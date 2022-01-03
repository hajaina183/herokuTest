package com.springboot.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChartController {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	private List<Region> regions;
	
	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	@RequestMapping("/chart")
    public String hello(Model model) {
		String sql = "SELECT * FROM Region";
    	setRegions(jdbcTemplate.query(sql,new RegionMapper()));
    	String[] nomRegion = new String[getRegions().size()];
    	int[] id = new int[getRegions().size()];
    	for(int i=0; i<nomRegion.length; i++) {
    		Region regi = getRegions().get(i);
    		nomRegion[i] = regi.getNom();
    	}
    	for(int i=0; i<id.length; i++) {
    		Region regi = getRegions().get(i);
    		id[i] = regi.getId();
    	}
        model.addAttribute("label",nomRegion);
        model.addAttribute("point",id);
        return "chart";
    }
}
