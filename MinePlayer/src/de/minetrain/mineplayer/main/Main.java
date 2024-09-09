package de.minetrain.mineplayer.main;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
		EclipseStore.getStoreRoot().setRootMusicFolderPath("C:/Users/justi/Videos/4K Video Downloader");
		
		String rootFolder = EclipseStore.getStoreRoot().getRootMusicFolderPath();
		System.err.println(rootFolder);
		
		if(rootFolder == null){
			System.exit(0);
			return;
		}
		
		
		new RootMusicFolder().load(Paths.get(EclipseStore.getStoreRoot().getRootMusicFolderPath()));

		
		
		
		
		System.exit(0);
	}
	
}
