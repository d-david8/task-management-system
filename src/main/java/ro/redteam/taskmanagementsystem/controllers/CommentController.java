package ro.redteam.taskmanagementsystem.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.redteam.taskmanagementsystem.models.dtos.CommentResponseDTO;
import ro.redteam.taskmanagementsystem.services.CommentService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponseDTO> addComment(@RequestBody CommentResponseDTO commentResponseDTO) {
        return ResponseEntity.ok(commentService.addComment(commentResponseDTO));
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }
}