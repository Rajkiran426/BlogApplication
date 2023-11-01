package com.blogProject.services;

import com.blogProject.entities.Post;
import com.blogProject.payload.PostDto;
import com.blogProject.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deleteById(long id);
}
