package com.linkedintools.rx

interface Jitter {
    fun get(): Double

    companion object {
        val NO_OP = { 1 }
    }
}