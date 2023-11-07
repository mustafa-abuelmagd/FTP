import java.io.*;
import java.net.Socket;

public class Main {
    public static String takeInput() throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(reader);
        return buffer.readLine();
    }

    public static void main(String[] args) throws IOException {
        String machineName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        Socket socketClient = new Socket(machineName, portNumber);

        // Initialize DataInputStream and DataOutputStream
        DataInputStream input = new DataInputStream(socketClient.getInputStream());
        DataOutputStream output = new DataOutputStream(socketClient.getOutputStream());

        while (true) {
            System.out.print("mytftp> ");
            String command = takeInput();
            output.writeUTF(command);
            System.out.println(input.readUTF());

            // Close the streams if quit
            if (command.equalsIgnoreCase("quit")) {
                input.close();
                output.close();
                break;
            }
        }


    }
}