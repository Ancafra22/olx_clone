package com.andrefrancisco.olxclone.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andrefrancisco.olxclone.R;
import com.andrefrancisco.olxclone.helper.FirebaseHelper;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private EditText edt_email;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        configCliques();
        iniciaComponentes();
    }

    public void ValidaDados(View view) {
        String email = edt_email.getText().toString();
        if (!email.isEmpty()) {

            progressBar.setVisibility(View.VISIBLE);

            recuperarSenha(email);

        } else {
            edt_email.requestFocus();
            edt_email.setError("Preenchimento obrigatório!");
        }

    }

    private void recuperarSenha(String email) {
        FirebaseHelper.getAuth().sendPasswordResetEmail(
                email
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Email enviado com sucesso!", Toast.LENGTH_SHORT).show();

            } else {
                String erro = FirebaseHelper.validaErros(task.getException().getMessage());
                Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
                Log.i("INFOTESTE", "logar: " + task.getException().getMessage());
            }
            progressBar.setVisibility(View.GONE);
        });

    }

    private void configCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(v -> finish());
    }

    private void iniciaComponentes() {

        edt_email = findViewById(R.id.edt_email);
        progressBar = findViewById(R.id.progressBar);

        TextView text_titulo = findViewById(R.id.text_toolbar);
        text_titulo.setText("Recuperar Senha");
    }
}