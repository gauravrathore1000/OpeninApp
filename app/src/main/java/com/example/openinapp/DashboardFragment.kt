package com.example.openinapp

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openinapp.Adapter.LinksAdapter
import com.example.openinapp.Model.dashboardAPIResponse.OverallUrlChart
import com.example.openinapp.Model.dashboardAPIResponse.TopLink
import com.example.openinapp.Utils.Status
import com.example.openinapp.ViewModelFactory.DashboardViewModelFactory
import com.example.openinapp.databinding.FragmentDashbpardBinding
import com.example.openinapp.network.Repository
import com.example.openinapp.viewmodels.DashboardViewModel
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class DashboardFragment : Fragment(), View.OnClickListener {
    lateinit var binding : FragmentDashbpardBinding
    lateinit var linksAdapter: LinksAdapter
    var dashboardViewModel : DashboardViewModel?=null
    var topLinksList = ArrayList<TopLink>()
    var urlChartList: OverallUrlChart? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
     // val view =  inflater.inflate(R.layout.fragment_dashbpard, container, false)
      binding = FragmentDashbpardBinding.inflate(layoutInflater)


      inithEADER()
      binding.onClick = this


      val viewModelFactory = DashboardViewModelFactory(Application(), Repository())
      dashboardViewModel =  ViewModelProvider(this, viewModelFactory)[DashboardViewModel::class.java]
      dashboardViewModel!!.hitApi()
      observer()

      return binding.root
    }

    fun inithEADER() {
        binding.tvTopLinks.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
        binding.tvTopLinks.setBackgroundResource(R.drawable.button_bg)
    }

    override fun onClick(p0: View?) {
        when(p0){
            binding.tvTopLinks->{
                binding.tvTopLinks.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                binding.tvTopLinks.setBackgroundResource(R.drawable.button_bg)
                binding.tvRecentLinks.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey))
                binding.tvRecentLinks.setBackgroundResource(R.drawable.bg_transparent)
            }

            binding.tvRecentLinks->{
                binding.tvRecentLinks.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                binding.tvRecentLinks.setBackgroundResource(R.drawable.button_bg)
                binding.tvTopLinks.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey))
                binding.tvTopLinks.setBackgroundResource(R.drawable.bg_transparent)

            }
        }
    }

    fun links() {
        binding.rvLinks.layoutManager = LinearLayoutManager(requireContext())
        linksAdapter = LinksAdapter(requireContext(), topLinksList)
        binding.rvLinks.adapter = linksAdapter
    }




    fun observer(){
        dashboardViewModel?.resultDashboard?.observe(viewLifecycleOwner, Observer {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideProgress()
                        topLinksList.clear()
                        topLinksList.addAll(it?.data?.data!!.top_links)
                        urlChartList=it?.data?.data!!.overall_url_chart
                        links()
                        settingChart()
                        Log.d("topLinksList", "${it?.data?.data}")

                    }
                    Status.LOADING->{
                        showProgress(requireContext())
                    }
                    Status.ERROR->{
                        hideProgress()
                    }
                    }
                }
            })
        }

    private fun settingChart() {
        // Sample data
        val entries = ArrayList<Entry>()
        entries.add(Entry(1f, urlChartList!!.`2024-01-23`.toFloat()))
        entries.add(Entry(2f, urlChartList!!.`2024-01-24`.toFloat()))
        entries.add(Entry(3f, urlChartList!!.`2024-01-25`.toFloat()))
        entries.add(Entry(4f, urlChartList!!.`2024-01-29`.toFloat()))
        entries.add(Entry(5f, urlChartList!!.`2024-01-30`.toFloat()))
        entries.add(Entry(5f, urlChartList!!.`2024-01-31`.toFloat()))

        // Creating a dataset
        val dataSet = LineDataSet(entries, "Label for Dataset")

        // Customize the dataset
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLUE

        // Creating a line data object and set the dataset
        val lineData = LineData(dataSet)

        // Setting data to the chart
        binding.lineChart.data = lineData

        // Customize X-axis
        val xAxis: XAxis = binding.lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May")
        xAxis.valueFormatter = IndexAxisValueFormatter(months)

        // Customize Y-axis
        val yAxisLeft: YAxis = binding.lineChart.axisLeft
        val yAxisRight: YAxis = binding.lineChart.axisRight
        yAxisLeft.setDrawGridLines(false)
        yAxisRight.setDrawGridLines(false)

        // Customize chart description
        val description = Description()
        description.text = "Chart Description"
        binding.lineChart.description = description

        // Invalidate the chart to refresh
        binding.lineChart.invalidate()
    }

    private var progressDialog: Dialog? = null
    fun hideProgress() {
        try {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
                progressDialog!!.cancel()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun showProgress(requireContext: Context) {
        try {
            if (progressDialog != null) {
                progressDialog!!.dismiss()
                progressDialog!!.cancel()
            }
            progressDialog = Dialog(requireContext)
            progressDialog!!.setContentView(R.layout.progress_dialog)
            progressDialog!!.setCancelable(false)
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}