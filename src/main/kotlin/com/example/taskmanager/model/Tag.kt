package com.example.taskmanager.model

import javax.persistence.*

@Entity
@Table(name="tags")
data class Tag(
        @Column(name="name")
        val name: String,

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0
)