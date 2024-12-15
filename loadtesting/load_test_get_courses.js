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
            'Authorization': 'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI1YTE1Njk5Yy1iNjNkLTRlZjQtYTQyMS04NzZlOTMwMTQzMzEiLCJyb2xlIjpbIlJPTEVfSU5TVFJVQ1RPUiJdLCJpYXQiOjE3MzQzMDI2MDIsImV4cCI6MTczNDMwNjIwMn0.3PTWxh7RCmeE92nLjVYMGRREVnZq4rmREVjuoQOH_hhN234yaJS3fsAstsGG0jwX'
        },
    })

    check(res, {
        'status is 200': (r) => r.status === 200
    });
}