package ru.job4j.ood.isp.menu.actions;

import ru.job4j.ood.isp.menu.Input;
import ru.job4j.ood.isp.menu.Menu;
import ru.job4j.ood.isp.menu.TodoApp;

public class AddRootElementAction implements UserAction {

    private final Menu menu;

    private final Input input;

    public AddRootElementAction(Menu menu, Input input) {
        this.menu = menu;
        this.input = input;
    }

    @Override
    public String name() {
        return "Add root element";
    }

    @Override
    public boolean execute() {
        String menuItemName = input.askStr("=== Enter menu item element ===");
        menu.add(Menu.ROOT, menuItemName, TodoApp.DEFAULT_ACTION);
        return true;
    }
}
