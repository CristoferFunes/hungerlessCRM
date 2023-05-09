package com.hungerless.HungerlessCRM;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.Query.Direction;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import com.hungerless.HungerlessCRM.clients.Client;

public abstract class API
{
	private FileInputStream serviceAccount;
	private FirebaseOptions options;
	private Firestore db;
	private CollectionReference getRef;
	private Query query;
	FirebaseApp firebaseApp;
	private boolean initialized = false;
	public static final int EQUALSTO = 1;
	public static final int GREATERTHAN = 2;
	public static final int GREATHEROREQUALTHAN = 3;
	public static final int LESSTHAN = 4;
	public static final int LESSOREQUALTHAN = 5;
	
	private void initializer()
	{
		if (initialized) return;
		try
		{
			serviceAccount = new FileInputStream("./ServiceAccountKey.json");
			options =FirebaseOptions
					.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();
			firebaseApp = null;
		    List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
		    if(firebaseApps!=null && !firebaseApps.isEmpty()){
		        for(FirebaseApp app : firebaseApps){
		            if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
		                firebaseApp = app;
		        }
		    }
		    else
		        firebaseApp = FirebaseApp.initializeApp(options);  
			db = FirestoreClient.getFirestore();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		initialized = true;
	}
	public List<Object> get()
	{
		initializer();
		List<Object> result = new ArrayList<>();
		try
		{
			getRef = db.collection(from());
			query = switch(getTypeOfCondition())
					{
						case 1 -> getRef.whereEqualTo((String) getCondition().getK(), getCondition().getV());
						case 2 -> getRef.whereGreaterThan((String) getCondition().getK(), getCondition().getV());
						case 3 -> getRef.whereGreaterThanOrEqualTo((String) getCondition().getK(), getCondition().getV());
						case 4 -> getRef.whereLessThan((String) getCondition().getK(), getCondition().getV());
						case 5 -> getRef.whereLessThanOrEqualTo((String) getCondition().getK(), getCondition().getV());
						default -> throw new IllegalArgumentException("Unexpected value: " + getTypeOfCondition());
					};
			query = query.orderBy(getOrder().getK(), (Direction) getOrder().getV())
					.limit(getLimit());
			ApiFuture<QuerySnapshot> future = query.get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			documents.stream().map(mapper()).forEach(o -> result.add(o));	
		}
		catch(ExecutionException | InterruptedException e)
		{
			e.printStackTrace();
		}
		return result;		
	}
	
	public String post(Object o)
	{
		String id = null;
		try
		{
			ApiFuture<DocumentReference> addDocRef = db.collection(from()).add(putMapper(o));
			id = addDocRef.get().getId();
		} catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
		return id;		
	}
	
	public void update(Object o)
	{
		DocumentReference addDocRef = db.collection(from()).document(updateRef(o));
		addDocRef.get();
	}
	
	public abstract String from();
	public abstract int getTypeOfCondition();
	public abstract <V> Pair<String, V> getCondition();
	public abstract Pair<String, Object> getOrder();
	public abstract int getLimit();
	public abstract Function<QueryDocumentSnapshot, Object> mapper();
	public abstract HashMap<String, Object> putMapper(Object o);
	public abstract String updateRef(Object o);
	
	
	/*
	// Get a reference to the "users" collection
	CollectionReference usersRef = db.collection("users");

	// Query for all documents where the "age" field is equal to 30
	Query query = usersRef.whereEqualTo("age", 30);

	// Execute the query and get the results as a QuerySnapshot
	ApiFuture<QuerySnapshot> querySnapshotFuture = query.get();
	QuerySnapshot querySnapshot = querySnapshotFuture.get();

	// Iterate over the results and print the data for each document
	for (QueryDocumentSnapshot document : querySnapshot) {
	    System.out.println(document.getData());
	}
	*/
	//<T> List<T>
		//Post
		//Put
		//Delete
	
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
