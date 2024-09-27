package de.minetrain.mineplayer.main;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.minetrain.mineplayer.obj.AudioTrack;
import de.minetrain.mineplayer.util.Mp3Parser;


public class RootMusicFolder {
	private static final Logger logger = LoggerFactory.getLogger(RootMusicFolder.class);
	public static enum MusicFolderValidationError{PROVIDED_A_FILE, PROVIDED_EMPTY_FOLDER, INVALID_PATH, ACCESS_DENIED, ERROR, SUCCESS};
	private HashMap<String, ArrayList<Path>> mp3Paths = new HashMap<>();
	public HashMap<String, List<AudioTrack>> mp3Tracks = new HashMap<>();
	
	public RootMusicFolder(Path path) {
		validateFolder(path);
		
		mp3Paths.entrySet().forEach(entry -> {
			mp3Tracks.put(entry.getKey(), entry.getValue().stream().map(Mp3Parser::parse).filter(Objects::nonNull).toList());
		});
//		mp3Tracks.values().stream().flatMap(List::stream).forEach(System.out::println);
//		System.err.println(validateFolder.name()+" - "+mp3Paths.values().stream().flatMap(List::stream).count()+" - "+mp3Tracks.values().stream().flatMap(List::stream).count());
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
			} catch (AccessDeniedException ex) {
				logger.info("Access Denied to folder -> "+path);
				return MusicFolderValidationError.ACCESS_DENIED;

			} catch (IOException ex) {
				logger.error("", ex);
				return MusicFolderValidationError.ERROR;
			}
		} else {
			return MusicFolderValidationError.PROVIDED_A_FILE;
		}
		
		return MusicFolderValidationError.SUCCESS;
	}
	
	private void validateFile(Path entry){
		if(entry.getFileName().toString().endsWith(".mp3")){ //Regex check??..
			logger.debug("Store path -> "+entry);
			mp3Paths.computeIfAbsent(entry.getParent().getFileName().toString(), key -> new ArrayList<>()).add(entry);
			
			return;
		}
		
		if(Files.isDirectory(entry)){
			MusicFolderValidationError validationError = validateFolder(entry);
			logger.info("Validate Folder -> "+validationError.name());
		}
	}
	
	public List<Object> getSubfolders() {
		return List.of(mp3Tracks.keySet().toArray());
	}

}
