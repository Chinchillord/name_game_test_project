package com.rack.namegame.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.GenericGenerator
import org.hibernate.search.annotations.Field
import org.hibernate.search.annotations.Indexed
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "tree_employees")
@EntityListeners(AuditingEntityListener::class)
@Indexed
data class WillowTreeEmployeeEntity (
        @Id
        @GeneratedValue(generator = "system-uuid")
        @GenericGenerator(name="system-uuid", strategy = "uuid")
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
        @OneToOne(mappedBy = "employee", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
        @PrimaryKeyJoinColumn
        val headshot: Headshot? = null
)