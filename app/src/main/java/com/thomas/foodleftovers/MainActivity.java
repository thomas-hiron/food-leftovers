package com.thomas.foodleftovers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.thomas.foodleftovers.ui.IngredientsListView;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    public static final String APP_TAG = "FOOD";
    private static final int CAMERA = 1;

    private DecoratedBarcodeView barcodeView;

    private BarcodeCallback callback = new BarcodeCallback()
    {
        @Override
        public void barcodeResult(BarcodeResult result)
        {
            /* Ajout dans l'adapter */
            IngredientsListView list = findViewById(R.id.ingredients_list);
            list.addIngredientFromBarcode(result.getText());
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints)
        {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Démarrage du scan */
        barcodeView = findViewById(R.id.barcode);
        barcodeView.decodeContinuous(callback);

        /* Suppression du texte en dessous */
        barcodeView.getStatusView().setVisibility(View.GONE);

        /* Demande de permission */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            Log.i("FOOD", "pas de permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case CAMERA:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this, "Cannot run application because camera service permission have not been granted", Toast.LENGTH_SHORT).show();
                    barcodeView.pause();
                }
                else
                {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    barcodeView.resume();
                }

                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}