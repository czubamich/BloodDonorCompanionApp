package com.mczuba.blooddonorcompanion

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.mczuba.blooddonorcompanion.data.DonorDatabase
import com.mczuba.blooddonorcompanion.data.models.DonationDao
import com.mczuba.blooddonorcompanion.data.models.UserDao
import com.mczuba.blooddonorcompanion.util.TestUtils
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class DonorDatabaseTestClass {
    private lateinit var userDao: UserDao
    private lateinit var donationDao: DonationDao
    private lateinit var db: DonorDatabase

    @Before
    fun createDb() {
        //val context = ApplicationProvider.getApplicationContext<Context>()
        //db = Room.inMemoryDatabaseBuilder(
        //    context, DonorDatabase::class.java).build()
        //userDao = db.getUserDao()
        //donationDao = db.donationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    suspend fun writeReadDonation() {
        val donation = TestUtils.getTestDonation()
        donationDao.addDonation(donation)
        val donations = userDao.readAllUserDonations(0)
        assertThat(donations.first(), equalTo(donation))
    }
}