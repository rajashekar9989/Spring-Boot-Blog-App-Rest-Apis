package com.springboot.blog.controller;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name="CRUD Rest Apis for Post Resource"
)
public class PostController {


    private PostService postService;

    public PostController(PostService postService) {

        this.postService = postService;
    }


    // create new blog post Rest Api
    @Operation(
            summary = "Create Post Rest Api",
            description = "Create post Rest Api is Used to save new post in Database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created"
    )
    @SecurityRequirement(
            name="bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/newpost")
    public ResponseEntity<PostDto> createPost( @Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);

    }

    // get allPosts Rest Api
    @Operation(
            summary = "Get All  Posts  Rest Api",
            description = "Get All  posts Rest Api is Used to fetch All posts from  Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )


    @GetMapping("/allposts")
    public PostResponse getAllPosts(
            @RequestParam(value="pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEAFULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEAFULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value ="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false)String sortDir

            )
    {


       return  postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);

    }

    // get postById Rest Api
    @Operation(
            summary = "Get Post by Id   Rest Api",
            description = "Get post  By Id Rest Api is Used to get single post from  Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById( @PathVariable(name="id") long id){

     return  ResponseEntity.ok(postService.getPostById(id));
    }

    // update PostById Rest Api
    @Operation(
            summary = "Update  Post by Id  Rest Api",
            description = "Update  post  By Id Rest Api is Used to Update single post by using post id in  Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )

    @SecurityRequirement(
            name="bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name="id") long id){

        PostDto postResponse=postService.updatePost(postDto, id);

        return new ResponseEntity<>(postResponse,HttpStatus.OK);

    }



    // delete post by id Rest Api
    @Operation(
            summary = "Delete Post by Id   Rest Api",
            description = "Delete  post  By Id Rest Api is Used to Delete single post from  Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Success"
    )
    @SecurityRequirement(
            name="bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<String> deletePostById(@PathVariable(name = "id") Long id) {

        postService.deletePostById(id);

        return  new ResponseEntity<>("post Deleted Successfully",HttpStatus.OK);

    }

    //Build Get postS bY Category rest Api
    // http:localhost:8080/api/posts/category/1

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory( @PathVariable("id")Long categoryId){

        List<PostDto> postsDtos =postService.getPostByCategory(categoryId);

        return ResponseEntity.ok(postsDtos);
    }





}
