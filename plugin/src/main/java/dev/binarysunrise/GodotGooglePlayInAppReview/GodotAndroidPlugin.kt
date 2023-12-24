package dev.binarysunrise.GodotGooglePlayInAppReview

import android.util.Log
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import dev.binarysunrise.GodotGooglePlayInAppReview.signals.ReviewSignals
import dev.binarysunrise.GodotGooglePlayInAppReview.signals.getSignals
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.SignalInfo
import org.godotengine.godot.plugin.UsedByGodot

class GodotAndroidPlugin(godot: Godot) : GodotPlugin(godot) {
    private var manager: ReviewManager? = null
    private var reviewInfo: ReviewInfo? = null

    override fun getPluginName() = BuildConfig.GODOT_PLUGIN_NAME

    override fun getPluginSignals(): MutableSet<SignalInfo> {
        return getSignals()
    }

    @UsedByGodot
    private fun requestReviewInfo() {
        if (manager == null) {
            Log.d(pluginName, "Getting review manager")
            manager = ReviewManagerFactory.create(godot.getActivity()!!)
        }

        val request = manager!!.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(pluginName, "Received review info")
                // We got the ReviewInfo object
                reviewInfo = task.result
                emitSignal(
                        godot,
                        BuildConfig.GODOT_PLUGIN_NAME,
                        ReviewSignals.reviewInfoRequested,
                        true,
                )
            } else {
                // There was some problem, log or handle the error code.
                val reviewErrorCode = (task.getException() as ReviewException).errorCode
                Log.e(pluginName, "Failed to retrieve review info. Error code: " + reviewErrorCode)
                emitSignal(
                        godot,
                        BuildConfig.GODOT_PLUGIN_NAME,
                        ReviewSignals.reviewInfoRequested,
                        false,
                )
            }
        }
    }

    @UsedByGodot
    private fun launchReviewFlow() {
        if (reviewInfo == null) {
            return
        }

        val flow = manager!!.launchReviewFlow(godot.getActivity()!!, reviewInfo!!)
        flow.addOnCompleteListener { _ ->
            // The flow has finished. The API does not indicate whether the user
            // reviewed or not, or even whether the review dialog was shown. Thus, no
            // matter the result, we continue our app flow.
            Log.d(pluginName, "Review flow completed")
            emitSignal(
                    godot,
                    BuildConfig.GODOT_PLUGIN_NAME,
                    ReviewSignals.reviewFlowComplete,
            )
        }
    }
}
