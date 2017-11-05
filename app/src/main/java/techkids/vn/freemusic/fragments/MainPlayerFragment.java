package techkids.vn.freemusic.fragments;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import techkids.vn.freemusic.R;
import techkids.vn.freemusic.databases.MusicTypeModel;
import techkids.vn.freemusic.databases.TopSongModel;
import techkids.vn.freemusic.events.OnTopSongEvent;
import techkids.vn.freemusic.utils.DownloadHandler;
import techkids.vn.freemusic.utils.MusicHandle;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPlayerFragment extends Fragment {
    @BindView(R.id.iv_close)
    ImageView ivBack;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    @BindView(R.id.tv_song_name)
    TextView tvSongName;
    @BindView(R.id.tv_singer_name)
    TextView tvSingerName;
    @BindView(R.id.iv_song)
    ImageView ivSong;
    @BindView(R.id.tv_current_time_song)
    TextView tvCurrentTime;
    @BindView(R.id.tv_duration_time_song)
    TextView tvDurationTime;
    @BindView(R.id.iv_pre)
    ImageView ivPre;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.fb_play)
    FloatingActionButton fbPlay;
    @BindView(R.id.sb_main)
    SeekBar sb;

    public MainPlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_player, container,
                false);

        setupUI(view);
        EventBus.getDefault().register(this);

        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);

        fbPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicHandle.playPause();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    @Subscribe(sticky = true)
    public void onReceiveTopSong(OnTopSongEvent onTopSongEvent) {
        final TopSongModel topSongModel = onTopSongEvent.getTopSongModel();

        tvSingerName.setText(topSongModel.getArtist());
        tvSongName.setText(topSongModel.getSong());


        Log.d("abcd", topSongModel.getLargeImage());
        if (topSongModel.getLargeImage().equals(DownloadFragment.OFFLINE_MODE)) {
            Picasso.with(getContext()).load(R.drawable.offline_music)
                    .transform(new CropCircleTransformation())
                    .into(ivSong);
        } else {
            Picasso.with(getContext()).load(topSongModel.getLargeImage())
                    .transform(new CropCircleTransformation())
                    .into(ivSong);
        }

        //check xem bài hát đã đc download chưa = cách lấy list đã down từ trong máy ra,
        //so vs từng bài trong list theo tên bài hát + tên ca sĩ,
        //trùng thì tức là down r
        for (TopSongModel downloadedSong : DownloadHandler.getDownloadedSongs(getContext())) {
            if ((topSongModel.getSong() + topSongModel.getArtist()).equals(
                    downloadedSong.getSong() + downloadedSong.getArtist())) {
                topSongModel.setDownloaded(true);
            }
        }

        //nếu download r thì giảm alpha để icon mờ hơn
        if (topSongModel.isDownloaded()) {
            ivDownload.setAlpha(100);
        } else {
            ivDownload.setAlpha(255);
        }

        //click button để download
        ivDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topSongModel.isDownloaded()) {
                    Toast.makeText(getContext(), "This song has already downloaded!", Toast.LENGTH_SHORT).show();
                } else {
                    DownloadHandler.downloadSong(getContext(), topSongModel, ivDownload);
                }
            }
        });

        MusicHandle.updateRealtime(sb, fbPlay, ivSong, tvCurrentTime, tvDurationTime);
    }

}
