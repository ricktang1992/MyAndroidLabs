package algonquin.cst2335.tang0243;

import static androidx.appcompat.app.AppCompatDelegate.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.tang0243.data.ChatRoomViewModel;
import algonquin.cst2335.tang0243.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.tang0243.databinding.ReceiveMessageBinding;
import algonquin.cst2335.tang0243.databinding.SentMessageBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatRoom extends AppCompatActivity {
    ArrayList<ChatMessage> messages = null;
    ChatRoomViewModel chatModel ; //In the beginning, there is no message.
    ActivityChatRoomBinding binding;//
    private  RecyclerView.Adapter myAdapter;
    ChatMessageDAO mDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();


        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            chatModel.messages.setValue(messages = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database

                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });
        }


        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            ChatMessage thisMessage=new ChatMessage(binding.textInput.getText().toString(),currentDateAndTime,true);
            messages.add(thisMessage);
            myAdapter.notifyDataSetChanged();
            binding.textInput.setText("");
            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute(( ) -> {
                //this is on a background thread
                thisMessage.id = (int)mDAO.insertMessage(thisMessage); //get the ID from the database
                Log.d("TAG", "The id created is:" + thisMessage.id);
            }); //the body of run()
        });
        binding.receiveButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());
            ChatMessage thisMessage=new ChatMessage(binding.textInput.getText().toString(),currentDateAndTime,false);
            messages.add(thisMessage);
            myAdapter.notifyDataSetChanged();
            binding.textInput.setText("");
            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute(( ) -> {
                //this is on a background thread
                thisMessage.id = (int)mDAO.insertMessage(thisMessage); //get the ID from the database
                Log.d("TAG", "The id created is:" + thisMessage.id);
            }); //the body of run()
        });
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if(viewType==0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                }else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                }


            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                String obj = messages.get(position).getMessage();
                String time = messages.get(position).getTimeSent();
                holder.messageText.setText(obj);
                holder.timeText.setText(time);
            }
            public int getItemViewType(int position){
                boolean isSend = messages.get(position).isSentButton();
                if(isSend){
                    return 0;
                }else{
                    return 1;
                }
            }
            @Override
            public int getItemCount() {
                return messages.size();
            }
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk -> {
                        int position = getAbsoluteAdapterPosition();
                        MyRowHolder newRow = (MyRowHolder) myAdapter.onCreateViewHolder(null, myAdapter.getItemViewType(position));

                        AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                        builder.setMessage("Do you want to delete the message:"
                                        + messageText.getText()).setTitle("Question: ")
                                .setNegativeButton("No", (dialog, cl) -> {
                                })
                                .setPositiveButton("Yes", (dialog, cl) -> {
                                    ChatMessage removedMessage = messages.get(position);
                                    messages.remove(position);
                                    myAdapter.notifyDataSetChanged();
                                    Executor thread1 = Executors.newSingleThreadExecutor();
                                    thread1.execute(( ) -> {
                                        //this is on a background thread
                                        mDAO.deleteMessage(removedMessage); //get the ID from the database
                                        Log.d("TAG", "The id removed is:" + removedMessage.id);
                                    }); //the body of run()
                                    Snackbar.make(messageText,"You deleted message #"
                                            + position,Snackbar.LENGTH_LONG)
                                            .setAction("Undo", click -> {
                                                messages.add(position,removedMessage);
                                                myAdapter.notifyDataSetChanged();
                                                Executor thread2 = Executors.newSingleThreadExecutor();
                                                thread2.execute(( ) -> {
                                                    //this is on a background thread
                                                    removedMessage.id = (int)mDAO.insertMessage(removedMessage); //get the ID from the database
                                                    Log.d("TAG", "The id created is:" + removedMessage.id);
                                                }); //the body of run()
                                            }).show();
                                }).create().show();
            });
            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);

        }
    }
}