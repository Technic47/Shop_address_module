package ru.kuznetsov.shop.address;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import ru.kuznetsov.shop.data.config.SpringConfig;
import ru.kuznetsov.shop.parameter.config.ParameterConfig;

@SpringBootApplication
@EnableDiscoveryClient
@Import({SpringConfig.class, ParameterConfig.class})
public class AddressModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddressModuleApplication.class, args);
    }

}
