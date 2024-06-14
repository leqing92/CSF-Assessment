package ibf2023.csf.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ibf2023.csf.backend.repositories.ImageRepository;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner{

	@Autowired
	ImageRepository imageRepo;

	@Value("${preconfigured.threshold}")
	private long preconfigured_threshold;
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("threshold:" + preconfigured_threshold);
		System.out.println(imageRepo.getFileSize());
	}

}
