package com.springboot.app.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.validator.EmailValidator;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.springboot.app.FormLoginFront;
import com.springboot.app.FormLoginPerson;
import com.springboot.app.FormSignalement;
import com.springboot.app.LoginFront;
import com.springboot.app.LoginFrontMapper;
import com.springboot.app.LoginPerson;
import com.springboot.app.LoginPersonInscription;
import com.springboot.app.LoginPersonMapper;
import com.springboot.app.Region;
import com.springboot.app.RegionMapper;
import com.springboot.app.ReponseLoginFront;
import com.springboot.app.ReponseLoginPerson;
import com.springboot.app.Signalement;
import com.springboot.app.SignalementChangerStatus;
import com.springboot.app.SignalementMapper;
import com.springboot.app.StatusSignalement;
import com.springboot.app.StatusSignalementMapper;
import com.springboot.app.Type;
import com.springboot.app.TypeMapper;

@RestController
@CrossOrigin
public class WebService implements CommandLineRunner {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	private FormLoginFront formLogFF;
	private FormSignalement formS;
	private List<Signalement> signalements;
	private List<Type> types;
	private FormLoginPerson formLoginPerson;
	private LoginPersonInscription loginPersonInscription;
	private SignalementChangerStatus signalementChangerStatus;
	private List<StatusSignalement> statusSignalements;
	private final Path uploadDirectory = null;
	
	public List<Type> getTypes() {
		return types;
	}

	public void setTypes(List<Type> types) {
		this.types = types;
	}

	public LoginPersonInscription getLoginPersonInscription() {
		return loginPersonInscription;
	}

