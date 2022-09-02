/*
 *  The MIT License (MIT)
 *
 *  Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package io.getstream.chat.virtualevent

object AppConfig {
    /**
     * Available livestream channels.
     */
    const val LIVESTREAM_ESG_DATA = "livestream:esg_data_7c56f238-aed1-40f1-83b6-d1172a26973f"
    const val LIVESTREAM_DATA_STRATEGY = "livestream:data_strategy_c80d2d33-88a9-4bff-9ce4-0a13c3272238"

    val availableUsers: List<UserCredentials> = listOf(
        UserCredentials(
            name = "Jc Minarro",
            image = "https://ca.slack-edge.com/T02RM6X6B-U011KEXDPB2-891dbb8df64f-128",
            id = "1f37e58d-d8b0-476a-a4f2-f8611e0d85d9",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMWYzN2U1OGQtZDhiMC00NzZhLWE0ZjItZjg2MTFlMGQ4NWQ5In0.aom5u_9KymJkz_S-R6AAZmSKAV-k-rCLO6-3sjplfWs"
        ),
        UserCredentials(
            name = "Amit Kumar",
            image = "https://ca.slack-edge.com/T02RM6X6B-U027L4AMGQ3-9ca65ea80b60-128",
            id = "6d95273b-33f0-40f5-b07c-0da261092074",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNmQ5NTI3M2ItMzNmMC00MGY1LWIwN2MtMGRhMjYxMDkyMDc0In0.QLhLlC0aNrTR76SCRxwzWOG-B03QfcyFZnL5tkoSfKs"
        ),
        UserCredentials(
            name = "Dmitrii Bychkov",
            image = "https://ca.slack-edge.com/T02RM6X6B-U01CDPY6YE8-b74b0739493e-128",
            id = "1e330111-670d-49a7-8f08-e6734338c641",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMWUzMzAxMTEtNjcwZC00OWE3LThmMDgtZTY3MzQzMzhjNjQxIn0._jYAx-ehIzRcYuOKNYC43pNhQQ3a6nkVDRohRvD_540"
        ),
        UserCredentials(
            name = "Filip Babic",
            image = "https://ca.slack-edge.com/T02RM6X6B-U022AFX9D2S-f7bcb3d56180-128",
            id = "29e46def-88f4-4b6a-a10c-584d10c4fdc9",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMjllNDZkZWYtODhmNC00YjZhLWExMGMtNTg0ZDEwYzRmZGM5In0.1tH5i2sTUkdujqsxf1eaLVAiykS-h7-jALoaMWoiOkc"
        ),
        UserCredentials(
            name = "Leandro Borges Ferreira",
            image = "https://ca.slack-edge.com/T02RM6X6B-U01AQ67NJ9Z-2f28d711cae9-128",
            id = "1f052c08-f682-4a83-896c-9f19a68bd2bb",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMWYwNTJjMDgtZjY4Mi00YTgzLTg5NmMtOWYxOWE2OGJkMmJiIn0.QKmpcVSygYa-5isnvkWLE6tFW5HfjV06m_MQPlUZSJ0"
        ),
        UserCredentials(
            name = "Marton Braun",
            image = "https://ca.slack-edge.com/T02RM6X6B-U018YPHEW7L-26ab96fd1ed3-128",
            id = "0d3e6e63-6200-4dd1-a841-4050664891e2",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMGQzZTZlNjMtNjIwMC00ZGQxLWE4NDEtNDA1MDY2NDg5MWUyIn0.etzxvC2C0Wsu2irdi7--pEH8CSn4v3_y86SLPNbsBNQ"
        ),
        UserCredentials(
            name = "Oleg Kuzmin",
            image = "https://ca.slack-edge.com/T02RM6X6B-U019BEATNCD-bad2dcf654ef-128",
            id = "12fb0ed9-93d8-48a5-9885-28e41f2e4c43",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMTJmYjBlZDktOTNkOC00OGE1LTk4ODUtMjhlNDFmMmU0YzQzIn0.FURL638pCUj41b3_9zFgC13N-jqKn-zj2WkAQahHStg"
        ),
        UserCredentials(
            name = "Rafal Adasiewicz",
            image = "https://ca.slack-edge.com/T02RM6X6B-U0177N46AFN-a4e664d1960d-128",
            id = "5531a8cb-3b81-4a54-b424-7ae4e27bf8ba",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNTUzMWE4Y2ItM2I4MS00YTU0LWI0MjQtN2FlNGUyN2JmOGJhIn0.UBomNZKC6pJCS2uRt-6y4suBvCBrT1I90UYH13O3QtI"
        ),
        UserCredentials(
            name = "Samuel Urbanowicz",
            image = "https://ca.slack-edge.com/T02RM6X6B-U011KEXD396-6d3169b36889-128",
            id = "06356564-149f-4b2c-8525-d22056fec404",
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMDYzNTY1NjQtMTQ5Zi00YjJjLTg1MjUtZDIyMDU2ZmVjNDA0In0.hhAQ58Rr6bgUfCiA3Iuir5MjYzmLLzRP8Vs9oC6Oyig"
        )
    )
}
