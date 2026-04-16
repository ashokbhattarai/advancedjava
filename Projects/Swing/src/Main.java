import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Extended Student Registration System
 * Covers: AWT/Swing, Layout Managers, GUI Controls,
 *         Menus, Dialogs, Borders, and more.
 */
class StudentRegistrationSystem extends JFrame {

    // ── Fields ──────────────────────────────────────────────────────────────
    private JTextField nameField, rollField, emailField;
    private JPasswordField passwordField;
    private JTextArea addressArea;
    private JComboBox<String> courseCombo;
    private JCheckBox activeCheck;
    private JRadioButton maleRb, femaleRb;
    private JSlider gradeSlider;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel statusBar;

    // ── Constructor ──────────────────────────────────────────────────────────
    public StudentRegistrationSystem() {
        super("Student Registration System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);

        buildMenuBar();          // 2.4 – Menus
        buildUI();               // 2.2 – Layouts, 2.3 – Controls
        buildStatusBar();

        setVisible(true);
    }

    // ════════════════════════════════════════════════════════════════════════
    // 2.4  MENU BAR
    // ════════════════════════════════════════════════════════════════════════
    private void buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // ── File Menu ────────────────────────────────────────────────────────
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);           // keyboard mnemonic

        JMenuItem newItem   = new JMenuItem("New",    new ImageIcon());
        JMenuItem saveItem  = new JMenuItem("Save");
        JMenuItem loadItem  = new JMenuItem("Load");
        JMenuItem exportItem= new JMenuItem("Export CSV");
        JMenuItem exitItem  = new JMenuItem("Exit");

        // Keyboard accelerators
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        saveItem.addActionListener(e -> saveToFile());
        loadItem.addActionListener(e -> loadFromFile());
        exportItem.addActionListener(e -> exportCSV());
        exitItem.addActionListener(e -> confirmExit());
        newItem.addActionListener(e -> clearFields());

        fileMenu.add(newItem);
        fileMenu.addSeparator();
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(exportItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // ── Edit Menu ────────────────────────────────────────────────────────
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);

        JMenuItem addItem    = new JMenuItem("Add Student");
        JMenuItem updateItem = new JMenuItem("Update Student");
        JMenuItem deleteItem = new JMenuItem("Delete Student");
        JMenuItem clearItem  = new JMenuItem("Clear Fields");

        addItem.addActionListener(e -> addStudent());
        updateItem.addActionListener(e -> updateStudent());
        deleteItem.addActionListener(e -> deleteStudent());
        clearItem.addActionListener(e -> clearFields());

        editMenu.add(addItem);
        editMenu.add(updateItem);
        editMenu.add(deleteItem);
        editMenu.addSeparator();
        editMenu.add(clearItem);

