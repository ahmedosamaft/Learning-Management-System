import http from 'k6/http'
import {check} from 'k6';

export let options = {
    vus: 10,
    iterations: 1000,
};

export default function () {
    let data = {
        "code": "LOADTEST",
        "name": "Introduction to Computer Science",
        "description": "This course covers basic principles of computer science and programming.",
        "instructorId": "3acc1a41-3b0a-4cbd-85c2-92471277a143"
    };

    let res = http.post("http://localhost:9090/api/v1/courses", JSON.stringify(data), {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIzY2E4M2FiZS0xZDVlLTQwNDYtOGQyYi1lMWE0YzcwYTIwNjciLCJyb2xlIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNzM0NTYxNTA5LCJleHAiOjE3MzQ1NjUxMDl9.0c32Cuto7uweBk8AW87YwiJIxoB_B-LntZWI8rS37z8l8ilWJPcpqShxV55yBCBc'
        },
    })
   // console.log(res.body);
    check(res, {
        'status is 201': (r) => r.status === 201
    });
}