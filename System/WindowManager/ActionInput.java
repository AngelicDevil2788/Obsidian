package obsidian.System.WindowManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ActionInput implements ActionListener {
    public ArrayList<Component> objects = new ArrayList<>();
    public ArrayList<Integer> activatedApps = new ArrayList<>();
    private Window window;
    public ActionInput(Window win) {
        this.window = win;
    }
    public void testForAction() {

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < objects.size(); i++) {
            if (e.getSource() == objects.get(i)) {
                activatedApps.add(i);
            }
        }
    }
}
