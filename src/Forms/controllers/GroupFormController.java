package Forms.controllers;

import Database.DAOS.StudentDAO;
import Database.Managers.SQLiteConnectionProvider;
import Database.Managers.SQLiteDBManager;
import Forms.models.StudentsModel;
import Forms.views.GroupFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Контроллер формы группы, отвечающий за обработку событий и взаимодействие с моделью и представлением.
 * Реализует ActionListener для обработки событий кнопок.
 * <p>
 * @author Будчанин В.А.
 * @version  1.0
 */
public class GroupFormController {
    private StudentDAO studentDAO;
    private StudentsModel studentsModel;
    private final GroupFrame groupFrame;
    private JPanel contentLayoutPanel;
    private JButton jbtListStudents;
    private JButton jbtShowStatistics;
    private JTable studentsTable;
    private Connection connection;

    /**
     * Конструктор контроллера формы группы.
     *
     * @param studentsModel  модель студентов.
     * @param groupData      данные группы.
     * @param studentNumber  номер студента.
     */
    public GroupFormController(StudentsModel studentsModel, String[] groupData, String studentNumber) {
        SQLiteConnectionProvider sqLiteConnectionProvider = new SQLiteConnectionProvider();
        connection = sqLiteConnectionProvider.getConnection();
        studentDAO = new StudentDAO(connection);
        this.studentsModel = studentsModel;
        groupFrame = new GroupFrame(groupData, studentNumber);
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
            Object[] columnsNames = StudentsModel.getTableColumnsNamesWithoutGroup();
            int columnsNumber = columnsNames.length;
            Object[][] studentsTableModel = SQLiteDBManager.getStudentsTableData(connection, groupFrame.getGroupNumber(), columnsNumber);
            JTable studentsTable = new JTable(studentsTableModel, columnsNames);
            setTableCustomCellsRenderers(studentsTable);
            JScrollPane tableScrollPane = new JScrollPane(studentsTable);
            contentLayoutPanel.add(tableScrollPane);
            groupFrame.repaint();
        };
        jbtListStudents.addActionListener(actionListener);
    }

    private void setTableCustomCellsRenderers(JTable studentsTable) {
        int[] columnsIndexesToCustomize = StudentsModel.getBooleanColumnsIndexes();
        for(int columnIndex: columnsIndexesToCustomize){
            studentsTable.getColumnModel().getColumn(columnIndex).setCellRenderer(new CustomBooleanRenderer());
        }
    }

    public void showGroupFrame() {
        groupFrame.setVisible(true);
    }

    static class CustomBooleanRenderer extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            if (value instanceof Boolean) {
                Boolean boolValue = (Boolean) value;
                if (boolValue) {
                    setText("Действительно");
                } else {
                    setText("Нет");
                }
            }
        }
    }
}