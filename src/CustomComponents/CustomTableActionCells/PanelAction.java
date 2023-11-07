package CustomComponents.CustomTableActionCells;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Панель с кнопками действий.
 *
 * <p>Панель, содержащая кнопки действий для добавления, удаления и обновления данных в таблице.</p>
 *
 * @version 1.0
 * @since 2022-10-01
 * @author Будчанин В.А.
 */
public class PanelAction extends javax.swing.JPanel {
    private ActionButton actionButtonDeleteStudent;
    private ActionButton actionButtonNewStudent;
    private ActionButton actionButtonUpdateDB;

    /**
     * Создает экземпляр панели действий.
     */
    public PanelAction() {
        initComponents();
    }

    /**
     * Инициализирует события для кнопок действий.
     *
     * @param event  события действий таблицы
     * @param row    индекс строки таблицы
     * @param jTable таблица студентов
     */
    public void initEvent(TableActionEvent event, int row, JTable jTable) {
        actionButtonNewStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.onAddRow(row, jTable);
            }
        });

        actionButtonDeleteStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.onDelete(row, jTable);
            }
        });

        actionButtonUpdateDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.onUpdateDB(row, jTable);
            }
        });
    }

    private void initComponents() {
        actionButtonNewStudent = new ActionButton();
        actionButtonDeleteStudent = new ActionButton();
        actionButtonUpdateDB = new ActionButton();

        actionButtonNewStudent.setIcon(new ImageIcon(getClass().getResource("/CustomComponents/CustomTableActionCells/AddStudentIcon.png")));
        actionButtonNewStudent.setToolTipText("Добавить запись студента после этой строки");

        actionButtonDeleteStudent.setIcon(new ImageIcon(getClass().getResource("/CustomComponents/CustomTableActionCells/DeleteRowIcon.png")));
        actionButtonDeleteStudent.setToolTipText("Удалить текущую запись студента");

        actionButtonUpdateDB.setIcon(new ImageIcon(getClass().getResource("/CustomComponents/CustomTableActionCells/AddToDBIcon.png")));
        actionButtonUpdateDB.setToolTipText("Обновить сведения о текущем студенте в базе данных");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(actionButtonNewStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(actionButtonDeleteStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(actionButtonUpdateDB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(actionButtonUpdateDB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(actionButtonDeleteStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(actionButtonNewStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        setOpaque(true);
    }
}