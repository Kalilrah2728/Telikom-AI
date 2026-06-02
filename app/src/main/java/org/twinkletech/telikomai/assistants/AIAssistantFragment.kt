package org.twinkletech.telikomai.assistants

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.twinkletech.telikomai.R
import org.twinkletech.telikomai.adapter.ChatAdapter
import org.twinkletech.telikomai.assistants.model.ChatMessage
import org.twinkletech.telikomai.databinding.FragmentAIAssistantBinding
import org.twinkletech.telikomai.databinding.FragmentDashboardBinding
import java.util.Locale


class AIAssistantFragment : Fragment() {

    lateinit var binding: FragmentAIAssistantBinding
    private lateinit var adapter: ChatAdapter
    private val messageList = mutableListOf<ChatMessage>()
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var speechIntent: Intent

    private lateinit var textToSpeech: TextToSpeech

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAIAssistantBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.chatRecyclerView)
        val messageBox = view.findViewById<EditText>(R.id.messageBox)
        val sendBtn = view.findViewById<ImageView>(R.id.sendBtn)
        adapter = ChatAdapter(messageList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())

        speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        speechIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        speechIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )

        addWelcomeMessages()

        sendBtn.setOnClickListener {
            val question = messageBox.text.toString().trim()
            if (question.isEmpty()) return@setOnClickListener
            addUserMessage(question)
            val answer = getBotReply(question)
            addBotMessage(answer)
            messageBox.setText("")
            recyclerView.scrollToPosition(
                messageList.size - 1
            )
        }

        textToSpeech = TextToSpeech(requireContext()) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech.language = Locale.US
            }
        }

        binding.camera.setOnClickListener {
            speechRecognizer.startListening(speechIntent)
        }


        speechRecognizer.setRecognitionListener(
            object : RecognitionListener {

                override fun onResults(results: Bundle?) {
                    val data =
                        results?.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                        )
                    if (!data.isNullOrEmpty()) {
                        val voiceText = data[0]
                        handleVoiceQuestion(voiceText)
                    }
                }

                override fun onReadyForSpeech(params: Bundle?) {}

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {}

                override fun onError(error: Int) {}

                override fun onPartialResults(
                    partialResults: Bundle?
                ) {
                }

                override fun onEvent(
                    eventType: Int,
                    params: Bundle?
                ) {
                }
            }
        )

    }

    private fun handleVoiceQuestion(
        question: String
    ) {
        addUserMessage(question)
        val answer = getAnswer(question)
        addBotMessage(answer)
        speak(answer)
    }

    private fun getAnswer(
        question: String
    ): String {
        val normalized =
            question.lowercase()
                .replace("?", "")
                .trim()
        return qaMap[normalized]
            ?: "Sorry I don't understand."
    }

    private fun speak(
        text: String
    ) {
        textToSpeech.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            null
        )
    }

    private val qaMap = hashMapOf(

        "what is my balance" to
                """
            Here are your balances:
            675-770-66066 K1.09
            675-770-66065 K3.70
            675-327-0800 Postpaid
            Balance expiry 11 Jan 2025
            """.trimIndent(),

        "what plan suits me best" to
                """
            Based on your usage:
            K10 - 5GB - 7 Days
            Best value for your data pattern.
            """.trimIndent(),

        "show my offers" to
                """
            Available Offers:
            10GB for K20
            Free WhatsApp
            Night Bundle
            """.trimIndent()
    )

    private fun addWelcomeMessages() {

        addBotMessage(
            "Hi 👋 Welcome to Telikom AI Assistant.\n\nTry asking:\n\n• What is my balance?\n• What plan suits me best?\n• Show my offers"
        )
    }

    private fun addUserMessage(message: String) {

        messageList.add(
            ChatMessage(message, true)
        )

        adapter.notifyItemInserted(
            messageList.size - 1
        )
    }

    private fun addBotMessage(message: String) {

        messageList.add(
            ChatMessage(message, false)
        )

        adapter.notifyItemInserted(
            messageList.size - 1
        )
    }

    private fun getBotReply(
        question: String
    ): String {

        return when {

            question.contains(
                "balance", ignoreCase = true) -> {
                """
                    Here are your balances:
                    675-770-66066   K1.09
                    675-770-66065   K3.70
                    675-327-0800    Postpaid
                    Balance expiry:
                    11 Jan 2025
                """.trimIndent()
            }

            question.contains(
                "plan",
                ignoreCase = true
            ) -> {
                """
                    Based on your usage I recommend:
                    K10 - 5GB - 7 Days
                    Best value for your data usage pattern.
                """.trimIndent()
            }

            question.contains(
                "offer",
                ignoreCase = true
            ) -> {
                """
                    Special Offers:
                    • 10GB for K20
                    • Unlimited WhatsApp
                    • Free Night Data
                """.trimIndent()
            }

            else -> {
                "Sorry, I couldn't understand that. Try:\n\nWhat is my balance?\nWhat plan suits me best?"
            }
        }
    }
}