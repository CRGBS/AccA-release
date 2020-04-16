package mattecarra.accapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_export.*
import mattecarra.accapp.R
import mattecarra.accapp.adapters.ProfileExportAdapter
import mattecarra.accapp.models.ProfileExportItem
import mattecarra.accapp.utils.ScopedFragment

class ExportFragment : ScopedFragment(), CompoundButton.OnCheckedChangeListener {

    companion object {
        fun newInstance() = ExportFragment()
    }

    private lateinit var mViewModel: ExportViewModel
    private lateinit var mProfilesRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_export, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mProfilesRecycler = export_list_rv

        mViewModel = ViewModelProvider(this).get(ExportViewModel::class.java)
        val adapter = ProfileExportAdapter()

        // Load list of profiles
        mViewModel.getProfiles().observe(viewLifecycleOwner, Observer { profiles ->
            //TODO: Create a nice 'no profiles' view to show/hide
            if (profiles.isEmpty()) {
                // Hide the view
            } else {
                // Show the view
            }

            mProfilesRecycler.adapter = ProfileExportAdapter(view.context, profiles, this)
        })

//        export_frag_fab.setOnClickListener { view ->
//            mViewModel.exportProfiles()
//        }

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, checked: Boolean) {
        val pos = mListView.getPositionForView(buttonView)
        if (pos != ListView.INVALID_POSITION) {
            var exProfile: ProfileExportItem = mListView.adapter.getItem(pos) as ProfileExportItem
            if (checked) {
                mViewModel.addProfileToExport(exProfile.getProfile())
            } else {
                mViewModel.removeProfileToExport(exProfile.getProfile())
            }

        }
    }
}