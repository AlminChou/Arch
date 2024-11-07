package com.almin.arch.network.converter.scalar

import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException


internal object CustomScalarResponseBodyConverters {
    var moshi: Moshi? = null

    internal class StringResponseBodyConverter : Converter<ResponseBody, String> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): String {
            return value.string()
        }

        companion object {
            val INSTANCE = StringResponseBodyConverter()
        }
    }

    internal class BooleanResponseBodyConverter : Converter<ResponseBody, Boolean> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): Boolean {
            return java.lang.Boolean.valueOf(value.string())
        }

        companion object {
            val INSTANCE = BooleanResponseBodyConverter()
        }
    }

    internal class ByteResponseBodyConverter : Converter<ResponseBody, Byte> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): Byte {
            return java.lang.Byte.valueOf(value.string())
        }

        companion object {
            val INSTANCE = ByteResponseBodyConverter()
        }
    }

    internal class CharacterResponseBodyConverter : Converter<ResponseBody, Char> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): Char {
            val body = value.string()
            if (body.length != 1) {
                throw IOException(
                    "Expected body of length 1 for Character conversion but was " + body.length
                )
            }
            return body[0]
        }

        companion object {
            val INSTANCE = CharacterResponseBodyConverter()
        }
    }

    internal class DoubleResponseBodyConverter : Converter<ResponseBody, Double> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): Double {
            return java.lang.Double.valueOf(value.string())
        }

        companion object {
            val INSTANCE = DoubleResponseBodyConverter()
        }
    }

    internal class FloatResponseBodyConverter : Converter<ResponseBody, Float> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): Float {
            return java.lang.Float.valueOf(value.string())
        }

        companion object {
            val INSTANCE = FloatResponseBodyConverter()
        }
    }

    internal class IntegerResponseBodyConverter : Converter<ResponseBody, Int> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): Int {
            return Integer.valueOf(value.string())
        }

        companion object {
            val INSTANCE = IntegerResponseBodyConverter()
        }
    }

    internal class LongResponseBodyConverter : Converter<ResponseBody, Long> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): Long {
            return java.lang.Long.valueOf(value.string())
        }

        companion object {
            val INSTANCE = LongResponseBodyConverter()
        }
    }

    internal class ShortResponseBodyConverter : Converter<ResponseBody, Short> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): Short? {
            return java.lang.Short.valueOf(value.string())
        }

        companion object {
            val INSTANCE = ShortResponseBodyConverter()
        }
    }
}
