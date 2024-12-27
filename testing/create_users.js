const axios = require('axios');

const BASE_URL = "http://localhost:9090/api/v1";
const COURSE_ID = "2ffe53e1-572e-4617-9087-a73a71424e11";

const generateEmail = (index) => `student${index}@fci.student.eg`;

const createStudent = async (index) => {
    const studentData = {
        name: `Student${index}`,
        email: generateEmail(index),
        password: "123456",
        role: "STUDENT"
    };
    try {
        const response = await axios.post(`${BASE_URL}/auth/register`, studentData);
        return response.data.data.user.token;
    } catch (error) {
        console.error(`Error creating student ${index}:`, error.response?.data || error.message);
    }
};

// Function to enroll a student in the course
const enrollStudent = async (token) => {
    try {
        const response = await axios.post(
            `${BASE_URL}/students/courses/${COURSE_ID}`,
            {},
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
    } catch (error) {
        console.error(`Error enrolling student`, error.response?.data || error.message);
        throw error
    }
};

// Function to create and enroll 100,000 students
const createAndEnrollStudents = async (l, r) => {
    for (let i = l; i <= r; i++) {
        try {
            let token = await createStudent(i);
            console.log(`student ${i} enrolled`);
            
            await enrollStudent(token);
        } catch (error) {
            console.error(`Failed for student ${i}. Continuing with the next one.`);
        }
    }
};

async function main() {
    let time = Date.now()
    let arr = [];
    for (let index = 1; index <= 20; index++) {
        arr.push(createAndEnrollStudents(1000000 + (index - 1) * 10000, 1000000 + index * 10000));
    }
    Promise.all(arr).then(_ => console.log("Finished!"));
    console.log(Date.now() - time);
}

main();
