package app.mat.movie.common.util

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper
import app.mat.movie.data.remote.dto.common.ShareDetailsModel
import app.mat.movie.data.remote.dto.common.VideoDto
import app.mat.movie.data.remote.type.ExternalContentType
import app.mat.movie.data.remote.type.ExternalIdsResource
import app.mat.movie.data.remote.type.VideoSiteDto

open class CaptureSpeechToText : ActivityResultContract<Void?, String?>() {
    @CallSuper
    override fun createIntent(
        context: Context,
        input: Void?
    ): Intent {
        return Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        ).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }
    }

    final override fun getSynchronousResult(
        context: Context,
        input: Void?
    ): SynchronousResult<String?>? = null

    final override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): String? {
        if (resultCode == RESULT_OK) {
            val results = intent?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            return results?.firstOrNull()
        }
        return null
    }
}

fun openExternalId(
    context: Context,
    externalId: ExternalIdsResource
) {
    when (externalId) {
        is ExternalIdsResource.Instagram -> openInstagramExternalId(
            context = context,
            instagramId = externalId
        )

        is ExternalIdsResource.Twitter -> openTwitterExternalId(
            context = context,
            twitterId = externalId
        )

        is ExternalIdsResource.Facebook -> openFacebookExternalId(
            context = context,
            facebookId = externalId
        )

        is ExternalIdsResource.Imdb -> openImdbExternalId(
            context = context,
            imdbId = externalId
        )
    }
}

private fun openInstagramExternalId(
    context: Context,
    instagramId: ExternalIdsResource.Instagram
) {
    val uri: Uri = Uri.parse("https://instagram.com/_u/${instagramId.id}")

    try {
        val instagramIntent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage("com.instagram.android")
        }
        context.startActivity(instagramIntent)
    } catch (e: ActivityNotFoundException) {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}

private fun openTwitterExternalId(
    context: Context,
    twitterId: ExternalIdsResource.Twitter
) {
    try {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("twitter://user?screen_name=${twitterId.id}")
        )
        context.startActivity(intent)
    } catch (
        e: ActivityNotFoundException
    ) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://twitter.com/#!/${twitterId.id}")
        )
        context.startActivity(intent)
    }
}

private fun openFacebookExternalId(
    context: Context,
    facebookId: ExternalIdsResource.Facebook
) {
    val url = "https://www.facebook.com/${facebookId.id}"

    try {
        val uri: Uri = Uri.parse("fb://facewebmodal/f?href=$url")
        val facebookIntent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage("com.facebook.katana")
        }

        context.startActivity(facebookIntent)
    } catch (e: ActivityNotFoundException) {
        val uri: Uri = Uri.parse("https://www.facebook.com/${facebookId.id}")
        val intent = Intent(Intent.ACTION_VIEW, uri)

        context.startActivity(intent)
    }
}

private fun openImdbExternalId(
    context: Context,
    imdbId: ExternalIdsResource.Imdb
) {
    val contentLink = when (imdbId.type) {
        ExternalContentType.Movie, ExternalContentType.Tv -> "title"
        else -> "name"
    }

    val url = "https://www.imdb.com/$contentLink/${imdbId.id}"
    val uri: Uri = Uri.parse(url)
    try {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    } catch (_: Exception) {

    }
}

fun openVideo(
    context: Context,
    video: VideoDto
) {
    when (video.site) {
        VideoSiteDto.YouTube -> openYoutubeVideo(context = context, key = video.key)
        VideoSiteDto.Vimeo -> openVimeoVideo(context = context, key = video.key)
    }
}

private fun openYoutubeVideo(
    context: Context,
    key: String
) {
    val url = "https://www.youtube.com/watch?v=$key"
    val uri = Uri.parse(url)

    val intent = Intent(Intent.ACTION_VIEW, uri)

    context.startActivity(intent)
}

private fun openVimeoVideo(
    context: Context,
    key: String
) {
    val url = "https://vimeo.com/$key"
    val uri = Uri.parse(url)

    val intent = Intent(Intent.ACTION_VIEW, uri)

    context.startActivity(intent)
}

fun shareImdb(
    context: Context,
    details: ShareDetailsModel
) {
    val message = details.asMessage()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }

    context.startActivity(Intent.createChooser(intent, "Share"))
}

