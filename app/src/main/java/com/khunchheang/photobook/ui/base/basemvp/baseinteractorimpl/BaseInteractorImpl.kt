package com.khunchheang.photobook.ui.base.basemvp.baseinteractorimpl

import com.khunchheang.photobook.R
import com.khunchheang.photobook.ui.base.basemvp.BaseInteractor
import com.khunchheang.photobook.ui.base.basemvp.response.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

abstract class BaseInteractorImpl<T> : BaseInteractor<T>, Callback<T> {

    internal var call: Call<T>? = null
    internal var completion: ((responseModel: ResponseModel) -> Unit)? = null

    override fun onCancelLoading() {
        call?.cancel()
    }

    override fun onRetry(completion: (responseModel: ResponseModel) -> Unit) {
        if (call != null) call?.clone()?.enqueue(this)
        else completion(ExceptionResponseModel("Exception"))
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is UnknownHostException) completion?.invoke(ErrorResponseIntModel(R.string.pls_check_internet))
        else if (!call.isCanceled) completion?.invoke(ErrorResponseIntModel(R.string.connection_timeout))
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (!response.isSuccessful) {
            if (response.code() == 503 || response.code() == 500 || response.code() == 501) {
                completion?.invoke(ServerDownResponseModel(R.string.cannot_connect_server))
            } else if (response.code() == 401) {
                completion?.invoke(UnauthorizedResponseModel(R.string.unauthorized_request))
            } else {
                try {
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    completion?.invoke(ExceptionResponseModel(jObjError.getString("message")))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        if (response.body() == null) return
        completion?.invoke(SuccessResponseModel(response.body()))
    }

    override fun clearReference() {
        completion = null
        call = null
    }
}