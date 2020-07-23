package com.rack.namegame.entity

import org.hibernate.search.annotations.Field
import org.hibernate.search.annotations.Indexed
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@Table(name = "tree_employees")
@EntityListeners(AuditingEntityListener::class)
@Indexed
data class WillowTreeEmployeeEntity (
        @Id
        @Column(name = "id")
        val id: String,
        @Column(name = "type")
        val type: String? = null,
        @Column(name = "slug")
        val slug: String? = null,
        @Column(name = "job_title")
        val jobTitle: String? = null,
        @Column(name = "first_name")
        @Field
        val firstName: String? = null,
        @Column(name = "last_name")
        @Field
        val lastName: String? = null,
        @OneToOne(cascade = [CascadeType.ALL])
        @PrimaryKeyJoinColumn
        val headshot: Headshot? = null
)