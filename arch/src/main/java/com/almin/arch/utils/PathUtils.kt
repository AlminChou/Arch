package com.almin.arch.utils

import android.app.Application
import android.os.Build
import android.os.Environment
import java.io.File

/**
 * getRootPath                    : 获取根路径
 * getDataPath                    : 获取数据路径
 * getDownloadCachePath           : 获取下载缓存路径
 * getInternalAppDataPath         : 获取内存应用数据路径
 * getInternalAppCodeCacheDir     : 获取内存应用代码缓存路径
 * getInternalAppCachePath        : 获取内存应用缓存路径
 * getInternalAppDbsPath          : 获取内存应用数据库路径
 * getInternalAppDbPath           : 获取内存应用数据库路径
 * getInternalAppFilesPath        : 获取内存应用文件路径
 * getInternalAppSpPath           : 获取内存应用 SP 路径
 * getInternalAppNoBackupFilesPath: 获取内存应用未备份文件路径
 * getExternalStoragePath         : 获取外存路径
 * getExternalMusicPath           : 获取外存音乐路径
 * getExternalPodcastsPath        : 获取外存播客路径
 * getExternalRingtonesPath       : 获取外存铃声路径
 * getExternalAlarmsPath          : 获取外存闹铃路径
 * getExternalNotificationsPath   : 获取外存通知路径
 * getExternalPicturesPath        : 获取外存图片路径
 * getExternalMoviesPath          : 获取外存影片路径
 * getExternalDownloadsPath       : 获取外存下载路径
 * getExternalDcimPath            : 获取外存数码相机图片路径
 * getExternalDocumentsPath       : 获取外存文档路径
 * getExternalAppDataPath         : 获取外存应用数据路径
 * getExternalAppCachePath        : 获取外存应用缓存路径
 * getExternalAppFilesPath        : 获取外存应用文件路径
 * getExternalAppMusicPath        : 获取外存应用音乐路径
 * getExternalAppPodcastsPath     : 获取外存应用播客路径
 * getExternalAppRingtonesPath    : 获取外存应用铃声路径
 * getExternalAppAlarmsPath       : 获取外存应用闹铃路径
 * getExternalAppNotificationsPath: 获取外存应用通知路径
 * getExternalAppPicturesPath     : 获取外存应用图片路径
 * getExternalAppMoviesPath       : 获取外存应用影片路径
 * getExternalAppDownloadPath     : 获取外存应用下载路径
 * getExternalAppDcimPath         : 获取外存应用数码相机图片路径
 * getExternalAppDocumentsPath    : 获取外存应用文档路径
 * getExternalAppObbPath          : 获取外存应用 OBB 路径
 * 路径 工具类   By https://github.com/Blankj/AndroidUtilCode  -> PathUtils.java
 * Created by Almin on 2024/9/14.
 */
class PathUtils private constructor() {
    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {
        /**
         * Return the path of /system.
         *
         * @return the path of /system
         */
        val rootPath: String
            get() = Environment.getRootDirectory().absolutePath

        /**
         * Return the path of /data.
         *
         * @return the path of /data
         */
        val dataPath: String
            get() = Environment.getDataDirectory().absolutePath

        /**
         * Return the path of /cache.
         *
         * @return the path of /cache
         */
        val downloadCachePath: String
            get() = Environment.getDownloadCacheDirectory().absolutePath

        /**
         * Return the path of /data/data/package.
         *
         * @return the path of /data/data/package
         */
        fun getInternalAppDataPath(application: Application): String {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                application.applicationInfo.dataDir
            } else application.dataDir.absolutePath
        }

