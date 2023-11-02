package algonquin.cst2335.tang0243;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name="Message")
    protected String message;
    @ColumnInfo(name="TimeSent")
    protected String timeSent;
    @ColumnInfo(name="SendOrReceive")
    protected boolean isSentButton;

    public ChatMessage(String message, String timeSent, boolean isSentButton)
    {
        this.message = message;
        this.timeSent = timeSent;
        this.isSentButton = isSentButton;
    }

    public String getMessage() {
        return message;
    }
    public String getTimeSent(){
        return timeSent;
    }
    public boolean isSentButton(){
        return isSentButton;
    }
}
