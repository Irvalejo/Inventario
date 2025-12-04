package ui;

import javax.swing.*;
import java.awt.*;

public class UiStyle {

    public static final Color AZUL_UNISON = Color.decode("#00529e");
    public static final Color AZUL_OSCURO_UNISON = Color.decode("#015294");
    public static final Color DORADO_UNISON = Color.decode("#f8bb00");
    public static final Color DORADO_OSCURO_UNISON = Color.decode("#d99e30");

    public static void applyGlobalStyle() {
        Font defaultFont = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("Label.font", defaultFont);
        UIManager.put("Button.font", defaultFont);
        UIManager.put("TextField.font", defaultFont);
        UIManager.put("PasswordField.font", defaultFont);
        UIManager.put("Table.font", defaultFont);
        UIManager.put("TableHeader.font", defaultFont);
        UIManager.put("ComboBox.font", defaultFont);
    }
}
