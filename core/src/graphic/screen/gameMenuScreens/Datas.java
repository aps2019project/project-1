package graphic.screen.gameMenuScreens;

import graphic.screen.MenuScreen;
import graphic.screen.ScreenManager;
import model.cards.Card;
import model.cards.Hero;
import model.game.*;
import model.other.Account;
import view.BattleScreen;

import java.util.ArrayList;


public class Datas {
    private static Datas datas = new Datas();
    private Account account;
    private Account secondAccount;
    private GameType type;
    private IntelligentPlayer firstLevelPlayer;
    private IntelligentPlayer secondLevelPlayer;
    private IntelligentPlayer thirdLevelPlayer;
    private IntelligentPlayer customPlayer;
    private ArrayList<Deck> customDecks = new ArrayList<Deck>();
    private Hero hero;
    private Game lastGame = null;
    private Datas() {
    }

    public boolean isLastGameNull() {
        return lastGame == null;
    }
    public void setLastGame(Game lastGame) {
        this.lastGame = lastGame;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }


    public static Datas getDatas() {
        return datas;
    }

    public void setAccount() {
        this.account = Account.getCurrentAccount();
    }

    public ArrayList<Deck> getCustomDecks() {
        return customDecks;
    }

    public void setPlayersSteps() {
        //customPlayer = new IntelligentPlayer(new Account("customPlayer","1234"));
        Account account = Account.findAccount("firstLevelPlayer");
        if(account == null)
            account = new Account("firstLevelPlayer","1234");
        try {
            Card.makeStroyDeck(1, account);
            customDecks.add(account.getAllDecks().get(0));
        } catch (Exception e){}
        firstLevelPlayer = new IntelligentPlayer(account);
        account = Account.findAccount("secondLevelPlayer");
        if(account == null)
            account = new Account("secondLevelPlayer","1234");

        try {
            Card.makeStroyDeck(2, account);
            customDecks.add(account.getAllDecks().get(0));
        } catch (Exception e){}
        secondLevelPlayer = new IntelligentPlayer(account);
        account = Account.findAccount("thirdLevelPlayer");
        if(account == null)
            account = new Account("thirdLevelPlayer","1234");
        try {
            Card.makeStroyDeck(3, account);
            customDecks.add(account.getAllDecks().get(0));
        } catch (Exception e){}
        thirdLevelPlayer = new IntelligentPlayer(account);
    }

    public void setCustomDecks() {
        if(hero == null) return;
        for(Deck deck : customDecks) {
            deck.deleteCard(deck.getHero());
            deck.addCard(hero);
        }
    }
    public void makeFirstStory() {
        type = GameType.KILL_HERO;
        new Thread(new Runnable() {
            @Override
            public void run() {
                playGame(0,firstLevelPlayer);
            }
        }).start();
    }

    public void makeSecondStory() {
        type = GameType.CAPTURE_THE_FLAG;
        new Thread(new Runnable() {
            @Override
            public void run() {
                playGame(1,secondLevelPlayer);
            }
        }).start();

    }

    public void makeThirdStory() {
        type = GameType.ROLLUP_FLAGS;
        new Thread(new Runnable() {
            @Override
            public void run() {
                playGame(3,thirdLevelPlayer);
            }
        }).start();

    }


    private void playGame(int numberOfFlags, float[] myArray, float[]enemyArray) {
        if(secondAccount.getMainDeck() == null || !secondAccount.getMainDeck().checkIfValid()) {
            BattleScreen.showInvalidDeckInMultiPlayer();
            return;
        }
        Game game = new Game(account,secondAccount,type,numberOfFlags);
        game.getFirstPlayer().getDeck().setArray(myArray);
        game.getSecondPlayer().getDeck().setArray(enemyArray);
        game.startMatch();
        if(game.isExitFromGame()) {
            System.out.println("you leave the game");
//            ScreenManager.setScreen(new MenuScreen());
        } else {
            MatchResult result = game.getResults();
//            System.out.println(account.getDaric());
//            System.out.println(secondAccount.getDaric());
            game.getWinner().increaseDaric(1000);
//            System.out.println(account.getDaric());
//            System.out.println(secondAccount.getDaric());
            System.out.println("this account win: " + game.getWinner().getUsername());
        }
    }

