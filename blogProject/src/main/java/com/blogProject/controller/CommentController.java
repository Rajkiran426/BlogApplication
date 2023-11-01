package com.blogProject.controller;

import com.blogProject.payload.CommentDto;
import com.blogProject.payload.CommentResponse;
import com.blogProject.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComments(
            @PathVariable("postId")long postId,
            @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.saveComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentById(
            @PathVariable("postId")long postId
    ){
        List<CommentDto> dto = commentService.getCommentsBypostId(postId);
        return dto;
    }
    // http://localhost:8080/api/comments
    // http://localhost:8080/api/comments?pageNo=0&pageSize=3
    // http://localhost:8080/api/comments?pageNo=3&pageSize=3&sortBy=id&sorDir=asc
    @GetMapping("/comments")
    public CommentResponse getAllComments(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false)int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        CommentResponse dto = commentService.getAllComment(pageNo,pageSize,sortBy,sortDir);
        return dto;
    }
    // http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable("postId")long postId,
            @PathVariable("commentId")long commentId
    ){
        CommentDto dto = commentService.getCommentBypostId(postId, commentId);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    // http://localhost:8080/api/posts/1/comments/1
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId")long postId,
            @PathVariable("commentId")long commentId,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.updateCommentById(postId, commentId, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    // http://localhost:8080/api/posts/1/comments/2
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("postId")long postId,
            @PathVariable("commentId")long commentId
    ){
        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("Comment is deleted!",HttpStatus.OK);
    }
}
