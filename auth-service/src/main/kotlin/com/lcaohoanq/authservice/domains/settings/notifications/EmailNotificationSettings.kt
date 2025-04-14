package com.lcaohoanq.authservice.domains.settings.notifications

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class EmailNotificationSettings(
    @Column(name = "email_master_enabled")
    var masterEnabled: Boolean = true,

    @Column(name = "email_order")
    var order: Boolean = true,

    @Column(name = "email_promo")
    var promo: Boolean = true,

    @Column(name = "email_survey")
    var survey: Boolean = true,
)