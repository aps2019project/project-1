package control;

import model.cards.Card;
import model.other.Account;
import model.variables.CardsArray;
import view.CollectionScreen;

class CollectionHandler extends Handler{

    @Override
    void handleCommands() {
        while (scanner.hasNext()) {
            command = scanner.nextLine().trim().toLowerCase();
            if (command.matches("exit")) {
                new MenuHandler();
            } else if (command.matches("search \\w+")) {
                searchCard();
            }
        }
    }

    private void searchCard() {
        String name = command.split(" ")[1];
        CardsArray collection = Account.getCurrentAccount().getCollection();
        Card card = collection.findByName(name);
        if (card == null) {
            CollectionScreen.showCardNotFound();
        } else {
            CollectionScreen.showFoundCard(card);
        }
    }


}
