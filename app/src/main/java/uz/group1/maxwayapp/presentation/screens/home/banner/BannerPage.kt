package uz.group1.maxwayapp.presentation.screens.home.banner

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import uz.group1.maxwayapp.R
import uz.group1.maxwayapp.data.model.BannerUIData
import uz.group1.maxwayapp.databinding.PageBannerBinding

class BannerPage: Fragment(R.layout.page_banner) {
    private val binding by viewBinding(PageBannerBinding::bind)

    companion object{
        private const val KEY_ID = "url"
        fun newInstance(data: BannerUIData): BannerPage{
            val fragment = BannerPage()
            fragment.arguments = Bundle().apply {
                putString(KEY_ID, data.bannerUrl)
            }
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUrl = arguments?.getString(KEY_ID)
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .into(binding.imageView)
    }
}