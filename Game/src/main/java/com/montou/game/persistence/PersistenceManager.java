package com.montou.game.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

import com.montou.game.shared.GameInformations;

public class PersistenceManager {
	
	public static final String GAME_INFORMATIONS_FILE = "GameInformations.save";
	
	public static void save(String dirPath, GameInformations gameInformations) {
		PersistenceManager.save(dirPath, GAME_INFORMATIONS_FILE, gameInformations);
	}
	
	public static void save(String dirPath, String fileName, Object objectToSave) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(Paths.get(dirPath, fileName).toString());
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(objectToSave);
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object load(String dirPath, String fileName) {
		Object loadedObject = null;
		
		try {
	         FileInputStream fileInputStream = new FileInputStream(Paths.get(dirPath, fileName).toString());
	         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
	         loadedObject = objectInputStream.readObject();
	         objectInputStream.close();
	         fileInputStream.close();
	      } catch (IOException | ClassNotFoundException e) {
	    	  if (!(e instanceof FileNotFoundException))
	    		  e.printStackTrace();
	      }
		
		return loadedObject;
	}
}
