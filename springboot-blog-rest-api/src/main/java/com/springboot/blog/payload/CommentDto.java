package com.springboot.blog.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private long id;
    // name should not be Empty
    @NotEmpty(message = "Name should not be Empty or null")
    private String name;

    // name should not be empty
    // check email format
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    // comment body should not be null or empty
    // comment body should have at least 4 characters
    @NotEmpty
    @Size(min = 4,message = "comment body should have at least 4 characters")
    private String comment;

}
