package ru.job4j.ood.isp.menu.actions;

import ru.job4j.ood.isp.menu.Input;
import ru.job4j.ood.isp.menu.Menu;
import ru.job4j.ood.isp.menu.Output;

public class SelectItemAction implements UserAction {

    private final Menu menu;

    private final Input input;

    private final Output output;

    public SelectItemAction(Menu menu, Input input, Output output) {
        this.menu = menu;
        this.input = input;
        this.output = output;
    }

    @Override
    public String name() {
        return "Select item";
    }

    @Override
    public boolean execute() {
        String menuItemName = input.askStr("=== Enter menu item element ===");
        if (menu.select(menuItemName).isEmpty()) {
            output.println("=== Menu element not found! ===");
            return true;
        }
        menu.select(menuItemName).get().getActionDelegate().delegate();
        return true;
    }
}
