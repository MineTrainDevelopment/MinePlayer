package de.minetrain.mineplayer.util;

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import de.minetrain.mineplayer.obj.AudioTrack;

/**
 * 
 * @author MineTrain/Justin
 * https://github.com/mpatric/mp3agic?tab=readme-ov-file
 */
public class Mp3Parser {
	private static final Logger logger = LoggerFactory.getLogger(Mp3Parser.class);
	
	public static AudioTrack parse(Path path) {
		ID3v2 tags = getMetaData(path);
		return tags == null ? null : new AudioTrack(tags, path);
	}
	
	/**
	 * @param path
	 * @return may be null.
	 */
	public static ID3v2 getMetaData(Path path){
		try {
			return new Mp3File(path).getId3v2Tag();
		} catch (Exception ex) {
			logger.warn("Faild to fetch MP3 metadata for ("+path.toString()+")");
			logger.debug("Stacktrace -> ", ex);
			return null;
		}
	}
	
}
