package com.seyfi.review.controller;

import com.seyfi.review.model.request.CreateProductDetailDto;
import com.seyfi.review.model.request.UpdateProductDetailDto;
import com.seyfi.review.model.response.GeneralResponse;
import com.seyfi.review.service.ProductDetailService;
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
@RequestMapping("product-detail")
public class ProductDetailController {

    private static final Logger logger = LogManager.getLogger(ProductDetailController.class);

    @Autowired
    private ProductDetailService productDetailService;

    @PostMapping()
    public ResponseEntity<GeneralResponse> create(@Valid @RequestBody CreateProductDetailDto createProductDetailDto)
            throws Exception {
        logger.info("Request for creating a productDetail : "+ createProductDetailDto.toString());
        GeneralResponse generalResponse = productDetailService.create(createProductDetailDto);
        logger.info("Response : "+ generalResponse.toString());
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GeneralResponse> update(@Valid @RequestBody UpdateProductDetailDto updateProductDetailDto,
                                                  @Positive(message = "id should be a positive number")
                                                  @NotNull(message = "id can't be null")
                                                  @PathVariable Long id)
            throws Exception {
        logger.info("Request for updating a productDetail : "+ id.toString());
        GeneralResponse generalResponse = productDetailService.update(updateProductDetailDto, id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> retrieve(@Positive(message = "id should be a positive number")
                                                    @NotNull(message = "id can't be null")
                                                        @PathVariable Long id)
            throws Exception {
        logger.info("Request for retrieve a productDetail : "+ id.toString());
        GeneralResponse generalResponse = productDetailService.retrieve(id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> delete(@Positive(message = "id should be a positive number")
                                                    @NotNull(message = "id can't be null")
                                                    @PathVariable Long id)
            throws Exception {
        logger.info("Request for delete a productDetail : "+ id.toString());
        GeneralResponse generalResponse = productDetailService.delete(id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }


    @GetMapping("/list")
    public ResponseEntity<GeneralResponse> list(@Positive(message = "size should be a positive number")
                                                    @NotNull(message = "size can't be null")
                                                    @RequestParam Integer size,
                                                @Positive(message = "sync should be a positive number")
                                                    @NotNull(message = "sync can't be null")
                                                    @RequestParam Long sync)
            throws Exception {
        logger.info("Request for retrieve all productDetails");
        GeneralResponse generalResponse = productDetailService.list(size, sync);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}/review")
    public ResponseEntity<GeneralResponse> review(@Positive(message = "id should be a positive number")
                                                    @NotNull(message = "id can't be null")
                                                    @PathVariable Long id)
            throws Exception {
        logger.info("Request for retrieve a productDetail review : "+ id.toString());
        GeneralResponse generalResponse = productDetailService.review(id);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<GeneralResponse> comments(@Positive(message = "id should be a positive number")
                                                        @NotNull(message = "id can't be null")
                                                        @PathVariable Long id,
                                                        @Positive(message = "size should be a positive number")
                                                        @NotNull(message = "size can't be null")
                                                        @RequestParam Integer size,
                                                        @Positive(message = "sync should be a positive number")
                                                        @NotNull(message = "sync can't be null")
                                                        @RequestParam Long sync)
            throws Exception {
        logger.info("Request for retrieve all productDetail comments");
        GeneralResponse generalResponse = productDetailService.comments(id, size, sync);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }

    @GetMapping("{id}/votes")
    public ResponseEntity<GeneralResponse> votes(@Positive(message = "id should be a positive number")
                                                    @NotNull(message = "id can't be null")
                                                    @PathVariable Long id,
                                                    @Positive(message = "size should be a positive number")
                                                    @NotNull(message = "size can't be null")
                                                    @RequestParam Integer size,
                                                    @Positive(message = "sync should be a positive number")
                                                    @NotNull(message = "sync can't be null")
                                                    @RequestParam Long sync)
            throws Exception {
        logger.info("Request for retrieve all productDetail votes");
        GeneralResponse generalResponse = productDetailService.votes(id, size, sync);
        return new ResponseEntity<>(generalResponse, HttpStatus.OK);
    }


}
