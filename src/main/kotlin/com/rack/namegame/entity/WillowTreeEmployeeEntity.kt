package com.rack.namegame.entity

import org.hibernate.annotations.GenericGenerator
import org.hibernate.search.annotations.Field
import org.hibernate.search.annotations.Indexed
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@Table(name = "tree_employees")
@EntityListeners(AuditingEntityListener::class)
@Indexed(index = "data/index/WillowTreeEmployeeEntity")
data class WillowTreeEmployeeEntity(
        @Id
        @GeneratedValue(generator = "system-uuid")
        @GenericGenerator(name="system-uuid", strategy = "uuid")
        @Column(name = "id")
        val id: String? = null,
        @Column(name = "type")
        val type: String? = null,
        @Column(name = "slug")
        val slug: String? = null,
        @Column(name = "job_title")
        val jobTitle: String? = null,
        @Column(name = "first_name")
        val firstName: String? = null,
        @Column(name = "last_name")
        val lastName: String? = null,
        @OneToOne(mappedBy = "employee", cascade = [CascadeType.MERGE], orphanRemoval = true, fetch = FetchType.LAZY)
        var headshot: Headshot? = null
)