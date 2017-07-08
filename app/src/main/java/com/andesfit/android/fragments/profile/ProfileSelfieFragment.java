package com.andesfit.android.fragments.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andesfit.android.activities.MainActivity;
import com.andesfit.android.R;
import com.andesfit.android.util.HealthSharedPreference;
import com.andesfit.android.util.RoundedImageView;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Vampire on 2017-05-25.
 */

public class ProfileSelfieFragment extends Fragment implements View.OnClickListener
{

    private static final int CAMERA_REQUEST = 1007;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.profile_setting_6, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init()
    {

        TextView finish = (TextView) getView().findViewById(R.id.txtFinish);
        finish.setOnClickListener(this);

        RoundedImageView cameraImg = (RoundedImageView)getView().findViewById(R.id.cameraImg);
        cameraImg.setOnClickListener(this);

        TextView takePIc = (TextView) getView().findViewById(R.id.takePIc);
        takePIc.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.cameraImg:
            case R.id.takePIc:
                captureImage();
                break;
            case R.id.txtFinish:
                profileCompleted();
                break;
        }
    }

    private void captureImage()
    {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            saveProfileBitmap(mphoto);
            ((RoundedImageView)getView().findViewById(R.id.cameraImg)).setImageBitmap(mphoto);
        }
    }

    private void saveProfileBitmap(Bitmap mphoto)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mphoto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        HealthSharedPreference preference = HealthSharedPreference.getInstance(getContext());
        preference.setProfileimage(encodedImage);
    }

    private void profileCompleted()
    {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}

