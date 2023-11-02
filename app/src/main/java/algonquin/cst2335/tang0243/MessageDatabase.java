package algonquin.cst2335.tang0243;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import algonquin.cst2335.tang0243.ChatMessageDAO;
import algonquin.cst2335.tang0243.ChatMessage;

@Database(entities = {ChatMessage.class}, version=1)
public abstract class MessageDatabase  extends RoomDatabase {
    public abstract ChatMessageDAO cmDAO();
}