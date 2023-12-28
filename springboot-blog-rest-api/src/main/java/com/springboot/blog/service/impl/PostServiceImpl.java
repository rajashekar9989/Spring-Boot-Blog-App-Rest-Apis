package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl  implements PostService {

   //  @Autowired is not required when
   //  we inject the constructor based dependency  no need to write the @Auto wired annotation
    // spring boot emmit the that.
    private PostRepository postRepository;

    private ModelMapper modelMapper;

  private CategoryRepository categoryRepository;

    // constructor
    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper,
                           CategoryRepository categoryRepository) {

        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
        this.categoryRepository=categoryRepository;
    }
    // Create new Post Rest Api
    public PostDto createPost(PostDto postDto) {

        Category category =categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("category","id",postDto.getCategoryId()));

        // convert Dto to  Post Entity
        Post post =  mapToPostEntity(postDto);

        post.setCategory(category);
        Post newpost= postRepository.save(post);

  //convert  Entity to post Dto

        PostDto postResponse = mapToPostDto(newpost);

        return postResponse;
    }


    // get all posts rest Api
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {



        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()

                :Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);



        Page<Post> posts =postRepository.findAll(pageable);

        //get content for page object
         List<Post> listOfPosts = posts.getContent();

       List<PostDto> content =  listOfPosts.stream().map(post -> mapToPostDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;


    }

    @Override
    public PostDto getPostById(long id) {

       Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));


       return mapToPostDto(post);

    }

    // update post Rest Api

    @Override

    public PostDto updatePost(PostDto postDto, long id) {

        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));

         Category category=categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("category","id",postDto.getCategoryId()));

         post.setTitle(postDto.getTitle());
         post.setDescription(postDto.getDescription());
         post.setContent(postDto.getContent());
         post.setCategory(category);

        Post updatepost= postRepository.save(post);

        PostDto updatepostDto  = mapToPostDto(updatepost);

        return updatepostDto;
    }
    // delete post by id Rest Api
    @Override
    public  void deletePostById(long id) {

        postRepository.deleteById(id);



    }

    @Override
    public List<PostDto> getPostByCategory(Long categoryId) {

       Category category= categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));

       List<Post> posts =postRepository.findByCategoryId(categoryId);

        // convert the list of post jpa entities to list of post dto objects


        return posts.stream()
                .map((post)->mapToPostDto(post)).collect(Collectors.toList());
    }


    // convert post entity to postDto
    private   PostDto mapToPostDto(Post post){

        PostDto postDto = modelMapper.map(post,PostDto.class);
       // PostDto postDto = new PostDto();

//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());

        return postDto;


    }

    // convert postDto to Post Entity
    private   Post mapToPostEntity(PostDto postDto){

        Post post =  modelMapper.map(postDto,Post.class);
        //Post post = new Post();
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        return post;
    }


}
