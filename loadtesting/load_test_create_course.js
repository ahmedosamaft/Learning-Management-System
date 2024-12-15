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
    let page = Math.floor(Math.random() * 100);

    let res = http.get(`http://localhost:9090/api/v1/courses?page=${page}`, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI1YTE1Njk5Yy1iNjNkLTRlZjQtYTQyMS04NzZlOTMwMTQzMzEiLCJyb2xlIjpbIlJPTEVfSU5TVFJVQ1RPUiJdLCJpYXQiOjE3MzQyNjcwMDIsImV4cCI6MTczNDI3MDYwMn0.6pvWKeDyFt5rCnd-FaFbfxPZe14Sfweg9ztdtpbTChH46wfagM88TmRyCEVlGPGu'
        },
    })

    // console.log(res);
    // console.log(res.body);
    check(res, {
        'status is 200': (r) => r.status === 200
    });
}