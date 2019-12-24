package com.packagename.appfrontend.views;

import com.packagename.appfrontend.domain.User;
import com.packagename.appfrontend.domain.PoemDto;
import com.packagename.appfrontend.client.PoemBackend;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class RightTable extends VerticalLayout {

    private Grid<PoemDto> grid;
    private PoemBackend poemBackend;
    private User user;
    private TextArea textArea;

    public RightTable(PoemBackend poemBackend, User user) {
        this.poemBackend = poemBackend;
        this.user = user;

        HorizontalLayout horizontalLayout = new HorizontalLayout();
            Button buttonSyncWithLibrary= new Button("Update my library");
            buttonSyncWithLibrary.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
                @Override
                public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                    getMyLibrary();
                }
            });

            Button buttonTranslate = new Button("Translate Poem");
            buttonTranslate.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
                @Override
                public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                    translatePoem();
                }
            });
            Button buttonDelete = new Button("Delete");
            buttonDelete.addClickListener(buttonClickEvent -> {
                removePoem();
            });

        horizontalLayout.add(buttonSyncWithLibrary);
        horizontalLayout.add(buttonTranslate);
        horizontalLayout.add(buttonDelete);
        add(horizontalLayout);
        add(addUserLibraryGrid());
    }

    private void removePoem() {
        Optional<PoemDto> libraryDtoOptional = grid.getSelectedItems().stream().findFirst();
        libraryDtoOptional.ifPresent(poemDto -> {
            poemBackend.removePoemForUser(poemDto.getId(), user.getId());
            getMyLibrary();
        });
    }

    private void translatePoem() {
        try {
            Optional<PoemDto> libraryDtoOptional = grid.getSelectedItems().stream().findFirst();
            libraryDtoOptional.ifPresent(poemDto -> {
                String text = poemBackend.translatePoem(poemDto.getLines());
                Dialog dialog = new Dialog();
                dialog.setWidth("400px");
                textArea = new TextArea();
                dialog.add(textArea);
                textArea.setValue(text);
                textArea.setSizeFull();
                dialog.open();
            });
        } catch (Exception e) {
            textArea.setValue("Something went wrong");
            e.printStackTrace();
        }
    }

    private Grid<PoemDto> addUserLibraryGrid() {

        grid = new Grid<>(PoemDto.class);
        return grid;
    }

    private void getMyLibrary() {
        List<PoemDto> titleStoreDtos = new ArrayList<>();
        PoemDto[] allPoemsFromLibrary = poemBackend.getAllPoemsForUser(user.getId());

        Arrays.asList(allPoemsFromLibrary).forEach(title -> titleStoreDtos.add(new PoemDto(title.getId(), title.getTitle(),
                title.getAuthor(), title.getLines(), title.getDateBought())));

        grid.setItems(titleStoreDtos);
    }
}
