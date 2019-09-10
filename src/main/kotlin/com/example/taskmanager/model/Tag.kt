package com.example.taskmanager.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name="tags")
data class Tag(
        @Column(name="name")
        @NotBlank(message = "description must not be empty")
        val name: String,

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0
)