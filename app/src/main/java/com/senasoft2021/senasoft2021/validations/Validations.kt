package com.senasoft2021.senasoft2021.validations

import android.widget.EditText

class Validations {

    companion object{

        /**
         * validar si el editText contiene texto
         * @return true si el editText esta vacio - false si no lo esta
         */
        fun validateEditText(editText: EditText)=editText.text.isNullOrEmpty()
    }//end Comp

}