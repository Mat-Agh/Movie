package app.mat.movie.common

import kotlin.time.Duration.Companion.seconds

object Parameter {
    object Api {
        // Remote api base url
        const val url = "https://api.themoviedb.org/3/"

        // Cash size in bytes
        const val cashSize: Long = (10 * 1024 * 1024)

        //TMDB API Key
        const val TMDB_KEY = "917bf7822a8a553bd2cde24ccfa3262e"

        // Time Out limits
        object TimeOut {
            val connect = 10.seconds
            val write = 10.seconds
            val read = 10.seconds
        }
    }

    object Scan {
        object Camera {

            const val SCAN_RESULT_KEY = "SCAN_RESULT_KEY"
        }
    }
}