package com.example.taskmanager.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name="tasks")
data class Task(

        @Column(name="name")
        @NotBlank(message = "name must not be empty")
        val name: String = "",

        @Column(name="description")
        @NotBlank(message = "description must not be empty")
        val description: String = "",

        @Column(name="task_date")
        @NotNull(message = "task_date must not be empty")
        val taskDate: LocalDate?,

        @Column(name="tag")
        val tag: Long,

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0
)