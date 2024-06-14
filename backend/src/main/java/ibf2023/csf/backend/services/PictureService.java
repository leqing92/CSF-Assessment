package ibf2023.csf.backend.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ibf2023.csf.backend.models.Post;
import ibf2023.csf.backend.repositories.ImageRepository;
import ibf2023.csf.backend.repositories.PictureRepository;
import jakarta.json.Json;

@Service
public class PictureService {

	@Autowired
	ImageRepository mongoRepo;

	@Autowired
	PictureRepository s3Repo;

	@Value("${preconfigured.threshold}")
	private long preconfigured_threshold;

	// TODO Task 5.1
	// You may change the method signature by adding parameters and/or the return type
	// You may throw any exception 
	public ResponseEntity<String> save(MultipartFile picture, Post post) {

		Long sum = mongoRepo.getFileSize();
		if((picture.getSize() + sum) > (preconfigured_threshold * 1024 * 1024)){
			String message = "The upload has exceeded your monthly upload quota of " + preconfigured_threshold + "MB";
			return ResponseEntity.status(413).body(
				Json.createObjectBuilder().add("message", message).build().toString()
			);
		}
		
		try {
			String id = UUID.randomUUID().toString().substring(0, 8);
			post.setId(id);

			String url = s3Repo.save(picture);			
			post.setUrl(url);

			mongoRepo.save(post);

			return ResponseEntity.status(200).body(
				Json.createObjectBuilder().add("id", id).build().toString()
			);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return ResponseEntity.status(500).body(
				Json.createObjectBuilder().add("message", "Internal server error").build().toString()
			);
		}	
	}
}
