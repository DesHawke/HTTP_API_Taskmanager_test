package com.example.taskmanager.model

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name="tasks")
data class Task(

        @Column(name="name")
        val name: String = "",

        @Column(name="description")
        val description: String = "",

        @Column(name="task_date")
        val taskDate: LocalDate?,

        @Column(name="tag_id")
        val tagId: Long = 0,

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0
)