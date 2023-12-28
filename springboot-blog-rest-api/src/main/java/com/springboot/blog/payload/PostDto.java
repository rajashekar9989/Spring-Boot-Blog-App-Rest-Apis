package com.springboot.blog.payload;

import com.springboot.blog.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema(

        description = "PostDto Model Information"
)
public class PostDto {

    private long id;
    // title should not be  null or empty
    // title should  have at least  2 characters
    @Schema(
            description = "blog post title"
    )
    @NotEmpty
    @Size(min = 2, message = "post title should have at least 2 characters")
    private String title;
    // description should not be  null or empty
    // description should  have at least  2 characters
    @Schema(
            description = "blog post description"
    )
    @NotEmpty
    @Size(min=10,message = "post description should have at least 10 characters")
    private String description;
    // post content should not be empty and null

    @Schema(
            description = "blog post content"
    )
    @NotEmpty
    private String content;

    private Set<CommentDto> comments;

    @Schema(
            description = " this blog post belongs to which category "
    )
    private Long categoryId;


}
