package techkids.vn.freemusic.fragments;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.vn.freemusic.R;
import techkids.vn.freemusic.databases.MusicTypeModel;
import techkids.vn.freemusic.events.OnClickMusicTypeEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopSongFragment extends Fragment {
    private static final String TAG = "TopSongFragment";
    @BindView(R.id.toolbar)
    Toolbar tbTopSongs;
    @BindView(R.id.iv_favourite)
    ImageView ivFavourite;
    @BindView(R.id.tv_music_type)
    TextView tvMusicType;
    @BindView(R.id.iv_top_song)
    ImageView ivMusicType;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    private MusicTypeModel musicTypeModel;

    public TopSongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_song, container, false);
        ButterKnife.bind(this, view);

        EventBus.getDefault().register(this);
        setupUI();

        return view;
    }

    @Subscribe(sticky = true)
    public void onEventMusicTypeClicked(OnClickMusicTypeEvent onClickMusicTypeEvent) {
        musicTypeModel = onClickMusicTypeEvent.getMusicTypeModel();
    }

    private void setupUI() {
        tbTopSongs.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        tbTopSongs.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        tvMusicType.setText(musicTypeModel.getKey());
        Picasso.with(getContext()).load(musicTypeModel.getImageID()).into(ivMusicType);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(TAG, "onOffsetChanged: " + verticalOffset);

                if (verticalOffset == 0) {
                    tbTopSongs.setBackground(getResources()
                                    .getDrawable(R.drawable.custom_gradient_toolbar));
                } else {
                    tbTopSongs.setBackground(null);
                }
            }
        });
    }

}
