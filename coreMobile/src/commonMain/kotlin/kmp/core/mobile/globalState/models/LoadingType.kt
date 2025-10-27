package kmp.core.mobile.globalState.models

import kmp.core.mobile.resoources.IFileResource

sealed class LoadingType {
    data class Lottie(val anim: IFileResource? = null) : LoadingType()
    data class LottieBlocking(val anim: IFileResource? = null) : LoadingType()
    data object PrimaryCircular : LoadingType()
    data object PrimaryCircularBlocking : LoadingType()
    data object SecondaryCircular : LoadingType()
    data object SecondaryCircularBlocking : LoadingType()
    data object HiddenBlocking : LoadingType()
    data object NoLoading : LoadingType()
}