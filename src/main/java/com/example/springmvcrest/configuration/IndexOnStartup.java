package com.example.springmvcrest.configuration;

import com.example.springmvcrest.domain.Product;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class IndexOnStartup implements CommandLineRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public void run(String... args) throws Exception {
        SearchSession searchSession = Search.session( entityManager );
        MassIndexer indexer = searchSession.massIndexer( Product.class );
        indexer.startAndWait();

    }
}