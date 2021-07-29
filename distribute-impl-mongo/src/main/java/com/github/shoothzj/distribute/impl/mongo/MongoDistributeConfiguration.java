package com.github.shoothzj.distribute.impl.mongo;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author hezhangjian
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.github.shoothzj.distribute.impl.mongo.repo")
public class MongoDistributeConfiguration {
}
