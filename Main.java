import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of stocks you want to view: ");
        int numberOfStocks = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String[] stockSymbols = new String[numberOfStocks];
        for (int i = 0; i < numberOfStocks; i++) {
            System.out.print("Enter stock symbol " + (i + 1) + ": ");
            stockSymbols[i] = scanner.nextLine();
        }

        int i = 0;
        while (i != 1) {
            String[] stockPrices = new String[numberOfStocks];
            
            for (int j = 0; j < numberOfStocks; j++) {
                String stockSymbol = stockSymbols[j];
                
                var request = HttpRequest.newBuilder()
                    .uri(URI.create("https://finnhub.io/api/v1/quote?symbol=%s".formatted(stockSymbol)))
                    .header("X-Finnhub-Token", "cp743jhr01qpb9rahbn0cp743jhr01qpb9rahbng").GET().build();

                var response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString()).body();

                var matcher = Pattern.compile("\"c\":(\\d+(\\.\\d+)?)").matcher(response);

                if (matcher.find()) {
                    stockPrices[j] = matcher.group(1);
                } else {
                    stockPrices[j] = "N/A";
                }
            }

            for (int j = 0; j < numberOfStocks; j++) {
                System.out.println("Current price of stock '%s' is %s USD".formatted(stockSymbols[j], stockPrices[j]));
            }

            try {
                // Wait for 3 seconds (3000 milliseconds)
                Thread.sleep(3000);
                System.out.println();
            } catch (InterruptedException e) {
                // Handle the exception if the sleep is interrupted
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}
