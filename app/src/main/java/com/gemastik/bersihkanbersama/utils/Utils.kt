package com.gemastik.bersihkanbersama.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Objects

object Utils {
    private const val FILENAME_FORMAT = "dd-MMM-yyyy"

    private val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())

    private fun createTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg", storageDir)
    }

    fun parseCurrencyString(value: String): BigDecimal {
        try {
            val locale = Locale("id", "ID")
            val formatter = NumberFormat.getCurrencyInstance(locale) as DecimalFormat

            val replaceRegex = String.format("[%s,.\\s]", Objects.requireNonNull(formatter.currency).getSymbol(locale))
            var currencyValue = value.replace(replaceRegex, "")
            currencyValue = if (currencyValue == "") {
                "0"
            } else {
                currencyValue
            }

            return BigDecimal(currencyValue)
        } catch (e: Exception) {
            Log.e("TextWatcher", "parseCurrencyString: ${e.message}", e)
        }

        return BigDecimal.ZERO
    }

    fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createTempFile(context)
        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    fun formatPrice(price: Int): String {
        val formattedPrice = NumberFormat.getNumberInstance(Locale.getDefault()).format(price)
        return "Rp $formattedPrice"
    }

    val daftarKota: List<String> = listOf(
        "Jakarta",
        "Bengkulu",
        "Surabaya",
        "Medan",
        "Bandung",
        "Bekasi",
        "Semarang",
        "Tangerang",
        "Depok",
        "Palembang",
        "Makassar",
        "South Tangerang",
        "Batam",
        "Pekanbaru",
        "Bogor",
        "Bandar Lampung",
        "Malang",
        "Padang",
        "Denpasar",
        "Samarinda",
        "Mataram"
    )

    val daftarTim: List<String> = listOf(
        "Tim 1",
        "Tim 2",
        "Tim 3"
    )

}