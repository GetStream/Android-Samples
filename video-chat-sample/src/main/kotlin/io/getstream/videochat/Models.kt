package io.getstream.videochat

import android.graphics.Color
import io.getstream.chat.android.client.models.User
import java.io.Serializable

data class Video(val id: String, val name: String, val image: String) : Serializable

val videos = listOf(
    Video("sJ0rDhmlMgM", "4K Video ultrahd wildlife animals with relaxation music for 4K UHD TV", "https://i.ytimg.com/vi/sJ0rDhmlMgM/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLD4hcEdBcPCwrfospTf7_S3nuyi0w"),
    Video("tawqjwGWZ0I", "4K video ultrahd wildlife animals and birds beautiful nature-for 4K UHD TV", "https://i.ytimg.com/vi/tawqjwGWZ0I/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLCH9I26aeknOT-YzGD9JfaMcSwNFQ"),
    Video("ftlvreFtA2A", "FLYING OVER NORWAY (4K UHD) 1HR Ambient Drone Film + Music by Nature Relaxation™ for Stress Relief", "https://i.ytimg.com/vi/ftlvreFtA2A/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLDNEGikDb_pq9XEknxgoJyz7r-M-w"),
    Video("lM02vNMRRB0", "7 HOUR 4K DRONE FILM: \"Earth from Above\" + Music by Nature Relaxation™ (Ambient AppleTV Style)", "https://i.ytimg.com/vi/lM02vNMRRB0/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLCJNXS4LB8MOqlFTCCZ1WXH0NG3uA"),
    Video("G52dUQLxPzg", "11 HOURS Stunning 4K Underwater footage + Music | Nature Relaxation™ Rare & Colorful Sea Life Video", "https://i.ytimg.com/vi/G52dUQLxPzg/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLBuqLvqCgIXyVsPDpisUX-roPL4-Q"),
    Video("_RTMLn7rDRw", "FLYING OVER SCOTLAND (Highlands / Isle of Skye) 4K UHD Drone Film + Healing Music for Stress Relief", "https://i.ytimg.com/vi/_RTMLn7rDRw/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLAfMJPtMSFW_Sn9riOWOXlyq8puYw"),
    Video("683p4Ubg2NU", "10 HOUR 4K DRONE FILM: Best of Nature Relaxation™ 2019 + Calming Music (Ambient AppleTV Style)", "https://i.ytimg.com/vi/683p4Ubg2NU/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLAOF4eUR7SfnoShOKmSwZxL0FPvTA"),
    Video("4AtJV7U3DlU", "FLYING OVER OAHU [4K] Hawaii Ambient Aerial Film + Music for Stress Relief - Honolulu to North Shore", "https://i.ytimg.com/vi/4AtJV7U3DlU/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLAPpmls8K7VTSREv1aTCmv6fZRIzg"),
    Video("1nf61dNdzPc", "FLYING OVER KAUAI (4K) Hawaii's Garden Island | Ambient Aerial Film + Music for Stress Relief 1.5HR", "https://i.ytimg.com/vi/1nf61dNdzPc/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLC9qy5xrfZge3XssdePApVvdVKymg"),
    Video("mA30W2dHQIo", "FLYING OVER BERMUDA (4K UHD Version!) Ambient Aerial/Drone Film + Music by Nature Relaxation™", "https://i.ytimg.com/vi/mA30W2dHQIo/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLATrywClm-BYyFQynbFDzAe8NME5Q"),
    Video("_T6SGPFTHWU", "10 HOURS of Healing Music & 4K Nature: Best of 2018 Mix (No Loops) Worlds Paradises by Drone UHD", "https://i.ytimg.com/vi/_T6SGPFTHWU/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLDM09VE71ldy2UieDWyPl0ZsHDtMA"),
    Video("xGlVPzmgpSM", "South Australia by Drone (4K) 1 Hour Nature Relaxation™ Ambient Film + Light Calming Music", "https://i.ytimg.com/vi/xGlVPzmgpSM/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLCu0I1x-jj10SjQLxRhIUrbujgA9w")
)
private val tokensMap = mapOf(
    "heavycat318" to "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiaGVhdnljYXQzMTgifQ.ZZoXIey7j0Lz6v8SJc84vTPY8s05Jauot3T4ysUEEA8",
    "smalldog114" to "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoic21hbGxkb2cxMTQifQ.pw0O7BQ3rBTM3Ph8VzM5oez3axZl_3p5kv3u1xnLA5k",
    "tinyleopard667" to "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoidGlueWxlb3BhcmQ2NjcifQ.cKi-oyp1ZiQp_S07h0tH2nQS9XQfDItPSiOw3WXWBMI",
    "goldenladybug700" to "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZ29sZGVubGFkeWJ1ZzcwMCJ9.ZipGVAQcj-QucvmkaSAmOoyHRhe-BVBm7srCOp0GelY",
    "smallrabbit651" to "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoic21hbGxyYWJiaXQ2NTEifQ.SHGO-2xifmlq8i5H0aa_sHCOvw8bDfXHLz14J7AVwKg",
    "orangerabbit275" to "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoib3JhbmdlcmFiYml0Mjc1In0.flRprhc8MweKHaUat2DkGIpZwRb476_mnxwRUnb6uQg",
    "lazymouse357" to "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoibGF6eW1vdXNlMzU3In0.5PximWF39so8l6KN1j-nyRk-Bt3rlsNk3td_L3MBKkM",
    "sadmouse707" to "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoic2FkbW91c2U3MDcifQ.5cg__W8AucW5mvXcaUeBQaJi3ENdy9BdXwSqi5TzXmE",
    "organicrabbit972" to "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoib3JnYW5pY3JhYmJpdDk3MiJ9.KfJkSAMhud2i9Kq-is24btlkpZXbCQtMPVFrR6pAE7Y",
    "tinykoala203" to "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoidGlueWtvYWxhMjAzIn0.NGUs20U62WDO4E_qoZmqvFB1cbYDaepkbmjUUsWkFIE"
)

