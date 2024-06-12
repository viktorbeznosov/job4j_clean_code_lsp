package ru.job4j.ood.isp.menu;

import java.util.*;

public class SimpleMenu implements Menu {

    private final List<MenuItem> rootElements = new ArrayList<>();

    @Override
    public boolean add(String parentName, String childName, ActionDelegate actionDelegate) {
        if (parentName == Menu.ROOT) {
            rootElements.add(new SimpleMenuItem(childName, actionDelegate));
            return true;
        }

        Optional<ItemInfo> parent = findItem(parentName);

        if (!parent.isEmpty()) {
            parent.get().menuItem.getChildren().add(new SimpleMenuItem(childName, actionDelegate));

            return true;
        }

        return false;
    }

    @Override
    public Optional<MenuItemInfo> select(String itemName) {
        Optional<MenuItemInfo> result = Optional.ofNullable(null);

        Optional<ItemInfo> item = findItem(itemName);
        if (item.isPresent()) {
            MenuItemInfo menuItemInfo = new MenuItemInfo(item.get().menuItem, item.get().number);
            result = Optional.ofNullable(menuItemInfo);
        }

        return result;
    }

    @Override
    public Iterator<MenuItemInfo> iterator() {
        return new Iterator<MenuItemInfo>() {

            private DFSIterator dfsIterator = new DFSIterator();

            @Override
            public boolean hasNext() {
                return dfsIterator.hasNext();
            }

            @Override
            public MenuItemInfo next() {
                ItemInfo next = dfsIterator.next();
                return new MenuItemInfo(next.menuItem, next.number);
            }
        };
    }

    private Optional<ItemInfo> findItem(String name) {
        DFSIterator iterator = new DFSIterator();
        ItemInfo result = null;

        while (iterator.hasNext()) {
            ItemInfo next = iterator.next();
            if (name.equals(next.menuItem.getName())) {
                result = next;
                break;
            }
        }

        return Optional.ofNullable(result);
    }

    private static class SimpleMenuItem implements MenuItem {

        private String name;
        private List<MenuItem> children = new ArrayList<>();
        private ActionDelegate actionDelegate;

        public SimpleMenuItem(String name, ActionDelegate actionDelegate) {
            this.name = name;
            this.actionDelegate = actionDelegate;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<MenuItem> getChildren() {
            return children;
        }

        @Override
        public ActionDelegate getActionDelegate() {
            return actionDelegate;
        }
    }

    private class DFSIterator implements Iterator<ItemInfo> {

        Deque<MenuItem> stack = new LinkedList<>();

        Deque<String> numbers = new LinkedList<>();

        DFSIterator() {
            int number = 1;
            for (MenuItem item : rootElements) {
                stack.addLast(item);
                numbers.addLast(String.valueOf(number++).concat("."));
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public ItemInfo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            MenuItem current = stack.removeFirst();
            String lastNumber = numbers.removeFirst();
            List<MenuItem> children = current.getChildren();
            int currentNumber = children.size();
            for (var i = children.listIterator(children.size()); i.hasPrevious();) {
                stack.addFirst(i.previous());
                numbers.addFirst(lastNumber.concat(String.valueOf(currentNumber--)).concat("."));
            }
            return new ItemInfo(current, lastNumber);
        }
    }

    private class ItemInfo {

        MenuItem menuItem;
        String number;

        public ItemInfo(MenuItem menuItem, String number) {
            this.menuItem = menuItem;
            this.number = number;
        }
    }

    public static void main(String[] args) {
        ActionDelegate stubAction = System.out::println;

        Menu menu = new SimpleMenu();

        menu.add(Menu.ROOT, "Сходить в магазин", stubAction);
        menu.add(Menu.ROOT, "Покормить собаку", stubAction);
        menu.add("Сходить в магазин", "Купить продукты", stubAction);
        menu.add("Купить продукты", "Купить хлеб", stubAction);
        menu.add("Купить продукты", "Купить молоко", stubAction);

        MenuPrinter printer = new Printer();
        printer.print(menu);
    }
}