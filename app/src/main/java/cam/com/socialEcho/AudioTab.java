package cam.com.socialEcho;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by GUL on 26-11-2017.
 */

public class AudioTab {

    public  static class DownloadingAudio extends  RecyclerView.ViewHolder{

        View mView;
        public DownloadingAudio(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public  void setAudioCaption(String audioCaption){
            TextView AudioCaption  = (TextView) mView.findViewById(R.id.tvAudiocaption);
            AudioCaption.setText(audioCaption);
        }

        public  void setDate_created(String date_created){
            TextView AudioUsername  = (TextView) mView.findViewById(R.id.tvAudioUsername);
            AudioUsername.setText(date_created);
        }

        public  void setAudio_path(Context context, String audio_path){
            FloatingActionButton floatingActionButton = (FloatingActionButton) mView.findViewById(R.id.audioPlayButton);
        }
    }



}
