package techkids.vn.freemusic.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import techkids.vn.freemusic.R;
import techkids.vn.freemusic.databases.MusicTypeModel;

/**
 * Created by Admins on 10/15/2017.
 */

public class MusicTypeAdapter extends RecyclerView.Adapter<MusicTypeAdapter.MusicTypeViewHolder> {
    private List<MusicTypeModel> musicTypeModels;

    public MusicTypeAdapter(List<MusicTypeModel> musicTypeModels) {
        this.musicTypeModels = musicTypeModels;
    }

    // create item view if needed
    @Override
    public MusicTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_music_type, parent, false);

        return new MusicTypeViewHolder(itemView);
    }

    // load data
    @Override
    public void onBindViewHolder(MusicTypeViewHolder holder, int position) {
        holder.setData(musicTypeModels.get(position));
    }

    // 1
    @Override
    public int getItemCount() {
        return musicTypeModels.size();
    }

    class MusicTypeViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMusicType;
        TextView tvMusicType;

        public MusicTypeViewHolder(View itemView) {
            super(itemView);

            ivMusicType = itemView.findViewById(R.id.iv_music_type);
            tvMusicType = itemView.findViewById(R.id.tv_music_type);
        }

        public void setData(MusicTypeModel musicTypeModel) {
            ivMusicType.setImageResource(musicTypeModel.getImageID());
            tvMusicType.setText(musicTypeModel.getKey());
        }
    }
}
