package org.twinkletech.telikomai.assistants

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.twinkletech.telikomai.R
import org.twinkletech.telikomai.adapter.ChatAdapter
import org.twinkletech.telikomai.model.ChatBotRepository
import org.twinkletech.telikomai.model.ChatMessage
import org.twinkletech.telikomai.model.ChatResponse
import org.twinkletech.telikomai.databinding.FragmentAIAssistantBinding
import java.util.Locale


class AIAssistantFragment : Fragment() {

    lateinit var binding: FragmentAIAssistantBinding
    private lateinit var adapter: ChatAdapter
    private val messageList = mutableListOf<ChatMessage>()
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var speechIntent: Intent

    private lateinit var textToSpeech: TextToSpeech


    private val audioPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {
                startVoiceRecognition()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Microphone permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

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
        //adapter = ChatAdapter(messageList)

        adapter = ChatAdapter(messageList) { action ->

            when(action){

                "RECHARGE" -> {
                    // Navigate Recharge Fragment
                    findNavController().navigate(R.id.action_AIAssistantFragment_to_plansFragment)
                }

                "BUY_PLAN" -> {
                    // Navigate Plan Fragment
                    findNavController().navigate(R.id.action_AIAssistantFragment_to_plansFragment)
                }

                "PURCHASE_PLAN" -> {
                    // Navigate Purchase Fragment
                    findNavController().navigate(R.id.action_AIAssistantFragment_to_plansFragment)
                }

                "TOPUP" -> {
                    // Navigate Topup Fragment
                    findNavController().navigate(R.id.action_AIAssistantFragment_to_topupFragment)
                }

                "SUBSCRIBE" -> {
                    // Navigate Topup Fragment
                    findNavController().navigate(R.id.action_AIAssistantFragment_to_plansFragment)
                }

                "NEWS" -> {
                    // Navigate Topup Fragment
                    findNavController().navigate(R.id.action_AIAssistantFragment_to_newsFragment)
                }

                "STORE_LOCATION" -> {
                    // Navigate Topup Fragment
                    findNavController().navigate(R.id.action_AIAssistantFragment_to_storeLocaterFragment)
                }
            }
        }
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


        sendBtn.setOnClickListener {
            val question = messageBox.text.toString().trim()
            if (question.isEmpty()) return@setOnClickListener
            addUserMessage(question)
            val response = ChatBotRepository.getResponse(question)
            showThinking(response)
            //addBotMessage(response)
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

            if (
                checkSelfPermission(
                    requireContext(),
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                startVoiceRecognition()

            } else {

                audioPermissionLauncher.launch(
                    Manifest.permission.RECORD_AUDIO
                )
            }
        }


        speechRecognizer.setRecognitionListener(
            object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    startMicAnimation()
                }
                override fun onBeginningOfSpeech() {
                    startMicAnimation()
                }

                override fun onBufferReceived(p0: ByteArray?) {
                }

                override fun onEndOfSpeech() {
                    stopMicAnimation()
                }

                override fun onError(error: Int) {

                    stopMicAnimation()

                    val msg = when (error) {

                        SpeechRecognizer.ERROR_NO_MATCH ->
                            "Didn't catch that"

                        SpeechRecognizer.ERROR_SPEECH_TIMEOUT ->
                            "No speech detected"

                        SpeechRecognizer.ERROR_NETWORK ->
                            "Network error"

                        else ->
                            "Voice error"
                    }

                    Toast.makeText(
                        requireContext(),
                        msg,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onEvent(p0: Int, p1: Bundle?) {
                }
                override fun onPartialResults(p0: Bundle?) {
                }
                override fun onResults(results: Bundle?) {

                    stopMicAnimation()

                    val data =
                        results?.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                        )

                    if (!data.isNullOrEmpty()) {

                        val voiceText = data[0]

                        handleVoiceQuestion(
                            voiceText
                        )
                    }
                }

                override fun onRmsChanged(p0: Float) {
                }
            }
        )

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_AIAssistantFragment_to_dashboardFragment)
        }

    }

    private fun startVoiceRecognition() {
        startMicAnimation()
        speechRecognizer.startListening(
            speechIntent
        )
    }
    private fun startMicAnimation() {
        val animation =
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.anim
            )
        binding.camera.startAnimation(animation)
    }
    private fun stopMicAnimation() {
        binding.camera.clearAnimation()
    }

    private fun scrollBottom() {
        binding.chatRecyclerView.post {
            binding.chatRecyclerView.scrollToPosition(
                messageList.size - 1
            )
        }
    }


    private fun handleVoiceQuestion(
        question: String
    ) {
        addUserMessage(question)
        val response = ChatBotRepository.getResponse(question)
        showThinking(response)
        //addBotMessage(response)
        speak(response)
    }

    private fun showThinking(
        response: ChatResponse
    ) {
        val typingPosition = messageList.size
        messageList.add(
            ChatMessage(
                message = " Telikom AI Assistant is typing...",
                isUser = false,
                isTyping = true
            )
        )
        adapter.notifyItemInserted(typingPosition)
        scrollBottom()
        binding.root.postDelayed({
            messageList.removeAt(typingPosition)
            adapter.notifyItemRemoved(
                typingPosition
            )
            addBotMessage(response)
        }, 1500)
    }

    private fun speak(response: ChatResponse) {
        textToSpeech.speak(
            response.message,
            TextToSpeech.QUEUE_FLUSH,
            null,
            null
        )
    }

    private fun addUserMessage(message: String) {
        messageList.add(
            ChatMessage(message, true)
        )
        adapter.notifyItemInserted(
            messageList.lastIndex
        )
        scrollBottom()
    }

    private fun addBotMessage(response: ChatResponse) {
        messageList.add(
            ChatMessage(
                message = response.message,
                isUser = false,
                buttons = response.buttons
            )
        )
        adapter.notifyItemInserted(messageList.lastIndex)
        scrollBottom()
    }

    override fun onDestroyView() {
        speechRecognizer.destroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroyView()
    }
}