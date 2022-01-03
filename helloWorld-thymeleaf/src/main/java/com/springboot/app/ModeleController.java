package com.springboot.app;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ModeleController implements CommandLineRunner{
	@Autowired
    private JdbcTemplate jdbcTemplate;
	private List<Region> regions;
	private List<Type> types;
	private List<ChartRegion> chartRegions;
	private List<ChartType> chartTypes;
	private List<LoginFront> loginFronts;
	private Region reg;
	private LoginFront log;
	private FormCrud formC;
	
	public List<ChartType> getChartTypes() {
		return chartTypes;
	}

	public void setChartTypes(List<ChartType> chartTypes) {
		this.chartTypes = chartTypes;
	}

	public List<LoginFront> getLoginFronts() {
		return loginFronts;
	}

	public void setLoginFronts(List<LoginFront> loginFronts) {
		this.loginFronts = loginFronts;
	}

	public FormCrud getFormC() {
		return formC;
	}

	public void setFormC(FormCrud formC) {
		this.formC = formC;
	}

	public LoginFront getLog() {
		return log;
	}

	public void setLog(LoginFront log) {
		this.log = log;
	}

	public Region getReg() {
		return reg;
	}

	public void setReg(Region reg) {
		this.reg = reg;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public List<ChartRegion> getChartRegions() {
		return chartRegions;
	}

	public void setChartRegions(List<ChartRegion> chartRegions) {
		this.chartRegions = chartRegions;
	}

	public List<Type> getTypes() {
		return types;
	}

	public void setTypes(List<Type> types) {
		this.types = types;
	}

	@GetMapping("/indexBackOffice")
	public String index(Model model) {
		String req = "select idRegion, count(*) * 100.0 / (select count(*) from signalement) pource from signalement group by idRegion";
		setChartRegions(jdbcTemplate.query(req,new ChartRegionMapper()));
		Vector v = new Vector();
		for(int i=0; i<getChartRegions().size(); i++) {
			String sql = "SELECT * FROM Region where id = ?";
			ChartRegion cR = getChartRegions().get(i);
	    	Region r = jdbcTemplate.queryForObject(sql, new Object[]{cR.getIdRegion()}, new RegionMapper());
	    	v.add(r);
		}
		setRegions(v);
    	String[] nomRegion = new String[getRegions().size()];
    	double[] pourcentage = new double[getChartRegions().size()];
    	for(int i=0; i<nomRegion.length; i++) {
    		Region regi = getRegions().get(i);
    		nomRegion[i] = regi.getNom();
    	}
    	for(int i=0; i<pourcentage.length; i++) {
    		ChartRegion cR = getChartRegions().get(i);
    		pourcentage[i] = cR.getPource();
    	}
        model.addAttribute("label",nomRegion);
        model.addAttribute("point",pourcentage);
        
        String req1 = "select idType, count(*) * 100.0 / (select count(*) from signalement) pource from signalement group by idType";
        setChartTypes(jdbcTemplate.query(req1,new ChartTypeMapper()));
        Vector v1 = new Vector();
		for(int i=0; i<getChartTypes().size(); i++) {
			String sql = "SELECT * FROM Type where id = ?";
			ChartType cT = getChartTypes().get(i);
	    	Type t = jdbcTemplate.queryForObject(sql, new Object[]{cT.getIdType()}, new TypeMapper());
	    	v1.add(t);
		}
		setTypes(v1);
		String[] intitule = new String[getTypes().size()];
    	double[] pourcentage1 = new double[getChartTypes().size()];
    	for(int i=0; i<intitule.length; i++) {
    		Type typ = getTypes().get(i);
    		intitule[i] = typ.getIntitule();
    		System.out.println(intitule[i]);
    	}
    	for(int i=0; i<pourcentage1.length; i++) {
    		ChartType cT = getChartTypes().get(i);
    		pourcentage1[i] = cT.getPource();
    	}
        model.addAttribute("label1",intitule);
        model.addAttribute("point1",pourcentage1);
		model.addAttribute("message","Hello World");
		return "index";
	}
	
	@GetMapping("/region")
	public String region(Model model) {
		String sql = "SELECT * FROM Region";
    	setRegions(jdbcTemplate.query(sql,new RegionMapper())); 
		model.addAttribute("regions", getRegions());
		return "region";
	}
	
	@GetMapping("/utilisateur")
	public String utilisateur(Model model) {
		String sql = "SELECT * FROM LoginFront";
		setLoginFronts(jdbcTemplate.query(sql,new LoginFrontMapper()));
		model.addAttribute("loginFronts", getLoginFronts());
		return "utilisateur";
	}
	
	@GetMapping("/ajouterRegion")
	public String test(@ModelAttribute FormCrud formCrud) {
		return "ajouterRegion";
	}
	
	@PostMapping("/ajouterTraitement")
	public String ajouterTraitement(@ModelAttribute FormCrud formCrud) {
		setFormC(formCrud);
		String sql1 = "INSERT INTO region (nom) VALUES ("
                + "'"+getFormC().getNomRegion()+"')";
        int rows = jdbcTemplate.update(sql1);
        String sql = "SELECT * FROM Region WHERE nom = ?";
    	Region regionS = jdbcTemplate.queryForObject(sql, new Object[]{getFormC().getNomRegion()}, new RegionMapper());
    	String sql2 = "INSERT INTO loginFront (idRegion,nom,mdp) VALUES ("
                + regionS.getId()+",'"+getFormC().nomChefRegion+"','"+getFormC().getNomRegion()+"')";
        int rows1 = jdbcTemplate.update(sql2);
		return "ajouterRegion";
	}
	
	@GetMapping("/modifier/{id}")
	public String modifier(Model model,@PathVariable("id") String id,@ModelAttribute LoginFront loginF) {
		int idd = Integer.parseInt(id);
		String sql = "SELECT * FROM LoginFront WHERE id = ?";
    	LoginFront loginFrontS = jdbcTemplate.queryForObject(sql, new Object[]{idd}, new LoginFrontMapper());
    	model.addAttribute("lF", loginFrontS);
    	model.addAttribute("id",idd);
		return "modifier";
	}
	
	@PostMapping("/modifierTraitement")
	public String modifierTraitement(Model model,@ModelAttribute LoginFront loginF) {
		setLog(loginF);
		String sql1 = "UPDATE LoginFront SET nom = '"+getLog().getNom()+"' WHERE id = "+getLog().getId()+")";
        int rows = jdbcTemplate.update(sql1);
    	String sql = "SELECT * FROM LoginFront";
		setLoginFronts(jdbcTemplate.query(sql,new LoginFrontMapper()));
		model.addAttribute("loginFronts", getLoginFronts());
		return "utilisateur";
	}
	
	@Override
    public void run(String... args) throws Exception {
    	
    }
}
