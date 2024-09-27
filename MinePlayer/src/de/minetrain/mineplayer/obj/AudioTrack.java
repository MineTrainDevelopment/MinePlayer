package de.minetrain.mineplayer.obj;

import java.nio.file.Path;
import java.util.Objects;

import com.mpatric.mp3agic.ID3v2;

import de.minetrain.mineplayer.util.Mp3Parser;

public class AudioTrack {
	private final Path path;
	private final String title;
	private final String artist;
	private final String genre;
	private final long length;
	private final boolean hasImage;
	
	public AudioTrack(ID3v2 tags, Path path) {
		this.path = path;
		this.title = tags.getTitle();
		this.artist = tags.getArtist();
		this.genre = tags.getGenreDescription();
		this.length = tags.getLength();
		hasImage = !Objects.isNull(tags.getAlbumImage());
	}
	
	public void getImage(){
		ID3v2 tags = Mp3Parser.getMetaData(path);
		if(tags != null){
			byte[] albumImageData = tags.getAlbumImage();
			if (albumImageData != null) {
				System.out.println("Album image mime type: " + tags.getAlbumImageMimeType());
			}
		}
	}
	
	
	
	public String getTitle() {
		return title != null ? title : path.getFileName().toString();
	}
	
	@Override
	public String toString() {
		return ((artist == null) ? "" : artist + " - ") + getTitle();
	}

}