        /**
         * Return the path of /data/data/package/code_cache.
         *
         * @return the path of /data/data/package/code_cache
         */
        fun getInternalAppCodeCacheDir(application: Application): String {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                application.applicationInfo.dataDir + "/code_cache"
            } else application.codeCacheDir.absolutePath
        }

        /**
         * Return the path of /data/data/package/cache.
         *
         * @return the path of /data/data/package/cache
         */
        fun getInternalAppCachePath(application: Application): String {
            return application.cacheDir.absolutePath
        }

        /**
         * Return the path of /data/data/package/databases.
         *
         * @return the path of /data/data/package/databases
         */
        fun getInternalAppDbsPath(application: Application): String {
            return application.applicationInfo.dataDir + "/databases"
        }

        /**
         * Return the path of /data/data/package/databases/name.
         *
         * @param name The name of database.
         * @return the path of /data/data/package/databases/name
         */
        fun getInternalAppDbPath(application: Application, name: String?): String {
            return application.getDatabasePath(name).absolutePath
        }

        /**
         * Return the path of /data/data/package/files.
         *
         * @return the path of /data/data/package/files
         */
        fun getInternalAppFilesPath(application: Application): String {
            return application.filesDir.absolutePath
        }

        /**
         * Return the path of /data/data/package/shared_prefs.
         *
         * @return the path of /data/data/package/shared_prefs
         */
        fun getInternalAppSpPath(application: Application): String {
            return application.applicationInfo.dataDir + "shared_prefs"
        }

        /**
         * Return the path of /data/data/package/no_backup.
         *
         * @return the path of /data/data/package/no_backup
         */
        fun getInternalAppNoBackupFilesPath(application: Application): String {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                application.applicationInfo.dataDir + "no_backup"
            } else application.noBackupFilesDir.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0.
         *
         * @return the path of /storage/emulated/0
         */
        val externalStoragePath: String?
            get() = if (isExternalStorageDisable) null else Environment.getExternalStorageDirectory().absolutePath

        /**
         * Return the path of /storage/emulated/0/Music.
         *
         * @return the path of /storage/emulated/0/Music
         */
        val externalMusicPath: String?
            get() = if (isExternalStorageDisable) null else Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC
            ).absolutePath

        /**
         * Return the path of /storage/emulated/0/Podcasts.
         *
         * @return the path of /storage/emulated/0/Podcasts
         */
        val externalPodcastsPath: String?
            get() = if (isExternalStorageDisable) null else Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PODCASTS
            ).absolutePath

        /**
         * Return the path of /storage/emulated/0/Ringtones.
         *
         * @return the path of /storage/emulated/0/Ringtones
         */
        val externalRingtonesPath: String?
            get() = if (isExternalStorageDisable) null else Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_RINGTONES
            ).absolutePath

        /**
         * Return the path of /storage/emulated/0/Alarms.
         *
         * @return the path of /storage/emulated/0/Alarms
         */
        val externalAlarmsPath: String?
            get() = if (isExternalStorageDisable) null else Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_ALARMS
            ).absolutePath

        /**
         * Return the path of /storage/emulated/0/Notifications.
         *
         * @return the path of /storage/emulated/0/Notifications
         */
        val externalNotificationsPath: String?
            get() = if (isExternalStorageDisable) null else Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_NOTIFICATIONS
            ).absolutePath

        /**
         * Return the path of /storage/emulated/0/Pictures.
         *
         * @return the path of /storage/emulated/0/Pictures
         */
        val externalPicturesPath: String?
            get() = if (isExternalStorageDisable) null else Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ).absolutePath

        /**
         * Return the path of /storage/emulated/0/Movies.
         *
         * @return the path of /storage/emulated/0/Movies
         */
        val externalMoviesPath: String?
            get() = if (isExternalStorageDisable) null else Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES
            ).absolutePath

        /**
         * Return the path of /storage/emulated/0/Download.
         *
         * @return the path of /storage/emulated/0/Download
         */
        val externalDownloadsPath: String?
            get() = if (isExternalStorageDisable) null else Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            ).absolutePath

        /**
         * Return the path of /storage/emulated/0/DCIM.
         *
         * @return the path of /storage/emulated/0/DCIM
         */
        val externalDcimPath: String?
            get() = if (isExternalStorageDisable) null else Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM
            ).absolutePath

        /**
         * Return the path of /storage/emulated/0/Documents.
         *
         * @return the path of /storage/emulated/0/Documents
         */
        val externalDocumentsPath: String?
            get() {
                if (isExternalStorageDisable) return null
                return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    Environment.getExternalStorageDirectory().absolutePath + "/Documents"
                } else Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS
                ).absolutePath
            }

        /**
         * Return the path of /storage/emulated/0/Android/data/package.
         *
         * @return the path of /storage/emulated/0/Android/data/package
         */
        fun getExternalAppDataPath(application: Application): String? {
            return if (isExternalStorageDisable) null else application.externalCacheDir?.parentFile?.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0/Android/data/package/cache.
         *
         * @return the path of /storage/emulated/0/Android/data/package/cache
         */
        fun getExternalAppCachePath(application: Application): String? {
            return if (isExternalStorageDisable) null else application.externalCacheDir?.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0/Android/data/package/files.
         *
         * @return the path of /storage/emulated/0/Android/data/package/files
         */
        fun getExternalAppFilesPath(application: Application): String? {
            return if (isExternalStorageDisable) null else application.getExternalFilesDir(null)?.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0/Android/data/package/files/Music.
         *
         * @return the path of /storage/emulated/0/Android/data/package/files/Music
         */
        fun getExternalAppMusicPath(application: Application): String? {
            return if (isExternalStorageDisable) null else application.getExternalFilesDir(
                Environment.DIRECTORY_MUSIC
            )?.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0/Android/data/package/files/Podcasts.
         *
         * @return the path of /storage/emulated/0/Android/data/package/files/Podcasts
         */
        fun getExternalAppPodcastsPath(application: Application): String? {
            return if (isExternalStorageDisable) null else application.getExternalFilesDir(
                Environment.DIRECTORY_PODCASTS
            )?.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0/Android/data/package/files/Ringtones.
         *
         * @return the path of /storage/emulated/0/Android/data/package/files/Ringtones
         */
        fun getExternalAppRingtonesPath(application: Application): String? {
            return if (isExternalStorageDisable) null else application.getExternalFilesDir(
                Environment.DIRECTORY_RINGTONES
            )?.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0/Android/data/package/files/Alarms.
         *
         * @return the path of /storage/emulated/0/Android/data/package/files/Alarms
         */
        fun getExternalAppAlarmsPath(application: Application): String? {
            return if (isExternalStorageDisable) null else application.getExternalFilesDir(
                Environment.DIRECTORY_ALARMS
            )?.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0/Android/data/package/files/Notifications.
         *
         * @return the path of /storage/emulated/0/Android/data/package/files/Notifications
         */
        fun getExternalAppNotificationsPath(application: Application): String? {
            return if (isExternalStorageDisable) null else application.getExternalFilesDir(
                Environment.DIRECTORY_NOTIFICATIONS
            )?.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0/Android/data/package/files/Pictures.
         *
         * @return path of /storage/emulated/0/Android/data/package/files/Pictures
         */
        fun getExternalAppPicturesPath(application: Application): String? {
            return if (isExternalStorageDisable) null else application.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            )?.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0/Android/data/package/files/Movies.
         *
         * @return the path of /storage/emulated/0/Android/data/package/files/Movies
         */
        fun getExternalAppMoviesPath(application: Application): String? {
            return if (isExternalStorageDisable) null else application.getExternalFilesDir(
                Environment.DIRECTORY_MOVIES
            )?.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0/Android/data/package/files/Download.
         *
         * @return the path of /storage/emulated/0/Android/data/package/files/Download
         */
        fun getExternalAppDownloadPath(application: Application): String? {
            return if (isExternalStorageDisable) null else application.getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS
            )?.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0/Android/data/package/files/DCIM.
         *
         * @return the path of /storage/emulated/0/Android/data/package/files/DCIM
         */
        fun getExternalAppDcimPath(application: Application): String? {
            return if (isExternalStorageDisable) null else application.getExternalFilesDir(
                Environment.DIRECTORY_DCIM
            )?.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0/Android/data/package/files/Documents.
         *
         * @return the path of /storage/emulated/0/Android/data/package/files/Documents
         */
        fun getExternalAppDocumentsPath(application: Application): String? {
            if (isExternalStorageDisable) return null
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                application.getExternalFilesDir(null)?.absolutePath + "/Documents"
            } else application.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS
            )?.absolutePath
        }

        /**
         * Return the path of /storage/emulated/0/Android/obb/package.
         *
         * @return the path of /storage/emulated/0/Android/obb/package
         */
        fun getExternalAppObbPath(application: Application): String? {
            return if (isExternalStorageDisable) null else application.obbDir.absolutePath
        }

        private val isExternalStorageDisable: Boolean
            private get() = Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()

        /**
         * 判断sub是否在parent之下的文件或子文件夹<br></br>
         *
         * @param parent
         * @param sub
         * @return
         */
        fun isSub(parent: File, sub: File): Boolean {
            return try {
                sub.absolutePath.startsWith(parent.absolutePath)
            } catch (e: Exception) {
                false
            }
        }

        /**
         * 获取子绝对路径与父绝对路径的相对路径
         *
         * @param parentPath
         * @param subPath
         * @return
         */
        fun getRelativePath(parentPath: String?, subPath: String?): String? {
            return try {
                if (parentPath == null || subPath == null) {
                    return null
                }
                if (subPath.startsWith(parentPath)) {
                    subPath.substring(parentPath.length)
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }

        /**
         * 拼接两个路径
         *
         * @param pathA 路径A
         * @param pathB 路径B
         * @return 拼接后的路径
         */
        fun plusPath(pathA: String?, pathB: String?): String? {
            if (pathA == null) {
                return pathB
            }
            if (pathB == null) {
                return pathA
            }
            return plusPathNotNull(pathA, pathB)
        }

        /**
         * 拼接两个路径
         *
         * @param pathA 路径A
         * @param pathB 路径B
         * @return 拼接后的路径
         */
        fun plusPathNotNull(pathA: String, pathB: String): String {
            val pathAEndSeparator = pathA.endsWith(File.separator)
            val pathBStartSeparator = pathB.startsWith(File.separator)
            return if (pathAEndSeparator && pathBStartSeparator) {
                pathA + pathB.substring(1)
            } else if (pathAEndSeparator || pathBStartSeparator) {
                pathA + pathB
            } else {
                pathA + File.separator + pathB
            }
        }
    }
}