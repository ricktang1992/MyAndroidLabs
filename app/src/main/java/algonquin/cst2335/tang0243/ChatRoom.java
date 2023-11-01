package algonquin.cst2335.tang0243;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import algonquin.cst2335.tang0243.data.ChatMessage;
import algonquin.cst2335.tang0243.data.ChatRoomViewModel;
import algonquin.cst2335.tang0243.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.tang0243.databinding.ReceiveMessageBinding;
import algonquin.cst2335.tang0243.databinding.SentMessageBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatRoom extends AppCompatActivity {
    ArrayList<ChatMessage> messages = null;
    ChatRoomViewModel chatModel ; //In the beginning, there is no message.
    ActivityChatRoomBinding binding;//
    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<ChatMessage>());
        }
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            messages.add(new ChatMessage(binding.textInput.getText().toString(),currentDateandTime,true));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });
        binding.receiveButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            messages.add(new ChatMessage(binding.textInput.getText().toString(),currentDateandTime,false));
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
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
            messageText=itemView.findViewById(R.id.messageText);
            timeText=itemView.findViewById(R.id.timeText);
        }
    }
}