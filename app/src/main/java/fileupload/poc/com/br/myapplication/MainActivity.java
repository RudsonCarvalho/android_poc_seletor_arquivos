package fileupload.poc.com.br.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        performFileSearch();
    }

    private static final int READ_REQUEST_CODE = 42;
    private static final String TAG = "FILE_UP";

    /**
     * Abrir o seletor de imagem do android.
     */
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT intent para selecionar o arquivo
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Apenas arquivos que podem ser abertos
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Teste apenas com imagens, ver MIME data type.
        // todos os provedores de imagens
        // qualquer arquivo "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // Evento de selecao, após o documento ser selecionado ACTION_OPEN_DOCUMENT

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Pega a URI do documento selecionado
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i(TAG, "Uri: " + uri.toString());

                try {
                    Log.i(TAG, "Transformar em stream " + readTextFromUri(uri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Transforma o arquivo apontado na URI em Stream
     * @param uri
     * @return
     * @throws IOException
     */
    private String readTextFromUri(Uri uri) throws IOException {

        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        inputStream.close();
        reader.close();
        return stringBuilder.toString();
    }


}