    public void createLastGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                lastGameStart();
            }
        }).start();
    }
    private void lastGameStart() {
        Game game = lastGame;
        game.backToGame();
        game.nextTurn();
        if(game.isExitFromGame()) {
            System.out.println("you leave the game");
            ScreenManager.setScreen(new MenuScreen());

        } else {
            if (game.getType() == GameType.KILL_HERO) {
                game.getWinner().increaseDaric(500);
            } else if (game.getType() == GameType.CAPTURE_THE_FLAG) {
                game.getWinner().increaseDaric(1000);
            } else if (game.getType() == GameType.ROLLUP_FLAGS) {
                game.getWinner().increaseDaric(1500);
            }
            MatchResult result = game.getResults();
        }
    }
    private void playGame(int numberOfFlags, IntelligentPlayer player) {
        Game game = new Game(account,player,type,numberOfFlags);
        game.startMatch();
        if(game.isExitFromGame()) {
            System.out.println("you leave the game");
//            ScreenManager.setScreen(new MenuScreen());

        } else {
            if (game.getType() == GameType.KILL_HERO) {
                game.getWinner().increaseDaric(500);
            } else if (game.getType() == GameType.CAPTURE_THE_FLAG) {
                game.getWinner().increaseDaric(1000);
            } else if (game.getType() == GameType.ROLLUP_FLAGS) {
                game.getWinner().increaseDaric(1500);
            }
            MatchResult result = game.getResults();
        }
    }
    public Deck setDeck(Deck deck) {
        Deck copyDeck = deck.copyAll();
        for(Card card : copyDeck.getCards().getAllCards()) {
            card.setUserName("customplayer");
        }
        return copyDeck;
    }

    public void makeKillHeroCustom(int deckNumber) {
        type = GameType.KILL_HERO;
        Account account;
        account = Account.findAccount("customplayer");
        if(account == null)
            account = new Account("customplayer","1234");
        account.setMainDeck(setDeck(customDecks.get(deckNumber)));
        customPlayer = new IntelligentPlayer(account);


        new Thread(new Runnable() {
            @Override
            public void run() {
                playGame(0,customPlayer);
            }
        }).start();
    }

    public void makeCaptureTheFlagCustom(int deckNumber) {
        type = GameType.CAPTURE_THE_FLAG;
        Account account;
        account = Account.findAccount("customplayer");
        if(account == null)
            account = new Account("customplayer","1234");
        account.setMainDeck(setDeck(customDecks.get(deckNumber)));
        customPlayer = new IntelligentPlayer(account);

        new Thread(new Runnable() {
            @Override
            public void run() {
                playGame(1,customPlayer);
            }
        }).start();
    }

    public void makeRollUpFlagCustom(int deckNumber, final int numberOfFlags) {
        type = GameType.ROLLUP_FLAGS;
        Account account;
        account = Account.findAccount("customplayer");
        if(account == null)
            account = new Account("customplayer","1234");
        account.setMainDeck(setDeck(customDecks.get(deckNumber)));
        customPlayer = new IntelligentPlayer(account);

        new Thread(new Runnable() {
            @Override
            public void run() {
                playGame(numberOfFlags,secondLevelPlayer);
            }
        }).start();

    }
    public void swapAccounts() {
        Account tmp = account;
        account = secondAccount;
        secondAccount = tmp;
    }
    public void makeKillHeroCustom(Account account,int number, final float[] myArray, final float[] enemyArray) {
        type = GameType.KILL_HERO;
        secondAccount = account;
        if(number == 2) swapAccounts();
        new Thread(new Runnable() {
            @Override
            public void run() {
                playGame(0, myArray, enemyArray);
            }
        }).start();
    }

    public void makeCaptureTheFlagCustom(Account account, int number, final float[] myArray, final float[] enemyArray) {
        type = GameType.CAPTURE_THE_FLAG;
        secondAccount = account;
        if(number == 2) swapAccounts();
        new Thread(new Runnable() {
            @Override
            public void run() {
                playGame(1, myArray, enemyArray);
            }
        }).start();
    }

    public void makeRollUpFlagCustom(Account account, final int numberOfFlags, int number, final float[] myArray, final float[] enemyArray) {
        type = GameType.ROLLUP_FLAGS;
        secondAccount = account;
        if(number == 2) swapAccounts();
        new Thread(new Runnable() {
            @Override
            public void run() {
                playGame(numberOfFlags, myArray, enemyArray);
            }
        }).start();
    }

}
