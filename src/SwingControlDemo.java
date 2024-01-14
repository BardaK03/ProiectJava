package SwingControlDemo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import org.json.JSONException;

public class SwingControlDemo {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;

    // Variabile pentru a stoca datele
    private String optiuneMasina = "";
    private boolean incalzireInScaune = false;
    private boolean ABS = false;
    private boolean CruiseControl = false;
    private Color culoareMasina;
    private int numarLuni = 0;
    private String dealerSelectat = "";
    private JTable dataTable;
    private DefaultTableModel tableModel;



    public SwingControlDemo(){
        prepareGUI();
    }

    public static void main(String[] args){
        SwingControlDemo swingControlDemo = new SwingControlDemo();
        swingControlDemo.showCheckBoxDemo();
        swingControlDemo.showRadioButtonDemo();
        swingControlDemo.showSliderDemo();
        swingControlDemo.showColorChooserDemo();
        swingControlDemo.showComboboxDemo();
        swingControlDemo.showButtonDemo();
        swingControlDemo.incarcaDateDinFisier();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("CAR DEALERSHIP");
        mainFrame.setSize(700, 700);
        mainFrame.setLayout(new GridLayout(3, 1));

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(7, 4));

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);

        // Inițializează modelul și tabelul
        tableModel = new DefaultTableModel();
        tableModel.addColumn("OptiuneMasina");
        tableModel.addColumn("IncalzireInScaune");
        tableModel.addColumn("CruiseControl");
        tableModel.addColumn("ABS");
        tableModel.addColumn("NumarLuni");
        tableModel.addColumn("DealerSelectat");
        tableModel.addColumn("CuloareMasina");

        dataTable = new JTable(tableModel);

        // Adaugă tabelul într-un JScrollPane pentru a permite defilarea
        JScrollPane tableScrollPane = new JScrollPane(dataTable);

        // Adaugă JScrollPane la controlPanel
        controlPanel.add(tableScrollPane);

        // Încarcă datele din fișier la inițializarea aplicației
        incarcaDateDinFisier();

        mainFrame.setVisible(true);
    }

    private void showRadioButtonDemo() {
        headerLabel.setText("Configurează-ți mașina");

        final JRadioButton radVolvo = new JRadioButton("VOLVO", true);
        final JRadioButton radBMW = new JRadioButton("BMW");
        final JRadioButton radAudi = new JRadioButton("AUDI");

        radVolvo.setMnemonic(KeyEvent.VK_C);
        radBMW.setMnemonic(KeyEvent.VK_M);
        radAudi.setMnemonic(KeyEvent.VK_P);

        radVolvo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                statusLabel.setText("Ați ales VOLVO: "
                        + (e.getStateChange() == 1 ? "checked" : "unchecked"));
                optiuneMasina = "VOLVO";
            }
        });

        radBMW.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                statusLabel.setText("Ați ales BMW: "
                        + (e.getStateChange() == 1 ? "checked" : "unchecked"));
                optiuneMasina = "BMW";
            }
        });

        radAudi.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                statusLabel.setText("Ați ales AUDI: "
                        + (e.getStateChange() == 1 ? "checked" : "unchecked"));
                optiuneMasina = "AUDI";
            }
        });

        ButtonGroup group = new ButtonGroup();
        group.add(radVolvo);
        group.add(radBMW);
        group.add(radAudi);

        controlPanel.add(radVolvo);
        controlPanel.add(radBMW);
        controlPanel.add(radAudi);
        mainFrame.setVisible(true);
    }

    private void showCheckBoxDemo(){
        headerLabel.setText("Alege-ți mașina");

        final JCheckBox chkIncalzire = new JCheckBox("Încălzire în scaune");
        final JCheckBox chkABS = new JCheckBox("ABS");
        final JCheckBox chkCruiseControl = new JCheckBox("Cruise control");

        chkIncalzire.setMnemonic(KeyEvent.VK_C);
        chkABS.setMnemonic(KeyEvent.VK_M);
        chkCruiseControl.setMnemonic(KeyEvent.VK_P);

        chkIncalzire.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                statusLabel.setText("Ați ales încălzire în scaune: "
                        + (e.getStateChange()==1?"checked":"unchecked"));
                incalzireInScaune = (e.getStateChange() == 1);
            }
        });

        chkABS.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                statusLabel.setText("Ați ales ABS: "
                        + (e.getStateChange()==1?"checked":"unchecked"));
                ABS = (e.getStateChange() == 1);
            }
        });

        chkCruiseControl.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                statusLabel.setText("Ați ales Cruise control: "
                        + (e.getStateChange()==1?"checked":"unchecked"));
                CruiseControl = (e.getStateChange() == 1);
            }
        });

        controlPanel.add(chkIncalzire);
        controlPanel.add(chkABS);
        controlPanel.add(chkCruiseControl);

        mainFrame.setVisible(true);
    }

    private void showSliderDemo(){
        JSlider slider = new JSlider(JSlider.HORIZONTAL,0,60,10);

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                statusLabel.setText("Numărul de luni ales pentru rată: " + ((JSlider)e.getSource()).getValue());
                numarLuni = ((JSlider)e.getSource()).getValue();
            }
        });

        controlPanel.add(slider);
        mainFrame.setVisible(true);
    }

    private void showColorChooserDemo(){
        JButton chooseButton = new JButton("Alege culoarea mașinii");

        chooseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color backgroundColor = JColorChooser.showDialog(mainFrame,
                        "Alege culoarea mașinii", Color.white);
                if (backgroundColor != null){
                    culoareMasina = backgroundColor;
                    controlPanel.setBackground(culoareMasina);
                    mainFrame.getContentPane().setBackground(culoareMasina);
                }
            }
        });

        controlPanel.add(chooseButton);
        mainFrame.setVisible(true);
    }


    private void showComboboxDemo(){
        headerLabel.setText("Control in action: JComboBox");
        final DefaultComboBoxModel fruitsName = new DefaultComboBoxModel();

        fruitsName.addElement("Oradea".trim());
        fruitsName.addElement("Budapesta".trim());
        fruitsName.addElement("Viena".trim());
        fruitsName.addElement("Cracovia".trim());

        final JComboBox fruitCombo = new JComboBox(fruitsName);
        fruitCombo.setSelectedIndex(0);

        JScrollPane fruitListScrollPane = new JScrollPane(fruitCombo);
        JButton showButton = new JButton("Apasa-ma pentru a alege dealer-ul");

        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fruitCombo.getSelectedIndex() != -1) {
                    // Folosiți getItemAt pentru a obține valoarea selectată din combobox
                    dealerSelectat = (String) fruitCombo.getItemAt(fruitCombo.getSelectedIndex());
                }
                statusLabel.setText("Alege dealer-ul la care să fie livrată mașina: " + dealerSelectat);
            }
        });


        controlPanel.add(fruitListScrollPane);
        controlPanel.add(showButton);
        mainFrame.setVisible(true);
    }

    private static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = SwingControlDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void showButtonDemo(){
        headerLabel.setText("Control in action: Button");

        ImageIcon icon = createImageIcon("/resources/java_icon.png","Java");

        JButton okButton = new JButton("Salvare");

        JButton cancelButton = new JButton("Cancel", icon);
        cancelButton.setHorizontalTextPosition(SwingConstants.LEFT);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Colectează datele din formular
                JSONObject json = new JSONObject();
                json.put("OptiuneMasina", optiuneMasina);
                json.put("IncalzireInScaune", incalzireInScaune);
                json.put("CruiseControl", CruiseControl);
                json.put("ABS", ABS);
                json.put("NumarLuni", numarLuni);
                json.put("DealerSelectat", dealerSelectat);
                json.put("CuloareMasina", culoareMasina.getRGB());
                // Afișează valorile  in consola înainte de salvare

                System.out.println("OptiuneMasina: " + optiuneMasina);
                System.out.println("IncalzireInScaune: " + incalzireInScaune);
                System.out.println("NumarLuni: " + numarLuni);
                System.out.println("DealerSelectat: " + dealerSelectat);
                System.out.println("CuloareMasina: " + culoareMasina);
                salvareInFisier(json.toString());
                adaugaDateInTabel();
                statusLabel.setText("Date salvate în fișier JSON.");
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        controlPanel.add(okButton);
        controlPanel.add(cancelButton);

        mainFrame.setVisible(true);
    }

    private void salvareInFisier(String jsonString) {
        try (FileWriter fileWriter = new FileWriter("date_masini.json", true)) {
            fileWriter.write(jsonString + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void adaugaDateInTabel() {
        // Adaugă un rând nou în tabel cu datele colectate
        Object[] rowData = {
                optiuneMasina,
                incalzireInScaune,
                CruiseControl,
                ABS,
                numarLuni,
                dealerSelectat,
                culoareMasina
        };
        tableModel.addRow(rowData);
    }
    private void incarcaDateDinFisier() {
        try {
            // Deschide fișierul JSON pentru citire
            FileReader fileReader = new FileReader("date_masini.json");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Citeste fiecare linie din fișier și adaugă datele în tabel
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                JSONObject json = new JSONObject(line);

                Object[] rowData = {
                        json.getString("OptiuneMasina"),
                        json.getBoolean("IncalzireInScaune"),
                        json.getBoolean("CruiseControl"),
                        json.getBoolean("ABS"),
                        json.getInt("NumarLuni"),
                        json.getString("DealerSelectat"),
                        new Color(json.getInt("CuloareMasina"))
                };

                tableModel.addRow(rowData);
            }

            // Închide resursele
            bufferedReader.close();
            fileReader.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}

