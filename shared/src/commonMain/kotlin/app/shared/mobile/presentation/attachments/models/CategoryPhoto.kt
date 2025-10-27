package app.shared.mobile.presentation.attachments.models

import kmp.core.mobile.utils.CommonParcelable
import kmp.core.mobile.utils.CommonParcelize
import kmp.core.mobile.utils.CommonSerializable


@CommonParcelize
class CategoryPhoto(
    val evaluationType: Int,  // Required to upload company attachment category
    val taskId: Int, // Required to upload company attachment category
) : CommonSerializable, CommonParcelable