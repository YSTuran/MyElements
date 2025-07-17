package yusufs.turan.myelements.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.room.Room
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import yusufs.turan.myelements.R
import yusufs.turan.myelements.adapter.ElementAdapter
import yusufs.turan.myelements.databinding.FragmentListBinding
import yusufs.turan.myelements.model.Element
import yusufs.turan.myelements.roomdb.ElementDAO
import yusufs.turan.receiptbook.roomdb.ElementDB


class ListFragment : Fragment() {

    private var _binding:FragmentListBinding? = null
    private val binding get() =_binding!!

    private lateinit var db : ElementDB
    private lateinit var elementDAO: ElementDAO
    private val mDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(requireContext() , ElementDB::class.java , "Elements").build()
        elementDAO =db.elementDao()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAddElement.setOnClickListener { newElement(it) }
        binding.elementRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        getDatas()
    }
    private fun getDatas(){
      mDisposable.add(elementDAO.getAll().subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(this::handleResponses))
    }
    private fun handleResponses(elements: List<Element>) {
        val adapter = ElementAdapter(elements) { selectedElement ->

            val action = ListFragmentDirections.actionListFragmentToElementFragment(
                atomNumarasi = selectedElement.atomNumarasi
            )
            view?.let { Navigation.findNavController(it).navigate(action) }
        }
        binding.elementRecyclerView.adapter = adapter
    }
    fun newElement(view: View){
        val action = ListFragmentDirections.actionListFragmentToElementFragment(atomNumarasi = -1)
        Navigation.findNavController(view).navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDisposable.clear()
    }


}