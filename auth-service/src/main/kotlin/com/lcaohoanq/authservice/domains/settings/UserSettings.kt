package com.lcaohoanq.authservice.domains.settings

import BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_settings")
class UserSettings(

    @Id
    @SequenceGenerator(
        name = "user_settings_seq",
        sequenceName = "user_settings_id_seq",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_settings_seq")
    @Column(name = "id", unique = true, nullable = false)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false, unique = true)
    val userId: Long,

    @Column(name = "two_fa_enabled")
    var twoFaEnabled: Boolean = false,

    @Column(name = "preferred_language")
    var preferredLanguage: String = "en",

    @Column(name = "dark_mode")
    var darkMode: Boolean = false,

    @Column(name = "email_notifications_enabled")
    var emailNotificationsEnabled: Boolean = true,

    @Column(name = "login_alerts")
    var loginAlerts: Boolean = true
) : BaseEntity() {

}
