package com.rack.namegame.entity

import org.hibernate.annotations.Table
import org.hibernate.search.annotations.Indexed
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Id

@Entity
@Table(appliesTo = "headshot")
@EntityListeners(AuditingEntityListener::class)
@Indexed
data class Headshot (
        @Id
        @Column(name = "id")
        val id: String,
        @Column(name = "type")
        val type: String?,
        @Column(name = "mime_type")
        val mimeType: String?,
        @Column(name = "photo_id")
        val photoID: String?,
        @Column(name = "url")
        val url: String?,
        @Column(name = "alt")
        val alt: String?,
        @Column(name = "height")
        val height: Int?,
        @Column(name = "width")
        val width: Int?
)