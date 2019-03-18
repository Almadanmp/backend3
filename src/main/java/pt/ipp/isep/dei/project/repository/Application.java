package pt.ipp.isep.dei.project.repository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pt.ipp.isep.dei.project.model.GeographicArea;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner dataPersisting(GeographicAreaRepository repository){
        return (args) -> {

            // fetch all customers
            log.info("Geographic Areas found with findAll():");
            log.info("--------------------------------------");
            for (GeographicArea geographicArea : repository.findAll()) {
                log.info(geographicArea.toString());
            }
            log.info("");
        };
    }
}