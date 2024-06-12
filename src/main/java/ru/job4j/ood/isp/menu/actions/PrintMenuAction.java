package ru.job4j.ood.isp.menu.actions;

import ru.job4j.ood.isp.menu.Menu;
import ru.job4j.ood.isp.menu.MenuPrinter;
import ru.job4j.ood.isp.menu.Printer;

public class PrintMenuAction implements UserAction {

    private final Menu menu;

    private final MenuPrinter printer = new Printer();

    public PrintMenuAction(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String name() {
        return "Print menu";
    }

    @Override
    public boolean execute() {
        printer.print(menu);
        return true;
    }
}
