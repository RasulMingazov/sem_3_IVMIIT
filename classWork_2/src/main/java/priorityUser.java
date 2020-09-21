public class priorityUser extends User {
    private long bankId;

    public priorityUser(long id, String userName, long number, long bankId) {
        super(id, userName, number);
        this.bankId = bankId;
    }

}
