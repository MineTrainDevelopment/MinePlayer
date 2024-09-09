package de.minetrain.mineplayer.main;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RootMusicFolder {
	private static final Logger logger = LoggerFactory.getLogger(RootMusicFolder.class);
	public enum MusicFolderValidationError{PROVIDED_A_FILE, PROVIDED_EMPTY_FOLDER, FOUND_NO_MP3, INVALID_PATH, ERROR, SUCCESS};
	private ArrayList<Path> mp3Files = new ArrayList<Path>();
	
	public MusicFolderValidationError load(Path path) {
		MusicFolderValidationError validateFolder = validateFolder(path);
		
		mp3Files.stream().map(Path::toString).forEach(System.out::println);
		System.err.println(validateFolder.name()+" - "+mp3Files.size());
		return validateFolder;
	}

	private MusicFolderValidationError validateFolder(Path path) {
		System.err.println("Folder: "+path);
		
		if(Files.exists(path) && Files.isDirectory(path)){
			System.err.println("Valid?");
			
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                boolean isEmpty = true;
                for (Path entry : stream) {
                    isEmpty = false;
                    validateFile(entry);
                }
                if (isEmpty) {
                    System.out.println("empty");
        			return MusicFolderValidationError.PROVIDED_EMPTY_FOLDER;
                }
            } catch (IOException ex) {
            	logger.error("", ex);
            	return MusicFolderValidationError.ERROR;
            }
		}else{
			System.err.println("Providet a file.");
			return MusicFolderValidationError.PROVIDED_A_FILE;
		}
		
		return MusicFolderValidationError.SUCCESS;
	}
	
	private void validateFile(Path entry){
		if(entry.getFileName().toString().endsWith(".mp3")){ //Regex check..
			logger.debug("Store path -> "+entry);
			mp3Files.add(entry);
			return;
		}
		
		if(Files.isDirectory(entry)){
			MusicFolderValidationError validationError = validateFolder(entry);
			logger.info("Validate Folder -> "+validationError.name());
		}
		
	}

}