val demoUsers = listOf(
    User("heavycat318").apply {
        extraData["name"] = "Arlo Thompson"
        extraData["image"] = "https://randomuser.me/api/portraits/thumb/men/98.jpg"
    },
    User("smalldog114").apply {
        extraData["name"] = "Léon Perrin"
        extraData["image"] = "https://randomuser.me/api/portraits/thumb/men/82.jpg"
    },
    User("tinyleopard667").apply {
        extraData["name"] = "Amaro Souza"
        extraData["image"] = "https://randomuser.me/api/portraits/thumb/men/83.jpg"
    },
    User("goldenladybug700").apply {
        extraData["name"] = "Siddártha Barbosa"
        extraData["image"] = "https://randomuser.me/api/portraits/thumb/men/27.jpg"
    },
    User("smallrabbit651").apply {
        extraData["name"] = "Tilde Nielsen"
        extraData["image"] = "https://randomuser.me/api/portraits/thumb/women/34.jpg"
    },
    User("orangerabbit275").apply {
        extraData["name"] = "Roderik Heikamp"
        extraData["image"] = "https://randomuser.me/api/portraits/thumb/men/17.jpg"
    },
    User("lazymouse357").apply {
        extraData["name"] = "Stefan Vincent"
        extraData["image"] = "https://randomuser.me/api/portraits/thumb/men/60.jpg"
    },
    User("sadmouse707").apply {
        extraData["name"] = "Ülkü Öztonga"
        extraData["image"] = "https://randomuser.me/api/portraits/thumb/women/70.jpg"
    },
    User("organicrabbit972").apply {
        extraData["name"] = "Cristobal Esteban"
        extraData["image"] = "https://randomuser.me/api/portraits/thumb/men/90.jpg"
    },
    User("tinykoala203").apply {
        extraData["name"] = "Mitchell Ford"
        extraData["image"] = "https://randomuser.me/api/portraits/thumb/men/34.jpg"
    }
)

val randomColors = listOf(
    Color.parseColor("#7a822b"),
    Color.parseColor("#e483c9"),
    Color.parseColor("#cfd78d"),
    Color.parseColor("#d5a879"),
    Color.parseColor("#1f926d"),
    Color.parseColor("#43356e"),
    Color.parseColor("#3c383f"),
    Color.parseColor("#06b185"),
    Color.parseColor("#9a0b9e"),
    Color.parseColor("#60bd55"),
    Color.parseColor("#2f580d"),
    Color.parseColor("#9edb30"),
    Color.parseColor("#faecea"),
    Color.parseColor("#eeecae"),
    Color.parseColor("#ce7b23"),
    Color.parseColor("#e2c413"),
    Color.parseColor("#ba2929"),
    Color.parseColor("#824869"),
    Color.parseColor("#183142"),
    Color.parseColor("#c58e59"),
    Color.parseColor("#3798a1")
)

val userColorMap = mutableMapOf<String, Int>()
val User.nicknameColor: Int
    get() = userColorMap.getOrPut(id) { randomColors.random() }

val User.token: String
    get() = tokensMap[id] ?: ""
