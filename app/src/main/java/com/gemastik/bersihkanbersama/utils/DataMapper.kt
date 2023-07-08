package com.gemastik.bersihkanbersama.utils

import com.gemastik.bersihkanbersama.data.models.AddressModel
import com.gemastik.bersihkanbersama.data.models.ContactModel
import com.gemastik.bersihkanbersama.data.models.CreateNewActivityModel
import com.gemastik.bersihkanbersama.data.models.OrganizationModel
import com.gemastik.bersihkanbersama.data.models.OrganizationSignUpModel
import com.gemastik.bersihkanbersama.data.models.PointHistoryModel
import com.gemastik.bersihkanbersama.data.models.PointsModel
import com.gemastik.bersihkanbersama.data.models.UserModel
import com.gemastik.bersihkanbersama.data.models.UserSignUpModel
import com.gemastik.bersihkanbersama.data.remote.response.CreateNewActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.OrganizationResponse
import com.gemastik.bersihkanbersama.data.remote.response.OrganizationSignUpResponse
import com.gemastik.bersihkanbersama.data.remote.response.UserResponse
import com.gemastik.bersihkanbersama.data.remote.response.UserSignUpResponse

/***
 * Object to mapping response to model vice versa
 */
object DataMapper {
    fun mapUserResponseToUserModel(input: UserResponse): UserModel =
        UserModel(
            id = input.id,
            name = input.name,
            phone = input.phone,
            role = input.role,
            email = input.email,
            address = AddressModel(
                phone = input.address.phone,
                province = input.address.province,
                subDistrict = input.address.subDistrict,
                city = input.address.city,
                fullAddress = input.address.fullAddress,
                postalCode = input.address.postalCode
            ),
            points = PointsModel(
                totalPoints = input.points.totalPoints,
                pointHistory = input.points.pointHistory.map {
                    PointHistoryModel(
                        title = it.title,
                        productName = it.productName,
                        pointOut = it.pointOut,
                        pointIn = it.pointIn,
                        createdAt = it.createdAt,
                        updatedAt = it.updatedAt
                    )
                }
            ),
            createdAt = input.createdAt,
            updatedAt = input.updatedAt,
            token = ""
        )

    fun mapUserSignUpResponseToUserSignUpModel(input: UserSignUpResponse): UserSignUpModel =
        UserSignUpModel(
            token = input.token,
            userId = input.userId
        )

    fun mapOrganizationResponseToOrganizationModel(input: OrganizationResponse): OrganizationModel =
       OrganizationModel(
           id = input.id,
           name = input.name,
           email = input.email,
           role = input.role,
           description = input.description,
           city = input.city,
           logo = input.logo,
           instagram = input.instagram,
           contact = ContactModel(
               name = input.contact.name,
               phone = input.contact.phone
           ),
           createdAt = input.createdAt,
           updatedAt = input.updatedAt,
           token = ""
       )

    fun mapOrganizationSignUpResponseToOrganizationSignUpModel(input: OrganizationSignUpResponse): OrganizationSignUpModel =
        OrganizationSignUpModel(
            token = input.token,
            organizationId = input.organizationId
        )

    fun mapCreateNewActivityResponseToCreateNewActivityModel(input: CreateNewActivityResponse): CreateNewActivityModel =
        CreateNewActivityModel(
            activityId = input.activityId
        )
}