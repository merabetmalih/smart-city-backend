package com.example.springmvcrest.services;

import com.example.springmvcrest.domain.Product;
import com.example.springmvcrest.repositories.ProductRepository;
import org.apache.lucene.search.Explanation;
import org.hibernate.search.backend.lucene.LuceneExtension;
import org.hibernate.search.backend.lucene.search.query.LuceneSearchQuery;
import org.hibernate.search.engine.search.common.BooleanOperator;
import org.hibernate.search.engine.search.predicate.dsl.PredicateFinalStep;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.StringTokenizer;


@Service
public class ProductSearchService {
    @PersistenceContext
    EntityManager entityManager;

    private final ProductRepository productRepository;

    public ProductSearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Transactional(readOnly = true)
    public List<Product> search(String query) {
        SearchSession session = Search.session(entityManager);

        SearchScope<Product> scope = session.scope( Product.class );

        //exact tags
        SearchResult<Product> result = session.search(scope).where(
                f-> (PredicateFinalStep)
                        f.simpleQueryString()
                                .fields("tags.name")
                                .matching(query)
                                .defaultOperator(BooleanOperator.AND)
        ).sort( f -> f.score() ).fetchAll();

        if(result.total().hitCount()==0){

            //exact names
            result = session.search(scope).where(
                    f-> (PredicateFinalStep)
                            f.simpleQueryString()
                                    .fields("description", "name")
                                    .matching(query)
                                    .defaultOperator(BooleanOperator.AND)
                    // .fuzzy(1)
                    //.analyzer("stop")
                    //.toPredicate()
            ).sort( f -> f.score() ).fetchAll();
            System.out.println("exact names");

            if (result.total().hitCount()==0){
                //yel9a w 5lass b tags
                SearchResult<Product> result1 = session.search(scope).where(
                        f -> f.match()
                                .fields( "color","tags.name" )
                                .matching( query )
                                .fuzzy(1,3)
                                .analyzer("stop")

                ).sort( f -> f.score() ).fetchAll();

                //yel9a w 5lass b name
                SearchResult<Product> result2 = session.search(scope).where(
                        f-> (PredicateFinalStep)
                                f.bool()
                                        .filter(
                                                f.match()
                                                        .fields("description", "name")
                                                        .matching(query)
                                                //.analyzer("stop")
                                        )


                ).sort( f -> f.score() ).fetchAll();

                if (result1.total().hitCount()>result2.total().hitCount()){
                    result=result1;
                    System.out.println("yel9a w 5lass b tags");
                }else {
                    System.out.println("yel9a w 5lass b name");
                    result=result2;
                }

            }
        }



        return result.hits();
    }

    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }
}
