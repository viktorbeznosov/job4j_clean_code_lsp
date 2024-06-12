package ru.job4j.ood.isp.menu;

import ru.job4j.ood.isp.menu.actions.*;

import java.util.Scanner;

public class TodoApp {
    public final static ActionDelegate DEFAULT_ACTION = () -> System.out.println("Some Action");

    private final Input in;

    private final Output out;

    TodoApp(Input in, Output out) {
        this.in = in;
        this.out = out;
    }

    public void init(UserAction[] actions) {
        Scanner scanner = new Scanner(System.in);
        boolean run = true;
        while (run) {
            showMenu(actions);
            int select = in.askInt("Select:");
            UserAction action = actions[select];
            run = action.execute();
        }
    }

    private void showMenu(UserAction[] actions) {
        out.println("Menu:");
        for (int index = 0; index < actions.length; index++) {
            out.println(index + ". " + actions[index].name());
        }
    }

    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        Input input = new ValidateInput(output, new ConsoleInput());
        TodoApp app = new TodoApp(input, output);
        Menu menu = new SimpleMenu();
        UserAction[] actions = {
            new AddRootElementAction(menu, input),
            new AddChildElementAction(menu, input, output),
            new SelectItemAction(menu, input, output),
            new PrintMenuAction(menu),
            new ExitAction(),
        };
        app.init(actions);
    }
}
