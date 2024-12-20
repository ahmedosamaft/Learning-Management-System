import http from 'k6/http'
import {check} from 'k6';

export let options = {
    vus: 10,
    iterations: 1000,
};

export default function () {
    let page = Math.floor(Math.random() * 100);

    let res = http.get(`http://localhost:9090/api/v1/courses?page=${page}`, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIzY2E4M2FiZS0xZDVlLTQwNDYtOGQyYi1lMWE0YzcwYTIwNjciLCJyb2xlIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNzM0NTYxNTA5LCJleHAiOjE3MzQ1NjUxMDl9.0c32Cuto7uweBk8AW87YwiJIxoB_B-LntZWI8rS37z8l8ilWJPcpqShxV55yBCBc'
        },
    })

    check(res, {
        'status is 200': (r) => r.status === 200
    });
}