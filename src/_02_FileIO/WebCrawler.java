package _02_FileIO;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by houli on 2017/2/21.
 */
public class WebCrawler {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter a URL");
        String url = input.nextLine();
        crawler(url);
    }

    public static void crawler(String startURL) {
        ArrayList<String> listOfPendingURLS = new ArrayList<>();
        ArrayList<String> listOfTraversedURLS = new ArrayList<>();

        listOfTraversedURLS.add(startURL);
        while (!listOfPendingURLS.isEmpty() && listOfTraversedURLS.size() <= 100) {
            String urlString = listOfPendingURLS.remove(0);
            if (!listOfTraversedURLS.contains(urlString)) {
                listOfTraversedURLS.add(urlString);
                System.out.println("Crawl " + urlString);
                for (String s: getSubURLs(urlString)) {
                    if (!listOfTraversedURLS.contains(s))
                        listOfTraversedURLS.add(s);
                }
            }
        }

    }

    public static ArrayList<String> getSubURLs(String urlString) {
        ArrayList<String> list = new ArrayList<>();
        try {
            URL url = new URL(urlString);
            Scanner input = new Scanner(url.openStream());
            int current = 0;
            while (input.hasNext()) {
                String line = input.nextLine();
                current = line.indexOf("http:", current);
                while (current > 0) {
                    int endIndex = line.indexOf("\"", current);
                    if (endIndex > 0) { //Ensure that a correct URL is found
                        list.add(line.substring(current,endIndex));
                        current = line.indexOf("http:",endIndex);
                    }else
                        current = -1;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
