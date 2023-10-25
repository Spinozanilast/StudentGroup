package Forms.controllers;

import Forms.views.StartFrame;
import Forms.models.GroupModel;
import CustomComponents.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Класс Start frame controller, представляющий часть стартовой формы, где пользователь так или иначе может
 * взаимодействовать с данными или изменять их представление.
 */
public class StartFrameController {
    private final ThreeActionLabelsPanel threeActionLabelsPanel;
    private final StartFrame formView;
    private final GroupModel groupModel;
    private final JPanel layoutGroupsPanel;
    private AddGroupPanel addGroupPanel;

    /**
     *  Создает новый контроллер начальной формы.
     *
     * @param groupModel модель, содержащая и обрабатывающая группы
     */
    public StartFrameController(GroupModel groupModel) {
        this.groupModel = groupModel;
        formView = new StartFrame();
        layoutGroupsPanel = formView.getLayoutGroupsPanel();
        threeActionLabelsPanel = formView.getThreeActionLabelsPanel();
        initializeListenersForView();
    }

    /**
     * Инициализирует слушателей для представления.
     */
    private void initializeListenersForView() {
        threeActionLabelsPanel.getLeftLabel().addMouseListener(getLeftLabelMouseListener());
        threeActionLabelsPanel.getCenterLabel().addMouseListener(getCenterLabelMouseListener());
        threeActionLabelsPanel.getRightLabel().addMouseListener(getRightLabelMouseListener());
        formView.addComponent(threeActionLabelsPanel, false, false);
        AddGroupPanel addGroupPanel = new AddGroupPanel(Color.BLACK, StartFrame.GROUP_PANEL_BACKGROUND_COLOR);
        this.addGroupPanel = addGroupPanel;
        formView.addComponentMouseListener(addGroupPanel, getAddGroupPanelMouseListener());
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
                if (groupModel.getGroupPanels().isEmpty()) {
                    return;
                }
                setStartLabelsForLayout();
                switch (labelType) {
                    case LEFT:
                        InputGroupPanel.sortGroupListByLeftText(groupModel.getGroupPanels(), !threeActionLabelsPanel.isLeftArrowDown());
                        threeActionLabelsPanel.setLeftLabelIcon(threeActionLabelsPanel.isLeftArrowDown() ?
                                ThreeActionLabelsPanel.DOWN_ARROW_ICON :
                                ThreeActionLabelsPanel.UP_ARROW_ICON);
                        break;
                    case CENTER:
                        InputGroupPanel.sortPanelListByCenterText(groupModel.getGroupPanels(), !threeActionLabelsPanel.isCenterArrowDown());
                        threeActionLabelsPanel.setCenterLabelIcon(threeActionLabelsPanel.isCenterArrowDown() ?
                                ThreeActionLabelsPanel.DOWN_ARROW_ICON :
                                ThreeActionLabelsPanel.UP_ARROW_ICON);
                        break;
                    case RIGHT:
                        InputGroupPanel.sortPanelListByRightText(groupModel.getGroupPanels(), !threeActionLabelsPanel.isRightArrowDown());
                        threeActionLabelsPanel.setRightLabelIcon(threeActionLabelsPanel.isRightArrowDown() ?
                                ThreeActionLabelsPanel.DOWN_ARROW_ICON :
                                ThreeActionLabelsPanel.UP_ARROW_ICON);
                        break;
                }

                addAllGroupPanels(false);
                layoutGroupsPanel.revalidate();
                layoutGroupsPanel.repaint();
            }
        };
    }

    /**
     * Устанавливает начальные метки для макета.
     */
    private void setStartLabelsForLayout() {
        layoutGroupsPanel.removeAll();
        layoutGroupsPanel.add(Box.createVerticalStrut(15));
        formView.addComponent(threeActionLabelsPanel, false, false);
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
        for (InputGroupPanel groupPanel : groupModel.getGroupPanels()) {
            formView.addComponent(groupPanel, true, true);
            JPanel utilitiesPanel = getGroupsUtilitiesPanel(groupPanel);
            formView.addComponent(utilitiesPanel, false, false);
            formView.addComponent(addGroupPanel, false, true);
        }
        layoutGroupsPanel.revalidate();
        layoutGroupsPanel.repaint();
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
                InputGroupPanel inputGroupPanel = new InputGroupPanel(Color.BLACK, StartFrame.GROUP_PANEL_BACKGROUND_COLOR, StartFrame.TEXT_BOXES_BACKGROUND_COLOR, "№ Группы", "Курс", "ФИО старосты");
                formView.addComponent(inputGroupPanel, false, false);
                addGroupPanel.repaint();
                layoutGroupsPanel.revalidate();
                HighResolutionImagePanel highResolutionImagePanel = new HighResolutionImagePanel(new HighResolutionImageLabel("Icons/Tick-Circle.png", 35, 35), 640, 45);
                formView.addComponent(highResolutionImagePanel, false, true);
                formView.safelyDeleteComponent(addGroupPanel);
                highResolutionImagePanel.setFocusable(true);
                highResolutionImagePanel.addMouseListener(getHighResolutionImagePanelMouseListener(inputGroupPanel, highResolutionImagePanel, false));
                highResolutionImagePanel.addKeyListener(getHighResolutionImagePanelKeyListener(inputGroupPanel, highResolutionImagePanel, false));
            }
        };
    }

    /**
     * Возвращает слушателя событий мыши для панели с изображением высокого разрешения.
     *
     * @param inputGroupPanel панель ввода группы
     * @param highResolutionImagePanel панель с изображением высокого разрешения
     * @param isEditMode флаг указывающий на режим редактирования
     * @return слушатель событий мыши
     */
    private MouseAdapter getHighResolutionImagePanelMouseListener(InputGroupPanel inputGroupPanel, HighResolutionImagePanel highResolutionImagePanel, boolean isEditMode) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getResultGroupByInputValidity(inputGroupPanel, highResolutionImagePanel, isEditMode);
            }
        };
    }

    /**
     * Возвращает слушателя событий клавиатуры для панели с изображением высокого разрешения.
     *
     * @param inputGroupPanel панель ввода группы
     * @param highResolutionImagePanel панель с изображением высокого разрешения
     * @param isEditMode флаг указывающий на режим редактирования
     * @return слушатель событий клавиатуры
     */
    private KeyAdapter getHighResolutionImagePanelKeyListener(InputGroupPanel inputGroupPanel, HighResolutionImagePanel highResolutionImagePanel, boolean isEditMode) {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    getResultGroupByInputValidity(inputGroupPanel, highResolutionImagePanel, isEditMode);
                }
            }
        };
    }

    /**
     * Устанавливает состояние обложки группы в необходимое на основе валидности ввода.
     *
     * @param inputGroupPanel панель ввода группы
     * @param highResolutionImagePanel панель с изображением высокого разрешения
     * @param isEditMode флаг указывающий на режим редактирования
     */
    private void getResultGroupByInputValidity(InputGroupPanel inputGroupPanel, HighResolutionImagePanel highResolutionImagePanel, boolean isEditMode) {
        if (!inputGroupPanel.isInputValid()){
            inputGroupPanel.setTextFieldsUnValid();
            return;
        }
        else {
            inputGroupPanel.setTextFieldsValid();
        }
        inputGroupPanel.setPanelNonEditableCustom(StartFrame.GROUP_PANEL_BACKGROUND_COLOR, StartFrame.GROUP_NON_EDITABLE_FOREGROUND, StartFrame.FONT_NON_EDITABLE);
        if (!isEditMode) {
            JPanel utilitiesPanel = getGroupsUtilitiesPanel(inputGroupPanel);
            formView.safelyDeleteComponent(highResolutionImagePanel);
            formView.addComponent(utilitiesPanel, false, false);
            formView.addComponent(this.addGroupPanel, false, true);
            groupModel.addGroupPanel(inputGroupPanel);
        }
        else {
            formView.safelyDeleteComponent(highResolutionImagePanel);
            addAllGroupPanels(true);
        }
    }

    /**
     * Возвращает панель утилит группы, также добавляет слушателей событый мыши для иконок,
     * с которыми можно взаимодействовать.
     *
     * @param inputGroupPanel панель ввода группы
     * @return панель утилит группы
     */
    private JPanel getGroupsUtilitiesPanel(InputGroupPanel inputGroupPanel) {
        JPanel groupPanel = new JPanel();
        groupPanel.setOpaque(false);
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.X_AXIS));

        HighResolutionImagePanel openGroupFormPanel = new HighResolutionImagePanel(new HighResolutionImageLabel("Icons/Group-Form-Open-Icon.png", 25,24), 30, 45);
        openGroupFormPanel.setToolTipText("Открыть основное окно для просмотра содержания группы");

        HighResolutionImagePanel editGroupData = new HighResolutionImagePanel(new HighResolutionImageLabel("Icons/Edit-Group-Icon.png", 25,27), 30, 45);
        editGroupData.setToolTipText("Редактировать начальные данные о группе сверху");
