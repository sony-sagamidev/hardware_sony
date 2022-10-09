package egg.lab.extra

import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import egg.lab.extra.utils.CMUtils


private const val TAG : String = "XperiSettings";

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private var mCMCtrl : CMUtils? = null

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

            this.mCMCtrl = CMUtils(context)
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            // Set state into Settings

            findPreference<SwitchPreference>("switchCreatorMode")!!.setOnPreferenceChangeListener { _, state ->
                if (state as Boolean) {
                    // TODO
                    mCMCtrl?.enableCM()
                    Settings.System.putInt(context?.contentResolver, "egg_extra_cm", 1)
                } else {
                    mCMCtrl?.disableCM()
                    Settings.System.putInt(context?.contentResolver, "egg_extra_cm", 0)
                }
                true
            }

            // About
            findPreference<Preference>("about")!!.setOnPreferenceClickListener {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
                builder.setTitle("EGG EXTRA")
                builder.setMessage(R.string.about_dialog)
                builder.setCancelable(false)
                builder.setPositiveButton("OKAY") { dialog, _ -> dialog.dismiss() }
                val dialog: AlertDialog = builder.create()
                dialog.show()
                true
            }
        }


    }
}
