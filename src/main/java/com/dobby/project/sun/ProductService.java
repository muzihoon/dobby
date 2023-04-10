package com.dobby.project.sun;

import java.util.List;

public interface ProductService {
    List<ProductDto> getMainProducts();
    List<ProductDCDto> Products_DC() throws Exception;
    List<ProductDto> getSkincareProducts();
    List<ProductDto> getMakeupProducts();
    List<ProductDto> getMenProducts();
    List<ProductDto> getHairBodyProducts();

}