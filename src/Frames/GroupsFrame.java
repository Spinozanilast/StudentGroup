package Frames;

import CustomComponents.*;
import Database.DAOS.GroupDAO;
import Database.Managers.SQLiteConnectionProvider;
import Database.Models.GroupDatabaseModel;
import Frames.models.GroupViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Класс GroupsFrame представляет собой начальную форму создания учебной группы.
 *
 * @author Будчанин В.А.
 * @version  1.4
 */
public class GroupsFrame extends JFrame {
    /**
     * Предпочтительный размер стандартного элемента.
     */
    public static final Dimension STANDARD_ELEMENT_PREFFERED_SIZE = new Dimension(640,45);

    /**
     * Цвет фона.
     */
    public static final Color BACKGROUND_COLOR = new Color(243,243,243);

    /**
     * Цвет фона панели группы.
     */
    public static final Color GROUP_PANEL_BACKGROUND_COLOR = new Color(239,255,255);

    /**
     * Цвет текста неизменяемых полей группы.
     */
    public static final Color GROUP_NON_EDITABLE_FOREGROUND = new Color(29,105,200);

    /**
     * Цвет фона текстовых полей.
     */
    public static final Color TEXT_BOXES_BACKGROUND_COLOR = new Color(193,228,228, 179);

    /**
     * Шрифт для неизменяемых полей.
     */
    public static final Font FONT_NON_EDITABLE = new Font("Montserrat", Font.BOLD, 20);

    /**
     * Стандартный размер окна.
     */
    private static final Dimension STANDARD_FRAME_SIZE = new Dimension(1500,750);

    /**
     * Панель для добавления группы.
     */
    private AddGroupPanel addGroupPanel;

    /**
     * DAO группы.
     */
    private static GroupDAO groupDAO;

    /**
     * Флаг, указывающий, является ли панель группы редактируемой.
     */
    private boolean groupPanelIsEditable = false;

    /**
     * Редактируемая панель ввода группы.
     */
    private GroupView editableGroupView = null;

    /**
     * Предыдущий номер группы для редактируемой панели.
     */
    private String previousGroupNumberOfEditable;

    /**
     * Модель групп.
     */
    private GroupViews groupViews;

    /**
     * Панель с тремя метками действий.
     */
    private final ThreeActionLabelsPanel threeActionLabelsPanel = new ThreeActionLabelsPanel(Color.BLACK,
            GroupsFrame.BACKGROUND_COLOR, "Номер группы", "Номер курса", "ФИО старосты");

    /**
     * Панель для размещения групп.
     */
    private JPanel pnlLayoutGroups;


    /**
     * Конструктор класса StartForm.
     */
    public GroupsFrame(GroupViews groupViews) {
        initFormState();
        initLayoutPanel();
        initDBConnection(groupViews);
        initializeListenersForView();
    }

