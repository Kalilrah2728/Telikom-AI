package org.twinkletech.telikomai.model

object ChatBotRepository {

    fun getResponse(question: String): ChatResponse {

        return when {

            question.contains("hi", true) -> {

                ChatResponse(
                    message = """
                        <b>Welcome</b><br><br>
                        I'm your Telikom AI Assistant.<br> 
                        How may I Help you?
                    """.trimIndent(),
                )
            }

            question.contains("balance", true) -> {

                ChatResponse(
                    message = """
                        <b>Here are your Balances:</b><br><br>

                        675-76404912: &nbsp;&nbsp;&nbsp;&nbsp;K 12<br>
                        675-78303104: &nbsp;&nbsp;&nbsp;&nbsp;K 25<br>
                        3142000: &nbsp;&nbsp;&nbsp;&nbsp;K 1<br>
                        17234457: &nbsp;&nbsp;&nbsp;&nbsp;Postpaid<br><br>

                        Wish to recharge your number?
                    """.trimIndent(),

                    buttons = listOf(
                        ChatAction(
                            "Recharge Now",
                            "RECHARGE"
                        )
                    )
                )
            }

            question.contains("recommend", true) -> {

                ChatResponse(
                    message = """
                        <b>Based on Your Usage, I Recommend</b><br><br>

                        K10 - 5GB - 7 Days Plan
                    """.trimIndent(),

                    buttons = listOf(
                        ChatAction(
                            "Buy this Plan",
                            "BUY_PLAN"
                        )
                    )
                )
            }

            question.contains("plan", true) -> {

                ChatResponse(
                    message = """
                        <b>Your Active Plans</b><br><br>

                        FibrePro 600 Add-On<br>
                        Expiry : 02/07/2026<br><br>

                        Turbo Monthly 1000<br>
                        Expiry : 30/06/2026<br><br>

                        Roaming Zone 2 Bundle<br>
                        Expiry : 10/06/2026<br><br>

                        Corporate Plus Post Paid Data Plan
                    """.trimIndent(),

                    buttons = listOf(
                        ChatAction(
                            "Purchase Now",
                            "PURCHASE_PLAN"
                        )
                    )
                )
            }

            question.contains("offer", true) -> {

                ChatResponse(
                    message = """
                        <b>Data Plan Offer</b><br><br>

                        Subscribe to the Night Data plan
                        and receive 10GB for only K10.
                    """.trimIndent(),

                    buttons = listOf(
                        ChatAction(
                            "Subscribe Now",
                            "SUBSCRIBE"
                        )
                    )
                )
            }

            question.contains("roaming", true) -> {

                ChatResponse(
                    message = """
                        <b>Roaming Zone 2 Bundle</b><br><br>

                        Roaming Zone 2 Bundle
                        K100

                        Roaming Zone 1 Bundle
                        K20
                    """.trimIndent()
                )
            }

            question.contains("topup", true) -> {

                ChatResponse(
                    message = """
                        Your last topup was done on

                        02/06/2026 18:11:38

                        Amount:
                        K50<br>
                        
                        Would you like to topup now? Click
                    """.trimIndent(),

                    buttons = listOf(
                        ChatAction(
                            "Topup Now",
                            "TOPUP"
                        )
                    )
                )
            }

            question.contains("bill", true) -> {

                ChatResponse(
                    message = """
                        Click below to download
                        your Postpaid Bill.
                    """.trimIndent(),

                    buttons = listOf(
                        ChatAction(
                            "Download Bill",
                            "DOWNLOAD_BILL"
                        )
                    )
                )
            }

            question.contains("store", true) -> {

                ChatResponse(
                    message =
                        "Find your nearest Telikom store."
                    ,

                    buttons = listOf(
                        ChatAction(
                            "View Stores",
                            "STORE_LOCATION"
                        )
                    )
                )
            }

            question.contains("happening", true) -> {

                ChatResponse(
                    message = """
                        Check our News Section
                        for the latest updates.
                    """.trimIndent(),

                    buttons = listOf(
                        ChatAction(
                            "Check News",
                            "NEWS"
                        )
                    )
                )
            }

            question.contains("Weather", true) -> {

                ChatResponse(
                    message = """
                        I'm sorry. I can only answer questions related Telikom PNG. Please ask relevant questions.
                    """.trimIndent(),
                )
            }

            else -> {

                ChatResponse(
                    "Sorry, I didn't understand."
                )
            }
        }
    }
}