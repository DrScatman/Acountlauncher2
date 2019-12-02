import java.io.*;
import java.io.IOException;
import java.net.Proxy;
import java.util.*;

import static java.lang.Thread.sleep;


public class Account_Launcher {
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

        Scanner inFile = new Scanner(new File("C:\\Users\\Laure\\Desktop\\proxies.txt"));
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


        Scanner inFile1 = new Scanner(new File("C:\\Users\\Laure\\Desktop\\accounts.txt"));
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
            String findProcess = "java.exe";
            String filenameFilter = "/nh /fi \"Imagename eq " + findProcess + "\"";
            String tasksCmd = System.getenv("windir") + "/system32/tasklist.exe " + filenameFilter;
            Process p = Runtime.getRuntime().exec(tasksCmd);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            ArrayList<String> procs = new ArrayList<String>();
            String line = null;
            while ((line = input.readLine()) != null) procs.add(line);
            input.close();
            int size = procs.size();
            Set<String> unique = new HashSet<String>(procs);
            for (String key : unique) {
                clients = size;
            }
            Boolean processFound = procs.stream().filter(row -> row.indexOf(findProcess) > -1).count() > 0;
// Head-up! If no processes were found - we still get:
// "INFO: No tasks are running which match the specified criteria."
            for (i = 0; i <= AccountFinal.size(); i++) {
                System.out.println(AccountFinal.size() + " account size");
                System.out.println(i);
                System.out.println(clients + " Clients are open");
                BufferedWriter out = null;
                String temp[] = AccountFinal.get(i).split(":");
                String Username = temp[0].replaceAll("\\[", "");
                String Password = temp[1].replaceAll("]", "");
                try {
                    FileWriter fstream = new FileWriter("C:\\Users\\Laure\\Desktop\\config.json", false); //true tells to append data.
                    out = new BufferedWriter(fstream);
                    Random r = new Random();
                    int randInt = r.nextInt(504-497) + 497;
                    out.write("{\"RsUsername\":"+Username+",\"RsPassword\":"+Password+",\"World\":393,\"ScriptName\":LOL,\"IsRepoScript\":false,\"ScriptArgs\":\"64.79.234.5\",\"UseProxy\":true,\"ProxyPort\":\""+ port.get(proxies) +"\",\"ProxyIp\":\""+ proxy.get(proxies) +"\",\"ProxyUser\":\"zaksmithcomputing\",\"ProxyPass\":\"REMGBEAU6W45UC4XBW2HFLN1\",\"Config\":{\"LowCpuMode\":true,\"SuperLowCpuMode\":true,\"EngineTickDelay\":0,\"DisableModelRendering\":true,\"DisableSceneRendering\":true}}");
                }

                catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                }

                finally {
                    if(out != null) {
                        out.close();
                    }
                }
                ProcessBuilder(Jsonconfig);
                System.out.println("Sending account: " + AccountFinal.get(i));
                sleep(10000);
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

    public static void ProcessBuilder(String Jsonconfig) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(new String[]{"java", "-jar", "-Xmx326m", "C:\\Users\\Laure\\.rspeer\\2.10.jar", "-XX:+SuppressFatalErrorMessage2", "-qsArgs", "\"C:\\Users\\Laure\\Desktop\\config.json\""});
        builder.redirectErrorStream(true);
        Process p = builder.start();
    }
}
