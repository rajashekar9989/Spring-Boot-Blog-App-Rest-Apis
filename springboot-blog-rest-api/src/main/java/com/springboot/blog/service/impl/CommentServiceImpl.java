package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl  implements CommentService {

    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

   private PostRepository postRepository;
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,
                              ModelMapper modelMapper) {

        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
        this.modelMapper=modelMapper;
    }


    // create Comment REST API
    @Override
    public CommentDto createCommet(long postId, CommentDto commentDto) {

         Comment comment = mapToEntity(commentDto);

         //retriev post entity by id
        Post post  =postRepository.findById(postId).orElseThrow(

                ()->  new ResourceNotFoundException("post","id",postId));

        //set post to comment entity
        comment.setPost(post);

        // save comment entity to database
        Comment newComment =commentRepository.save(comment);
        return   maptoDto(newComment) ;

    }
       // get all comments Rest Api
    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        // retriev coemmnts by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        // convert list of comment entities to list of comment Dto's

        return  comments.stream().map(comment->maptoDto(comment)).collect(Collectors.toList());


    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

        Post post  =postRepository.findById(postId).orElseThrow(

                ()-> new ResourceNotFoundException("post","id",postId));

        // retreive comment by commentId

        Comment comment = commentRepository.findById(commentId).orElseThrow(

                ()-> new ResourceNotFoundException("Comment","id",commentId));


        if(!comment.getPost().getId().equals(post.getId())){

            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belogs to post");
        }

        return maptoDto(comment);
    }

    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentRequest) {

        Post post  =postRepository.findById(postId).orElseThrow(

                ()-> new ResourceNotFoundException("post","id",postId));

        // retreive comment by commentId

        Comment comment = commentRepository.findById(commentId).orElseThrow(

                ()-> new ResourceNotFoundException("Comment","id",commentId));


        if(!comment.getPost().getId().equals(post.getId())){

            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belogs to post");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setComment(commentRequest.getComment());

        commentRepository.save(comment);


        return maptoDto(comment);
    }


    // delete comment by comment Id for particular post

    @Override
    public void deleteCommentById(Long postId, Long commentId) {

        //retrieve Post Entity by using postId

        Post post  =postRepository.findById(postId).orElseThrow(

                ()-> new ResourceNotFoundException("post","id",postId));

        // retreive comment by commentId

        Comment comment = commentRepository.findById(commentId).orElseThrow(

                ()-> new ResourceNotFoundException("Comment","id",commentId));


        if(!comment.getPost().getId().equals(post.getId())){

            throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belogs to post");
        }

         commentRepository.delete(comment);

    }


    // map dto to entity


    private CommentDto maptoDto(Comment comment) {

        CommentDto commentDto = modelMapper.map(comment,CommentDto.class);


//        CommentDto commentDto = new CommentDto();
//
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;


    }

    // map entity to dto

    private Comment mapToEntity(CommentDto commentDto){

        Comment comment = modelMapper.map(commentDto,Comment.class);

//        Comment comment = new Comment();
//
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());

        return comment;

    }



}



