package com.nastrsoft.commitChecks.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories("com.nastrsoft.commitChecks.repository")
@EnableTransactionManagement
public class DatabaseConfig {

}
