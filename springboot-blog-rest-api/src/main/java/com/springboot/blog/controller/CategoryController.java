package com.springboot.blog.controller;


import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(
        name="CRUD Rest Apis for Category Resource"
)
public class CategoryController {


     private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //Build Add category REST API
    @Operation(
            summary = "  Save Category  Rest Api",
            description = "save new  Category Rest Api is Used to add new Category into Database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto ){

        CategoryDto savedCategory =categoryService.addCategory(categoryDto);

        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);



    }

    //Build Get Category REST API

    @Operation(
            summary = "  Get Category  by Category Id  Rest Api",
            description = " Get Category Rest Api is Used to fetch the Category by using category id from Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory( @PathVariable("id") Long categoryId){

        CategoryDto categoryDto= categoryService.getCategory(categoryId);

        return   new ResponseEntity<>(categoryDto,HttpStatus.OK);


    }
    //Build get All categories REST API
    @Operation(
            summary = "  Get All Category   Rest Api",
            description = " Get  All Category Rest Api is Used to fetch the  All Categories from Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){

        List<CategoryDto> allCategories =categoryService.getAllcategories();

       return  new ResponseEntity<>(allCategories,HttpStatus.OK);
    }

    //Build update Category REST API
    @Operation(
            summary = "  Update  Category  by Category Id  Rest Api",
            description = " update Category Rest Api is Used to fetch the Category by using category id from Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory( @RequestBody CategoryDto categoryDto,
                                                       @PathVariable("id") Long categoryId
                                                       ){
        return  ResponseEntity.ok(categoryService.updateCategory(categoryDto,categoryId));


    }

    // Build Delete Rest Api
    @Operation(
            summary = "  Delete Category  by Category Id  Rest Api",
            description = " Delete Category Rest Api is Used to Delete the specific Category by using category id in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public  ResponseEntity<String> deleteCategory( @PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return  ResponseEntity.ok("Category Deleted Successfully");


    }


}
