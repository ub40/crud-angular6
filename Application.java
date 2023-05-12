package nl.jrevolt.app;

import lombok.extern.slf4j.Slf4j;
import nl.jrevolt.app.model.*;
import nl.jrevolt.app.repository.BusinessInformationRepository;
import nl.jrevolt.app.repository.RoleRepository;
import nl.jrevolt.app.repository.UserRepository;
import nl.jrevolt.app.repository.tokens.UrlTokenRepository;
import nl.jrevolt.app.repository.tokens.UrlTokenService;
import nl.jrevolt.app.services.SubscriptionService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableConfigurationProperties()
@EnableScheduling
@Slf4j
public class Application {
    @Autowired
    DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository, BusinessInformationRepository businessInformationRepository, RoleRepository roleRepository, UrlTokenRepository urlTokenRepository) {
        return args -> {
            List<Role> list = Arrays.stream(ERole.values()).map(Role::new).collect(Collectors.toList());
            if (roleRepository.count() == 0){
                roleRepository.saveAll(list);
            }else {
                list =  roleRepository.findAll();
            }
            User user = addPOwnerUser(userRepository,list.stream().filter(s -> s.getName() == ERole.ROLE_PRODUCT_OWNER).findFirst().get());
            addPOwnerBusinessInfo(user,businessInformationRepository);
            addPOwnerInitToken(user,urlTokenRepository);
            User admin = addAdminUser(userRepository,list.stream().filter(s -> s.getName() == ERole.ROLE_ADMIN).findFirst().get());
            addAdminInitToken(admin,urlTokenRepository);
        };
    }

    private User addPOwnerUser(UserRepository userRepository, Role role){
        return userRepository.findByEmailIgnoreCase("urfan@jrevolt.com").orElseGet(() -> {
            User user = new User(
                    "urfan@jrevolt.com",
                    "$2a$10$ishPp2Me2Cw7FHMh.5UrMuePICMPxKEPG4ppoks.BQRcUrroSX5JG",
                    "$2a$10$ishPp2Me2Cw7FHMh.5UrMuePICMPxKEPG4ppoks.BQRcUrroSX5JG"
            );
            user.setAccountVerified(true);
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            user.setFirstName("Urfan");
            user.setLastName("Beijlerbeijli");
            userRepository.save(user);

            return user;
        });
    }
    private User addAdminUser(UserRepository userRepository, Role role){
        return userRepository.findByEmailIgnoreCase("ub@beijlerbeijli.nl").orElseGet(() -> {
            User user = new User(
                    "ub@beijlerbeijli.nl",
                    "$2a$10$ishPp2Me2Cw7FHMh.5UrMuePICMPxKEPG4ppoks.BQRcUrroSX5JG",
                    "$2a$10$ishPp2Me2Cw7FHMh.5UrMuePICMPxKEPG4ppoks.BQRcUrroSX5JG"
            );
            user.setAccountVerified(true);
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            user.setFirstName("Urfan");
            user.setLastName("Beijlerbeijli");
            userRepository.save(user);

            return user;
        });
    }

    private void addPOwnerInitToken(User user, UrlTokenRepository urlTokenRepository){
        if (urlTokenRepository.findByUrlToken("rOzhaz4-q7NF-5U6Aa1wNZdX9xmA0COdIIwcINXFGU8MQuc") == null) {
            UrlToken urlToken = new UrlToken(user);
            urlToken.setUrlToken("rOzhaz4-q7NF-5U6Aa1wNZdX9xmA0COdIIwcINXFGU8MQuc");
            urlToken.setTimestamp(Instant.now());
            urlToken.setTokenExpireTime(LocalDateTime.of(2025, Month.OCTOBER, 25, 10, 10, 10));
            urlTokenRepository.save(urlToken);
        }
    }

    private void addAdminInitToken(User user, UrlTokenRepository urlTokenRepository){
        if (urlTokenRepository.findByUrlToken("z4oJ9Fm0-dIyG6PfgwU-ZckcAKW-ybVvuJgtF1pWgGbEt-s") == null) {
            UrlToken urlToken = new UrlToken(user);
            urlToken.setUrlToken("z4oJ9Fm0-dIyG6PfgwU-ZckcAKW-ybVvuJgtF1pWgGbEt-s");
            urlToken.setTimestamp(Instant.now());
            urlToken.setTokenExpireTime(LocalDateTime.of(2025, Month.OCTOBER, 25, 10, 10, 10));
            urlTokenRepository.save(urlToken);
        }
    }

    private void addPOwnerBusinessInfo(User user, BusinessInformationRepository businessInformationRepository){
        if (businessInformationRepository.findFirstByUser(user).isEmpty()) {
            BusinessInformation businessInformation = new BusinessInformation();
            businessInformation.setBusinessAddress("Groningen");
            businessInformation.setCompanyPostalCode("9728 NN");
            businessInformation.setCompanyStreetname("Semmelweisstraat 160");
            businessInformation.setCompanyTaxNumber("NL003713164B37");
            businessInformation.setCompanyBusinessNumber("82653747");
            businessInformation.setCompanyPhone("+31627045554");
            businessInformation.setCompanyURL("https://jrevolt.com");
            businessInformation.setUser(user);

            businessInformationRepository.save(businessInformation);
        }
    }

    @PostConstruct
    public void init() throws SQLException {
        log.info("Data Source : {}", dataSource.getConnection().getMetaData().getURL());
    }
}
