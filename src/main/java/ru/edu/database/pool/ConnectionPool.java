package ru.edu.database.pool;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("pool1")
public class ConnectionPool {

	private final String username;
	private final Integer poolSize;

	// @Autowired // для одного конструктора Autowired будет вызван автоматически
	public ConnectionPool(@Value("${db.username}") String username, @Value("${db.pool.size}") Integer poolSize) {
		this.username = username;
		this.poolSize = poolSize;
	}

	@PostConstruct
	private void init() {
		System.out.println("Init connection pool");
	}

	// отработает при явной закрытии контекста через close()
	@PreDestroy
	private void destroy() {
		System.out.println("Clean connection pool");
	}

}
