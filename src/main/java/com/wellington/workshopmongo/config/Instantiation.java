package com.wellington.workshopmongo.config;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.wellington.workshopmongo.domain.Post;
import com.wellington.workshopmongo.domain.User;
import com.wellington.workshopmongo.dto.AuthorDto;
import com.wellington.workshopmongo.dto.CommentDto;
import com.wellington.workshopmongo.repository.PostRepository;
import com.wellington.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.of("GMT"));

		userRepository.deleteAll();
		postRepository.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		userRepository.saveAll(Arrays.asList(maria, alex, bob));

		Post post1 = new Post(null, LocalDate.parse("21/03/2018", fmt), "Partiu viagem", "Vou viajar para São Paulo",
				new AuthorDto(maria));
		Post post2 = new Post(null, LocalDate.parse("23/03/2018", fmt), "Bom dia!", "Acordei feliz hoje!",
				new AuthorDto(maria));
		
		CommentDto c1 = new CommentDto("Boa viagem mano!", LocalDate.parse("21/03/2018", fmt), new AuthorDto(alex));
		CommentDto c2 = new CommentDto("Aproveite!", LocalDate.parse("22/03/2018", fmt), new AuthorDto(bob));
		CommentDto c3 = new CommentDto("Tenha um ótimo dia", LocalDate.parse("23/03/2018", fmt), new AuthorDto(alex));

		post1.getComments().addAll(Arrays.asList(c1,c2));
		post2.getComments().addAll(Arrays.asList(c3));
		 
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1,post2));
		userRepository.save(maria);

	}

}
