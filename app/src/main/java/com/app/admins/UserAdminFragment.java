package com.app.admins;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.R;
import com.app.login_register.LoginActivity;
import com.app.model.User;

public class UserAdminFragment extends Fragment {
    CardView btnLogout;
    View view;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_admin, container, false);
        user = User.getUserFromSharedPreferences(getActivity());
        btnLogout = view.findViewById(R.id.btn_log_out);
        btnLogout.setOnClickListener(logOut());

        return view;
    }

    private View.OnClickListener logOut() {
        return view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Bạn có muốn đăng xuất không")
                    .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            clearUserFromSharedPreferences();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                            Toast.makeText(getContext(), "Bạn đã đăng xuất", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        };
    }

    private void clearUserFromSharedPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}