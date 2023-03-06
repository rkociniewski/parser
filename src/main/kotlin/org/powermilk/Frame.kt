package org.powermilk

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

/**
 * Model with frame.
 */
data class Frame(
    /**
     * latitude
     */
    val latitude: Double = 0.0,
    /**
     * longitude
     */
    val longitude: Double = 0.0,
    /**
     * frame receive time
     */
    @SerializedName("receive_time")
    val receiveTime: LocalDateTime = LocalDateTime.now(),
    /**
     * event type
     */
    @SerializedName("event_type")
    val eventType: String? = "",
    /**
     * vehicle side number
     */
    val sideNumber: String,
    @SerializedName("gps_status")
    /**
     * GPS status
     */
    val gpsStatus: String?
)
