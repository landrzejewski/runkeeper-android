package pl.training.runkeeper.photos

import android.Manifest
import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraX
import androidx.camera.core.ImageCapture
import androidx.camera.core.impl.ImageCaptureConfig
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import pl.training.runkeeper.databinding.FragmentPhotosBinding

class PhotosFragment : Fragment() {

    private lateinit var binding: FragmentPhotosBinding
    private val requiredPermissions = arrayOf(CAMERA)
    private val permissionsRequestCode = 1_000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPhotosBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
    }

    private fun requestPermissions() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions(requiredPermissions, permissionsRequestCode)
        }
    }

    private fun allPermissionsGranted() = requiredPermissions.all { checkSelfPermission(requireContext(), it) == PERMISSION_GRANTED }

    private fun startCamera() {

    }

}