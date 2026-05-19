package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateSql(String userprompt){
        String prompt = """
                Convert the following natural language into PostgreSQL SQL query.
                Return ONLY SQL. Do not include markdown formatting.
                Database Tables:
                customers(id, customer_name, total_orders)
                orders(id, amount, customer_id)

                User Prompt:
                """ + userprompt;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestbody = Map.of("contents", List.of(
                Map.of("parts", List.of(Map.of("text", prompt)))));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestbody, headers);

        String finalUrl = apiUrl + "?key=" + apiKey;

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(finalUrl, entity, String.class);

            // FORCE SYSTEM OUT TO VERIFY HTTP BODY ARRIVAL
            System.out.println("=== 1. RAW HTTP RESPONSE FROM GEMINI ===");
            System.out.println(response.getBody());
            System.out.println("========================================");

            return extractSql(response.getBody());
        }catch (HttpClientErrorException.TooManyRequests ex) {

            System.out.println("=== 429 RESPONSE BODY ===");
            System.out.println(ex.getResponseBodyAsString());

            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    ex.getResponseBodyAsString()
            );
        }
        catch (HttpClientErrorException.Unauthorized ex) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid Gemini API Key"
            );
        }
        catch (Exception ex) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "AI generation failed"
            );
        }
    }

    private String extractSql(String responseBody) {
        if (responseBody == null || responseBody.isBlank()) {
            System.out.println("=== EXTRACT SQL ERROR: responseBody is NULL or Empty ===");
            return "";
        }

        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode textNode = root.path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text");

            String rawText = textNode.asText();

            System.out.println("=== 2. EXTRACTED TEXT FROM JSON ===");
            System.out.println(rawText);
            System.out.println("===================================");

            String finalSql = cleanMarkdown(rawText).replaceAll("\\s+", " ").trim();

            System.out.println("=== 3. FINAL CLEANED SQL SENT TO VALIDATION ===");
            System.out.println(finalSql);
            System.out.println("================================================");


            return finalSql;

        } catch (Exception e) {
            System.err.println("=== 2. JSON PARSING EXCEPTION ===");
            e.printStackTrace();
            return "";
        }
    }

    private String cleanMarkdown(String text) {
        if (text == null) return "";
        String cleaned = text.trim();

        if (cleaned.contains("```")) {
            String[] parts = cleaned.split("```");
            for (String part : parts) {
                String segment = part.trim();
                if (segment.toLowerCase().startsWith("sql")) {
                    segment = segment.substring(3).trim();
                }
                if (segment.toUpperCase().startsWith("SELECT")) {
                    return segment;
                }
            }
        }

        return cleaned.replace("```sql", "").replace("```", "").trim();
    }
}