package at.alexnavratil.networkingtest;

/**
 * Created by alexnavratil on 30.06.14.
 */
public class Item {
    private int id, num_files;
    private boolean checked;
    private String text;
    private boolean has_comment, has_reminder;

    //Constructor
    public Item(int id, int num_files, boolean checked, String text, boolean has_comment, boolean has_reminder) {
        this.id = id;
        this.num_files = num_files;
        this.checked = checked;
        this.text = text;
        this.has_comment = has_comment;
        this.has_reminder = has_reminder;
    }

    //Getter

    public int getId() {
        return id;
    }

    public int getNum_files() {
        return num_files;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getText() {
        return text;
    }

    public boolean isHas_comment() {
        return has_comment;
    }

    public boolean isHas_reminder() {
        return has_reminder;
    }

    //Setter

    public void setNum_files(int num_files) {
        this.num_files = num_files;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setHas_comment(boolean has_comment) {
        this.has_comment = has_comment;
    }

    public void setHas_reminder(boolean has_reminder) {
        this.has_reminder = has_reminder;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", num_files=" + num_files +
                ", checked=" + checked +
                ", text='" + text + '\'' +
                ", has_comment=" + has_comment +
                ", has_reminder=" + has_reminder +
                '}';
    }
}
