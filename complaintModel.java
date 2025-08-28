package foodhub;

public class complaintModel {
    private final String complainID;
    private final String username;
    private final String category;
    private final String comment;
    private String answer;

    public complaintModel(String complainID, String username, String category, String comment, String answer) {
        this.complainID = complainID;
        this.username = username;
        this.category = category;
        this.comment = comment;
        this.answer = answer;
    }

    public String getComplainID() {
        return complainID;
    }

    public String getUsername() {
        return username;
    }

    public String getCategory() {
        return category;
    }

    public String getComment() {
        return comment;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

