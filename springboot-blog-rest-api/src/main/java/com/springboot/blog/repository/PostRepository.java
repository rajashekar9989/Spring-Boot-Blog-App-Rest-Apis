package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


//no need to write @Repository because jpa implements simplejpa repository it annotated with @Repository.
public interface PostRepository extends JpaRepository<Post,Long> {


    List<Post> findByCategoryId(Long categoryId);







}
