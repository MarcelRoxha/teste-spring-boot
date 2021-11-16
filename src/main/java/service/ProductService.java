package service;

import entity.Product;
import model.ClienteModel;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProductService {
	
	

    private static final String COLLECTIO_NAME ="products";

public ProductService() {
	
}

    public String saveProduct(Product product) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();


       ApiFuture<WriteResult> apiFutureColletion=dbFirestore.collection(COLLECTIO_NAME).document(product.getName()).set(product);
        return apiFutureColletion.get().getUpdateTime().toString();

    }


    public Product getProductsDetailsByName(String name) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();


        DocumentReference documentReference =dbFirestore.collection(COLLECTIO_NAME).document(name);
        ApiFuture<DocumentSnapshot> future=documentReference.get();

        DocumentSnapshot documentSnapshot = future.get();
        if(documentSnapshot.exists()){
            Product product = documentSnapshot.toObject(Product.class);
            return product;
        }else {
            return null;
        }

    }

    public String updateProducts(Product product) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();


        ApiFuture<WriteResult> apiFutureColletion=dbFirestore.collection(COLLECTIO_NAME).document(product.getName()).set(product);
        return apiFutureColletion.get().getUpdateTime().toString();

    }

    public List<Product> getProductsAllDetails() throws ExecutionException, InterruptedException {
    	 List<Product> resultado = new ArrayList<>();

         Firestore firestore = FirestoreClient.getFirestore();
         CollectionReference collectionReference = firestore.collection("produtcts");

         ApiFuture<QuerySnapshot> query = collectionReference.get();
         List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
         for(QueryDocumentSnapshot doc : documentSnapshots){
             Product productmodel = doc.toObject(Product.class);
             resultado.add(productmodel);
         }
         return resultado;
    }
    
    public List<ClienteModel> getListaClientesCadastrados() throws ExecutionException, InterruptedException {
        List<ClienteModel> resultadoClientesCadastrados = new ArrayList<>();

        Firestore firestoreClientesCadastrados = FirestoreClient.getFirestore();
        CollectionReference collectionReferenceClientesCadastrados = firestoreClientesCadastrados.collection("CLIENTES");

        ApiFuture<QuerySnapshot> query = collectionReferenceClientesCadastrados.get();
        List<QueryDocumentSnapshot> documentSnapshots = query.get().getDocuments();
        for(QueryDocumentSnapshot doc : documentSnapshots){
            ClienteModel clienteCadastrado = doc.toObject(ClienteModel.class);
            resultadoClientesCadastrados.add(clienteCadastrado);
        }
        if(resultadoClientesCadastrados.size() == 0){
            return null;
        }else {
            return resultadoClientesCadastrados;
        }


    }



}
