package com.example.springmvcrest.product.service;

import com.example.springmvcrest.product.api.dto.ProductDTO;
import com.example.springmvcrest.product.api.mapper.ProductMapper;
import com.example.springmvcrest.product.domain.*;
import com.example.springmvcrest.product.repository.*;
import com.example.springmvcrest.storage.FileStorage;
import com.example.springmvcrest.storage.FileStorageException;
import com.example.springmvcrest.store.service.CustomCategoryService;
import com.example.springmvcrest.store.service.exception.CustomCategoryNotFoundExeption;
import com.example.springmvcrest.utils.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private final CustomCategoryService customCategoryService;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductVariantRepository productVariantRepository;
    private final ImagesRepository imagesRepository;
    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final ProductVariantAttributeValueRepository productVariantAttributeValueRepository;

    private final FileStorage fileStorage;


    public List<ProductDTO> getProductByCustomCategoryId(Long id){
        return productRepository.findAllByCustomCategory_Id(id)
                .stream()
                .filter(product -> !product.getDeleted())
                .map(productMapper::ToDto)
                .collect(Collectors.toList());
    }

    public  List<ProductDTO> getProductByCustomCategoryStoreProviderId(Long id){
        return productRepository.findAllByCustomCategory_Store_Provider_Id(id)
                .stream()
                .filter(product -> !product.getDeleted())
                .map(productMapper::ToDto)
                .collect(Collectors.toList());
    }

    public  List<ProductDTO> getProductByCustomCategoryStoreId(Long id){
        return productRepository.findAllByCustomCategory_StoreId(id)
                .stream()
                .filter(product -> !product.getDeleted())
                .map(productMapper::ToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Response<String> deleteProduct(Long id)
    {
        Product product=productRepository.findById(id)
                .orElseThrow(CustomCategoryNotFoundExeption::new);
        product.setDeleted(true);
        productRepository.save(product);
        return new Response<>("deleted.");
    }

    public ProductDTO updateProduct(ProductDTO productDTO,List<MultipartFile>  productImages,List<MultipartFile>  variancesImages){



      if(productRepository.findById(productDTO.getId()).isPresent()){

          deleteProduct(productDTO.getId());
          if(productImages!=null){
              saveProductImages(productImages);
          }
          Map<String,MultipartFile> variancesImagesMap = new HashMap<String,MultipartFile>();
          if (variancesImages!=null){
              for (MultipartFile image:variancesImages
              ) {
                  variancesImagesMap.put(image.getOriginalFilename(),image);
              }
          }

          return Optional.of(productDTO)
                  .map(productMapper::ToModel)
                  .map(product -> {


                      Set<Attribute> savedAttribute = product.getAttributes()
                              .stream()
                              .map(attribute -> attributeRepository.save( Attribute.builder()
                                      .name(attribute.getName())
                                      .build()))
                              .collect(Collectors.toSet());


                      Set<AttributeValue> savedAttributeValue = new HashSet<AttributeValue>();
                      for (Attribute attribute:product.getAttributes()
                      ) {
                          for (AttributeValue attributeValue:attribute.getAttributeValues()
                          ) {
                              savedAttributeValue.add(attributeValueRepository.save(
                                      AttributeValue.builder()
                                              .value(attributeValue.getValue())
                                              .attribute(
                                                      savedAttribute.stream()
                                                              .filter(saved -> saved.getName().equals(attribute.getName()))
                                                              .findFirst()
                                                              .get()
                                              )
                                              .build()));
                          }
                      }


                      List<ProductVariant> savedProductVariant = new ArrayList<>();
                      for (ProductVariant productVariant:product.getProductVariants()
                      ) {

                          List<ProductVariantAttributeValue> productVariantAttributeValue = new ArrayList<>();

                          for (ProductVariantAttributeValue productVariantAttributeValue1:productVariant.getProductVariantAttributeValuesProductVariant()
                          ) {


                              List<ProductVariantAttributeValue> savedProductVariantAttributeValue = new ArrayList<>();
                              savedProductVariantAttributeValue.add(
                                      productVariantAttributeValueRepository.save(
                                              ProductVariantAttributeValue.builder()
                                                      .attributeValue(
                                                              savedAttributeValue
                                                                      .stream()
                                                                      .filter(attributeValue -> attributeValue.getValue().equals(productVariantAttributeValue1.getAttributeValue().getValue()))
                                                                      .findFirst()
                                                                      .get())
                                                      .build())
                              );



                              for (ProductVariantAttributeValue productVariantAttributeValue2:savedProductVariantAttributeValue
                              ) {

                                  if (productVariantAttributeValue1.getAttributeValue().getValue().equals(productVariantAttributeValue2.getAttributeValue().getValue())){
                                      productVariantAttributeValue.add(productVariantAttributeValue2);
                                  }
                              }
                          }


                          if (!variancesImagesMap.isEmpty()){
                              saveVarianceImage(variancesImagesMap.get(productVariant.getImage()));
                          }

                          ProductVariant productVariantItemSaved=productVariantRepository.save(
                                  ProductVariant.builder()
                                          .image(productVariant.getImage())
                                          .price(productVariant.getPrice())
                                          .unit(productVariant.getUnit())
                                          .productVariantAttributeValuesProductVariant(productVariantAttributeValue)
                                          .build()
                          );

                          System.out.println("**********"+productVariantItemSaved.toString());
                          for (ProductVariantAttributeValue productVariantAttributeValue1:productVariantAttributeValue
                          ) {
                              productVariantAttributeValue1.setProductVariant(productVariantItemSaved);
                              productVariantAttributeValueRepository.save(productVariantAttributeValue1);
                          }

                          savedProductVariant.add(
                                  productVariantItemSaved
                          );


                      }






                      Product savedProduct = productRepository.save(Product.builder()
                              //.id(product.getId())
                              .name(product.getName())
                              .description(product.getDescription())
                              .tags(product.getTags())
                              .productVariants(savedProductVariant)
                              .attributes(savedAttribute)
                              .images(product.getImages())
                              .deleted(false)
                              .customCategory(customCategoryService.findById(product.getCustomCategory().getId()))
                              .build());




                          List<Images> images = product.getImages().stream()
                                  .map(Images::getImage)
                                  .map(image -> Images.builder()
                                          .image(image)
                                          .build()).collect(Collectors.toList());
                          for (Images image:images
                          ) {

                              image.setProduct(savedProduct);
                              imagesRepository.save(image);
                          }




                      for (ProductVariant productVariant :savedProductVariant
                      ) {
                          productVariant.setProduct(savedProduct);
                          productVariantRepository.save(productVariant);
                      }


                      return savedProduct;

                  })
                  .map(productMapper::ToDto)
                  .orElse(null);
      }
      else return null;
    }

    public byte[] downloadImage(String filename) {
        return fileStorage.download(filename, "smartCity-files");
    }

    public ProductDTO create(ProductDTO productDTO,List<MultipartFile>  productImages,List<MultipartFile>  variancesImages) {
        System.out.println(productDTO.toString());

        if(productImages!=null){
            saveProductImages(productImages);
        }
        Map<String,MultipartFile> variancesImagesMap = new HashMap<String,MultipartFile>();
        if (variancesImages!=null){
            for (MultipartFile image:variancesImages
            ) {
                variancesImagesMap.put(image.getOriginalFilename(),image);
            }
        }


        return Optional.of(productDTO)
                .map(productMapper::ToModel)
                .map(product -> {


                    Set<Attribute> savedAttribute = product.getAttributes()
                            .stream()
                            .map(attribute -> attributeRepository.save( Attribute.builder()
                                    .name(attribute.getName())
                                    .build()))
                            .collect(Collectors.toSet());


                    Set<AttributeValue> savedAttributeValue = new HashSet<AttributeValue>();
                    for (Attribute attribute:product.getAttributes()
                    ) {
                        for (AttributeValue attributeValue:attribute.getAttributeValues()
                        ) {
                            savedAttributeValue.add(attributeValueRepository.save(
                                    AttributeValue.builder()
                                            .value(attributeValue.getValue())
                                            .attribute(
                                                    savedAttribute.stream()
                                                            .filter(saved -> saved.getName().equals(attribute.getName()))
                                                            .findFirst()
                                                            .get()
                                            )
                                            .build()));
                        }
                    }


                    List<ProductVariant> savedProductVariant = new ArrayList<>();
                    for (ProductVariant productVariant:product.getProductVariants()
                    ) {

                        List<ProductVariantAttributeValue> productVariantAttributeValue = new ArrayList<>();

                        for (ProductVariantAttributeValue productVariantAttributeValue1:productVariant.getProductVariantAttributeValuesProductVariant()
                        ) {


                            List<ProductVariantAttributeValue> savedProductVariantAttributeValue = new ArrayList<>();
                                savedProductVariantAttributeValue.add(
                                        productVariantAttributeValueRepository.save(
                                                ProductVariantAttributeValue.builder()
                                                        .attributeValue(
                                                                savedAttributeValue
                                                                        .stream()
                                                                        .filter(attributeValue -> attributeValue.getValue().equals(productVariantAttributeValue1.getAttributeValue().getValue()))
                                                                        .findFirst()
                                                                        .get())
                                                        .build())
                                );



                            for (ProductVariantAttributeValue productVariantAttributeValue2:savedProductVariantAttributeValue
                            ) {

                                if (productVariantAttributeValue1.getAttributeValue().getValue().equals(productVariantAttributeValue2.getAttributeValue().getValue())){
                                    productVariantAttributeValue.add(productVariantAttributeValue2);
                                }
                            }
                        }


                        if (!variancesImagesMap.isEmpty()){
                            saveVarianceImage(variancesImagesMap.get(productVariant.getImage()));
                        }

                        ProductVariant productVariantItemSaved=productVariantRepository.save(
                                ProductVariant.builder()
                                        .image(productVariant.getImage())
                                        .price(productVariant.getPrice())
                                        .unit(productVariant.getUnit())
                                        .productVariantAttributeValuesProductVariant(productVariantAttributeValue)
                                        .build()
                        );

                        System.out.println("**********"+productVariantItemSaved.toString());
                        for (ProductVariantAttributeValue productVariantAttributeValue1:productVariantAttributeValue
                             ) {
                            productVariantAttributeValue1.setProductVariant(productVariantItemSaved);
                             productVariantAttributeValueRepository.save(productVariantAttributeValue1);
                        }

                        savedProductVariant.add(
                                productVariantItemSaved
                        );


                    }






                    Product savedProduct = productRepository.save(Product.builder()
                            .name(product.getName())
                            .description(product.getDescription())
                            .tags(product.getTags())
                            .productVariants(savedProductVariant)
                            .attributes(savedAttribute)
                            .images(product.getImages())
                            .deleted(false)
                            .customCategory(customCategoryService.findById(product.getCustomCategory().getId()))
                            .build());



                    List<Images> images = product.getImages().stream()
                            .map(Images::getImage)
                            .map(image -> Images.builder()
                                    .image(image)
                                    .build()).collect(Collectors.toList());
                    for (Images image:images
                    ) {

                        image.setProduct(savedProduct);
                        imagesRepository.save(image);
                    }



                    for (ProductVariant productVariant :savedProductVariant
                            ) {
                        productVariant.setProduct(savedProduct);
                        productVariantRepository.save(productVariant);
                    }


                    return savedProduct;

                })
                .map(productMapper::ToDto)
                .orElse(null);
    }


    private void saveVarianceImage(MultipartFile image) {
        try {
                if (image != null) {
                     fileStorage.upload(image.getOriginalFilename(),
                            "smartCity-files", image.getInputStream());
                }

        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("error.file.upload");
        }
    }
    private void saveProductImages(List<MultipartFile> images) {
        try {
            for (MultipartFile image:images
            ) {
                if (image != null) {
                    fileStorage.upload(image.getOriginalFilename(),
                            "smartCity-files", image.getInputStream());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("error.file.upload");
        }
    }

}
