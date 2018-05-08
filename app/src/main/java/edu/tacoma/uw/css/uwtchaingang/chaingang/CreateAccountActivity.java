package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.support.constraint.Constraints.TAG;

public class CreateAccountActivity extends AppCompatActivity {

    /**
     * Constant with a link to php code which will handle adding a new user to database
     */
    private final static String MEMBER_ADD_URL
            = "https://chaingang.000webhostapp.com/addUser.php?";

    /**
     * Variable to keep a user first name
     */
    private EditText mFirstName;

    /**
     * Variable to keep a user last name
     */
    private EditText mLastName;

    /**
     * Variable to keep a user email address
     */
    private EditText mEmail;

    /**
     * Variable to keep a user login
     */
    private EditText mLogin;

    /**
     * Variable to keep a user password
     */
    private EditText mPassword;

    /**
     * Variable to keep a user confirmation of the given password
     */
    private EditText mConfirmPassword;

    private MemberAddListener mListener;

    /**
     * Required empty public constructor
     */
    public CreateAccountActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Button addUserButton = (Button) findViewById(R.id.createAccountButton);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirstName = (EditText) findViewById(R.id.firstname);
                mLastName = (EditText) findViewById(R.id.lastname);
                mEmail = (EditText) findViewById(R.id.email);
                mLogin = (EditText) findViewById(R.id.login);
                mPassword = (EditText) findViewById(R.id.password);
                mConfirmPassword = (EditText) findViewById(R.id.confirmpassword);
                String url = buildUserURL(v);
                addMember(url);
                Log.v("CreateAccountActivity", "Felt the button");
            }
        });
    }


    public void addMember(String url) {
        AddMemberTask task = new AddMemberTask();
        task.execute(new String[]{url.toString()});

    }


    /**
     *
     */
    public interface MemberAddListener {
        public void addMember(String url);
    }

    /**
     *
     * @param v
     * @return
     */
    private String buildUserURL(View v) {

        StringBuilder sb = new StringBuilder(MEMBER_ADD_URL);
        try {
            Log.v("CreateAccountActivity", "build url                            1");

            String email = mEmail.getText().toString();
            sb.append("email=");
            sb.append(URLEncoder.encode(email, "UTF-8"));

            Log.v("CreateAccountActivity", "build url                            2");
            String lastName = mLastName.getText().toString();
            sb.append("&firstname=");
            sb.append(URLEncoder.encode(lastName, "UTF-8"));

            Log.v("CreateAccountActivity", "build url                            3");
            String firstName = mFirstName.getText().toString();
            sb.append("&lastname=");
            sb.append(URLEncoder.encode(firstName, "UTF-8"));

            Log.v("CreateAccountActivity", "build url                            4");
            String login = mLogin.getText().toString();
            sb.append("&login=");
            sb.append(URLEncoder.encode(login, "UTF-8"));

            Log.v("CreateAccountActivity", "build url                            5" + mPassword.getText().toString() + "jdjjdjjdj");
            String password = mPassword.getText().toString();
            sb.append("&password=");
            sb.append(URLEncoder.encode(password, "UTF-8"));

            Log.i(TAG, sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    /**
     *
     */
    private class AddMemberTask extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();

                    InputStream content = urlConnection.getInputStream();

                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    response = "Unable to add a user, reason: "
                            + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }


        /**
         * It checks to see if there was a problem with the URL(Network) which is when an
         * exception is caught. It tries to call the parse Method and checks to see if it was successful.
         * If not, it displays the exception.
         *
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            try {
                JSONObject jsonObject = new JSONObject(result);
                String status = (String) jsonObject.get("result");
                if (status.equals("success")) {
                    Toast.makeText(getApplicationContext(), "User successfully added!"
                            , Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add: "
                                    + jsonObject.get("error")
                            , Toast.LENGTH_LONG)
                            .show();
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Something wrong with the data" +
                        e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }


    //@Override
    public void addUser(String url) {

        AddMemberTask task = new AddMemberTask();
        task.execute(new String[]{url.toString()});

// Takes you back to the previous fragment by popping the current fragment out.
//        getSupportFragmentManager().popBackStackImmediate();

        //gotoChooseChain();
    }



    /**
     * Method to call choose chain activity
     * @param view
     */
    public void gotoChooseChain(View view) {
        Intent intent = new Intent(this, ChooseChainActivity.class);
        startActivity(intent);
    }


}
