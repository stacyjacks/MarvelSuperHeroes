package kurmakaeva.anastasia.common.logger

import kurmakaeva.anastasia.common.BuildConfig
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