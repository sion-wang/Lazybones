package com.sion.lazybones.utility

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.sion.lazybones.domain.service.LazyAccessibilityService.Companion.ACCESSIBILITY_NAME
import timber.log.Timber

object PermissionHelper {

    fun checkAccessibilityPermission(context: Context): Boolean {
        val prefString =
            Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
        return if (!prefString.isNullOrEmpty()) {
            prefString.contains("${context.packageName}/${context.packageName}.services.$ACCESSIBILITY_NAME")
        } else {
            false
        }
    }

    fun goAccessibilitySetting(context: Context) {
        /**
         * AK-857: Only for Samsung s9
         */
        val samsungIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            setPackage("com.android.settings")
            component = ComponentName(
                "com.android.settings",
                "com.android.settings.Settings\$AccessibilityInstalledServiceActivity"
            )
        }
        val resolveInfo = context.packageManager.resolveActivity(samsungIntent, 0)
        Timber.d("resolveInfo $resolveInfo")
        resolveInfo?.run {
            Timber.d("samsung intent $samsungIntent")
            context.startActivity(samsungIntent)
        } ?: run { context.startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
    }
}