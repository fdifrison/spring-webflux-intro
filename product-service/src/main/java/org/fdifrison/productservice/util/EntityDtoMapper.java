package org.fdifrison.productservice.util;

import org.fdifrison.productservice.dto.ProductDto;
import org.fdifrison.productservice.entity.Product;
import org.springframework.beans.BeanUtils;

public class EntityDtoMapper {

    public static ProductDto toDto(Product product) {
        return new ProductDto(product.getId(), product.getDescription(), product.getPrice());
    }

    public static Product toEntity(ProductDto dto) {
        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

}
