package com.andrefrancisco.olxclone.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andrefrancisco.olxclone.activities.MainActivity;
import com.andrefrancisco.olxclone.R;
import com.andrefrancisco.olxclone.helper.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_email;
    private EditText edt_senha;
    private ProgressBar progressBar;
    private ImageButton ib_Voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        configCliques();
        iniciaComponentes();
    }

    private void configCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));
        findViewById(R.id.text_cadastrar).setOnClickListener(v ->
                startActivity(new Intent(this, CriarContaActivity.class)));
        findViewById(R.id.text_recuperar_senha).setOnClickListener(v ->
                startActivity(new Intent(this, RecuperarSenhaActivity.class)));
    }

    public void validaDados(View view) {

        String email = edt_email.getText().toString();
        String senha = edt_senha.getText().toString();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){

                progressBar.setVisibility(View.VISIBLE);

                logar(email, senha);

            }else{
                edt_senha.requestFocus();
                edt_senha.setError("Preenchimento obrigatório!");
            }
        }else{
            edt_email.requestFocus();
            edt_email.setError("Preenchimento obrigatório!");
        }
    }

    private void logar(String email, String senha){
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }else{
                String erro = FirebaseHelper.validaErros(task.getException().getMessage());
                Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
                Log.i("INFOTESTE", "logar: " + task.getException().getMessage());
            }
            progressBar.setVisibility(View.GONE);
        });

    }

    private void iniciaComponentes() {

        TextView text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText("Login");
        edt_email = findViewById(R.id.edt_email);
        edt_senha = findViewById(R.id.edt_senha);
        progressBar = findViewById(R.id.progressBar);
        ib_Voltar = findViewById(R.id.ib_voltar);
    }
}