package choripan_solutions.db_exporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbExporterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbExporterApplication.class, args);
		System.out.println("DB Exporter Application Started");
		for (int i = 0; i < 3; i++) {
			System.out.println();
		}
		System.out.println("""
				==========================================
				|   Choripan Solutions - DB Exporter    |
				|    Application is now running!        |
				==========================================
				""");
	}

}
