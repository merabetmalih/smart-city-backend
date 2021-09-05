package com.example.springmvcrest.product.service;

import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.api.mapper.ProductMapper;
import com.example.springmvcrest.product.domain.Category;
import com.example.springmvcrest.product.domain.Product;
import com.example.springmvcrest.product.repository.ProductRepository;
import com.example.springmvcrest.user.simple.domain.SimpleUser;
import com.example.springmvcrest.user.simple.service.SimpleUserService;
import lombok.AllArgsConstructor;
import org.hibernate.search.engine.search.common.BooleanOperator;
import org.hibernate.search.engine.search.predicate.dsl.PredicateFinalStep;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ProductSearchService {
    @PersistenceContext
    EntityManager entityManager;

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final SimpleUserService simpleUserService;
    private final int PAGE_SIZE = 10;

    @Transactional(readOnly = true)
    public List<ProductDTO> search(Long userId,String query,int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        SearchSession session = Search.session(entityManager);
        SearchScope<Product> scope = session.scope( Product.class );

        //exact tags
        SearchResult<Product> result = session.search(scope).where(
                f-> (PredicateFinalStep)
                        f.simpleQueryString()
                                .fields("tags.name")
                                .matching(query)
                                .defaultOperator(BooleanOperator.AND)
        ).sort( f -> f.score() ).fetch((pageable.getPageNumber()-1)*pageable.getPageSize(),pageable.getPageSize());

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
            ).sort( f -> f.score() ).fetch((pageable.getPageNumber()-1)*pageable.getPageSize(),pageable.getPageSize());
            System.out.println("exact names");

            if (result.total().hitCount()==0){
                //yel9a w 5lass b tags
                SearchResult<Product> result1 = session.search(scope).where(
                        f -> f.match()
                                .fields( "tags.name" )
                                .matching( query )
                                .fuzzy(1,3)
                                .analyzer("stop")

                ).sort( f -> f.score() ).fetch((pageable.getPageNumber()-1)*pageable.getPageSize(),pageable.getPageSize());

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


                ).sort( f -> f.score() ).fetch((pageable.getPageNumber()-1)*pageable.getPageSize(),pageable.getPageSize());

                if (result1.total().hitCount()>result2.total().hitCount()){
                    result=result1;
                    System.out.println("yel9a w 5lass b tags");
                }else {
                    System.out.println("yel9a w 5lass b name");
                    result=result2;
                }

            }
        }




        return new PageImpl<>(
                result.hits()
                        .stream()
                        .filter(product -> !product.getDeleted())
                        .filter(product -> isAround(userId,product))
                        .map(productMapper::ToDto)
                        .collect(Collectors.toList()),
                pageable,
                pageable.getPageSize()
        ).getContent();
    }

    private Boolean isAround(Long userId, Product product){
        double distance=12.0;
        SimpleUser user=simpleUserService.findById(userId);
        Double latitude=user.getDefaultCity().getLatitude();
        Double longitude=user.getDefaultCity().getLongitude();

        return distFrom(
                latitude,
                longitude,
                product.getCustomCategory().getStore().getStoreAddress().getLatitude(),
                product.getCustomCategory().getStore().getStoreAddress().getLongitude()) < distance;
    }

     private Double distFrom(Double lat1, Double lng1, Double lat2, Double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

         return  (earthRadius * c)/1000;
    }

    public List<ProductDTO> findProductAround(Long userId,int page) {
        double distance=12.0;
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        if(page>0){
            pageable = PageRequest.of(page-1, PAGE_SIZE);
        }

        SimpleUser user=simpleUserService.findById(userId);
        Double latitude=user.getDefaultCity().getLatitude();
        Double longitude=user.getDefaultCity().getLongitude();

        List<Long> productIds=productRepository.findProductAround(latitude,longitude,distance,pageable)
                .getContent()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        return productRepository.findAllById(productIds).stream()
                .filter(product -> getParentCategories(product.getCustomCategory().getStore().getDefaultCategories()).stream().anyMatch(getParentCategories(user.getInterestCenter())::contains))
                .map(productMapper::ToDto)
                .collect(Collectors.toList());
    }

    private Set<Category> getParentCategories(Set<Category> subCategories){
        return subCategories.stream()
                .map(Category::getParentCategory)
                .collect(Collectors.toSet());
    }

    public List<ProductDTO> findProductAroundMayInterest(Long userId,int page) {
        double distance=12.0;
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        if(page>0){
            pageable = PageRequest.of(page-1, PAGE_SIZE);
        }

        SimpleUser user=simpleUserService.findById(userId);
        Double latitude=user.getDefaultCity().getLatitude();
        Double longitude=user.getDefaultCity().getLongitude();

        List<Long> productIds=productRepository.findProductAround(latitude,longitude,distance,pageable)
                .getContent()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        return productRepository.findAllById(productIds).stream()
                .filter(product -> product.getCustomCategory().getStore().getDefaultCategories().stream().anyMatch(user.getInterestCenter()::contains))
                .map(productMapper::ToDto)
                .collect(Collectors.toList());
    }
}
