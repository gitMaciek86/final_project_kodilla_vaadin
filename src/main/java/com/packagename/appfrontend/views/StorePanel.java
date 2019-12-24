package com.packagename.appfrontend.views;

import com.packagename.appfrontend.MessageBean;
import com.packagename.appfrontend.domain.User;
import com.packagename.appfrontend.client.PoemBackend;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Route("store")
@Component
public class StorePanel extends HorizontalLayout {

    private final MessageBean bean;
    private final PoemBackend poemBackend;
    private final User user;

    public StorePanel(@Autowired MessageBean bean,
                      @Autowired PoemBackend poemBackend,
                      @Autowired User user) {
        this.bean = bean;
        this.poemBackend = poemBackend;
        this.user = user;

        LeftTable leftTable = new LeftTable(poemBackend, user);
        leftTable.setWidth("30%");
        add(leftTable);
        add(new RightTable(poemBackend, user));
    }
}
