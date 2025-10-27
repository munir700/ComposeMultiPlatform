package app.shared.mobile.presentation.locationScreen.models

import app.shared.mobile.location.Location
import kmp.core.mobile.utils.CommonParcelable
import kmp.core.mobile.utils.CommonParcelize
import kmp.core.mobile.utils.CommonSerializable
import kotlinx.serialization.Serializable

@CommonParcelize
@Serializable
data class LocationParams(
    val title: String,
    val pickLocation: Boolean,
    val location: Location
): CommonParcelable, CommonSerializable