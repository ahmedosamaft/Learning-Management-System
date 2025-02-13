import requests

url = "http://localhost:9090/api/v1/auth"
payload = {
    "email": "abdelhady@fcai.admin.com",
    "password": "123456"
}


response = requests.post(url, json=payload)

print(response.json()['data']['user']['token'])
