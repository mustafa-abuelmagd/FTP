import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static String[] splitCommand(String command) {
        return command.split(" ");
    }

    public static void main(String[] args) {


        FTPServerOperations myFTPServer = new FTPServerOperations();

        //take the port number from the args of the process
        int portNumber = Integer.parseInt(args[0]);


        try {
            // set up the socket FTP port numbers are 21 and 22
            ServerSocket socket = new ServerSocket(portNumber);
            System.out.println("socket::  " + socket);

            Socket socketCon = socket.accept();
            System.out.println("socketCon::  " + socketCon);


            // set up the input and the output streams from and to the terminal
            DataInputStream inputStream = new DataInputStream(socketCon.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socketCon.getOutputStream());
            String inputString = null;


            //take inputs from the terminal and do operations based on them
            while (true) {
                inputString = inputStream.readUTF();
                System.out.println("received command:  " + inputString);

                if (splitCommand(inputString)[0].equalsIgnoreCase("mkdir")) {
                    outputStream.writeUTF(myFTPServer.makeDirectory(splitCommand(inputString)[1]));
                } else if (splitCommand(inputString)[0].equalsIgnoreCase("cd")) {
                    String subCommand = splitCommand(inputString)[1];
                    if (subCommand.equalsIgnoreCase("..")) {
                        outputStream.writeUTF(myFTPServer.changeDirectoryPrevious(subCommand));
                    } else {
                        outputStream.writeUTF(myFTPServer.changeDirectory(subCommand));
                    }
                } else if (splitCommand(inputString)[0].equalsIgnoreCase("rm")) {
                    outputStream.writeUTF(myFTPServer.DeleteDirectory(splitCommand(inputString)[1]));
                } else if (splitCommand(inputString)[0].equalsIgnoreCase("pwd")) {
                    outputStream.writeUTF(myFTPServer.pwd());
                } else if (splitCommand(inputString)[0].equalsIgnoreCase("ls")) {


                    File[] files;
                    StringBuilder allPath = new StringBuilder();
                    if (splitCommand(inputString).length == 1) {
                        files = myFTPServer.ls();

                        for (int i = 0; i < files.length; i++) {
                            System.out.println("file:  " + files[i].getName());
                            allPath.append("  ").append(files[i].getName()).append('\t');
                        }
                    } else {
                        files = myFTPServer.ls(new File(splitCommand(inputString)[1]));
                        for (File file : files) {
                            allPath.append("  ").append(file.getName()).append('\t');
                        }
                    }
                    outputStream.writeUTF(allPath.toString());
                } else if (splitCommand(inputString)[0].equalsIgnoreCase("upload")) {
                    String fileName = "";
                    String pathName = "";
                    if (splitCommand(inputString)[1].contains(File.separator)) {
                        String filePath = splitCommand(inputString)[1];
                        String[] pathArr = filePath.split(File.separator);
                        fileName = pathArr[pathArr.length - 1];
                    } else {
                        fileName = splitCommand(inputString)[1];
                    }
                    File file = new File(System.getProperty("user.dir"));
                    String filePath = file.getAbsolutePath();

                    FileOutputStream newFile = new FileOutputStream(filePath + File.separator + fileName);
                    int characters = 0;
                    while (characters != -1) {
                        characters = Integer.parseInt(inputStream.readUTF());
                        newFile.write(characters);
                    }
                    newFile.close();
                    System.out.println("File uploaded successfully!");


                } else if (splitCommand(inputString)[0].equalsIgnoreCase("download")) {
                    FileInputStream downloadedFile = new FileInputStream(splitCommand(inputString)[1]);
                    int characters;
                    while (downloadedFile.read() != -1) {
                        characters = downloadedFile.read();
                        outputStream.writeUTF(String.valueOf(characters));
                    }
                    downloadedFile.close();
                }


                // close input and output streams
                else if (inputString.equalsIgnoreCase("quit")) {
                    inputStream.close();
                    outputStream.close();
                    break;
                }
            }

        } catch (Exception exception) {
            System.out.println("exception" + exception + " exception " + exception.getMessage());
        }

    }
}