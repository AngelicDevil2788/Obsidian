package obsidian.System.BootLoader;

import java.io.File;

public class Package {
    private File source;
    private String name;

    public Package(String name, File source) {
        this.name = name;
        this.source = source;
    }

    public Package() {};

    public File getSource() {
        return source;
    }

    public void setSource(File source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
