import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;

public class VendingMachineDriver {

    public static VendingMachine vm;
    public static GridBagConstraints s = new GridBagConstraints();
    public static String selection1 = "";
    public static String selection2 = "";
    public static JLabel selection;
    public static JLabel resultLabel;
    public static JLabel remainLabel;
    public static VendingMachine drinkvm;
    public static VendingMachine snacksvm;

    public static void main(String[] args) {
        // write your code here
        try {
            drinkvm = new VendingMachine("drinks.txt");
            snacksvm = new VendingMachine("snacks.txt");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(new JFrame(), "File not found! Please check the file name!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        selection();
    }

    private static void selection() {
        JFrame frame = new JFrame("Vending Machine Selection");
        frame.setSize(400, 100);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new FlowLayout());

        JLabel userLabel = new JLabel("Please select a Vending Machine");
        panel.add(userLabel);

        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Snacks");
        comboBox.addItem("Drinks");
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int select = comboBox.getSelectedIndex();
                frame.dispose();
                input(select);
            }
        });
        panel.add(comboBox);
        frame.setVisible(true);
    }

    private static void mainpage() {
        JFrame frame = new JFrame("Vending Machine Selection");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainpanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        mainpanel.setLayout(layout);
        frame.add(mainpanel);
        initSearchPanel(mainpanel, layout);
        initSelectionPanel(mainpanel, layout);
        initListPanel(mainpanel, layout);
        initButtonPanel(frame, mainpanel, layout);
        frame.setVisible(true);
    }

    private static void initButtonPanel(JFrame frame, JPanel mainpanel, GridBagLayout layout) {
        JPanel buttonpanel = new JPanel();

        GridBagLayout buttonlayout = new GridBagLayout();
        buttonpanel.setLayout(buttonlayout);
        JButton change = new JButton("Get Change");
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new JFrame(), "Here is your change " + remainLabel.getText() + ". Thank you for using! Good Bye!", "Error",
                        JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                vm.setMoney(0);
                selection();
            }
        });
        buttonpanel.add(change);
        s.gridwidth = 1;
        s.gridx = 0;
        s.gridy = 0;
        s.weightx = 0;
        s.weighty = 0;
        buttonlayout.setConstraints(change, s);

        JButton addMoney = new JButton("Add Money");
        addMoney.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String add;
                double addmoney = 0;
                boolean transfer = false;
                while (!transfer) {
                    add = JOptionPane.showInputDialog(null, "How much money would you like to add", "Add Money", JOptionPane.QUESTION_MESSAGE);
                    transfer = false;
                    try {
                        addmoney = Double.parseDouble(add);
                        transfer = true;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(new JFrame(), "Please enter a valid number and try again!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                vm.setMoney(vm.getMoney() + addmoney);
                DecimalFormat df = new DecimalFormat("#.00");
                remainLabel.setText("$" + df.format(vm.getMoney()));
            }
        });
        buttonpanel.add(addMoney);
        s.gridwidth = 0;
        s.gridx = 1;
        s.gridy = 0;
        s.weightx = 0;
        s.weighty = 0;
        buttonlayout.setConstraints(addMoney, s);

        mainpanel.add(buttonpanel);
        s.gridwidth = 1;
        s.gridx = 0;
        s.gridy = 2;
        s.weightx = 0;
        s.weighty = 0;
        layout.setConstraints(buttonpanel, s);

        JButton vend = new JButton("Vend!");
        vend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = selection.getText();
                if (selected.length() < 2) {
                    JOptionPane.showMessageDialog(new JFrame(), "Please select an item and try again!", "Error",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    int letter = 0;
                    switch (selected.charAt(0)) {
                        case 'A':
                            letter = 0;
                            break;
                        case 'B':
                            letter = 1;
                            break;
                        case 'C':
                            letter = 2;
                            break;
                        case 'D':
                            letter = 3;
                            break;
                        case 'E':
                            letter = 4;
                            break;
                        case 'F':
                            letter = 5;
                            break;
                    }
                    int index = letter * 6 + Integer.parseInt(selected.substring(1)) - 1;
                    if (vm.getStock()[index].getQuantity() > 0) {
                        boolean vending = vm.vend(index);
                        if (vending) {
                            DecimalFormat df = new DecimalFormat("#.00");
                            JOptionPane.showMessageDialog(new JFrame(), selected + " - " + vm.getStock()[index].getDescription() + " has successfully vended! Your change is $" + df.format(vm.getMoney()) + " Thank you for your using! Good Bye!", "Error",
                                    JOptionPane.INFORMATION_MESSAGE);
                            frame.dispose();
                            vm.setMoney(0);
                            selection();
                        } else {
                            JOptionPane.showMessageDialog(new JFrame(), "Your money is not enough for " + selected + " - " + vm.getStock()[index].getDescription() + ". Please select a different item or add more money and try again!", "Error",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), selected + " - " + vm.getStock()[index].getDescription() + " is out of stock! Please select a different item and try again!", "Error",
                                JOptionPane.WARNING_MESSAGE);
                    }

                }
            }
        });
        mainpanel.add(vend);
        s.gridwidth = 1;
        s.gridx = 1;
        s.gridy = 2;
        s.weightx = 0;
        s.weighty = 0;
        layout.setConstraints(vend, s);
    }

    private static void initSearchPanel(JPanel mainpanel, GridBagLayout layout) {
        JPanel searchpanel = new JPanel();
        searchpanel.setLayout(new GridLayout(2, 2));
        JLabel searchLabel = new JLabel("Search for Item:");
        searchpanel.add(searchLabel);

        JTextField userText = new JTextField(10);
        userText.addActionListener(new Listener());
        searchpanel.add(userText);

        JLabel blankLabel = new JLabel("");
        searchpanel.add(blankLabel);

        resultLabel = new JLabel("");
        searchpanel.add(resultLabel);

        mainpanel.add(searchpanel);
        s.gridwidth = 1;
        s.gridx = 0;
        s.gridy = 0;
        s.weightx = 0;
        s.weighty = 0;
        layout.setConstraints(searchLabel, s);

    }

    private static void initSelectionPanel(JPanel mainpanel, GridBagLayout layout) {
        JPanel selectionPanel = new JPanel();
        GridBagLayout selectionlayout = new GridBagLayout();
        selectionPanel.setLayout(selectionlayout);

        JLabel searchLabel = new JLabel("Make a selection:");
        selectionPanel.add(searchLabel);
        s.gridwidth = 0;
        s.gridx = 0;
        s.gridy = 0;
        s.weightx = 0;
        s.weighty = 0;
        s.insets = new Insets(0, 0, 50, 0);
        selectionlayout.setConstraints(searchLabel, s);

        /*
         *Letter Panel setter
         */
        JPanel letter = new JPanel();
        letter.setLayout(new GridLayout(3, 2, 5, 5));
        letter.setBorder(BorderFactory.createLineBorder(Color.black));
        JButton A = new JButton("A");
        A.addActionListener(new Listener());
        A.setPreferredSize(new Dimension(40, 40));
        letter.add(A);
        JButton B = new JButton("B");
        B.addActionListener(new Listener());
        B.setPreferredSize(new Dimension(40, 40));
        letter.add(B);
        JButton C = new JButton("C");
        C.addActionListener(new Listener());
        C.setPreferredSize(new Dimension(40, 40));
        letter.add(C);
        JButton D = new JButton("D");
        D.addActionListener(new Listener());
        D.setPreferredSize(new Dimension(40, 40));
        letter.add(D);
        JButton E = new JButton("E");
        E.addActionListener(new Listener());
        E.setPreferredSize(new Dimension(40, 40));
        letter.add(E);
        JButton F = new JButton("F");
        F.addActionListener(new Listener());
        F.setPreferredSize(new Dimension(40, 40));
        letter.add(F);

        selectionPanel.add(letter);
        s.gridwidth = 1;
        s.gridx = 0;
        s.gridy = 1;
        s.weightx = 0;
        s.weighty = 0;
        s.insets = new Insets(0, 20, 20, 0);
        selectionlayout.setConstraints(letter, s);

        /*
         *Number Panel setter
         */
        JPanel number = new JPanel();
        number.setLayout(new GridLayout(3, 2, 5, 5));
        number.setBorder(BorderFactory.createLineBorder(Color.black));
        JButton one = new JButton("1");
        one.addActionListener(new Listener());
        one.setPreferredSize(new Dimension(40, 40));
        number.add(one);
        JButton two = new JButton("2");
        two.addActionListener(new Listener());
        two.setPreferredSize(new Dimension(40, 40));
        number.add(two);
        JButton three = new JButton("3");
        three.addActionListener(new Listener());
        three.setPreferredSize(new Dimension(40, 40));
        number.add(three);
        JButton four = new JButton("4");
        four.addActionListener(new Listener());
        four.setPreferredSize(new Dimension(40, 40));
        number.add(four);
        JButton five = new JButton("5");
        five.addActionListener(new Listener());
        five.setPreferredSize(new Dimension(40, 40));
        number.add(five);
        JButton six = new JButton("6");
        six.addActionListener(new Listener());
        six.setPreferredSize(new Dimension(40, 40));
        number.add(six);
        selectionPanel.add(number);

        s.gridwidth = 0;
        s.gridx = 1;
        s.gridy = 1;
        s.weightx = 0;
        s.weighty = 0;
        s.insets = new Insets(0, 0, 20, 20);
        selectionlayout.setConstraints(number, s);

        s.insets = new Insets(0, 0, 0, 0);


        JLabel selectionLabel = new JLabel("Item Selection:", SwingConstants.LEFT);
        selectionPanel.add(selectionLabel);

        s.gridwidth = 1;
        s.gridx = 0;
        s.gridy = 2;
        s.weightx = 0;
        s.weighty = 0;
        selectionlayout.setConstraints(selectionLabel, s);

        selection = new JLabel(selection1 + selection2);
        selection.setPreferredSize(new Dimension(80, 30));
        selection.setBorder(BorderFactory.createLineBorder(Color.black));
        selectionPanel.add(selection);

        s.gridwidth = 0;
        s.gridx = 1;
        s.gridy = 2;
        s.weightx = 0;
        s.weighty = 0;
        s.insets = new Insets(0, 0, 20, 0);
        selectionlayout.setConstraints(selection, s);

        s.insets = new Insets(0, 0, 0, 0);
        JLabel moneyRemainLabel = new JLabel("Money Remaining:", SwingConstants.LEFT);
        selectionPanel.add(moneyRemainLabel);

        s.gridwidth = 1;
        s.gridx = 0;
        s.gridy = 3;
        s.weightx = 0;
        s.weighty = 0;
        selectionlayout.setConstraints(moneyRemainLabel, s);
        DecimalFormat df = new DecimalFormat("#.00");
        remainLabel = new JLabel("$" + df.format(vm.getMoney()));
        remainLabel.setPreferredSize(new Dimension(80, 30));
        remainLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        selectionPanel.add(remainLabel);

        s.gridwidth = 0;
        s.gridx = 1;
        s.gridy = 3;
        s.weightx = 0;
        s.weighty = 0;
        s.insets = new Insets(0, 0, 20, 0);
        selectionlayout.setConstraints(remainLabel, s);

        s.insets = new Insets(0, 0, 0, 0);


        mainpanel.add(selectionPanel);
        s.gridwidth = 1;
        s.gridx = 0;
        s.gridy = 1;
        s.weightx = 0;
        s.weighty = 0;
        layout.setConstraints(selectionPanel, s);
    }


    private static void initListPanel(JPanel mainpanel, GridBagLayout layout) {
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BorderLayout());
        JList list = new JList(vm.getStock());
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Item) {
                    // Here value will be of the Type 'CD'
                    String text = initSelect(index) + ": " + ((Item) value).getDescription() + " - $" + ((Item) value).getPrice() + " (" + ((Item) value).getQuantity() + ")";
                    ((JLabel) renderer).setText(text);
                }
                return renderer;
            }

        });
        list.setVisibleRowCount(28);
        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(1, 1));
        menu.add(new JLabel("                    Machine Menu:"));
        JScrollPane scp = new JScrollPane(list);
        scp.setColumnHeaderView(menu);
        selectionPanel.add(scp, BorderLayout.CENTER);
        GridBagConstraints s = new GridBagConstraints();
        s.gridwidth = 0;
        s.gridheight = 2;
        s.gridx = 1;
        s.gridy = 0;
        s.weightx = 0;
        s.weighty = 0;
        layout.setConstraints(selectionPanel, s);
        mainpanel.add(selectionPanel);
    }


    private static void input(int select) {
        if (select == 0) {
            vm = snacksvm;
        } else {
            vm = drinkvm;
        }

        JFrame frame = new JFrame("Vending Machine Selection");
        frame.setSize(400, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new FlowLayout());

        JLabel userLabel = new JLabel("Please enter money into the machine");
        panel.add(userLabel);

        JTextField userText = new JTextField(10);
        panel.add(userText);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Double.parseDouble(userText.getText());
                    vm.setMoney(Double.parseDouble(userText.getText()));
                    frame.dispose();
                    mainpage();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Please enter number into the textfield and try again!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(confirmButton);
        frame.setVisible(true);
    }

    private static class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                JButton btn = (JButton) e.getSource();
                if (Character.isDigit(btn.getText().charAt(0))) {
                    selection2 = btn.getText();
                    selection.setText(selection1 + selection2);
                } else {
                    selection1 = btn.getText();
                    selection.setText(selection1 + selection2);
                }
            } else {
                JTextField textField = (JTextField) e.getSource();
                String result = "Item Not Found!";
                for (int i = 0; i < vm.getStock().length; i++) {
                    if (vm.getStock()[i].getDescription().toLowerCase().contains(textField.getText().toLowerCase())) {
                        result = initSelect(i) + ": " + vm.getStock()[i].getDescription() + " - $" + vm.getStock()[i].getPrice() + " (" + vm.getStock()[i].getQuantity() + ")";
                        break;
                    }
                }
                resultLabel.setText(result);
            }
        }
    }

    public static String initSelect(int index) {
        int first = (index + 1) / 6;
        int second = (index + 1) % 6;
        String[] letter = {"A", "B", "C", "D", "E", "F"};
        return letter[first] + second;
    }
}

