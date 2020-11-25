package ru.articat.crypta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.CookieHandler;
import java.net.CookieManager;

import ru.articat.crypta.Interfaces.MainPageParseInterface;
import ru.articat.crypta.Parsing.GetProfilePhoto;
import ru.articat.crypta.Parsing.VolleyResponce;
import ru.articat.crypta.Settings.AppSettings;
import ru.articat.crypta.Settings.Constants;
import ru.articat.crypta.Settings.DoPreferences;

import static ru.articat.crypta.Settings.Constants.TAG;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements /*LoaderCallbacks<Cursor>,*/ MainPageParseInterface {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
//    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
//    private static final String[] DUMMY_CREDENTIALS = new String[]{
//            "foo@example.com:hello", "bar@example.com:world"
//    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private ProgressDialog pd;
    private DoPreferences doPref;
    private CheckBox checkBox;
    private Resources res;
    private String login;
    private String password;
    private String type;
    private String profileLink;
    private String userId;
    private VolleyResponce vr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedTheme = Integer.parseInt(sharedPrefs.getString("key_theme", "1"));
        if(selectedTheme ==0) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
        else if(selectedTheme ==1)AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else if(selectedTheme ==2)AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        CookieManager manager = new CookieManager();
        CookieHandler.setDefault(manager);

        res=getResources();
//        AppSettings appSettings = new AppSettings();


        doPref=new DoPreferences(this);
        vr=new VolleyResponce(this);
        checkBox=(CheckBox)findViewById(R.id.fragmentloginCheckBox1);

        if(doPref.loadData(Constants.REMEMBER, "false").equals("true")) checkBox.setChecked(true);
        else checkBox.setChecked(false);
        login=doPref.loadData(Constants.LOGIN, "");
        password=doPref.loadData(Constants.PASSWORD, "");
        if (!login.equals("")){
            mEmailView.setText(login);
        }
        if (!password.equals("")){
            mPasswordView.setText(password);
        }
//        populateAutoComplete();


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

//        View mLoginFormView = findViewById(R.id.login_form);
//        View mProgressView = findViewById(R.id.login_progress);
    }

//    private void populateAutoComplete() {
//        if (!mayRequestContacts()) {
//            return;
//        }
//
//        getLoaderManager().initLoader(0, null, this);
//    }

