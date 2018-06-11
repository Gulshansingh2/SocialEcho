package cam.com.socialEcho;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cam.com.socialEcho.models.Audio;

/**
 * Created by GUL on 14/10/2017.
 */

public class AudioActivity extends AppCompatActivity{
    private static final String TAG = "AudioActivity";
    private static final int ACTIVITY_NUM = 3;

    private Context mContext = AudioActivity.this;
    private static MediaPlayer mediaPlayer = new MediaPlayer();

    RecyclerView rvAudioList;

    DatabaseReference drAudio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: started.");

        setContentView(R.layout.activity_echo);
        drAudio = FirebaseDatabase.getInstance().getReference().child("audio");

        rvAudioList = (RecyclerView) findViewById(R.id.echo);
        rvAudioList.setHasFixedSize(true);
        rvAudioList.setLayoutManager(new LinearLayoutManager(AudioActivity.this));


    }





    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Audio, AudioTab.DownloadingAudio> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Audio, AudioTab.DownloadingAudio>(
                Audio.class,
                R.layout.audio_list,
                AudioTab.DownloadingAudio.class,
                drAudio
        ) {
            @Override
            protected void populateViewHolder(AudioTab.DownloadingAudio viewHolder, Audio model, int position) {

                final String audio_key = getRef(position).getKey();

                //set the time it was posted
                String timestampDifference = getTimestampDifference(getItem(position));

                if(!timestampDifference.equals("0")){
                    viewHolder.setDate_created(timestampDifference + " DAYS AGO");
                }else{
                    viewHolder.setDate_created("Today");
                }

                viewHolder.setAudioCaption(model.getCaption());
                viewHolder.setAudio_path(mContext, model.getAudio_path());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drAudio.child(audio_key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String audio_file = (String) dataSnapshot.child("audio_path").getValue();

                                try {
                                    mediaPlayer.stop();
                                    mediaPlayer = new MediaPlayer();
                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    mediaPlayer.setDataSource(audio_file);
                                    mediaPlayer.prepareAsync();
                                    Toast.makeText(mContext, "Loading...", Toast.LENGTH_SHORT).show();
                                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                                        @Override
                                        public void onPrepared(MediaPlayer player) {
                                            Log.d(TAG, "onPrepared: Play");
                                            player.start();
                                        }
                                    });
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        };
        rvAudioList.setAdapter(firebaseRecyclerAdapter);

    }

    /**
     * Returns a string representing the number of days ago the post was made
     * @return
     */
    private String getTimestampDifference(Audio audio){
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        final String photoTimestamp = audio.getDate_created();
        try{
            timestamp = sdf.parse(photoTimestamp);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
        }catch (ParseException e){
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
            difference = "0";
        }
        return difference;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try{
            mediaPlayer.stop();
        }catch (NullPointerException e){
            Log.e(TAG, "onBackPressed: NullPointerException"+ e.getMessage());
        }
        finish();
    }

}

