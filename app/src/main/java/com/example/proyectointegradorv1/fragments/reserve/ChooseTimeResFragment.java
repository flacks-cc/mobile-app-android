package com.example.proyectointegradorv1.fragments.reserve;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.proyectointegradorv1.R;
import com.example.proyectointegradorv1.activities.ReservationsActivity;
import com.example.proyectointegradorv1.activities.ReserveActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ChooseTimeResFragment extends Fragment{

    private DatePicker datePicker;
    private TimePicker timePicker;

    public ChooseTimeResFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_time_res, container, false);

        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);

        // Obtén la fecha actual
        final Calendar calendar = Calendar.getInstance();

        // Establece la fecha mínima como la fecha actual
        datePicker.setMinDate(calendar.getTimeInMillis());

        // Agrega escuchadores a DatePicker y TimePicker
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Validación para no permitir fechas anteriores al día actual
                        if (calendar.before(obtenerFechaYHoraSeleccionadas())) {
                            // Cuando la fecha cambia, llama al método para mostrar el horario seleccionado
                            mostrarHorarioSeleccionado();
                        } else {
                            // Restaura la fecha seleccionada a la fecha actual
                            datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                        }
                    }
                });

        // Dentro de tu método onCreateView
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // Llama al método para mostrar el horario seleccionado
                mostrarHorarioSeleccionado();
            }
        });

        mostrarHorarioSeleccionado();

        return view;
    }

    // Método para mostrar la fecha y la hora seleccionadas
    public void mostrarHorarioSeleccionado() {
        Calendar horarioSeleccionado = obtenerFechaYHoraSeleccionadas();

        // Formatea la fecha y hora según tus necesidades
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String fechaYHoraFormateada = dateFormat.format(horarioSeleccionado.getTime());

        // Llama al método en ReservationsActivity para agregar el tercer estado
        ((ReserveActivity) requireActivity()).agregarTercerEstado("Horario de la reserva", fechaYHoraFormateada);
    }

    // Método para obtener la fecha y la hora seleccionadas
    public Calendar obtenerFechaYHoraSeleccionadas() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        int hour;
        int minute;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            // Para versiones anteriores a Android 6.0
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        // Crear un objeto Calendar con la fecha y hora seleccionadas
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        return calendar;
    }

    public boolean isHorarioValido() {
        Calendar horarioSeleccionado = obtenerFechaYHoraSeleccionadas();
        int horaInicio = 10; // Hora de inicio siempre es 10:00 am

        // Establece el rango de horas en el TimePicker según el día de la semana
        int horaFin = (horarioSeleccionado.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ? 15 : 21;

        // Verifica si la hora seleccionada está dentro del rango permitido
        if (horarioSeleccionado.get(Calendar.HOUR_OF_DAY) < horaInicio || horarioSeleccionado.get(Calendar.HOUR_OF_DAY) > horaFin) {
            // Muestra un Toast con el mensaje específico según el día de la semana
            String mensaje = (horarioSeleccionado.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                    ? "Selecciona un horario válido (10:00 am - 3:00 pm)"
                    : "Selecciona un horario válido (10:00 am - 9:00 pm)";
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show();
            return false;
        }

        // Compara la hora seleccionada con el límite y la hora de inicio
        return true;
    }

}