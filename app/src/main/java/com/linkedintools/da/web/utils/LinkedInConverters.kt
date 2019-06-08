package com.linkedintools.da.web.utils

import android.util.Log
import com.google.gson.Gson
import com.linkedintools.App
import com.linkedintools.R
import com.linkedintools.da.web.linkedin.connections.LinkedInConnectionsWeb
import com.linkedintools.di.components.DaggerLinkedInUserSettingsComponent
import com.linkedintools.model.LinkedInConnection
import com.linkedintools.model.LinkedInUserSettings
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject


/**
 * Retrofit parsers for parsing LinkedIn data.
 */
class LinkedInConverters : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {

        if (type == LinkedInUserSettings::class.java)
            return HtmlConverter.INSTANCE
        else if (type.toString().equals("java.util.List<com.linkedintools.model.LinkedInConnection>")) {
            Log.i("LinkedIn Type", type.toString())
            return JsonConverter.INSTANCE
        } else {
            return HtmlConverter.INSTANCE
        }
    }

    class HtmlConverter : Converter<ResponseBody, LinkedInUserSettings> {

        @Inject
        lateinit var linkedInUserSettings: LinkedInUserSettings

        init {
            DaggerLinkedInUserSettingsComponent.builder().build().inject(this)
        }

        private fun throwExceptionIfMissing(elements: Elements) {
            if (elements.size == 0)
                throw IOException(App.context.getString(R.string.problem_parsing_linkedin_user_settings))
        }

        companion object {
            val INSTANCE = HtmlConverter()
        }

        @Throws(IOException::class)
        override fun convert(responseBody: ResponseBody): LinkedInUserSettings {
            try {
                val body = responseBody.string()
                val doc = Jsoup.parse(body)

                var infoElements = doc.getElementsByClass("user-info")
                throwExceptionIfMissing(infoElements)

                // Photo
                var elements = infoElements[0].getElementsByTag("img")

                if (elements.size > 0)
                    linkedInUserSettings.photoUrl = elements[0].attr("src")

                // Name
                elements = infoElements[0].getElementsByTag("h2")

                if (elements.size > 0)
                    linkedInUserSettings.name = elements[0].text()

                // Title
                elements = infoElements[0].getElementsByTag("h3")

                if (elements.size > 0)
                    linkedInUserSettings.title = elements[0].text()

                // Member since
                elements = infoElements[0].getElementsByTag("p")

                if (elements.size > 0) {
                    var memberSinceText = elements[0].text().toLowerCase()
                    linkedInUserSettings.memberSince = getMemberSinceDate(memberSinceText)
                }

                // Profile url
                var element = doc.select("a.profile-container-link").first()

                if (element != null)
                    linkedInUserSettings.profileUrl = element.attr("href")

                return linkedInUserSettings
            } catch (e: IOException) {
                throw e
            }
        }

        private fun getMemberSinceDate(memberSinceText: String): Date? {

            var memberSinceDate: Date?
            val monthLanguages: MutableList<Array<String>> = ArrayList()

            // English
            monthLanguages.add(arrayOf("january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"))
            // French
            monthLanguages.add(arrayOf("janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"))
            // German
            monthLanguages.add(arrayOf("januar", "februar", "märz", "april", "mai", "juni", "juli", "august", "september", "oktober", "november", "dezember"))
            // Spanish
            monthLanguages.add(arrayOf("enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"))
            // Italian
            monthLanguages.add(arrayOf("gennaio", "febbraio", "marzo", "aprile", "maggio", "giugno", "luglio", "agosto", "settembre", "ottobre", "novembre", "dicembre"))
            // Dutch
            monthLanguages.add(arrayOf("januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober", "november", "december"))
            // Russian
            monthLanguages.add(arrayOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"))
            // Swedish
            monthLanguages.add(arrayOf("januari", "februari", "mars", "april", "maj", "juni", "juli", "augusti", "september", "oktober", "november", "december"))
            // Czech
            monthLanguages.add(arrayOf("leden", "únor", "březen", "duben", "květen", "červen", "červenec", "srpen", "září", "říjen", "listopad", "prosinec"))
            // Danish
            monthLanguages.add(arrayOf("januar", "februar", "marts", "april", "maj", "juni", "juli", "august", "september", "oktober", "november", "december"))
            // Norwegian
            monthLanguages.add(arrayOf("januar", "februar", "mars", "april", "mai", "juni", "juli", "august", "september", "oktober", "november", "desember"))
            // Polish
            monthLanguages.add(arrayOf("styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec", "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"))
            // Portuguese
            monthLanguages.add(arrayOf("janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"))
            // Romanian
            monthLanguages.add(arrayOf("januarie", "februarie", "martie", "aprilie", "mai", "iunie", "iulie", "august", "septembrie", "octombrie", "noiembrie", "decembrie"))
            // Tagalog (Filipino)
            monthLanguages.add(arrayOf("enero", "pebrero", "marso", "abril", "mayo", "hunyo", "hulyo", "agosto", "setyembre", "oktubre", "nobyembre", "disyembre"))
            // Thai
            monthLanguages.add(arrayOf("มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"))
            // Indonesian
            monthLanguages.add(arrayOf("januari", "februari", "maret", "april", "mei", "juni", "juli", "agustus", "september", "oktober", "november", "desember"))
            // Turkish
            monthLanguages.add(arrayOf("ocak", "şubat", "mart", "nisan", "mayıs", "haziran", "temmuz", "ağustos", "eylül", "ekim", "kasım", "aralık"))
            // Arabic
            monthLanguages.add(arrayOf("يناير", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمبر"))

            memberSinceDate = getMemberSinceDateForMonths(memberSinceText, monthLanguages)

            return memberSinceDate


        }

        /**
         * Extracts the date out of the string that indicates when a user became a member of LinkedIn.
         * For example, in the english string "Member since November 14, 2009", it will return a date object
         * for November 14, 2009
         */
        private fun getMemberSinceDateForMonths(memberSinceText: String, monthLanguages: MutableList<Array<String>>): Date? {

            try {
                var month: Int = 0

                for (months: Array<String> in monthLanguages) {
                    month = 0

                    // Get the month
                    for (monthText: String in months) {
                        month++

                        if (memberSinceText.indexOf(monthText) >= 0) {
                            for (year in 2000..2100) {
                                if (memberSinceText.indexOf(year.toString()) >= 0) {
                                    // Get the day.
                                    for (day in 1..31) {
                                        var p = memberSinceText.indexOf(" $day ")

                                        if (p < 0) {
                                            p = memberSinceText.indexOf(" $day.")

                                            if (p < 0) {
                                                p = memberSinceText.indexOf(" $day,")

                                                if (p < 0) {
                                                    p = memberSinceText.indexOf((0x200f).toChar().toString() + "$day ")
                                                }
                                            }
                                        }

                                        if ((p >= 0) || memberSinceText.startsWith("$day ")) {
                                            return convertToTime(year, month, day)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Handle languages like Chinese, Japanese and Korean.
                val numbers = memberSinceText.replace(Regex("[^-?0-9]+"), " ").trim().split(" ")

                return convertToTime(numbers[0].toString().toInt(), numbers[1].toString().toInt(), numbers[2].toString().toInt())


            } catch (ex: Exception) {
                throw Exception(App.context.getString(R.string.could_not_determine_membership_since_date))
            }
        }

        private fun convertToTime(year: Int, month: Int, day: Int): Date {
            val c = Calendar.getInstance()
            c.set(year, month, day, 0, 0)
            return c.time
        }
    }


    class JsonConverter : Converter<ResponseBody, List<LinkedInConnection>> {

        private fun throwExceptionIfMissing(elements: Elements) {
            if (elements.size == 0)
                throw IOException(App.context.getString(R.string.problem_parsing_linkedin_connections))
        }

        companion object {
            val INSTANCE = JsonConverter()
        }

        @Throws(IOException::class)
        override fun convert(responseBody: ResponseBody): List<LinkedInConnection> {
            try {
                val connections: MutableList<LinkedInConnection> = ArrayList()
                val data = responseBody.string()
//                val html = responseBody.string()
//
//                var con = html.indexOf("ConnectionsGetAll")
//                val startPos = html.lastIndexOf(">", con) + 3
//                val endPos = html.indexOf("</code>", con)
//                val data = Html.fromHtml(html.substring(startPos, endPos)).toString()

                val gson = Gson()
                val connectionsWeb: LinkedInConnectionsWeb = gson.fromJson(data, LinkedInConnectionsWeb::class.java)

                for (conWeb in connectionsWeb.elements!!) {
                    val profile = conWeb!!.miniProfile

                    if (profile?.publicIdentifier == null)
                        continue

                    var pictureUrl: String? = null

                    if (profile!!.picture != null)
                        pictureUrl = profile!!.picture!!.comlinkedincommonVectorImage!!.rootUrl.plus(profile!!.picture!!!!.comlinkedincommonVectorImage!!.artifacts!![0]!!.fileIdentifyingUrlPathSegment)

                    val connection = LinkedInConnection(
                        id = profile.publicIdentifier.hashCode(), //(0..0x7FFFFFFF).random(),
                        firstName = profile!!.firstName,
                        lastName = profile!!.lastName,
                        profileUrl = profile.publicIdentifier,
                        occupation = profile!!.occupation,
                        entityUrn = conWeb!!.entityUrn!!.split(":")[3],
                        photoUrl = pictureUrl,
                        companyId = null,
                        dateConnected = null,
                        location = null
                    )

                    connections.add(connection)
                }

                return connections
            } catch (e: IOException) {
                throw IOException(App.context.getString(R.string.problem_parsing_linkedin_connections))
            }
        }
    }
}