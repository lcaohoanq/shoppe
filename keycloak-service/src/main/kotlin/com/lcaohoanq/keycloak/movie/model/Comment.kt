package com.lcaohoanq.keycloak.movie.model

import jakarta.persistence.*
import java.time.Instant

@Entity(name = "comments")
class Comment(

    @Id
    @SequenceGenerator(name = "comments_seq", sequenceName = "comments_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq")
    @Column(name = "id", unique = true, nullable = false)
    val id: Long? = null,
    var username: String,
    var text: String,
    var timestamp: Instant,

    @ManyToOne
    var movie: Movie

) {

}
