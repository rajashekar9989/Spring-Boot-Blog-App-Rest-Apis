package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {



    CommentDto createCommet(long postId, CommentDto commentDto);


  List<CommentDto> getCommentsByPostId(long postId);

  CommentDto getCommentById(Long postId, Long commentId);

CommentDto updateCommentById(Long postId,Long CommentId,CommentDto commentRequest);

void deleteCommentById( Long postId,Long CommentId);

}
