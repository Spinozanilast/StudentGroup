package Frames;

import CustomComponents.CustomLightJTableWithActionColumn;
import CustomComponents.CustomTableActionCells.TableActionCellEvent;
import CustomComponents.PillButton;
import Database.DAOS.StudentDAO;
import Database.Managers.SQLiteConnectionProvider;
import Database.Managers.SQLiteDBHelper;
import Database.Models.StudentDatabaseModel;
import Frames.models.StudentsJTableModelInfo;
import TableExport.FilePathChooserDialog;
import TableExport.TableExporter;
import TableExport.TableExporterToExcel;
import TableExport.TableExporterToWord;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Класс StudentsFrame представляет форму для отображения информации о студенческой группе.
 *
 * @author Будчанин В.А.
 * @version 1.1 04.11.2023
 */
public class StudentsFrame extends JFrame {
    /**
     * Предпочтительный размер кнопки.
     */
    public static final Dimension BUTTON_PREFFERED_SIZE = new Dimension(200, 40);

    /**
     * Предпочтительный размер элемента управления "Pills".
     */
    public static final Dimension PILLS_PREFFERED_SIZE = new Dimension(150, 30);

    /**
     * Цвет переднего плана метки.
     */
    public static final Color LABEL_FOREGROUND = new Color(29, 105, 200);

    /**
     * Цвет фона панели.
     */
    public static final Color PANEL_BACKGROUND = new Color(242, 250, 255);

    /**
     * Цвет фона кнопки.
     */
    public static final Color BUTTON_BACKGROUND = new Color(0, 95, 184);

    /**
     * Главная панель.
     */
    private final JPanel pnlMain = new JPanel();

    /**
     * Внутреннее меню панели.
     */
    private final JPanel pnlInnerMenu = new JPanel();

    /**
     * Внутренние атрибуты панели.
     */
    private final JPanel pnlInnerAttributes = new JPanel();

    /**
     * Внутренняя верхняя панель.
     */
    private final JPanel pnlInnerUp = new JPanel();

    /**
     * Макет содержимого панели.
     */
    private final JPanel pnlContentLayout = new JPanel();

    /**
     * Значение числа студентов.
     */
    private JLabel lblStudentsNumValue;

    /**
     * Кнопка для отображения списка студентов.
     */
    private JButton jbtShowStudentsList;

    /**
     * Номер группы.
     */
    private final String groupNumber;

    /**
     * Число студентов.
     */
    private final String studentsNum;

    /**
     * Кнопка для экспорта в Word.
     */
    private JButton jbtToWordExport;

    /**
     * Кнопка для экспорта в Excel.
     */
    private JButton jbtToExcelExport;

    /**
     * Таблица студентов с действием в столбце.
     */
    private CustomLightJTableWithActionColumn studentsTable = null;

    /**
     * Соединение с базой данных.
     */
    private Connection connection;

    /**
     * Всплывающее меню.
     */
    private static JPopupMenu popupMenu;

    /**
     * Объект доступа к данным студента.
     */
    private static StudentDAO studentDAO;

    /**
     * Провайдер соединения SQLite.
     */
    SQLiteConnectionProvider sqLiteConnectionProvider;


    /**
     * Создает новый экземпляр класса StudentsFrame с указанными номером группы, номером курса и ФИО старосты вместе с
     * кол-вом студентов в группе.
     *
     * @param groupNumber     Номер группы.
     * @param courseNumber    Номер курса.
     * @param headmanFullName ФИО старосты.
     */
    public StudentsFrame(String groupNumber, String courseNumber, String headmanFullName) {
        // Получаем провайдер соединения SQLite
        sqLiteConnectionProvider = getSqLiteConnectionProvider();

        // Устанавливаем заголовок окна
        setTitle("Данные группы " + groupNumber);

        // Устанавливаем номер группы и количество студентов
        this.groupNumber = groupNumber;
        this.studentsNum = String.valueOf(studentDAO.getCountOfGroupStudents(groupNumber));

        // Устанавливаем иконку окна
        ImageIcon icon = new ImageIcon("assets/AloneGroupIcon.png");
        setIconImage(icon.getImage());

        // Устанавливаем операцию закрытия окна, размер и расположение
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);

