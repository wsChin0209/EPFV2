package my.edu.tarc.epf.ui.investment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import my.edu.tarc.epf.databinding.FragmentInvestmentBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

class InvestmentFragment : Fragment() {

    private var _binding: FragmentInvestmentBinding? = null
    var calendar = getInstance()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val investmentViewModel =
            ViewModelProvider(this).get(InvestmentViewModel::class.java)

        _binding = FragmentInvestmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> //Set Calendar values
                calendar.set(YEAR, year)
                calendar.set(MONTH, monthOfYear)
                calendar.set(DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        binding.buttonDOB.setOnClickListener {
            DatePickerDialog(it.context,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                calendar.get(YEAR),
                calendar.get(MONTH),
                calendar.get(DAY_OF_MONTH)).show()
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.buttonDOB.text = sdf.format(calendar.getTime())

        val endDate = getInstance()

        binding.textViewAge.text = daysBetween(calendar, endDate).div(365).toString()
    }

    fun daysBetween(startDate: Calendar, endDate: Calendar?): Long {
        val date = startDate.clone() as Calendar
        var daysBetween: Long = 0
        while (date.before(endDate)) {
            date.add(DAY_OF_MONTH, 1)
            daysBetween++
        }
        return daysBetween
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}