//        openGroupFormPanel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                GroupForm groupForm = new GroupForm(inputGroupPanel.getTextFieldValues()[0],
//                        inputGroupPanel.getTextFieldValues()[1],
//                        inputGroupPanel.getTextFieldValues()[2]);
//                groupForm.setVisible(true);
//            }
//        });
        editGroupData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int editablePanelIndex = groupModel.getGroupPanels().indexOf(inputGroupPanel);
                setStartLabelsForLayout();
                for (int i = 0; i < groupModel.getGroupPanels().size(); i++){
                    if (i == editablePanelIndex){
                        formView.addComponent(groupModel.getGroupPanels().get(i),false, true);
                        groupModel.getGroupPanels().get(i).setPanelEditableStandardValues();
                        HighResolutionImagePanel highResolutionImagePanel = new HighResolutionImagePanel(new HighResolutionImageLabel("Icons/Tick-Circle.png", 35, 35), 640, 45);
                        formView.addComponent(highResolutionImagePanel, false, true);
                        formView.safelyDeleteComponent(addGroupPanel);
                        highResolutionImagePanel.setFocusable(true);
                        highResolutionImagePanel.addMouseListener(getHighResolutionImagePanelMouseListener(groupModel.getGroupPanels().get(i), highResolutionImagePanel, true));
                        continue;
                    }
                    formView.addComponent(groupModel.getGroupPanels().get(i), true, true);
                    JPanel utilitiesPanel = getGroupsUtilitiesPanel(groupModel.getGroupPanels().get(i));
                    formView.addComponent(utilitiesPanel, false, false);
                }
                formView.addComponent(addGroupPanel, false, true);
            }
        });
        groupPanel.add(openGroupFormPanel);
        groupPanel.add(Box.createHorizontalStrut(100));
        groupPanel.add(editGroupData);
        groupPanel.setMaximumSize(new Dimension(150, 45));
        return groupPanel;
    }

    /**
     * Сделать компонент view видимым.
     */
    public void showStartForm() {
        formView.setVisible(true);
    }
}
