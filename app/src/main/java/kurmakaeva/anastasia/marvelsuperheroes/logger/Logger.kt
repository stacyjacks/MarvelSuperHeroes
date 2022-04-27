package kurmakaeva.anastasia.marvelsuperheroes.logger

import kurmakaeva.anastasia.marvelsuperheroes.BuildConfig
import javax.inject.Inject

class Logger @Inject constructor() {
    fun log(e: Throwable) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        } else {
            // for logging non-fatal errors on production builds we could use Firebase
        }
    }
}