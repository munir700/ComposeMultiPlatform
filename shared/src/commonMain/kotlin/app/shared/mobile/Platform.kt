package app.shared.mobile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform