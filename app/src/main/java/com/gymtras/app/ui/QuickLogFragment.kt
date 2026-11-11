package com.gymtras.app.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gymtras.app.R
import com.gymtras.app.databinding.FragmentQuickLogBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class QuickLogFragment : Fragment() {

    private var _binding: FragmentQuickLogBinding? = null
    private val binding get() = _binding!!
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuickLogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()
        setupDatePicker()
        setupSaveButton()
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categorias_array,
            R.layout.spinner_item
        ).apply {
            setDropDownViewResource(R.layout.spinner_dropdown_item)
        }
        binding.spCategoria.adapter = adapter
    }

    private fun setupDatePicker() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.etFecha.setText(dateFormat.format(Date()))

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            binding.etFecha.setText(dateFormat.format(calendar.time))
        }

        binding.etFecha.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupSaveButton() {
        binding.btnGuardar.setOnClickListener {
            val nombreEjercicio = binding.etNombreEjercicio.text.toString().trim()

            if (nombreEjercicio.isEmpty()) {
                binding.tilNombreEjercicio.error = getString(R.string.error_fill_required)
                binding.etNombreEjercicio.requestFocus()
                return@setOnClickListener
            } else {
                binding.tilNombreEjercicio.error = null
            }

            val peso = binding.etPeso.text.toString().ifEmpty { "0" }
            val series = binding.etSeries.text.toString().ifEmpty { "0" }
            val repeticiones = binding.etRepeticiones.text.toString().ifEmpty { "0" }
            val esEpico = binding.tbEpico.isChecked

            val mensajeToast = getString(
                R.string.toast_workout_saved,
                nombreEjercicio,
                peso,
                series,
                repeticiones
            ) + if (esEpico) "\n🔥 ¡SESIÓN ÉPICA REGISTRADA!" else ""

            Toast.makeText(requireContext(), mensajeToast, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
