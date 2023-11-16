package ro.redteam.taskmanagementsystem.models.dtos;

import lombok.Data;

@Data
public class UpdateProgressRequestDTO {

    private Long taskId;
    private int progress;
}