    /**
     * Инициализирует состояние формы.
     */
    private void initFormState(){
        setTitle("Студенческие группа");
        ImageIcon icon = new ImageIcon("assets/GroupIcon.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(STANDARD_FRAME_SIZE);
        setPreferredSize(STANDARD_FRAME_SIZE);
        setLocationRelativeTo(null);
        pnlLayoutGroups = new JPanel();
    }

    /**
     * Инициализирует панель макета.
     */
    private void initLayoutPanel(){
        setLayout(new BorderLayout());
        pnlLayoutGroups.setBackground(BACKGROUND_COLOR);
        pnlLayoutGroups.setLayout(new BoxLayout(pnlLayoutGroups, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(pnlLayoutGroups);
        scrollPane.createVerticalScrollBar();
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Добавляет компонент в макет.
     *
     * @param component компонент
     * @param hasVerticalStrutBefore флаг указывающий на необходимость добавления вертикального промежутка перед компонентом
     * @param hasVerticalStrutAfter флаг указывающий на необходимость добавления вертикального промежутка после компонента
     */
    public void addComponent(Component component, boolean hasVerticalStrutBefore, boolean hasVerticalStrutAfter) {
        if (hasVerticalStrutBefore) {
            pnlLayoutGroups.add(Box.createVerticalStrut(15));
        }
        pnlLayoutGroups.add(component);
        if (hasVerticalStrutAfter) {
            pnlLayoutGroups.add(Box.createVerticalStrut(15));
        }
    }

    /**
     * Добавляет слушатель событий мыши для панели добавления группы.
     *
     * @param addGroupPanel панель добавления группы
     * @param mouseAdapter слушатель событий мыши
     */
    public void addComponentMouseListener(AddGroupPanel addGroupPanel,
                                          MouseAdapter mouseAdapter) {
        addComponent(addGroupPanel, false, true);
        addGroupPanel.setMouseClickEvent(mouseAdapter);
    }

    /**
     * Безопасно удаляет компонент из макета.
     *
     * @param component компонент
     */
    public void safelyDeleteComponent(Component component){
        pnlLayoutGroups.remove(component);
        pnlLayoutGroups.revalidate();
        pnlLayoutGroups.repaint();
    }

    /**
     *  Создает новый контроллер начальной формы.
     *
     * @param groupViews модель, содержащая и обрабатывающая группы
     */
    private void initDBConnection(GroupViews groupViews) {
        SQLiteConnectionProvider sqLiteConnectionProvider = new SQLiteConnectionProvider();
        Connection connection = sqLiteConnectionProvider.getConnection();
        groupDAO = new GroupDAO(connection);

        this.groupViews = groupViews;
        addWindowListener(new WindowAdapter() {
            /**
             * Вызывается в процессе закрытия окна formView.
             *
             * @param e - экземпляр WindowEvent
             */
            @Override
            public void windowClosing(WindowEvent e) {
                try{
                    if (connection != null && !connection.isClosed()){
                        sqLiteConnectionProvider.closeConnection();
                    }
                }
                catch (SQLException exception){
                    exception.printStackTrace();
                }
            }
        });
    }

    /**
     * Инициализирует слушателей для представления.
     */
    private void initializeListenersForView() {
        threeActionLabelsPanel.getLeftLabel().addMouseListener(getLeftLabelMouseListener());
        threeActionLabelsPanel.getCenterLabel().addMouseListener(getCenterLabelMouseListener());
        threeActionLabelsPanel.getRightLabel().addMouseListener(getRightLabelMouseListener());
        threeActionLabelsPanel.setPreferredSize(GroupsFrame.STANDARD_ELEMENT_PREFFERED_SIZE);
        addComponent(threeActionLabelsPanel, false, false);
        createGroupsFromDB();
        addGroupPanel = new AddGroupPanel(Color.BLACK, GroupsFrame.GROUP_PANEL_BACKGROUND_COLOR);
        addGroupPanel.setToolTipText("Добавить новую группу");
        addGroupPanel.setPreferredSize(GroupsFrame.STANDARD_ELEMENT_PREFFERED_SIZE);
        addComponentMouseListener(addGroupPanel, getAddGroupPanelMouseListener());
    }

    /**
     * Возвращает слушатель событий мыши для меток.
     *
     * @param labelType тип метки
     * @return слушатель событий мыши
     */
    private MouseAdapter getLabelMouseListener(ThreeActionLabelsPanel.LabelType labelType) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (groupViews.getGroupPanels().isEmpty()) {
                    return;
                }
                setStartLabelsForLayout();
                switch (labelType) {
                    case LEFT:
                        GroupView.sortGroupListByLeftText(groupViews.getGroupPanels(), threeActionLabelsPanel.isLeftArrowDown());
                        threeActionLabelsPanel.setLeftLabelIcon(threeActionLabelsPanel.isLeftArrowDown() ?
                                ThreeActionLabelsPanel.DOWN_ARROW_ICON :
                                ThreeActionLabelsPanel.UP_ARROW_ICON);
                        break;
                    case CENTER:
                        GroupView.sortPanelListByCenterText(groupViews.getGroupPanels(), threeActionLabelsPanel.isCenterArrowDown());
                        threeActionLabelsPanel.setCenterLabelIcon(threeActionLabelsPanel.isCenterArrowDown() ?
                                ThreeActionLabelsPanel.DOWN_ARROW_ICON :
                                ThreeActionLabelsPanel.UP_ARROW_ICON);
                        break;
                    case RIGHT:
                        GroupView.sortPanelListByRightText(groupViews.getGroupPanels(), threeActionLabelsPanel.isRightArrowDown());
                        threeActionLabelsPanel.setRightLabelIcon(threeActionLabelsPanel.isRightArrowDown() ?
                                ThreeActionLabelsPanel.DOWN_ARROW_ICON :
                                ThreeActionLabelsPanel.UP_ARROW_ICON);
                        break;
                }

                addAllGroupPanels(false);
                pnlLayoutGroups.revalidate();
                pnlLayoutGroups.repaint();
            }
        };
    }

    /**
     * Устанавливает начальные метки для макета.
     */
    private void setStartLabelsForLayout() {
        pnlLayoutGroups.removeAll();
        addComponent(threeActionLabelsPanel, false, false);
    }

    /**
     * Добавляет все панели групп в макет.
     *
     * @param withClearing флаг указывающий на необходимость очистки макета перед добавлением
     */
    private void addAllGroupPanels(boolean withClearing) {
        if (withClearing) {
            setStartLabelsForLayout();
        }
        for (GroupView groupPanel : groupViews.getGroupPanels()) {
            groupPanel.setPreferredSize(GroupsFrame.STANDARD_ELEMENT_PREFFERED_SIZE);
            addComponent(groupPanel, false, false);
            JPanel utilitiesPanel = getGroupsUtilitiesPanel(groupPanel);
            utilitiesPanel.setPreferredSize(GroupsFrame.STANDARD_ELEMENT_PREFFERED_SIZE);
            addComponent(utilitiesPanel, false, false);
            addComponent(addGroupPanel, false, false);
        }
        pnlLayoutGroups.revalidate();
        pnlLayoutGroups.repaint();
    }

    /**
     * Возвращает слушателя событий мыши для левой метки.
     *
     * @return слушатель событий мыши
     */
    private MouseAdapter getLeftLabelMouseListener() {
        return getLabelMouseListener(ThreeActionLabelsPanel.LabelType.LEFT);
    }

    /**
     * Возвращает слушателя событий мыши для центральной метки.
     *
     * @return слушатель событий мыши
     */
    private MouseAdapter getCenterLabelMouseListener() {
        return getLabelMouseListener(ThreeActionLabelsPanel.LabelType.CENTER);
    }

    /**
     * Возвращает слушателя событий мыши для правой метки.
     *
     * @return слушатель событий мыши
     */
    private MouseAdapter getRightLabelMouseListener() {
        return getLabelMouseListener(ThreeActionLabelsPanel.LabelType.RIGHT);
    }

    /**
     * Возвращает слушателя событий мыши для панели добавления группы.
     *
     * @return слушатель событий мыши
     */
    private MouseAdapter getAddGroupPanelMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GroupView groupView = new GroupView(Color.BLACK, GroupsFrame.GROUP_PANEL_BACKGROUND_COLOR, GroupsFrame.TEXT_BOXES_BACKGROUND_COLOR, "№ Группы", "Курс", "ФИО старосты");
                groupView.setPreferredSize(GroupsFrame.STANDARD_ELEMENT_PREFFERED_SIZE);
                addComponent(groupView, false, false);
                addGroupPanel.repaint();
                pnlLayoutGroups.revalidate();
                HighResolutionImagePanel highResolutionImagePanel = new HighResolutionImagePanel(new HighResolutionImageLabel("Icons/Tick-Circle.png", 35, 35), 640, 45);
                Action doComponentsReload = new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addAllGroupPanels(true);
                    }
                };
                highResolutionImagePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
                highResolutionImagePanel.getActionMap().put("escape", doComponentsReload);

                highResolutionImagePanel.getInputMap().put(KeyStroke.getKeyStroke("ESC"), doComponentsReload);
                addComponent(highResolutionImagePanel, false, false);
                safelyDeleteComponent(addGroupPanel);
                highResolutionImagePanel.setFocusable(true);
                highResolutionImagePanel.addMouseListener(getHighResolutionImagePanelMouseListener(groupView, highResolutionImagePanel, false));
                highResolutionImagePanel.addKeyListener(getHighResolutionImagePanelKeyListener(groupView, highResolutionImagePanel, false));
            }
        };
    }

    /**
     * Возвращает слушателя событий мыши для панели с изображением высокого разрешения.
     *
     * @param groupView панель ввода группы
     * @param highResolutionImagePanel панель с изображением высокого разрешения
     * @param isEditMode флаг указывающий на режим редактирования
     * @return слушатель событий мыши
     */
    private MouseAdapter getHighResolutionImagePanelMouseListener(GroupView groupView, HighResolutionImagePanel highResolutionImagePanel, boolean isEditMode) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getResultGroupByInputValidity(groupView, highResolutionImagePanel, isEditMode);
            }
        };
    }

    /**
     * Возвращает слушателя событий клавиатуры для панели с изображением высокого разрешения.
     *
     * @param groupView панель ввода группы
     * @param highResolutionImagePanel панель с изображением высокого разрешения
     * @param isEditMode флаг указывающий на режим редактирования
     * @return слушатель событий клавиатуры
     */
    private KeyAdapter getHighResolutionImagePanelKeyListener(GroupView groupView, HighResolutionImagePanel highResolutionImagePanel, boolean isEditMode) {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    getResultGroupByInputValidity(groupView, highResolutionImagePanel, isEditMode);
                }
            }
        };
    }

    /**
     * Устанавливает состояние обложки группы в необходимое на основе валидности ввода.
     *
     * @param groupView панель ввода группы
     * @param highResolutionImagePanel панель с изображением высокого разрешения
     * @param isEditMode флаг указывающий на режим редактирования
     */
    private void getResultGroupByInputValidity(GroupView groupView, HighResolutionImagePanel highResolutionImagePanel, boolean isEditMode) {
        if (areGroupsContainingString(groupView) && !groupPanelIsEditable){
            groupView.setTextFieldsUnValid();
            JOptionPane.showMessageDialog(this, "Значения групп или ФИО старост совпадают, проверьте значения этих полей и исправьте либо удалите существующую группу.");
            return;
        }
        if (!groupView.isInputValid()){
            groupView.setTextFieldsUnValid();
            return;
        }
        else {
            groupView.setTextFieldsValid();
        }

        groupView.setPanelNonEditableCustom(GroupsFrame.GROUP_PANEL_BACKGROUND_COLOR, GroupsFrame.GROUP_NON_EDITABLE_FOREGROUND, GroupsFrame.FONT_NON_EDITABLE);

        if (!groupPanelIsEditable && !groupView.equals(editableGroupView)) {
            writeGroupToDB(groupView.getTextFieldValues());
        }
        else{
            groupDAO.editGroupData(previousGroupNumberOfEditable, groupView.getTextFieldValues()[0],
                    Integer.parseInt(groupView.getTextFieldValues()[1]),
                    groupView.getTextFieldValues()[2]);
            groupPanelIsEditable = false;
            editableGroupView = null;
            previousGroupNumberOfEditable = "";
        }

        if (!isEditMode) {
            JPanel utilitiesPanel = getGroupsUtilitiesPanel(groupView);
            utilitiesPanel.setPreferredSize(GroupsFrame.STANDARD_ELEMENT_PREFFERED_SIZE);
            safelyDeleteComponent(highResolutionImagePanel);
            addComponent(utilitiesPanel, false, false);
            addComponent(this.addGroupPanel, false, false);
            groupViews.addGroupPanel(groupView);
        }
        else {
            safelyDeleteComponent(highResolutionImagePanel);
            addAllGroupPanels(true);
        }
    }

    /**
     * Метод для записи информации о группе в базу данных.
     *
     * @param groupData Массив данных о группе, содержащий следующие элементы:
     * - groupNumber (номер группы)
     * - course (курс)
     * - headmanFullName (полное имя старосты)
     *
     * @throws NumberFormatException Если при попытке преобразовать курс в целое число возникает ошибка
     */
    private void writeGroupToDB(String[] groupData){
        String groupNumber = groupData[0];
        String course = groupData[1];
        String headmanFullName = groupData[2];

        try {
            groupDAO.addGroup(groupNumber, Integer.parseInt(course), headmanFullName);
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    /**
     * Возвращает панель утилит группы, также добавляет слушателей событый мыши для иконок,
     * с которыми можно взаимодействовать.
     *
     * @param groupView панель ввода группы
     * @return панель утилит группы
     */
    private JPanel getGroupsUtilitiesPanel(GroupView groupView) {
        Dimension imageLabelSize = new Dimension(27,27);
        JPanel groupPanel = new JPanel();
        groupPanel.setOpaque(false);
        groupPanel.setLayout(new GridLayout(1,3));

        HighResolutionImageLabel jlblOpenGroupForm = new HighResolutionImageLabel("UtilitiesIcons/Open-Form-Icon.png", 25, 25);
        jlblOpenGroupForm.setToolTipText("Открывает форму редактирования группы");
        jlblOpenGroupForm.setPreferredSize(imageLabelSize);
        HighResolutionImageLabel jlblDeleteGroup = new HighResolutionImageLabel("UtilitiesIcons/Delete-Icon.png", 25, 25);
        jlblDeleteGroup.setToolTipText("Удалить все данные, связанные с данной группой");
        jlblDeleteGroup.setPreferredSize(imageLabelSize);
        HighResolutionImageLabel jlblEditGroupData = new HighResolutionImageLabel("UtilitiesIcons/Edit-Icon.png", 25, 25);
        jlblDeleteGroup.setToolTipText("Редактировать обложку данной группы");
        jlblEditGroupData.setPreferredSize(imageLabelSize);
        jlblDeleteGroup.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int choice = JOptionPane.showOptionDialog(null, "Вы действительно хотите удалить все данные о группе?",
                        "Проверка", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Да", "Нет"}, "Да");
                if (choice != 0) return;

                groupDAO.deleteGroup(groupView.getTextFieldValues()[0]);
                groupViews.getGroupPanels().remove(groupView);
                addAllGroupPanels(true);
            }
        });
        jlblOpenGroupForm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                StudentsFrame studentsFrame = new StudentsFrame(groupView.getTextFieldValues());
                studentsFrame.setVisible(true);
            }
        });
        jlblEditGroupData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (groupPanelIsEditable) {
                    JOptionPane.showMessageDialog(null, "Закончите редактирование предыдущей панели.");
                    return;
                }
                int editablePanelIndex = groupViews.getGroupPanels().indexOf(groupView);
                setStartLabelsForLayout();
                for (int i = 0; i < groupViews.getGroupPanels().size(); i++){
                    if (i == editablePanelIndex){

                        groupPanelIsEditable = true;
                        editableGroupView = groupViews.getGroupPanels().get(i);
                        previousGroupNumberOfEditable = editableGroupView.getTextFieldValues()[0];

                        addComponent(groupViews.getGroupPanels().get(i),false, true);
                        groupViews.getGroupPanels().get(i).setPanelEditableStandardValues();
                        HighResolutionImagePanel highResolutionImagePanel = new HighResolutionImagePanel(new HighResolutionImageLabel("Icons/Tick-Circle.png", 35, 35), 640, 45);
                        addComponent(highResolutionImagePanel, false, true);
                        safelyDeleteComponent(addGroupPanel);
                        highResolutionImagePanel.setFocusable(true);
                        highResolutionImagePanel.addMouseListener(getHighResolutionImagePanelMouseListener(groupViews.getGroupPanels().get(i), highResolutionImagePanel, true));
                        continue;
                    }
                    addComponent(groupViews.getGroupPanels().get(i), true, true);
                    JPanel utilitiesPanel = getGroupsUtilitiesPanel(groupViews.getGroupPanels().get(i));
                    addComponent(utilitiesPanel, false, false);
                }
                addComponent(addGroupPanel, false, true);
            }
        });
        groupPanel.add(jlblOpenGroupForm);
        groupPanel.add(jlblEditGroupData);
        groupPanel.add(jlblDeleteGroup);
        groupPanel.setMaximumSize(new Dimension(630, 45));
        return groupPanel;
    }

    /**
     * Проверяет, содержатся ли значения из groupView в других группах.
     *
     * @param groupView панель ввода группы
     * @return true, если значения не содержатся в других группах, иначе false
     */
    private boolean areGroupsContainingString(GroupView groupView) {
        int numOfTextFields = groupView.getTextFieldValues().length;
        final int courseNumberIndexSkip = 1;
        String[] textFieldValues = groupView.getTextFieldValues();
        for (int i = 0; i < numOfTextFields; i++) {
            if (i == courseNumberIndexSkip){
                //Значит, что лишь значения номера группы и старосты не должно повторяться у разных групп
                continue;
            }
            if (isValuePresentInAnyGroup(textFieldValues[i], i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Проверяет, содержится ли значение в любой из групп.
     *
     * @param value значение для проверки
     * @param index индекс значения в массиве
     * @return true, если значение содержится в любой из групп, иначе false
     */
    private boolean isValuePresentInAnyGroup(String value, int index) {
        for (GroupView groupPanel : groupViews.getGroupPanels()) {
            String[] groupTextFieldValues = groupPanel.getTextFieldValues();
            if (value.equals(groupTextFieldValues[index])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Создает группы из базы данных.
     * Если таблица пустая, то ничего не делает.
     */
    private void createGroupsFromDB() {
        if (groupDAO.isTableEmpty()) return;
        List<GroupDatabaseModel> groupDatabaseModelList;
        groupDatabaseModelList = groupDAO.getAllGroups();
        for (GroupDatabaseModel groupDatabaseModel : groupDatabaseModelList) {
            GroupView groupView = new GroupView(Color.BLACK, GroupsFrame.GROUP_PANEL_BACKGROUND_COLOR,
                    GroupsFrame.TEXT_BOXES_BACKGROUND_COLOR, "№ группы", "Курс", "ФИО старосты");
            groupView.setTextFieldsLeftToRightStrings(groupDatabaseModel.getGroupNumber(), String.valueOf(groupDatabaseModel.getCourseNumber()), groupDatabaseModel.getHeadmanFullName());
            groupView.setTextFieldsValid();
            groupView.setPanelNonEditableCustom(GroupsFrame.GROUP_PANEL_BACKGROUND_COLOR, GroupsFrame.GROUP_NON_EDITABLE_FOREGROUND, GroupsFrame.FONT_NON_EDITABLE);
            groupView.setPreferredSize(GroupsFrame.STANDARD_ELEMENT_PREFFERED_SIZE);
            addComponent(groupView, false, false);
            JPanel utilitiesPanel = getGroupsUtilitiesPanel(groupView);
            utilitiesPanel.setPreferredSize(GroupsFrame.STANDARD_ELEMENT_PREFFERED_SIZE);
            addComponent(utilitiesPanel, false, false);
            groupViews.addGroupPanel(groupView);
        }
    }

    /**
     * Метод для получения панели макета групп.
     *
     * @return Панель макета групп.
     */
    public JPanel getPnlLayoutGroups() {
        return pnlLayoutGroups;
    }

    /**
     * Метод для получения панели с тремя действиями.
     *
     * @return Панель с тремя действиями.
     */
    public ThreeActionLabelsPanel getThreeActionLabelsPanel() {
        return threeActionLabelsPanel;
    }
}
