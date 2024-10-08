package de.minetrain.mineplayer.data;

import java.nio.file.Path;

import org.eclipse.store.storage.embedded.types.EmbeddedStorage;
import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;


public class EclipseStore {
	private static EmbeddedStorageManager storageManager;
	private static ProgramData root;
	
	public EclipseStore() {
		storageManager = EmbeddedStorage.start(Path.of(System.getProperty("user.home"), "appdata", "locallow", "MineTrainDev", "MinePlayer", "saved", "metadata"));
		root = getStoreRoot();
		
		if(root == null){
			root = new ProgramData();
			
			storageManager.setRoot(root);
			storageManager.storeRoot();
			root.persister = storageManager;
		}
		
	}

	public static ProgramData getStoreRoot() {
		return (ProgramData) storageManager.root();
	}

	/**
	 * @return true after a successful shutdown or falseif an internal InterruptedException happened.
	 */
	public static boolean shutdown(){
		return storageManager.shutdown();
	}
	
}
