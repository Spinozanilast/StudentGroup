package Forms.controllers;

import CustomComponents.CustomLightJTableWithActionColumn;
import CustomComponents.CustomTableActionCells.TableActionEvent;
import Database.DAOS.StudentDAO;
import Database.Managers.SQLiteConnectionProvider;
import Database.Managers.SQLiteDBManager;
import Forms.models.StudentsModel;
import Forms.views.GroupFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
                DefaultTableModel defaultTableModel = new DefaultTableModel(studentsData, columnsNames);
                CustomLightJTableWithActionColumn studentsTable = new CustomLightJTableWithActionColumn(defaultTableModel);
                studentsTable.addActionColumn(getTableActionEvents());
                studentsTable.setCustomBooleanColumnRenderer(StudentsModel.getBooleanColumnsIndexes());
                JScrollPane tableScrollPane = new JScrollPane(studentsTable);
                contentLayoutPanel.add(tableScrollPane);
                groupFrame.revalidate();
                groupFrame.repaint();
            }
        };
        jbtListStudents.addActionListener(actionListener);
    }

    private TableActionEvent getTableActionEvents(){
        return new TableActionEvent() {
            @Override
            public void onEdit(int rowIndex, JTable jTable) {

            }

            @Override
            public void onDelete(int rowIndex, JTable jTable) {
                DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
                String studentID = tableModel.getValueAt(rowIndex, 0).toString();
                studentDAO.deleteStudent(studentID);
                tableModel.removeRow(rowIndex);
            }

            @Override
            public void onView(int rowIndex, JTable jTable) {

            }
        };
    }

    public void showGroupFrame() {
        groupFrame.setVisible(true);
    }
}