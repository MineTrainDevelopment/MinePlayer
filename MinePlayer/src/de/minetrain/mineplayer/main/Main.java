package de.minetrain.mineplayer.main;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.minetrain.mineplayer.data.EclipseStore;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private static EclipseStore eclipseStore;
	
	/**
	 * This seams to be the start of the End.
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("Hello World :D");
		eclipseStore = new EclipseStore();

//		EclipseStore.getStoreRoot().setRootMusicFolderPath("C:/Users/justi/Desktop/Neuer Ordner");
//		EclipseStore.getStoreRoot().setRootMusicFolderPath("C:/Users/justi/Videos/4K Video Downloader");
		EclipseStore.getStoreRoot().setRootMusicFolderPath("C:/");
		
		System.err.println(EclipseStore.getStoreRoot().getRootMusicFolderPath());
		if(EclipseStore.getStoreRoot().getRootMusicFolderPath() == null){
			System.exit(0);
			return;
		}
		
		
		RootMusicFolder rootMusicFolder = new RootMusicFolder(Paths.get(EclipseStore.getStoreRoot().getRootMusicFolderPath()));
		System.err.println("---");
		rootMusicFolder.getSubfolders().forEach(System.out::println);
		
		
		
		
		
		System.exit(0);
	}
	
	
}
