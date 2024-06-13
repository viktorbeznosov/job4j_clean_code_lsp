package ru.job4j.ood.isp.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

class PrinterTest {

    public static final ActionDelegate STUB_ACTION = System.out::println;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void whenOnlyRootElementsThenNoIndents() {
        Menu menu = new SimpleMenu();
        MenuPrinter printer = new Printer();
        menu.add(Menu.ROOT, "First", STUB_ACTION);
        menu.add(Menu.ROOT, "Second", STUB_ACTION);
        menu.add(Menu.ROOT, "Third", STUB_ACTION);

        StringBuilder builder = new StringBuilder();
        builder
            .append("1.First")
            .append(System.lineSeparator())
            .append("2.Second")
            .append(System.lineSeparator())
            .append("3.Third");

        printer.print(menu);

        assertThat(builder.toString()).isEqualTo(outputStreamCaptor.toString().trim());
    }

    @Test
    public void whenChildrenElementsThenIndents() {
        Menu menu = new SimpleMenu();
        MenuPrinter printer = new Printer();
        menu.add(Menu.ROOT, "Parent1", STUB_ACTION);
        menu.add(Menu.ROOT, "Parent2", STUB_ACTION);
        menu.add("Parent1", "Child1", STUB_ACTION);
        menu.add("Parent2", "Child2", STUB_ACTION);
        StringBuilder builder = new StringBuilder();

        builder.append("1.Parent1")
            .append(System.lineSeparator())
            .append("--1.1.Child1")
            .append(System.lineSeparator())
            .append("2.Parent2")
            .append(System.lineSeparator())
            .append("--2.1.Child2");

        printer.print(menu);

        assertThat(builder.toString()).isEqualTo(outputStreamCaptor.toString().trim());
    }

}