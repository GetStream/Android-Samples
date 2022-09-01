/*
 * Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.videochat

import java.io.Serializable

object SampleData {

    fun createUsers(): List<UserCredentials> {
        return listOf(
            UserCredentials(
                id = "heavycat318",
                name = "Arlo Thompson",
                image = "https://randomuser.me/api/portraits/thumb/men/98.jpg",
                token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiaGVhdnljYXQzMTgifQ.ZZoXIey7j0Lz6v8SJc84vTPY8s05Jauot3T4ysUEEA8"
            ),
            UserCredentials(
                id = "smalldog114",
                name = "Léon Perrin",
                image = "https://randomuser.me/api/portraits/thumb/men/82.jpg",
                token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoic21hbGxkb2cxMTQifQ.pw0O7BQ3rBTM3Ph8VzM5oez3axZl_3p5kv3u1xnLA5k"
            ),
            UserCredentials(
                id = "tinyleopard667",
                name = "Amaro Souza",
                image = "https://randomuser.me/api/portraits/thumb/men/83.jpg",
                token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoidGlueWxlb3BhcmQ2NjcifQ.cKi-oyp1ZiQp_S07h0tH2nQS9XQfDItPSiOw3WXWBMI"
            ),
            UserCredentials(
                id = "goldenladybug700",
                name = "Siddártha Barbosa",
                image = "https://randomuser.me/api/portraits/thumb/men/27.jpg",
                token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiZ29sZGVubGFkeWJ1ZzcwMCJ9.ZipGVAQcj-QucvmkaSAmOoyHRhe-BVBm7srCOp0GelY"
            ),
            UserCredentials(
                id = "smallrabbit651",
                name = "Tilde Nielsen",
                image = "https://randomuser.me/api/portraits/thumb/women/34.jpg",
                token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoic21hbGxyYWJiaXQ2NTEifQ.SHGO-2xifmlq8i5H0aa_sHCOvw8bDfXHLz14J7AVwKg"
            ),
            UserCredentials(
                id = "orangerabbit275",
                name = "Roderik Heikamp",
                image = "https://randomuser.me/api/portraits/thumb/men/17.jpg",
                token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoib3JhbmdlcmFiYml0Mjc1In0.flRprhc8MweKHaUat2DkGIpZwRb476_mnxwRUnb6uQg"
            ),
            UserCredentials(
                id = "lazymouse357",
                name = "Stefan Vincent",
                image = "https://randomuser.me/api/portraits/thumb/men/60.jpg",
                token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoibGF6eW1vdXNlMzU3In0.5PximWF39so8l6KN1j-nyRk-Bt3rlsNk3td_L3MBKkM"
            ),
            UserCredentials(
                id = "sadmouse707",
                name = "Ülkü Öztonga",
                image = "https://randomuser.me/api/portraits/thumb/women/70.jpg",
                token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoic2FkbW91c2U3MDcifQ.5cg__W8AucW5mvXcaUeBQaJi3ENdy9BdXwSqi5TzXmE"
            ),
            UserCredentials(
                id = "organicrabbit972",
                name = "Cristobal Esteban",
                image = "https://randomuser.me/api/portraits/thumb/men/90.jpg",
                token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoib3JnYW5pY3JhYmJpdDk3MiJ9.KfJkSAMhud2i9Kq-is24btlkpZXbCQtMPVFrR6pAE7Y"
            ),
            UserCredentials(
                id = "tinykoala203",
                name = "Mitchell Ford",
                image = "https://randomuser.me/api/portraits/thumb/men/34.jpg",
                token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoidGlueWtvYWxhMjAzIn0.NGUs20U62WDO4E_qoZmqvFB1cbYDaepkbmjUUsWkFIE"
            )
        )
    }

    fun createVideos(): List<YoutubeVideo> {
        return listOf(
            YoutubeVideo(
                "sJ0rDhmlMgM",
                "4K Video ultrahd wildlife animals with relaxation music for 4K UHD TV",
                "https://i.ytimg.com/vi/sJ0rDhmlMgM/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLD4hcEdBcPCwrfospTf7_S3nuyi0w"
            ),
            YoutubeVideo(
                "tawqjwGWZ0I",
                "4K video ultrahd wildlife animals and birds beautiful nature-for 4K UHD TV",
                "https://i.ytimg.com/vi/tawqjwGWZ0I/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLCH9I26aeknOT-YzGD9JfaMcSwNFQ"
            ),
            YoutubeVideo(
                "ftlvreFtA2A",
                "FLYING OVER NORWAY (4K UHD) 1HR Ambient Drone Film + Music by Nature Relaxation™ for Stress Relief",
                "https://i.ytimg.com/vi/ftlvreFtA2A/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLDNEGikDb_pq9XEknxgoJyz7r-M-w"
            ),
            YoutubeVideo(
                "lM02vNMRRB0",
                "7 HOUR 4K DRONE FILM: \"Earth from Above\" + Music by Nature Relaxation™ (Ambient AppleTV Style)",
                "https://i.ytimg.com/vi/lM02vNMRRB0/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLCJNXS4LB8MOqlFTCCZ1WXH0NG3uA"
            ),
            YoutubeVideo(
                "G52dUQLxPzg",
                "11 HOURS Stunning 4K Underwater footage + Music | Nature Relaxation™ Rare & Colorful Sea Life Video",
                "https://i.ytimg.com/vi/G52dUQLxPzg/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLBuqLvqCgIXyVsPDpisUX-roPL4-Q"
            ),
            YoutubeVideo(
                "_RTMLn7rDRw",
                "FLYING OVER SCOTLAND (Highlands / Isle of Skye) 4K UHD Drone Film + Healing Music for Stress Relief",
                "https://i.ytimg.com/vi/_RTMLn7rDRw/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLAfMJPtMSFW_Sn9riOWOXlyq8puYw"
            ),
            YoutubeVideo(
                "683p4Ubg2NU",
                "10 HOUR 4K DRONE FILM: Best of Nature Relaxation™ 2019 + Calming Music (Ambient AppleTV Style)",
                "https://i.ytimg.com/vi/683p4Ubg2NU/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLAOF4eUR7SfnoShOKmSwZxL0FPvTA"
            ),
            YoutubeVideo(
                "4AtJV7U3DlU",
                "FLYING OVER OAHU [4K] Hawaii Ambient Aerial Film + Music for Stress Relief - Honolulu to North Shore",
                "https://i.ytimg.com/vi/4AtJV7U3DlU/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLAPpmls8K7VTSREv1aTCmv6fZRIzg"
            ),
            YoutubeVideo(
                "1nf61dNdzPc",
                "FLYING OVER KAUAI (4K) Hawaii's Garden Island | Ambient Aerial Film + Music for Stress Relief 1.5HR",
                "https://i.ytimg.com/vi/1nf61dNdzPc/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLC9qy5xrfZge3XssdePApVvdVKymg"
            ),
            YoutubeVideo(
                "mA30W2dHQIo",
                "FLYING OVER BERMUDA (4K UHD Version!) Ambient Aerial/Drone Film + Music by Nature Relaxation™",
                "https://i.ytimg.com/vi/mA30W2dHQIo/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLATrywClm-BYyFQynbFDzAe8NME5Q"
            ),
            YoutubeVideo(
                "_T6SGPFTHWU",
                "10 HOURS of Healing Music & 4K Nature: Best of 2018 Mix (No Loops) Worlds Paradises by Drone UHD",
                "https://i.ytimg.com/vi/_T6SGPFTHWU/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLDM09VE71ldy2UieDWyPl0ZsHDtMA"
            ),
            YoutubeVideo(
                "xGlVPzmgpSM",
                "South Australia by Drone (4K) 1 Hour Nature Relaxation™ Ambient Film + Light Calming Music",
                "https://i.ytimg.com/vi/xGlVPzmgpSM/hqdefault.jpg?sqp=-oaymwEYCKgBEF5IVfKriqkDCwgBFQAAiEIYAXAB&rs=AOn4CLCu0I1x-jj10SjQLxRhIUrbujgA9w"
            )
        )
    }
}

data class YoutubeVideo(
    val id: String,
    val name: String,
    val image: String
) : Serializable

data class UserCredentials(
    val id: String,
    val name: String,
    val image: String,
    val token: String
)
