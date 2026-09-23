package com.study.board.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "file")
@Data
public class BoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String originalFilename;
    private String filename;
    private String filepath;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
