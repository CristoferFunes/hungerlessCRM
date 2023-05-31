package com.hungerless.HungerlessCRM;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.Query.Direction;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public abstract class API
{
	
	private FileInputStream serviceAccount;
	private FirebaseOptions options;
	private boolean initialized = false;
	FirebaseApp firebaseApp;
	private Firestore db;
	private CollectionReference getRef;
	private Query query;
	
	public static final int EQUALSTO = 1;
	public static final int GREATERTHAN = 2;
	public static final int GREATHEROREQUALTHAN = 3;
	public static final int LESSTHAN = 4;
	public static final int LESSOREQUALTHAN = 5;
	public static final int ARRAYCONTAINS = 6;
	public static final int EQUALSANDGREATER = 7;
	public static final int EQUALSANDLESSOREQUAL = 8;
	public static final int DOUBLEEQUALSTO = 9;
	
	protected void initializer()
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
						case 6 -> getRef.whereArrayContains((String) getCondition().getK(), getCondition().getV());
						case 7 -> getRef.whereEqualTo((String) getCondition().getK(), getCondition().getV()).whereGreaterThan((String) getSecondCondition().getK(), getSecondCondition().getV());
						case 8 -> getRef.whereEqualTo((String) getCondition().getK(), getCondition().getV()).whereLessThanOrEqualTo((String) getSecondCondition().getK(), getSecondCondition().getV());
						case 9 -> getRef.whereEqualTo((String) getCondition().getK(), getCondition().getV()).whereEqualTo((String) getSecondCondition().getK(), getSecondCondition().getV());
						default -> throw new IllegalArgumentException("Unexpected value: " + getTypeOfCondition());
					};
			query = getOrder() != null ? query.orderBy(getOrder().getK(), (Direction) getOrder().getV()) : query;
			query = query.limit(getLimit());
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
			initializer();
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
		try
		{
			initializer();
			DocumentReference docRef = db.collection(from()).document(idRef(o));
			ApiFuture<WriteResult> updateFuture = docRef.update(putMapper(o));
			updateFuture.get();
		} catch (InterruptedException | ExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete(Object o)
	{
		try
		{
			initializer();
			DocumentReference docRef = db.collection(from()).document(idRef(o));
			ApiFuture<WriteResult> deleteFuture = docRef.delete();
			deleteFuture.get();
		} catch (InterruptedException | ExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public <T> T getDocument(String id, Function<DocumentSnapshot, T> mapper)
	{
		initializer();
		try
		{
			DocumentReference docRef = db.collection(from()).document(id);
			ApiFuture<DocumentSnapshot> document = docRef.get();
			return mapper.apply(document.get());
		}
		catch(ExecutionException | InterruptedException e)
		{
			e.printStackTrace();
		}
		return null;		
	}	
	
	public abstract String from();
	public abstract int getTypeOfCondition();
	public abstract <V> Pair<String, V> getCondition();
	public abstract <V> Pair<String, V> getSecondCondition();
	public abstract Pair<String, Object> getOrder();
	public abstract int getLimit();
	public abstract <T> Function<QueryDocumentSnapshot, T> mapper();
	public abstract <T> HashMap<String, Object> putMapper(T t);
	public abstract <T> String idRef(T t);
}
