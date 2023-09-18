package com.nick.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nick.service.ChatbotService;

import java.util.Random;

@Service
public class ChatbotServiceImpl implements ChatbotService {

    @Value("${openweathermap.api.key}")
    private String openWeatherMapApiKey;

    private final String[] greetings = {
            "Hello! How can I assist you today?",
            "Hi there! How can I help you?",
            "Hey! What can I do for you?",
            "Greetings! How may I assist you?"
    };

    private final String[] farewell = {
            "Goodbye! Have a great day!",
            "Farewell! See you soon!",
            "Bye! Take care!",
            "Goodbye! Don't hesitate to return if you have more questions."
    };

    private final String[] weatherResponses = {
            "The weather in %s is currently %s with a temperature of %s.",
            "In %s, the weather is %s, and the temperature is %s.",
            "Currently, it's %s in %s with a temperature of %s."
    };
    
    private final String[] unknownResponses = {
            "I'm not sure what you're asking. Could you please clarify?",
            "Sorry, I didn't catch that. Can you rephrase your question?",
            "I didn't understand. Could you provide more details?"
    };

    private final String[] thankYouResponses = {
            "You're welcome! If you have more questions, feel free to ask.",
            "No problem! Let me know if there's anything else I can assist you with.",
            "You're welcome. Have a great day!",
    };

    private final String[] helpResponses = {
            "I can provide information about the weather. Just ask about a city's weather, like 'What's the weather like in Paris?'",
            "You can ask me about the weather in different cities. For example, 'Tell me the weather in London.'",
            "If you have questions, feel free to ask! I'm here to help."
    };

    private final String[] jokes = {
            "Why did the scarecrow win an award? Because he was outstanding in his field!",
            "How does a penguin build its house? Igloos it together!",
            "What do you call a fish with no eyes? Fsh!",
            "Why don't scientists trust atoms? Because they make up everything!",
            "Did you hear about the mathematician who's afraid of negative numbers? He'll stop at nothing to avoid them!",
            "Parallel lines have so much in common. It's a shame they'll never meet.",
            "Why did the bicycle fall over? Because it was two-tired!",
    };

    @Override
    public String respondToUserInput(String userInput) {
        // Normalize user input to lowercase for case-insensitive matching.
        userInput = userInput.toLowerCase();

        // Check for greetings
        if (containsAnyKeyword(userInput, new String[]{"hello", "hi", "hey"})) {
            return getRandomResponse(greetings);
        }

        // Check if the user is asking about the weather
        if (userInput.contains("weather")) {
            String city = extractCityFromInput(userInput);
            if (city != null) {
                String weatherInfo = getWeatherInfo(city);
                if (weatherInfo != null) {
                    return weatherInfo;
                }
            }
        }

        // Check for farewells
        if (containsAnyKeyword(userInput, new String[]{"bye", "goodbye"})) {
            return getRandomResponse(farewell);
        }

        // Check for "thank you" messages
        if (containsAnyKeyword(userInput, new String[]{"thank you", "thanks"})) {
            return getRandomResponse(thankYouResponses);
        }

        // Check for requests for help
        if (containsAnyKeyword(userInput, new String[]{"help", "assistance"})) {
            return getRandomResponse(helpResponses);
        }

        // Check for requests for jokes
        if (containsAnyKeyword(userInput, new String[]{"joke", "tell me a joke"})) {
            return getRandomResponse(jokes);
        }

        // Default response for other input
        return getRandomResponse(unknownResponses);
    }

    private boolean containsAnyKeyword(String input, String[] keywords) {
        for (String keyword : keywords) {
            if (input.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private String extractCityFromInput(String userInput) {
        // Implement city extraction logic from user input.
        // For simplicity, you can assume that the user's input contains the city name.
        // In a real-world application, you would use NLP techniques to extract location information.
        return "New York"; // Example: You can modify this to extract the city from user input.
    }

    private String getWeatherInfo(String city) {
        // Implement code to fetch weather information from an API (e.g., OpenWeatherMap API).
        // You would need to replace this with your actual API call code.

        // Example: Assuming you have an API key and a RestTemplate configured in your Spring Boot application.
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + openWeatherMapApiKey;

        // Replace this with actual API call code to fetch weather data.
        // For simplicity, we return a static response.
        String weatherDescription = "sunny";
        String temperature = "25°C";
        return String.format(getRandomResponse(weatherResponses), city, weatherDescription, temperature);
    }

    private String getRandomResponse(String[] responses) {
        Random rand = new Random();
        int index = rand.nextInt(responses.length);
        return responses[index];
    }
}
