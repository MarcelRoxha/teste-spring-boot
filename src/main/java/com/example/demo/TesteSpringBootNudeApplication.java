package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import config.FirebaseInicializer;
import entity.Product;
import service.ProductService;



@RestController
@RequestMapping("/api")
@SpringBootApplication
@EnableAutoConfiguration
public  class TesteSpringBootNudeApplication {
	
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
	  
	    @GetMapping("/teste") public ResponseEntity<String> testGetEndpoint(){ return
	    		  ResponseEntity.ok("<h1>Teste get Endapoint is Working</h1>"); }
	    
	  
	    private ProductService productService; 
	    private Firestore firestore;
	    
	    @GetMapping("/products")
	    public List<Product> getProductsAllDetails() throws
	    ExecutionException, InterruptedException {   
	    	
	    	 List<Product> resultado = new ArrayList<>();

	         Firestore firestore = FirestoreClient.getFirestore();
	         CollectionReference collectionReference = firestore.collection("produtcts");

	         ApiFuture<QuerySnapshot> query = collectionReference.get();
	         List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
	         for(QueryDocumentSnapshot doc : documentSnapshots){
	             Product productmodel = doc.toObject(Product.class);
	             resultado.add(productmodel);
	         }
	       
	    	
	    	
	
		return resultado; }
	    
	    
	    
	    
	    
	    
	    

	    
	    @GetMapping("/listaEntradasLancadas")
	    		

	public static void main(String[] args) throws Exception {
		
		
		
		SpringApplication.run(TesteSpringBootNudeApplication.class, args);
		

		
		
	}

}
