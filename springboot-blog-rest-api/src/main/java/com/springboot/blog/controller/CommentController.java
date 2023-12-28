package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(
        name="CRUD Rest Apis for Comment Resource"
)
public class CommentController {


    private CommentService commentService;

    public CommentController(CommentService commentService) {


        this.commentService = commentService;
    }
//  Create Comment Rest Api
@Operation(
        summary = "Create Comment Rest Api",
        description = "Create Comment Rest Api is Used to Create new Comment to particular post id in Database"
)
@ApiResponse(
        responseCode = "201",
        description = "Http Status 201 Created"
)

    @PostMapping("/posts/{postId}/create/comment")
    public ResponseEntity<CommentDto> createComment(  @PathVariable(value = "postId") long postId,
                                                  @Valid @RequestBody CommentDto commentDto) {


        return new ResponseEntity<>(commentService.createCommet(postId, commentDto), HttpStatus.CREATED);
    }

       // Build get all comments on specific post id rest Api
    @Operation(
            summary = "Get All comments on Post id  Rest Api",
            description = "get All Comments Rest Api is Used to fetch All comments on  post id  in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 oK"
    )

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable(value = "postId") long postId) {


        return commentService.getCommentsByPostId(postId);
    }

    // get comments by id Rest api

    @Operation(
            summary = "Get comment by comment Id on specific  Post Id  Rest Api",
            description = "Get Comment  Rest Api is Used to fetch specific comment on specific post id in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Ok"
    )
    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(value ="postId") Long postId,

            @PathVariable(value ="commentId")  Long commentId){

        CommentDto  commentDto = commentService.getCommentById(postId,commentId);

        return  new ResponseEntity<>(commentDto,HttpStatus.OK);


    }

    // get update comment by  comment id Rest Api
    @Operation(
            summary = "Update comment by comment Id on specific  Post Id  Rest Api",
            description = "Update Comment  Rest Api is Used to update specific comment on specific post id in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Ok"
    )

     @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(
              @PathVariable(value="postId") Long postId,
              @PathVariable(value ="commentId") Long commentId,
            @Valid @RequestBody CommentDto commentDto){

         CommentDto updatedComment =commentService.updateCommentById(postId,commentId,commentDto);

        return  new ResponseEntity<>(updatedComment,HttpStatus.OK);


    }

    // get delete comment by id  rest Api

    @Operation(
            summary = "Delete comment by comment Id on specific  Post Id  Rest Api",
            description = "Delete Comment  Rest Api is Used to delete specific comment on specific post id in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 Ok"
    )

    @DeleteMapping("/posts/{postId}/delete/{commentId}")
    public  ResponseEntity<String>  deleteCommentById(  @PathVariable(value="postId") Long postId,

                                     @PathVariable(value="commentId")  Long commentId){

        commentService.deleteCommentById(postId, commentId);


        return  new ResponseEntity<>("Comment deleted Succesfully",HttpStatus.OK);

    }



}

