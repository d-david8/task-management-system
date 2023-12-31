package ro.redteam.taskmanagementsystem.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import ro.redteam.taskmanagementsystem.enums.Priority;
import ro.redteam.taskmanagementsystem.enums.Status;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tasks", uniqueConstraints = @UniqueConstraint(columnNames = "title"))
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", unique = true)
    private String title;
    @Column(name = "description")
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "due_date")
    private Date dueDate;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "progress")
    private int progress;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
}