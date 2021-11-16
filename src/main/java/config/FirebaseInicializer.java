package config;

import java.io.IOException;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@RestController
@RequestMapping("/")
@AutoConfigurationPackage
public class FirebaseInicializer {
	
	 @Value("${app.firebase-configuration-file}")
	    private String firebaseConfigPath;
	    Logger logger = LoggerFactory.getLogger(FirebaseInicializer.class);
	    @PostConstruct
	    public void initialize() {
	        try {
	            FirebaseOptions options = new FirebaseOptions.Builder()
	                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath)
	                    		.getInputStream()))
	                    		.setDatabaseUrl("https://destack360-default-rtdb.firebaseio.com")
	                    		.build();
	            if (FirebaseApp.getApps().isEmpty()) {
	                FirebaseApp.initializeApp(options);
	                logger.info("Firebase application has been initialized");
	            }
	        } catch (IOException e) {
	            logger.error(e.getMessage());
	        }
	    }

}
