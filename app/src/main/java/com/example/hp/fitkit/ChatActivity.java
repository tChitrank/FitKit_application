package com.example.hp.fitkit;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ChatActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 450;
    public static final int RC_SIGN_IN = 1;
    public static final int RC_PHOTO_PICKER = 2;

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;
    private FloatingActionButton floatingActionButton;
    private String mUsername,newToken;
    String strTime;

    FirebaseDatabase fd;
    DatabaseReference md;
    ChildEventListener cd;
    FirebaseAuth fa;
    FirebaseAuth.AuthStateListener ad;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat);

        mUsername = ANONYMOUS;

        // Initialize references to views
        fd = FirebaseDatabase.getInstance();
        fa = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        md = fd.getReference().child("messages");

        storageReference = firebaseStorage.getReference().child("chat_photos");

        mProgressBar =  findViewById(R.id.progressBar);
        floatingActionButton = findViewById(R.id.fab);
        mMessageListView =  findViewById(R.id.messageListView);
        mPhotoPickerButton =  findViewById(R.id.photoPickerButton);
        mMessageEditText =  findViewById(R.id.messageEditText);
        mSendButton =  findViewById(R.id.sendButton);


        // Initialize message ListView and its adapter
        List<FriendlyMessages> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_messages, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken= instanceIdResult.getToken();
            }
        });

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent.createChooser(intent, "Complete Action Using..."), RC_PHOTO_PICKER);
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });

        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setVisibility(View.VISIBLE);
                    mSendButton.setEnabled(true);
                    mSendButton.setBackgroundColor(getResources().getColor(R.color.chatEnabled));

                } else {
                    mSendButton.setVisibility(View.GONE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        //get system time



        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
                strTime = mdformat.format(calendar.getTime());
                String uniqueKey = md.push().getKey();
                String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                FriendlyMessages friendlyMessage = new FriendlyMessages(fa.getCurrentUser().getUid(),mMessageEditText.getText().toString().trim(), mUsername, null,uniqueKey,strTime,android_id);
                md.child(uniqueKey).setValue(friendlyMessage);
                // Clear input box
                mMessageEditText.setText("");
            }
        });

        //Set the Authentication For Application
        ad = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    onSignInIntialized(user.getDisplayName());
                } else {
                    onCleanUp();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.PhoneBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(ChatActivity.this);
            }
        });
    }

    public void onCleanUp() {
        mMessageAdapter.clear();
        mUsername = ANONYMOUS;
        if (cd != null) {
            md.removeEventListener(cd);
            cd = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Welcome Sign In", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled Sign In", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else if (resultCode == RESULT_OK && requestCode == RC_PHOTO_PICKER) {
            mProgressBar.setVisibility(View.INVISIBLE);
            Uri selectedImageUri = data.getData();
            final StorageReference photoRef = storageReference.child(selectedImageUri.getLastPathSegment());
            photoRef.putFile(selectedImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final Uri downloadUrl = uri;
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
                            strTime = mdformat.format(calendar.getTime());
                            String uniqueKey = md.push().getKey();
                            String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                                    Settings.Secure.ANDROID_ID);
                            FriendlyMessages friendlyMessage = new FriendlyMessages(fa.getCurrentUser().getUid(),null, mUsername, downloadUrl.toString(),uniqueKey,strTime,android_id);
                            md.child(uniqueKey).setValue(friendlyMessage);
                        }
                    });

                    // Set the download URL to the message box, so that the user can send it to the database

                }
            });
        }
    }

    //For retreiving data from RealTime Database
    public void onSignInIntialized(String displayName) {
        mUsername = displayName;
        if (cd == null) {
            cd = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    FriendlyMessages fm = dataSnapshot.getValue(FriendlyMessages.class);
                    mMessageAdapter.add(fm);
                    mMessageAdapter.notifyDataSetChanged();
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }
                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    FriendlyMessages fm = dataSnapshot.getValue(FriendlyMessages.class);
                    mMessageAdapter.remove(fm);
                    mMessageAdapter.notifyDataSetChanged();
                }
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };
            md.addChildEventListener(cd);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        fa.addAuthStateListener(ad);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ad != null) {
            fa.removeAuthStateListener(ad);
        }
    }
}
