package com.sion.lazybones.domain.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import timber.log.Timber

class LazyAccessibilityService : AccessibilityService() {
    companion object {
        const val ACCESSIBILITY_NAME = "LazyAccessibilityService"
    }

    private val packageNameAndButtonIdMap = mapOf(
        "com.android.packageinstaller" to "android:id/button1",
        "com.miui.securitycenter" to "android:id/button2"
    )

    override fun onInterrupt() {
        Timber.d("onInterrupt")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        Timber.d("[onAccessibilityEvent] $event")

        packageNameAndButtonIdMap[event?.packageName]?.also { buttonId ->
            rootInActiveWindow?.run {
                this.findAccessibilityNodeInfosByViewId(buttonId)?.firstOrNull()?.run {
                    Timber.d("[onAccessibilityEvent] click $buttonId")
                    performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }
            }
        }
    }

    override fun onServiceConnected() {
        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPES_ALL_MASK
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            notificationTimeout = 100
            flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS
            packageNames = packageNameAndButtonIdMap.keys.toTypedArray()
        }
        this.serviceInfo = info
        super.onServiceConnected()
    }
}

fun AccessibilityNodeInfo.findAll() {
    Timber.d("[findAll]")
    for (i in 0 until childCount) {
        getChild(i)?.run {
            Timber.d("[Node] $this")
            findAll()
        }
    }
}