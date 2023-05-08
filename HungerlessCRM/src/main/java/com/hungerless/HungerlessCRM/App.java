package com.hungerless.HungerlessCRM;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.hungerless.HungerlessCRM.clients.ClientsAPI;

public class App
{
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException
	{
		
		new ClientsAPI().get();
		/*FileInputStream serviceAccount = new FileInputStream("./ServiceAccountKey.json");
		FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();
		FirebaseApp.initializeApp(options);

		/*
		 * HashMap<String, String> person = new HashMap<>(); person.put("first_name",
		 * "Erick"); person.put("last_name", "Lopez");
		 */

		//Firestore db = FirestoreClient.getFirestore();
		/*
		 * ApiFuture<WriteResult> collectionsApiFuture =
		 * db.collection("Clients").document().set(person);
		 * 
		 * System.out.println("Write result: " +
		 * collectionsApiFuture.get().getUpdateTime());
		 */

		

		
	}

}
