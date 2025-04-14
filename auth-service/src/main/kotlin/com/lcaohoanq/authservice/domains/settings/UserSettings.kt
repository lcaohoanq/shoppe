package com.lcaohoanq.authservice.domains.settings

import BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import com.lcaohoanq.authservice.domains.settings.notifications.NotificationSettings
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
    @JsonIgnore
    var userId: Long? = null,

    @Column(name = "two_fa_enabled")
    var twoFaEnabled: Boolean = false,

    @Column(name = "preferred_language")
    var preferredLanguage: String = "en",

    @Column(name = "dark_mode")
    var darkMode: Boolean = false,

    @Embedded
    @AttributeOverrides(
        value = [
            AttributeOverride(name = "emailMasterEnabled", column = Column(name = "email_master_enabled")),
            AttributeOverride(name = "emailOrder", column = Column(name = "email_order")),
            AttributeOverride(name = "emailPromo", column = Column(name = "email_promo")),
            AttributeOverride(name = "emailSurvey", column = Column(name = "email_survey")),

            AttributeOverride(name = "smsMasterEnabled", column = Column(name = "sms_master_enabled")),
            AttributeOverride(name = "smsPromo", column = Column(name = "sms_promo")),

            AttributeOverride(name = "zaloMasterEnabled", column = Column(name = "zalo_master_enabled")),
            AttributeOverride(name = "zaloPromo", column = Column(name = "zalo_promo")),
        ]
    )
    var notificationSettings: NotificationSettings = NotificationSettings(),

    @Column(name = "login_alerts")
    var loginAlerts: Boolean = true,

    @Column(name = "request_disable_account")
    var requestDisableAccount: Boolean = false,
) : BaseEntity() {

}
