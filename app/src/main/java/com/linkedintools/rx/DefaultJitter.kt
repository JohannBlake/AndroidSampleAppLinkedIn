package com.linkedintools.rx

import java.util.*


class DefaultJitter : Jitter {
    private val random = Random()

    /** Returns a random value inside [0.85, 1.15] every time it's called  */
    override fun get(): Double = 0.85 + random.nextDouble() % 0.3f
}