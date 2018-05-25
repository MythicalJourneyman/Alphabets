package com.mythicaljourneyman.alphabets.activities;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mythicaljourneyman.alphabets.R;
import com.mythicaljourneyman.alphabets.databinding.ActivityAlphabetListBinding;
import com.mythicaljourneyman.alphabets.databinding.LayoutAlphabetListItemBinding;

import java.util.Locale;

public class AlphabetListActivity extends AppCompatActivity {
    ActivityAlphabetListBinding mBinding;
    TextToSpeech mTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_alphabet_list);
        mBinding.list.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                mBinding.list.setAdapter(new ViewAdapter(getResources().getStringArray(R.array.alphabet)));
            }
        });
        mTextToSpeech.setLanguage(Locale.UK);

    }

    private void playClick() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mediaPlayer.start();
    }

    class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ItemViewHolder> {
        private String[] mList;

        public ViewAdapter(String[] list) {
            mList = list;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutAlphabetListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_alphabet_list_item, parent, false);
            return new ItemViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
            holder.mBinding.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (mTextToSpeech.isSpeaking()) {
//                        mTextToSpeech.stop();
//                    }
                    playClick();
                    mTextToSpeech.speak(mList[position], TextToSpeech.QUEUE_FLUSH, null, "iddididiididi");
                }
            });
            holder.mBinding.title.setText(mList[position]);
        }

        @Override
        public int getItemCount() {
            return mList.length;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            LayoutAlphabetListItemBinding mBinding;

            public ItemViewHolder(LayoutAlphabetListItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }
        }
    }

}
