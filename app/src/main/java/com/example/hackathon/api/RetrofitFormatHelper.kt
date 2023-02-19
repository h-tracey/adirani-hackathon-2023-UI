package com.example.hackathon.api

import java.util.*

class RetrofitFormatHelper {

    fun formatGetUrl(urlFirstSection: String?, urlSecondSection: String?, urlThirdSection: String?, params: HashMap<String, String>?): String? {
        var url = checkNullSection(urlFirstSection, urlSecondSection, urlThirdSection)
        if (null == params) return url
        val stringBuilder = StringBuilder(url)
        val iterator: MutableIterator<Map.Entry<String, String>> = params.entries.iterator()
        var i = 1
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (i == 1) stringBuilder.append("?").append(entry.key).append("=").append(entry.value)
            else stringBuilder.append("&").append(entry.key).append("=").append(entry.value)
            iterator.remove()
            i++
        }
        url = stringBuilder.toString()
        return url
    }

    fun formatPostUrl(urlFirstSection: String?, urlSecondSection: String?, urlThirdSection: String?): String? {
        return checkNullSection(urlFirstSection, urlSecondSection, urlThirdSection)
    }

    private fun checkNullSection(urlFirstSection: String?, urlSecondSection: String?, urlThirdSection: String?): String? {
        return if (urlSecondSection != null && urlThirdSection != null) {
            urlFirstSection + urlSecondSection + urlThirdSection
        } else if (urlSecondSection != null) {
            urlFirstSection + urlSecondSection
        } else {
            urlFirstSection
        }
    }
}