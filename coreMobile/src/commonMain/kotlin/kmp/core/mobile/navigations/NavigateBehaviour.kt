package kmp.core.mobile.navigations

import kmp.core.mobile.utils.CommonParcelable
import kmp.core.mobile.utils.CommonParcelize
import kmp.core.mobile.utils.CommonSerializable


@CommonParcelize
enum class NavigateBehaviour : CommonParcelable, CommonSerializable {
    Normal,
    ReplaceIfCurrent,
    KeepIfCurrent
}