        // ── View Menu with Check-box & Radio items ───────────────────────────
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);

        JCheckBoxMenuItem showGridItem = new JCheckBoxMenuItem("Show Grid Lines", true);
        showGridItem.addActionListener(e -> {
            table.setShowGrid(showGridItem.isSelected());
            table.repaint();
        });

        JMenu themeMenu = new JMenu("Theme");
        ButtonGroup themeGroup = new ButtonGroup();
        JRadioButtonMenuItem lightItem = new JRadioButtonMenuItem("Light", true);
        JRadioButtonMenuItem darkItem  = new JRadioButtonMenuItem("Dark");
        themeGroup.add(lightItem);
        themeGroup.add(darkItem);
        themeMenu.add(lightItem);
        themeMenu.add(darkItem);

        viewMenu.add(showGridItem);
        viewMenu.add(themeMenu);

        // ── Tools Menu with Pop-up ───────────────────────────────────────────
        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem statsItem   = new JMenuItem("Statistics");
        JMenuItem searchItem  = new JMenuItem("Search Student");
        JMenuItem aboutItem   = new JMenuItem("About");

        statsItem.addActionListener(e -> showStatistics());
        searchItem.addActionListener(e -> searchStudent());
        aboutItem.addActionListener(e -> showAboutDialog());

        toolsMenu.add(statsItem);
        toolsMenu.add(searchItem);
        toolsMenu.addSeparator();
        toolsMenu.add(aboutItem);

        // ── Help Menu with Tooltip demo ──────────────────────────────────────
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("User Guide");
        helpItem.setToolTipText("Open the user guide dialog");
        helpItem.addActionListener(e -> showHelp());
        helpMenu.add(helpItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(toolsMenu);
        menuBar.add(Box.createHorizontalGlue());   // push Help to right
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    // ════════════════════════════════════════════════════════════════════════
    // 2.2  LAYOUT MANAGERS  +  2.3  GUI CONTROLS
    // ════════════════════════════════════════════════════════════════════════
    private void buildUI() {
        // Root: BorderLayout (default for JFrame)
        setLayout(new BorderLayout(5, 5));

        // ── NORTH: ToolBar ───────────────────────────────────────────────────
        JToolBar toolBar = new JToolBar("Main Toolbar");
        toolBar.setFloatable(true);

        JButton tbAdd    = makeToolBtn("Add",    "➕");
        JButton tbUpdate = makeToolBtn("Update", "✏️");
        JButton tbDelete = makeToolBtn("Delete", "🗑");
        JButton tbClear  = makeToolBtn("Clear",  "🧹");
        JButton tbSearch = makeToolBtn("Search", "🔍");

        tbAdd.addActionListener(e -> addStudent());
        tbUpdate.addActionListener(e -> updateStudent());
        tbDelete.addActionListener(e -> deleteStudent());
        tbClear.addActionListener(e -> clearFields());
        tbSearch.addActionListener(e -> searchStudent());

        toolBar.add(tbAdd); toolBar.add(tbUpdate);
        toolBar.add(tbDelete); toolBar.addSeparator();
        toolBar.add(tbClear); toolBar.add(tbSearch);

        add(toolBar, BorderLayout.NORTH);

        // ── CENTER: split between form and table ─────────────────────────────
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                buildFormPanel(), buildTablePanel());
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.45);
        add(splitPane, BorderLayout.CENTER);
    }

    // ── Form Panel (GridBagLayout) ───────────────────────────────────────────
    private JPanel buildFormPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBorder(new TitledBorder(
                BorderFactory.createEtchedBorder(), "Student Information",
                TitledBorder.LEFT, TitledBorder.TOP));

        // GridBagLayout for fine-grained control
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 6, 4, 6);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        // ── Row 0: Name ──────────────────────────────────────────────────────
        gc.gridx=0; gc.gridy=0; gc.weightx=0;
        form.add(new JLabel("Full Name:"), gc);
        gc.gridx=1; gc.weightx=1;
        nameField = new JTextField(20);
        nameField.setToolTipText("Enter student full name");
        form.add(nameField, gc);

        // ── Row 1: Roll No ───────────────────────────────────────────────────
        gc.gridx=0; gc.gridy=1; gc.weightx=0;
        form.add(new JLabel("Roll No:"), gc);
        gc.gridx=1; gc.weightx=1;
        rollField = new JTextField(20);
        form.add(rollField, gc);

        // ── Row 2: Email ─────────────────────────────────────────────────────
        gc.gridx=0; gc.gridy=2; gc.weightx=0;
        form.add(new JLabel("Email:"), gc);
        gc.gridx=1; gc.weightx=1;
        emailField = new JTextField(20);
        form.add(emailField, gc);

        // ── Row 3: Password (PasswordField demo) ─────────────────────────────
        gc.gridx=0; gc.gridy=3; gc.weightx=0;
        form.add(new JLabel("Password:"), gc);
        gc.gridx=1; gc.weightx=1;
        passwordField = new JPasswordField(20);
        form.add(passwordField, gc);

        // ── Row 4: Course (ComboBox) ──────────────────────────────────────────
        gc.gridx=0; gc.gridy=4; gc.weightx=0;
        form.add(new JLabel("Course:"), gc);
        gc.gridx=1; gc.weightx=1;
        courseCombo = new JComboBox<>(new String[]{
                "BCA","BIT","BSc CSIT","MCA","MScIT","BBA"});
        form.add(courseCombo, gc);

        // ── Row 5: Gender (RadioButtons) ──────────────────────────────────────
        gc.gridx=0; gc.gridy=5; gc.weightx=0;
        form.add(new JLabel("Gender:"), gc);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        ButtonGroup genderGroup = new ButtonGroup();
        maleRb   = new JRadioButton("Male",   true);
        femaleRb = new JRadioButton("Female");
        genderGroup.add(maleRb); genderGroup.add(femaleRb);
        genderPanel.add(maleRb); genderPanel.add(femaleRb);
        gc.gridx=1; gc.weightx=1;
        form.add(genderPanel, gc);

        // ── Row 6: Active (CheckBox) ───────────────────────────────────────────
        gc.gridx=0; gc.gridy=6; gc.weightx=0;
        form.add(new JLabel("Status:"), gc);
        gc.gridx=1; gc.weightx=1;
        activeCheck = new JCheckBox("Active Student", true);
        form.add(activeCheck, gc);

        // ── Row 7: Grade Slider ────────────────────────────────────────────────
        gc.gridx=0; gc.gridy=7; gc.weightx=0;
        form.add(new JLabel("Grade %:"), gc);
        gc.gridx=1; gc.weightx=1;
        gradeSlider = new JSlider(0, 100, 50);
        gradeSlider.setMajorTickSpacing(25);
        gradeSlider.setMinorTickSpacing(5);
        gradeSlider.setPaintTicks(true);
        gradeSlider.setPaintLabels(true);
        form.add(gradeSlider, gc);

        // ── Row 8: Address (TextArea + ScrollPane) ────────────────────────────
        gc.gridx=0; gc.gridy=8; gc.weightx=0; gc.anchor=GridBagConstraints.NORTHWEST;
        form.add(new JLabel("Address:"), gc);
        gc.gridx=1; gc.weightx=1; gc.weighty=1; gc.fill=GridBagConstraints.BOTH;
        addressArea = new JTextArea(3, 20);
        addressArea.setLineWrap(true);
        form.add(new JScrollPane(addressArea), gc);

        // ── Button row (FlowLayout) ────────────────────────────────────────────
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        JButton addBtn    = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn  = new JButton("Clear");

        addBtn.addActionListener(e -> addStudent());
        updateBtn.addActionListener(e -> updateStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
        clearBtn.addActionListener(e -> clearFields());

        btnPanel.add(addBtn); btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn); btnPanel.add(clearBtn);

        outer.add(form, BorderLayout.CENTER);
        outer.add(btnPanel, BorderLayout.SOUTH);
        return outer;
    }

    // ── Table Panel ──────────────────────────────────────────────────────────
    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Registered Students",
                TitledBorder.LEFT, TitledBorder.TOP));

        String[] cols = {"Name","Roll No","Email","Course","Gender","Active","Grade %","Address"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        // Row click → populate form
        table.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) return;
                nameField.setText(tableModel.getValueAt(row, 0).toString());
                rollField.setText(tableModel.getValueAt(row, 1).toString());
                emailField.setText(tableModel.getValueAt(row, 2).toString());
                courseCombo.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                String gender = tableModel.getValueAt(row, 4).toString();
                maleRb.setSelected(gender.equals("Male"));
                femaleRb.setSelected(gender.equals("Female"));
                activeCheck.setSelected(tableModel.getValueAt(row, 5).toString().equals("Yes"));
                gradeSlider.setValue(Integer.parseInt(tableModel.getValueAt(row, 6).toString()));
                addressArea.setText(tableModel.getValueAt(row, 7).toString());

                // Right-click pop-up menu
                if (SwingUtilities.isRightMouseButton(e))
                    buildPopupMenu().show(table, e.getX(), e.getY());
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // ── Status Bar ───────────────────────────────────────────────────────────
    private void buildStatusBar() {
        statusBar = new JLabel(" Ready  |  0 students registered");
        statusBar.setBorder(new CompoundBorder(
                new MatteBorder(1,0,0,0, Color.LIGHT_GRAY),
                new EmptyBorder(3,8,3,8)));
        add(statusBar, BorderLayout.SOUTH);
    }

    // ════════════════════════════════════════════════════════════════════════
    // 2.4  POP-UP MENU
    // ════════════════════════════════════════════════════════════════════════
    private JPopupMenu buildPopupMenu() {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem editItem   = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");
        JMenuItem detailItem = new JMenuItem("View Details");
        editItem.addActionListener(e -> updateStudent());
        deleteItem.addActionListener(e -> deleteStudent());
        detailItem.addActionListener(e -> showDetailDialog());
        popup.add(editItem);
        popup.add(deleteItem);
        popup.addSeparator();
        popup.add(detailItem);
        return popup;
    }

    // ════════════════════════════════════════════════════════════════════════
    // CRUD OPERATIONS
    // ════════════════════════════════════════════════════════════════════════
    private void addStudent() {
        if (!validateInput()) return;
        tableModel.addRow(new Object[]{
                nameField.getText().trim(),
                rollField.getText().trim(),
                emailField.getText().trim(),
                courseCombo.getSelectedItem(),
                maleRb.isSelected() ? "Male" : "Female",
                activeCheck.isSelected() ? "Yes" : "No",
                gradeSlider.getValue(),
                addressArea.getText().trim()
        });
        updateStatus("Student added.");
        clearFields();
    }

    private void updateStudent() {
        int row = table.getSelectedRow();
        if (row < 0) { warn("Select a row first."); return; }
        if (!validateInput()) return;
        tableModel.setValueAt(nameField.getText().trim(),   row, 0);
        tableModel.setValueAt(rollField.getText().trim(),   row, 1);
        tableModel.setValueAt(emailField.getText().trim(),  row, 2);
        tableModel.setValueAt(courseCombo.getSelectedItem(),row, 3);
        tableModel.setValueAt(maleRb.isSelected()?"Male":"Female", row, 4);
        tableModel.setValueAt(activeCheck.isSelected()?"Yes":"No", row, 5);
        tableModel.setValueAt(gradeSlider.getValue(),       row, 6);
        tableModel.setValueAt(addressArea.getText().trim(), row, 7);
        updateStatus("Student updated.");
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row < 0) { warn("Select a row first."); return; }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete selected student?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
            updateStatus("Student deleted.");
        }
    }

    private void clearFields() {
        nameField.setText(""); rollField.setText("");
        emailField.setText(""); passwordField.setText("");
        addressArea.setText("");
        courseCombo.setSelectedIndex(0);
        maleRb.setSelected(true);
        activeCheck.setSelected(true);
        gradeSlider.setValue(50);
        table.clearSelection();
        updateStatus("Fields cleared.");
    }

    // ════════════════════════════════════════════════════════════════════════
    // 2.5  DIALOGS
    // ════════════════════════════════════════════════════════════════════════

    /** Custom detail dialog using GridLayout inside a JDialog */
    private void showDetailDialog() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        JDialog dlg = new JDialog(this, "Student Details", true);
        dlg.setSize(400, 340);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout(10, 10));

        JPanel grid = new JPanel(new GridLayout(8, 2, 6, 6));
        grid.setBorder(new EmptyBorder(15, 15, 5, 15));
        String[] labels = {"Name","Roll No","Email","Course","Gender","Active","Grade %","Address"};
        for (int i = 0; i < labels.length; i++) {
            grid.add(new JLabel(labels[i] + ":"));
            grid.add(new JLabel(tableModel.getValueAt(row, i).toString()));
        }
        dlg.add(grid, BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dlg.dispose());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(closeBtn);
        dlg.add(south, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    /** Search dialog */
    private void searchStudent() {
        String query = JOptionPane.showInputDialog(this,
                "Enter name or roll number to search:", "Search", JOptionPane.QUESTION_MESSAGE);
        if (query == null || query.isBlank()) return;
        for (int r = 0; r < tableModel.getRowCount(); r++) {
            String name = tableModel.getValueAt(r, 0).toString().toLowerCase();
            String roll = tableModel.getValueAt(r, 1).toString().toLowerCase();
            if (name.contains(query.toLowerCase()) || roll.contains(query.toLowerCase())) {
                table.setRowSelectionInterval(r, r);
                table.scrollRectToVisible(table.getCellRect(r, 0, true));
                updateStatus("Found: " + tableModel.getValueAt(r, 0));
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "No match found for: " + query,
                "Not Found", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Statistics dialog */
    private void showStatistics() {
        int total   = tableModel.getRowCount();
        long active = 0; int gradeSum = 0;
        for (int r = 0; r < total; r++) {
            if ("Yes".equals(tableModel.getValueAt(r, 5))) active++;
            gradeSum += Integer.parseInt(tableModel.getValueAt(r, 6).toString());
        }
        String msg = String.format(
                "Total Students : %d%nActive          : %d%nInactive        : %d%nAverage Grade   : %.1f%%",
                total, active, total - active, total == 0 ? 0.0 : (double) gradeSum / total);
        JOptionPane.showMessageDialog(this, msg, "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    /** About dialog – uses an internal JDialog with BoxLayout */
    private void showAboutDialog() {
        JDialog dlg = new JDialog(this, "About", true);
        dlg.setSize(360, 220);
        dlg.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Student Registration System");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(centeredLabel("Version 2.0  —  Java Swing Demo"));
        panel.add(centeredLabel("Covers: AWT/Swing, Layouts, Controls,"));
        panel.add(centeredLabel("Menus, Toolbars, Dialogs & More"));
        panel.add(Box.createVerticalStrut(15));

        JButton ok = new JButton("OK");
        ok.setAlignmentX(CENTER_ALIGNMENT);
        ok.addActionListener(e -> dlg.dispose());
        panel.add(ok);

        dlg.add(panel);
        dlg.setVisible(true);
    }

    /** File chooser – save */
    private void saveToFile() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Save Data");
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            try (PrintWriter pw = new PrintWriter(f)) {
                for (int r = 0; r < tableModel.getRowCount(); r++) {
                    StringBuilder sb = new StringBuilder();
                    for (int c = 0; c < tableModel.getColumnCount(); c++) {
                        if (c > 0) sb.append("|");
                        sb.append(tableModel.getValueAt(r, c));
                    }
                    pw.println(sb);
                }
                updateStatus("Saved to " + f.getName());
            } catch (IOException ex) {
                warn("Save failed: " + ex.getMessage());
            }
        }
    }

    /** File chooser – load */
    private void loadFromFile() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Load Data");
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader br = new BufferedReader(new FileReader(fc.getSelectedFile()))) {
                tableModel.setRowCount(0);
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 8) tableModel.addRow(parts);
                }
                updateStatus("Loaded " + tableModel.getRowCount() + " records.");
            } catch (IOException ex) { warn("Load failed: " + ex.getMessage()); }
        }
    }

    /** Color chooser – export CSV using a chosen separator color ≈ just demonstrates color chooser */
    private void exportCSV() {
        Color chosen = JColorChooser.showDialog(this, "Pick header colour", Color.BLUE);
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Export CSV");
        if (fc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        try (PrintWriter pw = new PrintWriter(fc.getSelectedFile())) {
            pw.println("Name,Roll No,Email,Course,Gender,Active,Grade,Address");
            for (int r = 0; r < tableModel.getRowCount(); r++) {
                StringBuilder sb = new StringBuilder();
                for (int c = 0; c < tableModel.getColumnCount(); c++) {
                    if (c > 0) sb.append(",");
                    sb.append("\"").append(tableModel.getValueAt(r, c)).append("\"");
                }
                pw.println(sb);
            }
            updateStatus("Exported CSV  (header colour: " +
                    (chosen != null ? "#"+Integer.toHexString(chosen.getRGB()).substring(2) : "default") + ")");
        } catch (IOException ex) { warn("Export failed: " + ex.getMessage()); }
    }

    /** Help dialog with ScrollPane + TextArea */
    private void showHelp() {
        JDialog dlg = new JDialog(this, "User Guide", false);
        dlg.setSize(480, 360);
        dlg.setLocationRelativeTo(this);
        JTextArea ta = new JTextArea(HELP_TEXT);
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        ta.setMargin(new Insets(8, 8, 8, 8));
        dlg.add(new JScrollPane(ta));
        dlg.setVisible(true);
    }

    /** Confirm-exit dialog */
    private void confirmExit() {
        int r = JOptionPane.showConfirmDialog(this,
                "Exit the application?", "Confirm Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (r == JOptionPane.YES_OPTION) System.exit(0);
    }

    // ════════════════════════════════════════════════════════════════════════
    // HELPERS
    // ════════════════════════════════════════════════════════════════════════
    private boolean validateInput() {
        if (nameField.getText().isBlank()) { warn("Name is required."); return false; }
        if (rollField.getText().isBlank()) { warn("Roll No is required."); return false; }
        if (emailField.getText().isBlank()) { warn("Email is required."); return false; }
        return true;
    }

    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void updateStatus(String msg) {
        statusBar.setText(" " + msg + "  |  " + tableModel.getRowCount() + " students registered");
    }

    private JButton makeToolBtn(String tip, String text) {
        JButton b = new JButton(text);
        b.setToolTipText(tip);
        b.setFocusPainted(false);
        return b;
    }

    private JLabel centeredLabel(String text) {
        JLabel l = new JLabel(text);
        l.setAlignmentX(CENTER_ALIGNMENT);
        return l;
    }

    private static final String HELP_TEXT =
            "STUDENT REGISTRATION SYSTEM — USER GUIDE\n" +
                    "==========================================\n\n" +
                    "ADD STUDENT\n" +
                    "  Fill all fields and click 'Add' or use Edit > Add Student.\n\n" +
                    "UPDATE STUDENT\n" +
                    "  Click a row in the table to load it, edit fields,\n" +
                    "  then click 'Update'.\n\n" +
                    "DELETE STUDENT\n" +
                    "  Select a row and click 'Delete' or right-click the row.\n\n" +
                    "SEARCH\n" +
                    "  Use Tools > Search Student or the toolbar 🔍 button.\n\n" +
                    "SAVE / LOAD\n" +
                    "  File > Save / Load to persist data to a text file.\n\n" +
                    "EXPORT CSV\n" +
                    "  File > Export CSV to export all records as CSV.\n\n" +
                    "KEYBOARD SHORTCUTS\n" +
                    "  Ctrl+N  New / Clear\n" +
                    "  Ctrl+S  Save\n" +
                    "  Ctrl+Q  Exit\n";

    // ════════════════════════════════════════════════════════════════════════
    // MAIN
    // ════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new StudentRegistrationSystem();
        });
    }
}