package obsidian.System.BootLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PackageManager {
    private static ArrayList<Package> cachedPackages = new ArrayList<>();

    public static Package getPackage(String name) {
        for (Package p : cachedPackages) {
            if (p.getName().equals(name))
                return p;
        }
        return null;
    }

    public static void addPackage(Package p) {
        cachedPackages.add(p);
    }

    public static void removePackage(Package p) {
        cachedPackages.remove(p);
    }

    public static boolean createDirectory(String directory) {
        return new File(directory).mkdir();
    }

    public static boolean createFile(String fileDirectory){
        try {
            return new File(fileDirectory).createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
