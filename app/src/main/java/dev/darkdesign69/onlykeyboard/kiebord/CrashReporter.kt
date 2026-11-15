package dev.darkdesign69.onlykeyboard.kiebord

import android.content.Context
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Saves the stack trace of the last crash to a file so the app can show it on the next start.
 */
object CrashReporter {
    private const val FILE_NAME = "last_crash.txt"

    fun install(context: Context) {
        val appContext = context.applicationContext
        val previous = Thread.getDefaultUncaughtExceptionHandler()
        if (previous is SavingHandler) return
        Thread.setDefaultUncaughtExceptionHandler(SavingHandler(appContext, previous))
    }

    fun readAndClear(context: Context): String? {
        return try {
            val file = File(context.applicationContext.filesDir, FILE_NAME)
            if (!file.exists()) return null
            val text = file.readText()
            file.delete()
            text.ifBlank { null }
        } catch (_: Throwable) {
            null
        }
    }

    private class SavingHandler(
        private val context: Context,
        private val previous: Thread.UncaughtExceptionHandler?
    ) : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(thread: Thread, throwable: Throwable) {
            try {
                val writer = StringWriter()
                throwable.printStackTrace(PrintWriter(writer))
                File(context.filesDir, FILE_NAME).writeText("Thread: ${thread.name}\n$writer")
            } catch (_: Throwable) {
            }
            previous?.uncaughtException(thread, throwable)
        }
    }
}
