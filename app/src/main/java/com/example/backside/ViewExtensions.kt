import android.view.View

fun View.setLogoutClickListener(logoutAction: () -> Unit) {
    setOnClickListener {
        logoutAction.invoke()
    }
}
