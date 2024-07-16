package pl.training.runkeeper.commons

sealed class ViewState {

    data object Initial : ViewState()
    data object Loading : ViewState()
    class Loaded<D>(val data: D) : ViewState() {

        @Suppress("UNCHECKED_CAST")
        fun <T> get(): T {
            return data as T
        }

    }
    class Failed(val message: String) : ViewState()

}