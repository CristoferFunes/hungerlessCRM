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
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.Query.Direction;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public abstract class API
{
	FileInputStream serviceAccount;
	FirebaseOptions options;
	Firestore db;
	CollectionReference getRef;
	Query query;
	
	private void initializer()
	{
		try
		{
			serviceAccount = new FileInputStream("./ServiceAccountKey.json");
			options = FirebaseOptions
					.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();
			FirebaseApp.initializeApp(options);
			db = FirestoreClient.getFirestore();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	public List<Object> get()
	{
		initializer();
		List<Object> result = new ArrayList<>();
		try
		{
			getRef = db.collection(getFrom());
			query = getRef
					.whereEqualTo((String) getCondition().getK(), getCondition().getV())
					.orderBy(getOrder().getK(), (Direction) getOrder().getV())
					.limit(getLimit());
			ApiFuture<QuerySnapshot> future = query.get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents)
			{
				// System.out.println("Document data: " + document.getData());
				System.out.println("Name: " + document.get("first_name"));
			}	
			//documents.stream().map(mapper()).forEach(o -> result.add(o));	
		}
		catch(ExecutionException | InterruptedException e)
		{
			e.printStackTrace();
		}
		return result;
			
	}
	
	public abstract String getFrom();
	public abstract <V> Pair<String, V> getCondition();
	public abstract Pair<String, Object> getOrder();
	public abstract int getLimit();
	public abstract Function<QueryDocumentSnapshot, Object> mapper();
	
	
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
}