//    private boolean mayRequestContacts() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        }
//        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok, new OnClickListener() {
//                        @Override
//                        @TargetApi(Build.VERSION_CODES.M)
//                        public void onClick(View v) {
//                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                        }
//                    });
//        } else {
//            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//        }
//        return true;
//    }

    /**
     * Callback received when a permissions request has been completed.
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_READ_CONTACTS) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
//            }
//        }
//    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        login = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();
        doPref.saveData(Constants.LOGIN, login);
        doPref.saveData(Constants.PASSWORD, password);

//        int requestCode = new CanIDidIt(this).getRequest();
        String toastMessage = res.getString(R.string.wait);
//        switch (requestCode) {
//
//            case Constants.OK:
//                init();
//                break;
//
//            case Constants.NOINTERNET:
//                toastMessage = res.getString(R.string.check_internet);
//                break;
//
//            case Constants.ERROR:
//                toastMessage = res.getString(R.string.try_later);
//                break;
//
//        }

        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }


    private void init(){
        Log.i(TAG, "init");
        LoadDialog(res.getString(R.string.autorisation));
        vr.postAuthResponce("https://", login, password, new VolleyResponce.VolleyCallback(){

            @Override
            public void requestComplete()
            {
                // TODO: Implement this method
                // Reset errors.
                mEmailView.setError(null);
                mPasswordView.setError(null);

                // Store values at the time of the login attempt.
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();

                boolean cancel = false;
                View focusView = null;

                // Check for a valid password, if the user entered one.
                if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                    mPasswordView.setError(getString(R.string.error_invalid_password));
                    focusView = mPasswordView;
                    cancel = true;
                }

                // Check for a valid email address.
                if (TextUtils.isEmpty(email)) {
                    mEmailView.setError(getString(R.string.error_field_required));
                    focusView = mEmailView;
                    cancel = true;
//        } else if (!isEmailValid(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
                }

                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                } else {
                    getInfo();
                    // Show a progress spinner, and kick off a background task to
                    // perform the user login attempt.
//                    showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
                }

            }

            @Override
            public String requestResult(String res)
            {
                // TODO: Implement this method
                return null;
            }

            @Override
            public int requestError(int res)
            {
                // TODO: Implement this method
                return 0;
            }
        });

    }

    private void getInfo(){
        Log.i(TAG, "getInfo"+login+"_"+password);
        vr.getSimpleResponce("https://", new VolleyResponce.VolleyCallback(){

            @Override
            public void requestComplete()
            {
                // TODO: Implement this method
            }

            @Override
            public String requestResult(String res)
            {

                // TODO: Implement this method
                Document doc = Jsoup.parse(res);

                type=doc.select("td.bg5").select("a.bbs_grey").first().text();
                if (ChekData()) { // проверяем верность пароля
                    Log.i(TAG, "type" + type);
                    profileLink = doc.select("div.nav4").eq(7).select("a[href]").first().attr("href");
                    userId = profileLink.substring(11);

                    if (pd.isShowing()) {
                        pd.dismiss();
                    }
                    new GetProfilePhoto(getApplication()).init("https://" + profileLink);
                    AppSettings.unlock = true;

                    doPref.saveData(Constants.REMEMBER, checkBox.isChecked() ? "true" : "false");
                    doPref.saveData(Constants.USERID, userId);
                    doPref.saveData(Constants.USERNAME, type);
                    doPref.saveData(Constants.USERLINK, profileLink);
                    new GetProfilePhoto(getApplication()).init("https://" + profileLink);
                    Constants.AUTHORIZED=true;
                }
                return null;
            }

            @Override
            public int requestError(int res)
            {
                // TODO: Implement this method
                return 0;
            }
        });
    }

    private boolean ChekData()
    {
        Log.i(TAG, "ChekData "+ type);
        if (pd.isShowing()) {
            pd.dismiss();
        }

        if(type.equals("пользователь: Гость"))
        {
            Log.w(Constants.TAG, "Not Equals: "+ type);
            ((TextView)findViewById(R.id.txtError)).setText(R.string.login_error);
            doPref.saveData(Constants.REMEMBER, "false");
            return false;
        }
        else
        {
            ((TextView)findViewById(R.id.txtError)).setText("");
//            ((MainActivity)getActivity()).unlockSlideMenu();
//            ((MainActivity)getActivity()).setUserNick();

//            new MainPageParse(this, this).doParse(true);

            return true;
        }

    }

    @Override
    public void mainPageParseCallback() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        Toast.makeText(this, res.getString(R.string.wellcom), Toast.LENGTH_SHORT).show();
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(mainIntent);
        finish();
//        ForumsListFragment yfc= new ForumsListFragment();
//        ((MainActivity)getActivity()).FragmentChanger(yfc);
    }

    private void LoadDialog(String caption)
    {
        pd = new ProgressDialog(this);
        //	pd.setTitle(caption);
        pd.setMessage(caption);
        pd.setCancelable(false);
        pd.show();

    }


    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 7;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private void showProgress(final boolean show) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });
//
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mProgressView.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });
//        } else {
//            // The ViewPropertyAnimator APIs are not available, so simply show
//            // and hide the relevant UI components.
//            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
//            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
//        }
//    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
//        return new CursorLoader(this,
//                // Retrieve data rows for the device user's 'profile' contact.
//                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
//                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
//
//                // Select only email addresses.
//                ContactsContract.Contacts.Data.MIMETYPE +
//                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
//                .CONTENT_ITEM_TYPE},
//
//                // Show primary email addresses first. Note that there won't be
//                // a primary email address if the user hasn't specified one.
//                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
//    }

//    @Override
//    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
//        List<String> emails = new ArrayList<>();
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            emails.add(cursor.getString(ProfileQuery.ADDRESS));
//            cursor.moveToNext();
//        }
//
//        addEmailsToAutoComplete(emails);
//    }

//    @Override
//    public void onLoaderReset(Loader<Cursor> cursorLoader) {
//
//    }

//    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
//        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<>(LoginActivity.this,
//                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
//
//        mEmailView.setAdapter(adapter);
//    }


//    private interface ProfileQuery {
//        String[] PROJECTION = {
//                ContactsContract.CommonDataKinds.Email.ADDRESS,
//                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
//        };
//
//        int ADDRESS = 0;
//        int IS_PRIMARY = 1;
//    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
//    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//
//        private final String mEmail;
//        private final String mPassword;
//
//        UserLoginTask(String email, String password) {
//            mEmail = email;
//            mPassword = password;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            // TODO: attempt authentication against a network service.
//
//            try {
//                // Simulate network access.
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                return false;
//            }
//
//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    return pieces[1].equals(mPassword);
//                }
//            }
//
//            // TODO: register the new account here.
//            return true;
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            mAuthTask = null;
//            showProgress(false);
//
//            if (success) {
//                finish();
//            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            mAuthTask = null;
//            showProgress(false);
//        }
//    }
}

