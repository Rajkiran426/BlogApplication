package com.blogProject.services.impl;

import com.blogProject.entities.Comment;
import com.blogProject.entities.Post;
import com.blogProject.exceptions.BlogAPIException;
import com.blogProject.exceptions.ResourceNotFoundException;
import com.blogProject.payload.CommentDto;
import com.blogProject.payload.CommentResponse;
import com.blogProject.repositories.CommentRepository;
import com.blogProject.repositories.PostRepository;
import com.blogProject.services.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto saveComment(long postId,CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post doesn't found with id:" + postId)
        );
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment saveComment = commentRepository.save(comment);
        return mapToDto(saveComment);
    }

    @Override
    public List<CommentDto> getCommentsBypostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post does not found with id:" + postId)
        );

            List<Comment> comments = commentRepository.findByPostId(postId);
            List<CommentDto> dtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public CommentResponse getAllComment(int pageNo,int pageSize,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Comment> content = commentRepository.findAll(pageable);
        List<Comment> comments = content.getContent();
        List<CommentDto> dtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        CommentResponse commentResponse=new CommentResponse();
        commentResponse.setContent(dtos);
        commentResponse.setPageNo(content.getNumber());
        commentResponse.setPageSize(content.getSize());
        commentResponse.setTotalPages(content.getTotalPages());
        commentResponse.setTotalElements(content.getTotalElements());
        commentResponse.setLast(content.isLast());
        return commentResponse;
    }

    @Override
    public CommentDto getCommentBypostId(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id:" + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id:" + commentId)
        );
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment doesn't belong to post.");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id:" + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id:" + commentId)
        );
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment doesn't belong to post.");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updateComment = commentRepository.save(comment);

        return mapToDto(comment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id:" + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id:" + commentId)
        );
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException("Comment does not belongs to post.");
        }
        commentRepository.deleteById(commentId);
    }

    Comment mapToEntity(CommentDto commentDto){
        Comment comment=new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
    CommentDto mapToDto(Comment comment){
        CommentDto commentDto=new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}
