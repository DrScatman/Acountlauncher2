package main;

import java.io.*;
import java.io.IOException;
import java.net.Proxy;
import java.util.*;

import static java.lang.Thread.sleep;


public class Account_Launcher {

    private static final String SCRIPT_NAME = "LOL";
    private static final int WORLD = 454;
    public static final int LAUNCHER_INDEX = 0;
    public static final String API_KEY = "S1Z8S8QHPE0LST3E2H07T8YABM63L17AW738NN61LAT0CT9NQG38JLDUDY7FCX5YG0ZVZ4";

    public static boolean isRunning = true;
    public static int clients;
    public static int i = 0;
    public static String Accounts[];
    static ArrayList<String> AccountFinal = new ArrayList<>();
    public static String Jsonconfig;
    public static String Proxies[];
    static ArrayList<String> proxyfinal = new ArrayList<>();
    static ArrayList<String> proxy = new ArrayList<>();
    static ArrayList<String> port = new ArrayList<>();
    private static int proxies = 0;

    public static void main(String[] args) throws Exception {
        Scanner inFile = new Scanner(new File(System.getProperty("user.home") + "\\Desktop\\proxies.txt"));
        ArrayList<String[]> temps = new ArrayList<>();

        while (inFile.hasNextLine()) {
            // find next line
            String line = inFile.nextLine();
            Proxies = line.split("\\s+");
            temps.add(Proxies);

        }

        inFile.close();

        temps.forEach(Accounts -> {
            proxyfinal.add(Arrays.toString(Accounts));
        });


        Scanner inFile1 = new Scanner(new File(System.getProperty("user.home") + "\\Desktop\\accounts.txt"));
        ArrayList<String[]> temps1 = new ArrayList<>();

        while (inFile1.hasNextLine()) {
            // find next line
            String line = inFile1.nextLine();
            Accounts = line.split("\\s+");
            temps1.add(Accounts);

        }

        inFile1.close();

        temps1.forEach(Proxies -> {
            AccountFinal.add(Arrays.toString(Proxies));
        });

        while(isRunning) {
            for(String el : proxyfinal){
                String result = el.replaceAll("^\\[|]$", "");
                String namepass[] = result.split(":");
                proxy.add(namepass[0]);
                port.add(namepass[1]);
                String lala = namepass[0];

            }
            for (i = 0; i <= AccountFinal.size(); i++) {
                System.out.println(AccountFinal.size() + " account size");
                System.out.println(i);
                System.out.println(clients + " Clients are open");
                BufferedWriter out = null;
                String temp[] = AccountFinal.get(i).split(":");
                String Username = temp[0].replaceAll("\\[", "");
                String Password = temp[1].replaceAll("]", "");
                try {
                    ClientQuickLauncher launcher = new ClientQuickLauncher(SCRIPT_NAME, false, WORLD);
                    launcher.launchClient(new String[]{
                            Username,
                            Password,
                            proxy.get(proxies),
                            port.get(proxies)
                    });
                } catch (Exception e) {
                    i --;
                    e.printStackTrace();
                }
                System.out.println("Sending account: " + AccountFinal.get(i));
                sleep(15_000);
                proxies = proxies + 1;
            }
            if(Proxies.length >= proxies){
                System.out.println(Proxies.length);
                System.out.println(proxies);
                System.out.println("End of proxies, looping back through");
                proxies = 0;
            }
        }
    }
}
