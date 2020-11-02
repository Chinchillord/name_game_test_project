package com.rack.namegame.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.Table
import org.hibernate.search.annotations.Indexed
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(appliesTo = "headshot")
@EntityListeners(AuditingEntityListener::class)
@Indexed
data class Headshot (
        @Id
        @Column(name = "employee_id")
        val id: String? = null,
        @Column(name = "type")
        val type: String? = null,
        @Column(name = "mime_type")
        val mimeType: String? = null,
        @Column(name = "photo_id")
        val photoID: String? = null,
        @Column(name = "url")
        val url: String? = null,
        @Column(name = "alt")
        val alt: String? = null,
        @Column(name = "height")
        val height: Int? = null,
        @Column(name = "width")
        val width: Int? = null,
        @MapsId
        @OneToOne
        @JoinColumn(name = "employee_id")
        @JsonIgnore
        var employee: WillowTreeEmployeeEntity? = null
)