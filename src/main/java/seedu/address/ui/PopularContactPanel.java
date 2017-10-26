package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PopularContactPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel to display top 5 popular most frequently accessed contacts.
 */
public class PopularContactPanel extends UiPart<Region> {

    private static final String FXML = "PopularContactPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PopularContactCard> popularContactListView;

    public PopularContactPanel(ObservableList<ReadOnlyPerson> popularContactList) {
        super(FXML);
        setConnections(popularContactList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> popularContactList) {
        ObservableList<PopularContactCard> mappedList = EasyBind.map(
                popularContactList, (person) -> new PopularContactCard(person, popularContactList.indexOf(person) + 1));
        popularContactListView.setItems(mappedList);
        popularContactListView.setCellFactory(listView -> new PopularContactPanel.PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        popularContactListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PopularContactPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PopularContactCard}.
     */
    class PersonListViewCell extends ListCell<PopularContactCard> {

        @Override
        protected void updateItem(PopularContactCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }

}

