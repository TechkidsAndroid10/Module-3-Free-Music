package techkids.vn.freemusic.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import techkids.vn.freemusic.databases.TopSongModel;
import techkids.vn.freemusic.fragments.DownloadFragment;

/**
 * Created by LapTop on 11/3/2017.
 */

public class DownloadHandler {

    public static void downloadSong(final Context context, final TopSongModel topSongModel,
                                    final ImageView ivDownload) {
        Toast.makeText(context, "Start downloading...", Toast.LENGTH_SHORT).show();

        Uri downloadUri = Uri.parse(topSongModel.getUrl());
        Uri destinationUri = Uri.parse(context.getExternalCacheDir().toString() + "/" +
                topSongModel.getSong() + " --- " + topSongModel.getArtist() + ".mp3");
        DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                .setDestinationURI(destinationUri)
                .setDownloadListener(new DownloadStatusListener() {
                    @Override
                    public void onDownloadComplete(int id) {
                        Toast.makeText(context, topSongModel.getSong() +
                                " is downloaded", Toast.LENGTH_SHORT).show();
                        ivDownload.setAlpha(100);
                    }

                    @Override
                    public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                        Toast.makeText(context, "Download error!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {

                    }
                });

        ThinDownloadManager thinDownloadManager = new ThinDownloadManager();
        thinDownloadManager.add(downloadRequest);
    }

    //lấy ra các file nhạc đã down
    public static List<TopSongModel> getDownloadedSongs(Context context) {
        ArrayList<TopSongModel> downloadedList = new ArrayList<>();
        if (context != null) {
            File home = new File(context.getExternalCacheDir().toString());
            File[] listFiles = home.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.getName().endsWith(".mp3")) {
                        String fileName = file.getName().substring(0, (file.getName().length() - 4));
                        String[] parts = fileName.split(" --- ");

                        TopSongModel topSongModel = new TopSongModel();
                        topSongModel.setSong(parts[0]);
                        topSongModel.setArtist(parts[1]);
                        topSongModel.setUrl(context.getExternalCacheDir().toString()+ "/" + file.getName());
                        topSongModel.setSmallImage(DownloadFragment.OFFLINE_MODE);
                        topSongModel.setLargeImage(DownloadFragment.OFFLINE_MODE);
                        topSongModel.setDownloaded(true);

                        downloadedList.add(topSongModel);
                    }
                }
            }
        }
        return downloadedList;
    }
}
