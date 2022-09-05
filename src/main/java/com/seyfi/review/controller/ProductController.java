package com.seyfi.review.controller;

import com.seyfi.review.model.entity.Product;
import com.seyfi.review.model.entity.Vote;
import com.seyfi.review.model.response.GeneralResponse;
import com.seyfi.review.service.ProductService;
import com.seyfi.review.service.VoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@RestController
@RequestMapping("product")
public class ProductController {

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping()
    public ResponseEntity<GeneralResponse> create(@Valid @RequestBody Product product)
            throws Exception {
        logger.info("Request for creating a product : "+product.toString());
        GeneralResponse generalResponse = productService.create(product);
        logger.info("Response : "+ generalResponse.toString());
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> retrieve(@Positive(message = "id should be a positive number")
                                                    @NotNull(message = "id can't be null")
                                                        @PathVariable Integer id)
            throws Exception {
        logger.info("Request for retrieve a product : "+ id.toString());
        GeneralResponse generalResponse = productService.retrieve(id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> delete(@Positive(message = "id should be a positive number")
                                                    @NotNull(message = "id can't be null")
                                                    @PathVariable Integer id)
            throws Exception {
        logger.info("Request for delete a product : "+ id.toString());
        GeneralResponse generalResponse = productService.delete(id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }


    @GetMapping("/list")
    public ResponseEntity<GeneralResponse> list()
            throws Exception {
        logger.info("Request for retrieve all products");
        GeneralResponse generalResponse = productService.list();
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

}
