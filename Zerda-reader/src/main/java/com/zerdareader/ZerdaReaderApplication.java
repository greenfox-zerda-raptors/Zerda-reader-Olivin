package com.zerdareader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZerdaReaderApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ZerdaReaderApplication.class, args);
	}

	@Autowired
	private FeedItemRepository repo;

	@Override
	public void run(String... strings) throws Exception {
		for (int i = 0; i < 10; i++) {
			repo.save(new FeedItem();
		}
	}
}

