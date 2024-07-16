package pl.training.runkeeper.commons

sealed class ViewState {

    data object Initial : ViewState()
    data object Loading : ViewState()
    class Loaded<D>(val data: D) : ViewState()
    class Failed(val message: String) : ViewState()

}