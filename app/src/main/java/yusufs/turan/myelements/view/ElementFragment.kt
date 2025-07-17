package yusufs.turan.myelements.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.room.Room
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import yusufs.turan.myelements.databinding.FragmentElementBinding
import yusufs.turan.myelements.model.Element
import yusufs.turan.myelements.roomdb.ElementDAO
import yusufs.turan.receiptbook.roomdb.ElementDB

class ElementFragment : Fragment() {

    private lateinit var binding: FragmentElementBinding
    private lateinit var elementDAO: ElementDAO
    private val disposable = CompositeDisposable()
    private var selectedAtomNumarasi: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentElementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Room.databaseBuilder(
            requireContext(),
            ElementDB::class.java,
            "Elements"
        ).build()
        elementDAO = db.elementDao()

        arguments?.let {
            val atomNumarasi = ElementFragmentArgs.fromBundle(it).atomNumarasi
            if (atomNumarasi != -1) {
                selectedAtomNumarasi = atomNumarasi

                disposable.add(
                    elementDAO.findByAtomNumarasi(atomNumarasi)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { element ->
                            binding.editTextName.setText(element.name)
                            binding.editTextAtomNumarasi.setText(element.atomNumarasi.toString())
                            binding.editTextAgirlik.setText(element.agirlik.toString())
                            binding.editTextHal.setText(element.hal)
                            binding.editTextGrup.setText(element.grup)
                            binding.buttonSave.text = "Güncelle"
                            binding.buttonDelete.visibility = View.VISIBLE
                        }
                )
            } else {
                binding.buttonSave.text = "Kaydet"
                binding.buttonDelete.visibility = View.GONE
            }
        }

        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val number = binding.editTextAtomNumarasi.text.toString().toIntOrNull()
            val weight = binding.editTextAgirlik.text.toString().toDoubleOrNull()
            val state = binding.editTextHal.text.toString()
            val group = binding.editTextGrup.text.toString()

            if (name.isNotEmpty() && number != null && weight != null && state.isNotEmpty() && group.isNotEmpty()) {
                val element = Element(number, name, weight, state, group)
                disposable.add(
                    elementDAO.insert(element)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            Toast.makeText(requireContext(), "Element kaydedildi", Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(view).popBackStack()
                        }
                )
            } else {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonDelete.setOnClickListener {
            selectedAtomNumarasi?.let { atomNumarasi ->
                disposable.add(
                    elementDAO.findByAtomNumarasi(atomNumarasi)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { element ->
                            disposable.add(
                                elementDAO.delete(element)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe {
                                        Toast.makeText(requireContext(), "Element silindi", Toast.LENGTH_SHORT).show()
                                        Navigation.findNavController(view).popBackStack()
                                    }
                            )
                        }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }
}
