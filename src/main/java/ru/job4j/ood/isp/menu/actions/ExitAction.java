package ru.job4j.ood.isp.menu.actions;

public class ExitAction implements UserAction {
    @Override
    public String name() {
        return "Exit";
    }

    @Override
    public boolean execute() {
        return false;
    }
}
