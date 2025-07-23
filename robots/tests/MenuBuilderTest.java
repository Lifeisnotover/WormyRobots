package tests;

import src.controller.MainApplicationFrame;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuBuilderTest {
    @Test
    void testFileMenuExists() {
        MainApplicationFrame frame = new MainApplicationFrame();
        JMenuBar menuBar = frame.getJMenuBar();
        assertNotNull(menuBar, "Меню-бар не должен быть null");

        JMenu fileMenu = null;
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            if ("Файл".equals(menuBar.getMenu(i).getText())) {
                fileMenu = menuBar.getMenu(i);
                break;
            }
        }

        assertNotNull(fileMenu, "Меню 'Файл' не найдено");
        assertEquals("Файл", fileMenu.getText(), "Некорректное название меню");

        boolean exitItemFound = false;
        for (int i = 0; i < fileMenu.getItemCount(); i++) {
            JMenuItem item = fileMenu.getItem(i);
            if (item != null && "Выход".equals(item.getText())) {
                exitItemFound = true;
                break;
            }
        }
        assertTrue(exitItemFound, "Пункт 'Выход' не найден в меню 'Файл'");

        frame.dispose();
    }
}