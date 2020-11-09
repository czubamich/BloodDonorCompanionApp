package com.mczuba.blooddonorcompanion.data

class UserRepository(private val userDao: UserDao, private val donationDao: DonationDao ) {
    suspend fun addDonation(donation: Donation) = donationDao.addDonation(donation)
    suspend fun removeDonation(donation: Donation) = donationDao.removeDonation(donation)
    suspend fun updateDonation(donation: Donation) = donationDao.updateDonation(donation)
    //fun readAllUserDonations(userId: Int) = donationDao.readAllUserDonations(userId)

    suspend fun addUser(user: User) = userDao.addUser(user)
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    suspend fun readAllUsers() = userDao.readAllUsers()
    suspend fun userExists() = userDao.getUserCount()>0;
    suspend fun readUserDonations() = userDao.readAllUserDonations()
    suspend fun checkUserExists(userId: Int) = userDao.checkUserExist(userId)
    suspend fun getUserDonationSummaryByType(userId: Int) = userDao.getUserDonationSummaryByType(userId)
    suspend fun getUserDonationSummary(userId: Int) = userDao.getUserDonationSummary(userId)
    suspend fun readUserDonations(userId: Int) = userDao.readAllUserDonations(userId)
}