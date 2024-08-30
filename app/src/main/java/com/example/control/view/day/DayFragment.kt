package com.example.control.view.day

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.control.R
import com.example.control.adapters.SomethingAdapter
import com.example.control.databinding.FragmentDayBinding
import com.example.control.listners.SomethingListener
import com.example.control.room.Something
import com.example.control.room.MainDb
import com.example.control.room.SomethingRepository
import com.example.control.tools.Constants
import com.example.control.tools.SwipeGesture
import com.example.control.view.addSomething.AddSomethingActivity
import java.text.SimpleDateFormat
import java.util.Locale

class DayFragment() : Fragment(), SomethingListener {

    var day: String = Constants.SUNDAY

    lateinit var binding: FragmentDayBinding
    lateinit var adapter: SomethingAdapter

    private val viewModel: DayViewModel by lazy {
        ViewModelProvider(requireActivity(),
            DayViewModelFactory(SomethingRepository(
                MainDb.getDb(requireActivity()))))[DayViewModel::class.java]
    }

    val someActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        update()}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDayBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        update()
        setListeners()
        setSwipeListener()
    }

    private fun setListeners() {
        binding.floatingActionButton.setOnClickListener {
            binding.animCircle.visibility = View.VISIBLE
            binding.animCircle.startAnimation(AnimationUtils.loadAnimation(requireActivity(), R.anim.anim_for_fab))

            val handler = android.os.Handler()
            handler.postDelayed({
                val intent = Intent(requireActivity(), AddSomethingActivity::class.java).putExtra("day", day)
                someActivityResultLauncher.launch(intent)}, 700)
        }
    }

    private fun setSwipeListener() {
        val swipeGesture = object : SwipeGesture(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val something = adapter.getSomethingByListId(viewHolder.adapterPosition)
                        viewModel.delete(something.id)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvSomething)
    }

    private fun update() {
        val listDb = viewModel.getAllItems(day)

        listDb.observe(requireActivity()) { list ->
            binding.imageList.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            binding.tvList.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE

            val sortedList = viewModel.sortByTime(list)
            adapter = SomethingAdapter(sortedList, this@DayFragment)
            binding.rvSomething.adapter = adapter
        }
    }


    override fun onSomethingClicked(something: Something) {
        val builder = AlertDialog.Builder(requireActivity(), R.style.DialogTheme)
            .setTitle(something.title)
            .setMessage(something.data)
            .setPositiveButton("Окей") { dialogInterface, i -> }
        builder.show()
    }

    override fun onTimeClicked(something: Something) {
        val calendar: Calendar = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            val newTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
            viewModel.updateTime(newTime.toString(), something.id)
        }
        TimePickerDialog(
            requireActivity(),
            timeSetListener,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    companion object {
        fun newInstance(dayPut: String): DayFragment {
            val fragment = DayFragment()
            fragment.day = dayPut
            return fragment
        }
    }
}