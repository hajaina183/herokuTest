package com.springboot.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class WebService implements CommandLineRunner {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	private FormLoginFront formLogFF;
	private FormSignalement formS;
	private List<Signalement> signalements;
	private FormLoginPerson formLoginPerson;
	private SignalementChangerStatus signalementChangerStatus;
	private List<StatusSignalement> statusSignalements;
	
	public FormLoginPerson getFormLoginPerson() {
		return formLoginPerson;
	}

	public void setFormLoginPerson(FormLoginPerson formLoginPerson) {
		this.formLoginPerson = formLoginPerson;
	}

	public SignalementChangerStatus getSignalementChangerStatus() {
		return signalementChangerStatus;
	}

	public void setSignalementChangerStatus(SignalementChangerStatus signalementChangerStatus) {
		this.signalementChangerStatus = signalementChangerStatus;
	}

	public List<StatusSignalement> getStatusSignalements() {
		return statusSignalements;
	}

	public void setStatusSignalements(List<StatusSignalement> statusSignalements) {
		this.statusSignalements = statusSignalements;
	}

	public FormSignalement getFormS() {
		return formS;
	}

	public void setFormS(FormSignalement formS) {
		this.formS = formS;
	}

	public List<Signalement> getSignalements() {
		return signalements;
	}

	public void setSignalements(List<Signalement> signalements) {
		this.signalements = signalements;
	}

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
			rep = logF.getIdRegion();
		}
		return rep;
	}
	
	@GetMapping("/listeSignalement/{id}")
	public Signalement[] getListe(Model model,@PathVariable String id) {
		int idd = Integer.parseInt(id);
		String sql = "SELECT * FROM Signalement WHERE idRegion = "+idd;
    	setSignalements(jdbcTemplate.query(sql,new SignalementMapper()));
    	model.addAttribute("id",idd);
    	Signalement[] listeSignalement = new Signalement[getSignalements().size()];
    	for(int i=0; i<getSignalements().size(); i++) {
    		listeSignalement[i] = getSignalements().get(i);
    	}
		return listeSignalement;
	}
	
	@PostMapping("/insertSignalement")
	public int insertSignalement(Model model,@RequestBody FormSignalement formSignalement) {
		setFormS(formSignalement);
		int rep = 0;
		String sql1 = "INSERT INTO Signalement (idType,titre,image,longitude,latitude,description) VALUES ("
                +getFormS().getIdType()+",'"+getFormS().getTitre()+"','"+getFormS().getImage()+"',"+getFormS().getLongitude()+","+getFormS().getLatitude()+",'"+getFormS().getDescription()+"')";
        int rows = jdbcTemplate.update(sql1);
		return rep;
	}
	
	@GetMapping("/changerStatus")//maka an l id
	public String affectation(Model model,@PathVariable("id") String id,@RequestBody StatusSignalement statusSignalement) {
		int idd = Integer.parseInt(id);
		String retour = "vita";
		model.addAttribute("id",idd);
		String sql1 = "select * from statusSignalements";
		setStatusSignalements(jdbcTemplate.query(sql1,new StatusSignalementMapper()));
		System.out.println(getStatusSignalements());
		model.addAttribute("regions", getStatusSignalements());
		return retour;
	}

	@PostMapping("/changerStatusTraitement")// manao an l update
	public String affectationTraitement(Model model,@RequestBody SignalementChangerStatus signalementChangerStatus) {
		setSignalementChangerStatus(signalementChangerStatus);
		String sql1 = "UPDATE Signalement SET idStatusSignalement = '"+getSignalementChangerStatus().getIdStatusSignalement()+"' WHERE id = "+getSignalementChangerStatus().getId();
		int rows = jdbcTemplate.update(sql1);
		String ret = "vita";
		System.out.println(sql1);
		return ret;
	}

	@PostMapping("/traitementPerson")
	public int traitementPerson(Model model,@RequestBody FormLoginPerson formLoginPerson) {
		setFormLoginPerson(formLoginPerson);
		System.out.println(getFormLoginPerson().getEmail());
		int rep = 0;
		String sql = "SELECT Count(*) FROM LoginPerson Where email = '"+getFormLoginPerson().getEmail()+"' and mdp = '"+getFormLoginPerson().getMdp()+"'";
		System.out.println(sql);
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		if(count == 0) {
			rep = 0;
		} else {
			String sql1 = "SELECT * FROM LoginPerson Where email = ? and mdp = ?";
			LoginPerson logF = jdbcTemplate.queryForObject(sql1, new Object[]{getFormLoginPerson().getEmail(),getFormLoginPerson().getMdp()}, new LoginPersonMapper());
			//model.addAttribute("loginFront", logF);
			rep = 1;
		}
		return rep;
	}
	
	@GetMapping("/detailSignalement/{id}")
	public ResponseEntity<DetailSignalement> getDetailSignalementById(@PathVariable("id") long id){
		String sql = "SELECT * FROM DetailSignalement WHERE id = ? ";
		DetailSignalement detailSignalement = jdbcTemplate.queryForObject(sql,new Object[]{id},new DetailSignalementMapper());
		return new ResponseEntity<DetailSignalement>(detailSignalement,HttpStatus.OK);
	} 
	
	@Override
    public void run(String... args) throws Exception {
    	
    }
}
