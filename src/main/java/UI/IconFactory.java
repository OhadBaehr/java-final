package UI;

import javax.swing.*;

public class IconFactory {
    public static ImageIcon createIcon(String icon){
        return  new ImageIcon(System.getProperty("user.dir")+"/images/"+ icon + ".png");
    }
}
