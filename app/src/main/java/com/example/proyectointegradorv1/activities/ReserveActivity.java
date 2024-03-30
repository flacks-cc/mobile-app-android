package com.example.proyectointegradorv1.activities;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.fragments.reserve.ChooseProductFragment;
import com.example.proyectointegradorv1.fragments.reserve.ChooseTimeResFragment;
import com.example.proyectointegradorv1.fragments.reserve.ConfirmInformationResFragment;
import com.example.proyectointegradorv1.fragments.reserve.UserInformationResFragment;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReserveActivity extends AppCompatActivity {

    private String tituloEstadoDos;
    private String contenidoEstadoDos;
    private String tituloEstadoTres;
    private String contenidoEstadoTres;
    private String tituloEstadoCuatro;
    private String contenidoEstadoCuatro;
    int anchoRectangulo;

    private int contadorArchivos = 0;
    private ImageView btnReturn;
    private Fragment currentFragment;
    private TextView txtTitulo, txtDescripcion;
    private LinearLayout lytEstadoDos, lytEstadoTres, lytEstadoCuatro;
    private Button btnSiguienteP;
    private CardView cardInferior;
    private FrameLayout frameContainer;

    private UserInformationResFragment userInformationResFragment;

    private Button btnGenTicket, btnProductos;
    private TextView txtCantidad, txtNombreProducto, txtInfo;
    private int cantidad = 1; // Valor mínimo
    private int productoPredeterminado;
    String activityOrigen;

    private String obtenerFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        btnReturn = findViewById(R.id.btnReturn);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnSiguienteP = findViewById(R.id.btnSiguienteP);
        frameContainer = findViewById(R.id.frameContainer);
        cardInferior = findViewById(R.id.CardInferior);

        btnReturn.setOnClickListener(view -> onBackPressed());

        // Verificar la actividad de la que viene el usuario
        activityOrigen = getIntent().getStringExtra("activityOrigen");

        // Crea e inicializa el fragmento con el producto predeterminado si es necesario
        productoPredeterminado = obtenerProductoPredeterminado(activityOrigen);

        currentFragment = ChooseProductFragment.newInstance(productoPredeterminado);
        showSelectedFragment(currentFragment);
        updateTextsForFragment(currentFragment);

        userInformationResFragment = (UserInformationResFragment) getSupportFragmentManager().findFragmentById(R.id.frameContainer);
    }

    private void eliminarEstado(LinearLayout estado) {
        if (estado != null) {
            LinearLayout lytInt = findViewById(R.id.lytInt);
            lytInt.removeView(estado);
        }
    }

    private int obtenerProductoPredeterminado(String activityOrigen) {
        productoPredeterminado = R.id.chkTres; // ID del CheckBox predeterminado

        if (activityOrigen != null) {
            switch (activityOrigen) {
                case "CremaBarbaActivity":
                    productoPredeterminado = R.id.chkUno; // Crema para la barba
                    break;
                case "PomadaBarbaActivity":
                    productoPredeterminado = R.id.chkDos; // Pomada para la barba
                    break;
                case "CremaActivity":
                    productoPredeterminado = R.id.chkTres; // Crema
                    break;
                case "BalsamoBarbaActivity":
                    productoPredeterminado = R.id.chkCuatro; // Bálsamo para la barba
                    break;
                case "PolvoActivity":
                    productoPredeterminado = R.id.chkCinco; // Polvo voluminizador
                    break;
                case "FibraActivity":
                    productoPredeterminado = R.id.chkSeis; // Fibra para el cabello
                    break;
                case "ShampooActivity":
                    productoPredeterminado = R.id.chkSiete; // Shampoo para la barba
                    break;
                case "CeraActivity":
                    productoPredeterminado = R.id.chkOcho; // Cera
                    break;
                default:
                    // Manejar otros casos si es necesario
            }
        }

        return productoPredeterminado;
    }

    public void agregarSegundoEstado(String titulo, String contenido) {
        // Encuentra el LinearLayout dentro del CardView
        LinearLayout lytInt = findViewById(R.id.lytInt);

        // Crea un nuevo LinearLayout para el estado
        lytEstadoDos = new LinearLayout(this);

        // Configura el LinearLayout
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lytEstadoDos.setLayoutParams(layoutParams);
        lytEstadoDos.setOrientation(LinearLayout.VERTICAL);

        // Crea un TextView para el título
        TextView txtStateTitle = new TextView(this);
        txtStateTitle.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        txtStateTitle.setText(titulo);
        txtStateTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);  // Ajusta el tamaño del texto en SP
        txtStateTitle.setTypeface(null, Typeface.BOLD);

        // Crea un TextView para el contenido
        TextView txtStateContent = new TextView(this);
        txtStateContent.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        txtStateContent.setText(contenido);
        txtStateContent.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);  // Ajusta el tamaño del texto en SP

        // Agrega los TextView al LinearLayout
        lytEstadoDos.addView(txtStateTitle);
        lytEstadoDos.addView(txtStateContent);

        // Agrega el nuevo estado al LinearLayout dentro del CardView
        // Obtiene la posición del botón y agrega el nuevo estado justo encima de él
        int botonReservarIndex = lytInt.indexOfChild(findViewById(R.id.btnSiguienteP));
        lytInt.addView(lytEstadoDos, botonReservarIndex);

        // Añade un margen en la parte superior del nuevo estado
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) lytEstadoDos.getLayoutParams();
        marginLayoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.spacing_medium); // Puedes ajustar el valor directamente aquí
        lytEstadoDos.setLayoutParams(marginLayoutParams);

        // Almacena la información del segundo estado en las variables correspondientes
        tituloEstadoDos = titulo;
        contenidoEstadoDos = contenido;
    }

    public void agregarTercerEstado(String titulo, String contenido) {
        // Encuentra el LinearLayout dentro del CardView
        LinearLayout lytInt = findViewById(R.id.lytInt);

        // Verifica si ya existe lytEstadoTres
        if (lytEstadoTres != null) {
            lytInt.removeView(lytEstadoTres); // Elimina el estado existente
        }

        // Crea un nuevo LinearLayout para el estado
        lytEstadoTres = new LinearLayout(this);

        // Configura el LinearLayout
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lytEstadoTres.setLayoutParams(layoutParams);
        lytEstadoTres.setOrientation(LinearLayout.VERTICAL);

        // Crea un TextView para el título
        TextView txtStateTitle = new TextView(this);
        txtStateTitle.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        txtStateTitle.setText(titulo);
        txtStateTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        txtStateTitle.setTypeface(null, Typeface.BOLD);

        // Crea un TextView para el contenido
        TextView txtStateContent = new TextView(this);
        txtStateContent.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        txtStateContent.setText(contenido);
        txtStateContent.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);

        // Agrega los TextView al LinearLayout
        lytEstadoTres.addView(txtStateTitle);
        lytEstadoTres.addView(txtStateContent);

        // Agrega el nuevo estado al LinearLayout dentro del CardView
        // Obtiene la posición del botón y agrega el nuevo estado justo encima de él
        int botonReservarIndex = lytInt.indexOfChild(findViewById(R.id.btnSiguienteP));
        lytInt.addView(lytEstadoTres, botonReservarIndex);

        // Añade un margen en la parte superior del nuevo estado
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) lytEstadoTres.getLayoutParams();
        marginLayoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.spacing_medium);
        lytEstadoTres.setLayoutParams(marginLayoutParams);
        // Almacena la información del tercer estado en las variables correspondientes
        tituloEstadoTres = titulo;
        contenidoEstadoTres = contenido;

    }

    public void agregarCuartoEstado(String titulo, String contenido) {
        // Encuentra el LinearLayout dentro del CardView
        LinearLayout lytInt = findViewById(R.id.lytInt);

        // Verifica si ya existe lytEstadoTres
        if (lytEstadoCuatro != null) {
            lytInt.removeView(lytEstadoCuatro); // Elimina el estado existente
        }

        // Crea un nuevo LinearLayout para el estado
        lytEstadoCuatro = new LinearLayout(this);

        // Configura el LinearLayout
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lytEstadoCuatro.setLayoutParams(layoutParams);
        lytEstadoCuatro.setOrientation(LinearLayout.VERTICAL);

        // Crea un TextView para el título
        TextView txtStateTitle = new TextView(this);
        txtStateTitle.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        txtStateTitle.setText(titulo);
        txtStateTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        txtStateTitle.setTypeface(null, Typeface.BOLD);

        // Crea un TextView para el contenido
        TextView txtStateContent = new TextView(this);
        txtStateContent.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        txtStateContent.setText(contenido);
        txtStateContent.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);

        // Agrega los TextView al LinearLayout
        lytEstadoCuatro.addView(txtStateTitle);
        lytEstadoCuatro.addView(txtStateContent);

        // Agrega el nuevo estado al LinearLayout dentro del CardView
        // Obtiene la posición del botón y agrega el nuevo estado justo encima de él
        int botonReservarIndex = lytInt.indexOfChild(findViewById(R.id.btnSiguienteP));
        lytInt.addView(lytEstadoCuatro, botonReservarIndex);

        // Añade un margen en la parte superior del nuevo estado
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) lytEstadoCuatro.getLayoutParams();
        marginLayoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.spacing_medium);
        lytEstadoCuatro.setLayoutParams(marginLayoutParams);

        // Almacena la información del cuarto estado en las variables correspondientes
        tituloEstadoCuatro = titulo;
        contenidoEstadoCuatro = contenido;
    }

    public void generarPDF() {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        TextPaint textPaint = new TextPaint();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(792, 612, 1).create(); // Rotamos las dimensiones para orientación vertical
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Configuración del fondo blanco
        canvas.drawColor(ContextCompat.getColor(this, android.R.color.white));

        // Agrega un título al documento
        textPaint.setTextSize(20);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Ticket de Reserva", canvas.getWidth() / 2 - textPaint.measureText("Ticket de Reserva") / 2, 30, textPaint);

        // Configuración de la fecha
        textPaint.setTextSize(12);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String fechaGeneracion = dateFormat.format(new Date());
        String fechaTexto = "Fecha de generación: " + fechaGeneracion;

        float maxWidth = 600;
        float textWidth = textPaint.measureText(fechaTexto);
        float xFecha = (canvas.getWidth() - textWidth) / 2;
        float yFecha = 50; // Ajusta esta coordenada según tus necesidades

        // Dibuja la fecha en la parte superior media
        canvas.drawText(fechaTexto, xFecha, yFecha, textPaint);

        // Configuración de la página
        canvas.drawColor(Color.WHITE); // Cambié a Color.WHITE para un fondo blanco

        // Configuración del borde
        paint.setColor(Color.BLACK); // Cambié a Color.BLACK para un borde negro
        paint.setStrokeWidth(5); // Grosor del borde

        // Dibuja el borde
        canvas.drawLine(0, 0, 792, 0, paint); // Borde superior
        canvas.drawLine(0, 0, 0, 612, paint); // Borde izquierdo
        canvas.drawLine(792, 0, 792, 612, paint); // Borde derecho
        canvas.drawLine(0, 612, 792, 612, paint); // Borde inferior

        // Dibuja el logo de la empresa en la esquina superior izquierda
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        // Ajusta el tamaño del logo según tus necesidades
        int nuevoAnchoLogo = 100;  // Ajusta el ancho del logo
        int nuevoAltoLogo = 100;   // Ajusta el alto del logo
        logo = Bitmap.createScaledBitmap(logo, nuevoAnchoLogo, nuevoAltoLogo, false);
        // Ajusta las coordenadas x e y según tus necesidades
        int xLogo = 10;  // Ajusta la posición horizontal
        int yLogo = 10;  // Ajusta la posición vertical
        canvas.drawBitmap(logo, xLogo, yLogo, paint);

        // Configuración del rectángulo de datos del producto
        int xRectangulo = 50;
        int yRectangulo = 150;
        int anchoRectangulo = 600;
        int altoRectangulo = 200;


        // Dibuja el rectángulo con relleno blanco y borde negro
        paint.setStyle(Paint.Style.FILL_AND_STROKE); // Relleno y borde
        paint.setColor(Color.WHITE); // Color de relleno blanco
        canvas.drawRect(xRectangulo, yRectangulo, xRectangulo + anchoRectangulo, yRectangulo + altoRectangulo, paint);

        // Configuración del texto dentro del rectángulo
        textPaint.setTextSize(16);
        paint.setStyle(Paint.Style.FILL); // Solo relleno para el texto

        // Dibuja los datos del producto dentro del rectángulo
        drawTextInsideRectangle(canvas, "Flack's Barber Shop", textPaint, xRectangulo + 10, yRectangulo + 10, maxWidth);
        drawTextInsideRectangle(canvas, "Cerca de, Av 1 333, México, 94520 Córdoba, Ver.", textPaint, xRectangulo + 10, yRectangulo + 30, maxWidth);
        // Modificación: Agrega información de los productos seleccionados
        drawTextInsideRectangle(canvas, "Productos seleccionados:", textPaint, xRectangulo + 10, yRectangulo + 90, maxWidth);

        if (tituloEstadoDos != null && contenidoEstadoDos != null) {
            drawTextInsideRectangle(canvas, "Nombre del producto: " + tituloEstadoDos, textPaint, xRectangulo + 20, yRectangulo + 120, maxWidth);
            drawTextInsideRectangle(canvas, "Información del producto: " + contenidoEstadoDos, textPaint, xRectangulo + 20, yRectangulo + 150, maxWidth);
        }

        if (tituloEstadoTres != null && contenidoEstadoTres != null) {
            drawTextInsideRectangle(canvas, "Horario de la reserva: " + contenidoEstadoTres, textPaint, xRectangulo + 20, yRectangulo + 190, maxWidth);
        }

        if (tituloEstadoCuatro != null && contenidoEstadoCuatro != null) {
            drawTextInsideRectangle(canvas, "A nombre de: " + tituloEstadoCuatro, textPaint, xRectangulo + 20, yRectangulo + 220, maxWidth);
            drawTextInsideRectangle(canvas, "Información de contacto: " + contenidoEstadoCuatro, textPaint, xRectangulo + 20, yRectangulo + 250, maxWidth);
        }

        // Configuración de la fecha en la parte superior media
        xFecha += textPaint.measureText("Fecha de generación: "); // Ajusta la coordenada x para la parte después de los dos puntos
        canvas.drawText(fechaGeneracion, xFecha, yFecha, textPaint);

        pdfDocument.finishPage(page);

        // Obtén la URI del directorio de descargas
        Uri downloadsUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        // Crea un ContentValues con el nombre del archivo y el tipo MIME
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Downloads.DISPLAY_NAME, "ticket_reserva_flacks.pdf");
        contentValues.put(MediaStore.Downloads.MIME_TYPE, "application/pdf");
        // Inserta el archivo en el directorio de descargas
        Uri uri = getContentResolver().insert(downloadsUri, contentValues);

        try {
            if (uri != null) {
                // Abre un OutputStream para escribir en el archivo
                OutputStream outputStream = getContentResolver().openOutputStream(uri);
                if (outputStream != null) {
                    // Escribe tu PDF en outputStream
                    pdfDocument.writeTo(outputStream);

                    Toast.makeText(this, "Ticket generado y guardado en tu carpeta de descargas", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Error al obtener la URL de descargas", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al escribir en el archivo PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            pdfDocument.close();
        }
    }

    // Método para dibujar texto dentro de un rectángulo
    private void drawTextInsideRectangle(Canvas canvas, String text, TextPaint textPaint, float x, float y, float maxWidth) {
        List<String> lineas = dividirTexto(text, textPaint, maxWidth);

        for (String linea : lineas) {
            canvas.drawText(linea, x, y, textPaint);
            y += textPaint.getFontSpacing();
        }
    }

    // Método para dividir el texto en líneas más cortas
    private List<String> dividirTexto(String text, TextPaint textPaint, float maxWidth) {
        List<String> lineas = new ArrayList<>();
        int start = 0;
        int end;

        while (start < text.length()) {
            try {
                end = textPaint.breakText(text, start, text.length(), true, maxWidth, null);
            } catch (Throwable t) {
                // Manejar la excepción y salir del bucle
                t.printStackTrace();
                break;
            }

            lineas.add(text.substring(start, start + end));
            start += end;
        }

        return lineas;
    }

    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 200);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            if (grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permiso rechazado", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    private void showSelectedFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void updateTextsForFragment(Fragment fragment) {
        // Actualiza los textos según el fragmento actual
        if (fragment instanceof ChooseProductFragment) {
            txtTitulo.setText("Elige un producto");
            txtDescripcion.setText("¿Qué producto deseas apartar?");
        } else if (fragment instanceof ChooseTimeResFragment) {
            txtTitulo.setText("Elige un horario");
            txtDescripcion.setText("¿En qué fecha y hora te gustaría recoger tu producto?");
        } else if (fragment instanceof UserInformationResFragment) {
            txtTitulo.setText("Información de contacto");
            txtDescripcion.setText("Estos detalles se usarán para confirmar tu reserva del producto.");
            btnSiguienteP.setText("Siguiente");
        } else if (fragment instanceof ConfirmInformationResFragment) {
            txtTitulo.setText("Confirma tu información");
            txtDescripcion.setText("Asegúrate de que toda tu información sea correcta y luego haz clic en 'Reservar' para programar tu cita.");
            btnSiguienteP.setText("Reservar");
        }
    }

    public void siguiente(View view) {
        // Lógica para avanzar al siguiente fragmento en orden
        if (currentFragment instanceof ChooseProductFragment) {
            ChooseProductFragment chooseProductFragment = (ChooseProductFragment) currentFragment;

            // Verifica si se ha seleccionado algún CheckBox en el fragmento
            if (!chooseProductFragment.isCheckBoxSelected()) {
                // Ningún CheckBox seleccionado, avisa al usuario y no avances al siguiente fragmento
                Toast.makeText(this, "Selecciona un producto antes de continuar", Toast.LENGTH_SHORT).show();
                return;
            }

            // Al menos un CheckBox seleccionado, continúa al siguiente fragmento
            currentFragment = new ChooseTimeResFragment();
        } else if (currentFragment instanceof ChooseTimeResFragment) {
            ChooseTimeResFragment chooseTimeResFragment = (ChooseTimeResFragment) currentFragment;

            // Verifica si la fecha y hora seleccionadas están dentro del rango permitido
            if (!chooseTimeResFragment.isHorarioValido()) {
                // Fecha y hora no válidas, avisa al usuario y no avances al siguiente fragmento
                Toast.makeText(this, "Selecciona una fecha y hora válidas", Toast.LENGTH_SHORT).show();
                return;
            }

            currentFragment = new UserInformationResFragment();
        } else if (currentFragment instanceof UserInformationResFragment) {
            // Validar campos en el fragmento UserInformationResFragment
            boolean camposValidos = ((UserInformationResFragment) currentFragment).validarCampos();
            if (camposValidos) {
                currentFragment = new ConfirmInformationResFragment();
            } else {
                // No avanzar si la validación falla
                return;
            }
        } else if (currentFragment instanceof ConfirmInformationResFragment) {
            if (checkPermission()) {
                generarPDF();
            } else {
                // Solicitar permisos si no están concedidos
                requestPermissions();
            }
        }
        // Muestra el fragmento actualizado
        showSelectedFragment(currentFragment);
        updateTextsForFragment(currentFragment);
    }


    @Override
    public void onBackPressed() {
        // Lógica para manejar el botón de atrás según el fragmento actual
        if (currentFragment instanceof ChooseProductFragment) {
            super.onBackPressed();
        } else if (currentFragment instanceof ChooseTimeResFragment) {
            // Elimina el tercer y segundo estado si existe
            eliminarEstado(lytEstadoDos);
            eliminarEstado(lytEstadoTres);

            // Regresa al fragmento anterior
            currentFragment = new ChooseProductFragment();
            showSelectedFragment(currentFragment);
            updateTextsForFragment(currentFragment);

        } else if (currentFragment instanceof UserInformationResFragment) {
            // Elimina el tercer estado si existe
            eliminarEstado(lytEstadoCuatro);

            // Regresa al fragmento anterior
            currentFragment = new ChooseTimeResFragment();
            showSelectedFragment(currentFragment);
            updateTextsForFragment(currentFragment);
        } else if (currentFragment instanceof ConfirmInformationResFragment) {
            // Restaura la visibilidad y diseño de frameContainer y CardInferior
            View frameContainer = findViewById(R.id.frameContainer);
            View cardInferior = findViewById(R.id.CardInferior);

            frameContainer.setVisibility(View.VISIBLE);

            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) cardInferior.getLayoutParams();
            layoutParams.topMargin = 0;
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToBottom = R.id.frameContainer;
            cardInferior.setLayoutParams(layoutParams);

            // Regresa al fragmento anterior
            currentFragment = new UserInformationResFragment();
            showSelectedFragment(currentFragment);
            updateTextsForFragment(currentFragment);
        }
    }

}