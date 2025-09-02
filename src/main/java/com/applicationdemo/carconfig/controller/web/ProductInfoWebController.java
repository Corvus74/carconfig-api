package com.applicationdemo.carconfig.controller.web;

import com.applicationdemo.carconfig.dto.web.ProductInfoDetailDto;

import com.applicationdemo.carconfig.dto.web.ProductInfoDto;
import com.applicationdemo.carconfig.services.ProductInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/productInfo")
public class ProductInfoWebController {

    private final ProductInfoService productInfoService;

    @PostMapping(path ="getInfo", consumes = "application/json", produces = "application/json")
    public ProductInfoDetailDto getAllProductByProductIds(@RequestBody ProductInfoDto productInfoDto) {
        log.debug("Getting all product information");
        return productInfoService.getProductDetailsByConfiguration(productInfoDto);
    }

}