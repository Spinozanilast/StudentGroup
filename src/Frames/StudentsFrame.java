package Frames;

import CustomComponents.CustomLightJTableWithActionColumn;
import CustomComponents.CustomTableActionCells.TableActionCellEvent;
import CustomComponents.PillButton;
import Database.DAOS.StudentDAO;
import Database.Managers.SQLiteConnectionProvider;
import Database.Managers.SQLiteDBHelper;
import Frames.models.Student;
import TableExport.FilePathChooserDialog;
import TableExport.TableExporter;
import TableExport.TableExporterToExcel;
import TableExport.TableExporterToWord;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
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
     * Всплывающее меню.
     */
    private static JPopupMenu popupMenu;
    /**
     * Объект доступа к данным студента.
     */
    private static StudentDAO studentDAO;
    /**
     * Главная панель.
     */
    private final JPanel pnlMain = new JPanel();
    /**
     * Внутреннее меню панели.
     */
    private final JPanel pnlUpButtons = new JPanel();
    /**
     * Внутренние атрибуты панели.
     */
    private final JPanel pnlInnerAttributes = new JPanel();
    /**
     * Внутренняя верхняя панель.
     */
    private final JPanel pnlShortGroupInfo = new JPanel();
    /**
     * Макет содержимого панели.
     */
    private final JPanel pnlContentLayout = new JPanel();
    /**
     * Нижняя панель, содержащая кнопки "Выход", "Об Авторе", "О Программе"
     */
    private final JPanel pnlDownButtonsPanel = new JPanel();
    /**
     * Номер группы.
     */
    private final String groupNumber;

    /**
     * Число студентов.
     */
    private final String studentsNum;
    /**
     * Провайдер соединения SQLite.
     */
    SQLiteConnectionProvider sqLiteConnectionProvider;
    /**
     * Значение числа студентов.
     */
    private JLabel lblStudentsNumValue;
    /**
     * Кнопка для отображения списка студентов.
     */
    private JButton jbtShowStudentsTable;
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
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/AloneGroupIcon.png")));
        setIconImage(icon.getImage());

        // Устанавливаем операцию закрытия окна, размер и расположение
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);

        // Настраиваем компоненты представления панели
        setLayouts();
        setUpPanelViewComponents(courseNumber, studentsNum, headmanFullName);

        // Настраиваем верхнюю панель кнопок
        setUpButtonsPanel();

        // Настраиваем нижнюю панель кнопок
        setDownButtonsPanel();

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
        return new PillButton(columnName, activatedBackground, notActivatedBackground, activatedForeground, notActivatedForeground);
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
     * Метод setLayouts устанавливает компоновки для панелей.
     */
    private void setLayouts() {
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlUpButtons.setLayout(new BoxLayout(pnlUpButtons, BoxLayout.X_AXIS));
        pnlInnerAttributes.setLayout(new BoxLayout(pnlInnerAttributes, BoxLayout.X_AXIS));
        pnlShortGroupInfo.setLayout(new BoxLayout(pnlShortGroupInfo, BoxLayout.X_AXIS));
        pnlContentLayout.setLayout(new BoxLayout(pnlContentLayout, BoxLayout.Y_AXIS));
        pnlDownButtonsPanel.setLayout(new BoxLayout(pnlDownButtonsPanel, BoxLayout.X_AXIS));

        pnlMain.add(pnlShortGroupInfo);
        pnlMain.add(pnlUpButtons);
        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(pnlInnerAttributes);
        pnlMain.add(pnlContentLayout);
        pnlMain.add(pnlDownButtonsPanel);
        pnlMain.setBackground(PANEL_BACKGROUND);
        pnlInnerAttributes.setBackground(PANEL_BACKGROUND);
        pnlContentLayout.setBackground(PANEL_BACKGROUND);

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
        int strutWidth = 15;

        JLabel lblCourse = new JLabel("Курс: ");
        JLabel lblStudentsNum = new JLabel("Количество студентов: ");
        JLabel lblHeadmanFullName = new JLabel("Староста: ");
        JLabel lblCourseValue = new JLabel(course);
        JLabel lblHeadmanValue = new JLabel(headmanFullName);
        lblStudentsNumValue = new JLabel(studentsCount);

        pnlShortGroupInfo.setLayout(new BoxLayout(pnlShortGroupInfo, BoxLayout.X_AXIS));

        stylizeLabels(LABEL_FOREGROUND, lblCourse, lblStudentsNum, lblHeadmanFullName);
        stylizeLabels(Color.BLACK, lblCourseValue, lblStudentsNumValue, lblHeadmanValue);

        pnlShortGroupInfo.add(Box.createHorizontalStrut(strutWidth));
        pnlShortGroupInfo.add(lblCourse);

        pnlShortGroupInfo.add(Box.createHorizontalStrut(strutWidth));
        pnlShortGroupInfo.add(lblCourseValue);

        pnlShortGroupInfo.add(Box.createHorizontalStrut(strutWidth));
        pnlShortGroupInfo.add(Box.createHorizontalGlue());
        pnlShortGroupInfo.add(lblStudentsNum);

        pnlShortGroupInfo.add(Box.createHorizontalStrut(strutWidth));
        pnlShortGroupInfo.add(lblStudentsNumValue);

        pnlShortGroupInfo.add(Box.createHorizontalStrut(strutWidth));
        pnlShortGroupInfo.add(Box.createHorizontalGlue());
        pnlShortGroupInfo.add(lblHeadmanFullName);

        pnlShortGroupInfo.add(Box.createHorizontalStrut(strutWidth));
        pnlShortGroupInfo.add(lblHeadmanValue);
        pnlShortGroupInfo.add(Box.createHorizontalStrut(strutWidth));

        pnlShortGroupInfo.setBackground(PANEL_BACKGROUND);
    }
    /**
     * Метод getJbtAboutAuthor создает кнопку "Об авторе".
     *
     * @return JButton, который отображает информацию об авторе при нажатии.
     */
    public JButton getJbtAboutAuthor() {
        JButton jbtAboutAuthorInfo = new JButton("Об авторе", new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/CustomComponents/Icons/AuthorButtonIcon.png"))));
        jbtToWordExport.setToolTipText("Показать краткие сведения об авторе приложения");
        jbtAboutAuthorInfo.addActionListener(e -> {
            AboutAuthorFrame aboutAuthorFrame = new AboutAuthorFrame();
            aboutAuthorFrame.setLocationRelativeTo(null);
        });
        return jbtAboutAuthorInfo;
    }

    /**
     * Метод getJbtAboutApp создает кнопку "О приложении".
     *
     * @return JButton, который отображает информацию о приложении при нажатии.
     */
    public JButton getJbtAboutApp() {
        JButton jbtAboutAppInfo = new JButton("О приложении", new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/CustomComponents/Icons/AboutButtonIcon.png"))));
        jbtAboutAppInfo.setToolTipText("Показать краткую информацию о приложении");
        jbtAboutAppInfo.addActionListener(e -> {
            AboutAppFrame aboutAppFrame = new AboutAppFrame();
            aboutAppFrame.setLocationRelativeTo(null);
        });
        return jbtAboutAppInfo;
    }

    /**
     * Метод getExitButton создает кнопку "Закрыть окно".
     *
     * @return JButton, который закрывает окно при нажатии.
     */
    public JButton getExitButton() {
        JButton jbtExit = new JButton("Закрыть окно", new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/CustomComponents/Icons/CloseButtonIcon.png"))));
        jbtExit.setToolTipText("Закрывает окно редактирования данной группы");
        jbtExit.addActionListener(e -> {
            int choice = JOptionPane.showOptionDialog(null, "Вы действительно хотите Выйти? Убедитесь, что все необходимые пользователи добавлены в базу данных (правая клавиша на панели действий)",
                    "Подтверждение выхода", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Да", "Нет"}, "Да");
            if (choice != 0) return;
            JFrame jFrame = (JFrame) jbtExit.getTopLevelAncestor();
            jFrame.dispose();
        });
        return jbtExit;
    }

    /**
     * Метод setUpButtonsPanel устанавливает верхнюю панель кнопок.
     */
    private void setUpButtonsPanel() {
        jbtShowStudentsTable = new JButton("Список студентов", new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/CustomComponents/Icons/TableButtonIcon.png"))));
        jbtShowStudentsTable.setToolTipText("Вывести список всех студентов для данной группы");

        jbtToWordExport = new JButton("Экспорт", new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/CustomComponents/Icons/WordIcon.png"))));

        jbtToExcelExport = new JButton("Экспорт", new ImageIcon(Objects.requireNonNull(getClass()
                .getResource("/CustomComponents/Icons/ExcelIcon.png"))));
        jbtToExcelExport.setToolTipText("Экспортирует таблицу со студентами в Excel");

        stylizeButtons(BUTTON_BACKGROUND, jbtShowStudentsTable);
        stylizeButtons(new Color(24, 90, 189), jbtToWordExport);
        stylizeButtons(new Color(16, 124, 65), jbtToExcelExport);

        pnlUpButtons.setBackground(PANEL_BACKGROUND);
        pnlUpButtons.setBorder(getTitledBorder("Действия с данными о студентах"));
        pnlInnerAttributes.setBackground(PANEL_BACKGROUND);
        pnlContentLayout.setBackground(PANEL_BACKGROUND);
        addButton(pnlUpButtons, jbtShowStudentsTable, false);
        addButton(pnlUpButtons, jbtToWordExport, false);
        addButton(pnlUpButtons, jbtToExcelExport, true);
    }

    /**
     * Метод setUpButtonsPanel устанавливает нижнюю панель кнопок с дополнительной информацией.
     */
    private void setDownButtonsPanel() {
        JButton jbtAboutAppInfo = getJbtAboutApp();
        JButton jbtAboutAuthorInfo = getJbtAboutAuthor();
        JButton jbtExit = getExitButton();

        stylizeButtons(BUTTON_BACKGROUND, jbtAboutAppInfo, jbtAboutAuthorInfo);
        stylizeButtons(Color.RED, jbtExit);

        pnlDownButtonsPanel.setBackground(PANEL_BACKGROUND);
        pnlDownButtonsPanel.setBorder(getTitledBorder("Дополнительные сведения и действия"));
        addButton(pnlDownButtonsPanel, jbtAboutAppInfo, false);
        addButton(pnlDownButtonsPanel, jbtAboutAuthorInfo, false);
        addButton(pnlDownButtonsPanel, jbtExit, true);
    }

    /**
     * Возвращает объект TitledBorder с настроенным шрифтом и стилем.
     *
     * @param title     Текст границы панели
     * @return          объект TitledBorder с настроенным шрифтом и стилем
     */
    private static TitledBorder getTitledBorder(String title) {
        TitledBorder pnlUpTitledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.lightGray),
                title,
                TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION);
        pnlUpTitledBorder.setTitleFont(new Font("Montserrat", Font.ITALIC, 12));
        return pnlUpTitledBorder;
    }

    /**
     * Метод addButton добавляет кнопку на панель.
     *
     * @param pnlLayout   Панель, которая будет содержать кнопки
     * @param button      Кнопка.
     * @param isEndButton Флаг, указывающий, является ли кнопка последней на панели.
     */
    private void addButton(JPanel pnlLayout, JButton button, boolean isEndButton) {
        pnlLayout.add(button);
        if (isEndButton) return;
        pnlLayout.add(Box.createHorizontalGlue());
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
        jbtShowStudentsTable.addActionListener(actionListenerShowStudentsTable);
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
            Object[] columnsNames = Student.StudentsJTableModelInfo.getTableColumnsNamesWithoutGroup();
            int columnsNumber = columnsNames.length;
            Object[][] studentsData = SQLiteDBHelper.getStudentsTableData(connection, groupNumber, columnsNumber);
            studentsTable = getCustomLightJTableWithActionColumn(studentsData, columnsNames);
            studentsTable.setCustomBooleanIntegerRenderers(Student.StudentsJTableModelInfo.getBooleanColumnsIndexes(),
                    Student.StudentsJTableModelInfo.getIntegerColumnsIndexes());
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
    private void addPopupMenuItems(CustomLightJTableWithActionColumn studentsTable, JMenuItem addMenuItem,
                                   JMenuItem deleteMenuItem, JMenuItem updateMenuItem) {
        addMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = studentsTable.getSelectedRow();
                if (rowIndex < 0) {
                    if (isEmptyTable(studentsTable)) {
                        initJTableInput(studentsTable);
                    } else {
                        JOptionPane.showMessageDialog(null, "Выберите строку таблицы, после " +
                                "которой" + " необходимо добавить студента");
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
                jbtShowStudentsTable.doClick();
            }
        });
    }

    /**
     * Возвращает события действий таблицы.
     *
     * @return события действий таблицы
     */
    private TableActionCellEvent getTableActionEvents() {
        return new TableActionCellEvents();
    }

    /**
     * Возвращает модель студента из строки данных.
     *
     * @param rowData строка данных
     * @return модель студента
     */
    private Student getStudentDBModelFromRow(Object[] rowData) {
        return new Student((int) rowData[0], groupNumber, rowData[1].toString(), rowData[2].toString(), rowData[3].toString(), (boolean) rowData[4], rowData[5].toString() == null ? "" : rowData[5].toString(), rowData[6].toString() == null ? "" : rowData[6].toString(), (boolean) rowData[7], rowData[8].toString());
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

    /**
     * Класс, реализующий события действий таблицы.
     */
    private class TableActionCellEvents implements TableActionCellEvent {

        /**
         * Добавляет строку в таблицу.
         *
         * @param rowIndex индекс строки
         * @param jTable   таблица
         */
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

        /**
         * Удаляет строку из таблицы.
         *
         * @param rowIndex индекс строки
         * @param jTable   таблица
         */
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

        /**
         * Обновляет базу данных.
         *
         * @param rowIndex индекс строки
         * @param jTable   таблица
         */
        @Override
        public void onUpdateDB(int rowIndex, JTable jTable) {
            DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
            int columnCount = tableModel.getColumnCount();
            Object[] rowData = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                rowData[i] = tableModel.getValueAt(rowIndex, i);
            }
            SQLiteDBHelper.StudentsDataValidator.ValidityConstants validityResult = SQLiteDBHelper.StudentsDataValidator.validateData(rowData, connection, groupNumber);
            if (validityResult == SQLiteDBHelper.StudentsDataValidator.ValidityConstants.NOT_VALID_VALUES) {
                return;
            } else if (validityResult == SQLiteDBHelper.StudentsDataValidator.ValidityConstants.VALID_ROW) {
                studentDAO.addStudentToDB(getStudentDBModelFromRow(rowData));
                setStudentsNum(String.valueOf(Integer.parseInt(studentsNum) + 1));
            } else if (validityResult == SQLiteDBHelper.StudentsDataValidator.ValidityConstants.UPDATE_ROW) {
                studentDAO.updateStudentInDB(getStudentDBModelFromRow(rowData));
            }
            pnlContentLayout.removeAll();
            jbtShowStudentsTable.doClick();
        }
    }
}