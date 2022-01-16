package com.example.springmvcrest;


import com.example.springmvcrest.product.domain.*;
import com.example.springmvcrest.product.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
@AllArgsConstructor
public class startup  implements ApplicationListener<ContextRefreshedEvent> {
   private ImagesRepository imagesRepository;
    private ProductTypeRepository productTypeRepository;
    private ProductOptionRepository productOptionRepository;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

        @Override public void onApplicationEvent(ContextRefreshedEvent event) {


            /*


            ProductType productType = productTypeRepository.getOne(5L);
           // System.out.printf(productType.getProperties().toString());
            for (Properties pro:productType.getProperties()
                 ) {
                System.out.println(pro.toString());
*/

            Category cato =Category.builder()
                 //   .id(1L)
                    .name("phones").build();
            cato=categoryRepository.save(cato);

            ProductOption option1 =ProductOption.builder()
            //        .id(1L)
                    .name("size")
                    .listValues("s m l").
                    build();
            option1=productOptionRepository.save(option1);

            ProductOption option2 =ProductOption.builder()
             //       .id(2L)
                    .name("color")
                    .listValues("Red black Green").
                    build();
            option2=productOptionRepository.save(option2);


            Set<ProductOption> options = new HashSet<>();
            options.add(option1);
            options.add(option2);

            Product produ= Product.builder()
                   // .id(1L)
                    .name("iphone").
                    deleted(false)
                    .category(cato)
                    .description("smart phones")
                    .productOption(options)
                    .build();


            Set<Properties> proper = new HashSet<>();
            proper.add(Properties.builder().name("color").Value("red")
                    .build());
            proper.add(Properties.builder().name("size").Value("S")
                    .build());
            Images sora =
                    imagesRepository.save(Images.builder().image("ds").build());

            ProductType ism = ProductType.builder()
             //       .id(1L)
                    .price(14.0)
                    .unit(10)
                    .images(sora)
                    .properties(proper).build();
                    productTypeRepository.save(ism);
            produ=productRepository.save(produ);

            }
        }


