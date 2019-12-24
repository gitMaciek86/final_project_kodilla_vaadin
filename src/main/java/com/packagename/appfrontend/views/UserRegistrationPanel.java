package com.packagename.appfrontend.views;

import com.packagename.appfrontend.domain.UserDto;
import com.packagename.appfrontend.client.UserBackend;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;


public class UserRegistrationPanel extends VerticalLayout {

    private Dialog dialog;
    private UserBackend userBackend;
    private boolean userCreatedCorrectly;

    public UserRegistrationPanel(Dialog dialog, UserBackend userBackend) {
        this.dialog = dialog;
        this.userBackend = userBackend;
        addForm();
    }

    private void addForm() {
        TextField userNameTextField = new TextField("User Name");
        TextField userSurnameTextField = new TextField("User Surname");
        TextField mailTextField = new TextField("Mail");
        TextField loginTextField = new TextField("Login");
        TextField passwordTextField = new TextField("Password");
        add(userNameTextField);
        add(userSurnameTextField);
        add(mailTextField);
        add(loginTextField);
        add(passwordTextField);

        Button registerButton = new Button("Register");
        registerButton.addClickListener(buttonClickEvent -> {
            UserDto userDto = new UserDto();
            userDto.setLogin(loginTextField.getValue());
            userDto.setPassword(passwordTextField.getValue());
            userDto.setMail(mailTextField.getValue());
            userDto.setSurname(userSurnameTextField.getValue());
            userDto.setSurname(userSurnameTextField.getValue());
            String user = userBackend.createUser(userDto);

            if(user != null || user.equals("")){
                userCreatedCorrectly = true;
            }
            dialog.close();
            Dialog dialogUserCreated = new Dialog();
            dialogUserCreated.add(new Label("User Created correctly. Please login."));
            dialogUserCreated.open();
        });
        add(registerButton);
    }

    public boolean isUserCreatedCorrectly() {
        return userCreatedCorrectly;
    }
}
