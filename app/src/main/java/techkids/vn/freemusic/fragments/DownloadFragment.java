package techkids.vn.freemusic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.vn.freemusic.R;
import techkids.vn.freemusic.adapters.TopSongAdapter;
import techkids.vn.freemusic.databases.TopSongModel;
import techkids.vn.freemusic.utils.DownloadHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment {
    public static String OFFLINE_MODE = "offline_mode";
    private List<TopSongModel> downloadedSongs;
    private TopSongAdapter downloadedSongAdapter;

    @BindView(R.id.rv_download)
    RecyclerView rvDownload;

    public DownloadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);

        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        rvDownload.setLayoutManager(new LinearLayoutManager(getContext()));

        downloadedSongs = DownloadHandler.getDownloadedSongs(getContext());
        downloadedSongAdapter = new TopSongAdapter(getContext(), downloadedSongs);
        rvDownload.setAdapter(downloadedSongAdapter);
    }

}
