package kmp.core.mobile.notifications

import kmp.core.mobile.utils.CommonParcelable
import kmp.core.mobile.utils.CommonParcelize


@CommonParcelize
data class NotificationChannel(
    val id: String,
    val name: String,
) : CommonParcelable