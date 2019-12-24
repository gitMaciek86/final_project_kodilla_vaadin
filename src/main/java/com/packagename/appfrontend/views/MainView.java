package com.packagename.appfrontend.views;

import com.packagename.appfrontend.MessageBean;
import com.packagename.appfrontend.domain.User;
import com.packagename.appfrontend.domain.UserDto;
import com.packagename.appfrontend.client.PoemBackend;
import com.packagename.appfrontend.client.UserBackend;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends HorizontalLayout {

    private final MessageBean bean;
    private final PoemBackend poemBackend;
    private final UserBackend userBackend;
    private final User user;

    public MainView(@Autowired MessageBean bean,
                    @Autowired PoemBackend poemBackend,
                    @Autowired UserBackend userBackend,
                    @Autowired User user) {
        this.bean = bean;
        this.poemBackend = poemBackend;
        this.userBackend = userBackend;
        this.user = user;
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout verticalLayout = new VerticalLayout();
        TextField loginField = new TextField();
        PasswordField passwordField = new PasswordField();
        verticalLayout.add(loginField);
        verticalLayout.add(passwordField);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button loginbutton = new Button("Login");
        loginbutton.addClickListener(buttonClickEvent -> {
            UserDto userDto = userBackend.login(loginField.getValue(), passwordField.getValue());
            if(userDto.isValid()){
                user.setId(userDto.getId().toString());
                user.setLogin(userDto.getLogin());
                UI.getCurrent().navigate(StorePanel.class);
            }else{
                Dialog dialog = new Dialog();
                dialog.add(new Label("No User with given credentials."));
                dialog.open();
            }
        });

        Button createbutton = new Button("Create");
        createbutton.addClickListener(buttonClickEvent -> {
            Dialog dialog = new Dialog();
            UserRegistrationPanel userRegistrationPanel = new UserRegistrationPanel(dialog, userBackend);
            dialog.add(userRegistrationPanel);
            dialog.open();
        });

        horizontalLayout.add(loginbutton);
        horizontalLayout.add(createbutton);

        verticalLayout.setSizeUndefined();
        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        verticalLayout.add(horizontalLayout);
        add(verticalLayout);

        this.setSizeFull();
    }
}
