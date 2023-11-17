package algonquin.cst2335.tang0243;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.tang0243.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {
    ChatMessage selected;
    public MessageDetailsFragment(ChatMessage m){
        selected=m;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);
        binding.messageDetail.setText(selected.message);
        binding.timeDetail.setText(selected.timeSent);
        binding.databaseDetail.setText("Id = "+selected.id);
        if(selected.isSentButton()){
            binding.sendDetail.setText("Sender");
        }else{
            binding.sendDetail.setText("Receiver");
        }

        return binding.getRoot();
    }
}
