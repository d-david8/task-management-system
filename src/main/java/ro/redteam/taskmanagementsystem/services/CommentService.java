package ro.redteam.taskmanagementsystem.services;

import ro.redteam.taskmanagementsystem.models.dtos.CommentResponseDTO;

import java.util.List;

public interface CommentService {

    CommentResponseDTO addComment(CommentResponseDTO commentResponseDTO);

    List<CommentResponseDTO> getAllComments();
}