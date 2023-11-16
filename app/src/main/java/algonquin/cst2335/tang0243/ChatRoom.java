package algonquin.cst2335.tang0243;

import static androidx.appcompat.app.AppCompatDelegate.create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    // ArrayList to store chat messages
    ArrayList<ChatMessage> messages = null;

    // ViewModel for managing data related to the chat room
    ChatRoomViewModel chatModel;

    // View binding for the activity
    ActivityChatRoomBinding binding;

    // RecyclerView adapter for displaying chat messages
    private RecyclerView.Adapter myAdapter;

    // Data Access Object for interacting with the message database
    ChatMessageDAO mDAO;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflating the layout using view binding
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initializing the ViewModel for the chat room
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        // Creating or retrieving the message database
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();

        // Retrieving messages from ViewModel
        messages = chatModel.messages.getValue();

        // If no messages are present, fetch them from the database
        if (messages == null) {
            chatModel.messages.setValue(messages = new ArrayList<>());

            // Execute database query on a background thread
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                messages.addAll(mDAO.getAllMessages());

                // Load the RecyclerView on the UI thread
                runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter));
            });
        }
        setSupportActionBar( binding.myToolbar);

        //send Button click funtion to put message into database.
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

        //receive Button click funtion to put message into database.
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
        chatModel.selectedMessage.observe(this, (newMessageValue) -> {
            MessageDetailsFragment chatFragment = new MessageDetailsFragment( newMessageValue );
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            tx.addToBackStack("anything?");
            tx.replace(R.id.fragmentLocation,chatFragment);
            tx.commit();
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
                ChatMessage selected = messages.get(position);

                chatModel.selectedMessage.postValue(selected);
                /*     int position = getAbsoluteAdapterPosition();
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
                                }).create().show();*/
            });
            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);

        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch( item.getItemId() )
        {
            case R.id.item_1:
                     /*int position = getAbsoluteAdapterPosition();
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
                                }).create().show();*/
                //put your ChatMessage deletion code here. If you select this item, you should show the alert dialog
                //asking if the user wants to delete this message.
                break;
            case R.id.item_2:
                CharSequence text = "Version 1.0, created by Ziyao Tang";
                Toast.makeText(this,text, Toast.LENGTH_SHORT).show();
                //put your ChatMessage deletion code here. If you select this item, you should show the alert dialog
                //asking if the user wants to delete this message.
                break;
        }

        return true;
    }
}