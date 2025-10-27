package kmp.core.mobile.itemPicker.models

import kmp.core.mobile.utils.CommonParcelize
import kmp.core.mobile.utils.CommonSerializable

@CommonParcelize
data class PickerItemUIModel(
    override val key: String,
    override val title: String
) : PickerItem, CommonSerializable