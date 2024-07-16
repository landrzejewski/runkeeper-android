package pl.training.runkeeper.commons

sealed class ViewState<D> {

    class Initial<D> : ViewState<D>()
    class Loading<D> : ViewState<D>()
    class Loaded<D>(val data: D) : ViewState<D>()
    class Failed<D>(val message: String) : ViewState<D>()

}