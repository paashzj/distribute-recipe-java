package com.github.shoothzj.distribute.impl.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author hezhangjian
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.github.shoothzj.distribute.impl.jpa.repo")
@EntityScan(basePackages = {"com.github.shoothzj.sdk.distribute.impl.jpa.domain"})
public class JpaDistributeConfiguration {
}
