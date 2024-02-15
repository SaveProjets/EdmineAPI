package fr.edminecoreteam.api.spigot.menu;

public class MenuManager {

    public Menu createMenu(String name) {
        return new Menu(name);
    }

    public Menu createMenu(String name, int lines){
        return new Menu(name, lines);
    }

}
