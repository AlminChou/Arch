package com.almin.arch.utils

import com.almin.arch.log.ILog
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipException
import java.util.zip.ZipInputStream

/**
 * Created by Almin on 2024/9/23.
 */
object FileZipUtil : ILog {

    @Throws(IOException::class)
    fun unZip(zipFile: File, dirPath: String, cacheKey: String): String {
        logI("================ 准备解压 ================")
        val cacheDir = File("$dirPath/$cacheKey/")

        // 清理现有的缓存
        if (cacheDir.exists()) {
            cacheDir.deleteRecursively()  // 删除所有文件和子目录
        }
        cacheDir.mkdirs()

        try {
            FileInputStream(zipFile).use { fileInputStream ->
                BufferedInputStream(fileInputStream).use { bufferedInputStream ->
                    ZipInputStream(bufferedInputStream).use { zipInputStream ->
                        while (true) {
                            val zipItem = zipInputStream.nextEntry ?: break
                            logD("正在解压: ${zipItem.name}")

                            val file = File(cacheDir, zipItem.name)
                            if (zipItem.isDirectory) {
                                file.mkdirs()
                            } else {
                                file.parentFile?.mkdirs()
                                ensureUnzipSafety(file, cacheDir.absolutePath)

                                FileOutputStream(file).use { fileOutputStream ->
                                    BufferedOutputStream(fileOutputStream).use { bufferedOutputStream ->
                                        val buff = ByteArray(4096)
                                        var readBytes: Int
                                        while (zipInputStream.read(buff).also { readBytes = it } != -1) {
                                            bufferedOutputStream.write(buff, 0, readBytes)
                                        }
                                    }
                                }
                            }
                            zipInputStream.closeEntry()
                            logD("解压完成: ${zipItem.name}")
                        }
                    }
                }
            }
        } catch (e: ZipException) {
            logE("ZipException: ${e.message}")
            clearDir(cacheDir.absolutePath)
            cacheDir.delete()
            throw e
        } catch (e: IOException) {
            logE("IOException: ${e.message}")
            clearDir(cacheDir.absolutePath)
            cacheDir.delete()
            throw e
        } catch (e: Exception) {
            logE("Exception: ${e.message}")
            clearDir(cacheDir.absolutePath)
            cacheDir.delete()
            throw e
        }

        return cacheDir.absolutePath
    }


    // 检查 zip 路径穿透
    private fun ensureUnzipSafety(outputFile: File, dstDirPath: String) {
        val dstDirCanonicalPath = File(dstDirPath).canonicalPath
        val outputFileCanonicalPath = outputFile.canonicalPath
        if (!outputFileCanonicalPath.startsWith(dstDirCanonicalPath)) {
            throw IOException("Found Zip Path Traversal Vulnerability with $dstDirCanonicalPath")
        }
    }

    // 清除目录下的所有文件
    internal fun clearDir(path: String) {
        try {
            val dir = File(path)
            dir.takeIf { it.exists() }?.let { parentDir ->
                parentDir.listFiles()?.forEach { file ->
                    if (!file.exists()) {
                        return@forEach
                    }
                    if (file.isDirectory) {
                        clearDir(file.absolutePath)
                    }
                    file.delete()
                }
            }
        } catch (e: Exception) {
            logE("Clear cache path: $path fail", e)
        }
    }
}