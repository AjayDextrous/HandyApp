package com.makeathon.handyapp

import com.makeathon.handyapp.models.Contact
import com.makeathon.handyapp.models.ContactType
import com.makeathon.handyapp.models.Job

class MockDataRepositoryImpl : DataRepository {

    var realDataRepo=  RealDataRepositoryImpl()

    override fun getJobs(): List<Job> {
        return listOf(realDataRepo.getJob(1)!!)
//        return listOf(
//
//            Job(
//                jobId = 1,
//                jobTitle = "AC Repair at Darmstadt",
//                jobDescription = "Customer has complained that the AC is not cooling well enough. looks to be a compressor issue - make sure to check the valves and other stuff , and send me a report by friday regarding the job",
//                addressString = "1234 Elm Street, " + "Springfield, Anytown 56789, " + "United States",
//                locationMapsLink = "https://goo.gl/maps/jwCdboZZcBGSbV4S7",
//                time = "8:15 AM"
//            ),
//            Job(
//                2,
//                "Plumbing work - Broken Sink",
//                "Customer has complained that the AC is not cooling well enough. looks to be a compressor issue - make sure to check the valves and other stuff , and send me a report by friday regarding the job",
//                "1234 Elm Street, " + "Springfield, Anytown 56789, " + "United States",
//                "https://goo.gl/maps/jwCdboZZcBGSbV4S7",
//                "10:00 AM"
//            ),
//            Job(
//                3,
//                "Radiator Leakage - Needs new Check Valve",
//                "Customer has complained that the AC is not cooling well enough. looks to be a compressor issue - make sure to check the valves and other stuff , and send me a report by friday regarding the job",
//                "1234 Elm Street, " + "Springfield, Anytown 56789, " + "United States",
//                "https://goo.gl/maps/jwCdboZZcBGSbV4S7",
//                "11:15 AM"
//            ),
//            Job(
//                4,
//                "AC Repair at Munich",
//                "Customer has complained that the AC is not cooling well enough. looks to be a compressor issue - make sure to check the valves and other stuff , and send me a report by friday regarding the job",
//                "1234 Elm Street, " + "Springfield, Anytown 56789, " + "United States",
//                "https://goo.gl/maps/jwCdboZZcBGSbV4S7",
//                "12:15 AM"
//            ),
//            Job(
//                5,
//                "AC Repair Technician",
//                "AC not cooling properly",
//                "123 Main Street, Anytown, USA",
//                "https://maps.google.com/?q=123+Main+Street,+Anytown,+USA",
//                "Yesterday",
//                isFinished = false
//            ),
//            Job(
//                6,
//                "Plumber",
//                "Leaking faucet in the kitchen",
//                "456 Elm Avenue, Somewhere City, Country",
//                "https://maps.google.com/?q=456+Elm+Avenue,+Somewhere+City,+Country",
//                "Yesterday",
//                isFinished = true
//            ),
//            Job(
//                7,
//                "Electrician",
//                "Power outage in the living room",
//                "789 Oak Road, Anotherplace, Region",
//                "https://maps.google.com/?q=789+Oak+Road,+Anotherplace,+Region",
//                "Yesterday",
//                isFinished = true
//            ),
//            Job(
//                8,
//                "HVAC Technician",
//                "Heater not working in the office",
//                "987 Pine Lane, Anytown, USA",
//                "https://maps.google.com/?q=987+Pine+Lane,+Anytown,+USA",
//                "Yesterday",
//                isFinished = true
//            ),
//            Job(
//                9,
//                "Carpenter",
//                "Repairing a broken cabinet in the bathroom",
//                "654 Maple Street, Somewhere City, Country",
//                "https://maps.google.com/?q=654+Maple+Street,+Somewhere+City,+Country",
//                "Last Week",
//                isFinished = true
//            ),
//            Job(
//                10,
//                "Appliance Repair Technician",
//                "Fixing a malfunctioning dishwasher",
//                "321 Oak Avenue, Anotherplace, Region",
//                "https://maps.google.com/?q=321+Oak+Avenue,+Anotherplace,+Region",
//                "Last Week",
//                isFinished = true
//            ),
//            Job(
//                11,
//                "Painter",
//                "Painting the exterior walls of a house",
//                "789 Walnut Road, Anytown, USA",
//                "https://maps.google.com/?q=789+Walnut+Road,+Anytown,+USA",
//                "Last Week",
//                isFinished = true
//            )
//        )
    }

    override fun getContacts(): List<Contact> {
        return listOf(
            Contact(1, "Martha K.", ContactType.BOSS, R.drawable.boss_1, "Did you get time to check the AC Repair?"),
            Contact(2, "Emily R.", ContactType.BOSS, R.drawable.boss_2, "I've processed your Salary slip", "9:15 AM"),
            Contact(3, "Customer - AC Repair", ContactType.CUSTOMER, R.drawable.customer_9, "The red light seems to be blinking.", "8:00 AM"),
            Contact(4, "Customer - Plumbing Westpark", ContactType.CUSTOMER, R.drawable.customer_3, "Thanks for all your help!", "7:55 AM"),
            Contact(5, "Customer - Heater Repair 3", ContactType.CUSTOMER, R.drawable.customer_4, "I think it's fixed now", "Yesterday"),
            Contact(6, "Supplier - Jakob", ContactType.COWORKER, R.drawable.customer_6, "I'm on holiday next week, maybe we could talk about it afterwards?", "19/10/22"),

        )
    }

    override fun getJob(jobId: Int): Job? {
        return null
    }
}