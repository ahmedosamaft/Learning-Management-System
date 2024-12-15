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
        "instructorId": "5a15699c-b63d-4ef4-a421-876e93014331"
    };

    let res = http.post("http://localhost:9090/api/v1/courses", JSON.stringify(data), {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI1YTE1Njk5Yy1iNjNkLTRlZjQtYTQyMS04NzZlOTMwMTQzMzEiLCJyb2xlIjpbIlJPTEVfSU5TVFJVQ1RPUiJdLCJpYXQiOjE3MzQzMDI2MDIsImV4cCI6MTczNDMwNjIwMn0.3PTWxh7RCmeE92nLjVYMGRREVnZq4rmREVjuoQOH_hhN234yaJS3fsAstsGG0jwX'
        },
    })

    check(res, {
        'status is 201': (r) => r.status === 201
    });
}