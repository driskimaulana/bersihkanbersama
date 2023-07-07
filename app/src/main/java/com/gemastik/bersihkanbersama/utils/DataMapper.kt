package com.gemastik.bersihkanbersama.utils

import com.gemastik.bersihkanbersama.data.models.ExampleModel
import com.gemastik.bersihkanbersama.data.remote.response.ExampleResponse

/***
 * Object to mapping response to model vice versa
 */
object DataMapper {
    fun mapExampleResponseToExampleModel(input: ExampleResponse): ExampleModel =
        ExampleModel(
            example = input.example
        )

    fun mapExampleModelToExampleResponse(input: ExampleModel): ExampleResponse =
        ExampleResponse(
            example = input.example
        )
}