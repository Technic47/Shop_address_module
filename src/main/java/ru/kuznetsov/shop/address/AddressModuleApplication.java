package ru.kuznetsov.shop.address;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.kuznetsov.shop.data.config.SpringConfig;

@SpringBootApplication
@Import(SpringConfig.class)
public class AddressModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddressModuleApplication.class, args);
    }

}
