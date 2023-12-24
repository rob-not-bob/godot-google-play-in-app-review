package dev.binarysunrise.GodotGooglePlayInAppReview.signals

import org.godotengine.godot.plugin.SignalInfo

/** @suppress */
fun getSignals(): MutableSet<SignalInfo> =
        mutableSetOf(
                ReviewSignals.reviewInfoRequested,
                ReviewSignals.reviewFlowComplete,
        )

/** Signals emitted by the plugin */
object ReviewSignals {
    /**
     * This signal is emitted once the review info has been requested
     *
     * @return Returns true if the review info was requested successfully, false otherwise
     */
    val reviewInfoRequested = SignalInfo("review_info_requested", Boolean::class.javaObjectType)
    /**
     * This signal is emitted once the review flow has completed Note: In some circumstances the
     * review flow will not be shown to the user, e.g. they have already seen it recently
     */
    val reviewFlowComplete = SignalInfo("review_flow_complete")
}