	public void setLoginPersonInscription(LoginPersonInscription loginPersonInscription) {
		this.loginPersonInscription = loginPersonInscription;
	}

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
	public ReponseLoginFront traitementLoginFront(Model model,@RequestBody FormLoginFront formLoginFront) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		ReponseLoginFront rl = new ReponseLoginFront();
		setFormLogFF(formLoginFront);
		System.out.println(getFormLogFF().getNom());
		int rep = 0;
		String sql = "SELECT Count(*) FROM LoginFront Where nom = '"+getFormLogFF().getNom()+"' and mdp = '"+getFormLogFF().getMdp()+"'";
		System.out.println(sql);
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		if(count == 0) {
			rep = 0;
			rl.setReponse(rep);
		} else {
			String sql1 = "SELECT * FROM LoginFront Where nom = ? and mdp = ?";
			LoginFront logF = jdbcTemplate.queryForObject(sql1, new Object[]{getFormLogFF().getNom(),getFormLogFF().getMdp()}, new LoginFrontMapper());
			model.addAttribute("loginFront", logF);
			String token = creteToken(getFormLogFF().getNom());
			System.out.println(token);
			String sql2 = "INSERT INTO Token (id,token) VALUES ("
	                +logF.getId()+",'"+token+"')";
	        int rows = jdbcTemplate.update(sql2);
			rep = logF.getIdRegion();
			System.out.println("rep : "+rep);
			System.out.println("token : "+token);
			rl.setReponse(rep);
			rl.setToken(token);
		}
		return rl;
	}
	
	public int traitementToken(String token) {
		int rep = 0;
		String sql = "SELECT Count(*) FROM Token Where token = '"+token+"'";
		System.out.println(sql);
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		if(count == 0) {
			rep = 0;
		} else {
			rep = 1;
		}
		return rep;
	}
	
	@GetMapping("/listeSignalement/{id}/{token}")
	public Signalement[] getListe(Model model,@PathVariable String id,@PathVariable String token) {
		int istoken = traitementToken(token);
		if(istoken == 1) {
			int idd = Integer.parseInt(id);
			String sql = "SELECT * FROM Signalement WHERE idRegion = "+idd;
	    	setSignalements(jdbcTemplate.query(sql,new SignalementMapper()));
	    	System.out.println(sql);
	    	model.addAttribute("id",idd);
	    	Signalement[] listeSignalement = new Signalement[getSignalements().size()];
	    	try {
	    		for(int i=0; i<getSignalements().size(); i++) {
		    		listeSignalement[i] = getSignalements().get(i);
		    		String fileName = encodeImage(listeSignalement[i].getImage());
		    		listeSignalement[i].setImage(fileName);
		    		System.out.println(listeSignalement[i].getIdStatusSignalement());
		    	}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	    	
			return listeSignalement;
		} else {
			return null;
		}
		
	}
	
	@GetMapping("/listeSignalementRechercherA/{id}/{status}/{token}")
	public Signalement[] getListeRechercherA(Model model,@PathVariable String id,@PathVariable String status,@PathVariable String token) {
		int istoken = traitementToken(token);
		if(istoken == 1) {
			int idd = Integer.parseInt(id);
			String sql = "";
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			sql = "select s.id id,s.idType,s.idRegion,s.idpersonne, s.idStatussignalement,titre,image,longitude,latitude,description,date from signalement s join statutsignalement st on s.idStatussignalement = st.id where st.intitule = '"+status+"' and idRegion = "+idd;
	    	setSignalements(jdbcTemplate.query(sql,new SignalementMapper()));
	    	System.out.println(sql);
	    	model.addAttribute("id",idd);
	    	Signalement[] listeSignalement = new Signalement[getSignalements().size()];
	    	for(int i=0; i<getSignalements().size(); i++) {
	    		listeSignalement[i] = getSignalements().get(i);
	    		System.out.println(listeSignalement[i].getIdStatusSignalement());
	    	}
			return listeSignalement;
		} else {
			return null;
		}
	}
	
	@GetMapping("/listeSignalementRechercherB/{id}/{status}/{type}/{token}")
	public Signalement[] getListeRechercherB(Model model,@PathVariable String id,@PathVariable String status,@PathVariable String type,@PathVariable String token) {
		int istoken = traitementToken(token);
		if(istoken == 1) {
			int idd = Integer.parseInt(id);
			String sql = "";
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			sql = "select s.id id,s.idType,s.idRegion,s.idpersonne, s.idStatussignalement,titre,image,longitude,latitude,description,date from signalement s join statutsignalement st on s.idStatussignalement = st.id where st.intitule = '"+status+"' and t.intitule = '"+type+"' and idRegion = "+idd;
	    	setSignalements(jdbcTemplate.query(sql,new SignalementMapper()));
	    	System.out.println(sql);
	    	model.addAttribute("id",idd);
	    	Signalement[] listeSignalement = new Signalement[getSignalements().size()];
	    	for(int i=0; i<getSignalements().size(); i++) {
	    		listeSignalement[i] = getSignalements().get(i);
	    		System.out.println(listeSignalement[i].getIdStatusSignalement());
	    	}
			return listeSignalement;
		} else {
			return null;
		}
	}
	
	@GetMapping("/listeSignalementRechercherC/{id}/{status}/{type}/{dateMin}/{dateMax}/{token}")
	public Signalement[] getListeRechercherC(Model model,@PathVariable String id,@PathVariable String status,@PathVariable String type,@PathVariable String dateMin,@PathVariable String dateMax,@PathVariable String token) {
		int istoken = traitementToken(token);
		if(istoken == 1) {
			int idd = Integer.parseInt(id);
			String sql = "";
			sql = "select s.id id,s.idType,s.idRegion,s.idpersonne, s.idStatussignalement,titre,image,longitude,latitude,description,date from signalement s join statutsignalement st on s.idStatussignalement = st.id where st.intitule = '"+status+"' and t.intitule = '"+type+"' and s.date between '"+dateMin+"' and '"+dateMax+"' and idRegion = "+idd;
	    	setSignalements(jdbcTemplate.query(sql,new SignalementMapper()));
	    	System.out.println(sql);
	    	model.addAttribute("id",idd);
	    	Signalement[] listeSignalement = new Signalement[getSignalements().size()];
	    	for(int i=0; i<getSignalements().size(); i++) {
	    		listeSignalement[i] = getSignalements().get(i);
	    		System.out.println(listeSignalement[i].getIdStatusSignalement());
	    	}
			return listeSignalement;
		} else {
			return null;
		}
	}
	
	@GetMapping("/listeSignalementRechercherD/{id}/{type}/{token}")
	public Signalement[] getListeRechercherD(Model model,@PathVariable String id,@PathVariable String type,@PathVariable String token) {
		int istoken = traitementToken(token);
		if(istoken == 1) {
			System.out.println(type);
			int idd = Integer.parseInt(id);
			String sql = "";
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			sql = "select s.id id,s.idType,s.idRegion,s.idpersonne, s.idStatussignalement,titre,image,longitude,latitude,description,date from signalement s join statutsignalement st on s.idStatussignalement = st.id join type t on s.idType = t.id where t.intitule = '"+type+"' and idRegion = "+idd;
	    	setSignalements(jdbcTemplate.query(sql,new SignalementMapper()));
	    	System.out.println(sql);
	    	model.addAttribute("id",idd);
	    	Signalement[] listeSignalement = new Signalement[getSignalements().size()];
	    	for(int i=0; i<getSignalements().size(); i++) {
	    		listeSignalement[i] = getSignalements().get(i);
	    		System.out.println(listeSignalement[i].getIdStatusSignalement());
	    	}
			return listeSignalement;
		} else {
			return null;
		}
	}
	
	@GetMapping("/listeSignalementRechercherE/{id}/{type}/{dateMin}/{dateMax}/{token}")
	public Signalement[] getListeRechercherE(Model model,@PathVariable String id,@PathVariable String status,@PathVariable String type,@PathVariable String dateMin,@PathVariable String dateMax,@PathVariable String token) {
		int istoken = traitementToken(token);
		if(istoken == 1) {
			int idd = Integer.parseInt(id);
			String sql = "";
			sql = "select s.id id,s.idType,s.idRegion,s.idpersonne, s.idStatussignalement,titre,image,longitude,latitude,description,date from signalement s join statutsignalement st on s.idStatussignalement = st.id where t.intitule = '"+type+"' and s.date between '"+dateMin+"' and '"+dateMax+"' and idRegion = "+idd;
	    	setSignalements(jdbcTemplate.query(sql,new SignalementMapper()));
	    	System.out.println(sql);
	    	model.addAttribute("id",idd);
	    	Signalement[] listeSignalement = new Signalement[getSignalements().size()];
	    	for(int i=0; i<getSignalements().size(); i++) {
	    		listeSignalement[i] = getSignalements().get(i);
	    		System.out.println(listeSignalement[i].getIdStatusSignalement());
	    	}
			return listeSignalement;
		} else {
			return null;
		}
	}
	
	@GetMapping("/listeSignalementRechercherF/{id}/{dateMin}/{dateMax}/{token}")
	public Signalement[] getListeRechercherF(Model model,@PathVariable String id,@PathVariable String dateMin,@PathVariable String dateMax,@PathVariable String token) {
		int istoken = traitementToken(token);
		if(istoken == 1) {
			int idd = Integer.parseInt(id);
			String sql = "";
			System.out.println("date : "+dateMin);
			sql = "select s.id id,s.idType,s.idRegion,s.idpersonne, s.idStatussignalement,titre,image,longitude,latitude,description,date from signalement s join statutsignalement st on s.idStatussignalement = st.id where s.date between '"+dateMin+"' and '"+dateMax+"' and idRegion = "+idd;
	    	setSignalements(jdbcTemplate.query(sql,new SignalementMapper()));
	    	System.out.println(sql);
	    	model.addAttribute("id",idd);
	    	Signalement[] listeSignalement = new Signalement[getSignalements().size()];
	    	for(int i=0; i<getSignalements().size(); i++) {
	    		listeSignalement[i] = getSignalements().get(i);
	    		System.out.println(listeSignalement[i].getIdStatusSignalement());
	    	}
			return listeSignalement;
		} else {
			return null;
		}
	}
	
	@GetMapping("/listeSignalementRechercherG/{id}/{status}/{dateMin}/{dateMax}/{token}")
	public Signalement[] getListeRechercherG(Model model,@PathVariable String id,@PathVariable String status,@PathVariable String dateMin,@PathVariable String dateMax,@PathVariable String token) {
		int istoken = traitementToken(token);
		if(istoken == 1) {
			int idd = Integer.parseInt(id);
			String sql = "";
			sql = "select s.id id,s.idType,s.idRegion,s.idpersonne, s.idStatussignalement,titre,image,longitude,latitude,description,date from signalement s join statutsignalement st on s.idStatussignalement = st.id where st.intitule = '"+status+"' and s.date between '"+dateMin+"' and '"+dateMax+"' and idRegion = "+idd;
	    	setSignalements(jdbcTemplate.query(sql,new SignalementMapper()));
	    	System.out.println(sql);
	    	model.addAttribute("id",idd);
	    	Signalement[] listeSignalement = new Signalement[getSignalements().size()];
	    	for(int i=0; i<getSignalements().size(); i++) {
	    		listeSignalement[i] = getSignalements().get(i);
	    		System.out.println(listeSignalement[i].getIdStatusSignalement());
	    	}
			return listeSignalement;
		} else {
			return null;
		}
	}
	
	@GetMapping("/listeType/{token}")
	public Type[] getType(Model model,@PathVariable String token) {
		int istoken = traitementToken(token);
		if(istoken == 1) {
			String sql = "SELECT * FROM Type";
			setTypes(jdbcTemplate.query(sql,new TypeMapper()));
	    	System.out.println(sql);
	    	Type[] listeType = new Type[getTypes().size()];
	    	for(int i=0; i<listeType.length; i++) {
	    		listeType[i] = getTypes().get(i);
	    	}
			return listeType;
		} else {
			return null;
		}
		
	}
	
	@CrossOrigin
	@PostMapping("/insertSignalement/{token}")
	public int insertSignalement(Model model,@RequestBody FormSignalement formSignalement,@PathVariable String token) {
		int istoken = traitementToken(token);
		int rep = 0;
		if(istoken == 1) {
			setFormS(formSignalement);
			String fileName = saveImage(getFormS().getImage());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");      
			String dateToStr = dateFormat.format(getFormS().getDate());
			String sql1 = "INSERT INTO Signalement (idType,idPersonne,idStatusSignalement,titre,image,longitude,latitude,description,date) VALUES ("
	                +getFormS().getIdType()+","+getFormS().getIdPersonne()+","+getFormS().getIdStatusSignalement()+",'"+getFormS().getTitre()+"','"+fileName+"',"+getFormS().getLongitude()+","+getFormS().getLatitude()+",'"+getFormS().getDescription()+"','"+dateToStr+"')";
	        int rows = jdbcTemplate.update(sql1);
	        System.out.println(sql1);
	        rep = 1;
		} 
		return rep;
	}
	
	@GetMapping("/region/{idRegion}/{token}")
	public ResponseEntity<Region> getRegion(@PathVariable("idRegion") long id,@PathVariable String token){
		int istoken = traitementToken(token);
		if(istoken == 1) {
			String sql = "SELECT * FROM Region WHERE id = ? ";
			Region region = jdbcTemplate.queryForObject(sql,new Object[]{id},new RegionMapper());
			
			return new ResponseEntity<Region>(region,HttpStatus.OK);
		} else {
			return null;
		}
	}
	
	@GetMapping("/changerStatus/{token}")//maka an l id
	public int affectation(Model model,@PathVariable("id") String id,@RequestBody StatusSignalement statusSignalement,@PathVariable String token) {
		int istoken = traitementToken(token);
		int rep = 0;
		if(istoken == 1) {
			int idd = Integer.parseInt(id);
			model.addAttribute("id",idd);
			String sql1 = "select * from statusSignalements";
			setStatusSignalements(jdbcTemplate.query(sql1,new StatusSignalementMapper()));
			System.out.println(getStatusSignalements());
			model.addAttribute("regions", getStatusSignalements());
			rep = 1;
		}
		return rep;
	}
	
	@GetMapping("/supprimerToken/{token}")
	public int supprimerToken(Model model,@PathVariable("token") String token) {
		int istoken = traitementToken(token);
		int rep = 0;
		if(istoken == 1) {
			String sql1 = "delete from token where token = '"+token+"'";
			int rows = jdbcTemplate.update(sql1);
			rep = 1;
		}
		return rep;
	}

	@PostMapping("/changerStatusTraitement/{token}")// manao an l update
	public int affectationTraitement(Model model,@RequestBody SignalementChangerStatus signalementChangerStatus,@PathVariable String token) {
		int istoken = traitementToken(token);
		int rep = 0;
		if(istoken == 1) {
			setSignalementChangerStatus(signalementChangerStatus);
			String sql1 = "UPDATE Signalement SET idStatusSignalement = '"+getSignalementChangerStatus().getIdStatusSignalement()+"' WHERE id = "+getSignalementChangerStatus().getId();
			int rows = jdbcTemplate.update(sql1);
			System.out.println(sql1);
			rep = 1;
		}
		return rep;
	}
	
	@PostMapping("/idPersonne")
	public int getIdPersonne(@RequestBody FormLoginPerson formLoginPerson){
		setFormLoginPerson(formLoginPerson);
		String sql1 = "SELECT * FROM LoginPerson Where email = ? and mdp = ?";
		LoginPerson logF = jdbcTemplate.queryForObject(sql1, new Object[]{getFormLoginPerson().getEmail(),getFormLoginPerson().getMdp()}, new LoginPersonMapper());
		System.out.println(sql1);
		int id = logF.getId();
		return id;
	}
	
	@GetMapping("/listeSignalementParPersonne/{idPersonne}/{idToken}")
	public Signalement[] listeSignalementParPersonne(Model model,@PathVariable String idPersonne) {
		int id = Integer.parseInt(idPersonne);
		String sql = "select * from Signalement where idPersonne ="+id+"";
		setSignalements(jdbcTemplate.query(sql,new SignalementMapper()));
		Signalement[] signalement = new Signalement[getSignalements().size()];
		for(int i=0; i<getSignalements().size(); i++) {
			signalement[i] = getSignalements().get(i);
		}
		return signalement;
	}
	
	@GetMapping("/changerStatusTraitement/{idStatusSignalement}/{idSignalement}/{token}")// manao an l update
	public int affectationTraitement(Model model,@PathVariable String token,@PathVariable String idSignalement,@PathVariable String idStatusSignalement) {
		int istoken = traitementToken(token);
		int rep = 0;
		if(istoken == 1) {
			setSignalementChangerStatus(signalementChangerStatus);
			int idStatus = Integer.parseInt(idStatusSignalement);
			int idSignal = Integer.parseInt(idSignalement);
			String sql1 = "UPDATE Signalement SET idStatusSignalement = '"+idStatus+"' WHERE id = "+idSignal;
			int rows = jdbcTemplate.update(sql1);
			System.out.println(sql1);
			rep = 1;
		}
		return rep;
	}
	
	@GetMapping("/listeStatusSignalement")
	public StatusSignalement[] listeStatusSignalement(Model model) {
		String sql = "select * from statutsignalement";
		setStatusSignalements(jdbcTemplate.query(sql,new StatusSignalementMapper()));
		StatusSignalement[] statusSignalements = new StatusSignalement[getStatusSignalements().size()];
		for(int i=0; i<getStatusSignalements().size(); i++) {
			statusSignalements[i] = getStatusSignalements().get(i);
		}
		return statusSignalements;
	}
	
	@PostMapping("/inscription")
	public int inscription(Model model,@RequestBody LoginPersonInscription loginPersonIns) {
		setLoginPersonInscription(loginPersonIns);
		int rep = 0;
		String valiny = "tsy mety";
		String r = "yes";
		String[] accent = {"á","à","â","ä","å","ã","Á","À","Â","Ä","Å","Ã","é","è","ê","ë","É","È","Ê","Ë","í","ì","î","ï","Í","Ì","Î","Ï","ñ","Ñ","ó","ò","ô","ö","õ","Ó","Ò","Ô","Ö","Õ","š","Š","ú","ù","û","ü","Ú","Ù","Û","Ü","ý","ÿ","ž","Ý","Ÿ","Ž"};
		List<String> list = Arrays.asList(accent);
		EmailValidator validator = EmailValidator.getInstance();
		if (validator.isValid(getLoginPersonInscription().getEmail())) {
			char[] motDePasse = getLoginPersonInscription().getMdp().toCharArray();
			if(motDePasse.length >= 8) {
				for(int i=0; i<motDePasse.length; i++) {
					System.out.println(motDePasse[i]);
					System.out.println(list.contains(Character.toString(motDePasse[i])));
					if(list.contains(Character.toString(motDePasse[i])) == true) {
						r = "no";
						break;
					} 
				}
				if(r.equals("yes")) {
					String sql1 = "INSERT INTO LoginPerson (email,mdp,nom,age) VALUES ('"
			                +getLoginPersonInscription().getEmail()+"','"+getLoginPersonInscription().getMdp()+"','"+getLoginPersonInscription().getNom()+"',"+getLoginPersonInscription().getAge()+")";
			        int rows = jdbcTemplate.update(sql1);
			        System.out.println(sql1);
					rep = 1;
					valiny = "mety";
				}
			}
		}
		return rep;
	}
	
	@GetMapping("/detailSignalement/{id}/{token}")
	public ResponseEntity<Signalement> getDetailSignalementById(@PathVariable("id") long id,@PathVariable String token){
		int istoken = traitementToken(token);
		if(istoken == 1) {
			String sql = "SELECT * FROM Signalement WHERE id = ? ";
			Signalement detailSignalement = jdbcTemplate.queryForObject(sql,new Object[]{id},new SignalementMapper());
			System.out.println(detailSignalement.getImage());
			String fileName = "";
			try {
				fileName = encodeImage(detailSignalement.getImage());
				detailSignalement.setImage(fileName);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			return new ResponseEntity<Signalement>(detailSignalement,HttpStatus.OK);
		} else {
			return null;
		}
	}
	
	
	
	
	public String dateNow()
	{
		LocalDateTime now = LocalDateTime.now();
		String d = String.valueOf(now.getDayOfMonth())+ "/" + String.valueOf(now.getMonthValue()) + "/" + String.valueOf(now.getYear()) +" "+ String.valueOf(now.getHour()) + ":" + String.valueOf(now.getMinute()) + ":"+String.valueOf(now.getSecond());
		return  d;
	}
	public String creteToken(String newUserId)throws NoSuchAlgorithmException, UnsupportedEncodingException {
		newUserId = newUserId + dateNow();
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		crypt.update(newUserId.getBytes("UTF-8"));
		return new BigInteger(1,crypt.digest()).toString(16);
	}

	@PostMapping("/traitementPerson")
	public ReponseLoginPerson traitementPerson(Model model,@RequestBody FormLoginPerson formLoginPerson) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		setFormLoginPerson(formLoginPerson);
		ReponseLoginPerson lp = new ReponseLoginPerson();
		System.out.println(getFormLoginPerson().getEmail());
		String rep ;
		String sql = "SELECT Count(*) FROM LoginPerson Where email = '"+getFormLoginPerson().getEmail()+"' and mdp = '"+getFormLoginPerson().getMdp()+"'";
		System.out.println(sql);
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		if(count == 0) {
			rep = "non";
		} else {
			String sql1 = "SELECT * FROM LoginPerson Where email = ? and mdp = ?";
			rep = creteToken(getFormLoginPerson().getEmail());
			System.out.println(rep);
			LoginPerson logF = jdbcTemplate.queryForObject(sql1, new Object[]{getFormLoginPerson().getEmail(),getFormLoginPerson().getMdp()}, new LoginPersonMapper());
			String sql2 = "INSERT INTO Token (id,token) VALUES ("
	                +logF.getId()+",'"+rep+"')";
	        int rows = jdbcTemplate.update(sql2);
			lp.setId(logF.getId());
			lp.setToken(rep);
			//model.addAttribute("loginFront", logF);
		}
		return lp;
	}
	
	

	  @PostMapping("/uploadAll")
	  @ResponseBody
	  public boolean uploadAll(@RequestParam("file") MultipartFile file) {

	    try {
	      Path downloadedFile = this.uploadDirectory
	          .resolve(Paths.get(file.getOriginalFilename()));
	      Files.deleteIfExists(downloadedFile);
	      Files.copy(file.getInputStream(), downloadedFile);
	      return true;
	    }
	    catch (IOException e) {
	      LoggerFactory.getLogger(this.getClass()).error("uploadAll", e);
	      return false;
	    }

	  }
	  
	  public String saveImage(String dataString){
		  String fileName = "";
	        try {
	        	String data = dataString.split(",")[1];
	            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(data);
	            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
	            // write the image to a file
	            DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
	            Date date = new Date();        
	            String dateToStr = dateFormat.format(date);
	            fileName = dateToStr+".png";
	            File outputfile = new File("D:/fianarana/s5/Mr_rojo/CloudProject/"+fileName);
	            ImageIO.write(image, "png", outputfile);
            }catch(Exception e) {
                System.out.println(e.getStackTrace());
            }
	        return fileName;
	    }

	    public String encodeImage(String filename) throws Exception {
	        FileInputStream stream = new FileInputStream("D:/fianarana/s5/Mr_rojo/CloudProject/"+filename);
	        int bufLength = 2048;
	        byte[] buffer = new byte[2048];
	        byte[] data;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int readLength;
	        while ((readLength = stream.read(buffer, 0, bufLength)) != -1) {
	            out.write(buffer, 0, readLength);
	        }
	        data = out.toByteArray();
	        String imageString = Base64.getEncoder().encodeToString(data);
	        stream.close();
	        return imageString;
	    }
	
	@Override
    public void run(String... args) throws Exception {
    	
    }
}
