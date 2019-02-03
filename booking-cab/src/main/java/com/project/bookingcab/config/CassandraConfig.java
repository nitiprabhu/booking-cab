package com.project.bookingcab.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * @author nitish on 2019-02-02.
 */
@Configuration
@EnableCassandraRepositories
public class CassandraConfig {
  @Value("${cassandra.address}")
  private String cassandraAddress;
  @Value("${cassandra.port}")
  private String cassandraPort;
  @Value("${keyspace.name:carbooking}")
  private String keyspaceName;

  @Bean
  public CassandraOperations cassandraTemplate() {
    return new CassandraTemplate(session().getObject());
  }

  @Bean
  public CassandraSessionFactoryBean session() {
    CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
    session.setCluster(cluster().getObject());
    session.setKeyspaceName(keyspaceName);//keyspace defined
    session.setConverter(converter());
    session.setSchemaAction(SchemaAction.NONE);
    return session;
  }

  @Bean
  public CassandraClusterFactoryBean cluster() {
    CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
    cluster.setContactPoints(cassandraAddress); //ip address of the cassandra
    cluster.setPort(Integer.parseInt(cassandraPort)); //port of the cassandra running
    return cluster;
  }

  @Bean
  public CassandraConverter converter() {
    return new MappingCassandraConverter(mappingContext());
  }

  @Bean
  public CassandraMappingContext mappingContext() {
    return new BasicCassandraMappingContext();
  }
}
