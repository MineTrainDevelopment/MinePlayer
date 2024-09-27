package de.minetrain.mineplayer.data;

import org.eclipse.serializer.persistence.types.Persister;
import org.eclipse.serializer.persistence.types.Storer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProgramData {
	private static final Logger logger = LoggerFactory.getLogger(ProgramData.class);
	private String rootMusicFolderPath;
	public transient Persister persister;
	
	public String getRootMusicFolderPath(){
		return rootMusicFolderPath;
	}
	
	public void setRootMusicFolderPath(String path){
		rootMusicFolderPath = path;
		persister.store(this);
	}
	
	public void store() {
		Storer eagerStorer = persister.createEagerStorer();
		eagerStorer.store(rootMusicFolderPath);
		eagerStorer.commit();
	}

}