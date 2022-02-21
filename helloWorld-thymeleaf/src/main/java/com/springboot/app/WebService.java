package com.springboot.app;

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
	    	for(int i=0; i<getSignalements().size(); i++) {
	    		listeSignalement[i] = getSignalements().get(i);
	    		System.out.println(listeSignalement[i].idStatusSignalement);
	    	}
			return listeSignalement;
		} else {
			return null;
		}
		
	}
	
	@GetMapping("/listeSignalementRechercher/{id}/{status}/{token}")
	public Signalement[] getListeRechercher(Model model,@PathVariable String id,@PathVariable String status,@PathVariable String token) {
		int istoken = traitementToken(token);
		if(istoken == 1) {
			int idd = Integer.parseInt(id);
			String sql = "select s.id id,s.idType,s.idRegion,s.idStatussignalement,titre,image,longitude,latitude,description from signalement s join statutsignalement st on s.idStatussignalement = st.id where st.intitule = '"+status+"' and idRegion = "+idd;
	    	setSignalements(jdbcTemplate.query(sql,new SignalementMapper()));
	    	System.out.println(sql);
	    	model.addAttribute("id",idd);
	    	Signalement[] listeSignalement = new Signalement[getSignalements().size()];
	    	for(int i=0; i<getSignalements().size(); i++) {
	    		listeSignalement[i] = getSignalements().get(i);
	    		System.out.println(listeSignalement[i].idStatusSignalement);
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
			String sql1 = "INSERT INTO Signalement (idType,idStatusSignalement,titre,image,longitude,latitude,description) VALUES ("
	                +getFormS().getIdType()+","+getFormS().getIdStatusSignalement()+",'"+getFormS().getTitre()+"','"+fileName+"',"+getFormS().getLongitude()+","+getFormS().getLatitude()+",'"+getFormS().getDescription()+"')";
	        int rows = jdbcTemplate.update(sql1);
	        System.out.println(sql1);
	        rep = 1;
		} 
		return rep;
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
	
	@PostMapping("/inscription")
	public String inscription(Model model,@RequestBody LoginPersonInscription loginPersonIns) {
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
		return valiny;
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
	
	@Value("${file.upload-dir}")//ito maka anle anaranle fichier histockena anle fichier ,jerevo ao amin application.properties
	String FILE_DIRECTORY;
	@GetMapping("/encoder")
	public String encoder(@RequestParam("base64Img") String base64Img){
		System.out.println("base : "+base64Img);
		String rep = "";
		/*Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmssSS");
		String fileName = sdf+".jpg";
		String pathFile = "D:\\fianarana\\s5\\Mr_rojo\\CloudProject\\";
		try { 
			byte[] imageByteArray = Base64.getDecoder().decode(base64Img.getBytes(StandardCharsets.UTF_8));
			File myFile = new File(FILE_DIRECTORY+fileName);
			myFile.createNewFile();
			FileOutputStream fos =new FileOutputStream(myFile);
			fos.write(imageByteArray);
			fos.close();
		} catch(FileNotFoundException e) {
			System.out.println("Image not found " + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the image " + ioe);
		}*/
		return rep;
	}
	
	@PostMapping("/ajouterPhoto")
	public ResponseEntity<Object> fileUpload(@RequestParam("File") MultipartFile file) throws Exception{
		
		String fileName = file.getOriginalFilename();
		
		if(fileName.equals("")) return new ResponseEntity<Object>("Veuillez selectionner une image pour ce signalement", HttpStatus.OK);
		else {
			File myFile = new File(FILE_DIRECTORY+fileName);
			myFile.createNewFile();
			FileOutputStream fos =new FileOutputStream(myFile);
			fos.write(file.getBytes());
			fos.close();
			return new ResponseEntity<Object>("Image ajouté avec Succes", HttpStatus.OK);
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
	public String traitementPerson(Model model,@RequestBody FormLoginPerson formLoginPerson) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		setFormLoginPerson(formLoginPerson);
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
			LoginPerson logF = jdbcTemplate.queryForObject(sql1, new Object[]{getFormLoginPerson().getEmail(),getFormLoginPerson().getMdp()}, new LoginPersonMapper());
			String sql2 = "INSERT INTO Token (id,token) VALUES ("
	                +logF.getId()+",'"+rep+"')";
	        int rows = jdbcTemplate.update(sql2);
			
			//model.addAttribute("loginFront", logF);
		}
		return rep;
	}
	
	@PostMapping("/ajouterSignalement/{token}")
	public ResponseEntity<Object> ajouterSignalement(
			@RequestParam("idType") String idType,
			@RequestParam("titre") String titre,
			@RequestParam("longitude") String longitude,
			@RequestParam("latitude") String latitude,
			@RequestParam("description") String description,
			@RequestParam("image") MultipartFile image,
			@PathVariable String token
			) throws IOException {
		int istoken = traitementToken(token);
		if(istoken == 1) {
			System.out.println(image);
			String imageFileName = image.getOriginalFilename();
			if(imageFileName.equals("")) {
				String sql1 = "INSERT INTO Signalement (idType,idStatussignalement,titre,longitude,latitude,description) VALUES ("
		                +idType+",1,'"
						+titre+"',"
						+longitude+","
		                +latitude+",'"
						+description+"')";
				int rows = jdbcTemplate.update(sql1);
				return new ResponseEntity<Object>("Signalement ajouté avec succès", HttpStatus.OK);
			}
			else {
				String sql1 = "INSERT INTO Signalement (idType,idStatussignalement,titre,image,longitude,latitude,description) VALUES ("
			                +idType+",1,'"
							+titre+"','"
			                +imageFileName+"',"
							+longitude+","
			                +latitude+",'"
							+description+"')";
		        int rows = jdbcTemplate.update(sql1);
		        File myFile = new File(FILE_DIRECTORY+imageFileName);
		        myFile.createNewFile();
				FileOutputStream fos =new FileOutputStream(myFile);
				fos.write(image.getBytes());
				fos.close();
				
				return new ResponseEntity<Object>("Signalement ajouté avec succès", HttpStatus.OK);
			}	
		} else {
			return null;
		}
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