package com.sursindmitry.repairhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RepairHubApplication {

  public static void main(String[] args) {
    SpringApplication.run(RepairHubApplication.class, args);
  }

}
