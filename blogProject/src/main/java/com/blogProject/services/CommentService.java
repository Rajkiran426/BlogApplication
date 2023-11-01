package com.blogProject.services;

import com.blogProject.payload.CommentDto;
import com.blogProject.payload.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentDto saveComment(long postId,CommentDto commentDto);

    List<CommentDto> getCommentsBypostId(long postId);

    CommentResponse getAllComment(int pageNo, int pageSize, String sortBy, String sortDir);

    CommentDto getCommentBypostId(long postId, long commentId);

    CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto);

    void deleteCommentById(long postId, long commentId);
}
