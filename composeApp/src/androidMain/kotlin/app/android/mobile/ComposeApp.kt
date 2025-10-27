package app.android.mobile


import android.app.Application
import com.google.android.gms.maps.MapsInitializer
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize


class ComposeApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase (GitLive KMP)
        Firebase.initialize(this)

        // Initialize Maps SDK
        MapsInitializer.initialize(applicationContext)

    }
}