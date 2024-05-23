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

        StarterMessage starterMessage = new StarterMessage();
        starterMessage.starter();

        int numberOfStocks = 0;
        UserInteract userInteract = new UserInteract();
        numberOfStocks = userInteract.askNumber(numberOfStocks);

        String[] stockSymbols = new String[numberOfStocks];

        for (int i = 0; i < numberOfStocks; i++) {
            System.out.print("Enter stock symbol " + (i + 1) + ": ");
            stockSymbols[i] = scanner.nextLine();
        }

        int frequencyInSeconds = 0;
        frequencyInSeconds = userInteract.askFrequency(frequencyInSeconds);

        Double[] previousPrices = new Double[numberOfStocks];

        while (2 != 1) {
            Double[] stockPrices = new Double[numberOfStocks];
            
            for (int j = 0; j < numberOfStocks; j++) {
                String stockSymbol = stockSymbols[j];
                
                var request = HttpRequest.newBuilder()
                    .uri(URI.create("https://finnhub.io/api/v1/quote?symbol=%s".formatted(stockSymbol)))
                    .header("X-Finnhub-Token", "cp743jhr01qpb9rahbn0cp743jhr01qpb9rahbng").GET().build();

                var response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString()).body();

                var matcher = Pattern.compile("\"c\":(\\d+(\\.\\d+)?)").matcher(response);

                if (matcher.find()) {
                    stockPrices[j] = Double.parseDouble(matcher.group(1));
                } else {
                    stockPrices[j] = null;
                }
            }

            for (int j = 0; j < numberOfStocks; j++) {
                if (stockPrices[j] != null) {
                    String colorCode;
                    if (previousPrices[j] == null) {
                        colorCode = "\u001B[0m"; // No color for the first print
                    } else if (stockPrices[j] > previousPrices[j]) {
                        colorCode = "\u001B[32m"; // Green
                    } else if (stockPrices[j].equals(previousPrices[j])) {
                        colorCode = "\u001B[33m"; // Yellow
                    } else {
                        colorCode = "\u001B[31m"; // Red
                    }
                    System.out.println("-----------------------------------");
                    System.out.println(String.format("Current price of stock '%s' is %s%s USD\u001B[0m", stockSymbols[j], colorCode, stockPrices[j]));
                    System.out.println("-----------------------------------");
                    previousPrices[j] = stockPrices[j];
                } else {
                    System.out.println(String.format("Current price of stock '%s' is N/A", stockSymbols[j]));
                }
            }

            try {
                // Wait for the user-defined frequency
                Thread.sleep(frequencyInSeconds * 1000);
                System.out.println();
            } catch (InterruptedException e) {
                // Handle the exception if the sleep is interrupted
                e.printStackTrace();
            }
        }
    }
}
