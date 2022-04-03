package com.app.catapi.presentation.screens

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.app.catapi.databinding.FragmentDialogBinding
import com.app.catapi.model.CatItem
import com.app.catapi.util.constants.Constants.FAILURE_TEXT
import com.app.catapi.util.constants.Constants.SUCCESS_TEXT
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import java.io.BufferedOutputStream

class DetailFragment : DialogFragment() {

    private var _binding: FragmentDialogBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args: DetailFragmentArgs by navArgs()

    private val picasso: Picasso by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val catItem = args.detailItem

        setImage(catItem)

        binding.btnSave.setOnClickListener {
            val bitmap = binding.ivDetail.drawToBitmap()
            try {
                saveToGallery(bitmap = bitmap)
                Toast.makeText(requireContext(), SUCCESS_TEXT, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), FAILURE_TEXT, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveToGallery(bitmap: Bitmap) {
        val contentResolver = requireContext().contentResolver
        val values = setContentValues(date = System.currentTimeMillis())
        val uri = buildUri(contentResolver, values)

        if (uri != null) {
            contentResolver.openOutputStream(uri)?.let { stream ->
                val outputStream = BufferedOutputStream(stream)
                bitmap.compress(Bitmap.CompressFormat.JPEG, BITMAP_QUALITY, outputStream)
                outputStream.close()
            }
        }
    }

    private fun setContentValues(date: Long): ContentValues {
        return ContentValues().apply {
            put(MediaStore.Images.Media.DATE_TAKEN, date)
            put(MediaStore.Images.Media.MIME_TYPE, JPEG_MIME_TYPE)
            put(MediaStore.Images.Media.DISPLAY_NAME, "CAT_$date")
        }
    }

    private fun buildUri(contentResolver: ContentResolver, values: ContentValues): Uri? {
        return contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )
    }

    private fun setImage(catItem: CatItem) {
        picasso
            .load(catItem.src)
            .fit()
            .centerCrop()
            .into(binding.ivDetail)
    }

    private companion object {
        private const val JPEG_MIME_TYPE = "image/jpeg"
        private const val BITMAP_QUALITY = 100
    }
}