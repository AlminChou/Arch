package com.almin.arch.network.converter.type

import com.squareup.moshi.*
import okhttp3.ResponseBody
import okio.ByteString
import okio.ByteString.Companion.decodeHex
import retrofit2.Converter
import java.io.IOException


internal class CustomMoshiResponseBodyConverter<T>(private val adapter: JsonAdapter<T>, private val moshi: Moshi) :
    Converter<ResponseBody, T?> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val source = value.source()

        try {
            // Moshi has no document-level API so the responsibility of BOM skipping falls to whatever
            // is delegating to it. Since it's a UTF-8-only library as well we only honor the UTF-8 BOM.
            if (source.rangeEquals(0, UTF8_BOM)) {
                source.skip(UTF8_BOM.size.toLong())
            }
            val reader = JsonReader.of(source)
            val result = adapter.fromJson(reader)
            if (reader.peek() !== JsonReader.Token.END_DOCUMENT) {
                throw JsonDataException("JSON document was not fully consumed.")
            }
            return result
        } finally {
            value.close()
        }
    }

    companion object {
        private val UTF8_BOM: ByteString = "EFBBBF".decodeHex()
    }
}