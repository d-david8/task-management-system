package ro.redteam.taskmanagementsystem.services;

import ro.redteam.taskmanagementsystem.models.dtos.CommentDTO;

public interface CommentService {
    public CommentDTO addComment(CommentDTO commentDTO);
}