package com.example.taskmanager.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
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
        val id: Long = 0,

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "tag")
        val tasks: MutableList<Task> = mutableListOf<Task>()
)