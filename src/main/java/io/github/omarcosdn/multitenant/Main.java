package io.github.omarcosdn.multitenant;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    final var app = new SpringApplication(Main.class);
    app.setBannerMode(Mode.OFF);
    app.run(args);
  }

  @PostConstruct
  void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

}