        // Настраиваем компоненты представления панели
        setLayouts();
        setUpPanelViewComponents(courseNumber, studentsNum, headmanFullName);

        // Настраиваем панель кнопок
        setUpButtonsPanel();

        // Инициализируем соединение с базой данных
        initDBConnection();
    }

    /**
     * Создает новый экземпляр класса StudentsFrame с указанными данными о группе (группа, курс и ФИО старосты) вместе с
     * кол-вом студентов в группе.
     *
     * @param groupData Массив данных о группе (группа, курс и ФИО старосты).
     */
    public StudentsFrame(String[] groupData) {
        this(groupData[0], groupData[1], groupData[2]);
    }

    /**
     * Метод setLayouts устанавливает компоновки для панелей.
     */
    private void setLayouts() {
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlInnerMenu.setLayout(new BoxLayout(pnlInnerMenu, BoxLayout.X_AXIS));
        pnlInnerAttributes.setLayout(new BoxLayout(pnlInnerAttributes, BoxLayout.X_AXIS));
        pnlInnerUp.setLayout(new BoxLayout(pnlInnerUp, BoxLayout.X_AXIS));
        pnlContentLayout.setLayout(new BoxLayout(pnlContentLayout, BoxLayout.Y_AXIS));

        pnlMain.add(pnlInnerUp);
        pnlMain.add(pnlInnerMenu);
        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(pnlInnerAttributes);
        pnlMain.add(pnlContentLayout);
        pnlMain.setBackground(PANEL_BACKGROUND);

        add(pnlMain);
        setContentPane(pnlMain);
    }

    /**
     * Метод setUpPanelViewComponents устанавливает компоненты представления панели.
     *
     * @param course          Курс.
     * @param studentsCount   Количество студентов.
     * @param headmanFullName ФИО старосты.
     */
    private void setUpPanelViewComponents(String course, String studentsCount, String headmanFullName) {
        int strutWidth = 17;

        JLabel lblCourse = new JLabel("Курс: ");
        JLabel lblStudentsNum = new JLabel("Количество студентов: ");
        JLabel lblHeadmanFullName = new JLabel("Староста: ");
        JLabel lblCourseValue = new JLabel(course);
        JLabel lblHeadmanValue = new JLabel(headmanFullName);
        lblStudentsNumValue = new JLabel(studentsCount);

        pnlInnerUp.setLayout(new BoxLayout(pnlInnerUp, BoxLayout.X_AXIS));

        stylizeLabels(LABEL_FOREGROUND, lblCourse, lblStudentsNum, lblHeadmanFullName);
        stylizeLabels(Color.BLACK, lblCourseValue, lblStudentsNumValue, lblHeadmanValue);

        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));
        pnlInnerUp.add(lblCourse);

        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));
        pnlInnerUp.add(lblCourseValue);

        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));
        pnlInnerUp.add(Box.createHorizontalGlue());
        pnlInnerUp.add(lblStudentsNum);

        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));
        pnlInnerUp.add(lblStudentsNumValue);

        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));
        pnlInnerUp.add(Box.createHorizontalGlue());
        pnlInnerUp.add(lblHeadmanFullName);

        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));
        pnlInnerUp.add(lblHeadmanValue);
        pnlInnerUp.add(Box.createHorizontalStrut(strutWidth));

        pnlInnerUp.setBackground(PANEL_BACKGROUND);
    }

    public JButton getExitButton() {
        JButton jbtExit = new JButton("Закрыть окно");
        jbtExit.addActionListener(e -> {
            JFrame jFrame = (JFrame) jbtExit.getTopLevelAncestor();
            jFrame.dispose();
        });
        return jbtExit;
    }

    /**
     * Метод setUpButtonsPanel устанавливает панель кнопок.
     */
    private void setUpButtonsPanel() {
        jbtShowStudentsList = new JButton("Список студентов");
        jbtShowStudentsList.setToolTipText("Вывести список всех студентов для данной группы");

        JButton jbtExit = getExitButton();
        jbtExit.setToolTipText("Закрывает окно редактирования данной группы");

        jbtToWordExport = new JButton("Экспорт", new ImageIcon(Objects.requireNonNull(getClass().getResource("/CustomComponents/Icons/WordIcon.png"))));
        jbtToWordExport.setToolTipText("Экспортирует таблицу со студентами в Word");

        jbtToExcelExport = new JButton("Экспорт", new ImageIcon(Objects.requireNonNull(getClass().getResource("/CustomComponents/Icons/ExcelIcon.png"))));
        jbtToExcelExport.setToolTipText("Экспортирует таблицу со студентами в Excel");

        stylizeButtons(BUTTON_BACKGROUND, jbtShowStudentsList);
        stylizeButtons(Color.RED, jbtExit);
        stylizeButtons(new Color(24, 90, 189), jbtToWordExport);
        stylizeButtons(new Color(16, 124, 65), jbtToExcelExport);

        pnlInnerMenu.setBackground(PANEL_BACKGROUND);
        pnlInnerAttributes.setBackground(PANEL_BACKGROUND);
        pnlContentLayout.setBackground(PANEL_BACKGROUND);
        addButton(jbtShowStudentsList, false);
        addButton(jbtToWordExport, false);
        addButton(jbtToExcelExport, false);
        addButton(jbtExit, true);
    }

    /**
     * Метод addButton добавляет кнопку на панель.
     *
     * @param button      Кнопка.
     * @param isEndButton Флаг, указывающий, является ли кнопка последней на панели.
     */
    private void addButton(JButton button, boolean isEndButton) {
        pnlInnerMenu.add(button);
        if (isEndButton) return;
        pnlInnerMenu.add(Box.createHorizontalGlue());
    }

    /**
     * Метод stylizeLabels задает стиль для надписей.
     *
     * @param foreground Цвет переднего плана.
     * @param labels     Надписи.
     */
    private void stylizeLabels(Color foreground, JLabel... labels) {
        for (JLabel label : labels) {
            label.setFont(new Font("Montserrat", Font.BOLD | Font.ITALIC, 24));
            label.setForeground(foreground);
        }
    }

    /**
     * Метод stylizeButtons задает стиль для кнопок.
     *
     * @param background Цвет заднего плана.
     * @param jButtons   Кнопки.
     */
    private void stylizeButtons(Color background, JButton... jButtons) {
        for (JButton jButton : jButtons) {
            jButton.setPreferredSize(BUTTON_PREFFERED_SIZE);
            jButton.setForeground(Color.WHITE);
            jButton.setBackground(background);
            jButton.setFont(new Font("Montserrat", Font.BOLD | Font.ITALIC, 14));
            jButton.setBorder(null);
            jButton.setBorder(new EmptyBorder(10, 10, 10, 10));
        }
    }

    /**
     * Конструктор контроллера формы группы.
     */
    public void initDBConnection() {
        addWindowListener(new WindowAdapter() {
            /**
             * Вызывается в процессе закрытия окна formView.
             *
             * @param e экземпляр WindowEvent
             */
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (connection != null && !connection.isClosed()) {
                        sqLiteConnectionProvider.closeConnection();
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        initButtonsListeners();
    }

    /**
     * Возвращает провайдера соединения SQLite.
     *
     * @return Провайдер соединения SQLite.
     */
    private SQLiteConnectionProvider getSqLiteConnectionProvider() {
        SQLiteConnectionProvider sqLiteConnectionProvider = new SQLiteConnectionProvider();
        connection = sqLiteConnectionProvider.getConnection();
        studentDAO = new StudentDAO(connection);
        return sqLiteConnectionProvider;
    }

    /**
     * Инициализирует слушателей событий для кнопок на View.
     */
    private void initButtonsListeners() {
        ActionListener actionListenerShowStudentsTable = this::actionShowStudentsTablePerformed;
        ActionListener actionListenerExportToWord = e -> actionExportToWordPerformed();
        ActionListener actionListenerExportToExcel = e -> actionExportToExcelPerformed();
        jbtShowStudentsList.addActionListener(actionListenerShowStudentsTable);
        getJbtToWordExport().addActionListener(actionListenerExportToWord);
        getJbtToExcelExport().addActionListener(actionListenerExportToExcel);
    }

    /**
     * Метод, содержащий логику добавления таблицы,
     * осуществляемое соответствующей кнопкой.
     *
     * @param event Экземпляр сообщения о произошедшем событии
     */
    private void actionShowStudentsTablePerformed(ActionEvent event) {
        if (pnlContentLayout.getComponentCount() == 0) {
            Object[] columnsNames = StudentsJTableModelInfo.getTableColumnsNamesWithoutGroup();
            int columnsNumber = columnsNames.length;
            Object[][] studentsData = SQLiteDBHelper.getStudentsTableData(connection, groupNumber, columnsNumber);
            studentsTable = getCustomLightJTableWithActionColumn(studentsData, columnsNames);
            studentsTable.setCustomBooleanIntegerRenderers(StudentsJTableModelInfo.getBooleanColumnsIndexes(), StudentsJTableModelInfo.getIntegerColumnsIndexes());
            initAttributesPanel(columnsNames, studentsTable.getColumnModel());
            studentsTable.addActionColumn(getTableActionEvents());
            if (studentsData.length == 0) {
                initJTableInput(studentsTable);
            }
            JScrollPane tableScrollPane = new JScrollPane(studentsTable);
            pnlContentLayout.add(tableScrollPane);
            addTablePopupMenu(studentsTable);
            revalidate();
            repaint();
            initTablePopupMenu(studentsTable);
        }
    }

    /**
     * Инициализирует панель атрибутов.
     *
     * @param columnNames      имена столбцов
     * @param tableColumnModel модель столбцов таблицы
     */
    private void initAttributesPanel(Object[] columnNames, TableColumnModel tableColumnModel) {
        if (pnlInnerAttributes.getComponentCount() != 0) return;
        for (int i = 0, columnNamesLength = columnNames.length; i < columnNamesLength; i++) {
            PillButton columnPillButton = getPillButton(columnNames[i]);
            columnPillButton.setLblFont(new Font("Montserrat", Font.ITALIC, 10));
            columnPillButton.setPreferredSize(StudentsFrame.PILLS_PREFFERED_SIZE);
            columnPillButton.setMaximumSize(StudentsFrame.PILLS_PREFFERED_SIZE);
            pnlInnerAttributes.add(columnPillButton);

            TableColumn columnToHide = tableColumnModel.getColumn(i);
            columnPillButton.addMouseListener(new MouseAdapter() {
                /**
                 * Вызывается при щелчке мыши на кнопке.
                 *
                 * @param e экземпляр MouseEvent
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    int normalWidth = getWidth() / columnNames.length;
                    int widthToSet = columnPillButton.getPillActivated() ? normalWidth : 0;
                    columnToHide.setMinWidth(widthToSet);
                    columnToHide.setMaxWidth(widthToSet);
                    columnToHide.setWidth(widthToSet);
                    columnToHide.setPreferredWidth(widthToSet);
                }
            });
        }
    }

    /**
     * Возвращает кнопку-пилюлю.
     *
     * @param columnNames имена столбцов
     * @return кнопка-пилюля
     */
    private static PillButton getPillButton(Object columnNames) {
        String columnName = columnNames.toString();
        Color activatedBackground = new Color(0, 95, 184);
        Color notActivatedBackground = new Color(243, 245, 246);
        Color activatedForeground = Color.WHITE;
        Color notActivatedForeground = Color.BLACK;
        return new PillButton(
                columnName,
                activatedBackground,
                notActivatedBackground,
                activatedForeground,
                notActivatedForeground);
    }

    /**
     * Инициализирует всплывающее меню для таблицы студентов.
     *
     * @param studentsTable таблица студентов
     */
    private void initTablePopupMenu(CustomLightJTableWithActionColumn studentsTable) {
        popupMenu = new JPopupMenu();

        JMenuItem addMenuItem = new JMenuItem("Добавить строку");
        addMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        JMenuItem deleteMenuItem = new JMenuItem("Удалить студента");
        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));

        JMenuItem updateMenuItem = new JMenuItem("Обновить таблицу");
        updateMenuItem.setFocusable(true);
        updateMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));

        addPopupMenuItems(studentsTable, addMenuItem, deleteMenuItem, updateMenuItem);
        popupMenu.add(addMenuItem);
        popupMenu.add(deleteMenuItem);
        popupMenu.add(updateMenuItem);
    }

    /**
     * Добавляет элементы контекстного меню в таблицу студентов.
     *
     * @param studentsTable  таблица студентов
     * @param addMenuItem    элемент меню "Добавить строку"
     * @param deleteMenuItem элемент меню "Удалить студента"
     * @param updateMenuItem элемент меню "Обновить таблицу"
     */
    private void addPopupMenuItems(CustomLightJTableWithActionColumn studentsTable, JMenuItem addMenuItem, JMenuItem deleteMenuItem, JMenuItem updateMenuItem) {
        addMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = studentsTable.getSelectedRow();
                if (rowIndex < 0) {
                    if (isEmptyTable(studentsTable)) {
                        initJTableInput(studentsTable);
                    } else {
                        JOptionPane.showMessageDialog(null, "Выберите строку таблицы, после которой" + " необходимо добавить студента");
                    }
                    return;
                }
                DefaultTableModel tableModel = (DefaultTableModel) studentsTable.getModel();
                Object previousStudentID = 0;
                previousStudentID = tableModel.getValueAt(rowIndex, 0);
                addRowToStudentsTable(rowIndex, (int) previousStudentID + 1, tableModel, false);
                setStudentsNum(String.valueOf((Integer.parseInt(studentsNum) + 1)));

            }
        });

        deleteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isEmptyTable(studentsTable)) return;
                int[] selectedRowsIndexes = studentsTable.getSelectedRows();
                if (selectedRowsIndexes.length == 0) {
                    JOptionPane.showMessageDialog(null, "Выберите студента, которого необходимо удалить");
                    return;
                }
                DefaultTableModel tableModel = (DefaultTableModel) studentsTable.getModel();
                for (int rowIndex : selectedRowsIndexes) {
                    String studentID = tableModel.getValueAt(rowIndex, 0).toString();
                    studentDAO.deleteStudent(studentID);
                    tableModel.removeRow(rowIndex);
                    setStudentsNum(String.valueOf((Integer.parseInt(studentsNum) - 1)));
                }
                if (studentsTable.getModel().getRowCount() == 0) {
                    addRowToStudentsTable(0, 1, (DefaultTableModel) studentsTable.getModel(), true);
                }
            }
        });

        updateMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jbtShowStudentsList.doClick();
            }
        });
    }

    /**
     * Проверяет, является ли таблица пустой.
     *
     * @param studentsTable таблица студентов
     * @return true, если таблица пустая, иначе false
     */
    private static boolean isEmptyTable(JTable studentsTable) {
        return ((DefaultTableModel) studentsTable.getModel()).getRowCount() == 0;
    }

    /**
     * Добавляет всплывающее контекстное меню к таблице.
     *
     * @param studentsTable таблица студентов
     */
    private static void addTablePopupMenu(CustomLightJTableWithActionColumn studentsTable) {
        studentsTable.addMouseListener(new MouseAdapter() {
            /**
             * Вызывается при нажатии кнопки мыши.
             *
             * @param e экземпляр MouseEvent
             */
            public void mousePressed(MouseEvent e) {
                showPopupMenu(e);
            }

            /**
             * Вызывается при отпускании кнопки мыши.
             *
             * @param e экземпляр MouseEvent
             */
            public void mouseReleased(MouseEvent e) {
                showPopupMenu(e);
            }

            /**
             * Отображает всплывающее контекстное меню.
             *
             * @param e экземпляр MouseEvent
             */
            private void showPopupMenu(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    /**
     * Возвращает настраиваемую таблицу студентов с действием в столбце.
     *
     * @param studentsData данные студентов
     * @param columnsNames названия столбцов
     * @return настраиваемая таблица студентов
     */
    private static CustomLightJTableWithActionColumn getCustomLightJTableWithActionColumn(Object[][] studentsData, Object[] columnsNames) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(studentsData, columnsNames);
        CustomLightJTableWithActionColumn studentsTable = new CustomLightJTableWithActionColumn(defaultTableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return switch (column) {
                    case 0 -> Integer.class;
                    case 1, 2, 3, 5, 6, 8 -> String.class;
                    default -> Boolean.class;
                };
            }
        };
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(defaultTableModel);
        studentsTable.setRowSorter(rowSorter);
        return studentsTable;
    }

    /**
     * Возвращает события действий таблицы.
     *
     * @return события действий таблицы
     */
    private TableActionCellEvent getTableActionEvents() {
        return new TableActionCellEvent() {
            @Override
            public void onAddRow(int rowIndex, JTable jTable) {
                try {
                    DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
                    Object newRowStudentID = (int) tableModel.getValueAt(rowIndex, 0) + 1;
                    addRowToStudentsTable(rowIndex, newRowStudentID, tableModel, false);
                    setStudentsNum(String.valueOf((Integer.parseInt(studentsNum) + 1)));
                } catch (ClassCastException e) {
                    JOptionPane.showMessageDialog(null, "Извините, что-то пошло не так");
                }
            }

            @Override
            public void onDelete(int rowIndex, JTable jTable) {
                DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
                String studentID = tableModel.getValueAt(rowIndex, 0).toString();
                studentDAO.deleteStudent(studentID);
                int rowCount = tableModel.getRowCount();
                if (rowIndex >= 0 && rowIndex < rowCount) {
                    tableModel.removeRow(rowIndex);
                }
                if (jTable.getModel().getRowCount() == 0) {
                    addRowToStudentsTable(0, 1, (DefaultTableModel) jTable.getModel(), true);
                }
            }

            @Override
            public void onUpdateDB(int rowIndex, JTable jTable) {
                DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
                int columnCount = tableModel.getColumnCount();
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = tableModel.getValueAt(rowIndex, i);
                }
                SQLiteDBHelper.StudentsDataValidator.ValidityConstants validityResult = SQLiteDBHelper
                        .StudentsDataValidator.validateData(rowData, connection, groupNumber);
                if (validityResult == SQLiteDBHelper.StudentsDataValidator.ValidityConstants.NOT_VALID_VALUES) {
                    return;
                } else if (validityResult == SQLiteDBHelper.StudentsDataValidator.ValidityConstants.VALID_ROW) {
                    studentDAO.addStudentToDB(getStudentDBModelFromRow(rowData));
                    setStudentsNum(String.valueOf(Integer.parseInt(studentsNum) + 1));
                } else if (validityResult == SQLiteDBHelper.StudentsDataValidator.ValidityConstants.UPDATE_ROW) {
                    studentDAO.updateStudentInDB(getStudentDBModelFromRow(rowData));
                }
                pnlContentLayout.removeAll();
                jbtShowStudentsList.doClick();
            }
        };
    }

    /**
     * Добавляет строку в таблицу студентов.
     *
     * @param rowIndex        индекс строки
     * @param newRowStudentID идентификатор новой строки студента
     * @param tableModel      модель таблицы
     * @param isFirstRow      флаг, указывающий, является ли это первой строкой
     */
    private static void addRowToStudentsTable(int rowIndex, Object newRowStudentID, DefaultTableModel tableModel, boolean isFirstRow) {
        Object[] rowData = {newRowStudentID, "", "", "", false, "", "", false, ""};
        int newStudentIndex = isFirstRow ? 0 : rowIndex + 1;
        tableModel.insertRow(newStudentIndex, rowData);
        tableModel.fireTableRowsInserted(newStudentIndex, newStudentIndex);
    }

    /**
     * Возвращает модель студента из строки данных.
     *
     * @param rowData строка данных
     * @return модель студента
     */
    private StudentDatabaseModel getStudentDBModelFromRow(Object[] rowData) {
        return new StudentDatabaseModel((int) rowData[0], groupNumber, rowData[1].toString(), rowData[2].toString(), rowData[3].toString(), (boolean) rowData[4], rowData[5].toString() == null ? "" : rowData[5].toString(), rowData[6].toString() == null ? "" : rowData[6].toString(), (boolean) rowData[7], rowData[8].toString());
    }

    /**
     * Инициализирует ввод таблицы студентов.
     *
     * @param jTableStudentsList таблица студентов
     */
    private void initJTableInput(JTable jTableStudentsList) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) jTableStudentsList.getModel();
        addRowToStudentsTable(0, 0, defaultTableModel, true);
        setStudentsNum("1");
    }

    /**
     * Метод для выполнения экспорта в файл.
     *
     * @param fileType тип файла для экспорта (WORD или EXCEL)
     */
    private void actionExportPerformed(FilePathChooserDialog.FileType fileType) {
        TableExporter exporter;
        if (fileType == FilePathChooserDialog.FileType.WORD) {
            exporter = new TableExporterToWord();
        } else if (fileType == FilePathChooserDialog.FileType.EXCEL) {
            exporter = new TableExporterToExcel();
        } else {
            throw new IllegalArgumentException("Неподдерживаемый тип файла");
        }

        String filepath = FilePathChooserDialog.createFile(fileType);
        exporter.export(studentsTable, filepath);
    }

    /**
     * Метод для выполнения экспорта в файл Word.
     *
     */
    private void actionExportToWordPerformed() {
        if (studentsTable != null) {
            actionExportPerformed(FilePathChooserDialog.FileType.WORD);
        } else {
            JOptionPane.showMessageDialog(null, "Отобразите таблицу, которую необходимо вывести.");
        }
    }

    /**
     * Метод для выполнения экспорта в файл Excel.
     *
     */
    private void actionExportToExcelPerformed() {
        if (studentsTable != null) {
            actionExportPerformed(FilePathChooserDialog.FileType.EXCEL);
        } else {
            JOptionPane.showMessageDialog(null, "Отобразите таблицу, которую необходимо вывести.");
        }
    }

    /**
     * Метод setStudentsNum устанавливает количество студентов.
     *
     * @param studentsNum Количество студентов.
     */
    public void setStudentsNum(String studentsNum) {
        lblStudentsNumValue.setText(studentsNum);
    }

    /**
     * Возвращает кнопку jbtToWordExport.
     *
     * @return кнопка jbtToWordExport
     */
    public JButton getJbtToWordExport() {
        return jbtToWordExport;
    }

    /**
     * Возвращает кнопку jbtToExcelExport.
     *
     * @return кнопка jbtToExcelExport
     */
    public JButton getJbtToExcelExport() {
        return jbtToExcelExport;
    }
}