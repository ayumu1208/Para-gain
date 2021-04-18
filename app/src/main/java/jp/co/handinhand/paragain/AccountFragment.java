package jp.co.handinhand.paragain;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class AccountFragment extends Fragment {

    FirebaseDatabase rootNode;
    DatabaseReference reference;

     TextInputLayout mUserName, mEmail, mPhoneNumber, mPassword;
     Button mButtonUpdate;
     ImageView mProfileImage;
     TextView mFullName;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        mUserName = view.findViewById(R.id.user_name);
        mEmail = view.findViewById(R.id.e_mail);
        mPhoneNumber = view.findViewById(R.id.phone_number);
        mPassword = view.findViewById(R.id.password);

        mButtonUpdate = view.findViewById(R.id.button_update);
        mProfileImage = view.findViewById(R.id.profile_image);
        mFullName = view.findViewById(R.id.full_name);



        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("user");

                String username = mUserName.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String phoneNumber = mPhoneNumber.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                UserHelper userHelper = new UserHelper();

                reference.setValue("");
            }
        });

    return view;}
}

