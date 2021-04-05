/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.util;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;

/**
 *
 * @author plintus
 */
@Singleton
@Startup
public class FlywayMigration {
    @Resource(lookup = "java:jboss/datasources/testDS")
    private DataSource dataSource;

    @PostConstruct
    public void initFlyWay() {
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();

        //flyway.clean();
        flyway.baseline();
        flyway.migrate();
    }
}
