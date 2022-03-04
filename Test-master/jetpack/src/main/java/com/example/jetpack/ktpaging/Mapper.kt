package com.example.jetpack.ktpaging

interface Mapper<I,O> {
    fun map(input:I):O
}