import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RestClient {


    public static void main(String[] args) throws IOException, InterruptedException {
        //httpPOSTRequest();
        //httpGetRequest();
        httpPutRequest();
        //httpDeleteRequest();
    }

    public static void httpGetRequest() throws IOException, InterruptedException {
       Scanner scanner = new Scanner(System.in);
        String search;
        System.out.println("Please enter a number.");
        search = scanner.next();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/members")).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode()==200) {
                System.out.println("*****" + response.body());
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void httpPOSTRequest() throws IOException, InterruptedException {
            Map<Object, Object> data = new HashMap<>();
            data.put("firstName", "Triple");
            data.put("lastName", "D");
            data.put("address", "33 that street");
            data.put("email", "webfiu@gmail.com");
            data.put("phone", 5555555);
            data.put("durationOfMembership", 1);
            data.put("membershipType", "VIP");
            data.put("currentNumTournaments", 3);
            data.put("pastNumTournaments", 2);
            data.put("futureNumTournaments", 6);

            data.put("finalStandings", "36th");
            data.put("location", "St. John's");
            data.put("participationMembers", "Double D");
            data.put("ts", System.currentTimeMillis());

            ObjectMapper dataMapper = new ObjectMapper();
            String stringifydataMapper = dataMapper.writeValueAsString(data);

            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_2)
                    .build();
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/save/"))
                    .version(HttpClient.Version.HTTP_2)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(stringifydataMapper))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println("httpPostRequest : " + responseBody);
        }

    public static void httpPutRequest() throws IOException, InterruptedException{
        Map<Object, Object> data = new HashMap<>();
        data.put("firstName", "Triple");
        data.put("lastName", "Doe");
        data.put("address", "33 that street");
        data.put("email", "webfiu@gmail.com");
        data.put("phone", 5555555);
        data.put("durationOfMembership", 1);
        data.put("membershipType", "VIP");
        data.put("currentNumTournaments", 3);
        data.put("pastNumTournaments", 2);
        data.put("futureNumTournaments", 6);
        data.put("entryFee", 100);
        data.put("cashPrize", 500);
        data.put("finalStandings", "36th");
        data.put("location", "St. John's");
        data.put("participatingMembers", "Double D");

        data.put("ts", System.currentTimeMillis());

        ObjectMapper dataMapper = new ObjectMapper();
        String stringifydataMapper = dataMapper.writeValueAsString(data);

        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/update/45"))
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(stringifydataMapper))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        System.out.println("httpPostRequest : " + responseBody);
    }

    public static void httpDeleteRequest() throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/delete/29"))
                .version(HttpClient.Version.HTTP_2)
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        System.out.println("httpPostRequest : " + responseBody);
    }

}
