package Forms.controllers;

import CustomComponents.CustomLightJTableWithActionColumn;
import CustomComponents.CustomTableActionCells.TableActionEvent;
import Database.DAOS.StudentDAO;
import Database.Managers.SQLiteConnectionProvider;
import Database.Managers.SQLiteDBManager;
import Database.Managers.SQLiteDBManager.StudentsDataValidator.ValidityConstants;
import Database.Models.StudentDatabaseModel;
import Forms.models.StudentsModel;
import Forms.views.GroupFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Контроллер формы группы, отвечающий за обработку событий и взаимодействие с моделью и представлением.
 * Реализует ActionListener для обработки событий кнопок.
 *
 * @author Будчанин В.А.
 * @version  1.0
 */
public class GroupFormController {
    private static StudentDAO studentDAO;
    private StudentsModel studentsModel;
    private final GroupFrame groupFrame;
    private final JPanel contentLayoutPanel;
    private static JButton jbtListStudents;
    private final JButton jbtShowStatistics;
    private JTable studentsTable;
    private final Connection connection;
    private static JPopupMenu popupMenu;

    /**
     * Конструктор контроллера формы группы.
     *
     * @param studentsModel  модель студентов.
     * @param groupData      данные группы.
     */
    public GroupFormController(StudentsModel studentsModel, String[] groupData) {
        SQLiteConnectionProvider sqLiteConnectionProvider = new SQLiteConnectionProvider();
        connection = sqLiteConnectionProvider.getConnection();
        studentDAO = new StudentDAO(connection);
        this.studentsModel = studentsModel;
        groupFrame = new GroupFrame(groupData, String.valueOf(studentDAO.getCountOfGroupStudents(groupData[0])));
        groupFrame.addWindowListener(new WindowAdapter() {
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
        contentLayoutPanel = groupFrame.getContentLayoutPanel();
        jbtListStudents = groupFrame.getJbtShowStudentsList();
        jbtShowStatistics = groupFrame.getJbtShowStatistics();
        initButtonsListeners();
    }

    /**
     * Инициализирует слушателей событий для кнопок.
     */
    private void initButtonsListeners() {
        ActionListener actionListener = e -> {
            if (contentLayoutPanel.getComponentCount() == 0) {
                Object[] columnsNames = StudentsModel.getTableColumnsNamesWithoutGroup();
                int columnsNumber = columnsNames.length;
                Object[][] studentsData = SQLiteDBManager.getStudentsTableData(connection, groupFrame.getGroupNumber(), columnsNumber);
                CustomLightJTableWithActionColumn studentsTable = getCustomLightJTableWithActionColumn(studentsData, columnsNames);
                studentsTable.setCustomBooleanIntegerRenderers(StudentsModel.getBooleanColumnsIndexes(),
                        StudentsModel.getIntegerColumnsIndexes());
                studentsTable.addActionColumn(getTableActionEvents());
                studentsTable.setPreferredSize(new Dimension(500,500));
                if (studentsData.length == 0) {
                    initJTableInput(studentsTable);
                }
                JScrollPane tableScrollPane = new JScrollPane(studentsTable);
                contentLayoutPanel.add(tableScrollPane);
                addTablePopupMenu(studentsTable);
                groupFrame.revalidate();
                groupFrame.repaint();
                initTablePopupMenu(studentsTable);
            }
        };
        jbtListStudents.addActionListener(actionListener);
    }

    /**
     * Инициализирует всплывающее меню для таблицы студентов.
     *
     * @param studentsTable  таблица студентов
     */
    private static void initTablePopupMenu(CustomLightJTableWithActionColumn studentsTable) {
        popupMenu = new JPopupMenu();

        JMenuItem addMenuItem = new JMenuItem("Добавить строку");
        addMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        JMenuItem deleteMenuItem = new JMenuItem("Удалить студента");
        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));

        JMenuItem updateMenuItem = new JMenuItem("Обновить таблицу");
        updateMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));

        addPopupMenuItems(studentsTable, addMenuItem, deleteMenuItem, updateMenuItem);
        popupMenu.add(addMenuItem);
        popupMenu.add(deleteMenuItem);
        popupMenu.add(updateMenuItem);
    }

    /**
     * Добавляет элементы во всплывающее меню.
     *
     * @param studentsTable    таблица студентов
     * @param addMenuItem      элемент меню "Добавить строку"
     * @param deleteMenuItem   элемент меню "Удалить студента"
     * @param updateMenuItem   элемент меню "Обновить таблицу"
     */
    private static void addPopupMenuItems(CustomLightJTableWithActionColumn studentsTable, JMenuItem addMenuItem, JMenuItem deleteMenuItem, JMenuItem updateMenuItem) {
        addMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = studentsTable.getSelectedRow();
                if (rowIndex < 0) {
                    if (isEmptyTable(studentsTable)) {
                        initJTableInput(studentsTable);
                    } else {
                        JOptionPane.showMessageDialog(null, "Выберите строку таблицы, после которой" +
                                " необходимо добавить студента");
                    }
                    return;
                }
                DefaultTableModel tableModel = (DefaultTableModel) studentsTable.getModel();
                Object previousStudentID = 0;
                previousStudentID = tableModel.getValueAt(rowIndex, 0);
                addRowToStudentsTable(rowIndex, (int) previousStudentID + 1, tableModel, false);
            }
        });

        deleteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isEmptyTable(studentsTable)) return;
                int[] rowIndexes = studentsTable.getSelectedRows();
                if (rowIndexes.length == 0) {
                    JOptionPane.showMessageDialog(null, "Выберите студента, которого необходимо удалить");
                    return;
                }
                DefaultTableModel tableModel = (DefaultTableModel) studentsTable.getModel();
                for (int rowIndex: rowIndexes){
                    String studentID = tableModel.getValueAt(rowIndex, 0).toString();
                    studentDAO.deleteStudent(studentID);
                    tableModel.removeRow(rowIndex);
                }
            }
        });

        updateMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jbtListStudents.doClick();
            }
        });
    }

    /**
     * Проверяет, является ли таблица пустой.
     *
     * @param studentsTable  таблица студентов
     * @return true, если таблица пустая, иначе false
     */
    private static boolean isEmptyTable(JTable studentsTable) {
        return ((DefaultTableModel) studentsTable.getModel()).getRowCount() == 0;
    }

    private static void addTablePopupMenu(CustomLightJTableWithActionColumn studentsTable) {
        studentsTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                showPopupMenu(e);
            }

            public void mouseReleased(MouseEvent e) {
                showPopupMenu(e);
            }

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
     * @param studentsData    данные студентов
     * @param columnsNames    названия столбцов
     * @return настраиваемая таблица студентов
     */
    private static CustomLightJTableWithActionColumn getCustomLightJTableWithActionColumn(Object[][] studentsData, Object[] columnsNames) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(studentsData, columnsNames);
        CustomLightJTableWithActionColumn studentsTable = new CustomLightJTableWithActionColumn(defaultTableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return switch (column) {
                    case 0 -> Integer.class;
                    case 1, 2, 3, 5, 6 -> String.class;
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
    private TableActionEvent getTableActionEvents() {
        return new TableActionEvent() {
            @Override
            public void onAddRow(int rowIndex, JTable jTable) {
                DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
                Object newRowStudentID = (int) tableModel.getValueAt(rowIndex, 0) + 1;
                addRowToStudentsTable(rowIndex, newRowStudentID, tableModel, false);
            }

            @Override
            public void onDelete(int rowIndex, JTable jTable) {
                DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
                String studentID = tableModel.getValueAt(rowIndex, 0).toString();
                studentDAO.deleteStudent(studentID);
                groupFrame.setStudentsNum(String.valueOf(studentDAO.getCountOfGroupStudents(groupFrame.getGroupNumber())));
                int rowCount = tableModel.getRowCount();
                if (rowIndex >= 0 && rowIndex < rowCount) {
                    tableModel.removeRow(rowIndex);
                } else {
                    System.out.println("Invalid row index");
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
                ValidityConstants validityResult = SQLiteDBManager.StudentsDataValidator.isValidData(rowData, connection, groupFrame.getGroupNumber());
                if (validityResult == ValidityConstants.NOT_VALID_VALUES) {
                    return;
                } else if (validityResult == ValidityConstants.VALID_ROW) {
                    studentDAO.addStudentToDB(getStudentDBModelFromRow(rowData));
                    groupFrame.setStudentsNum(String.valueOf(Integer.parseInt(groupFrame.getStudentsNum()) + 1));
                } else if (validityResult == ValidityConstants.UPDATE_ROW) {
                    studentDAO.updateStudentInDB(getStudentDBModelFromRow(rowData));
                }
                contentLayoutPanel.removeAll();
                jbtListStudents.doClick();
            }
        };
    }

    /**
     * Добавляет строку в таблицу студентов.
     *
     * @param rowIndex          индекс строки
     * @param newRowStudentID   идентификатор новой строки студента
     * @param tableModel        модель таблицы
     * @param isFirstRow        флаг, указывающий, является ли это первой строкой
     */
    private static void addRowToStudentsTable(int rowIndex, Object newRowStudentID, DefaultTableModel tableModel, boolean isFirstRow) {
        Object[] rowData = {newRowStudentID, "", "", "", false, "", "", false};
        int newStudentIndex = isFirstRow ? 0 : rowIndex + 1;
        tableModel.insertRow(newStudentIndex, rowData);
        tableModel.fireTableRowsInserted(newStudentIndex, newStudentIndex);
    }

    /**
     * Возвращает модель студента из строки данных.
     *
     * @param rowData   строка данных
     * @return модель студента
     */
    private StudentDatabaseModel getStudentDBModelFromRow(Object[] rowData) {
        return new StudentDatabaseModel(
                (int) rowData[0],
                groupFrame.getGroupNumber(),
                rowData[1].toString(),
                rowData[2].toString(),
                rowData[3].toString(),
                (boolean) rowData[4],
                rowData[5].toString(),
                rowData[6].toString(),
                (boolean) rowData[7]
        );
    }

    /**
     * Инициализирует ввод таблицы студентов.
     *
     * @param jTableStudentsList  таблица студентов
     */
    private static void initJTableInput(JTable jTableStudentsList) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) jTableStudentsList.getModel();
        addRowToStudentsTable(0, 0, defaultTableModel, true);
    }

    /**
     * Отображает окно группы.
     */
    public void showGroupFrame() {

        groupFrame.setVisible(true);
    }
}