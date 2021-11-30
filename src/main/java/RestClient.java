import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.logging.Log;

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


    public static void main(String[] args) {
            Scanner input = new Scanner(System.in);
            String request;
            System.out.println("Input which request you wish to perform GET(search), POST(Input data), PUT(update data or DELETE data): ");
            request = input.nextLine().toUpperCase();
            System.out.println();
            try {
                if (request.equals("GET")) {
                    httpGetRequest();//gets all in Database
                } else if (request.equals("POST")) {
                    httpPOSTRequest();
                } else if (request.equals("PUT")) {
                    httpPutRequest();
                } else {
                    httpDeleteRequest();
                }
            } catch (Exception e) {
                new Exception("must be one of the given examples please try again");
                e.printStackTrace();
            }
        }

        public static void httpGetRequest () throws IOException, InterruptedException {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/member")).build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    System.out.println("*****" + response.body());
                }

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void httpPOSTRequest () throws IOException, InterruptedException {
            Map<Object, Object> data = new HashMap<>();
            data.put("firstName", "Tom");
            data.put("lastName", "Hanks");
            data.put("address", "2 that street");
            data.put("email", "jdoiwhec@gmail.com");
            data.put("phone", 9283094);
            data.put("durationOfMembership", 2);
            //          data.put("membershipType", 107);
            data.put("currentTournaments", 122);
            data.put("pastTournaments", 123);
            data.put("futureTournaments", 124);
            data.put("startOfMembership", "05/05/2019");

//            data.put("finalStandings", "36th");
//            data.put("location", "St. John's");
//            data.put("participationMembers", "Double D");
            data.put("ts", System.currentTimeMillis());

            ObjectMapper dataMapper = new ObjectMapper();
            String stringifydataMapper = dataMapper.writeValueAsString(data);
            try {
                HttpClient client = HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_2)
                        .build();
                HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/member"))
                        .version(HttpClient.Version.HTTP_2)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(stringifydataMapper))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();
                System.out.println("httpPostRequest : " + responseBody);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void httpPutRequest () throws IOException, InterruptedException {
            Map<Object, Object> data = new HashMap<>();
        data.put("firstName", "Tom");
        data.put("lastName", "Hanks");
        data.put("address", "2 that street");
        data.put("email", "jdoiwhec@gmail.com");
        data.put("phone", 9283094);
        data.put("durationOfMembership", 2);
        //          data.put("membershipType", 107);
        data.put("currentTournaments", 122);
        data.put("pastTournaments", 123);
        data.put("futureTournaments", 124);
        data.put("startOfMembership", "06/05/2019");
       // data.put("pastNumTournaments", 123);
      // data.put("past_tournaments", 123);
//        data.put("entryFee", 100);
//        data.put("cashPrize", 500);
//        data.put("finalStandings", "36th");
//        data.put("location", "St. John's");
//        data.put("participatingMembers", "Double D");

            data.put("ts", System.currentTimeMillis());

            ObjectMapper dataMapper = new ObjectMapper();
            String stringifydataMapper = dataMapper.writeValueAsString(data);
            try {
                HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/updateMember/115"))
                        .version(HttpClient.Version.HTTP_2)
                        .header("Content-Type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(stringifydataMapper))
                        .build();

                HttpClient client = HttpClient.newHttpClient();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();
                System.out.println("httpPostRequest : " + responseBody);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }

        public static void httpDeleteRequest () throws IOException, InterruptedException {
            try {
                HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/deleteMember/136"))
                        .version(HttpClient.Version.HTTP_2)
                        .header("Content-Type", "application/json")
                        .DELETE()
                        .build();

                HttpClient client = HttpClient.newHttpClient();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();
                System.out.println("httpPostRequest : " + responseBody);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

