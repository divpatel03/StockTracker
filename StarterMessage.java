public class StarterMessage {
    public void starter(){
        String purple = "\u001B[35m";
        String reset = "\u001B[0m"; 

        System.out.println(purple+ "-----------------------------------");
        System.out.println("Welcome to StockTracker");
        System.out.println("Your Personal Investment Profile");
        System.out.println("-----------------------------------" +reset);
    }
}
