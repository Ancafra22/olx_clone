package com.andrefrancisco.olxclone.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andrefrancisco.olxclone.activities.MainActivity;
import com.andrefrancisco.olxclone.R;
import com.andrefrancisco.olxclone.helper.FirebaseHelper;
import com.andrefrancisco.olxclone.model.Usuario;

public class CriarContaActivity extends AppCompatActivity {

    private EditText edt_nome;
    private EditText edt_email;
    private EditText edt_telefone;
    private EditText edt_senha;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        iniciaComponentes();
        confgCliques();
    }

    private void confgCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(v -> finish());
    }

    private void cadastrarUsuario(Usuario usuario) {
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                String idUser = task.getResult().getUser().getUid();

                usuario.setId(idUser);
                usuario.salvar(progressBar, getBaseContext());
                startActivity(new Intent(this, MainActivity.class));
                finish();

            } else {
                String erro = FirebaseHelper.validaErros(task.getException().getMessage());
                Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
                Log.i("INFOTESTE", "logar: " + task.getException().getMessage());

                if(erro == "Password should be at least 6 characters"){
                    edt_senha.setText("");
                    edt_senha.requestFocus();
                }else {
                    edt_nome.setText("");
                    edt_email.setText("");
                    edt_telefone.setText("");
                    edt_senha.setText("");
                    progressBar.setVisibility(View.GONE);
                    edt_nome.requestFocus();
                }
            }
        });
    }

    private void iniciaComponentes() {
        TextView text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText("Criar Conta");
        edt_nome = findViewById(R.id.edt_nome);
        edt_email = findViewById(R.id.edt_email);
        edt_telefone = findViewById(R.id.edt_telefone);
        edt_senha = findViewById(R.id.edt_senha);
        progressBar = findViewById(R.id.progressBar);
    }

    public void validaDados(View view) {
        //criando as variáveis para receber  e recuperar as informações digitadadas pelos usuários
        String nome = edt_nome.getText().toString();
        String email = edt_email.getText().toString();
        String telefone = edt_telefone.getText().toString();
        String senha = edt_senha.getText().toString();

        //fazendo as validações dos campos que tem de ser obrigatóriamente preenchidos
        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!telefone.isEmpty()) {
                    if (!senha.isEmpty()) {
                        progressBar.setVisibility(View.VISIBLE);
                        Usuario usuario = new Usuario();
                        usuario.setNome(nome);
                        usuario.setEmail(email);
                        usuario.setTelefone(telefone);
                        usuario.setSenha(senha);
                        cadastrarUsuario(usuario);
                    } else {
                        edt_senha.requestFocus();
                        edt_senha.setError("Campo senha obrigatório");
                    }
                } else {
                    edt_telefone.requestFocus();
                    edt_telefone.setError("Campo telefone é obrigatório");
                }
            } else {
                edt_email.requestFocus();
                edt_email.setError("Campo e-mail é obrigatório");
            }
        } else {
            edt_nome.requestFocus();
            edt_nome.setError("Campo nome é obrigatório");
        }
    }
}