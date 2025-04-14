package com.lcaohoanq.authservice.domains.settings.notifications

import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded


@Embeddable
class NotificationSettings(
    @Embedded
    var email: EmailNotificationSettings = EmailNotificationSettings(),

    @Embedded
    var sms: SmsNotificationSettings = SmsNotificationSettings(),

    @Embedded
    var zalo: ZaloNotificationSettings = ZaloNotificationSettings(),
)

