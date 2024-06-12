package ru.job4j.ood.isp.menu;

public class Printer implements MenuPrinter {
    @Override
    public void print(Menu menu) {
        menu.forEach(i -> {
            int indentationLength = i.getNumber().split("\\.").length - 1;
            System.out.println(indentation(indentationLength) + i.getNumber() + i.getName());
        });
    }

    private String indentation(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append("--");
        }
        return builder.toString();
    }
}
