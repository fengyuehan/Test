package com.example.buildsrc.utils

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util.println
import java.lang.Exception

class LogUtil {

    companion object {

        fun log(stage: String, filePath: String, oldInfo: String, newInfo: String) {
            println("[$stage][$filePath][oldInfo: $oldInfo][newInfo: $newInfo]")
        }

        fun log(stage: String, info: String, result: String) {
            println("[$stage][Info: $info][Result: $result]")
        }

        fun log(str: String) {
            println(str)
        }

        fun log(exception: Exception) {
            println(exception)
        }

    }

}
