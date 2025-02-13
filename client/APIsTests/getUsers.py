import requests

url = "http://localhost:9090/api/v1/admin/users"
headers = {
    "Authorization": "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI3ZDJhZGFiYy1mNjY2LTQ0OTMtYjZlMi04MDVhMjAyZTRkNTEiLCJyb2xlIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNzM5NDgzNTQ2LCJleHAiOjE3Mzk0ODcxNDZ9.TUrps4H9WUwkTbIhZabG9YqTnMqMfnjX5uvurjAes1pC6ElvokALIqz2PO0dDLGm",
}

response = (requests.get(url, headers=headers)).json()

print(response["data"])
