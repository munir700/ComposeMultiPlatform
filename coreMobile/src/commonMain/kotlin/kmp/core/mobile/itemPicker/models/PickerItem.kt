package kmp.core.mobile.itemPicker.models

import kmp.core.mobile.utils.CommonParcelable
import kmp.core.mobile.utils.CommonSerializable


interface PickerItem : CommonParcelable, CommonSerializable {
    val key: String
    val title: String
}