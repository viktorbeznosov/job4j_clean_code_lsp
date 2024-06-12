package ru.job4j.ood.isp.menu.actions;

import ru.job4j.ood.isp.menu.Input;
import ru.job4j.ood.isp.menu.Menu;
import ru.job4j.ood.isp.menu.Output;
import ru.job4j.ood.isp.menu.TodoApp;

public class AddChildElementAction implements UserAction {

    private final Menu menu;

    private final Input input;

    private final Output output;

    public AddChildElementAction(Menu menu, Input input, Output output) {
        this.menu = menu;
        this.input = input;
        this.output = output;
    }

    @Override
    public String name() {
        return "Add child";
    }

    @Override
    public boolean execute() {
        String parentMenuItemName = input.askStr("=== Enter parent element ===");
        String menuItemName = input.askStr("=== Enter menu item element ===");
        if (!menu.add(parentMenuItemName, menuItemName, TodoApp.DEFAULT_ACTION)) {
            output.println("=== Parent element not found! ===");
        }
        return true;
    }
}
