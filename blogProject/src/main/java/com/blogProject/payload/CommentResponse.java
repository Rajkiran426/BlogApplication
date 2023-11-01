package com.blogProject.payload;

import lombok.Data;

import java.util.List;

@Data
public class CommentResponse {
    private List<CommentDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isLast;
}
