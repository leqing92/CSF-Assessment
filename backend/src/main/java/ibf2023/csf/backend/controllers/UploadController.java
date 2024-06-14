package ibf2023.csf.backend.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2023.csf.backend.models.Post;
import ibf2023.csf.backend.services.PictureService;

// You can add addtional methods and annotations to this controller. 
// You cannot remove any existing annotations or methods from UploadController
@Controller
@RequestMapping(path="/api")
public class UploadController {

	@Autowired
	PictureService pictureSvc;

	// TODO Task 5.2
	// You may change the method signature by adding additional parameters and annotations.
	// You cannot remove any any existing annotations and parameters from postUpload()
	// 2024-06-14T03:40:26.491Z
	@PostMapping(path="/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> postUpload(@RequestPart MultipartFile picture, 
		@RequestPart String title, 
		@RequestPart (required = false) String comments, 
		@RequestPart String date) {

		// System.out.println("\n\n\ntitle: " + title);
		// System.out.println("comments: " + comments);
		// System.out.println("date: " + date);
		// System.out.println("picture: " + picture.getSize());
		
		Post post = new Post();		
		Date dateDateFormat = new Date(Long.parseLong(date));
		post.setDate(dateDateFormat);
		post.setTitle(title);	
		post.setComment(comments);
		post.setFileSize(picture.getSize());	
		
		return pictureSvc.save(picture, post);
	}
}
