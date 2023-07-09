package com.gemastik.bersihkanbersama.utils

import com.gemastik.bersihkanbersama.data.models.ActivityModel
import com.gemastik.bersihkanbersama.data.models.AddressModel
import com.gemastik.bersihkanbersama.data.models.AllActivityModel
import com.gemastik.bersihkanbersama.data.models.AllArticleModel
import com.gemastik.bersihkanbersama.data.models.ArticleModel
import com.gemastik.bersihkanbersama.data.models.ContactModel
import com.gemastik.bersihkanbersama.data.models.CreateNewActivityModel
import com.gemastik.bersihkanbersama.data.models.DonationActivityModel
import com.gemastik.bersihkanbersama.data.models.DonationModel
import com.gemastik.bersihkanbersama.data.models.LeaderboardModel
import com.gemastik.bersihkanbersama.data.models.OrganizationModel
import com.gemastik.bersihkanbersama.data.models.OrganizationSignUpModel
import com.gemastik.bersihkanbersama.data.models.PaymentDetailsModel
import com.gemastik.bersihkanbersama.data.models.PointHistoryModel
import com.gemastik.bersihkanbersama.data.models.PointsModel
import com.gemastik.bersihkanbersama.data.models.RewardsModel
import com.gemastik.bersihkanbersama.data.models.TeamModel
import com.gemastik.bersihkanbersama.data.models.TeamResultModel
import com.gemastik.bersihkanbersama.data.models.UserModel
import com.gemastik.bersihkanbersama.data.models.UserRegisteredModel
import com.gemastik.bersihkanbersama.data.models.UserSignUpModel
import com.gemastik.bersihkanbersama.data.models.VolunteerModel
import com.gemastik.bersihkanbersama.data.remote.response.ActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.ArticleResponse
import com.gemastik.bersihkanbersama.data.remote.response.CreateNewActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.GetAllActivityResponse
import com.gemastik.bersihkanbersama.data.remote.response.GetAllArticleResponse
import com.gemastik.bersihkanbersama.data.remote.response.LeaderboardResponse
import com.gemastik.bersihkanbersama.data.remote.response.OrganizationResponse
import com.gemastik.bersihkanbersama.data.remote.response.OrganizationSignUpResponse
import com.gemastik.bersihkanbersama.data.remote.response.PaymentDetailsResponse
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
                        createdAt = it.createdAt
                    )
                }
            ),
            activities = input.activities,
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
           activities = input.activities,
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

    fun mapAllActivityResponseToAllActivityModel(input: GetAllActivityResponse): AllActivityModel =
        AllActivityModel(
            activities = input.activities.map { data ->
                ActivityModel(
                    id = data.id,
                    organizationId = data.organizationId,
                    title = data.title,
                    description = data.description,
                    eventDate = data.eventDate,
                    coverImage = data.coverImage,
                    volunteer = VolunteerModel(
                        count = data.volunteer.count,
                        userRegistered = data.volunteer.userRegistered.map {
                            UserRegisteredModel(
                                id = it.id,
                                name = it.name,
                                phone = it.phone
                            )
                        },
                        teams = data.volunteer.teams.map { team ->
                            TeamModel(
                                name = team.name,
                                members = team.members.map {
                                    UserRegisteredModel(
                                        id = it.id,
                                        name = it.name,
                                        phone = it.phone
                                    )
                                },
                                trashResults = team.trashResults
                            )
                        }
                    ),
                    status = data.status,
                    rewards = RewardsModel(
                        participation = data.rewards.participation,
                        first = data.rewards.first,
                        second = data.rewards.second,
                        third = data.rewards.third
                    ),
                    donationActivity = DonationActivityModel(
                        totalDonation = data.donationActivity.totalDonation,
                        donationHistory = data.donationActivity.donationHistory.map {
                            DonationModel(
                                donationId = it.donationId,
                                userName = it.userName,
                                totalDonation = it.totalDonation
                            )
                        }
                    ),
                    createdAt = data.createdAt,
                    updatedAt = data.updatedAt
                )
            }
        )

    fun mapActivityResponseToActivityModel(input: ActivityResponse): ActivityModel =
        ActivityModel(
            id = input.id,
            organizationId = input.organizationId,
            title = input.title,
            description = input.description,
            eventDate = input.eventDate,
            coverImage = input.coverImage,
            volunteer = VolunteerModel(
                count = input.volunteer.count,
                userRegistered = input.volunteer.userRegistered.map {
                    UserRegisteredModel(
                        id = it.id,
                        name = it.name,
                        phone = it.phone
                    )
                },
                teams = input.volunteer.teams.map { team ->
                    TeamModel(
                        name = team.name,
                        members = team.members.map {
                            UserRegisteredModel(
                                id = it.id,
                                name = it.name,
                                phone = it.phone
                            )
                        },
                        trashResults = team.trashResults
                    )
                }
            ),
            status = input.status,
            rewards = RewardsModel(
                participation = input.rewards.participation,
                first = input.rewards.first,
                second = input.rewards.second,
                third = input.rewards.third
            ),
            donationActivity = DonationActivityModel(
                totalDonation = input.donationActivity.totalDonation,
                donationHistory = input.donationActivity.donationHistory.map {
                    DonationModel(
                        donationId = it.donationId,
                        userName = it.userName,
                        totalDonation = it.totalDonation
                    )
                }
            ),
            createdAt = input.createdAt,
            updatedAt = input.updatedAt
        )

    fun mapLeaderboardResponseToLeaderboardModel(input: LeaderboardResponse): LeaderboardModel =
        LeaderboardModel(
            leaderboard = input.leaderboard.map {
                TeamResultModel(
                    teamName = it.teamName,
                    trashResult = it.trashResults
                )
            }
        )

    fun mapPaymentDetailsResponseToPaymentDetailsModel(input: PaymentDetailsResponse): PaymentDetailsModel =
        PaymentDetailsModel(
            amount = input.amount,
            paymentUrl = input.paymentUrl,
            status = input.status
        )

    fun mapGetAllArticleResponseToAllArticleModel(input: GetAllArticleResponse): AllArticleModel =
        AllArticleModel(
            articles = input.articles.map {
                ArticleModel(
                    id = it.id,
                    title = it.title,
                    write = it.write,
                    summary = it.summary,
                    content = it.content,
                    cover = it.cover,
                    createdAt = it.createdAt,
                    updatedAt = it.updatedAt
                )
            }
        )

    fun mapArticleResponseToArticleModel(input: ArticleResponse): ArticleModel =
        ArticleModel(
            id = input.id,
            title = input.title,
            write = input.write,
            summary = input.summary,
            content = input.content,
            cover = input.cover,
            createdAt = input.createdAt,
            updatedAt = input.updatedAt
        )
}