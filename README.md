# Lazybones

### Apk安裝懶人小工具

使用小米或Vivo手機安裝apk時會跳出一個讓使用者確認的介面，常常會忘記按。這個小工具利用Accessibility自動偵測並點擊安裝按鈕。

目前僅支援小米&Vivo部份裝置，如果要擴充，可以直接到LazyAccessibilityService.kt，在packageNameAndButtonIdMap加入安裝介面的packageName還有要點擊的buttonId。如下:
```
    private val packageNameAndButtonIdMap = mapOf(
        "com.android.packageinstaller" to "android:id/button1",
        "com.miui.securitycenter" to "android:id/button2"
    )
```
