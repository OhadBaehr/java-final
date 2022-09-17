import App.AppState;
import UI.ViewManager;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.geom.AffineTransform;

public class Main {
    public static void main(String[] args) {
        AppState.initDefaultState();
        ViewManager.initStyles();
        ViewManager.loginView();
    }
}