package Forms.controllers;

import Database.DAOS.StudentDAO;
import Database.Managers.SQLiteConnectionProvider;
import Forms.models.StudentsModel;
import Forms.views.GroupFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Контроллер формы группы, отвечающий за обработку событий и взаимодействие с моделью и представлением.
 * Реализует ActionListener для обработки событий кнопок.
 * <p>
 * Автор: Будчанин В.А.
 * Версия: 1.0
 */
public class GroupFormController {
    private StudentDAO studentDAO;
    private StudentsModel studentsModel;
    private final GroupFrame groupFrame;
    private JPanel contentLayoutPanel;
    private JButton jbtListStudents;
    private JButton jbtShowStatistics;
    private JTable studentsTable;

    /**
     * Конструктор контроллера формы группы.
     *
     * @param studentsModel  модель студентов.
     * @param groupData      данные группы.
     * @param studentNumber  номер студента.
     */
    public GroupFormController(StudentsModel studentsModel, String[] groupData, String studentNumber) {
        SQLiteConnectionProvider sqLiteConnectionProvider = new SQLiteConnectionProvider();
        Connection connection = sqLiteConnectionProvider.getConnection();
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
        studentsTable = new JTable();
        initButtonsListeners();
    }

    /**
     * Инициализирует слушателей событий для кнопок.
     */
    private void initButtonsListeners() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel = studentsModel.getRepresentationalTableModel();
                JTable jtblStudents = new JTable(tableModel);
                contentLayoutPanel.add(jtblStudents);
            }
        };
        jbtListStudents.addActionListener(actionListener);
    }

    public void showGroupFrame() {
        groupFrame.setVisible(true);
    }
}