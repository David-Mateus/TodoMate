package br.com.davidmateus.todolistrkt.taks;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@Entity(name="tb_tasks")
public class TaskModel {
    /**
     * ID
     * Usuario(Id_usuario)
     * Descrição
     * Titulo
     * Data de Inicio
     * Data de término
     * Prioridade
     * */
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;
    @Column(length = 50)
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;
    //OBS
    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;



}
