package com.linkedintools.utils

/**
 * Utility functions for working with strings.
 */
class StringUtils {
    companion object {
        fun concat(string1: String?, string2: String?, separator: String?): String? {
            if ((string1 == null) || (string2 == null))
                return null;

            return string1 + separator + string2
        }
    }
}