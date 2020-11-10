package com.rack.namegame.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import org.hibernate.search.annotations.Indexed
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@Table(name = "games")
@EntityListeners(AuditingEntityListener::class)
@Indexed(index = "data/index/Game")
data class Game (
        @Id
        @GeneratedValue(generator = "uuid", strategy = GenerationType.TABLE)
        @GenericGenerator(name = "uuid", strategy = "uuid2")
        @Column
        var id: String? = null,
        @OneToMany(cascade = arrayOf(CascadeType.REMOVE), orphanRemoval = true)
        @JoinColumn
        var selectionOptions: MutableList<WillowTreeEmployeeEntity>? = null,
        @OneToOne()
        var correctEmployee: WillowTreeEmployeeEntity? = null,
        @Column
        var correctGuesses: Int? = null,
        @Column
        var incorrectGuesses: Int? = null,
        @Column
        @JsonIgnore
        var mode: String? = null
)