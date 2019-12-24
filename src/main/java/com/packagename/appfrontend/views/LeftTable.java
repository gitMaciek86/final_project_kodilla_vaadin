package com.packagename.appfrontend.views;

import com.packagename.appfrontend.domain.User;
import com.packagename.appfrontend.domain.TitleStoreDto;
import com.packagename.appfrontend.client.PoemBackend;
import com.packagename.appfrontend.domain.TitlesFromStore;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LeftTable extends VerticalLayout {

    private PoemBackend poemBackend;
    private Grid<TitleStoreDto> grid;
    private User user;

    public LeftTable(@Autowired PoemBackend poemBackend, User user) {
        this.poemBackend = poemBackend;
        this.user = user;
        HorizontalLayout horizontalLayout = new HorizontalLayout();
            Button buttonSync = new Button("Update Poem Store");
            buttonSync.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
                @Override
                public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                    addToTable();
                }
            });
            Button buttonBuy = new Button("Buy from Store");
            buttonBuy.addClickListener(buttonClickEvent -> {
                Optional<TitleStoreDto> titleStoreDtoOptional = grid.getSelectedItems().stream().findFirst();
                titleStoreDtoOptional.ifPresent(titleStoreDto -> {
                    poemBackend.buyPoem(titleStoreDto.getPoemTitle(), user.getId());
                });
            });
        horizontalLayout.add(buttonSync);
        horizontalLayout.add(buttonBuy);

        add(horizontalLayout);
        add(addTitlesGrid());
    }

    private Grid<TitleStoreDto> addTitlesGrid() {
        grid = new Grid<>(TitleStoreDto.class);
        return grid;
    }

    private void addToTable() {
        List<TitleStoreDto> titleStoreDtos = new ArrayList<>();
        TitlesFromStore allPoems = poemBackend.getAllPoemsFromStore();
        allPoems.getTitles().forEach(title -> titleStoreDtos.add(new TitleStoreDto(title)));
        grid.setItems(titleStoreDtos);
    }
}
