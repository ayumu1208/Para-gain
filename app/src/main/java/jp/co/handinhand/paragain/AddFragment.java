package jp.co.handinhand.paragain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AddFragment extends Fragment{

    public AddFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);
      Button buttonUpload = (Button) v.findViewById(R.id.button_upload);


        buttonUpload.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                HomeFragment homeFragment = new HomeFragment();
              FragmentManager manager = getFragmentManager();
              manager.beginTransaction()
                      .replace(R.id.image_view_upload,homeFragment,homeFragment.getTag()).commit();
          }
      });
    }

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mBottunPasteUrl;
    private Button mBottunUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        mBottunPasteUrl = view.findViewById(R.id.button_paste_url);
        mBottunUpload = view.findViewById(R.id.button_upload);
        mTextViewShowUploads = view.findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = view.findViewById(R.id.edit_text_file_name);
        mImageView = view.findViewById(R.id.image_view);
        mProgressBar = view.findViewById(R.id.progress_bar);

        return view}



      mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              openHomeFragment();
          }
      });




    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
private void openHomeFragment(){
        Intent intent = new Intent(this,AddFragment.class)
                startActivity(intent);
}



}
