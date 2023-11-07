import java.io.File;

public class FTPServerOperations {


    public String makeDirectory(String folderName) {
        try {//create a folder using system
            File file = new File(System.getProperty("user.dir"));
            File directory = new File(file + File.separator + folderName);
            directory.mkdir();
            return "success";
        } catch (Exception exception) {
            return exception.toString();
        }
    }

    public String changeDirectory(String path) {
        try {
            File directory = new File(path);
            System.setProperty("user.dir", System.getProperty("user.dir") + File.separator + directory);

            File file = new File(System.getProperty("user.dir"));
            return file.getAbsolutePath();
        } catch (Exception exception) {
            return exception.toString();
        }
    }

    public String changeDirectoryPrevious(String path) {
        try {
            File file = new File(System.getProperty("user.dir"));
            String parentPath = file.getAbsoluteFile().getParentFile().getAbsolutePath();
            System.setProperty("user.dir", parentPath);


            file = new File(System.getProperty("user.dir"));
            return file.getAbsolutePath();
        } catch (Exception exception) {
            return exception.toString();
        }
    }


    public String DeleteDirectory(String path) {
        try {
            File file = new File(System.getProperty("user.dir"));
            file = new File(file.getAbsolutePath() + File.separator + path);

            boolean status = file.delete();

            return status ? "Success" : "Failed";
        } catch (Exception exception) {
            return exception.toString();
        }
    }

    public File[] ls() {
        File file = new File(System.getProperty("user.dir"));
        File[] files = file.listFiles();
        return files;
    }

    public File[] ls(File file) {
        System.out.println("came here::::  ");

        String filePath = file.getAbsolutePath();
        String[] path = filePath.split(File.separator);
        StringBuilder calculatedPath = new StringBuilder();

        for (int i = 0; i < path.length; i++) {
            calculatedPath.append(path[i]).append(File.separator);
        }
        filePath = calculatedPath.toString();
        System.out.println("came here11111::::  " + filePath);

        File file1 = new File(System.getProperty("user.dir") + File.separator + filePath);

        File[] files = file1.listFiles();
        for (int i = 0; i < files.length; i++) {
            System.out.println("came here2222::::  " + files[i].getAbsolutePath());
        }
        return files;
    }

    public String pwd() {
        return new File(System.getProperty("user.dir")).getAbsolutePath();
    }


}
