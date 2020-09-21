public class User implements c{
    private long id;
    private String userName;
    private String language;
    private int countGames;
    private long number;
    private boolean makeNewGame;
    private boolean readyToJoinGame;

    public User(long id, String userName, long number) {
        this.id = id;
        this.userName = userName;
        this.number = number;
    }

    public boolean isReadyToJoinGame() {
        return readyToJoinGame;
    }

    public void setReadyToJoinGame(boolean readyToJoinGame) {
        this.readyToJoinGame = readyToJoinGame;
    }

    public boolean isMakeNewGame() {
        return makeNewGame;
    }

    public void setMakeNewGame(boolean makeNewGame) {
        this.makeNewGame = makeNewGame;
    }

    public long getId() {
        return id;
    }

    public void setId(long tgId) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getCountGames() {
        return countGames;
    }

    public void setCountGames(int countGames) {
        this.countGames = countGames;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + "\n"
                + "UserName: " + getUserName() + "\n";
    }
}
interface c { }