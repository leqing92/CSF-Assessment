package ibf2023.csf.backend.repositories;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class PictureRepository {

	@Autowired
	private AmazonS3 s3;

	// TODO Task 4.2
	// You may change the method signature by adding parameters and/or the  return type
	// You may throw any exception 
	public String save(MultipartFile file) throws IOException {

		Map<String, String> userData = new HashMap<>();
        userData.put("upload-timestamp", (new Date()).toString());
        userData.put("name", "lq");
        userData.put("filename", file.getOriginalFilename());
        
        //metadata for the file
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(userData);

        String key = UUID.randomUUID().toString().substring(0, 8);
        //take 4 parameter (bucketname, keyname | filename, inputstream, metadata)
        PutObjectRequest putReq = new PutObjectRequest("day37lq", key, file.getInputStream(), metadata);

        // make object publically available (ppl without key also can access)
        putReq.withCannedAcl(CannedAccessControlList.PublicRead);

        // PutObjectResult result = s3.putObject(putReq);
        s3.putObject(putReq);
        
        return s3.getUrl("day37lq", key).toString();
	}
	
}
