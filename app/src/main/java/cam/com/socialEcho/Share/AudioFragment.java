package cam.com.socialEcho.Share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cam.com.socialEcho.R;
import cam.com.socialEcho.Utils.Permissions;

/**
 * Created by GUL on 05-11-2017.
 */

public class AudioFragment extends Fragment{

    private static final String TAG = "AudioFragment";

    //constant
    private static final int AUDIO_FRAGMENT_NUM = 2;
    private static final int GALLERY_FRAGMENT_NUM = 0;
    private static final int  RECORD_AUDIO_REQUEST_CODE = 4;

    Uri savedUri;



    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio, container, false);
        Log.d(TAG, "onCreateView: started.");

        Button btnLaunchRECORD_AUDIO = (Button) view.findViewById(R.id.btnLaunchMic);

        btnLaunchRECORD_AUDIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: launching RECORD_AUDIO.");

                if(((ShareActivity)getActivity()).getCurrentTabNumber() == AUDIO_FRAGMENT_NUM){
                    Log.d(TAG, "onClick: in record_Audio tab");
                    if(((ShareActivity)getActivity()).checkPermissions(Permissions.RECORD_AUDIO_PERMISSION[0])){
                        Log.d(TAG, "onClick: starting RECORD_AUDIO");
                        Intent recordAudioIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                        startActivityForResult(recordAudioIntent, RECORD_AUDIO_REQUEST_CODE);
                    }else{
                        Intent intent = new Intent(getActivity(), ShareActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }
        });

        return view;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{
            savedUri = data.getData();
            if(requestCode == RECORD_AUDIO_REQUEST_CODE){
                Log.d(TAG, "onActivityResult: done recording an audio.");
                Log.d(TAG, "onActivityResult: attempting to navigate to final share screen.");
                //navigate to the final share screen to publish Audio
                try{
                    Log.d(TAG, "onActivityResult: received new uri from Mic: " + savedUri);
                    Intent intent = new Intent(getActivity(), NextActivityAudio.class);
                    intent.putExtra(getString(R.string.selected_uri), savedUri.toString());
                    startActivity(intent);
                }catch (NullPointerException e){
                    Log.d(TAG, "onActivityResult: NullPointerException: " + e.getMessage());
                }
            }
        }catch (RuntimeException e){
            Log.d(TAG, "onActivityResult: RuntimeException" + e.getMessage());
        }


    }


}