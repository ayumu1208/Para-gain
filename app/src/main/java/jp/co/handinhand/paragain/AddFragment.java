package jp.co.handinhand.paragain;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class AddFragment extends Fragment {

    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;
    private ImageView image_view;

    public AddFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        Button buttonUpload = (Button) view.findViewById(R.id.button_upload);

        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;
        mBottunChooseImage = view.findViewById(R.id.button_choose_image);
        mBottunUpload = view.findViewById(R.id.button_upload);
        mTextViewShowUploads = view.findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = view.findViewById(R.id.edit_text_file_name);
        mImageView = view.findViewById(R.id.image_view);
        mProgressBar = view.findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("thumbnails");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("thumbnails");


        return view;


    }

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mBottunChooseImage;
    private Button mBottunUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBottunChooseImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openMovieFragment();
            }
        });

        mBottunUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getContext(),"Upload in progress", Toast.LENGTH_SHORT).show();
                }else{
                    uploadFile();
                }
            }
        });

    }
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            image_view.setImageURI(mImageUri);

        }
    }

    private void openMovieFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(new MovieFragment(),null)
                .commit();

        MainActivity activity = (MainActivity) getActivity();
        activity.showFragment(R.id.navigation_movie);
    }
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private ContentResolver getContentResolver() {
    }


    private void uploadFile(){
        if (mImageUri != null){
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."
                    +getFileExtension(mImageUri));

            mUploadTask=fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    },5000);

                    Toast.makeText(getContext(),
                            "Upload successful", Toast.LENGTH_LONG).show();
                    Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                            taskSnapshot.getDownloadUrl().toString());
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(upload);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        }else{
            Toast.makeText(getContext(),"No file selected",Toast.LENGTH_SHORT).show();
        }
        private void openMovieFragment(){
         Intent intent = new Intent(getContext(),MovieFragment.class);
         startActivity(intent);
        }
    }


}


